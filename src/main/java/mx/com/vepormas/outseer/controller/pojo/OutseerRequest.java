package mx.com.vepormas.outseer.controller.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OutseerRequest {
    private Request request;

    @Data
    public static class Request {
        private Session session;
        private DeviceRequest deviceRequest;
        private IdentificationData identificationData;
        private EventDataList eventDataList;
        private MessageHeader messageHeader;
        private SecurityHeader securityHeader;
        private String runRiskType;
        private String channelIndicator;
        private String otp;
    }

    @Data
    public static class Session {
        private String userId;
        private String sessionId;
        private String channelId;
        private String hostName;
        private String hostIpAddress;
        private String sourceURL;
        private String customerId;
        private String userPrincipal;
    }

    @Data
    public static class DeviceRequest {
        private String deviceTokenCookie;
        private String httpAccept;
        private String httpAcceptEncoding;
        private String httpAcceptLanguage;
        private String httpReferrer;
        private String ipAddress;
        private String userAgent;
        private DeviceIdentifier deviceIdentifier;
        private Geolocation geolocation;
    }

    @Data
    public static class DeviceIdentifier {
        private String mobileSdkData;
    }

    @Data
    public static class GeoLocationInfo {
        @JsonProperty("Status")
        private String status;
        @JsonProperty("Longitude")
        private String longitude;
        @JsonProperty("Latitude")
        private String latitude;
        @JsonProperty("HorizontalAccuracy")
        private String horizontalAccuracy;
        @JsonProperty("Altitude")
        private String altitude;
        @JsonProperty("AltitudeAccuracy")
        private String altitudeAccuracy;
        @JsonProperty("Timestamp")
        private String timestamp;
        @JsonProperty("Speed")
        private String speed;

        @Override
        public String toString() {
            return String.format("(status=%s, longitude=%s, latitude=%s, horizontalAccuracy=%s, altitude=%s, altitudeAccuracy=%s, timestamp=%s, speed=%s)",
                    status, longitude, latitude, horizontalAccuracy, altitude, altitudeAccuracy, timestamp, speed);
        }
    }

    @Data
    public static class Geolocation {
        private List<GeoLocationInfo> geoLocationInfo;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (GeoLocationInfo info : geoLocationInfo) {
                sb.append(info.toString()).append(", ");
            }
            if (sb.length() > 1) {
                sb.setLength(sb.length() - 2);
            }
            sb.append("]");
            return sb.toString();
        }
    }

    @Data
    public static class IdentificationData {
        private String sessionId;
        private String transactionId;
        private String orgName;
        private String userName;
        private String userStatus;
        private String userType;
    }

    @Data
    public static class EventDataList {
        private List<EventData> eventData;
    }

    @Data
    public static class EventData {
        private ClientDefinedAttributeList clientDefinedAttributeList;
        private String clientDefinedEventType;
        private String eventType;
        private String eventReferenceId;
        private AuthenticateLevel authenticationLevel;
        private TransactionData transactionData;
    }

    @Data
    public static class AuthenticateLevel {
        private String attemptsTryCount;
        private String successful;
    }

    @Data
    public static class ClientDefinedAttributeList {
        private List<Fact> facts;
    }

    @Data
    public static class Fact {
        private String name;
        private String value;
        private String dataType;
    }

    @Data
    public static class TransactionData {
        private Amount amount;
        private String dueDate;
        private String estimatedDeliveryDate;
        private String executionSpeed;
        private MyAccountData myAccountData;
        private OtherAccountData otherAccountData;
        private String otherAccountBankType;
        private String otherAccountOwnershipType;
        private String otherAccountType;
        private String schedule;
        private String transferMediumType;
    }

    @Data
    public static class Amount {
        private Double amount;
        private Double amountInUSD;
        private String currency;
    }

    @Data
    public static class MyAccountData {
        private AccountBalance accountBalance;
        private String accountNumber;
        private String accountType;
    }

    @Data
    public static class AccountBalance {
        private Double amount;
        private Double amountInUSD;
        private String currency;
    }

    @Data
    public static class OtherAccountData {
        private String accountName;
        private String accountNickName;
        private String accountNumber;
        private String routingCode;
        private String accountOpenedDate;
        private AccountDailyLimit accountDailyLimit;
    }

    @Data
    public static class AccountDailyLimit {
        private Double amount;
        private Double amountInUSD;
        private String currency;
    }

    @Data
    public static class MessageHeader {
        private String apiType;
        private String requestType;
        private String version;
    }

    @Data
    public static class SecurityHeader {
        private String callerCredential;
        private String callerId;
        private String method;
    }
}