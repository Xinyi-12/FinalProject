import org.junit.Test;

import static org.junit.Assert.*;

public class HospitalTest {

    Hospital hospital = new Hospital();

    @Test
    public void pickBed() {
        Bed bed = hospital.pickBed();
        assertNotNull(bed);

    }

    @Test
    public void returnBed() {

        Bed bed = hospital.pickBed();
        hospital.returnBed(bed);
        assertEquals(bed.isEmpty(),true);
    }
}