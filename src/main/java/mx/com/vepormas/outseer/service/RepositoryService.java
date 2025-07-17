package mx.com.vepormas.outseer.service;

import com.rsa.csd.ws.AnalyzeRequest;
import com.rsa.csd.ws.EventData;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import mx.com.vepormas.outseer.model.outseer.Bitacora;
import mx.com.vepormas.outseer.model.outseer.OutseerContadorRetos;
import mx.com.vepormas.outseer.model.outseer.OutseerRetos;
import mx.com.vepormas.outseer.model.outseer.OutseerResponseBitacora;
import mx.com.vepormas.outseer.model.usuario.Usuario;
import mx.com.vepormas.outseer.repository.mongo.BitacoraRepository;
import mx.com.vepormas.outseer.repository.mongo.ContadorRetosRepository;
import mx.com.vepormas.outseer.repository.mongo.OperaRepository;
import mx.com.vepormas.outseer.repository.mongo.OutseerRetosRepository;
import mx.com.vepormas.outseer.repository.mongo.OutseerRepository;
import mx.com.vepormas.outseer.tmp.UsuarioRepository;
import mx.com.vepormas.outseer.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @apiNote Servicio que contiene las diferentes operaciones a mongo
 * @since 2024-16-08
 */


@Slf4j
@Service
public class RepositoryService {

    @Autowired
    private OperaRepository operaRepository;
    @Autowired
    private BitacoraRepository bitacoraRepository;
    @Autowired
    private OutseerRetosRepository ctaRepository;
    @Autowired
    private OutseerRepository outseerResponseBitacora;
    @Autowired
    private ContadorRetosRepository retosRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    UsuarioRepository usuarioRepository;

    @PostConstruct
    public void initContadorRetosCollection() {
        if (!mongoTemplate.collectionExists(OutseerContadorRetos.class)) {
            mongoTemplate.createCollection(OutseerContadorRetos.class);
            log.info("Colección 'contador_retos_outseer' creada al iniciar el servicio.");
        } else {
            log.info("Colección 'contador_retos_outseer' ya existe.");
        }
    }

    public void saveBitacora(Bitacora bitacora) {
        bitacoraRepository.save(bitacora);
    }

    public void saveCtaRepository(OutseerRetos integracion) {
        ctaRepository.save(integracion);
    }

    public void updateIntegrationRecord(AnalyzeRequest analyzeRequest) {
        EventData[] eventDataResponse = analyzeRequest.getEventDataList();
        Query query = new Query(Criteria.where("sessionId").is(analyzeRequest.getIdentificationData().getSessionId()));
        Update update = new Update()
                .set("successful", eventDataResponse[0].getAuthenticationLevel().getSuccessful().toString())
                .set("intentos", eventDataResponse[0].getAuthenticationLevel().getAttemptsTryCount())
                .set("eventReferenceid", eventDataResponse[0].getEventReferenceId())
                .set("fechaModificacion", new Date());
        mongoTemplate.updateFirst(query, update, OutseerRetos.class);
    }

    public String findTypeOperation(String eventType) {
        return operaRepository.findByCveOpera(eventType).getCveT24();
    }

    public void saveOutseerResponse(OutseerResponseBitacora bitacora) {
        outseerResponseBitacora.save(bitacora);
    }

    public int saveContador(OutseerContadorRetos retos) {
        OutseerContadorRetos existente = retosRepository.findByUserName(retos.getUserName());
        if (existente != null) {
            int nuevoContador = existente.getCount() + 1;
            Query query = new Query(Criteria.where("userName").is(retos.getUserName()));
            Update update = new Update().set("count", nuevoContador);
            mongoTemplate.updateFirst(query, update, OutseerContadorRetos.class);
            log.info("El usuario {} ya existe, contador actualizado a: {}", retos.getUserName(), nuevoContador);
            return nuevoContador;
        } else {
            retos.setCount(0);
            retosRepository.save(retos);
            log.info("Usuario {} insertado con contador en cero.", retos.getUserName());
            return 0;
        }
    }

    public void deleteIntegrationRecordByOTP(String username) {
        Query query = new Query(Criteria.where("userName").is(username));
        mongoTemplate.remove(query, OutseerContadorRetos.class);
    }

    public boolean isOtpValid(String sessionId, String otp) {
        Query query = new Query(Criteria.where("sessionId").is(sessionId));
        OutseerRetos integracion = mongoTemplate.findOne(query, OutseerRetos.class);
        if (integracion == null) {
            log.warn("No se encontró registro para sessionId: {}", sessionId);
            return false;
        }
        if (integracion.getOtpInterno() == null && integracion.getOtpExterno() == null) {
            log.warn("No hay OTP registrado (interno ni externo) para sessionId: {}", sessionId);
            return false;
        }
        boolean valido = Objects.equals(integracion.getOtpInterno(), otp) || Objects.equals(integracion.getOtpExterno(), otp);
        if (!valido) {
            log.warn("OTP inválido para sessionId: {}", sessionId);
        }
        return valido;
    }


    public OutseerContadorRetos existsInContadorRetos(String userName) {
        return retosRepository.findByUserName(userName);
    }

    public OutseerRetos findUltimoOutseerRetoSinSuccessful(String userName) {
        Query query = new Query();
          query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("customerId").is(userName),
                        Criteria.where("actionCode").is(Constantes.CHALLENGE))
        );
        query.with(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "fechaInsercion"));
        query.limit(1);
        return mongoTemplate.findOne(query, OutseerRetos.class);
    }

    public Optional<Usuario> findUserByPrincipal(String principal) {
        return usuarioRepository.findById(principal);
    }

    public Optional<Usuario> findUserByNumeroCliente(String numeroCliente) {
        Query query = new Query(Criteria.where("cliente.numeroCliente").is(numeroCliente));
        Usuario usuario = mongoTemplate.findOne(query, Usuario.class);
        return Optional.ofNullable(usuario);
    }

    public void updateOtpExternoBySessionId(String sessionId, String otpExterno) {
        Query query = new Query(Criteria.where("sessionId").is(sessionId));
        Update update = new Update()
                .set("otpExterno", otpExterno)
                .set("fechaModificacion", new Date());
        var result = mongoTemplate.updateFirst(query, update, OutseerRetos.class);
        if (result.getModifiedCount() > 0) {
            log.info("otpExterno actualizado correctamente para sessionId: {}", sessionId);
        } else {
            log.warn("No se encontró registro para actualizar otpExterno con sessionId: {}", sessionId);
        }
    }
}
