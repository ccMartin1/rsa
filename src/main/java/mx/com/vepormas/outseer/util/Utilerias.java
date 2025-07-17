package mx.com.vepormas.outseer.util;


import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @since 2024-16-08
 * @apiNote  Utilidad que concentra las diferentes operaciones de conversión y rutina que se utilizan en el servicio
 */
public class Utilerias {

    public static String mensajeLimitado(String mensaje) {
        String mensajeError = mensaje;

        if (mensajeError.length() > 500) {
            mensajeError = mensajeError.substring(0, 500) + "...";
        }
        return mensajeError;
    }

    public static long fechaToMilisegundos() {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private Utilerias() {
    }
    public static String getMessageResponse(boolean isBlocked) {
        return isBlocked ? Constantes.OP_EXITOSA : Constantes.ERROR_MSG_WRONG_REQUEST;
    }
}