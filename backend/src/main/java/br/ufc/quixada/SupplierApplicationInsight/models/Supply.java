package br.ufc.quixada.SupplierApplicationInsight.models;

import br.ufc.quixada.SupplierApplicationInsight.types.enums.SupplyType;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "supplies")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Supply {
    @Id
    @Generated(value = "uuid")
    private String id;

    @NotNull(message = "supplierId is required")
    @NotBlank(message = "supplierId cannot be blank")
    private String supplierId;

    @NotNull(message = "name is required")
    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotNull(message = "description is required")
    @NotBlank(message = "description cannot be blank")
    private String description;

    @NotNull(message = "date is required")
    @NotBlank(message = "date cannot be blank")
    private String supplyDate;

    @NotNull(message = "supplyType is required")
    @NotBlank(message = "supplyType cannot be blank")
    private SupplyType supplyType;

    public Supply(String name, String supplierId, String description, String supplyDate, SupplyType supplyType) {
        this.name = name;
        this.supplierId = supplierId;
        this.description = description;
        this.supplyDate = supplyDate;
        this.supplyType = supplyType;
    }
}
