package den.vor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SumTest {

    @Test
    public void sumTest() {
        assertEquals(new Sum().sum(-2, 2), 0);
    }
}
