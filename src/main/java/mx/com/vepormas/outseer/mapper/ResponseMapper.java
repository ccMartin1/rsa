package mx.com.vepormas.outseer.mapper;

import com.rsa.csd.ws.AnalyzeResponse;
import com.rsa.csd.ws.AuthenticateResponse;
import com.rsa.csd.ws.ChallengeResponse;
import com.rsa.csd.ws.CreateUserResponse;
import com.rsa.csd.ws.NotifyResponse;
import mx.com.vepormas.outseer.controller.pojo.ResponseBlockedUser;
import mx.com.vepormas.outseer.controller.pojo.OutseerResponse;
import mx.com.vepormas.outseer.controller.pojo.OutseerUserResponse;
import mx.com.vepormas.outseer.model.outseer.OutseerContadorRetos;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @apiNote Interfaz que se encarga del mapeo del request de la peticiones de Outseer
 * @since 2024-16-08
 */
@Mapper(componentModel = "spring")
public interface ResponseMapper {
    @Mapping(source = "deviceResult.authenticationResult.authStatusCode", target = "statusAuthCode")
    @Mapping(source = "deviceResult.authenticationResult.risk", target = "risk")
    @Mapping(source = "deviceResult.callStatus.statusCode", target = "statusCode")
    @Mapping(source = "deviceResult.deviceData.bindingType.value", target = "bindingType")
    @Mapping(source = "deviceResult.deviceData.deviceTokenCookie", target = "deviceTokenCookie1")
    @Mapping(source = "deviceResult.deviceData.deviceTokenFSO", target = "deviceTokenFSO1")
    @Mapping(source = "identificationData.delegated", target = "delegated")
    @Mapping(source = "identificationData.transactionId", target = "transactionId")
    @Mapping(source = "identificationData.userName", target = "username")
    @Mapping(source = "identificationData.userStatus.value", target = "userStatus")
    @Mapping(source = "identificationData.userType.value", target = "userType")
    @Mapping(source = "messageHeader.apiType.value", target = "apyType")
    @Mapping(source = "messageHeader.requestType.value", target = "requestType")
    @Mapping(source = "messageHeader.timeStamp", target = "timeStamp")
    @Mapping(source = "messageHeader.version.value", target = "version")
    @Mapping(source = "statusHeader.reasonCode", target = "reasonCode")
    @Mapping(source = "statusHeader.reasonDescription", target = "reasonDescription")
    @Mapping(source = "statusHeader.statusCode", target = "statusCodeHeader")
    OutseerUserResponse toCreateUserResponse(CreateUserResponse response);

    @Mapping(source = "dto.identificationData.sessionId", target = "sessionId")
    @Mapping(source = "dto.identificationData.transactionId", target = "transactionId")
    @Mapping(source = "dto.deviceResult.deviceData.deviceTokenCookie", target = "deviceTokenCookie")
    @Mapping(source = "rsaMessage", target = "rsaMessage")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "message", target = "message")
    @Mapping(source = "actionType", target = "actionType")
    OutseerResponse toOutseerResponseAuthenticate(NotifyResponse dto, boolean status, int code, String message, String rsaMessage, String actionType);

    @Mapping(source = "challengeResponse.identificationData.sessionId", target = "sessionId")
    @Mapping(source = "challengeResponse.identificationData.transactionId", target = "transactionId")
    @Mapping(source = "challengeResponse.deviceResult.deviceData.deviceTokenCookie", target = "deviceTokenCookie")
    @Mapping(source = "challenge", target = "rsaMessage")
    @Mapping(source = "analyzeResponse.riskResult.riskScore", target = "riskScore")
    @Mapping(source = "analyzeResponse.riskResult.triggeredRule.ruleId", target = "ruleId")
    @Mapping(source = "endpointCodeChallenge", target = "code")
    @Mapping(source = "message", target = "message")
    @Mapping(source = "actionType", target = "actionType")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "otp", target = "otp")
    @Mapping(source = "retos", target = "countChallenge")
    OutseerResponse toOutseerResponseChallenge(AnalyzeResponse analyzeResponse, ChallengeResponse challengeResponse, String challenge, int endpointCodeChallenge, boolean status, String message, String actionType, String otp, int retos);

    @Mapping(source = "analyzeResponse.identificationData.sessionId", target = "sessionId")
    @Mapping(source = "analyzeResponse.identificationData.transactionId", target = "transactionId")
    @Mapping(source = "analyzeResponse.deviceResult.deviceData.deviceTokenCookie", target = "deviceTokenCookie")
    @Mapping(source = "analyzeResponse.riskResult.triggeredRule.actionCode.value", target = "rsaMessage")
    @Mapping(source = "analyzeResponse.riskResult.riskScore", target = "riskScore")
    @Mapping(source = "analyzeResponse.riskResult.triggeredRule.ruleId", target = "ruleId")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "analyzeResponse.statusHeader.reasonDescription", target = "message")
    OutseerResponse toResponseAnalyze(AnalyzeResponse analyzeResponse, int code);

    @Mapping(source = "response", target = "userName")
    @Mapping(source = "count", target = "count")
    OutseerContadorRetos toCountRetos(String response, int count);

    @Mapping(target = "code", expression = "java(getCode(isBlocked, authenticateResponse))")
    @Mapping(target = "message", expression = "java(getMessage(isBlocked, authenticateResponse))")
    @Mapping(target = "status", expression = "java(isBlocked)")
    @Mapping(target = "deviceTokenCookie", source = "deviceToken")
    @Mapping(target = "countChallenge", expression = "java(isBlocked ? 0 : null)")
    ResponseBlockedUser toBlockedUser(boolean isBlocked, String deviceToken, AuthenticateResponse authenticateResponse);
    default int getCode(boolean isBlocked, AuthenticateResponse authenticateResponse) {
        return isBlocked ? 423 : authenticateResponse.getStatusHeader().getStatusCode();
    }

    default String getMessage(boolean isBlocked, AuthenticateResponse authenticateResponse) {
        return isBlocked ? "Usuario bloqueado. " : authenticateResponse.getStatusHeader().getReasonDescription();
    }


}
