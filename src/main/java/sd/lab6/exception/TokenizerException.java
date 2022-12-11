package sd.lab6.exception;

public class TokenizerException extends RuntimeException {
    public TokenizerException(int pos, char c) {
        super(String.format("Invalid character at position %d: %c", pos, c));
    }
}
