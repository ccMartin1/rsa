package mx.com.vepormas.outseer;

/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @since 2024-16-08
 * Clase de Exception personalizada para el manejo de las excepciones del servicio de transferencias
 */
public class OutseerException extends RuntimeException {
    public OutseerException(String message) {
        super(message);
    }
}
