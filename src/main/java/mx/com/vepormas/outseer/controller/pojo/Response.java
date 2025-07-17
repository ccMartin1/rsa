package mx.com.vepormas.outseer.controller.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.com.vepormas.outseer.util.Constantes;
import mx.com.vepormas.outseer.util.Utilerias;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> {
    private String message;
    private String dateTime;
    private long time;
    private String exception;
    private T data;

    public Response(String message, T data) {
        this.message = message;
        this.dateTime = LocalDateTime.now().toString();
        this.time = Utilerias.fechaToMilisegundos();
        this.exception = Constantes.STR_VACIO;
        this.data = data;
    }

}