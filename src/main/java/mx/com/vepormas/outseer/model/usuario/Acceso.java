package mx.com.vepormas.outseer.model.usuario;

import lombok.Data;

import java.util.Date;

@Data
public class Acceso {
    private int intentos;
    private boolean cambioClave;
    private String estadoAcceso;
    private String dispositivo;
    private String tokenDevice;
    private String ultimoAcceso;
    private String estadoEnrolamiento;
    private Date caducidadAccesoTemporal;
    private String observaciones;
}
