package mx.com.vepormas.outseer.controller.pojo;

import lombok.Data;

@Data
public class RefreshOTPRequest {
    private String principal;
    private String sessionId;
}
