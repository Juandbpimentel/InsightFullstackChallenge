package br.ufc.quixada.SupplierApplicationInsight.types.dto.supplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record SupplierUpdateRequestDTO(String name, String phone, String email, String address,
                                       List<String> supplyTypes) {
    public Map<String, Object> toMap() {
        Map<String, Object> supplierFields = new HashMap<>();
        if (name != null)
            supplierFields.put("name", name);
        if (phone != null)
            supplierFields.put("phone", phone);
        if (email != null)
            supplierFields.put("email", email);
        if (address != null)
            supplierFields.put("address", address);
        if (supplyTypes != null)
            supplierFields.put("supplyTypes", supplyTypes);
        return supplierFields;
    }
}
