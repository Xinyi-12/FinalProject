import org.junit.Test;

import static org.junit.Assert.*;

public class HospitalTest {

    Constants constants = new Constants();
    Hospital hospital = new Hospital(constants,100,100);

    @Test
    public void pickBed() {
        Constants constants = new Constants();
        constants.setBedCount(100);
        Hospital hospital = new Hospital(constants,100,100);

        Bed bed = hospital.pickBed();
        assertNotNull(bed);

    }

    @Test
    public void returnBed() {
        Constants constants = new Constants();
        constants.setBedCount(100);
        Hospital hospital = new Hospital(constants,100,100);

        Bed bed = hospital.pickBed();
        hospital.returnBed(bed);
        assertEquals(bed.isEmpty(),true);
    }
}