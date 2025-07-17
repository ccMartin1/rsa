package mx.com.vepormas.outseer.mapper;


import com.rsa.csd.ws.APIType;
import com.rsa.csd.ws.AcspAuthenticationRequestData;
import com.rsa.csd.ws.AcspChallengeRequestData;
import com.rsa.csd.ws.AcspManagementRequestData;
import com.rsa.csd.ws.AnalyzeRequest;
import com.rsa.csd.ws.AuthenticateRequest;
import com.rsa.csd.ws.AuthorizationMethod;
import com.rsa.csd.ws.ChallengeQuestionManagementRequest;
import com.rsa.csd.ws.ChallengeRequest;
import com.rsa.csd.ws.CreateUserRequest;
import com.rsa.csd.ws.CredentialChallengeRequestList;
import com.rsa.csd.ws.CredentialDataList;
import com.rsa.csd.ws.CredentialManagementRequestList;
import com.rsa.csd.ws.CredentialProvisioningStatus;
import com.rsa.csd.ws.GenericActionType;
import com.rsa.csd.ws.GenericActionTypeList;
import com.rsa.csd.ws.IdentificationData;
import com.rsa.csd.ws.MessageHeader;
import com.rsa.csd.ws.MessageVersion;
import com.rsa.csd.ws.NotifyRequest;
import com.rsa.csd.ws.OTPAuthenticationRequest;
import com.rsa.csd.ws.OTPChallengeRequest;
import com.rsa.csd.ws.OTPManagementRequest;
import com.rsa.csd.ws.RequestType;
import com.rsa.csd.ws.SecurityHeader;
import com.rsa.csd.ws.UpdateUserRequest;
import com.rsa.csd.ws.UserStatus;
import com.rsa.csd.ws.WSUserType;
import mx.com.vepormas.outseer.util.Constantes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @apiNote Interfaz que se encarga del mapeo del request de la peticiones de Outseer
 * @since 2024-16-08
 */
@Mapper(componentModel = "spring")
public interface RequestMapper {
    @Mapping(target = "actionTypeList", expression = "java(getGenericActionTypeList())")
    @Mapping(source = "analyzeRequest.securityHeader", target = "securityHeader")
    @Mapping(target = "identificationData", expression = "java(getIdentificationData(analyzeRequest,null,null)) ")
    @Mapping(target = "messageHeader", expression = "java(getMessageHeader(requestType))")
    @Mapping(source = "analyzeRequest.channelIndicator", target = "channelIndicator")
    @Mapping(source = "analyzeRequest.deviceRequest", target = "deviceRequest")
    @Mapping(target = "credentialManagementRequestList", expression = "java(credentialManagementRequestList())")
    CreateUserRequest toCreateUserRequest(AnalyzeRequest analyzeRequest, RequestType requestType);

    @Mapping(target = "messageHeader", expression = "java(getMessageHeader(requestType))")
    @Mapping(source = "analyzeRequest.securityHeader", target = "securityHeader")
    @Mapping(source = "analyzeRequest.identificationData", target = "identificationData")
    @Mapping(source = "analyzeRequest.channelIndicator", target = "channelIndicator")
    @Mapping(target = "credentialChallengeRequestList", expression = "java(credentialOTP())")
    ChallengeRequest toChallengeRequest(AnalyzeRequest analyzeRequest, RequestType requestType);

    @Mapping(target = "credentialManagementRequestList", expression = "java(credentialUserUpdate())")
    @Mapping(target = "actionTypeList", expression = "java(getGenericActionTypeList())")
    @Mapping(target = "identificationData", expression = "java(getIdentificationData(customer,null,null,null))")
    @Mapping(target = "messageHeader", expression = "java(getMessageHeader(requestType))")
    @Mapping(target = "securityHeader", expression = "java(mapSecurityHeader())")
    UpdateUserRequest toOutseerUpdateUser(String customer, RequestType requestType);

    @Mapping(target = "deviceRequest", source = "analyzeRequest.deviceRequest")
    @Mapping(target = "messageHeader", expression = "java(getMessageHeader(requestType))")
    @Mapping(target = "securityHeader", expression = "java(mapSecurityHeader())")
    @Mapping(target = "identificationData", expression = "java(getIdentificationData(analyzeRequest,null,null))")
    @Mapping(target = "channelIndicator", source = "analyzeRequest.channelIndicator")
    @Mapping(target = "eventDataList", source = "analyzeRequest.eventDataList")
    NotifyRequest toOutseerNotifyRequest(AnalyzeRequest analyzeRequest, RequestType requestType);

    @Mapping(target = "messageHeader", expression = "java(getMessageHeader(requestType))")
    @Mapping(target = "securityHeader", expression = "java(mapSecurityHeader())")
    @Mapping(target = "identificationData", expression = "java(getIdentificationData(userName,sessionId,transactionId,orgName))")
    @Mapping(target = "channelIndicator", source = "analyzeRequest.channelIndicator")
    @Mapping(target = "credentialDataList", expression = "java(getCredentialDataList(otp,analyzeRequest))")
    AuthenticateRequest toOutseerAuthenticate(AnalyzeRequest analyzeRequest, String otp, RequestType requestType, String userName, String sessionId, String transactionId, String orgName);

    default GenericActionTypeList getGenericActionTypeList() {
        GenericActionType[] genericActionType = new GenericActionType[1];
        genericActionType[0] = GenericActionType.SET_USER_STATUS;
        GenericActionTypeList genericActionTypeList = new GenericActionTypeList();
        genericActionTypeList.setGenericActionTypes(genericActionType);
        return genericActionTypeList;
    }

    default MessageHeader getMessageHeader(RequestType requestType) {
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setRequestType(requestType);
        messageHeader.setApiType(APIType.DIRECT_SOAP_API);
        messageHeader.setVersion(MessageVersion.value1);
        return messageHeader;
    }

    default IdentificationData getIdentificationData(AnalyzeRequest analyzeRequest, String sessionId, String transactionId) {
        IdentificationData identificationData = new IdentificationData();
        identificationData.setUserName(analyzeRequest.getIdentificationData().getUserName());
        identificationData.setUserStatus(UserStatus.VERIFIED);
        identificationData.setUserType(WSUserType.PERSISTENT);

        if (analyzeRequest.getIdentificationData().getSessionId() != null  && analyzeRequest.getIdentificationData().getTransactionId() != null) {
            identificationData.setSessionId(analyzeRequest.getIdentificationData().getSessionId());
            identificationData.setTransactionId(analyzeRequest.getIdentificationData().getTransactionId());
        } else {
            identificationData.setSessionId(sessionId);
            identificationData.setTransactionId(transactionId);
        }

        if (analyzeRequest.getIdentificationData().getOrgName() != null && !analyzeRequest.getIdentificationData().getOrgName().isEmpty()) {
            identificationData.setOrgName(analyzeRequest.getIdentificationData().getOrgName());
        }

        return identificationData;
    }

    default CredentialManagementRequestList credentialManagementRequestList() {
        CredentialManagementRequestList credentialManagementRequestList = new CredentialManagementRequestList();

        ChallengeQuestionManagementRequest challengeQuestionManagementRequest = new ChallengeQuestionManagementRequest();

        AcspManagementRequestData acspManagementRequestData = new AcspManagementRequestData();
        OTPManagementRequest payloadOTP = new OTPManagementRequest();

        acspManagementRequestData.setCredentialProvisioningStatus(CredentialProvisioningStatus.ACTIVE);
        acspManagementRequestData.setPayload(payloadOTP);
        credentialManagementRequestList.setAcspManagementRequestData(acspManagementRequestData);
        challengeQuestionManagementRequest.setCredentialProvisioningStatus(CredentialProvisioningStatus.ACTIVE);
        credentialManagementRequestList.setChallengeQuestionManagementRequest(challengeQuestionManagementRequest);

        return credentialManagementRequestList;
    }

    default CredentialChallengeRequestList credentialOTP() {
        AcspChallengeRequestData acspChallengeRequestData = new AcspChallengeRequestData();
        OTPChallengeRequest payload = new OTPChallengeRequest();
        acspChallengeRequestData.setPayload(payload);

        CredentialChallengeRequestList credentialChallengeRequestList = new CredentialChallengeRequestList();
        credentialChallengeRequestList.setAcspChallengeRequestData(acspChallengeRequestData);
        return credentialChallengeRequestList;
    }

    default CredentialManagementRequestList credentialUserUpdate() {
        CredentialManagementRequestList credentialManagementRequestList = new CredentialManagementRequestList();
        ChallengeQuestionManagementRequest challengeQuestionManagementRequest = new ChallengeQuestionManagementRequest();
        AcspManagementRequestData acspManagementRequestData = new AcspManagementRequestData();
        OTPManagementRequest payloadOTP = new OTPManagementRequest();
        acspManagementRequestData.setCredentialProvisioningStatus(CredentialProvisioningStatus.ACTIVE);
        acspManagementRequestData.setPayload(payloadOTP);
        credentialManagementRequestList.setAcspManagementRequestData(acspManagementRequestData);
        challengeQuestionManagementRequest.setCredentialProvisioningStatus(CredentialProvisioningStatus.ACTIVE);
        credentialManagementRequestList.setChallengeQuestionManagementRequest(challengeQuestionManagementRequest);

        return credentialManagementRequestList;
    }

    default IdentificationData getIdentificationData(String userName, String sessionId, String transactionId, String orgName) {

        IdentificationData identificationData = new IdentificationData();
        identificationData.setUserName(userName);
        identificationData.setUserStatus(UserStatus.VERIFIED);
        identificationData.setUserType(WSUserType.PERSISTENT);
        if (sessionId != null && transactionId != null) {
            identificationData.setSessionId(sessionId);
            identificationData.setTransactionId(transactionId);
        }
        if (orgName != null && !orgName.isEmpty()) {
            identificationData.setOrgName(orgName);
        }
        return identificationData;
    }

    default CredentialDataList getCredentialDataList(String otp, AnalyzeRequest request) {
        if (!(otp.isEmpty() || otp == null)) {
            AcspAuthenticationRequestData acspAuthenticationRequestData = new AcspAuthenticationRequestData();
            OTPAuthenticationRequest payload = new OTPAuthenticationRequest();
            payload.setOtp(otp);
            acspAuthenticationRequestData.setPayload(payload);

            CredentialDataList credentialRequestList = new CredentialDataList();
            credentialRequestList.setAcspAuthenticationRequestData(acspAuthenticationRequestData);
            return credentialRequestList;
        } else {
            return request.getCredentialDataList();
        }
    }

    default SecurityHeader mapSecurityHeader() {
        SecurityHeader securityHeader = new SecurityHeader();
        securityHeader.setCallerCredential(Constantes.PASST24);
        securityHeader.setCallerId(Constantes.USRT24);
        securityHeader.setMethod(AuthorizationMethod.PASSWORD);
        return securityHeader;
    }
}
