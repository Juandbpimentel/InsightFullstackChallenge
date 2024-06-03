package br.ufc.quixada.SupplierApplicationInsight.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import br.ufc.quixada.SupplierApplicationInsight.models.User;
import br.ufc.quixada.SupplierApplicationInsight.types.enums.UserRole;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findUserByRole(UserRole role);
}
