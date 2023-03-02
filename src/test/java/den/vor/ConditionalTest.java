package den.vor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConditionalTest {

    private final Conditional conditional = new Conditional();

    @Test
    public void OneIsPositive() {
        assertTrue(conditional.isPositive(1));
    }

    @Test
    public void MinusOneIsNotPositive() {
        assertFalse(conditional.isPositive(-1));
    }

    @Test
    public void ZeroIsNotPositive() {
        assertFalse(conditional.isPositive(0));
    }
}
