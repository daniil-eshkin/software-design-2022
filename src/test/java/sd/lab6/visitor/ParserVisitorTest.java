package sd.lab6.visitor;

import org.junit.jupiter.api.Test;
import sd.lab6.token.NumberToken;
import sd.lab6.token.Operation;
import sd.lab6.token.Token;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserVisitorTest {
    @Test
    public void testOk() {
        assertOk("2 + 2", Arrays.asList(new NumberToken(2), new NumberToken(2), Operation.PLUS));
        assertOk("1 + 2 * (3 + 4) - 5 / 66", Arrays.asList(
                new NumberToken(1),
                new NumberToken(2),
                new NumberToken(3),
                new NumberToken(4),
                Operation.PLUS,
                Operation.MUL,
                Operation.PLUS,
                new NumberToken(5),
                new NumberToken(66),
                Operation.DIV,
                Operation.MINUS
        ));
    }

    private void assertOk(String expr, List<Token> expected) {
        try {
            assertEquals(expected, new ParserVisitor().parse(expr));
        } catch (Exception e) {
            fail(e);
        }
    }
}
