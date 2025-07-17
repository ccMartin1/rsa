package mx.com.vepormas.outseer.mapper;


import com.rsa.csd.ws.AnalyzeRequest;
import com.rsa.csd.ws.AnalyzeResponse;
import com.rsa.csd.ws.ChallengeResponse;
import com.rsa.csd.ws.EventData;
import com.rsa.csd.ws.UserStatus;
import mx.com.vepormas.outseer.controller.pojo.OutseerResponse;
import mx.com.vepormas.outseer.model.outseer.Bitacora;
import mx.com.vepormas.outseer.model.outseer.OutseerRetos;
import mx.com.vepormas.outseer.model.outseer.OutseerResponseBitacora;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Date;

/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @apiNote Interfaz que se encarga del mapeo del request de la peticiones de Outseer
 * @since 2024-16-08
 */
@Mapper(componentModel = "spring")
public interface MongoMapper {

    @Mapping(target = "customerId", source = "request.identificationData.userName")
    @Mapping(expression = "java(getEventType(request))", target = "eventType")
    @Mapping(expression = "java(getClientEventType(request))", target = "clientDefinedEventType")
    @Mapping(target = "actionCode", source = "response.riskResult.triggeredRule.actionCode.value")
    @Mapping(target = "sessionId", source = "challengeResponse.identificationData.sessionId")
    @Mapping(target = "transactionId", source = "challengeResponse.identificationData.transactionId")
    @Mapping(target = "successful", source = "o")
    @Mapping(target = "intentos", source = "i")
    @Mapping(target = "otpInterno", source = "otpInterno")
    @Mapping(target = "otpExterno", source = "otpExterno")
    @Mapping(target = "otherAccountBankType", expression = "java(request.getEventDataList().length > 0 ? request.getEventDataList()[0].getTransactionData().getOtherAccountBankType().getValue() : null)")
    @Mapping(target = "accountName", expression = "java(request.getEventDataList().length > 0 ? request.getEventDataList()[0].getTransactionData().getOtherAccountData().getAccountName() : null)")
    @Mapping(target = "accountNickName", expression = "java(request.getEventDataList().length > 0 ? request.getEventDataList()[0].getTransactionData().getOtherAccountData().getAccountNickName() : null)")
    @Mapping(target = "accountNumber", expression = "java(request.getEventDataList().length > 0 ? request.getEventDataList()[0].getTransactionData().getOtherAccountData().getAccountNumber() : null)")
    @Mapping(target = "eventReferenceid", source = "o")
    @Mapping(target = "fechaInsercion", source = "date")
    @Mapping(target = "fechaModificacion", source = "date")
    OutseerRetos toOutseerIntegracion(AnalyzeRequest request, String otpInterno,String otpExterno, ChallengeResponse challengeResponse, Date date, String o, int i, AnalyzeResponse response);

    @Mapping(expression = "java(getReasonCode(response))", target = "reasonCode")
    @Mapping(expression = "java(getReasonDescription(response,outseerResponse))", target = "reasonDescription")
    @Mapping(expression = "java(getRiskScore(response))", target = "riskScore")
    @Mapping(expression = "java(getActionCode(response))", target = "actionCode")
    @Mapping(expression = "java(getActionName(response))", target = "actionName")
    @Mapping(expression = "java(getUserName(request,response))", target = "userName")
    @Mapping(expression = "java(getUserStatus(response))", target = "userStatus")
    @Mapping(source = "fhOutseerResp", target = "fecAnalyze")
    @Mapping(expression = "java(getEventType(request))", target = "eventType")
    @Mapping(expression = "java(getClientEventType(request))", target = "clientDefinedEventType")
    @Mapping(expression = "java(getAmount(request))", target = "amount")
    @Mapping(source = "request.channelIndicator.value", target = "channel")
    @Mapping(expression = "java(getMyAccountNumber(request))", target = "account")
    @Mapping(expression = "java(getOtherAccountNumber(request))", target = "otherAccount")
    @Mapping(source = "fhRegistro", target = "fhRequest")
    @Mapping(source = "fhOutseerReq", target = "fhOutseerReq")
    @Mapping(source = "fhOutseerResp", target = "fhOutseerResp")
    @Mapping(source = "request.deviceRequest.ipAddress", target = "ipAddress")
    @Mapping(source = "request.deviceRequest.geoLocation", target = "geolocation")
    @Mapping(expression = "java(getOrgName(response))", target = "orgName")
    Bitacora toSaveBitacora(Date fhRegistro, Date fhOutseerReq,
                            Date fhOutseerResp, AnalyzeRequest request,
                            AnalyzeResponse response,
                            OutseerResponse outseerResponse);


    @Mapping(target = "timestamp", source = "timestamp")
    @Mapping(target = "analyzeRequest", source = "analyzeRequest")
    @Mapping(target = "analyzeResponse", source = "analyzeResponse")
    @Mapping(target = "success", source = "success")
    @Mapping(target = "errorMessage", source = "errorMessage")
    @Mapping(target = "id", ignore = true)
        // Mongo genera el id
    OutseerResponseBitacora toSaveResponseBitacora(
            Date timestamp,
            String analyzeRequest,
            String analyzeResponse,
            boolean success,
            String errorMessage
    );

    default String getReasonCode(AnalyzeResponse analyzeResponse) {
        if (analyzeResponse != null && analyzeResponse.getStatusHeader() != null) {
            return analyzeResponse.getStatusHeader().getReasonCode().toString();
        }
        return null;
    }

    default String getReasonDescription(AnalyzeResponse analyzeResponse, OutseerResponse outseerResponse) {
        if (analyzeResponse != null && analyzeResponse.getStatusHeader() != null) {
            return outseerResponse.getCode() + "  " + analyzeResponse.getStatusHeader().getReasonDescription() + " " + outseerResponse.getMessage();
        } else {
            return outseerResponse.getMessage();
        }

    }

    default String getRiskScore(AnalyzeResponse analyzeResponse) {
        if (analyzeResponse != null && analyzeResponse.getRiskResult() != null) {
            return analyzeResponse.getRiskResult().getRiskScore().toString();
        }
        return null;
    }

    default String getActionCode(AnalyzeResponse analyzeResponse) {
        if (analyzeResponse != null && analyzeResponse.getRiskResult() != null) {
            return analyzeResponse.getRiskResult().getTriggeredRule().getActionCode().getValue();
        }
        return null;
    }

    default String getActionName(AnalyzeResponse analyzeResponse) {
        if (analyzeResponse != null && analyzeResponse.getRiskResult() != null) {
            return analyzeResponse.getRiskResult().getTriggeredRule().getActionName();
        }
        return null;
    }

    default String getUserName(AnalyzeRequest analyzeRequest, AnalyzeResponse analyzeResponse) {
        if (analyzeResponse != null && analyzeResponse.getIdentificationData() != null) {
            return analyzeResponse.getIdentificationData().getUserName();
        }
        return analyzeRequest.getIdentificationData().getUserName();
    }

    default String getUserStatus(AnalyzeResponse analyzeResponse) {
        if (analyzeResponse != null && analyzeResponse.getIdentificationData() != null) {
            if (analyzeResponse.getIdentificationData().getUserStatus() != null) {
                return analyzeResponse.getIdentificationData().getUserStatus().getValue();
            }
            return UserStatus.UNVERIFIED.getValue();
        }
        return UserStatus.UNVERIFIED.getValue();
    }

    default String getOrgName(AnalyzeResponse analyzeResponse) {
        if (analyzeResponse != null && analyzeResponse.getIdentificationData() != null) {
            return analyzeResponse.getIdentificationData().getOrgName() != null ? analyzeResponse.getIdentificationData().getOrgName() : "default";
        }
        return "default";
    }

    default String getEventType(AnalyzeRequest request) {
        EventData[] eventData = request.getEventDataList();
        if (eventData != null) {
            for (EventData eventData1 : eventData) {
                if (eventData1.getClientDefinedEventType() != null) {
                    return eventData1.getEventType().getValue();
                } else {
                    return null;
                }
            }
        }
        return null;
    }
    default String getClientEventType(AnalyzeRequest request) {
        EventData[] eventData = request.getEventDataList();
        if (eventData != null) {
            for (EventData eventData1 : eventData) {
                if (eventData1.getClientDefinedEventType() != null) {
                    return eventData1.getClientDefinedEventType();
                } else {
                    return eventData1.getEventType().getValue();
                }
            }
        }
        return null;
    }

    default String getAmount(AnalyzeRequest request) {
        EventData[] eventData = request.getEventDataList();
        if (eventData != null) {
            for (EventData eventData1 : eventData) {
                if (eventData1.getTransactionData() != null && eventData1.getTransactionData().getAmount() != null) {
                    return eventData1.getTransactionData().getAmount().getAmount().toString();
                }
            }
        }

        return "NULL";
    }

    default String getMyAccountNumber(AnalyzeRequest request) {
        EventData[] eventData = request.getEventDataList();
        if (eventData != null) {
            for (EventData eventData1 : eventData) {
                if (eventData1.getTransactionData() != null && eventData1.getTransactionData().getMyAccountData() != null) {
                    return eventData1.getTransactionData().getMyAccountData().getAccountNumber();
                }

            }
        }
        return "NULL";
    }

    default String getOtherAccountNumber(AnalyzeRequest request) {

        EventData[] eventData = request.getEventDataList();
        if (eventData != null) {
            for (EventData eventData1 : eventData) {
                if (eventData1.getTransactionData() != null) {
                    return eventData1.getTransactionData().getOtherAccountData().getAccountNumber();
                }

            }
        }
        return "NULL";
    }

}
