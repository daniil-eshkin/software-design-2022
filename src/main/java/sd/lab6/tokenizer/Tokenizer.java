package sd.lab6.tokenizer;

import sd.lab6.exception.TokenizerException;
import sd.lab6.token.Brace;
import sd.lab6.token.NumberToken;
import sd.lab6.token.Operation;
import sd.lab6.token.Token;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private int pos;
    private State state;
    private final Reader reader;
    private final List<Token> tokens = new ArrayList<>();
    private final StringBuilder numBuilder = new StringBuilder();
    private int current;

    public Tokenizer(Reader reader) {
        this.reader = reader;
        this.state = State.START;
    }

    public Tokenizer(String input) {
        this(new StringReader(input));
    }

    public List<Token> parse() throws TokenizerException, IOException {
        while (true) {
            switch (state) {
                case START -> {
                    read();
                    if (current == -1) {
                        state = State.END;
                        return tokens;
                    }
                    if (Character.isWhitespace(curChar())) {
                        continue;
                    }
                    switch (curChar()) {
                        case '+', '-', '*', '/' -> tokens.add(Operation.of(curChar()));
                        case '(', ')' -> tokens.add(Brace.of(curChar()));
                        case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                            numBuilder.append(curChar());
                            state = State.NUMBER;
                        }
                        default -> {
                            state = State.ERROR;
                            throw new TokenizerException(pos, curChar());
                        }
                    }
                }
                case NUMBER -> {
                    read();
                    if (current == -1) {
                        addNum();
                        state = State.END;
                        return tokens;
                    }
                    if (Character.isWhitespace(curChar())) {
                        addNum();
                        state = State.START;
                        continue;
                    }
                    switch (curChar()) {
                        case '+', '-', '*', '/' -> {
                            addNum();
                            tokens.add(Operation.of(curChar()));
                            state = State.START;
                        }
                        case '(', ')' -> {
                            addNum();
                            tokens.add(Brace.of(curChar()));
                            state = State.START;
                        }
                        case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> numBuilder.append(curChar());
                        default -> {
                            state = State.ERROR;
                            throw new TokenizerException(pos, curChar());
                        }
                    }
                }
                default -> {
                    return tokens;
                }
            }
        }
    }

    private void read() throws IOException {
        current = reader.read();
        pos++;
    }

    private char curChar() {
        return (char)current;
    }

    private void addNum() {
        tokens.add(new NumberToken(Integer.parseInt(numBuilder.toString())));
        numBuilder.delete(0, numBuilder.length());
    }

    private enum State {
        START, NUMBER, END, ERROR
    }
}
