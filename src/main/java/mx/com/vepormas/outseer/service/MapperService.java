package mx.com.vepormas.outseer.service;


import com.rsa.csd.ws.ActionCode;
import com.rsa.csd.ws.AnalyzeRequest;
import com.rsa.csd.ws.AnalyzeResponse;
import com.rsa.csd.ws.AuthenticateRequest;
import com.rsa.csd.ws.AuthenticateResponse;
import com.rsa.csd.ws.ChallengeRequest;
import com.rsa.csd.ws.ChallengeResponse;
import com.rsa.csd.ws.CreateUserRequest;
import com.rsa.csd.ws.CreateUserResponse;
import com.rsa.csd.ws.NotifyRequest;
import com.rsa.csd.ws.NotifyResponse;
import com.rsa.csd.ws.RequestType;
import com.rsa.csd.ws.UpdateUserRequest;
import lombok.extern.slf4j.Slf4j;
import mx.com.vepormas.outseer.controller.pojo.OutseerResponse;
import mx.com.vepormas.outseer.controller.pojo.OutseerUserResponse;
import mx.com.vepormas.outseer.controller.pojo.ResponseBlockedUser;
import mx.com.vepormas.outseer.mapper.MongoMapper;
import mx.com.vepormas.outseer.mapper.RequestMapper;
import mx.com.vepormas.outseer.mapper.ResponseMapper;
import mx.com.vepormas.outseer.model.outseer.Bitacora;
import mx.com.vepormas.outseer.model.outseer.OutseerContadorRetos;
import mx.com.vepormas.outseer.model.outseer.OutseerRetos;
import mx.com.vepormas.outseer.model.outseer.OutseerResponseBitacora;
import mx.com.vepormas.outseer.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @apiNote Servicio que contiene las diferentes operaciones a mongo
 * @since 2024-16-08
 */


@Slf4j
@Service
public class MapperService {

    @Autowired
    private RequestMapper requestMapper;
    @Autowired
    private ResponseMapper responseMapper;
    @Autowired
    private MongoMapper mongoMapper;

    public CreateUserRequest createUserRequest(AnalyzeRequest request) {
        return requestMapper.toCreateUserRequest(request, RequestType.CREATEUSER);
    }

    public OutseerUserResponse createUserResponse(CreateUserResponse response) {
        return responseMapper.toCreateUserResponse(response);
    }

    public ChallengeRequest challengeRequest(AnalyzeRequest request) {
        return requestMapper.toChallengeRequest(request, RequestType.CHALLENGE);
    }

    public UpdateUserRequest updateUserRequest(String userName) {
        return requestMapper.toOutseerUpdateUser(userName, RequestType.UPDATEUSER);
    }

    public OutseerResponse outseerResponseChallenge(AnalyzeResponse response, ChallengeResponse challengeResponse, String otp, int retos) {
        return responseMapper.toOutseerResponseChallenge(response, challengeResponse,
                ActionCode._CHALLENGE,
                Constantes.ENDPOINT_CODE_CHALLENGE,
                true,
                Constantes.MSG_ACTION_CODE_CHALLENGE,
                ActionCode._CHALLENGE, otp, retos);
    }

    public NotifyRequest notifyRequest(AnalyzeRequest request) {
        return requestMapper.toOutseerNotifyRequest(request, RequestType.NOTIFY);
    }

    public AuthenticateRequest authenticateRequest(AnalyzeRequest analyzeRequest, String otp, String userName, String sessionId, String transactionId, String orgName) {
        return requestMapper.toOutseerAuthenticate(analyzeRequest, otp, RequestType.AUTHENTICATE, userName, sessionId, transactionId, orgName);
    }

    public OutseerResponse authenticateResponse(NotifyResponse notifyResponse, boolean status, int type, String message, String description) {
        return responseMapper.toOutseerResponseAuthenticate(notifyResponse, status, type, message, description, null);
    }

    public OutseerRetos outseerIntegracion(AnalyzeRequest request, String otpInterno,String otpExterno, ChallengeResponse challengeResponse, AnalyzeResponse response) {
        return mongoMapper.toOutseerIntegracion(request, otpInterno,otpExterno, challengeResponse, new Date(), "null", 0, response);
    }

    public Bitacora bitacora(Date fhRsaReq, Date fhRsaResp, AnalyzeRequest request, AnalyzeResponse responseUser, OutseerResponse response) {
        return mongoMapper.toSaveBitacora(new Date(), fhRsaReq, fhRsaResp, request, responseUser, response);
    }

    public OutseerResponseBitacora saveOutseerResponse(String request, String response) {
        boolean success = response != null;
        String errorMessage = success ? null : "Error en analyze response";
        return mongoMapper.toSaveResponseBitacora(
                new Date(),
                request,
                response,
                success,
                errorMessage
        );
    }

    public OutseerResponse toOutseerResponseDenyAllow(AnalyzeResponse analyzeResponse, int code) {
        return responseMapper.toResponseAnalyze(analyzeResponse, code);
    }

    public OutseerContadorRetos countRetos(String response, int count) {
        return responseMapper.toCountRetos(response, count);
    }

    public ResponseBlockedUser blockedUser(boolean isBlocked, String deviceToken, AuthenticateResponse authenticateResponse) {
        return responseMapper.toBlockedUser(
                isBlocked,
                deviceToken,
                authenticateResponse
        );
    }

}
