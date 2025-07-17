package mx.com.vepormas.outseer.repository.mongo;

import mx.com.vepormas.outseer.model.outseer.OutseerContadorRetos;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContadorRetosRepository extends MongoRepository<OutseerContadorRetos, String> {
    OutseerContadorRetos findByUserName(String userName);
}
