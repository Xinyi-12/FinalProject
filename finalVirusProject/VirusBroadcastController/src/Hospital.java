import java.util.ArrayList;
import java.util.List;


public class Hospital extends Point {
    //    public static final int HOSPITAL_X = 880;
//    public static final int HOSPITAL_Y = 80;
    public static int HOSPITAL_X = 880;
    public static int HOSPITAL_Y = 80;

    private int width;
    private int height = 600;

    private Constants constants;

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }


//    private static Hospital hospital = new Hospital();
//
//    public static Hospital getInstance() {
//        return hospital;
//    }

    private Point point;//The coordinates of the first bed, used to set the absolute coordinates of the other beds
    private List<Bed> beds = new ArrayList<>();


    public List<Bed> getBeds() {
        return beds;
    }

    public Hospital(Constants constants, int sx, int sy) {
        //Hospital rectangle location coordinates

        super(sx, sy + 10);
        this.HOSPITAL_X = sx;
        this.HOSPITAL_Y = sy;
        point = new Point(sx, sy);

        this.constants = constants;
        //Adjusting the size of the hospital rectangle to the number of beds
        if (constants.getBedCount() == 0) {
            width = 0;
            height = 0;
        }
        //Calculate the width of the hospital according to the number of hospital beds
        //Because the height is set only 100 beds can be loaded
        int column = constants.getBedCount() / 100;
        width = column * 6;
        if (width < 60)
            width = 60;
        //Initialize the coordinates of the other beds based on the coordinates of the first bed
        for (int i = 0; i < column; i++) {

            for (int j = 10; j <= 606; j += 6) {

                Bed bed = new Bed(point.getX() + i * 6, point.getY() + j);
                beds.add(bed);
                if (beds.size() >= constants.getBedCount()) {//Determining the number of hospital beds to be carried
                    break;
                }
            }

        }
    }


    public Bed pickBed() {
        for (Bed bed : beds) {
            if (bed.isEmpty()) {
                return bed;
            }
        }
        return null;
    }


    public Bed returnBed(Bed bed) {
        if (bed != null) {
            bed.setEmpty(true);
        }
        return bed;
    }
}
