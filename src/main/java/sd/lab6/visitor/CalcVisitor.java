package sd.lab6.visitor;

import lombok.SneakyThrows;
import sd.lab6.exception.CalcException;
import sd.lab6.token.Brace;
import sd.lab6.token.NumberToken;
import sd.lab6.token.Operation;
import sd.lab6.token.Token;
import sd.lab6.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;

public class CalcVisitor implements TokenVisitor {
    private final List<Integer> stack = new ArrayList<>();

    @Override
    public void visit(NumberToken token) {
        stack.add(token.getValue());
    }

    @Override
    public void visit(Brace token) {
        throw new CalcException(token);
    }

    @Override
    public void visit(Operation token) {
        if (stack.size() < 2) {
            throw new CalcException("Invalid number of args for operation: " + token);
        }
        int b = stack.get(stack.size() - 1);
        stack.remove(stack.size() - 1);
        int a = stack.get(stack.size() - 1);
        stack.remove(stack.size() - 1);
        switch (token) {
            case PLUS -> stack.add(a + b);
            case MINUS -> stack.add(a - b);
            case MUL -> stack.add(a * b);
            case DIV -> {
                if (b == 0) {
                    // Restore stack for exception safety
                    stack.add(a);
                    stack.add(b);
                    throw new CalcException("Division by zero");
                } else {
                    stack.add(a / b);
                }
            }
        }
    }

    @SneakyThrows
    public int calc(String expr) {
        return calc(new ParserVisitor().parse(new Tokenizer(expr).parse()));
    }

    public int calc(List<Token> tokens) {
        tokens.forEach(t -> t.accept(this));
        return getResult();
    }

    public int getResult() {
        if (stack.size() != 1) {
            throw new CalcException("Expression is not complete");
        }
        return stack.get(0);
    }
}
