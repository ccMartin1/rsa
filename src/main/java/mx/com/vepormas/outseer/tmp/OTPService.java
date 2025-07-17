package mx.com.vepormas.outseer.tmp;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import mx.com.vepormas.outseer.controller.pojo.Response;
import mx.com.vepormas.outseer.model.usuario.Usuario;
import mx.com.vepormas.outseer.service.RepositoryService;
import mx.com.vepormas.outseer.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Optional;
import java.util.Random;
@Slf4j
@Service
public class OTPService {
    @Value( "${tmp.sms.url}" )
    private String smsURL;
    @Autowired
    RepositoryService repositoryService;
    private WebClient smsClient;

    public Response<String> sendOTP(String principal) {
        String otp = String.format("%06d", new Random().nextInt(10000));
       var usuario = repositoryService.findUserByPrincipal(principal);
        //Se envia por SMS
        String requestSMS = Constantes.SMS_REQUEST
                .replace("${celular}", Constantes.LADA_MX.concat(usuario.get().getCelular()))
                .replace("${otp}",otp);

        smsClient
                .post()
                .bodyValue(requestSMS)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        //Se envia por MAIL
        String requestMAIL = Constantes.MAIL_REQUEST
                .replace("${principal}", principal)
                .replace("${cliente}", usuario.get().getCliente().getNumeroCliente())
                .replace("${correo}", usuario.get().getCorreo())
                .replace("${nombre}", usuario.get().getNombre())
                .replace("${otp}", otp);

        smsClient
                .post()
                .bodyValue(requestMAIL)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return new Response<>(HttpStatus.OK + " " + Constantes.OP_EXITOSA, otp);
    }
    @PostConstruct
    private void init() {

        smsClient = WebClient.builder()
                .baseUrl( smsURL )
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML_VALUE )
                .defaultHeader(Constantes.SOAP_ACTION_HEADER, "")
                .build();
        log.info(".{} hola", smsClient);
    }
}
