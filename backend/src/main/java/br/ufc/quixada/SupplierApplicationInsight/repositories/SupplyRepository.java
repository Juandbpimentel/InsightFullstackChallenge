package br.ufc.quixada.SupplierApplicationInsight.repositories;

import br.ufc.quixada.SupplierApplicationInsight.models.Supply;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SupplyRepository extends MongoRepository<Supply, String> {
    List<Supply> findSuppliesBySupplierId(String supplierId);
}
