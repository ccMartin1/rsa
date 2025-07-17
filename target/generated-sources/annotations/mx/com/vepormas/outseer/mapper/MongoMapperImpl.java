package mx.com.vepormas.outseer.mapper;

import com.rsa.csd.ws.ActionCode;
import com.rsa.csd.ws.AnalyzeRequest;
import com.rsa.csd.ws.AnalyzeResponse;
import com.rsa.csd.ws.ChallengeResponse;
import com.rsa.csd.ws.ChannelIndicatorType;
import com.rsa.csd.ws.DeviceRequest;
import com.rsa.csd.ws.IdentificationData;
import com.rsa.csd.ws.RiskResult;
import com.rsa.csd.ws.TriggeredRule;
import java.util.Date;
import javax.annotation.processing.Generated;
import mx.com.vepormas.outseer.controller.pojo.OutseerResponse;
import mx.com.vepormas.outseer.model.outseer.Bitacora;
import mx.com.vepormas.outseer.model.outseer.OutseerResponseBitacora;
import mx.com.vepormas.outseer.model.outseer.OutseerRetos;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-16T13:18:11-0600",
    comments = "version: 1.6.0, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class MongoMapperImpl implements MongoMapper {

    @Override
    public OutseerRetos toOutseerIntegracion(AnalyzeRequest request, String otpInterno, String otpExterno, ChallengeResponse challengeResponse, Date date, String o, int i, AnalyzeResponse response) {
        if ( request == null && otpInterno == null && otpExterno == null && challengeResponse == null && date == null && o == null && response == null ) {
            return null;
        }

        OutseerRetos outseerRetos = new OutseerRetos();

        if ( request != null ) {
            outseerRetos.setCustomerId( requestIdentificationDataUserName( request ) );
        }
        if ( challengeResponse != null ) {
            outseerRetos.setSessionId( challengeResponseIdentificationDataSessionId( challengeResponse ) );
            outseerRetos.setTransactionId( challengeResponseIdentificationDataTransactionId( challengeResponse ) );
        }
        if ( date != null ) {
            outseerRetos.setFechaInsercion( date );
            outseerRetos.setFechaModificacion( date );
        }
        if ( o != null ) {
            outseerRetos.setSuccessful( o );
            outseerRetos.setEventReferenceid( o );
        }
        if ( response != null ) {
            outseerRetos.setActionCode( responseRiskResultTriggeredRuleActionCodeValue( response ) );
        }
        outseerRetos.setOtpInterno( otpInterno );
        outseerRetos.setOtpExterno( otpExterno );
        outseerRetos.setIntentos( i );
        outseerRetos.setEventType( getEventType(request) );
        outseerRetos.setClientDefinedEventType( getClientEventType(request) );
        outseerRetos.setOtherAccountBankType( request.getEventDataList().length > 0 ? request.getEventDataList()[0].getTransactionData().getOtherAccountBankType().getValue() : null );
        outseerRetos.setAccountName( request.getEventDataList().length > 0 ? request.getEventDataList()[0].getTransactionData().getOtherAccountData().getAccountName() : null );
        outseerRetos.setAccountNickName( request.getEventDataList().length > 0 ? request.getEventDataList()[0].getTransactionData().getOtherAccountData().getAccountNickName() : null );
        outseerRetos.setAccountNumber( request.getEventDataList().length > 0 ? request.getEventDataList()[0].getTransactionData().getOtherAccountData().getAccountNumber() : null );

        return outseerRetos;
    }

    @Override
    public Bitacora toSaveBitacora(Date fhRegistro, Date fhOutseerReq, Date fhOutseerResp, AnalyzeRequest request, AnalyzeResponse response, OutseerResponse outseerResponse) {
        if ( fhRegistro == null && fhOutseerReq == null && fhOutseerResp == null && request == null && response == null && outseerResponse == null ) {
            return null;
        }

        Bitacora bitacora = new Bitacora();

        if ( fhOutseerResp != null ) {
            bitacora.setFecAnalyze( fhOutseerResp );
            bitacora.setFhOutseerResp( fhOutseerResp );
        }
        if ( request != null ) {
            bitacora.setChannel( requestChannelIndicatorValue( request ) );
            bitacora.setIpAddress( requestDeviceRequestIpAddress( request ) );
            bitacora.setGeolocation( requestDeviceRequestGeoLocation( request ) );
        }
        bitacora.setFhRequest( fhRegistro );
        bitacora.setFhOutseerReq( fhOutseerReq );
        bitacora.setReasonCode( getReasonCode(response) );
        bitacora.setReasonDescription( getReasonDescription(response,outseerResponse) );
        bitacora.setRiskScore( getRiskScore(response) );
        bitacora.setActionCode( getActionCode(response) );
        bitacora.setActionName( getActionName(response) );
        bitacora.setUserName( getUserName(request,response) );
        bitacora.setUserStatus( getUserStatus(response) );
        bitacora.setEventType( getEventType(request) );
        bitacora.setClientDefinedEventType( getClientEventType(request) );
        bitacora.setAmount( getAmount(request) );
        bitacora.setAccount( getMyAccountNumber(request) );
        bitacora.setOtherAccount( getOtherAccountNumber(request) );
        bitacora.setOrgName( getOrgName(response) );

        return bitacora;
    }

    @Override
    public OutseerResponseBitacora toSaveResponseBitacora(Date timestamp, String analyzeRequest, String analyzeResponse, boolean success, String errorMessage) {
        if ( timestamp == null && analyzeRequest == null && analyzeResponse == null && errorMessage == null ) {
            return null;
        }

        OutseerResponseBitacora outseerResponseBitacora = new OutseerResponseBitacora();

        outseerResponseBitacora.setTimestamp( timestamp );
        outseerResponseBitacora.setAnalyzeRequest( analyzeRequest );
        outseerResponseBitacora.setAnalyzeResponse( analyzeResponse );
        outseerResponseBitacora.setSuccess( success );
        outseerResponseBitacora.setErrorMessage( errorMessage );

        return outseerResponseBitacora;
    }

    private String requestIdentificationDataUserName(AnalyzeRequest analyzeRequest) {
        IdentificationData identificationData = analyzeRequest.getIdentificationData();
        if ( identificationData == null ) {
            return null;
        }
        return identificationData.getUserName();
    }

    private String responseRiskResultTriggeredRuleActionCodeValue(AnalyzeResponse analyzeResponse) {
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

    private String requestChannelIndicatorValue(AnalyzeRequest analyzeRequest) {
        ChannelIndicatorType channelIndicator = analyzeRequest.getChannelIndicator();
        if ( channelIndicator == null ) {
            return null;
        }
        return channelIndicator.getValue();
    }

    private String requestDeviceRequestIpAddress(AnalyzeRequest analyzeRequest) {
        DeviceRequest deviceRequest = analyzeRequest.getDeviceRequest();
        if ( deviceRequest == null ) {
            return null;
        }
        return deviceRequest.getIpAddress();
    }

    private String requestDeviceRequestGeoLocation(AnalyzeRequest analyzeRequest) {
        DeviceRequest deviceRequest = analyzeRequest.getDeviceRequest();
        if ( deviceRequest == null ) {
            return null;
        }
        return deviceRequest.getGeoLocation();
    }
}
