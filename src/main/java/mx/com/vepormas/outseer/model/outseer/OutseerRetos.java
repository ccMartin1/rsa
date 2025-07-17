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
 * @apiNote Modelo de mongo para guardar datos de registro
 * @since 2024-16-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "tr_outseer_reto")
public class OutseerRetos {
    @Id
    @Field(name = "_id")
    private String id;
    private String userPrincipal;
    private String customerId;
    private String eventType;
    private String clientDefinedEventType;
    private String actionCode;
    private String sessionId;
    private String transactionId;
    private String successful;
    private int intentos;
    private String otpInterno;
    private String otpExterno;
    private String otherAccountBankType;
    private String accountName;
    private String accountNickName;
    private String accountNumber;
    private String eventReferenceid;
    private Date fechaInsercion;
    private Date fechaModificacion;
}
