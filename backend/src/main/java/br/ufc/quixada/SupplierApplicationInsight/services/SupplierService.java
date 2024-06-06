package br.ufc.quixada.SupplierApplicationInsight.services;

import br.ufc.quixada.SupplierApplicationInsight.models.Supplier;
import br.ufc.quixada.SupplierApplicationInsight.repositories.SupplierRepository;
import br.ufc.quixada.SupplierApplicationInsight.types.enums.SupplyType;
import br.ufc.quixada.SupplierApplicationInsight.types.exceptions.SupplierAlreadyExistsException;
import br.ufc.quixada.SupplierApplicationInsight.types.exceptions.SupplierNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier createSupplier(Supplier supplier) {
        if (supplierRepository.findByCNPJOrEmail(supplier.getCNPJ(), supplier.getEmail()).isPresent())
            throw new SupplierAlreadyExistsException("Supplier already exists");
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(String id, Map<String, Object> updateFields) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new SupplierNotFoundException("Supplier does not exist"));
        if (updateFields.isEmpty()) {
            throw new IllegalArgumentException("No fields to update");
        }

        supplier.setId(id);
        updateFields.forEach((key, value) -> {
            switch (key) {
                case "name":
                    supplier.setName((String) value);
                    break;
                case "email":
                    supplier.setEmail((String) value);
                    break;
                case "phone":
                    supplier.setPhone((String) value);
                    break;
                case "address":
                    supplier.setAddress((String) value);
                    break;
                case "supplyTypes":
                    List<?> supplyTypesRaw = (List<?>) value;
                    List<SupplyType> supplyTypeList = supplyTypesRaw.stream().map(supplyType -> SupplyType.valueOf((String) supplyType)).toList();
                    supplier.setSupplyTypes(supplyTypeList);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });
        return supplierRepository.save(supplier);
    }

    public void deleteSupplierById(String id) {
        // Delete the supplier
        Optional<Supplier> supplierOptional = supplierRepository.findById(id);
        if (supplierOptional.isEmpty())
            throw new SupplierNotFoundException("Supplier does not exist");
        supplierRepository.deleteById(id);
    }

    public Supplier getSupplierById(String id) {
        // Get the supplier by id
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isEmpty())
            throw new SupplierNotFoundException("Supplier does not exist");
        return supplier.get();
    }

    public List<Supplier> listSuppliers() {
        // Get all suppliers
        return supplierRepository.findAll();
    }
}
