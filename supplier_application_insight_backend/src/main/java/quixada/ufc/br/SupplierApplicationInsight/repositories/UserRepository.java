package quixada.ufc.br.SupplierApplicationInsight.repositories;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import quixada.ufc.br.SupplierApplicationInsight.models.User;
import quixada.ufc.br.SupplierApplicationInsight.types.enums.UserRole;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findUserByRole(UserRole role);
}
