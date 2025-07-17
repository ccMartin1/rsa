package mx.com.vepormas.outseer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsa.csd.ws.ActionCode;
import com.rsa.csd.ws.AnalyzeRequest;
import com.rsa.csd.ws.AnalyzeResponse;
import com.rsa.csd.ws.AuthenticateResponse;
import com.rsa.csd.ws.ChallengeResponse;
import com.rsa.csd.ws.CreateUserResponse;
import com.rsa.csd.ws.EventType;
import com.rsa.csd.ws.NotifyResponse;
import com.rsa.csd.ws.OTPChallengeResponse;
import com.rsa.csd.ws.UpdateUserResponse;
import com.rsa.csd.ws.UserStatus;
import com.rsa.csd.ws.WSUserType;
import lombok.extern.slf4j.Slf4j;
import mx.com.vepormas.outseer.OutseerClient;
import mx.com.vepormas.outseer.OutseerException;
import mx.com.vepormas.outseer.controller.pojo.DataT24;
import mx.com.vepormas.outseer.controller.pojo.EndChallengeResult;
import mx.com.vepormas.outseer.controller.pojo.NotifyOutseerResponse;
import mx.com.vepormas.outseer.controller.pojo.OTPRequest;
import mx.com.vepormas.outseer.controller.pojo.OutseerRequest;
import mx.com.vepormas.outseer.controller.pojo.OutseerResponse;
import mx.com.vepormas.outseer.controller.pojo.OutseerUserResponse;
import mx.com.vepormas.outseer.controller.pojo.RequestT24;
import mx.com.vepormas.outseer.controller.pojo.Response;
import mx.com.vepormas.outseer.controller.pojo.ResponseBlockedUser;
import mx.com.vepormas.outseer.controller.pojo.ResponseT24;
import mx.com.vepormas.outseer.mapper.OutseerMapper;
import mx.com.vepormas.outseer.mapper.T24Mapper;
import mx.com.vepormas.outseer.model.outseer.OutseerContadorRetos;
import mx.com.vepormas.outseer.model.outseer.OutseerRetos;
import mx.com.vepormas.outseer.tmp.OTPService;
import mx.com.vepormas.outseer.util.Constantes;
import mx.com.vepormas.outseer.util.Utilerias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Objects;

/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @apiNote Servicio que contiene las distintas operaciones a mongo
 * @since 2024-16-08
 */

@Slf4j
@Service
public class OutseerService {

    @Autowired
    private OutseerMapper outseerMapper;
    @Autowired
    private OutseerClient outseerClient;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private WebClient catalogosConectorT24;
    @Autowired
    private MapperService mapperService;
    @Autowired
    OTPService otpService;
    @Value("${cliente.conectort24.uri}")
    private String uriClientT24;

    // Se usa para bitácora, se pasa como parámetro en helpers
    private Date fhRsaAAReq;

    // ===========================
    // MÉTODOS ENDPOINT PRINCIPALES
    // ===========================

    public Response<AnalyzeResponse> processAnalyze(OutseerRequest request) {
        try {
            fhRsaAAReq = new Date();
            AnalyzeRequest analyzeRequest = getAnalyzeRequest(request);
            boolean sessionEnded = false;
            int contador = 0;
            if (!analyzeRequest.getChannelIndicator().equals(Constantes.SESSION_SIGNIN)) {
                var endChallengeResult = endChallengePast(analyzeRequest);
                sessionEnded = endChallengeResult.isSessionClosed();
                contador = endChallengeResult.getContador();
            }
            log.info("¿Sesión previa cerrada? {}", sessionEnded);
            if (contador == 3) {
                log.info("Contador llegó a 3, retornando respuesta genérica y terminando flujo.");
                return buildMaxAttemptsResponse(analyzeRequest.getIdentificationData().getUserName());
            }

            AnalyzeResponse analyzeResponse = getAnalyzeResponse(analyzeRequest);
            Date fhRes = new Date();
            int code = getActionCode(analyzeResponse);
            OutseerResponse response = mapperService.toOutseerResponseDenyAllow(analyzeResponse, code);
            saveBitacorasResponse(analyzeRequest, analyzeResponse, fhRes, response);
            return new Response<>(HttpStatus.OK + " " + Constantes.OP_EXITOSA, analyzeResponse);

        } catch (Exception e) {
            log.error("Error interno en processAnalyze: {}", e.getMessage(), e);
            return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR + e.getMessage(), null);
        }
    }

    public Response<OutseerUserResponse> processCreateUser(OutseerRequest request) {
        AnalyzeRequest analyzeRequest;
        CreateUserResponse createUserResponse = null;
        Cache cache = cacheManager.getCache(Constantes.CACHE_NOMBRE_ANALYZE_RERQUEST);
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(request.getRequest().getChannelIndicator());
            if (valueWrapper != null) {
                analyzeRequest = (AnalyzeRequest) valueWrapper.get();
                if (analyzeRequest != null) {
                    try {
                        createUserResponse = outseerClient.createUser(mapperService.createUserRequest(analyzeRequest));
                    } catch (RemoteException e) {
                        throw new OutseerException("Error al crear usuario: " + e.getMessage());
                    }
                }
            }
        }
        if (createUserResponse == null) {
            throw new OutseerException("No se pudo crear el usuario, AnalyzeRequest o CreateUserResponse es nulo");
        }
        return new Response<>(HttpStatus.OK + " " + Constantes.OP_EXITOSA, mapperService.createUserResponse(createUserResponse));
    }

    public Response<OutseerResponse> processChallengeOTP(OutseerRequest request, int typeCha, String principal) {
        AnalyzeRequest analyzeRequest = getAnalyzeRequest(request);
        AnalyzeResponse analyzeResponse = getCachedAnalyzeResponse(analyzeRequest, typeCha);
        String otpExt = otpService.sendOTP(principal).getData();
        int retoCount = repositoryService.saveContador(mapperService.countRetos(analyzeResponse.getIdentificationData().getUserName(), 0));
        logSessionAndTransaction(analyzeResponse);
        analyzeRequest.setIdentificationData(analyzeResponse.getIdentificationData());
        analyzeRequest.getDeviceRequest().setDeviceTokenCookie(analyzeResponse.getDeviceResult().getDeviceData().getDeviceTokenCookie());
        log.info("Este es el Token del Analyze {}.", analyzeRequest.getDeviceRequest().getDeviceTokenCookie());
        try {
            ChallengeResponse challengeResponse = outseerClient.challenge(mapperService.challengeRequest(analyzeRequest));
            handleChallengeResponse(challengeResponse, analyzeRequest);
            OTPChallengeResponse otpChallengeResponse = (OTPChallengeResponse) challengeResponse
                    .getCredentialChallengeList().getAcspChallengeResponseData().getPayload();

            log.info("Este es el Token del Challenge {}.", challengeResponse.getDeviceResult().getDeviceData().getDeviceTokenCookie());
            logChallengeDetails(challengeResponse, otpChallengeResponse);

            repositoryService.saveCtaRepository(mapperService.outseerIntegracion(analyzeRequest, otpChallengeResponse.getOtp(),otpExt, challengeResponse, analyzeResponse));
            log.info("integration account OTP : {}", otpChallengeResponse.getOtp());

            return createChallengeResponse(otpChallengeResponse, challengeResponse, analyzeResponse, analyzeRequest, retoCount);
        } catch (RemoteException e) {
            throw new OutseerException("Error en processChallengeOTP: " + e.getMessage());
        }
    }

    public Response<NotifyOutseerResponse> processNotify(OutseerRequest request, String otp) {
        int count = 0;
        NotifyResponse notifyResponse = null;
        try {
            AnalyzeRequest analyzeRequest = outseerMapper.toAnalyzeRequest(request);
            String userName = analyzeRequest.getIdentificationData().getUserName();
            String sessionId = analyzeRequest.getIdentificationData().getSessionId();

            // Validación de OTP antes de cualquier otra lógica
            if (!repositoryService.isOtpValid(sessionId, otp)) {
                log.info("OTP | SESSION IS NOT FOUND");
                if (!request.getRequest().getEventDataList().getEventData().get(0).getAuthenticationLevel().getAttemptsTryCount().equals(3)) {
                    count = repositoryService.saveContador(mapperService.countRetos(userName, 0));
                }
                repositoryService.updateIntegrationRecord(analyzeRequest);
                return buildNotifyResponse(false, Constantes.ENDPOINT_CODE_OTP_ERROR, Constantes.BAD_OTP, count, null);
            }
            count = repositoryService.saveContador(mapperService.countRetos(userName, 0));
            boolean isAuthSuccessful = Boolean.TRUE.equals(analyzeRequest.getEventDataList()[0].getAuthenticationLevel().getSuccessful());
            if (!isAuthSuccessful) {
                repositoryService.updateIntegrationRecord(analyzeRequest);
                log.info("SUCCESSFUL IS NOT TRUE");
                return buildNotifyResponse(false, Constantes.ENDPOINT_CODE_OTP_ERROR, Constantes.BAD_OTP, count, null);
            }

            notifyResponse = outseerClient.notify(mapperService.notifyRequest(analyzeRequest));

            int statusCode = notifyResponse.getStatusHeader().getStatusCode();
            String reasonDesc = notifyResponse.getStatusHeader().getReasonDescription();

            if (statusCode != Constantes.SUCCESS) {
                log.info("No puedo continuar porque el status es: {}", statusCode);
                repositoryService.updateIntegrationRecord(analyzeRequest);
                return buildNotifyResponse(false, statusCode, reasonDesc, count, notifyResponse.getDeviceResult().getDeviceData().getDeviceTokenCookie());
            }

            if (isExtraAuthSuccessful(analyzeRequest, notifyResponse)) {
                repositoryService.updateIntegrationRecord(analyzeRequest);
                log.info("Autenticación exitosa: {}", getAuthentication(analyzeRequest, otp, notifyResponse).getMessage());
                repositoryService.deleteIntegrationRecordByOTP(userName);
                return buildNotifyResponse(true, Constantes.SUCCESS, Constantes.NOTIFY_SUCCESS, 0, notifyResponse.getDeviceResult().getDeviceData().getDeviceTokenCookie());
            } else {
                log.info("Error en isExtraAuthSuccessful. Status: {}", statusCode);
                return buildNotifyResponse(false, statusCode, reasonDesc, count, notifyResponse.getDeviceResult().getDeviceData().getDeviceTokenCookie());
            }
        } catch (Exception e) {
            log.error("Error en processNotify: {}", e.getMessage(), e);
            assert notifyResponse != null;
            NotifyOutseerResponse notifyOutseerResponse = new NotifyOutseerResponse(false, Constantes.ENDPOINT_CODE_FAIL, e.getMessage(), count, notifyResponse.getDeviceResult().getDeviceData().getDeviceTokenCookie());
            return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR + " " + Constantes.ERROR_MSG_WRONG_REQUEST, notifyOutseerResponse);
        }
    }

    public Response<ResponseBlockedUser> processBlockedUser(String userName, String otp, String sessionID, String transactionID, String orgName,String deviceToken) {
        ResponseBlockedUser blockedUser;
        String messageResponse;
        try {
            AuthenticateResponse authenticateResponse = outseerClient.authenticate(mapperService.authenticateRequest(null, otp,
                    userName,
                    sessionID,
                    transactionID,
                    orgName));
            boolean isBlocked = authenticateResponse.getStatusHeader().getStatusCode() == Constantes.SUCCESS;
            blockedUser = mapperService.blockedUser(isBlocked, deviceToken, authenticateResponse);
            if (isBlocked) {
                repositoryService.deleteIntegrationRecordByOTP(userName);
            }
            messageResponse = Utilerias.getMessageResponse(isBlocked);
        } catch (RemoteException e) {
            throw new OutseerException("Error en getAuthentication: " + e.getMessage());
        }
        return new Response<>(HttpStatus.OK +" "+ messageResponse, blockedUser);
    }

    public Response<NotifyOutseerResponse> processValidOTP(OTPRequest otpRequest) {
        int count = 0;

        if (!repositoryService.isOtpValid(otpRequest.getSession(), otpRequest.getOtp())) {
            count = repositoryService.saveContador(mapperService.countRetos(otpRequest.getUserName(), 0));
            return buildNotifyResponse(false, Constantes.ENDPOINT_CODE_OTP_ERROR, Constantes.BAD_OTP, count, null);
        }
        return buildNotifyResponse(true, Constantes.SUCCESS, Constantes.SUSSES_OTP, count, null);
    }

    public Response<String> processRefreshOTP(String principal, String sessionId) {
        String otp = otpService.sendOTP(principal).getData();
        String msg = Constantes.ERROR_SEND_OTP;
        if (!otp.isEmpty()) {
            msg = Constantes.SEND_OTP;
            repositoryService.updateOtpExternoBySessionId(sessionId, otp);
        }
        return new Response<>(HttpStatus.OK + " " + Constantes.OP_EXITOSA, msg);
    }
    public Response<OutseerResponse> getAuthentication(AnalyzeRequest analyzeRequest, String otp, NotifyResponse notifyResponse) {
        try {
            AuthenticateResponse authenticateResponse = outseerClient.authenticate(mapperService.authenticateRequest(analyzeRequest, otp,
                    analyzeRequest.getIdentificationData().getUserName(),
                    analyzeRequest.getIdentificationData().getSessionId(),
                    analyzeRequest.getIdentificationData().getTransactionId(),
                    analyzeRequest.getIdentificationData().getOrgName()));
            return handleAuthenticationResponse(authenticateResponse, notifyResponse);
        } catch (RemoteException e) {
            throw new OutseerException("Error en getAuthentication: " + e.getMessage());
        }
    }

    public Response<String> processIntegrateDate(String eventType, String userName, String accountNumber) {
        String responseT24Ofs;
        String dateT24 = "";
        try {
            String event = repositoryService.findTypeOperation(eventType);
            if (!event.equals(Constantes.NA)) {
                responseT24Ofs = getDateIntegration(event, userName, accountNumber).getData();
                if ((responseT24Ofs != null) && (!responseT24Ofs.isEmpty())) {
                    dateT24 = responseT24Ofs.substring(0, 4) + "-" + responseT24Ofs.substring(4, 6) + "-" + responseT24Ofs.substring(6, 8);
                }
            }
        } catch (Exception e) {
            throw new OutseerException("Error en processIntegrateDate: " + e.getMessage());
        }
        return new Response<>(HttpStatus.OK + " " + Constantes.OP_EXITOSA, dateT24);
    }

    @Cacheable("accountDateT24")
    public Response<String> getDateIntegration(String eventType, String customerId, String accountNumber) {
        RequestT24 requestT24 = RequestT24.builder()
                .tipo(Constantes.TIPO_OFS)
                .ofsT24(T24Mapper.ofsDateAccount(eventType, customerId, accountNumber))
                .build();
        log.info("RequestT24: {}", requestT24);
        try {
            var responseT24 = catalogosConectorT24.post()
                    .uri(uriClientT24)
                    .body(Mono.just(requestT24), DataT24.class)
                    .retrieve().bodyToMono(ResponseT24.class)
                    .block();
            log.info("ResponseT24: {}", responseT24);
            if (responseT24 != null) {
                return outseerMapper.responseT24ToApiResponse(responseT24);
            } else {
                return new Response<>(Constantes.ERROR_CONECTOR_T24, null);
            }
        } catch (Exception ex) {
            log.error("..:: getAccountDateOutseer " + Constantes.ERROR_CONECTOR_T24 + "{} ::..", ex.getMessage());
            return new Response<>(Constantes.ERROR_CONECTOR_T24, "");
        }
    }

    public Response<String> getBlockUserIntegration(String idCore) {
        RequestT24 requestT24 = RequestT24.builder()
                .tipo(Constantes.TIPO_OFS)
                .ofsT24(T24Mapper.ofsBlockUserAccount(idCore))
                .build();
        log.info("RequestT24 (bloqueo usuario): {}", requestT24);
        try {
            var responseT24 = catalogosConectorT24.post()
                    .uri(uriClientT24)
                    .body(Mono.just(requestT24), DataT24.class)
                    .retrieve().bodyToMono(ResponseT24.class)
                    .block();
            log.info("ResponseT24 (bloqueo usuario): {}", responseT24);
            if (responseT24 != null) {
                return outseerMapper.responseT24ToApiResponse(responseT24);
            } else {
                return new Response<>(Constantes.ERROR_CONECTOR_T24, null);
            }
        } catch (Exception ex) {
            log.error("..:: getBlockUser " + Constantes.ERROR_CONECTOR_T24 + "{} ::..", ex.getMessage());
            return new Response<>(Constantes.ERROR_CONECTOR_T24, "");
        }
    }

    // ===========================
    // MÉTODOS AUXILIARES / PRIVADOS
    // ===========================

    @Cacheable(value = Constantes.CACHE_NOMBRE_ANALYZE_RERQUEST, key = "request.request.channelIndicator")
    public AnalyzeRequest getAnalyzeRequest(OutseerRequest request) {
        return outseerMapper.toAnalyzeRequest(request);
    }

    @Cacheable(value = Constantes.CACHE_NOMBRE_ANALYZE_RESPONSE, key = "analyzeRequest.runRiskType.value")
    public AnalyzeResponse getAnalyzeResponse(AnalyzeRequest analyzeRequest) {
        try {
            return outseerClient.analyze(analyzeRequest);
        } catch (RemoteException e) {
            throw new OutseerException("Error al obtener AnalyzeResponse: " + e.getMessage());
        }
    }

    private AnalyzeResponse getCachedAnalyzeResponse(AnalyzeRequest analyzeRequest, int typeCha) {
        Cache cache = cacheManager.getCache(Constantes.CACHE_NOMBRE_ANALYZE_RESPONSE);
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(analyzeRequest.getRunRiskType().getValue());
            if (valueWrapper != null) {
                AnalyzeResponse analyzeResponse = (AnalyzeResponse) valueWrapper.get();
                if (analyzeResponse != null && typeCha == 0) {
                    try {
                        return outseerClient.analyze(analyzeRequest);
                    } catch (RemoteException e) {
                        throw new OutseerException("Error al analizar request: " + e.getMessage());
                    }
                }
                return analyzeResponse;
            }
        }
        throw new OutseerException("No se encontró AnalyzeResponse en caché");
    }

    private void logSessionAndTransaction(AnalyzeResponse analyzeResponse) {
        log.info("SessionID: {}", analyzeResponse.getIdentificationData().getSessionId());
        log.info("TransactionId: {}", analyzeResponse.getIdentificationData().getTransactionId());
    }

    private void handleChallengeResponse(ChallengeResponse challengeResponse, AnalyzeRequest analyzeRequest) throws RemoteException {
        if (challengeResponse.getStatusHeader().getStatusCode() != Constantes.SUCCESS) {
            log.info("El status es {}, se actualiza el usuario {}", challengeResponse.getStatusHeader().getStatusCode(), analyzeRequest.getIdentificationData().getUserName());
            UpdateUserResponse updateUserResponse = outseerClient.updateUser(mapperService.updateUserRequest(analyzeRequest.getIdentificationData().getUserName()));
            log.info("Se Actualizó el usuario: {}", updateUserResponse.getStatusHeader().getReasonDescription());
        }
    }

    private void logChallengeDetails(ChallengeResponse challengeResponse, OTPChallengeResponse otpChallengeResponse) {
        log.info("challengeResponse detalle: {}", challengeResponse.getCredentialChallengeList()
                .getAcspChallengeResponseData().getCallStatus().getStatusDescription());
        log.info("OTP presente: {}", otpChallengeResponse.getOtp() != null);
        log.info("SessionId detalle: {}", challengeResponse.getIdentificationData().getSessionId());
        log.info("TransaccionId detalle: {}", challengeResponse.getIdentificationData().getTransactionId());
    }

    private Response<OutseerResponse> createChallengeResponse(OTPChallengeResponse otpChallengeResponse, ChallengeResponse challengeResponse, AnalyzeResponse analyzeResponseUser, AnalyzeRequest analyzeRequest, int countRetos) {
        Date fhRsaAAResp = new Date();
        OutseerResponse outseerResponse = mapperService.outseerResponseChallenge(analyzeResponseUser, challengeResponse, otpChallengeResponse.getOtp(), countRetos);
        repositoryService.saveBitacora(mapperService.bitacora(fhRsaAAReq, fhRsaAAResp, analyzeRequest, analyzeResponseUser, outseerResponse));
        log.info("DATA SAVED IN LOG: {}", challengeResponse.getIdentificationData().getTransactionId());
        return new Response<>(HttpStatus.OK + " " + Constantes.OP_EXITOSA, outseerResponse);
    }

    private Response<NotifyOutseerResponse> buildNotifyResponse(boolean status, int code, String message, int count, String token) {
        NotifyOutseerResponse response = new NotifyOutseerResponse(status, code, message, count, token);
        return new Response<>(HttpStatus.OK + " " + Constantes.OP_EXITOSA, response);
    }

    private boolean isExtraAuthSuccessful(AnalyzeRequest analyzeRequest, NotifyResponse notifyResponse) {
        return analyzeRequest.getEventDataList()[0].getEventType().equals(EventType.EXTRA_AUTH)
                && notifyResponse.getStatusHeader().getStatusCode() == Constantes.SUCCESS
                && Boolean.TRUE.equals(analyzeRequest.getEventDataList()[0].getAuthenticationLevel().getSuccessful());
    }

    private Response<OutseerResponse> handleAuthenticationResponse(AuthenticateResponse authenticateResponse, NotifyResponse notifyResponse) {
        int statusCode = authenticateResponse.getStatusHeader().getStatusCode();
        log.info("Status code de autenticación: {}", statusCode);
        if (statusCode == Constantes.SUCCESS) {
            return handleSuccessfulAuthentication(authenticateResponse, notifyResponse);
        } else if (statusCode == 300) {
            return createErrorResponse(notifyResponse, Constantes.FAIL, Constantes.ERROR_MSG_WRONG_REQUEST);
        } else {
            return createErrorResponse(notifyResponse, authenticateResponse.getStatusHeader().getReasonCode().toString(), authenticateResponse.getStatusHeader().getReasonDescription());
        }
    }

    private Response<OutseerResponse> handleSuccessfulAuthentication(AuthenticateResponse authenticateResponse, NotifyResponse notifyResponse) {
        if (isUserCreationRequired(authenticateResponse)) {
            log.info("Se tiene que crear el usuario.");
            return createErrorResponse(notifyResponse, Constantes.FAIL, "User creation required");
        }

        var authResponseData = authenticateResponse.getCredentialAuthResultList().getAcspAuthenticationResponseData();
        if (authResponseData != null) {
            var callStatus = authResponseData.getCallStatus();
            String statusCode = callStatus.getStatusCode();
            String statusDescription = callStatus.getStatusDescription();
            if (!statusCode.equals(String.valueOf(Constantes.SUCCESS))) {
                return createErrorResponse(notifyResponse, Constantes.BAD_OTP, statusCode + "-" + statusDescription);
            } else {
                return createSuccessResponse(notifyResponse, statusDescription);
            }
        }
        log.warn("Authentication data missing en handleSuccessfulAuthentication");
        return createErrorResponse(notifyResponse, Constantes.FAIL, "Authentication data missing");
    }

    private boolean isUserCreationRequired(AuthenticateResponse authenticateResponse) {
        return !Objects.equals(authenticateResponse.getIdentificationData().getUserType().getValue(), WSUserType._PERSISTENT)
                || !Objects.equals(authenticateResponse.getIdentificationData().getUserStatus().getValue(), UserStatus._VERIFIED);
    }

    private Response<OutseerResponse> createErrorResponse(NotifyResponse notifyResponse, String message, String description) {
        OutseerResponse outseerResponse = mapperService.authenticateResponse(notifyResponse, false, Constantes.ENDPOINT_CODE_REQUEST_BAD, message, description);
        return new Response<>(HttpStatus.OK + " " + Constantes.OP_EXITOSA, outseerResponse);
    }

    private Response<OutseerResponse> createSuccessResponse(NotifyResponse notifyResponse, String statusDescription) {
        OutseerResponse outseerResponse = mapperService.authenticateResponse(notifyResponse, true, Constantes.SUCCESS, statusDescription, ActionCode._ALLOW);
        return new Response<>(HttpStatus.OK + " " + Constantes.OP_EXITOSA, outseerResponse);
    }

    public void saveBitacorasResponse(AnalyzeRequest request, AnalyzeResponse analyzeResponse, Date endOutseer, OutseerResponse outseerResponse) {
        ObjectMapper mapper = new ObjectMapper();
        String req;
        String res;
        try {
            req = mapper.writeValueAsString(request);
            res = mapper.writeValueAsString(analyzeResponse);
        } catch (JsonProcessingException e) {
            throw new OutseerException("Error serializando bitácora: " + e.getMessage());
        }
        log.info("Guardando bitácora inicial del response y request");
        repositoryService.saveOutseerResponse(mapperService.saveOutseerResponse(req, res));
        repositoryService.saveBitacora(mapperService.bitacora(fhRsaAAReq, endOutseer, request, analyzeResponse, outseerResponse));
    }

    public EndChallengeResult endChallengePast(AnalyzeRequest request) {
        OutseerRetos outseerRetos;
        EndChallengeResult endChallengeResult = new EndChallengeResult();
        int contador;
        boolean session = false;
        log.info("Usuario: {}", request.getIdentificationData().getUserName());
        OutseerContadorRetos existsInOutseerContadorRetos = repositoryService.existsInContadorRetos(request.getIdentificationData().getUserName());
        if (existsInOutseerContadorRetos != null) {
            contador = existsInOutseerContadorRetos.getCount();
            log.info("Usuario existe en contador de retos: {}, contador: {}", existsInOutseerContadorRetos.getUserName(), contador);

            outseerRetos = repositoryService.findUltimoOutseerRetoSinSuccessful(request.getIdentificationData().getUserName());
            if (outseerRetos != null && !outseerRetos.getCustomerId().isBlank()) {
                log.info("Último reto sin successful para {}: {}", request.getIdentificationData().getUserName(), outseerRetos.getId());
                try {
                    AuthenticateResponse authenticateResponse = outseerClient.authenticate(
                            mapperService.authenticateRequest(request, outseerRetos.getOtpInterno(),
                                    outseerRetos.getCustomerId(), outseerRetos.getSessionId(),
                                    outseerRetos.getTransactionId(),
                                    request.getIdentificationData().getOrgName()));
                    if (authenticateResponse.getStatusHeader().getStatusCode() == Constantes.SUCCESS) {
                        log.info("Session cerrada correctamente para el usuario: {}", outseerRetos.getCustomerId());
                        session = true;
                        endChallengeResult.setSessionClosed(session);
                        endChallengeResult.setContador(contador);
                    } else {
                        log.error("Session NO cerrada: {}", authenticateResponse.getStatusHeader().getReasonDescription());
                    }
                } catch (RemoteException e) {
                    throw new OutseerException("Error cerrando sesión previa: " + e.getMessage());
                }
            }

            endChallengeResult.setSessionClosed(session);
            endChallengeResult.setContador(contador);
        }
        return endChallengeResult;
    }

    private Response<AnalyzeResponse> buildMaxAttemptsResponse(String userName) {

        String message = "";
        var usuario = repositoryService.findUserByNumeroCliente(userName);
        String responseT24 = getBlockUserIntegration(usuario.get().getIdCore()).getData();
        if (responseT24.contains("//1")) {
            message = HttpStatus.LOCKED + " Usuario Bloqueado";
            repositoryService.deleteIntegrationRecordByOTP(userName);
        } else if (responseT24.contains("//-1/")) {
            message = HttpStatus.CONFLICT + " Usuario No Bloqueado";
        }
        return new Response<>(message, null);
    }

    private int getActionCode(AnalyzeResponse analyzeResponse) {
        String actionCode = analyzeResponse.getRiskResult().getTriggeredRule().getActionCode().getValue();
        if (actionCode.equals(Constantes.DENY)) {
            return Constantes.ENDPOINT_CODE_USER_DENY;
        } else if (actionCode.equals(Constantes.ALLOW)) {
            return Constantes.SUCCESS;
        }
        return 0;
    }
}