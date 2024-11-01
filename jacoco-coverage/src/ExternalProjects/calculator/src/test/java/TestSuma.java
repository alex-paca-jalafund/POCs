import org.example.Suma;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSuma {

    @Test
    public void testSumar() {
        Assertions.assertEquals(15, Suma.sumar(10, 5), "10 + 5 should be 15");
        assertEquals(7, Suma.sumar(2, 5), "2 + 5 should be 7");
        assertEquals(14, Suma.sumar(7, 7), "7 + 7 should be 14");
    }
}
