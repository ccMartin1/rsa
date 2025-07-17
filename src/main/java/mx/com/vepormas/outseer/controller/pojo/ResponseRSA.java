package mx.com.vepormas.outseer.controller.pojo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
@Data
public class ResponseRSA<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -2590565840468667515L;
    private boolean status;
    private int code;
    private String message;
    private T data;

}
