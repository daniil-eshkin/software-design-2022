package sd.lab6.exception;

import sd.lab6.token.Token;

public class CalcException extends RuntimeException {
    public CalcException(Token token) {
        super("Invalid token: " + token);
    }

    public CalcException(String message) {
        super(message);
    }
}
