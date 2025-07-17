package mx.com.vepormas.outseer.model.outseer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "tr_outseer_conteo_reto")
public class OutseerContadorRetos {
    @Id
    @Field(name = "_id")
    private String id;
    private String userName;
    private int count;
}
