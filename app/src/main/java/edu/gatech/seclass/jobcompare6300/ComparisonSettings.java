package edu.gatech.seclass.jobcompare6300;

import android.os.Parcel;
import android.os.Parcelable;

public class ComparisonSettings implements Parcelable {
    private int salaryWeight;
    private int bonusWeight;
    private int retirementMatchWeight;
    private int reloactionStipendWeight;
    private int trainingFundWeight;

    public ComparisonSettings() {
        this.salaryWeight = 0;
        this.bonusWeight = 0;
        this.retirementMatchWeight = 0;
        this.reloactionStipendWeight = 0;
        this.trainingFundWeight = 0;
    }

    public ComparisonSettings(int salaryWeight, int bonusWeight, int retirementMatchWeight,
                              int reloactionStipendWeight, int trainingFundWeight) {
        this.salaryWeight = salaryWeight;
        this.bonusWeight = bonusWeight;
        this.retirementMatchWeight = retirementMatchWeight;
        this.reloactionStipendWeight = reloactionStipendWeight;
        this.trainingFundWeight = trainingFundWeight;
    }

    protected ComparisonSettings(Parcel in) {
        salaryWeight = in.readInt();
        bonusWeight = in.readInt();
        retirementMatchWeight = in.readInt();
        reloactionStipendWeight = in.readInt();
        trainingFundWeight = in.readInt();
    }

    public int getSalaryWeight() {
        return salaryWeight;
    }

    public String getSalaryWeightStr() {
        return String.valueOf(salaryWeight);
    }

    public void setSalaryWeight(int salaryWeight) {
        this.salaryWeight = salaryWeight;
    }

    public int getBonusWeight() {
        return bonusWeight;
    }

    public String getBonusWeightStr() {
        return String.valueOf(bonusWeight);
    }

    public void setBonusWeight(int bonusWeight) {
        this.bonusWeight = bonusWeight;
    }

    public int getRetirementMatchWeight() {
        return retirementMatchWeight;
    }

    public String getRetirementMatchWeightStr() {
        return String.valueOf(retirementMatchWeight);
    }

    public void setRetirementMatchWeight(int retirementMatchWeight) {
        this.retirementMatchWeight = retirementMatchWeight;
    }

    public int getReloactionStipendWeight() {
        return reloactionStipendWeight;
    }

    public String getReloactionStipendWeightStr() {
        return String.valueOf(reloactionStipendWeight);
    }

    public void setReloactionStipendWeight(int reloactionStipendWeight) {
        this.reloactionStipendWeight = reloactionStipendWeight;
    }

    public int getTrainingFundWeight() {
        return trainingFundWeight;
    }

    public String getTrainingFundWeightStr() {
        return String.valueOf(trainingFundWeight);
    }

    public void setTrainingFundWeight(int trainingFundWeight) {
        this.trainingFundWeight = trainingFundWeight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(salaryWeight);
        dest.writeInt(bonusWeight);
        dest.writeInt(retirementMatchWeight);
        dest.writeInt(reloactionStipendWeight);
        dest.writeInt(trainingFundWeight);
    }

    public static final Creator<ComparisonSettings> CREATOR = new Creator<ComparisonSettings>() {
        @Override
        public ComparisonSettings createFromParcel(Parcel in) {
            return new ComparisonSettings(in);
        }

        @Override
        public ComparisonSettings[] newArray(int size) {
            return new ComparisonSettings[size];
        }
    };

}
