package quixada.ufc.br.SupplierApplicationInsight.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import quixada.ufc.br.SupplierApplicationInsight.models.Supplier;

public interface SupplierRepository extends MongoRepository<Supplier, String>{
}
