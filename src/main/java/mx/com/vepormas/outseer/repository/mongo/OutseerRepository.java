package mx.com.vepormas.outseer.repository.mongo;

import mx.com.vepormas.outseer.model.outseer.OutseerResponseBitacora;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OutseerRepository extends MongoRepository<OutseerResponseBitacora, String>{
}
