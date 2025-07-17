package mx.com.vepormas.outseer.model.usuario;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "usuario")
public class Usuario {

    @Indexed(unique = true)
    private String idCore;

    @Id
    private String principal;

    private String rfc;
    private String curp;
    private String roles;
    private String perfil;
    private String nombre;
    private String correo;
    private String celular;
    private String fechaHoraAlta;
    private String fechaHoraAperturaCuenta;
    private String estado;
    private String observaciones;

    private Acceso acceso;
    private Cliente cliente;
}