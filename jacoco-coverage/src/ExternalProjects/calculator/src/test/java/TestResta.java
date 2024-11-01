import org.example.Resta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestResta {

    @Test
    public void testRestar() {
        Assertions.assertEquals(5, Resta.restar(10, 5), "10 - 5 should be 5");
        assertEquals(-3, Resta.restar(2, 5), "2 - 5 should be -3");
        assertEquals(0, Resta.restar(7, 7), "7 - 7 should be 0");
    }
}

