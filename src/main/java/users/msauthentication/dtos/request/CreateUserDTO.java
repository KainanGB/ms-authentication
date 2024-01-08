package users.msauthentication.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import users.msauthentication.dtos.Role;

import java.util.Objects;

public record CreateUserDTO(
    @Email(message = "email is not valid")
    String email,
    @NotNull
    @NotEmpty(message = "password must not be empty")
    String password,
    Role role
) {
    public CreateUserDTO(String email, String password, Role role) {
        this.email = email;
        this.password = encodePassword(password);
        this.role = Objects.requireNonNullElse(role, Role.DEFAULT);
    }

    private String encodePassword(String password) {
        var encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}

