package br.ufc.quixada.SupplierApplicationInsight.services;

import br.ufc.quixada.SupplierApplicationInsight.models.Supply;
import br.ufc.quixada.SupplierApplicationInsight.repositories.SupplyRepository;
import br.ufc.quixada.SupplierApplicationInsight.types.enums.SupplyType;
import br.ufc.quixada.SupplierApplicationInsight.types.exceptions.SupplierNotFoundException;
import br.ufc.quixada.SupplierApplicationInsight.types.exceptions.SupplyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SupplyService {

    @Autowired
    private SupplyRepository supplyRepository;

    @Autowired
    private SupplierService supplierService;

    public void deleteSupplyById(String id) {
        Optional<Supply> supply = supplyRepository.findById(id);
        if (supply.isEmpty())
            throw new IllegalArgumentException("Supply does not exist");
        supplyRepository.deleteById(id);
    }

    public Supply updateSupply(String id, Map<String, Object> supplyFields) {
        Supply supply = supplyRepository.findById(id).orElseThrow(() -> new SupplyNotFoundException("Supply does not exist"));
        if (supplyFields.isEmpty())
            throw new IllegalArgumentException("Supply fields are empty");
        supplyFields.forEach((key, value) -> {
            switch (key) {
                case "name":
                    supply.setName((String) value);
                    break;
                case "description":
                    supply.setDescription((String) value);
                    break;
                case "supplyDate":
                    supply.setSupplyDate((String) value);
                    break;
                case "supplyType":
                    supply.setSupplyType((SupplyType) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });
        return supplyRepository.save(supply);
    }

    public Supply createSupply(Supply supply) {
        if (supplierService.getSupplierById(supply.getSupplierId()) == null)
            throw new SupplierNotFoundException("Supplier does not exist");
        return supplyRepository.save(supply);
    }

    public Supply getSupplyById(String id) {
        return supplyRepository.findById(id).orElseThrow(() -> new SupplyNotFoundException("Supply does not exist"));
    }

    public List<Supply> getSuppliesBySupplierId(String supplierId) {
        if (supplierService.getSupplierById(supplierId) == null)
            throw new SupplierNotFoundException("Supplier does not exist");
        return supplyRepository.findSuppliesBySupplierId(supplierId);
    }

    public List<Supply> listSupplies() {
        return supplyRepository.findAll();
    }
}
