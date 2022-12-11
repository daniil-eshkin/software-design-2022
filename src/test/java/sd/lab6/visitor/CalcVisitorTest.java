package sd.lab6.visitor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalcVisitorTest {
    @Test
    public void testOk() {
        assertOk("2 + 2", 4);
        assertOk("1 + 2 * (3 + 4) - 5 / 66", 15);
    }

    private void assertOk(String expr, int expected) {
        try {
            assertEquals(expected, new CalcVisitor().calc(expr));
        } catch (Exception e) {
            fail(e);
        }
    }
}
