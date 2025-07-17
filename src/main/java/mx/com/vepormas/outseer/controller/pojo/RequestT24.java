package mx.com.vepormas.outseer.controller.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestT24 {

    @NotNull
    private String tipo;
    @NotNull
    private String ofsT24;
}