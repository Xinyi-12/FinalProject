
public class Constants {

    public static final int CITY_WIDTH = 840;
    public static final int CITY_HEIGHT = 760;

    public int originalCount = 50;//Initial number of infections
    public static float broadRate = 0.8f;//Dissemination rate
    public float shadowTime = 140;//Incubation time, 14 days for 140
    public int hospitalReceiveTime = 10;//Hospital admission response time
    public int bedCount = 0;//Hospital Beds

    public float u = 0.99f;
    public int cityPersonSize = 5000;//Total city population
    public float fatalityRate = 0.30f;//fatality_rate
    public int dieTime = 100;//Mean time to death, 30 days, from onset (diagnosis)
    public double dieVariance = 1;//Time variance of death


    public float fatalityRateHospital = 0.15f;//fatality_rate
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
