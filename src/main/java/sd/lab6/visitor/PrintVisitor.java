package sd.lab6.visitor;

import sd.lab6.token.Brace;
import sd.lab6.token.NumberToken;
import sd.lab6.token.Operation;
import sd.lab6.token.Token;

import java.util.List;

public class PrintVisitor implements TokenVisitor {
    @Override
    public void visit(NumberToken token) {
        System.out.print(token);
    }

    @Override
    public void visit(Brace token) {
        System.out.print(token);
    }

    @Override
    public void visit(Operation token) {
        System.out.print(token);
    }

    public void println(List<Token> tokens) {
        tokens.forEach(t -> {
            t.accept(this);
            System.out.print(' ');
        });
        System.out.println();
    }
}
