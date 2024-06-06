package br.ufc.quixada.SupplierApplicationInsight.types.dto.user.response;

import br.ufc.quixada.SupplierApplicationInsight.types.dto.user.UserDTO;

public record UserMeUpdateResponseDTO(UserDTO user, String token) {
}
