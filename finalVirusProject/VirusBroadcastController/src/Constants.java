
public class Constants {

    public static final int CITY_WIDTH = 840;
    public static final int CITY_HEIGHT = 760;

    public int originalCount = 50;//初始感染数量
    public static float broadRate = 0.8f;//传播率
    public float shadowTime = 0;//潜伏时间，14天为140
    public int hospitalReceiveTime = 10;//医院收治响应时间
    public int bedCount = 0;//医院床位

    public float u = 0.99f;
    public int cityPersonSize = 5000;//城市总人口数量
    public float fatalityRate = 0.50f;//fatality_rate病死率，根据2月6日数据估算（病死数/确诊数）为0.02
    public int dieTime = 100;//死亡时间均值，30天，从发病（确诊）时开始计时
    public double dieVariance = 1;//死亡时间方差


    public float fatalityRateHospital = 0.25f;//fatality_rate病死率，根据2月6日数据估算（病死数/确诊数）为0.02
    public int cureTime = 100;
    public double cureVariance = 1;




    public int getOriginalCount() {
        return originalCount;
    }

    public void setOriginalCount(int originalCount) {
        this.originalCount = originalCount;
    }

    public float getBroadRate() {
        return broadRate;
    }

    public void setBroadRate(float broadRate) {
        this.broadRate = broadRate;
    }

    public float getShadowTime() {
        return shadowTime;
    }

    public void setShadowTime(float shadowTime) {
        this.shadowTime = shadowTime;
    }

    public int getHospitalReceiveTime() {
        return hospitalReceiveTime;
    }

    public void setHospitalReceiveTime(int hospitalReceiveTime) {
        this.hospitalReceiveTime = hospitalReceiveTime;
    }

    public int getBedCount() {
        return bedCount;
    }

    public void setBedCount(int bedCount) {
        this.bedCount = bedCount;
    }

    public float getU() {
        return u;
    }

    public void setU(float u) {
        this.u = u;
    }

    public int getCityPersonSize() {
        return cityPersonSize;
    }

    public void setCityPersonSize(int cityPersonSize) {
        this.cityPersonSize = cityPersonSize;
    }

    public float getFatalityRate() {
        return fatalityRate;
    }

    public void setFatalityRate(float fatalityRate) {
        this.fatalityRate = fatalityRate;
    }

    public int getDieTime() {
        return dieTime;
    }

    public void setDieTime(int dieTime) {
        this.dieTime = dieTime;
    }

    public double getDieVariance() {
        return dieVariance;
    }

    public void setDieVariance(double dieVariance) {
        this.dieVariance = dieVariance;
    }

    public float getFatalityRateHospital() {
        return fatalityRateHospital;
    }

    public void setFatalityRateHospital(float fatalityRateHospital) {
        this.fatalityRateHospital = fatalityRateHospital;
    }

    public int getCureTime() {
        return cureTime;
    }

    public void setCureTime(int cureTime) {
        this.cureTime = cureTime;
    }

    public double getCureVariance() {
        return cureVariance;
    }

    public void setCureVariance(double cureVariance) {
        this.cureVariance = cureVariance;
    }
}
