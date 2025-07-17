package mx.com.vepormas.outseer.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsa.csd.ws.APIType;
import com.rsa.csd.ws.AccountData;
import com.rsa.csd.ws.AccountType;
import com.rsa.csd.ws.Amount;
import com.rsa.csd.ws.AnalyzeRequest;
import com.rsa.csd.ws.AuthenticationLevel;
import com.rsa.csd.ws.AuthorizationMethod;
import com.rsa.csd.ws.ChannelIndicatorType;
import com.rsa.csd.ws.ClientDefinedFact;
import com.rsa.csd.ws.DataType;
import com.rsa.csd.ws.DeviceIdentifier;
import com.rsa.csd.ws.DeviceRequest;
import com.rsa.csd.ws.EventData;
import com.rsa.csd.ws.EventType;
import com.rsa.csd.ws.ExecutionSpeed;
import com.rsa.csd.ws.IdentificationData;
import com.rsa.csd.ws.MessageHeader;
import com.rsa.csd.ws.MessageVersion;
import com.rsa.csd.ws.MobileDevice;
import com.rsa.csd.ws.OtherAccountBankType;
import com.rsa.csd.ws.OtherAccountOwnershipType;
import com.rsa.csd.ws.OtherAccountType;
import com.rsa.csd.ws.RequestType;
import com.rsa.csd.ws.RunRiskType;
import com.rsa.csd.ws.Schedule;
import com.rsa.csd.ws.SecurityHeader;
import com.rsa.csd.ws.TransactionData;
import com.rsa.csd.ws.TransferMediumType;
import com.rsa.csd.ws.UserStatus;
import com.rsa.csd.ws.WSUserType;
import mx.com.vepormas.outseer.controller.pojo.OutseerRequest;
import mx.com.vepormas.outseer.controller.pojo.Response;
import mx.com.vepormas.outseer.controller.pojo.ResponseT24;
import mx.com.vepormas.outseer.util.Constantes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.cache.annotation.CachePut;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @apiNote Interfaz que se encarga del mapeo del request de la peticiones de Outseer
 * @since 2024-16-08
 */
@Mapper(componentModel = "spring")
public interface OutseerMapper {

    @CachePut(value = Constantes.CACHE_NOMBRE_ANALYZE_RERQUEST, key = "#requestDto.request.channelIndicator")
    @Mapping(source = "request.deviceRequest", target = "deviceRequest")
    @Mapping(source = "request.identificationData", target = "identificationData")
    @Mapping(expression = "java(mapMessageHeader())", target = "messageHeader")
    @Mapping(expression = "java(mapSecurityHeader())", target = "securityHeader")
    @Mapping(source = "request.eventDataList.eventData", target = "eventDataList", qualifiedByName = "mapEventDataToArray")
    @Mapping(source = "request.runRiskType", target = "runRiskType", qualifiedByName = "mapRunRiskType")
    @Mapping(source = "request.channelIndicator", target = "channelIndicator", qualifiedByName = "mapChannelIndicator")
    AnalyzeRequest toAnalyzeRequest(OutseerRequest requestDto);


    @Mapping(source = "deviceTokenCookie", target = "deviceTokenCookie")
    @Mapping(source = "ipAddress", target = "ipAddress")
    @Mapping(source = "userAgent", target = "devicePrint", defaultValue = "")
    @Mapping(source = "httpAccept", target = "httpAccept")
    @Mapping(source = "httpAcceptEncoding", target = "httpAcceptEncoding")
    @Mapping(source = "httpAcceptLanguage", target = "httpAcceptLanguage")
    @Mapping(source = "httpReferrer", target = "httpReferrer")
    @Mapping(source = "userAgent", target = "userAgent")
    @Mapping(source = "deviceIdentifier", target = "deviceIdentifier", qualifiedByName = "mapDeviceIdentifier")
    @Mapping(expression = "java(mapGeolocationInfo(dto))", target = "geoLocation")
    DeviceRequest toDeviceRequest(OutseerRequest.DeviceRequest dto);

    @Mapping(source = "sessionId", target = "sessionId")
    @Mapping(source = "transactionId", target = "transactionId")
    @Mapping(source = "orgName", target = "orgName")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "userStatus", target = "userStatus", qualifiedByName = "mapUserStatus")
    @Mapping(source = "userType", target = "userType", qualifiedByName = "mapUserType")
    IdentificationData toIdentificationData(OutseerRequest.IdentificationData dto);


    @Mapping(source = "clientDefinedAttributeList", target = "clientDefinedAttributeList", qualifiedByName = "mapOutseerClientDefinedAttributeList")
    @Mapping(source = "eventType", target = "eventType", qualifiedByName = "mapEventType")
    @Mapping(source = "eventReferenceId", target = "eventReferenceId", qualifiedByName = "mapEventReferenceId")
    EventData toEventData(OutseerRequest.EventData dto);

    @Mapping(source = "attemptsTryCount", target = "attemptsTryCount")
    @Mapping(source = "successful", target = "successful")
    AuthenticationLevel toAuthenticationLevel(OutseerRequest.AuthenticateLevel dto);

    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "dueDate", target = "dueDate", defaultValue = "")
    @Mapping(source = "estimatedDeliveryDate", target = "estimatedDeliveryDate", defaultValue = "")
    @Mapping(source = "executionSpeed", target = "executionSpeed", qualifiedByName = "mapExecutionSpeed", defaultValue = "")
    @Mapping(source = "myAccountData", target = "myAccountData")
    @Mapping(source = "otherAccountData", target = "otherAccountData")
    @Mapping(source = "otherAccountBankType", target = "otherAccountBankType", qualifiedByName = "mapOtherAccountBankType", defaultValue = "")
    @Mapping(source = "otherAccountOwnershipType", target = "otherAccountOwnershipType", qualifiedByName = "mapOtherAccountOwnershipType", defaultValue = "")
    @Mapping(source = "otherAccountType", target = "otherAccountType", qualifiedByName = "mapOtherAccountType", defaultValue = "")
    @Mapping(source = "schedule", target = "schedule", qualifiedByName = "mapSchedule", defaultValue = "")
    @Mapping(source = "transferMediumType", target = "transferMediumType", qualifiedByName = "mapTransferMediumType", defaultValue = "")
    TransactionData toTransactionData(OutseerRequest.TransactionData dto);

    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "amountInUSD", target = "amountInUSD")
    @Mapping(source = "currency", target = "currency")
    Amount toAmount(OutseerRequest.Amount dto);

    @Mapping(source = "accountBalance", target = "accountBalance")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "accountType", target = "accountType", qualifiedByName = "mapAccountType")
    AccountData toMyAccountData(OutseerRequest.MyAccountData dto);


    @Mapping(target = "message", source = "message")
    @Mapping(target = "dateTime", source = "dateTime")
    @Mapping(target = "time", expression = "java(mapearCampoTime())")
    @Mapping(target = "data", source = "data")
    Response<String> responseT24ToApiResponse(ResponseT24 data);

    default long mapearCampoTime() {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @Named("mapDeviceIdentifier")
    default DeviceIdentifier[] mapDeviceIdentifier(OutseerRequest.DeviceIdentifier deviceIdentifierDTO) {
        if (deviceIdentifierDTO == null) {
            return new DeviceIdentifier[0];
        }
        MobileDevice mobileDevice = new MobileDevice();
        mobileDevice.setMobileSdkData(deviceIdentifierDTO.getMobileSdkData() != null ?
                deviceIdentifierDTO.getMobileSdkData() : null);

        return new DeviceIdentifier[]{mobileDevice};
    }

    @Named("mapEventDataToArray")
    default EventData[] mapEventDataList(List<OutseerRequest.EventData> eventDataDTOList) {
        if (eventDataDTOList == null || eventDataDTOList.isEmpty()) {
            return new EventData[0];
        }

        return eventDataDTOList.stream().map(this::toEventData).toArray(EventData[]::new);
    }

    @Named("mapRunRiskType")
    default RunRiskType mapRunRiskType(String runRiskType) {
        return RunRiskType.fromValue(runRiskType);
    }

    @Named("mapChannelIndicator")
    default ChannelIndicatorType mapChannelIndicator(String channelIndicator) {
        return ChannelIndicatorType.fromValue(channelIndicator);
    }

    @Named("mapEventType")
    default EventType mapEventType(String eventType) {
        if (eventType == null) {
            return null;
        }
        return EventType.fromValue(eventType);
    }

    @Named("mapEventReferenceId")
    default String mapEventReferenceId(String mapEventReferenceId) {
        return mapEventReferenceId;
    }

    @Named("mapDataType")
    default DataType mapDataType(String dataType) {
        if (dataType.isEmpty()) {
            return null;
        }
        return DataType.fromValue(dataType);
    }

    @Named("mapExecutionSpeed")
    default ExecutionSpeed mapExecutionSpeed(String executionSpeed) {
        if (executionSpeed.isEmpty()) {
            return null;
        }
        return ExecutionSpeed.fromValue(executionSpeed);
    }

    @Named("mapOtherAccountBankType")
    default OtherAccountBankType mapOtherAccountBankType(String otherAccountBankType) {
        if (otherAccountBankType.isEmpty()) {
            return null;
        } else {
            return OtherAccountBankType.fromValue(otherAccountBankType);
        }
    }

    @Named("mapOtherAccountOwnershipType")
    default OtherAccountOwnershipType mapOtherAccountOwnershipType(String otherAccountOwnershipType) {
        if (otherAccountOwnershipType.isEmpty()) {
            return null;
        } else {
            return OtherAccountOwnershipType.fromValue(otherAccountOwnershipType);
        }
    }

    @Named("mapOtherAccountType")
    default OtherAccountType mapOtherAccountType(String otherAccountType) {
        if (otherAccountType.isEmpty()) {
            return null;
        } else {
            return OtherAccountType.fromValue(otherAccountType);
        }
    }

    @Named("mapSchedule")
    default Schedule mapSchedule(String schedule) {
        if (schedule.isEmpty()) {
            return null;
        } else {
            return Schedule.fromValue(schedule);
        }
    }

    @Named("mapTransferMediumType")
    default TransferMediumType mapTransferMediumType(String transferMediumType) {
        if (transferMediumType.isEmpty()) {
            return null;
        } else {
            return TransferMediumType.fromValue(transferMediumType);
        }
    }

    @Named("mapUserStatus")
    default UserStatus mapUserStatus(String userStatus) {
        return userStatus == null ? null : UserStatus.fromValue(userStatus);
    }

    @Named("mapUserType")
    default WSUserType mapUserType(String userType) {
        return userType == null ? null : WSUserType.fromValue(userType);
    }


    @Named("mapAccountType")
    default AccountType mapAccountType(String accountType) {
        return accountType == null ? null : AccountType.fromValue(accountType);
    }

    @Named("mapOutseerClientDefinedAttributeList")
    default ClientDefinedFact[] mapOutseerClientDefinedAttributeList(OutseerRequest.ClientDefinedAttributeList attributeList) {
        if (attributeList == null) {
            return new ClientDefinedFact[0];
        }

        return Optional.ofNullable(attributeList.getFacts()).map(facts
                        -> facts.stream()
                        .map(this::convertToClientDefinedFact)
                        .toArray(ClientDefinedFact[]::new))
                .orElse(new ClientDefinedFact[0]);
    }

    @Named("convertToClientDefinedFact")
    default ClientDefinedFact convertToClientDefinedFact(Object fact) throws NoSuchElementException {
        ClientDefinedFact clientDefinedFact = new ClientDefinedFact();

        try {
            String name = invokeGetter(fact, "getName");
            String value = invokeGetter(fact, "getValue");
            String dataType = invokeGetter(fact, "getDataType");

            clientDefinedFact.setName(name);
            clientDefinedFact.setValue(value);
            clientDefinedFact.setDataType(mapDataType(dataType));
        } catch (Exception e) {
            throw new NoSuchElementException(e);
        }

        return clientDefinedFact;
    }

    default String invokeGetter(Object obj, String methodName) {
        try {
            Method method = obj.getClass().getMethod(methodName);
            Object result = method.invoke(obj);
            return result != null ? result.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }


    default MessageHeader mapMessageHeader() {
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setApiType(APIType.DIRECT_SOAP_API);
        messageHeader.setRequestType(RequestType.ANALYZE);
        messageHeader.setVersion(MessageVersion.value1);
        return messageHeader;
    }

    default SecurityHeader mapSecurityHeader() {
        SecurityHeader securityHeader = new SecurityHeader();
        securityHeader.setCallerCredential(Constantes.PASST24);
        securityHeader.setCallerId(Constantes.USRT24);
        securityHeader.setMethod(AuthorizationMethod.PASSWORD);
        return securityHeader;
    }

    default String mapGeolocationInfo(OutseerRequest.DeviceRequest request) throws NoSuchElementException {
        OutseerRequest.Geolocation geolocation = new OutseerRequest.Geolocation();
        List<OutseerRequest.GeoLocationInfo> geoLocationInfos = new ArrayList<>();
        if (request.getGeolocation() != null) {
            geoLocationInfos.addAll(request.getGeolocation().getGeoLocationInfo());
        }
        if (request.getDeviceIdentifier() != null && request.getDeviceIdentifier().getMobileSdkData() != null) {

            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode rootNode = mapper.readTree(request.getDeviceIdentifier().getMobileSdkData());
                if (rootNode.has("GeoLocationInfo")) {
                    for (JsonNode ee : rootNode.get("GeoLocationInfo")) {
                        OutseerRequest.GeoLocationInfo geoLocationInfo = mapper.treeToValue(ee, OutseerRequest.GeoLocationInfo.class);
                        geoLocationInfos.add(geoLocationInfo);
                    }
                }
            } catch (JsonProcessingException e) {
                throw new NoSuchElementException();
            }

        }
        geolocation.setGeoLocationInfo(geoLocationInfos);
        return geolocation.toString();
    }


}