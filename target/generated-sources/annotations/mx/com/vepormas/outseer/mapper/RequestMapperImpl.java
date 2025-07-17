package mx.com.vepormas.outseer.mapper;

import com.rsa.csd.ws.AnalyzeRequest;
import com.rsa.csd.ws.AuthenticateRequest;
import com.rsa.csd.ws.ChallengeRequest;
import com.rsa.csd.ws.CreateUserRequest;
import com.rsa.csd.ws.EventData;
import com.rsa.csd.ws.NotifyRequest;
import com.rsa.csd.ws.RequestType;
import com.rsa.csd.ws.UpdateUserRequest;
import java.util.Arrays;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-16T13:18:11-0600",
    comments = "version: 1.6.0, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class RequestMapperImpl implements RequestMapper {

    @Override
    public CreateUserRequest toCreateUserRequest(AnalyzeRequest analyzeRequest, RequestType requestType) {
        if ( analyzeRequest == null && requestType == null ) {
            return null;
        }

        CreateUserRequest createUserRequest = new CreateUserRequest();

        if ( analyzeRequest != null ) {
            createUserRequest.setSecurityHeader( analyzeRequest.getSecurityHeader() );
            createUserRequest.setChannelIndicator( analyzeRequest.getChannelIndicator() );
            createUserRequest.setDeviceRequest( analyzeRequest.getDeviceRequest() );
            createUserRequest.setConfigurationHeader( analyzeRequest.getConfigurationHeader() );
            createUserRequest.setDeviceManagementRequest( analyzeRequest.getDeviceManagementRequest() );
            createUserRequest.setRunRiskType( analyzeRequest.getRunRiskType() );
            createUserRequest.setUserData( analyzeRequest.getUserData() );
            createUserRequest.setClientDefinedChannelIndicator( analyzeRequest.getClientDefinedChannelIndicator() );
        }
        createUserRequest.setActionTypeList( getGenericActionTypeList() );
        createUserRequest.setIdentificationData( getIdentificationData(analyzeRequest,null,null) );
        createUserRequest.setMessageHeader( getMessageHeader(requestType) );
        createUserRequest.setCredentialManagementRequestList( credentialManagementRequestList() );

        return createUserRequest;
    }

    @Override
    public ChallengeRequest toChallengeRequest(AnalyzeRequest analyzeRequest, RequestType requestType) {
        if ( analyzeRequest == null && requestType == null ) {
            return null;
        }

        ChallengeRequest challengeRequest = new ChallengeRequest();

        if ( analyzeRequest != null ) {
            challengeRequest.setSecurityHeader( analyzeRequest.getSecurityHeader() );
            challengeRequest.setIdentificationData( analyzeRequest.getIdentificationData() );
            challengeRequest.setChannelIndicator( analyzeRequest.getChannelIndicator() );
            challengeRequest.setActionTypeList( analyzeRequest.getActionTypeList() );
            challengeRequest.setConfigurationHeader( analyzeRequest.getConfigurationHeader() );
            challengeRequest.setDeviceRequest( analyzeRequest.getDeviceRequest() );
            challengeRequest.setDeviceManagementRequest( analyzeRequest.getDeviceManagementRequest() );
            EventData[] eventDataList = analyzeRequest.getEventDataList();
            if ( eventDataList != null ) {
                challengeRequest.setEventDataList( Arrays.copyOf( eventDataList, eventDataList.length ) );
            }
            challengeRequest.setUserData( analyzeRequest.getUserData() );
            challengeRequest.setClientDefinedChannelIndicator( analyzeRequest.getClientDefinedChannelIndicator() );
        }
        challengeRequest.setMessageHeader( getMessageHeader(requestType) );
        challengeRequest.setCredentialChallengeRequestList( credentialOTP() );

        return challengeRequest;
    }

    @Override
    public UpdateUserRequest toOutseerUpdateUser(String customer, RequestType requestType) {
        if ( customer == null && requestType == null ) {
            return null;
        }

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();

        updateUserRequest.setCredentialManagementRequestList( credentialUserUpdate() );
        updateUserRequest.setActionTypeList( getGenericActionTypeList() );
        updateUserRequest.setIdentificationData( getIdentificationData(customer,null,null,null) );
        updateUserRequest.setMessageHeader( getMessageHeader(requestType) );
        updateUserRequest.setSecurityHeader( mapSecurityHeader() );

        return updateUserRequest;
    }

    @Override
    public NotifyRequest toOutseerNotifyRequest(AnalyzeRequest analyzeRequest, RequestType requestType) {
        if ( analyzeRequest == null && requestType == null ) {
            return null;
        }

        NotifyRequest notifyRequest = new NotifyRequest();

        if ( analyzeRequest != null ) {
            notifyRequest.setDeviceRequest( analyzeRequest.getDeviceRequest() );
            notifyRequest.setChannelIndicator( analyzeRequest.getChannelIndicator() );
            EventData[] eventDataList = analyzeRequest.getEventDataList();
            if ( eventDataList != null ) {
                notifyRequest.setEventDataList( Arrays.copyOf( eventDataList, eventDataList.length ) );
            }
            notifyRequest.setActionTypeList( analyzeRequest.getActionTypeList() );
            notifyRequest.setConfigurationHeader( analyzeRequest.getConfigurationHeader() );
            notifyRequest.setAutoCreateUserFlag( analyzeRequest.getAutoCreateUserFlag() );
            notifyRequest.setDeviceManagementRequest( analyzeRequest.getDeviceManagementRequest() );
            notifyRequest.setUserData( analyzeRequest.getUserData() );
            notifyRequest.setClientDefinedChannelIndicator( analyzeRequest.getClientDefinedChannelIndicator() );
        }
        notifyRequest.setMessageHeader( getMessageHeader(requestType) );
        notifyRequest.setSecurityHeader( mapSecurityHeader() );
        notifyRequest.setIdentificationData( getIdentificationData(analyzeRequest,null,null) );

        return notifyRequest;
    }

    @Override
    public AuthenticateRequest toOutseerAuthenticate(AnalyzeRequest analyzeRequest, String otp, RequestType requestType, String userName, String sessionId, String transactionId, String orgName) {
        if ( analyzeRequest == null && otp == null && requestType == null && userName == null && sessionId == null && transactionId == null && orgName == null ) {
            return null;
        }

        AuthenticateRequest authenticateRequest = new AuthenticateRequest();

        if ( analyzeRequest != null ) {
            authenticateRequest.setChannelIndicator( analyzeRequest.getChannelIndicator() );
            authenticateRequest.setActionTypeList( analyzeRequest.getActionTypeList() );
            authenticateRequest.setConfigurationHeader( analyzeRequest.getConfigurationHeader() );
            authenticateRequest.setDeviceRequest( analyzeRequest.getDeviceRequest() );
            authenticateRequest.setDeviceManagementRequest( analyzeRequest.getDeviceManagementRequest() );
            EventData[] eventDataList = analyzeRequest.getEventDataList();
            if ( eventDataList != null ) {
                authenticateRequest.setEventDataList( Arrays.copyOf( eventDataList, eventDataList.length ) );
            }
            authenticateRequest.setUserData( analyzeRequest.getUserData() );
            authenticateRequest.setClientDefinedChannelIndicator( analyzeRequest.getClientDefinedChannelIndicator() );
        }
        authenticateRequest.setMessageHeader( getMessageHeader(requestType) );
        authenticateRequest.setSecurityHeader( mapSecurityHeader() );
        authenticateRequest.setIdentificationData( getIdentificationData(userName,sessionId,transactionId,orgName) );
        authenticateRequest.setCredentialDataList( getCredentialDataList(otp,analyzeRequest) );

        return authenticateRequest;
    }
}
