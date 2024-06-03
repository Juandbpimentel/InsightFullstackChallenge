package br.ufc.quixada.SupplierApplicationInsight.types.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import br.ufc.quixada.SupplierApplicationInsight.models.User;
import br.ufc.quixada.SupplierApplicationInsight.types.enums.UserRole;

public record UserMeUpdateRequestDTO(@NotNull @NotBlank String name, @NotNull @NotBlank String email, @NotNull @NotBlank String password, @NotNull @NotBlank String role) {
    public User toUser() {
        return new User(null, name, email, password, UserRole.valueOf(role));
    }
}
