import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Person extends Point {
    private City city;

    //假设人都在做三点运动：家 单位 食堂 单位 家 这个顺序 每个人的初始位置都在家里
    //并且都会在三个点待一定时间，具体是围绕着一个固定距离做圆周运动还是呆在那里还没想好
    //[0] 是x [1]是y

    int[] home  = new int[2];
    //这两个分别保存在moveTargets里的[0]和[1]里
    //int[] wordPlace= new int[2];
    //int[] restuarnt= new int[2];

    Random random = new Random();


    private List<MoveTarget> moveTargets  = new ArrayList<MoveTarget>();
    //0-home,1-workPlace,2-resturant
    //第一个目标点是workPlace
    int location = 1;
    //true 代表回家 false 代表出门
    boolean isBack = false;


    public Person(City city,int x,int y) {
        super(x, y);

        this.city = city;
        //没必要进行正态分布，随机分布即可
        home[0] = x;
        home[0] = y;

        MoveTarget home = new MoveTarget(x,y);
        moveTargets.add(home);
        //代表着person只能在三个点中进行运动，已经确定好了
        //第一次是单位的地址，第二次是食堂的地址；
        for(int i = 0;i<2;i++){
            int x_point = random.nextInt(Constants.CITY_WIDTH);
            int y_point = random.nextInt(Constants.CITY_HEIGHT);
            MoveTarget t = new MoveTarget(x_point,y_point);
            moveTargets.add(t);
        }

    }

    public interface State {
        int NORMAL = 0;//正常人，未感染的健康人
        int SUSPECTED = NORMAL + 1;//有暴露感染风险
        int SHADOW = SUSPECTED + 1;//潜伏期
        int CONFIRMED = SHADOW + 1;//发病且已确诊为感染病人
        int FREEZE = CONFIRMED + 1;//隔离治疗，禁止位移
        //已治愈出院的人转为NORMAL即可，否则会与作者通过数值大小判断状态的代码冲突
        int DEATH = FREEZE + 1;//病死者
    }



    //public boolean wantMove() {
       // return MathUtil.stdGaussian(sig, Constants.u) > 0;
    //}

    //初始状态为normal
    private int state = State.NORMAL;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    int infectedTime = 0;//感染时刻
    int confirmedTime = 0;//确诊时刻
    int dieMoment = 0;//死亡时刻，为0代表未确定，-1代表不会病死


    //是否被感染
    public boolean isInfected() {
        return state >= State.SHADOW;
    }

    //感染的时间
    public void beInfected() {
        state = State.SHADOW;
        infectedTime = MyPanel.worldTime;
    }

    //两点之间的直线距离
    public double distance(Person person) {
        return Math.sqrt(Math.pow(getX() - person.getX(), 2) + Math.pow(getY() - person.getY(), 2));
    }

    /**
     * 住院
     */
    //暂时还用不到
    private void freezy() {
        state = State.FREEZE;
    }


    //将如何运动
    private void action() {

        if (state == State.FREEZE || state == State.DEATH) {
            return;//如果处于隔离或者死亡状态，则无法行动
        }

        MoveTarget moveTarget = moveTargets.get(location);

        //计算运动位移
        int dX = moveTarget.getX() - getX();
        int dY = moveTarget.getY() - getY();

        double length = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));//与目标点的距离

        if (length < 1) {
            //判断是否到达目标点
            moveTarget.setArrived(true);

            //判断这个人位置的状态 以便于移动到下一个状态
            if(location == 0){
                location++;
                isBack = false;
            }else if(location == 1){
                if(isBack){
                    location--;
                }else{
                    location++;
                }
            }else if(location == 2){
                location--;
                isBack = true;
            }

            return;
        }

        int udX = (int) (dX / length);//x轴dX为位移量，符号为沿x轴前进方向, 即udX为X方向表示量
        if (udX == 0 && dX != 0) {
            if (dX > 0) {
                udX = 1;
            } else {
                udX = -1;
            }
        }


        int udY = (int) (dY / length);//y轴dY为位移量，符号为沿x轴前进方向，即udY为Y方向表示量
        //FIXED: 修正一处错误
        if (udY == 0 && dY != 0) {
            if (dY > 0) {
                udY = 1;
            } else {
                udY = -1;
            }
        }

        //横向运动边界
        if (getX() > Constants.CITY_WIDTH || getX() < 0) {
            moveTarget = null;
            if (udX > 0) {
                udX = -udX;
            }
        }
        //纵向运动边界
        if (getY() > Constants.CITY_HEIGHT || getY() < 0) {
            moveTarget = null;
            if (udY > 0) {
                udY = -udY;
            }
        }
        moveTo(udX, udY);

    }

    //public Bed useBed;

    private float SAFE_DIST = 2f;//安全距离

    //对各种状态的人进行不同的处理，更新发布市民健康状态
    public void update() {
        //@TODO找时间改为状态机

        if (state == State.FREEZE || state == State.DEATH) {
            return;//如果已经隔离或者死亡了，就不需要处理了
        }

        //处理已经确诊的感染者（即患者）
        if (state == State.CONFIRMED && dieMoment == 0) {

            int destiny = new Random().nextInt(10000) + 1;//幸运数字，[1,10000]随机数
            if (1 <= destiny && destiny <= (int) (Constants.FATALITY_RATE * 10000)) {

                //如果幸运数字落在死亡区间
                int dieTime = (int) MathUtil.stdGaussian(Constants.DIE_VARIANCE, Constants.DIE_TIME);
                dieMoment = confirmedTime + dieTime;//发病后确定死亡时刻
            } else {
                dieMoment = -1;//逃过了死神的魔爪

            }
        }

        //隔离区没加


        //增加一个正态分布用于潜伏期内随机发病时间
        double stdRnShadowtime = MathUtil.stdGaussian(25, Constants.SHADOW_TIME / 2);
        //处理发病的潜伏期感染者
        if (MyPanel.worldTime - infectedTime > stdRnShadowtime && state == State.SHADOW) {
            state = State.CONFIRMED;//潜伏者发病
            confirmedTime = MyPanel.worldTime;//刷新时间
        }
        //处理未隔离者的移动问题
        action();
        //处理健康人被感染的问题
        List<Person> people = PersonPool.getInstance().personList;
        if (state >= State.SHADOW) {
            return;
        }
        //通过一个随机幸运值和安全距离决定感染其他人
        for (Person person : people) {
            if (person.getState() == State.NORMAL) {
                continue;
            }
            float random = new Random().nextFloat();
            if (random < Constants.BROAD_RATE && distance(person) < SAFE_DIST) {
                this.beInfected();
                break;
            }
        }
    }
}
