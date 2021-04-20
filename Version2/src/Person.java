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
    //感染的地方
    int[] confirmLocation = new int[2];

    Random random = new Random();


    private List<MoveTarget> moveTargets  = new ArrayList<MoveTarget>();
    //0-home,1-workPlace,2-resturant
    //第一个目标点是workPlace
    int location = 1;
    //true 代表回家 false 代表出门
    boolean isBack = false;
   // boolean isIsolation = false;
    //初始状态下，两者均为false，未戴口罩，没打疫苗
    boolean isMask= false;
    boolean isVaccine = false;
    //初始状态下，我们并不追踪
    boolean isTrack = false;


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
        //int CURED = DEATH + 1;//治愈数量用于计算治愈出院后归还床位数量，该状态是否存续待定
    }


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
    int cureMoment = 0;//治愈时刻


    public boolean isInfected() {
        return state >= State.SHADOW;
    }




    //用于判断两个人之间的距离
    public double distance(Person person) {
        return Math.sqrt(Math.pow(getX() - person.getX(), 2) + Math.pow(getY() - person.getY(), 2));
    }


    private void freezy() {
        state = State.FREEZE;
    }

    //正常情况下将如何运动
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



    //带上口罩 或者 打完疫苗之后的行动
    //口罩好做，改参数就行
    //疫苗怎么做啊 打过疫苗之后感染率降低80% 并且不会死亡
    //疫苗与口罩可以共存
    //怎么打疫苗是个问题


    public Bed useBed;

    private float SAFE_DIST = 2f;//安全距离

    //对各种状态的人进行不同的处理，更新发布市民健康状态
    public void update() {

        //如果死亡了就不用处理了，但是在医院中的还是要判断一下出院or死亡，这个等医院写完了再做,隔离治疗的人依旧有一定的可能性会死亡
        if (state == State.DEATH) {
            return;
        }

        //处理在医院的患者
        if(state == State.FREEZE){

            freezeAction();
            return;
            //因为不能移动所以直接return
        }

        //处理已经确诊的感染者（即患者）但是并不在医院的
        //如何确定患者已经死亡呢，找到重症的概率 2%左右

        if(state == State.CONFIRMED){
            confirmAction();
        }


        //状态为潜伏期的时候应该怎么判断
        if(state == State.SHADOW){
            shadowAction();
        }

        //处理未隔离者的移动问题
        //医院本身等于隔离，不必再做第二次隔离规则了

        action();


        //State == NORMAL 时的状态应该怎么判断
        if(state == State.NORMAL) {
            normalAction();
        }

    }

    //这个函数用来判断健康人士是否会感染
    public void normalAction(){
        List<Person> people = PersonPool.getInstance().personList;

        //如果小于安全距离且这个人感染了，那么有一定概率将会被感染
        for (Person person : people) {
            //如果这个人是正常人或者已经死去，那么将不会被感染
            if (person.getState() == State.NORMAL || person.getState() == State.DEATH) {
                continue;
            }
            //戴口罩和打疫苗都会降低感染的概率

            float broad_rate = Constants.BROAD_RATE;

            if(isMask){
                broad_rate = broad_rate*0.8f;
            }

            if(isVaccine){
                broad_rate = broad_rate*0.2f;
            }

            float random = new Random().nextFloat();
            if (random < broad_rate && distance(person) < SAFE_DIST) {
                //记录下被感染的时间和地点
                this.beInfected();
                break;
            }
        }

    }

    public void beInfected() {
        state = State.SHADOW;
        infectedTime = MyPanel.worldTime;
        //再记录一下当前被感染的地点
        confirmLocation[0]  = this.getX();
        confirmLocation[1] = this.getY();

    }

    //这个函数用来判断潜伏人士
    public void shadowAction(){

        //增加一个正态分布用于潜伏期内随机发病时间
        double stdRnShadowtime = MathUtil.stdGaussian(25, Constants.SHADOW_TIME / 2);
        //处理发病的潜伏期感染者
        if (MyPanel.worldTime - infectedTime > stdRnShadowtime) {
            state = State.CONFIRMED;//潜伏者发病
            confirmedTime = MyPanel.worldTime;//刷新时间
        }

    }


    //这个函数用来判断已经感染的人士应该怎么
    public void confirmAction(){
        //打了疫苗的人不会死
        if(isVaccine){
            dieMoment = -1;
        }

        if ( dieMoment == 0) {

            int destiny = new Random().nextInt(10000) + 1;//[1,10000]随机数
            //如果落在死亡区间，那么确定一个死亡时刻
            if (1 <= destiny && destiny <= (int) (Constants.FATALITY_RATE * 10000)) {
                int dieTime = (int) MathUtil.stdGaussian(Constants.DIE_VARIANCE, Constants.DIE_TIME);
                dieMoment = confirmedTime + dieTime;//发病后确定死亡时刻(确诊时间+还能活几天)
            } else {
                dieMoment = -1;//逃过了死神的魔爪

            }
        }


        //医院需要一定的时间来收治病人
        if (MyPanel.worldTime - confirmedTime >= Constants.HOSPITAL_RECEIVE_TIME) {
            //如果患者已经确诊，且（世界时刻-确诊时刻）大于医院响应时间，即医院准备好病床了，可以抬走了
            Bed bed = Hospital.getInstance().pickBed();//查找空床位
            if (bed == null) {

                //没有床位了，报告需求床位数

            } else {
                //安置病人
                useBed = bed;
                state = State.FREEZE;
                setX(bed.getX());
                setY(bed.getY());
                bed.setEmpty(false);
            }
        }

        //处理病死者
        if (MyPanel.worldTime >= dieMoment && dieMoment > 0) {
            state = State.DEATH;//患者死亡
            //Hospital.getInstance().returnBed(useBed);//归还床位
        }
    }


    //这个函数用来判断已经住院的患者的行动
    //追溯患病者源头在这里
    public void freezeAction(){

        //追踪病人的行动轨迹，但是只追踪一次
        if(isTrack){
            List<Person> people = PersonPool.getInstance().personList;
            for(Person person :people){
                int dX = person.getX() - confirmLocation[0];
                int dY = person.getY() - confirmLocation[1];

                double length = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));//与目标点的距离
                //如果距离小于一定距离（称之为能追踪的最大范围且已经感染了或者在潜伏中就进入隔离程序）
                if(length<5 && (person.state == State.CONFIRMED || person.state == State.SHADOW)){
                    Bed bed = Hospital.getInstance().pickBed();//查找空床位
                    if (bed == null) {

                        //没有床位了，报告需求床位数

                    } else {
                        //安置病人
                        person.useBed = bed;
                        person.state = State.FREEZE;
                        setX(bed.getX());
                        setY(bed.getY());
                        bed.setEmpty(false);
                    }
                }
            }
            //下一次判断死亡or治愈时并不会再次追踪 or 溯源
            isTrack = false;

        }

        //如果这个人既未能被治愈也没确定死亡
        if ( dieMoment == 0 && cureMoment == 0) {

            int destiny = new Random().nextInt(10000) + 1;//[1,10000]随机数
            //如果落在死亡区间，那么确定一个死亡时刻
            //在医院中的死亡概率要比在外面的要小
            if (1 <= destiny && destiny <= (int) (Constants.FATALITY_RATE_HOSPITAL * 10000)) {
                int dieTime = (int) MathUtil.stdGaussian(Constants.DIE_VARIANCE, Constants.DIE_TIME);
                dieMoment = confirmedTime + dieTime;//发病后确定死亡时刻(确诊时间+还能活几天)
            } else {
                dieMoment = -1;//逃过了死神的魔爪
                int cureTime = (int) MathUtil.stdGaussian(Constants.CURE_VARIANCE, Constants.CURE_TIME);
                cureMoment = confirmedTime + cureTime;

            }
        }

        //处理病死者
        if (MyPanel.worldTime >= dieMoment && dieMoment > 0) {
            state = State.DEATH;//患者死亡
            Hospital.getInstance().returnBed(useBed);//归还床位
        }


        //处理治愈者
        if (MyPanel.worldTime >= cureMoment && cureMoment > 0) {
            state = State.NORMAL;//患者回归正常生活
            Hospital.getInstance().returnBed(useBed);//归还床位
        }



    }
}
