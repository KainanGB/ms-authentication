package users.msauthentication.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import users.msauthentication.entities.UsersEntity;
import users.msauthentication.exceptions.AuthenticationTokenNotFoundException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtTokenService {

    @Value("${api.security.token.secret}")
    private String JWT_SECRET;

    public String generateToken(UsersEntity user) {
        try {
            final Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.create()
                .withIssuer("authentication ms")
                .withSubject(user.getEmail())
                .withExpiresAt(generateExpirationTime())
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole().toString())
                .sign(algorithm);
        } catch (
            JWTCreationException exception) {
            throw new JWTCreationException("failed to generate jwt token", exception);
        }
    }

    public DecodedJWT validateToken(String token) throws AuthenticationTokenNotFoundException {
        try {
            final Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algorithm)
                .withIssuer("authentication ms")
                .build().verify(token);
        } catch (Exception exception) {
            throw new AuthenticationTokenNotFoundException();
        }
    }

    private Instant generateExpirationTime() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}