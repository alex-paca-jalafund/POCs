import org.example.Suma;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SumaTest {

    @Test
    public void test() {
        assertEquals(3,Suma.sumar(1,2));
    }
}
