package br.ufc.quixada.SupplierApplicationInsight.types.dto.supply;

import br.ufc.quixada.SupplierApplicationInsight.types.enums.SupplyType;

import java.util.HashMap;
import java.util.Map;

public record SupplyUpdateRequestDTO(
        String name,
        String description,
        String supplyDate,
        SupplyType supplyType
) {
    public Map<String, Object> toMap() {
        Map<String, Object> supplyFields = new HashMap<>();
        if (name != null)
            supplyFields.put("name", name);
        if (description != null)
            supplyFields.put("description", description);
        if (supplyDate != null)
            supplyFields.put("supplyDate", supplyDate);
        if (supplyType != null)
            supplyFields.put("supplyType", supplyType);
        return supplyFields;
    }
}
