package mx.com.vepormas.outseer.mapper;

import com.rsa.csd.ws.AccountData;
import com.rsa.csd.ws.Amount;
import com.rsa.csd.ws.AnalyzeRequest;
import com.rsa.csd.ws.AuthenticationLevel;
import com.rsa.csd.ws.DeviceRequest;
import com.rsa.csd.ws.EventData;
import com.rsa.csd.ws.IdentificationData;
import com.rsa.csd.ws.TransactionData;
import java.util.List;
import javax.annotation.processing.Generated;
import mx.com.vepormas.outseer.controller.pojo.OutseerRequest;
import mx.com.vepormas.outseer.controller.pojo.Response;
import mx.com.vepormas.outseer.controller.pojo.ResponseT24;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-16T13:18:11-0600",
    comments = "version: 1.6.0, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class OutseerMapperImpl implements OutseerMapper {

    @Override
    public AnalyzeRequest toAnalyzeRequest(OutseerRequest requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        AnalyzeRequest analyzeRequest = new AnalyzeRequest();

        analyzeRequest.setDeviceRequest( toDeviceRequest( requestDtoRequestDeviceRequest( requestDto ) ) );
        analyzeRequest.setIdentificationData( toIdentificationData( requestDtoRequestIdentificationData( requestDto ) ) );
        analyzeRequest.setEventDataList( mapEventDataList( requestDtoRequestEventDataListEventData( requestDto ) ) );
        analyzeRequest.setRunRiskType( mapRunRiskType( requestDtoRequestRunRiskType( requestDto ) ) );
        analyzeRequest.setChannelIndicator( mapChannelIndicator( requestDtoRequestChannelIndicator( requestDto ) ) );

        analyzeRequest.setMessageHeader( mapMessageHeader() );
        analyzeRequest.setSecurityHeader( mapSecurityHeader() );

        return analyzeRequest;
    }

    @Override
    public DeviceRequest toDeviceRequest(OutseerRequest.DeviceRequest dto) {
        if ( dto == null ) {
            return null;
        }

        DeviceRequest deviceRequest = new DeviceRequest();

        deviceRequest.setDeviceTokenCookie( dto.getDeviceTokenCookie() );
        deviceRequest.setIpAddress( dto.getIpAddress() );
        if ( dto.getUserAgent() != null ) {
            deviceRequest.setDevicePrint( dto.getUserAgent() );
        }
        else {
            deviceRequest.setDevicePrint( "" );
        }
        deviceRequest.setHttpAccept( dto.getHttpAccept() );
        deviceRequest.setHttpAcceptEncoding( dto.getHttpAcceptEncoding() );
        deviceRequest.setHttpAcceptLanguage( dto.getHttpAcceptLanguage() );
        deviceRequest.setHttpReferrer( dto.getHttpReferrer() );
        deviceRequest.setUserAgent( dto.getUserAgent() );
        deviceRequest.setDeviceIdentifier( mapDeviceIdentifier( dto.getDeviceIdentifier() ) );

        deviceRequest.setGeoLocation( mapGeolocationInfo(dto) );

        return deviceRequest;
    }

    @Override
    public IdentificationData toIdentificationData(OutseerRequest.IdentificationData dto) {
        if ( dto == null ) {
            return null;
        }

        IdentificationData identificationData = new IdentificationData();

        identificationData.setSessionId( dto.getSessionId() );
        identificationData.setTransactionId( dto.getTransactionId() );
        identificationData.setOrgName( dto.getOrgName() );
        identificationData.setUserName( dto.getUserName() );
        identificationData.setUserStatus( mapUserStatus( dto.getUserStatus() ) );
        identificationData.setUserType( mapUserType( dto.getUserType() ) );

        return identificationData;
    }

    @Override
    public EventData toEventData(OutseerRequest.EventData dto) {
        if ( dto == null ) {
            return null;
        }

        EventData eventData = new EventData();

        eventData.setClientDefinedAttributeList( mapOutseerClientDefinedAttributeList( dto.getClientDefinedAttributeList() ) );
        eventData.setEventType( mapEventType( dto.getEventType() ) );
        eventData.setEventReferenceId( mapEventReferenceId( dto.getEventReferenceId() ) );
        eventData.setAuthenticationLevel( toAuthenticationLevel( dto.getAuthenticationLevel() ) );
        eventData.setClientDefinedEventType( dto.getClientDefinedEventType() );
        eventData.setTransactionData( toTransactionData( dto.getTransactionData() ) );

        return eventData;
    }

    @Override
    public AuthenticationLevel toAuthenticationLevel(OutseerRequest.AuthenticateLevel dto) {
        if ( dto == null ) {
            return null;
        }

        AuthenticationLevel authenticationLevel = new AuthenticationLevel();

        if ( dto.getAttemptsTryCount() != null ) {
            authenticationLevel.setAttemptsTryCount( Integer.parseInt( dto.getAttemptsTryCount() ) );
        }
        if ( dto.getSuccessful() != null ) {
            authenticationLevel.setSuccessful( Boolean.parseBoolean( dto.getSuccessful() ) );
        }

        return authenticationLevel;
    }

    @Override
    public TransactionData toTransactionData(OutseerRequest.TransactionData dto) {
        if ( dto == null ) {
            return null;
        }

        TransactionData transactionData = new TransactionData();

        transactionData.setAmount( toAmount( dto.getAmount() ) );
        if ( dto.getDueDate() != null ) {
            transactionData.setDueDate( dto.getDueDate() );
        }
        else {
            transactionData.setDueDate( "" );
        }
        if ( dto.getEstimatedDeliveryDate() != null ) {
            transactionData.setEstimatedDeliveryDate( dto.getEstimatedDeliveryDate() );
        }
        else {
            transactionData.setEstimatedDeliveryDate( "" );
        }
        if ( dto.getExecutionSpeed() != null ) {
            transactionData.setExecutionSpeed( mapExecutionSpeed( dto.getExecutionSpeed() ) );
        }
        else {
            transactionData.setExecutionSpeed( mapExecutionSpeed( "" ) );
        }
        transactionData.setMyAccountData( toMyAccountData( dto.getMyAccountData() ) );
        transactionData.setOtherAccountData( otherAccountDataToAccountData( dto.getOtherAccountData() ) );
        if ( dto.getOtherAccountBankType() != null ) {
            transactionData.setOtherAccountBankType( mapOtherAccountBankType( dto.getOtherAccountBankType() ) );
        }
        else {
            transactionData.setOtherAccountBankType( mapOtherAccountBankType( "" ) );
        }
        if ( dto.getOtherAccountOwnershipType() != null ) {
            transactionData.setOtherAccountOwnershipType( mapOtherAccountOwnershipType( dto.getOtherAccountOwnershipType() ) );
        }
        else {
            transactionData.setOtherAccountOwnershipType( mapOtherAccountOwnershipType( "" ) );
        }
        if ( dto.getOtherAccountType() != null ) {
            transactionData.setOtherAccountType( mapOtherAccountType( dto.getOtherAccountType() ) );
        }
        else {
            transactionData.setOtherAccountType( mapOtherAccountType( "" ) );
        }
        if ( dto.getSchedule() != null ) {
            transactionData.setSchedule( mapSchedule( dto.getSchedule() ) );
        }
        else {
            transactionData.setSchedule( mapSchedule( "" ) );
        }
        if ( dto.getTransferMediumType() != null ) {
            transactionData.setTransferMediumType( mapTransferMediumType( dto.getTransferMediumType() ) );
        }
        else {
            transactionData.setTransferMediumType( mapTransferMediumType( "" ) );
        }

        return transactionData;
    }

    @Override
    public Amount toAmount(OutseerRequest.Amount dto) {
        if ( dto == null ) {
            return null;
        }

        Amount amount = new Amount();

        if ( dto.getAmount() != null ) {
            amount.setAmount( dto.getAmount().longValue() );
        }
        if ( dto.getAmountInUSD() != null ) {
            amount.setAmountInUSD( dto.getAmountInUSD().longValue() );
        }
        amount.setCurrency( dto.getCurrency() );

        return amount;
    }

    @Override
    public AccountData toMyAccountData(OutseerRequest.MyAccountData dto) {
        if ( dto == null ) {
            return null;
        }

        AccountData accountData = new AccountData();

        accountData.setAccountBalance( accountBalanceToAmount( dto.getAccountBalance() ) );
        accountData.setAccountNumber( dto.getAccountNumber() );
        accountData.setAccountType( mapAccountType( dto.getAccountType() ) );

        return accountData;
    }

    @Override
    public Response<String> responseT24ToApiResponse(ResponseT24 data) {
        if ( data == null ) {
            return null;
        }

        Response<String> response = new Response<String>();

        response.setMessage( data.getMessage() );
        response.setDateTime( data.getDateTime() );
        response.setData( data.getData() );
        response.setException( data.getException() );

        response.setTime( mapearCampoTime() );

        return response;
    }

    private OutseerRequest.DeviceRequest requestDtoRequestDeviceRequest(OutseerRequest outseerRequest) {
        OutseerRequest.Request request = outseerRequest.getRequest();
        if ( request == null ) {
            return null;
        }
        return request.getDeviceRequest();
    }

    private OutseerRequest.IdentificationData requestDtoRequestIdentificationData(OutseerRequest outseerRequest) {
        OutseerRequest.Request request = outseerRequest.getRequest();
        if ( request == null ) {
            return null;
        }
        return request.getIdentificationData();
    }

    private List<OutseerRequest.EventData> requestDtoRequestEventDataListEventData(OutseerRequest outseerRequest) {
        OutseerRequest.Request request = outseerRequest.getRequest();
        if ( request == null ) {
            return null;
        }
        OutseerRequest.EventDataList eventDataList = request.getEventDataList();
        if ( eventDataList == null ) {
            return null;
        }
        return eventDataList.getEventData();
    }

    private String requestDtoRequestRunRiskType(OutseerRequest outseerRequest) {
        OutseerRequest.Request request = outseerRequest.getRequest();
        if ( request == null ) {
            return null;
        }
        return request.getRunRiskType();
    }

    private String requestDtoRequestChannelIndicator(OutseerRequest outseerRequest) {
        OutseerRequest.Request request = outseerRequest.getRequest();
        if ( request == null ) {
            return null;
        }
        return request.getChannelIndicator();
    }

    protected Amount accountDailyLimitToAmount(OutseerRequest.AccountDailyLimit accountDailyLimit) {
        if ( accountDailyLimit == null ) {
            return null;
        }

        Amount amount = new Amount();

        if ( accountDailyLimit.getAmount() != null ) {
            amount.setAmount( accountDailyLimit.getAmount().longValue() );
        }
        if ( accountDailyLimit.getAmountInUSD() != null ) {
            amount.setAmountInUSD( accountDailyLimit.getAmountInUSD().longValue() );
        }
        amount.setCurrency( accountDailyLimit.getCurrency() );

        return amount;
    }

    protected AccountData otherAccountDataToAccountData(OutseerRequest.OtherAccountData otherAccountData) {
        if ( otherAccountData == null ) {
            return null;
        }

        AccountData accountData = new AccountData();

        accountData.setAccountDailyLimit( accountDailyLimitToAmount( otherAccountData.getAccountDailyLimit() ) );
        accountData.setAccountName( otherAccountData.getAccountName() );
        accountData.setAccountNickName( otherAccountData.getAccountNickName() );
        accountData.setAccountNumber( otherAccountData.getAccountNumber() );
        accountData.setAccountOpenedDate( otherAccountData.getAccountOpenedDate() );
        accountData.setRoutingCode( otherAccountData.getRoutingCode() );

        return accountData;
    }

    protected Amount accountBalanceToAmount(OutseerRequest.AccountBalance accountBalance) {
        if ( accountBalance == null ) {
            return null;
        }

        Amount amount = new Amount();

        if ( accountBalance.getAmount() != null ) {
            amount.setAmount( accountBalance.getAmount().longValue() );
        }
        if ( accountBalance.getAmountInUSD() != null ) {
            amount.setAmountInUSD( accountBalance.getAmountInUSD().longValue() );
        }
        amount.setCurrency( accountBalance.getCurrency() );

        return amount;
    }
}
