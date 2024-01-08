package users.msauthentication.controllers.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import users.msauthentication.entities.UsersEntity;
import users.msauthentication.exceptions.AuthenticationTokenNotFoundException;
import users.msauthentication.repositories.UsersRepository;
import users.msauthentication.services.JwtTokenService;

import java.io.IOException;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    JwtTokenService jwtTokenService;
    UsersRepository usersRepository;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

        if (request.getHeader("Authorization") == null) {
            filterChain.doFilter(request, response);
            return;
        }

        final DecodedJWT verifiedToken;
        final String token;
        try {
            token = retrieveTokenFromHeader(request);
            verifiedToken = jwtTokenService.validateToken(token);
        } catch (AuthenticationTokenNotFoundException e) {
            throw new RuntimeException(e);
        }

        final UsersEntity user = (UsersEntity) usersRepository.findByEmail(verifiedToken.getSubject());

        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String retrieveTokenFromHeader(HttpServletRequest request) throws AuthenticationTokenNotFoundException {
        final String token = request.getHeader("Authorization");

        if (token == null) {
            throw new AuthenticationTokenNotFoundException();
        }

        return token.replace("Bearer", "");
    }
}
