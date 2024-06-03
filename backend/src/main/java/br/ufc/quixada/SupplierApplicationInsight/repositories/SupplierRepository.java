package br.ufc.quixada.SupplierApplicationInsight.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import br.ufc.quixada.SupplierApplicationInsight.models.Supplier;

public interface SupplierRepository extends MongoRepository<Supplier, String>{
}
