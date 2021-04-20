
public class Constants {

    public static int ORIGINAL_COUNT = 50;//初始感染数量
    public static float BROAD_RATE = 0.8f;//传播率
    public static float SHADOW_TIME = 140;//潜伏时间，14天为140
    public static int HOSPITAL_RECEIVE_TIME = 10;//医院收治响应时间
    public static int BED_COUNT = 1000;//医院床位


    //public static float u = 0.99f;
    public static int CITY_PERSON_SIZE = 5000;//城市总人口数量
    public static float FATALITY_RATE = 0.1f;//fatality_rate病死率，根据2月6日数据估算（病死数/确诊数）为0.02
    public static float FATALITY_RATE_HOSPITAL = 0.05f;//fatality_rate病死率，根据2月6日数据估算（病死数/确诊数）为0.02
    public static int DIE_TIME = 100;//死亡时间均值，30天，从发病（确诊）时开始计时
    public static double DIE_VARIANCE = 1;//死亡时间方差
    public static int CURE_TIME = 100;//死亡时间均值，30天，从发病（确诊）时开始计时
    public static double CURE_VARIANCE = 1;//死亡时间方差

    public static final int CITY_WIDTH = 700;
    public static final int CITY_HEIGHT = 800;

}
