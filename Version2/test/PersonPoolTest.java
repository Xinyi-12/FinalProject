import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PersonPoolTest {

    PersonPool pool = new PersonPool();

    @Test
    public void getPersonList() {
        List<Person> list = PersonPool.getInstance().getPersonList();
        assertNotNull(list);

    }

    @Test
    public void getPeopleSize() {

        assertEquals(PersonPool.getInstance().getPeopleSize(Person.State.NORMAL),5000);
        assertEquals(PersonPool.getInstance().getPeopleSize(Person.State.SHADOW),0);
    }
}