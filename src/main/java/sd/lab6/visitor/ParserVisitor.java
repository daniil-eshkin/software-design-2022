package sd.lab6.visitor;

import lombok.SneakyThrows;
import sd.lab6.exception.ParserException;
import sd.lab6.token.Brace;
import sd.lab6.token.NumberToken;
import sd.lab6.token.Operation;
import sd.lab6.token.Token;
import sd.lab6.tokenizer.Tokenizer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ParserVisitor implements TokenVisitor {
    private final Queue<Token> tokens = new ArrayDeque<>();
    private final List<Token> operators = new ArrayList<>();

    @SneakyThrows
    public List<Token> parse(String expr) {
        return parse(new Tokenizer(expr).parse());
    }

    public List<Token> parse(List<Token> tokens) {
        tokens.forEach(t -> t.accept(this));
        return getTokens();
    }

    public List<Token> getTokens() {
        while (!operators.isEmpty()) {
            Token t = operators.get(operators.size() - 1);
            if (t.equals(Brace.LEFT)) {
                throw new ParserException(t);
            } else {
                tokens.add(t);
                operators.remove(operators.size() - 1);
            }
        }
        return new ArrayList<>(tokens);
    }

    @Override
    public void visit(NumberToken token) {
        tokens.add(token);
    }

    @Override
    public void visit(Brace token) {
        if (token.equals(Brace.LEFT)) {
            operators.add(token);
        } else {
            while (true) {
                if (operators.isEmpty()) {
                    throw new ParserException(token);
                }
                if (!operators.get(operators.size() - 1).equals(Brace.LEFT)) {
                    tokens.add(operators.get(operators.size() - 1));
                    operators.remove(operators.size() - 1);
                } else {
                    operators.remove(operators.size() - 1);
                    break;
                }
            }
        }
    }

    @Override
    public void visit(Operation token) {
        while (!operators.isEmpty()) {
            Token t = operators.get(operators.size() - 1);
            if (!t.equals(Brace.LEFT) && priority(token) <= priority((Operation) t)) {
                tokens.add(t);
                operators.remove(operators.size() - 1);
            } else {
                break;
            }
        }
        operators.add(token);
    }

    private int priority(Operation operation) {
        switch (operation) {
            case PLUS, MINUS -> {
                return 0;
            }
            case MUL, DIV -> {
                return 1;
            }
            default -> {
                return -1;
            }
        }
    }
}
