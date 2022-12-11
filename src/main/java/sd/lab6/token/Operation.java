package sd.lab6.token;

import sd.lab6.visitor.TokenVisitor;

public enum Operation implements Token {
    PLUS, MINUS, MUL, DIV;

    public static Operation of(char c) {
        switch (c) {
            case '+' -> {
                return PLUS;
            }
            case '-' -> {
                return MINUS;
            }
            case '*' -> {
                return MUL;
            }
            case '/' -> {
                return DIV;
            }
            default -> throw new IllegalArgumentException(String.valueOf(c));
        }
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
