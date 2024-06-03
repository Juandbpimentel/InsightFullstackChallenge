package br.ufc.quixada.SupplierApplicationInsight.types.dto.authentication.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import br.ufc.quixada.SupplierApplicationInsight.types.enums.UserRole;

public record RegisterRequestDTO(
        @NotNull(message = "name cannot be null or empty") String name,

        @NotNull(message = "email cannot be null or empty")
        @Email(message = "email must have a valid email format") String email,

        @NotNull(message = "password cannot be null or empty") String password,

        @NotNull(message = "role cannot be null or empty") UserRole role){
}
