package mx.com.vepormas.outseer.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @since 2024-16-08
 * * *Clase que contiene los datos del response de crear un usuario en mongo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutseerUserResponse {
    private String statusAuthCode;
    private Integer risk;
    private String statusCode;
    private String bindingType;
    private String deviceTokenCookie1;
    private String deviceTokenFSO1;
    private String delegated;
    private String transactionId;
    private String username;
    private String userStatus;
    private String userType;
    private String apyType;
    private String requestType;
    private String timeStamp;
    private String version;
    private Integer reasonCode;
    private String reasonDescription;
    private Integer statusCodeHeader;
}
