package br.ufc.quixada.SupplierApplicationInsight.types.dto.authentication.response;

import br.ufc.quixada.SupplierApplicationInsight.types.dto.user.UserDTO;

public record AuthenticationResponseDTO(String token, UserDTO user) {
}
