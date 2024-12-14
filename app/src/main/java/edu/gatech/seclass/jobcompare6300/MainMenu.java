package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {
    private OfferTableHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().setTitle("Job Offer Comparison");
        db = new OfferTableHelper(this);

        findViewById(R.id.btn_compare).setEnabled(db.getNumberOfOffers() != 0);
    }

    public void goToUpdateCurrentJob(View view) {
        Intent intent = new Intent(this, EnterCurrentJobDetails.class);
        startActivity(intent);
    }

    public void goToAddJobOffer(View view){
        Intent intent = new Intent(this, EnterOfferDetails.class);
        startActivity(intent);
    }

    public void goToAdjustComparisonSettings(View view){
        Intent intent = new Intent(this, UpdateComparisonSettings.class);
        startActivity(intent);
    }

    public void goToViewAndCompareOffers(View view){
        Intent intent = new Intent(this, ViewAndCompareOffers.class);
        startActivity(intent);
    }
}