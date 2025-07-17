package mx.com.vepormas.outseer.tmp;

import mx.com.vepormas.outseer.model.usuario.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
}
