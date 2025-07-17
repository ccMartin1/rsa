package mx.com.vepormas.outseer;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import mx.com.vepormas.outseer.controller.pojo.Response;
import mx.com.vepormas.outseer.util.Constantes;
import mx.com.vepormas.outseer.util.Utilerias;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

/**
 ** Clase que maneja la exception general
 *
 * @author Hector Aguirre Loza
 * @version 1.0
 * @since 2024-16-08
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler( MethodArgumentNotValidException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    public ResponseEntity<Response<Map<String, String>>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request ) {
        log.error( "..:: handler MethodArgumentNotValidException ::.." );
        Response<Map<String,String>> response = new Response<>();
        Map<String,String> mapMensajesError = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->  {
            String campo = ((FieldError) error).getField();
            String valor = error.getDefaultMessage();
            mapMensajesError.put(campo,valor);
        });
        response.setMessage(Constantes.ARGUMENT_NOT_VALID);
        response.setDateTime(LocalDateTime.now().toString());
        response.setTime(Utilerias.fechaToMilisegundos());
        response.setException(Utilerias.mensajeLimitado(ex.getMessage()));
        response.setData(mapMensajesError);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler( OutseerException.class )
    @ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR )
    public ResponseEntity<Response<String>> outseerExcepcion(OutseerException ex) {
        log.error( "..:: handler OutseerExcepcion ::.." );
        Response<String> response = new Response<>();
        response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.setDateTime(LocalDateTime.now().toString());
        response.setTime(Utilerias.fechaToMilisegundos());
        response.setException(Utilerias.mensajeLimitado(ex.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler( NoSuchElementException.class )
    @ResponseStatus( value = HttpStatus.NOT_FOUND )
    public ResponseEntity<Response<String>> resourceNotFoundException(NoSuchElementException ex, WebRequest request ) {
        log.error( "..:: handler NoSuchElementException ::.." );
        Response<String> response = new Response<>();
        response.setMessage(Constantes.ARGUMENT_NOT_VALID);
        response.setDateTime(LocalDateTime.now().toString());
        response.setTime(Utilerias.fechaToMilisegundos());
        response.setException(request.getDescription( false ));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler( TimeoutException.class )
    @ResponseStatus( value = HttpStatus.REQUEST_TIMEOUT )
    public ResponseEntity<Response<String>> resourceNotTimeoutException(TimeoutException ex, WebRequest request ) {
        log.error( "..:: handler TimeoutException ::.." );
        Response<String> response = new Response<>();
        response.setMessage(Constantes.ARGUMENT_NOT_VALID);
        response.setDateTime(LocalDateTime.now().toString());
        response.setTime(Utilerias.fechaToMilisegundos());
        response.setException(request.getDescription( false ));
        return new ResponseEntity<>(response, HttpStatus.REQUEST_TIMEOUT);
    }


}
