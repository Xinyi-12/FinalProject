import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class PersonTest {




    @Test
    public void personTest(){
        City city = new City(400,400);
        Person person  = new Person(city, 234,255);
        assertEquals(person.getTargetsSize(),3);
    }


    @Test
    public void isInfected() {
        City city = new City(400,400);
        Person person  = new Person(city, 234,255);
        //person.setState(Person.State.SHADOW);
        assertEquals(person.isInfected(),false);
        person.setState(Person.State.SHADOW);
        assertEquals(person.isInfected(),true);

    }

    @Test
    public void distance() {
        City city = new City(400,400);
        Person person1  = new Person(city, 234,255);
        Person person2 = new Person(city, 233,255);
        assertEquals((Double)person1.distance(person2),(Double) 1.0);

    }

    @Test
    public void action1() {
        City city = new City(400,400);
        Person person  = new Person(city, 234,255);
        person.setState(Person.State.DEATH);
        person.action();
        assertEquals(person.getX(),234);
        assertEquals(person.getY(),255);


    }
    @Test
    public void action2() {
        //处于位置0
        City city = new City(400,400);
        Person person  = new Person(city, 234,255);
        assertEquals(person.getLocation(),1);
        person.action();
        //MoveTarget work = person.getMoveTargets().get(1);
        assertNotEquals(person.getX(),234);
        assertNotEquals(person.getY(),255);

    }

    @Test
    public void action3() {
        City city = new City(400,400);
        Person person  = new Person(city, 234,255);
        MoveTarget work = person.getMoveTargets().get(1);
        person.setX(work.getX());
        person.setY(work.getY());
        assertEquals(person.isBack,false);
        assertEquals(person.getLocation(),1);
        person.action();
        assertEquals(person.getLocation(),2);
        person.action();
        assertNotEquals(person.getX(),work.getX());
        assertNotEquals(person.getY(),work.getY());

    }

    @Test
    public void action4() {
        //处于位置2
        City city = new City(400,400);
        Person person  = new Person(city, 234,255);
        person.setLocation(2);
        MoveTarget food = person.getMoveTargets().get(2);
        person.setX(food.getX());
        person.setY(food.getY());
        //person.action();
        assertEquals(person.isBack,false);
        assertEquals(person.getLocation(),2);
        person.action();
        assertEquals(person.isBack,true);
        assertEquals(person.getLocation(),1);
        person.action();
        assertNotEquals(person.getX(),food.getX());
        assertNotEquals(person.getY(),food.getY());
    }

    public static List<Person> people = PersonPool.getInstance().getPersonList();//获取所有的市民


    public static void initInfected() {

        for (int i = 0; i < Constants.ORIGINAL_COUNT; i++) {
            Person person;
            do {
                person = people.get(new Random().nextInt(people.size() - 1));//随机挑选一个市民
            } while (person.isInfected());//如果该市民已经被感染，重新挑选
            person.beInfected();//让这个幸运的市民成为感染者
        }
    }


    @Test
    public void deathAction(){
        City city = new City(400,400);
        Person person  = new Person(city, 234,255);
        person.setState(Person.State.DEATH);
        person.update();
        assertEquals(person.getX(),234);
        assertEquals(person.getY(),255);

    }

    @Test
    public void normalAction() {
        City city = new City(400,400);
        Person person  = new Person(city, 234,255);
        person.update();
        assertNotEquals(person.getX(),234);
        assertNotEquals(person.getY(),255);

    }


    @Test
    public void beInfected() {
        City city = new City(400,400);
        Person person  = new Person(city, 234,255);
        person.beInfected();
        assertEquals(person.getState(), Person.State.SHADOW);
        assertEquals(person.getConfirmLocation()[0],234);
        assertEquals(person.getConfirmLocation()[1],255);

    }

    @Test
    public void shadowAction() {
        City city = new City(400,400);
        Person person  = new Person(city, 234,255);
        person.beInfected();
        person.shadowAction();
        assertNotEquals(person.getState(), Person.State.CONFIRMED);

    }

    @Test
    public void confirmAction() {

        City city = new City(400,400);
        Person person  = new Person(city, 234,255);
        person.beInfected();
        person.setState(Person.State.CONFIRMED);
        person.confirmAction();
        assertNotEquals(person.getDieMoment(),0);

    }

    @Test
    public void freezeAction() {
        initInfected();
        City city = new City(400,400);
        Person person  = new Person(city, 234,255);
        person.beInfected();
        person.setState(Person.State.CONFIRMED);
        person.freezeAction();
        Bed bed = Hospital.getInstance().pickBed();//查找空床位
        person.useBed  = bed;
        assertNotEquals(person.useBed,null);

    }

    @Test
    public void traceTest() {
        initInfected();
        City city = new City(400,400);
        Person person  = new Person(city, 234,255);
        person.beInfected();
        person.setState(Person.State.CONFIRMED);
        person.trace();
        assertNotEquals(person.isTrack(),true);

    }
}