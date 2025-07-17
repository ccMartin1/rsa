package mx.com.vepormas.outseer.model.outseer;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @apiNote Modelo de Mongo para tipo de operaciones
 * @since 2024-16-08
 */
@Data
@Document(collection = "outseer_operacion")
public class OutseerOperacion {
    private String cveOpera;
    private String descOpera;
    private String cveT24;
}
