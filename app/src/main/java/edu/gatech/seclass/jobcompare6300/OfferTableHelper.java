package edu.gatech.seclass.jobcompare6300;

import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class OfferTableHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Offer.db";
    public static final String OFFER_TABLE_NAME = "Offer_Table";
    public static final String ID_COL = "id";
    public static final int ID_IDX = 0;
    public static final String TITLE_COL = "title_col";
    public static final int TITLE_IDX = 1;
    public static final String COMPANY_COL = "company";
    public static final int COMPANY_IDX = 2;
    public static final String LOCATION_COL = "location";
    public static final int LOCATION_IDX = 3;
    public static final String COST_OF_LIVING_INDEX_COL = "cost_of_living_index";
    public static final int COST_OF_LIVING_INDEX_IDX = 4;
    public static final String YEARLY_SALARY_COL = "yearly_salary";
    public static final int YEARLY_SALARY_IDX = 5;
    public static final String YEARLY_BONUS_COL = "yearly_bonus";
    public static final int YEARLY_BONUS_IDX = 6;
    public static final String RETIREMENT_MATCH_PERCENT_COL = "retirement_match_percent";
    public static final int RETIREMENT_MATCH_PERCENT_IDX = 7;
    public static final String RELOCATION_STIPEND_COL = "relocation_stipend";
    public static final int RELOCATION_STIPEND_IDX = 8;
    public static final String TRAINING_FUND_COL = "training_fund";
    public static final int TRAINING_FUND_IDX = 9;
    public static final String IS_CURRENT_JOB_COL = "is_current_job";
    public static final int IS_CURRENT_JOB_IDX = 10;

    public OfferTableHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + OFFER_TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE_COL + " TEXT, " +
                COMPANY_COL + " TEXT, " +
                LOCATION_COL + " TEXT, " +
                COST_OF_LIVING_INDEX_COL + " INTEGER, " +
                YEARLY_SALARY_COL + " FLOAT, " +
                YEARLY_BONUS_COL + " FLOAT, " +
                RETIREMENT_MATCH_PERCENT_COL + " INTEGER, " +
                RELOCATION_STIPEND_COL + " FLOAT, " +
                TRAINING_FUND_COL + " FLOAT, " +
                IS_CURRENT_JOB_COL + " BOOLEAN "
                 + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + OFFER_TABLE_NAME);
        onCreate(db);
    }

    //Returns job ID of new job. -1 if insert fails
    public int insertOffer(Offer offer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE_COL, offer.getTitle());
        cv.put(COMPANY_COL, offer.getCompany());
        cv.put(LOCATION_COL, offer.getLocation());
        cv.put(COST_OF_LIVING_INDEX_COL, offer.getCostOfLivingIndex());
        cv.put(YEARLY_SALARY_COL, offer.getYearlySalary());
        cv.put(YEARLY_BONUS_COL, offer.getYearlyBonus());
        cv.put(RETIREMENT_MATCH_PERCENT_COL, offer.getRetirementMatchPercent());
        cv.put(RELOCATION_STIPEND_COL, offer.getRelocationStipend());
        cv.put(TRAINING_FUND_COL, offer.getTrainingFund());
        cv.put(IS_CURRENT_JOB_COL, offer.isCurrentJob());

        long result = db.insert(OFFER_TABLE_NAME, null, cv);

        if(result == -1){
            return -1;
        }

        String sql = "SELECT * FROM " + OFFER_TABLE_NAME + " ORDER BY " + ID_COL + " DESC LIMIT 1;";
        Cursor res = db.rawQuery(sql, null);
        if(res.getCount() == 0){
            return -1;
        }

        res.moveToFirst();
        return res.getInt(ID_IDX);
    }

    public Offer getCurrentJob(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + OFFER_TABLE_NAME + " WHERE " + IS_CURRENT_JOB_COL + " = true LIMIT 1;";
        Cursor res = db.rawQuery(sql, null);

        if(res.getCount() == 0){
            return null;
        }

        res.moveToFirst();
        return new Offer(
            res.getInt(ID_IDX),
            res.getString(TITLE_IDX),
            res.getString(COMPANY_IDX),
            res.getString(LOCATION_IDX),
            res.getInt(COST_OF_LIVING_INDEX_IDX),
            res.getDouble(YEARLY_SALARY_IDX),
            res.getDouble(YEARLY_BONUS_IDX),
            res.getInt(RETIREMENT_MATCH_PERCENT_IDX),
            res.getDouble(RELOCATION_STIPEND_IDX),
            res.getDouble(TRAINING_FUND_IDX),
            true
        );
    }

    public boolean updateCurrentJob(Offer updatedJob){
        boolean success = false;
        Offer currentJob = getCurrentJob();

        if(currentJob != null){
            updatedJob.setId(currentJob.getId());
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TITLE_COL, updatedJob.getTitle());
            cv.put(COMPANY_COL, updatedJob.getCompany());
            cv.put(LOCATION_COL, updatedJob.getLocation());
            cv.put(COST_OF_LIVING_INDEX_COL, updatedJob.getCostOfLivingIndex());
            cv.put(YEARLY_SALARY_COL, updatedJob.getYearlySalary());
            cv.put(YEARLY_BONUS_COL, updatedJob.getYearlyBonus());
            cv.put(RETIREMENT_MATCH_PERCENT_COL, updatedJob.getRetirementMatchPercent());
            cv.put(RELOCATION_STIPEND_COL, updatedJob.getRelocationStipend());
            cv.put(TRAINING_FUND_COL, updatedJob.getTrainingFund());
            cv.put(IS_CURRENT_JOB_COL, updatedJob.isCurrentJob());

            long result = db.update(OFFER_TABLE_NAME, cv, ID_COL + " = ?", new String[]{String.valueOf(updatedJob.getId())});
            success = result != -1;
        }
        else {
            success = insertOffer(updatedJob) != -1;
        }
        return success;
    }

    public int getNumberOfOffers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + OFFER_TABLE_NAME + ";";
        Cursor res = db.rawQuery(sql, null);
        return res.getCount();
    }

    public List<Offer> getOffers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + OFFER_TABLE_NAME + ";";
        Cursor res = db.rawQuery(sql, null);

        if(res.getCount() == 0){
            return null;
        }

        List<Offer> offers = new ArrayList<Offer>();
        while(res.moveToNext()){
            offers.add(new Offer(
                    res.getInt(ID_IDX),
                    res.getString(TITLE_IDX),
                    res.getString(COMPANY_IDX),
                    res.getString(LOCATION_IDX),
                    res.getInt(COST_OF_LIVING_INDEX_IDX),
                    res.getDouble(YEARLY_SALARY_IDX),
                    res.getDouble(YEARLY_BONUS_IDX),
                    res.getInt(RETIREMENT_MATCH_PERCENT_IDX),
                    res.getDouble(RELOCATION_STIPEND_IDX),
                    res.getDouble(TRAINING_FUND_IDX),
                    res.getInt(IS_CURRENT_JOB_IDX) == 1
            ));
        }
        return offers;
    }

    public Offer getOffer(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + OFFER_TABLE_NAME + " WHERE " + ID_COL + " = " + id + " LIMIT 1;";
        Cursor res = db.rawQuery(sql, null);

        if(res.getCount() == 0){
            return null;
        }

        res.moveToFirst();
        return new Offer(
                res.getInt(ID_IDX),
                res.getString(TITLE_IDX),
                res.getString(COMPANY_IDX),
                res.getString(LOCATION_IDX),
                res.getInt(COST_OF_LIVING_INDEX_IDX),
                res.getDouble(YEARLY_SALARY_IDX),
                res.getDouble(YEARLY_BONUS_IDX),
                res.getInt(RETIREMENT_MATCH_PERCENT_IDX),
                res.getDouble(RELOCATION_STIPEND_IDX),
                res.getDouble(RELOCATION_STIPEND_IDX),
                res.getInt(IS_CURRENT_JOB_IDX) == 1
        );
    }

    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ OFFER_TABLE_NAME);
    }
}
