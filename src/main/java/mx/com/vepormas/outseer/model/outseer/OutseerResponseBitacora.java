package mx.com.vepormas.outseer.model.outseer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection =  "tr_outseer_response_log")
public class OutseerResponseBitacora {
    @Id
    @Field(name = "_id")
    private String id;
    private Date timestamp;
    private String analyzeRequest;
    private String analyzeResponse;
    private boolean success;
    private String errorMessage;
}
