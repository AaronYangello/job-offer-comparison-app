package edu.gatech.seclass.jobcompare6300;

import android.os.Parcel;
import android.os.Parcelable;

public class Offer implements Parcelable{
    private int id;
    private String title;
    private String company;
    private String location;
    private int costOfLivingIndex;
    private double yearlySalary;
    private double yearlyBonus;
    private int retirementMatchPercent;
    private double relocationStipend;
    private double trainingFund;
    private boolean isCurrentJob;

    public Offer() {
        this.id = -1;
        this.title = "";
        this.company = "";
        this.location = "";
        this.costOfLivingIndex = -1;
        this.yearlySalary = 0.0;
        this.yearlyBonus = 0.0;
        this.retirementMatchPercent = 0;
        this.relocationStipend = 0.0;
        this.trainingFund = 0.0;
        this.isCurrentJob = false;
    }

    public Offer(int id, String title, String company, String location, int costOfLivingIndex,
                 double yearlySalary, double yearlyBonus, int retirementMatchPercent,
                 double relocationStipend, double trainingFund, boolean isCurrentJob) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.costOfLivingIndex = costOfLivingIndex;
        this.yearlySalary = yearlySalary;
        this.yearlyBonus = yearlyBonus;
        this.retirementMatchPercent = retirementMatchPercent;
        this.relocationStipend = relocationStipend;
        this.trainingFund = trainingFund;
        this.isCurrentJob = isCurrentJob;
    }

    public Offer(String title, String company, String location, int costOfLivingIndex,
                 double yearlySalary, double yearlyBonus, int retirementMatchPercent,
                 double relocationStipend, double trainingFund, boolean isCurrentJob) {
        this.id = -1;
        this.title = title;
        this.company = company;
        this.location = location;
        this.costOfLivingIndex = costOfLivingIndex;
        this.yearlySalary = yearlySalary;
        this.yearlyBonus = yearlyBonus;
        this.retirementMatchPercent = retirementMatchPercent;
        this.relocationStipend = relocationStipend;
        this.trainingFund = trainingFund;
        this.isCurrentJob = isCurrentJob;
    }

    protected Offer(Parcel in) {
        title = in.readString();
        company = in.readString();
        location = in.readString();
        costOfLivingIndex = in.readInt();
        yearlySalary = in.readDouble();
        yearlyBonus = in.readDouble();
        retirementMatchPercent = in.readInt();
        relocationStipend = in.readDouble();
        trainingFund = in.readDouble();
        isCurrentJob = in.readByte() != 0;
    }

    private double getAdjustedSalary() {
        return yearlySalary / costOfLivingIndex * 100;
    }

    private double getAdjustedBonus(){
        return yearlyBonus / costOfLivingIndex * 100;
    }

    public double calculateScore(ComparisonSettings settings){
        return ((settings.getSalaryWeight() / 7 * getAdjustedSalary()) +
                (settings.getBonusWeight() / 7 * getAdjustedBonus()) +
                (settings.getRetirementMatchWeight() / 7 * retirementMatchPercent / 100 * getAdjustedSalary()) +
                (settings.getReloactionStipendWeight() / 7 * relocationStipend) +
                (settings.getTrainingFundWeight() / 7 * trainingFund));
    }

    public int getId() {
        return id;
    }

    public String getIdStr() {
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCostOfLivingIndex() {
        return costOfLivingIndex;
    }
    public String getCostOfLivingIndexStr() {
        return String.valueOf(costOfLivingIndex);
    }

    public void setCostOfLivingIndex(int costOfLivingIndex) {
        this.costOfLivingIndex = costOfLivingIndex;
    }

    public double getYearlySalary() {
        return yearlySalary;
    }

    public String getYearlySalaryStr() {
        return String.valueOf(yearlySalary);
    }

    public void setYearlySalary(double yearlySalary) {
        this.yearlySalary = yearlySalary;
    }

    public double getYearlyBonus() {
        return yearlyBonus;
    }

    public String getYearlyBonusStr() {
        return String.valueOf(yearlyBonus);
    }

    public void setYearlyBonus(double yearlyBonus) {
        this.yearlyBonus = yearlyBonus;
    }

    public int getRetirementMatchPercent() {
        return retirementMatchPercent;
    }

    public String getRetirementMatchPercentStr() {
        return String.valueOf(retirementMatchPercent);
    }

    public void setRetirementMatchPercent(int retirementMatchPercent) {
        this.retirementMatchPercent = retirementMatchPercent;
    }

    public double getRelocationStipend() {
        return relocationStipend;
    }

    public String getRelocationStipendStr() {
        return String.valueOf(relocationStipend);
    }

    public void setRelocationStipend(double relocationStipend) {
        this.relocationStipend = relocationStipend;
    }

    public double getTrainingFund() {
        return trainingFund;
    }

    public String getTrainingFundStr() {
        return String.valueOf(trainingFund);
    }

    public void setTrainingFund(double trainingFund) {
        this.trainingFund = trainingFund;
    }

    public boolean isCurrentJob() {
        return isCurrentJob;
    }

    public void setCurrentJob(boolean currentJob) {
        isCurrentJob = currentJob;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(company);
        dest.writeString(location);
        dest.writeInt(costOfLivingIndex);
        dest.writeDouble(yearlySalary);
        dest.writeDouble(yearlyBonus);
        dest.writeInt(retirementMatchPercent);
        dest.writeDouble(relocationStipend);
        dest.writeDouble(trainingFund);
        dest.writeByte((byte) (isCurrentJob ? 1 : 0));
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };
}
