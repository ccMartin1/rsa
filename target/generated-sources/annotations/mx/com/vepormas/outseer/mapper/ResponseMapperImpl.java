package mx.com.vepormas.outseer.mapper;

import com.rsa.csd.ws.APIType;
import com.rsa.csd.ws.ActionCode;
import com.rsa.csd.ws.AnalyzeResponse;
import com.rsa.csd.ws.AuthenticateResponse;
import com.rsa.csd.ws.AuthenticationResult;
import com.rsa.csd.ws.BindingType;
import com.rsa.csd.ws.CallStatus;
import com.rsa.csd.ws.ChallengeResponse;
import com.rsa.csd.ws.CreateUserResponse;
import com.rsa.csd.ws.DeviceData;
import com.rsa.csd.ws.DeviceResult;
import com.rsa.csd.ws.IdentificationData;
import com.rsa.csd.ws.MessageHeader;
import com.rsa.csd.ws.MessageVersion;
import com.rsa.csd.ws.NotifyResponse;
import com.rsa.csd.ws.RequestType;
import com.rsa.csd.ws.RiskResult;
import com.rsa.csd.ws.StatusHeader;
import com.rsa.csd.ws.TriggeredRule;
import com.rsa.csd.ws.UserStatus;
import com.rsa.csd.ws.WSUserType;
import javax.annotation.processing.Generated;
import mx.com.vepormas.outseer.controller.pojo.OutseerResponse;
import mx.com.vepormas.outseer.controller.pojo.OutseerUserResponse;
import mx.com.vepormas.outseer.controller.pojo.ResponseBlockedUser;
import mx.com.vepormas.outseer.model.outseer.OutseerContadorRetos;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-16T13:18:11-0600",
    comments = "version: 1.6.0, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class ResponseMapperImpl implements ResponseMapper {

    @Override
    public OutseerUserResponse toCreateUserResponse(CreateUserResponse response) {
        if ( response == null ) {
            return null;
        }

        OutseerUserResponse outseerUserResponse = new OutseerUserResponse();

        outseerUserResponse.setStatusAuthCode( responseDeviceResultAuthenticationResultAuthStatusCode( response ) );
        outseerUserResponse.setRisk( responseDeviceResultAuthenticationResultRisk( response ) );
        outseerUserResponse.setStatusCode( responseDeviceResultCallStatusStatusCode( response ) );
        outseerUserResponse.setBindingType( responseDeviceResultDeviceDataBindingTypeValue( response ) );
        outseerUserResponse.setDeviceTokenCookie1( responseDeviceResultDeviceDataDeviceTokenCookie( response ) );
        outseerUserResponse.setDeviceTokenFSO1( responseDeviceResultDeviceDataDeviceTokenFSO( response ) );
        Boolean delegated = responseIdentificationDataDelegated( response );
        if ( delegated != null ) {
            outseerUserResponse.setDelegated( String.valueOf( delegated ) );
        }
        outseerUserResponse.setTransactionId( responseIdentificationDataTransactionId( response ) );
        outseerUserResponse.setUsername( responseIdentificationDataUserName( response ) );
        outseerUserResponse.setUserStatus( responseIdentificationDataUserStatusValue( response ) );
        outseerUserResponse.setUserType( responseIdentificationDataUserTypeValue( response ) );
        outseerUserResponse.setApyType( responseMessageHeaderApiTypeValue( response ) );
        outseerUserResponse.setRequestType( responseMessageHeaderRequestTypeValue( response ) );
        outseerUserResponse.setTimeStamp( responseMessageHeaderTimeStamp( response ) );
        outseerUserResponse.setVersion( responseMessageHeaderVersionValue( response ) );
        outseerUserResponse.setReasonCode( responseStatusHeaderReasonCode( response ) );
        outseerUserResponse.setReasonDescription( responseStatusHeaderReasonDescription( response ) );
        outseerUserResponse.setStatusCodeHeader( responseStatusHeaderStatusCode( response ) );

        return outseerUserResponse;
    }

    @Override
    public OutseerResponse toOutseerResponseAuthenticate(NotifyResponse dto, boolean status, int code, String message, String rsaMessage, String actionType) {
        if ( dto == null && message == null && rsaMessage == null && actionType == null ) {
            return null;
        }

        OutseerResponse outseerResponse = new OutseerResponse();

        if ( dto != null ) {
            outseerResponse.setSessionId( dtoIdentificationDataSessionId( dto ) );
            outseerResponse.setTransactionId( dtoIdentificationDataTransactionId( dto ) );
            outseerResponse.setDeviceTokenCookie( dtoDeviceResultDeviceDataDeviceTokenCookie( dto ) );
        }
        outseerResponse.setStatus( status );
        outseerResponse.setCode( code );
        outseerResponse.setMessage( message );
        outseerResponse.setRsaMessage( rsaMessage );
        outseerResponse.setActionType( actionType );

        return outseerResponse;
    }

    @Override
    public OutseerResponse toOutseerResponseChallenge(AnalyzeResponse analyzeResponse, ChallengeResponse challengeResponse, String challenge, int endpointCodeChallenge, boolean status, String message, String actionType, String otp, int retos) {
        if ( analyzeResponse == null && challengeResponse == null && challenge == null && message == null && actionType == null && otp == null ) {
            return null;
        }

        OutseerResponse outseerResponse = new OutseerResponse();

        if ( analyzeResponse != null ) {
            outseerResponse.setRiskScore( analyzeResponseRiskResultRiskScore( analyzeResponse ) );
            outseerResponse.setRuleId( analyzeResponseRiskResultTriggeredRuleRuleId( analyzeResponse ) );
        }
        if ( challengeResponse != null ) {
            outseerResponse.setSessionId( challengeResponseIdentificationDataSessionId( challengeResponse ) );
            outseerResponse.setTransactionId( challengeResponseIdentificationDataTransactionId( challengeResponse ) );
            outseerResponse.setDeviceTokenCookie( challengeResponseDeviceResultDeviceDataDeviceTokenCookie( challengeResponse ) );
        }
        outseerResponse.setRsaMessage( challenge );
        outseerResponse.setCode( endpointCodeChallenge );
        outseerResponse.setStatus( status );
        outseerResponse.setMessage( message );
        outseerResponse.setActionType( actionType );
        outseerResponse.setOtp( otp );
        outseerResponse.setCountChallenge( retos );

        return outseerResponse;
    }

    @Override
    public OutseerResponse toResponseAnalyze(AnalyzeResponse analyzeResponse, int code) {
        if ( analyzeResponse == null ) {
            return null;
        }

        OutseerResponse outseerResponse = new OutseerResponse();

        if ( analyzeResponse != null ) {
            outseerResponse.setSessionId( analyzeResponseIdentificationDataSessionId( analyzeResponse ) );
            outseerResponse.setTransactionId( analyzeResponseIdentificationDataTransactionId( analyzeResponse ) );
            outseerResponse.setDeviceTokenCookie( analyzeResponseDeviceResultDeviceDataDeviceTokenCookie( analyzeResponse ) );
            outseerResponse.setRsaMessage( analyzeResponseRiskResultTriggeredRuleActionCodeValue( analyzeResponse ) );
            outseerResponse.setRiskScore( analyzeResponseRiskResultRiskScore( analyzeResponse ) );
            outseerResponse.setRuleId( analyzeResponseRiskResultTriggeredRuleRuleId( analyzeResponse ) );
            outseerResponse.setMessage( analyzeResponseStatusHeaderReasonDescription( analyzeResponse ) );
        }
        outseerResponse.setCode( code );

        return outseerResponse;
    }

    @Override
    public OutseerContadorRetos toCountRetos(String response, int count) {
        if ( response == null ) {
            return null;
        }

        OutseerContadorRetos outseerContadorRetos = new OutseerContadorRetos();

        outseerContadorRetos.setUserName( response );
        outseerContadorRetos.setCount( count );

        return outseerContadorRetos;
    }

    @Override
    public ResponseBlockedUser toBlockedUser(boolean isBlocked, String deviceToken, AuthenticateResponse authenticateResponse) {
        if ( deviceToken == null && authenticateResponse == null ) {
            return null;
        }

        ResponseBlockedUser.ResponseBlockedUserBuilder responseBlockedUser = ResponseBlockedUser.builder();

        responseBlockedUser.deviceTokenCookie( deviceToken );
        responseBlockedUser.code( getCode(isBlocked, authenticateResponse) );
        responseBlockedUser.message( getMessage(isBlocked, authenticateResponse) );
        responseBlockedUser.status( isBlocked );
        responseBlockedUser.countChallenge( isBlocked ? 0 : null );

        return responseBlockedUser.build();
    }

    private String responseDeviceResultAuthenticationResultAuthStatusCode(CreateUserResponse createUserResponse) {
        DeviceResult deviceResult = createUserResponse.getDeviceResult();
        if ( deviceResult == null ) {
            return null;
        }
        AuthenticationResult authenticationResult = deviceResult.getAuthenticationResult();
        if ( authenticationResult == null ) {
            return null;
        }
        return authenticationResult.getAuthStatusCode();
    }

    private Integer responseDeviceResultAuthenticationResultRisk(CreateUserResponse createUserResponse) {
        DeviceResult deviceResult = createUserResponse.getDeviceResult();
        if ( deviceResult == null ) {
            return null;
        }
        AuthenticationResult authenticationResult = deviceResult.getAuthenticationResult();
        if ( authenticationResult == null ) {
            return null;
        }
        return authenticationResult.getRisk();
    }

    private String responseDeviceResultCallStatusStatusCode(CreateUserResponse createUserResponse) {
        DeviceResult deviceResult = createUserResponse.getDeviceResult();
        if ( deviceResult == null ) {
            return null;
        }
        CallStatus callStatus = deviceResult.getCallStatus();
        if ( callStatus == null ) {
            return null;
        }
        return callStatus.getStatusCode();
    }

    private String responseDeviceResultDeviceDataBindingTypeValue(CreateUserResponse createUserResponse) {
        DeviceResult deviceResult = createUserResponse.getDeviceResult();
        if ( deviceResult == null ) {
            return null;
        }
        DeviceData deviceData = deviceResult.getDeviceData();
        if ( deviceData == null ) {
            return null;
        }
        BindingType bindingType = deviceData.getBindingType();
        if ( bindingType == null ) {
            return null;
        }
        return bindingType.getValue();
    }

    private String responseDeviceResultDeviceDataDeviceTokenCookie(CreateUserResponse createUserResponse) {
        DeviceResult deviceResult = createUserResponse.getDeviceResult();
        if ( deviceResult == null ) {
            return null;
        }
        DeviceData deviceData = deviceResult.getDeviceData();
        if ( deviceData == null ) {
            return null;
        }
        return deviceData.getDeviceTokenCookie();
    }

    private String responseDeviceResultDeviceDataDeviceTokenFSO(CreateUserResponse createUserResponse) {
        DeviceResult deviceResult = createUserResponse.getDeviceResult();
        if ( deviceResult == null ) {
            return null;
        }
        DeviceData deviceData = deviceResult.getDeviceData();
        if ( deviceData == null ) {
            return null;
        }
        return deviceData.getDeviceTokenFSO();
    }

    private Boolean responseIdentificationDataDelegated(CreateUserResponse createUserResponse) {
        IdentificationData identificationData = createUserResponse.getIdentificationData();
        if ( identificationData == null ) {
            return null;
        }
        return identificationData.getDelegated();
    }

    private String responseIdentificationDataTransactionId(CreateUserResponse createUserResponse) {
        IdentificationData identificationData = createUserResponse.getIdentificationData();
        if ( identificationData == null ) {
            return null;
        }
        return identificationData.getTransactionId();
    }

    private String responseIdentificationDataUserName(CreateUserResponse createUserResponse) {
        IdentificationData identificationData = createUserResponse.getIdentificationData();
        if ( identificationData == null ) {
            return null;
        }
        return identificationData.getUserName();
    }

    private String responseIdentificationDataUserStatusValue(CreateUserResponse createUserResponse) {
        IdentificationData identificationData = createUserResponse.getIdentificationData();
        if ( identificationData == null ) {
            return null;
        }
        UserStatus userStatus = identificationData.getUserStatus();
        if ( userStatus == null ) {
            return null;
        }
        return userStatus.getValue();
    }

    private String responseIdentificationDataUserTypeValue(CreateUserResponse createUserResponse) {
        IdentificationData identificationData = createUserResponse.getIdentificationData();
        if ( identificationData == null ) {
            return null;
        }
        WSUserType userType = identificationData.getUserType();
        if ( userType == null ) {
            return null;
        }
        return userType.getValue();
    }

    private String responseMessageHeaderApiTypeValue(CreateUserResponse createUserResponse) {
        MessageHeader messageHeader = createUserResponse.getMessageHeader();
        if ( messageHeader == null ) {
            return null;
        }
        APIType apiType = messageHeader.getApiType();
        if ( apiType == null ) {
            return null;
        }
        return apiType.getValue();
    }

    private String responseMessageHeaderRequestTypeValue(CreateUserResponse createUserResponse) {
        MessageHeader messageHeader = createUserResponse.getMessageHeader();
        if ( messageHeader == null ) {
            return null;
        }
        RequestType requestType = messageHeader.getRequestType();
        if ( requestType == null ) {
            return null;
        }
        return requestType.getValue();
    }

    private String responseMessageHeaderTimeStamp(CreateUserResponse createUserResponse) {
        MessageHeader messageHeader = createUserResponse.getMessageHeader();
        if ( messageHeader == null ) {
            return null;
        }
        return messageHeader.getTimeStamp();
    }

    private String responseMessageHeaderVersionValue(CreateUserResponse createUserResponse) {
        MessageHeader messageHeader = createUserResponse.getMessageHeader();
        if ( messageHeader == null ) {
            return null;
        }
        MessageVersion version = messageHeader.getVersion();
        if ( version == null ) {
            return null;
        }
        return version.getValue();
    }

    private Integer responseStatusHeaderReasonCode(CreateUserResponse createUserResponse) {
        StatusHeader statusHeader = createUserResponse.getStatusHeader();
        if ( statusHeader == null ) {
            return null;
        }
        return statusHeader.getReasonCode();
    }

    private String responseStatusHeaderReasonDescription(CreateUserResponse createUserResponse) {
        StatusHeader statusHeader = createUserResponse.getStatusHeader();
        if ( statusHeader == null ) {
            return null;
        }
        return statusHeader.getReasonDescription();
    }

    private Integer responseStatusHeaderStatusCode(CreateUserResponse createUserResponse) {
        StatusHeader statusHeader = createUserResponse.getStatusHeader();
        if ( statusHeader == null ) {
            return null;
        }
        return statusHeader.getStatusCode();
    }

    private String dtoIdentificationDataSessionId(NotifyResponse notifyResponse) {
        IdentificationData identificationData = notifyResponse.getIdentificationData();
        if ( identificationData == null ) {
            return null;
        }
        return identificationData.getSessionId();
    }

    private String dtoIdentificationDataTransactionId(NotifyResponse notifyResponse) {
        IdentificationData identificationData = notifyResponse.getIdentificationData();
        if ( identificationData == null ) {
            return null;
        }
        return identificationData.getTransactionId();
    }

    private String dtoDeviceResultDeviceDataDeviceTokenCookie(NotifyResponse notifyResponse) {
        DeviceResult deviceResult = notifyResponse.getDeviceResult();
        if ( deviceResult == null ) {
            return null;
        }
        DeviceData deviceData = deviceResult.getDeviceData();
        if ( deviceData == null ) {
            return null;
        }
        return deviceData.getDeviceTokenCookie();
    }

    private String challengeResponseIdentificationDataSessionId(ChallengeResponse challengeResponse) {
        IdentificationData identificationData = challengeResponse.getIdentificationData();
        if ( identificationData == null ) {
            return null;
        }
        return identificationData.getSessionId();
    }

    private String challengeResponseIdentificationDataTransactionId(ChallengeResponse challengeResponse) {
        IdentificationData identificationData = challengeResponse.getIdentificationData();
        if ( identificationData == null ) {
            return null;
        }
        return identificationData.getTransactionId();
    }

    private String challengeResponseDeviceResultDeviceDataDeviceTokenCookie(ChallengeResponse challengeResponse) {
        DeviceResult deviceResult = challengeResponse.getDeviceResult();
        if ( deviceResult == null ) {
            return null;
        }
        DeviceData deviceData = deviceResult.getDeviceData();
        if ( deviceData == null ) {
            return null;
        }
        return deviceData.getDeviceTokenCookie();
    }

    private Integer analyzeResponseRiskResultRiskScore(AnalyzeResponse analyzeResponse) {
        RiskResult riskResult = analyzeResponse.getRiskResult();
        if ( riskResult == null ) {
            return null;
        }
        return riskResult.getRiskScore();
    }

    private String analyzeResponseRiskResultTriggeredRuleRuleId(AnalyzeResponse analyzeResponse) {
        RiskResult riskResult = analyzeResponse.getRiskResult();
        if ( riskResult == null ) {
            return null;
        }
        TriggeredRule triggeredRule = riskResult.getTriggeredRule();
        if ( triggeredRule == null ) {
            return null;
        }
        return triggeredRule.getRuleId();
    }

    private String analyzeResponseIdentificationDataSessionId(AnalyzeResponse analyzeResponse) {
        IdentificationData identificationData = analyzeResponse.getIdentificationData();
        if ( identificationData == null ) {
            return null;
        }
        return identificationData.getSessionId();
    }

    private String analyzeResponseIdentificationDataTransactionId(AnalyzeResponse analyzeResponse) {
        IdentificationData identificationData = analyzeResponse.getIdentificationData();
        if ( identificationData == null ) {
            return null;
        }
        return identificationData.getTransactionId();
    }

    private String analyzeResponseDeviceResultDeviceDataDeviceTokenCookie(AnalyzeResponse analyzeResponse) {
        DeviceResult deviceResult = analyzeResponse.getDeviceResult();
        if ( deviceResult == null ) {
            return null;
        }
        DeviceData deviceData = deviceResult.getDeviceData();
        if ( deviceData == null ) {
            return null;
        }
        return deviceData.getDeviceTokenCookie();
    }

    private String analyzeResponseRiskResultTriggeredRuleActionCodeValue(AnalyzeResponse analyzeResponse) {
        RiskResult riskResult = analyzeResponse.getRiskResult();
        if ( riskResult == null ) {
            return null;
        }
        TriggeredRule triggeredRule = riskResult.getTriggeredRule();
        if ( triggeredRule == null ) {
            return null;
        }
        ActionCode actionCode = triggeredRule.getActionCode();
        if ( actionCode == null ) {
            return null;
        }
        return actionCode.getValue();
    }

    private String analyzeResponseStatusHeaderReasonDescription(AnalyzeResponse analyzeResponse) {
        StatusHeader statusHeader = analyzeResponse.getStatusHeader();
        if ( statusHeader == null ) {
            return null;
        }
        return statusHeader.getReasonDescription();
    }
}
