import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TravelCalTest{

    @Test
    public void testCalSpeed_NormalValues() {
        double result = TravelCal.calSpeed(100.0, 2.0);
        assertEquals(50.0, result);
    }

    @Test
    void testCalSpeed_DecimalValues() {
        double result = TravelCal.calSpeed(150.5, 3.0);
        assertEquals(50.1667, result, 0.0001); // delta for double comparison
    }

    @Test
    void testCalSpeed_ZeroDistance() {
        double result = TravelCal.calSpeed(0.0, 5.0);
        assertEquals(0.0, result);
    }


}