package br.ufc.quixada.SupplierApplicationInsight.types.dto.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SupplierCreateRequestDTO(
        @NotNull(message = "name is required")
        @NotBlank(message = "name cannot be blank") String name,

        @NotNull(message = "CNPJ is required")
        @NotBlank(message = "CNPJ cannot be blank") String CNPJ,

        @NotNull(message = "phone is required")
        @NotBlank(message = "phone cannot be blank") String phone,

        @NotNull(message = "email is required")
        @NotBlank(message = "email cannot be blank")
        @Email(message = "Invalid email") String email,

        @NotNull(message = "address is required")
        @NotBlank(message = "address cannot be blank") String address,

        @NotNull(message = "supplyTypes is required") List<String> supplyTypes) {
}
