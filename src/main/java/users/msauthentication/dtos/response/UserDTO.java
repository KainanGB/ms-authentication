package users.msauthentication.dtos.response;


public record UserDTO(
        Long id,
        String email,
        String created_at
) {
}
