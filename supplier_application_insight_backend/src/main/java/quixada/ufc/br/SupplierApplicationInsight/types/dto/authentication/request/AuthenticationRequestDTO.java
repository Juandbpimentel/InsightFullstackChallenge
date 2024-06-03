package quixada.ufc.br.SupplierApplicationInsight.types.dto.authentication.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthenticationRequestDTO(
        @NotNull(message = "Email cannot be null or empty")
        @Email(message = "Email must be valid") String email,

        @NotNull(message = "Password cannot be null or empty")
        String password){
}
