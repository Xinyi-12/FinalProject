import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PersonPoolTest {

    Constants constants = new Constants();
    Hospital hospital = new Hospital(constants,100,100);
    PersonPool personPool = new PersonPool(constants,hospital);

    @Test
    public void getPersonList() {
        List<Person> list = personPool.getPersonList();
        assertNotNull(list);

    }

    @Test
    public void getPeopleSize() {

        assertEquals(personPool.getPeopleSize(Person.State.NORMAL),0);
        assertEquals(personPool.getPeopleSize(Person.State.SHADOW),0);
    }
}