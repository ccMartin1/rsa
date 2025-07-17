package mx.com.vepormas.outseer.controller.pojo;

import lombok.Data;

@Data
public class OTPRequest {
    private String userName;
    private String otp;
    private String session;
}
