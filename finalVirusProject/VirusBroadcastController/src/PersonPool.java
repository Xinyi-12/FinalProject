import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PersonPool {
//    private static PersonPool personPool = new PersonPool();
//
//    public static PersonPool getInstance() {
//        return personPool;
//    }

    List<Person> personList = new ArrayList<Person>();

    public List<Person> getPersonList() {
        return personList;
    }

    private Constants constants;

    private Hospital hospital;

    private MyPanel myPanel;

    public MyPanel getMyPanel() {
        return myPanel;
    }

    public void setMyPanel(MyPanel myPanel) {
        this.myPanel = myPanel;
    }

    public int getPeopleSize(int state) {
        if (state == -1) {
            return personList.size();
        }
        int i = 0;
        for (Person person : personList) {
            if (person.getState() == state) {
                i++;
            }
        }
        return i;
    }


    public PersonPool(Constants constants, Hospital hospital) {
        this.constants = constants;
        this.hospital = hospital;
    }

    public void initPersonPool(int cityCenterX, int cityCenterY) {
        City city = new City(cityCenterX, cityCenterY);//设置城市中心为坐标(400,400)
        //添加城市居民
        for (int i = 0; i < constants.getCityPersonSize(); i++) {
            Random random = new Random();
            //产生N(a,b)的数：Math.sqrt(b)*random.nextGaussian()+a
            int x = (int) (100 * random.nextGaussian() + city.getCenterX());
            int y = (int) (100 * random.nextGaussian() + city.getCenterY());
            if (x > cityCenterX * 2) {
                x = cityCenterX * 2;
            }
            Person p = new Person(city, x, y);
            p.setConstants(constants);
            p.setPersonPool(this);
            p.setHospital(hospital);
            p.setMyPanel(myPanel);
            personList.add(p);
        }
    }
}
