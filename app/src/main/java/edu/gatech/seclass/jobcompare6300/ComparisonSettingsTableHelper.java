package edu.gatech.seclass.jobcompare6300;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ComparisonSettingsTableHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Settings.db";
    public static final String SETTINGS_TABLE_NAME = "Comparison_Settings_Table";
    public static final String ID_COL = "id";
    public static final int ID_IDX = 0;
    public static final String SALARY_WEIGHT_COL = "salary_weight";
    public static final int SALARY_WEIGHT_IDX = 1;
    public static final String BONUS_WEIGHT_COL = "bonus_weight";
    public static final int BONUS_WEIGHT_IDX = 2;
    public static final String RETIREMENT_MATCH_WEIGHT_COL = "retirement_match_weight";
    public static final int RETIREMENT_MATCH_WEIGHT_IDX = 3;
    public static final String RELOCATION_STIPEND_WEIGHT_COL = "relocation_stipend_weight";
    public static final int RELOCATION_STIPEND_WEIGHT_IDX = 4;
    public static final String TRAINING_FUND_WEIGHT_COL = "training_fund_weight";
    public static final int TRAINING_FUND_WEIGHT_IDX = 5;

    public ComparisonSettingsTableHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + SETTINGS_TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SALARY_WEIGHT_COL + " INTEGER, " +
                BONUS_WEIGHT_COL + " INTEGER, " +
                RETIREMENT_MATCH_WEIGHT_COL + " INTEGER, " +
                RELOCATION_STIPEND_WEIGHT_COL + " INTEGER, " +
                TRAINING_FUND_WEIGHT_COL + " INTEGER "
                + ")";
        db.execSQL(sql);

        init(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SETTINGS_TABLE_NAME);
        onCreate(db);
    }

    private void init(SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put(ID_COL, 1);
        cv.put(SALARY_WEIGHT_COL, 0);
        cv.put(BONUS_WEIGHT_COL, 0);
        cv.put(RETIREMENT_MATCH_WEIGHT_COL, 0);
        cv.put(RELOCATION_STIPEND_WEIGHT_COL, 0);
        cv.put(TRAINING_FUND_WEIGHT_COL, 0);

        db.insertOrThrow(SETTINGS_TABLE_NAME, null, cv);
    }

    public ComparisonSettings getComparisonSettings(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + SETTINGS_TABLE_NAME + " LIMIT 1;";
        Cursor res = db.rawQuery(sql, null);

        if(res.getCount() == 0){
            return null;
        }

        res.moveToFirst();
        return new ComparisonSettings(
                res.getInt(SALARY_WEIGHT_IDX),
                res.getInt(BONUS_WEIGHT_IDX),
                res.getInt(RETIREMENT_MATCH_WEIGHT_IDX),
                res.getInt(RELOCATION_STIPEND_WEIGHT_IDX),
                res.getInt(TRAINING_FUND_WEIGHT_IDX)
        );
    }

    public boolean updateComparisonSettings(ComparisonSettings newSettings){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SALARY_WEIGHT_COL, newSettings.getSalaryWeight());
        cv.put(BONUS_WEIGHT_COL, newSettings.getBonusWeight());
        cv.put(RETIREMENT_MATCH_WEIGHT_COL, newSettings.getRetirementMatchWeight());
        cv.put(RELOCATION_STIPEND_WEIGHT_COL, newSettings.getReloactionStipendWeight());
        cv.put(TRAINING_FUND_WEIGHT_COL, newSettings.getTrainingFundWeight());

        long result = db.update(SETTINGS_TABLE_NAME, cv, ID_COL + "= ?", new String[]{"1"});
        return result != -1;
    }
}
