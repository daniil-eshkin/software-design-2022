package sd.lab6.token;

import sd.lab6.visitor.TokenVisitor;

public enum Brace implements Token {
    LEFT, RIGHT;

    public static Brace of(char c) {
        switch (c) {
            case '(' -> {
                return LEFT;
            }
            case ')' -> {
                return RIGHT;
            }
            default -> throw new IllegalArgumentException(String.valueOf(c));
        }
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
