package users.msauthentication.dtos;

public record Message<T>(
    String correlationId,
    T payload
) {


}
