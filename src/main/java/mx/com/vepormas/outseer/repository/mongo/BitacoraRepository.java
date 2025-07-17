package mx.com.vepormas.outseer.repository.mongo;

import mx.com.vepormas.outseer.model.outseer.Bitacora;
import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @apiNote Repositorio que se encarga de gestionar las operaciones en mongo
 * @since 2024-16-08
 */
public interface BitacoraRepository extends MongoRepository<Bitacora, String> {

}
