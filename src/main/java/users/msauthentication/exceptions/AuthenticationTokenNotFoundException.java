package users.msauthentication.exceptions;

public class AuthenticationTokenNotFoundException extends Throwable {
    public AuthenticationTokenNotFoundException() {
        super("authorization token not found");
    }
}
