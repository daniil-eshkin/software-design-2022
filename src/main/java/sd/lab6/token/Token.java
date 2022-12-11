package sd.lab6.token;

import sd.lab6.visitor.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor);
}
