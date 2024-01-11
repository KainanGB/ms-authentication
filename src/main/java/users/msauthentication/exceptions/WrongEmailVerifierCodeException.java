package users.msauthentication.exceptions;

public class WrongEmailVerifierCodeException extends Exception {
    public WrongEmailVerifierCodeException(String s) {
        super(s);
    }
}
