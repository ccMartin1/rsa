package mx.com.vepormas.outseer.model.usuario;

import lombok.Data;

@Data
public class Cliente {

    private String rfc;
    private String razonSocial;
    private String numeroCliente;

    public Cliente(String rfc, String razonSocial, String numeroCliente) {
        super();
        this.rfc = rfc;
        this.razonSocial = razonSocial;
        this.numeroCliente = numeroCliente;
    }
}
