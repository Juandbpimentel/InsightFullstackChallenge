package br.ufc.quixada.SupplierApplicationInsight.models;

import br.ufc.quixada.SupplierApplicationInsight.types.enums.SupplyType;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "suppliers")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Supplier {
    @Id
    @Generated(value = "uuid")
    private String id;

    @NotNull(message = "name is required")
    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotNull(message = "CNPJ is required")
    @NotBlank(message = "CNPJ cannot be blank")
    private String CNPJ;

    @NotNull(message = "phone is required")
    @NotBlank(message = "phone cannot be blank")
    private String phone;

    @NotNull(message = "email is required")
    @NotBlank(message = "email cannot be blank")
    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "address is required")
    @NotBlank(message = "address cannot be blank")
    private String address;

    @NotNull(message = "supplyType is required")
    private List<SupplyType> supplyTypes;

    public Supplier(String name, String CNPJ, String phone, String email, String address, List<SupplyType> supplyTypes) {
        this.name = name;
        this.CNPJ = CNPJ;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.supplyTypes = supplyTypes;
    }
}
