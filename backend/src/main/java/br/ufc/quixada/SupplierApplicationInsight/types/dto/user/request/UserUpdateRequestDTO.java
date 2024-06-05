package br.ufc.quixada.SupplierApplicationInsight.types.dto.user.request;

import java.util.HashMap;
import java.util.Map;

public record UserUpdateRequestDTO(String name, String email, String password, String role) {
    public Map<String, Object> toMap() {
        Map <String, Object> userFields = new HashMap<>();
        if (role != null)
            userFields.put("role", role);
        if (name != null)
            userFields.put("name", name);
        if (email != null)
            userFields.put("email", email);
        if (password != null)
            userFields.put("password", password);
        return userFields;
    }
}
