package br.ufc.quixada.SupplierApplicationInsight.repositories;

import br.ufc.quixada.SupplierApplicationInsight.models.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SupplierRepository extends MongoRepository<Supplier, String> {
    Optional<Supplier> findByCNPJOrEmail(String CNPJ, String email);
}
