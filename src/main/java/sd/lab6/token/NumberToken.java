package sd.lab6.token;

import lombok.Data;
import sd.lab6.visitor.TokenVisitor;

@Data
public class NumberToken implements Token {
    private final int value;

    public NumberToken(int value) {
        if (value < 0) {
            throw new IllegalArgumentException(String.valueOf(value));
        }
        this.value = value;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format("NUMBER(%d)", value);
    }
}
