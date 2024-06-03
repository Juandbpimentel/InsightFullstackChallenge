package br.ufc.quixada.SupplierApplicationInsight.models;

import jakarta.annotation.Generated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import br.ufc.quixada.SupplierApplicationInsight.types.enums.UserRole;

import java.util.Collection;
import java.util.List;

@Document(collection = "users")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @Generated(value = "uuid")
    private String id;

    @NotNull(message = "name is required")
    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotNull(message = "email is required")
    @Email(message = "invalid email")
    private String email;

    @NotNull(message = "password is required")
    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotNull(message = "role is required")
    private UserRole role;

    public User( String name, String email, String password, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (this.role == UserRole.ADMIN)
                ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_USER"))
                : List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
