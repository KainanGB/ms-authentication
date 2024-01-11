package users.msauthentication.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import users.msauthentication.dtos.request.CreateUserDTO;
import users.msauthentication.dtos.request.VerifyEmailDTO;
import users.msauthentication.dtos.response.UserDTO;
import users.msauthentication.dtos.response.VerifyEmailResponseDTO;
import users.msauthentication.entities.UsersEntity;
import users.msauthentication.exceptions.UserAlreadyVerifiedException;
import users.msauthentication.exceptions.WrongEmailVerifierCodeException;
import users.msauthentication.services.UsersService;


@RestController
@AllArgsConstructor
@RequestMapping("users")
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid CreateUserDTO data) {
        return ResponseEntity.ok(usersService.register(data));
    }

    @GetMapping("/me/{id}")
    @CircuitBreaker(name = "ms-authentication")
    public ResponseEntity<UsersEntity> getById(@PathVariable Long id) throws JWTVerificationException {
        return ResponseEntity.ok(usersService.getById(id));
    }

    @GetMapping("/fallback")
    public String fallback(@Value("${local.server.port}") String port) {
        return "fallback in " + port;
    }

    @PutMapping("/verify")
    public ResponseEntity<VerifyEmailResponseDTO> verifyEmail(@RequestBody @Valid VerifyEmailDTO request)
        throws UserAlreadyVerifiedException,
        WrongEmailVerifierCodeException {
        usersService.verifyEmail(request);
        return ResponseEntity.ok(new VerifyEmailResponseDTO("verified"));

    }

}
