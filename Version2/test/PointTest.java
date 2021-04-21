import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {

    @Test
    public void moveTo() {
        Point p = new Point(1,2);
        p.moveTo(2,2);

        assertEquals(p.getX(),3);
        assertEquals(p.getY(),4);

    }
}