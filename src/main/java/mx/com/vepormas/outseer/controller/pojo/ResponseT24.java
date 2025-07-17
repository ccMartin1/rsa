package mx.com.vepormas.outseer.controller.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.com.vepormas.outseer.util.Utilerias;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseT24 {
    private String message;
    private String dateTime;
    private long time = Utilerias.fechaToMilisegundos();
    private String exception;
    private String data;
}
