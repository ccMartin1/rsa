package mx.com.vepormas.outseer.controller.pojo;

import com.rsa.csd.ws.BindingType;
import lombok.Data;

import java.util.List;

@Data
public class ValidUserRequest {
    private DeviceResult deviceResult;
    private IdentificationData identificationData;
    private MessageHeader messageHeader;
    private StatusHeader statusHeader;
    private List<RequiredCredential> requiredCredentialList;
    private RiskResult riskResult;
    private String serverRedirectData;

    @Data
    public static class DeviceResult {
        private AuthenticationResult authenticationResult;
        private CallStatus callStatus;
        private DeviceData deviceData;
    }

    @Data
    public static class AuthenticationResult {
        private String authStatusCode;
        private String risk;
    }

    @Data
    public static class CallStatus {
        private String statusCode;
        private String statusDescription;

    }

    @Data
    public static class DeviceData {
        private BindingType bindingType;
        private String deviceTokenCookie;
        private String deviceTokenF50;
        private String lookupLabel;
        private String newLabel;
    }

    @Data
    public static class IdentificationData {
        private String clientSessionId;
        private String clientTransactionId;
        private boolean delegated;
        private String groupName;
        private String newUserName;
        private String orgName;
        private String sessionId;
        private String transactionId;
        private String userCountry;
        private String userLanguage;
        private String userLoginName;
        private String userName;
        private UserStatus userStatus;
        private UserType userType;

    }

    @Data
    public static class UserStatus {
        private String value;

    }

    @Data
    public static class UserType {
        private String value;
    }

    @Data
    public static class MessageHeader {
        private ApiType apiType;
        private String requestId;
        private RequestType requestType;
        private String timeStamp;
        private Version version;

    }

    @Data
    public static class ApiType {
        private String value;

    }

    @Data
    public static class RequestType {
        private String value;

    }

    @Data
    public static class Version {
        private String value;

    }

    @Data
    public static class StatusHeader {
        private int reasonCode;
        private String reasonDescription;
        private int statusCode;

    }

    @Data
    public static class RequiredCredential {
        private CredentialType credentialType;
        private String genericCredentialType;
        private String groupName;
        private int preference;
        private boolean required;
    }

    @Data
    public static class CredentialType {
        private String value;
    }

    @Data
    public static class RiskResult {
        private int riskScore;
        private String riskScoreBand;
        private TriggeredRule triggeredRule;
        private TriggeredTestRule triggeredTestRule;

    }

    @Data
    public static class TriggeredRule {
        private ActionCode actionCode;
        private String actionName;
        private ActionType actionType;
        private List<ClientFact> clientFactList;
        private String ruleId;
        private String ruleName;

    }

    @Data
    public static class ActionCode {
        private String value;

    }

    @Data
    public static class ActionType {
        private String value;

    }

    @Data
    public static class ClientFact {
        private String name;
        private String value;

    }

    @Data
    public static class TriggeredTestRule {
        private String ruleId;
        private String ruleName;
    }
}
