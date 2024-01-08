package users.msauthentication.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import users.msauthentication.dtos.Role;

public record AuthenticateUserDTO(
    @Email(message = "email is not valid")
    String email,
    @NotNull
    @NotEmpty(message = "password must not be empty")
    String password
) {
}

