package mx.com.vepormas.outseer.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutseerResponse extends ResponseRSA<Object> {

    private String sessionId;
    private String transactionId;
    private String deviceTokenCookie;
    private String ruleId;
    private String rsaMessage;
    private Integer riskScore;
    private boolean status;
    private int code;
    private String message;
    private String actionType;
    private String otp;
    private int countChallenge;

}


