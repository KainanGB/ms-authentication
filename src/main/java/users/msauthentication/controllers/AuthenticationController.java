package users.msauthentication.controllers;


import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import users.msauthentication.dtos.request.AuthenticateUserDTO;
import users.msauthentication.dtos.response.AuthenticateDTO;
import users.msauthentication.entities.UsersEntity;
import users.msauthentication.services.JwtTokenService;

@RestController
@AllArgsConstructor
@RequestMapping("users/login")
public class AuthenticationController {

    private final AuthenticationManager manager;

    private final JwtTokenService tokenService;

    @PostMapping
    public ResponseEntity<AuthenticateDTO> login(@RequestBody @Valid AuthenticateUserDTO data) {
        final var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        final var auth = manager.authenticate(usernamePassword);
        final var authUser = (UsersEntity) auth.getPrincipal();
        final var token = tokenService.generateToken(authUser);
        final AuthenticateDTO authenticateDTO = new AuthenticateDTO(token);
        return ResponseEntity.ok(authenticateDTO);
    }

    @GetMapping
    public ResponseEntity<Boolean> verifyToken() throws JWTVerificationException {
        return ResponseEntity.ok(true);
    }

}
