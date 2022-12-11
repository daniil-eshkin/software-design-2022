package sd.lab6.visitor;

import sd.lab6.token.Brace;
import sd.lab6.token.NumberToken;
import sd.lab6.token.Operation;

public interface TokenVisitor {
    void visit(NumberToken token);
    void visit(Brace token);
    void visit(Operation token);
}
