package sd.lab6.exception;

import sd.lab6.token.Token;

public class ParserException extends RuntimeException {
    public ParserException(Token token) {
        super("Invalid token: " + token);
    }
}
