package br.ufc.quixada.SupplierApplicationInsight.types.dto.supply;

import br.ufc.quixada.SupplierApplicationInsight.types.enums.SupplyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SupplyCreateRequestDTO(

        @NotNull(message = "name is required")
        @NotBlank(message = "name cannot be blank")
        String name,

        @NotNull(message = "supplierId is required")
        @NotBlank(message = "supplierId cannot be blank")
        String supplierId,

        @NotNull(message = "description is required")
        @NotBlank(message = "description cannot be blank")
        String description,

        @NotNull(message = "supplyDate is required")
        @NotBlank(message = "supplyDate cannot be blank")
        String supplyDate,

        @NotNull(message = "supplyType is required")
        SupplyType supplyType
) {
}
