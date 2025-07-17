package mx.com.vepormas.outseer.model.outseer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @apiNote Modelo de mongo para agregar bitacora general
 * @since 2024-16-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "tr_outseer_log")
public class Bitacora {
    @Id
    @Field(name = "_id")
    private String id;
    private String reasonCode;
    private String reasonDescription;
    private String riskScore;
    private String actionCode;
    private String actionName;
    private String userName;
    private String userStatus;
    private Date fecAnalyze;
    private String eventType;
    private String clientDefinedEventType;
    private String amount;
    private String channel;
    private String account;
    private String otherAccount;
    private Date fhRequest;
    private Date fhOutseerReq;
    private Date fhOutseerResp;
    private String ipAddress;
    private String geolocation;
    private String orgName;
}
