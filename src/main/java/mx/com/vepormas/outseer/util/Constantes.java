package mx.com.vepormas.outseer.util;

import org.apache.axis.utils.JavaUtils;

/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @apiNote Utilidad que se encarga de concentrar las constantes utilizadas en el servicio
 * @since 2024-16-08
 */

public class Constantes {
    public static final String TIPO_OFS = "3";
    public static final String USR = "USRT24";
    public static final String PASS = "PWDT24";
    public static final String DIAG = "/";
    public static final String DIAG2 = "|";
    public static final String PASST24 = "rsauser07$";
    public static final String USRT24 = "RSA_USER";
    public static final String CHALLENGE = "CHALLENGE";
    public static final String DENY = "DENY";
    public static final String ALLOW = "ALLOW";
    /* ----- Codigos de respuesta-----*/
    public static final int SUCCESS = 200;
    public static final int ENDPOINT_CODE_CHALLENGE = 201;
    public static final int ENDPOINT_CODE_REQUEST_BAD = 400;
    public static final int ENDPOINT_CODE_FAIL = 500;
    public static final int ENDPOINT_CODE_USER_DENY = 518;
    public static final String MSG_ACTION_CODE_CHALLENGE = "Se recomienda retar la accion/transaccion";
    public static final String ERROR_MSG_WRONG_REQUEST = "Error en la peticion";
    public static final String FAIL = "FAIL";
    public static final String OP_EXITOSA = "Operación exitosa";
    /* ----- mensajes de respuesta services apiResponse generico-----*/
    public static final String BAD_OTP = "El código de verificación es incorrecto intenta nuevamente.";
    public static final String SUSSES_OTP = "OTP valido.";
    public static final String ERROR_CONECTOR_T24 = "Error al consultar datos a T24 - ";
    public static final String STR_VACIO = "";
    public static final String ARGUMENT_NOT_VALID = "invalid arguments in input parameters";
    /* ----- constantes EventType-----*/
    public static final String NA = "NA";
    public static final String T24_ESTADO_BLOQUEADO = "BLOQUEADO";

    // Nombres de las cachés
    public static final String CACHE_NOMBRE_ANALYZE_RERQUEST = "analyze_request";
    public static final String CACHE_NOMBRE_ANALYZE_RESPONSE = "analyze_response";
    public static final String NOTIFY_SUCCESS = "Notificacion Exitosa";
    public static final int ENDPOINT_CODE_OTP_ERROR =204;
    public static final String SESSION_SIGNIN =  "SESSION_SIGNIN";
    public static final String LADA_MX = "52";
    public static final String SOAP_ACTION_HEADER = "SOAPAction";
    public static final String SMS_REQUEST = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:gen=\"http://vepormas.com.mx/banca/schemas/generanotificaciones\" xmlns:base=\"http://vepormas.com.mx/banca/schemas/base\">\r\n"
            + "   <soapenv:Header><wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsse:UsernameToken wsu:Id=\"UsernameToken-17766BF1FC7336A1A215591706115381\"><wsse:Username>usr_sb01</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">temporal</wsse:Password></wsse:UsernameToken></wsse:Security></soapenv:Header>\r\n"
            + "   <soapenv:Body>\r\n"
            + "      <gen:SendSMSRequest>\r\n"
            + "         <gen:data>\r\n"
            + "            <gen:phoneNumber>${celular}</gen:phoneNumber>\r\n"
            + "            <gen:notificationId>notificacion.banca.retos.otp</gen:notificationId>\r\n"
            + "            <gen:parameters>\r\n"
            + "               <gen:key>parametro_numero_entero</gen:key>\r\n"
            + "               <gen:value>${otp}</gen:value>\r\n"
            + "            </gen:parameters>\r\n"
            + "         </gen:data>\r\n"
            + "      </gen:SendSMSRequest>\r\n"
            + "   </soapenv:Body>\r\n"
            + "</soapenv:Envelope>";


    public static final String MAIL_REQUEST = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:gen=\"http://vepormas.com.mx/banca/schemas/generanotificaciones\" xmlns:base=\"http://vepormas.com.mx/banca/schemas/base\">\r\n"
            + "   <soapenv:Header><wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsse:UsernameToken wsu:Id=\"UsernameToken-35E59B81769F7FD38315795442610861\"><wsse:Username>usr_sb01</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">temporal</wsse:Password></wsse:UsernameToken></wsse:Security></soapenv:Header>\r\n"
            + "   <soapenv:Body>\r\n"
            + "      <gen:SendNotificationRequest>\r\n"
            + "         <base:subject>\r\n"
            + "            <base:principal>${principal}</base:principal>\r\n"
            + "         </base:subject>\r\n"
            + "         <base:session>\r\n"
            + "            <base:channelId>1</base:channelId>\r\n"
            + "            <base:customerId>${cliente}</base:customerId>\r\n"
            + "            <base:userPrincipal>${principal}</base:userPrincipal>\r\n"
            + "         </base:session>\r\n"
            + "         <gen:data>\r\n"
            + "            <gen:email>${correo}</gen:email>\r\n"
            + "            <gen:notificationId>notif.movil.registro.folio</gen:notificationId>\r\n"
            + "            <gen:parameters>\r\n"
            + "               <gen:key>subject</gen:key>\r\n"
            + "               <gen:value>NOTIFICACION CONTRATACION BANCA MOVIL Bx+</gen:value>\r\n"
            + "            </gen:parameters>\r\n"
            + "     		  <gen:parameters>\r\n"
            + "               <gen:key>nombre</gen:key>\r\n"
            + "               <gen:value>${nombre}</gen:value>\r\n"
            + "            </gen:parameters>\r\n"
            + "            <gen:parameters>\r\n"
            + "               <gen:key>folioNumero</gen:key>\r\n"
            + "               <gen:value>${otp}</gen:value>\r\n"
            + "            </gen:parameters>\r\n"
            + "         </gen:data>\r\n"
            + "      </gen:SendNotificationRequest>\r\n"
            + "   </soapenv:Body>\r\n"
            + "</soapenv:Envelope>";
    public static final String SEND_OTP = "Reenvió de OTP correcto.";
    public static final String ERROR_SEND_OTP = "Error al reenviar el  OTP.";

    private Constantes() {
    }
}
