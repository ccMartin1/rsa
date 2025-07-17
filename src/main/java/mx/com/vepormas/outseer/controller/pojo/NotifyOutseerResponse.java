package mx.com.vepormas.outseer.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyOutseerResponse {
    private Boolean status;
    private int code;
    private String message;
    private int countChallenge;
    private String deviceTokenCookie;
}
