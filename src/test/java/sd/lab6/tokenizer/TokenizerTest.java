package sd.lab6.tokenizer;

import org.junit.jupiter.api.Test;

import sd.lab6.exception.TokenizerException;
import sd.lab6.token.Brace;
import sd.lab6.token.NumberToken;
import sd.lab6.token.Operation;
import sd.lab6.token.Token;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class TokenizerTest {
    @Test
    public void testOk() throws IOException {
        assertOk("2 + 2", Arrays.asList(new NumberToken(2), Operation.PLUS, new NumberToken(2)));
        assertOk("1 + 2 * (3 + 4) - 5 / 6", Arrays.asList(
                new NumberToken(1),
                Operation.PLUS,
                new NumberToken(2),
                Operation.MUL,
                Brace.LEFT,
                new NumberToken(3),
                Operation.PLUS,
                new NumberToken(4),
                Brace.RIGHT,
                Operation.MINUS,
                new NumberToken(5),
                Operation.DIV,
                new NumberToken(6)
        ));
    }

    @Test
    public void testFail() {
        assertFail("hello");
        assertFail("a + b");
    }

    private void assertOk(String expr, List<Token> expected) throws IOException {
        try {
            assertEquals(expected, new Tokenizer(expr).parse());
        } catch (TokenizerException e) {
            fail(e);
        }
    }

    private void assertFail(String expr) {
        assertThrows(TokenizerException.class, () -> new Tokenizer(expr).parse());
    }
}
