package br.ufc.quixada.SupplierApplicationInsight.types.dto.user;

import br.ufc.quixada.SupplierApplicationInsight.types.enums.UserRole;

public record UserDTO(String id, String name, String email, UserRole role) {
}
