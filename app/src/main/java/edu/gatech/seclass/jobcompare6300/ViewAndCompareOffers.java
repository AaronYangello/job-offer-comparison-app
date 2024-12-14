package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ViewAndCompareOffers extends AppCompatActivity {
    private OfferTableHelper offersDb;
    private ComparisonSettingsTableHelper settingsDb;

    private TextView job1CurrentJob;
    private TextView job1Title;
    private TextView job1Company;
    private TextView job1Location;
    private TextView job1Salary;
    private TextView job1Bonus;
    private TextView job1Retire;
    private TextView job1Relocation;
    private TextView job1Training;
    private TextView job2CurrentJob;
    private TextView job2Title;
    private TextView job2Company;
    private TextView job2Location;
    private TextView job2Salary;
    private TextView job2Bonus;
    private TextView job2Retire;
    private TextView job2Relocation;
    private TextView job2Training;

    private LinearLayout jobListHolder;

    private int numChecked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_compare_offers);
        getSupportActionBar().setTitle("Compare Offers");

        offersDb = new OfferTableHelper(this);
        settingsDb = new ComparisonSettingsTableHelper(this);

        job1CurrentJob = (TextView) findViewById(R.id.txt_job1_currentJob);
        job1Title = (TextView) findViewById(R.id.txt_job1_title);
        job1Company = (TextView) findViewById(R.id.txt_job1_company);
        job1Location = (TextView) findViewById(R.id.txt_job1_location);
        job1Salary = (TextView) findViewById(R.id.txt_job1_salary);
        job1Bonus = (TextView) findViewById(R.id.txt_job1_bonus);
        job1Retire = (TextView) findViewById(R.id.txt_job1_retire);
        job1Relocation = (TextView) findViewById(R.id.txt_job1_relocation);
        job1Training = (TextView) findViewById(R.id.txt_job1_training);
        job2CurrentJob = (TextView) findViewById(R.id.txt_job2_currentJob);
        job2Title = (TextView) findViewById(R.id.txt_job2_title);
        job2Company = (TextView) findViewById(R.id.txt_job2_company);
        job2Location = (TextView) findViewById(R.id.txt_job2_location);
        job2Salary = (TextView) findViewById(R.id.txt_job2_salary);
        job2Bonus = (TextView) findViewById(R.id.txt_job2_bonus);
        job2Retire = (TextView) findViewById(R.id.txt_job2_retire);
        job2Relocation = (TextView) findViewById(R.id.txt_job2_relocation);
        job2Training = (TextView) findViewById(R.id.txt_job2_training);

        jobListHolder = (LinearLayout) findViewById(R.id.layout_joblist_holder);

        buildJobsList(getIntent().getIntExtra("ID_OF_SELECTED_JOB", -1));
    }

    public void compareJobs(View view){
        List<Offer> selectedOffers = new ArrayList<Offer>();
        final int childCount = jobListHolder.getChildCount();
        for(int i = 0; i < childCount && selectedOffers.size() <= 2; i++){
            ConstraintLayout cl = (ConstraintLayout) jobListHolder.getChildAt(i);
            LinearLayout ll = (LinearLayout) cl.getChildAt(0);
            CheckBox c = (CheckBox) ll.getChildAt(0);
            if(c.isChecked()){
                TextView idView = (TextView) ll.getChildAt(4);
                selectedOffers.add(offersDb.getOffer(idView.getText().toString()));
            }
        }

        populateJobDetails(selectedOffers);
    }

    private void populateJobDetails(List<Offer> offers){
        if(offers.size() >= 1){
            Offer job1 = offers.get(0);

            if(job1.isCurrentJob()){
                job1CurrentJob.setVisibility(View.VISIBLE);
            }
            else{
                job1CurrentJob.setVisibility(View.INVISIBLE);
            }
            job1Title.setText(job1.getTitle());
            job1Company.setText(job1.getCompany());
            job1Location.setText(job1.getLocation());
            job1Salary.setText(job1.getYearlySalaryStr());
            job1Bonus.setText(job1.getYearlyBonusStr());
            job1Retire.setText(job1.getRetirementMatchPercentStr());
            job1Relocation.setText(job1.getRelocationStipendStr());
            job1Training.setText(job1.getTrainingFundStr());
        }

        if(offers.size() >= 2){
            Offer job2 = offers.get(1);

            if(job2.isCurrentJob()){
                job2CurrentJob.setVisibility(View.VISIBLE);
            }
            else{
                job2CurrentJob.setVisibility(View.INVISIBLE);
            }
            job2Title.setText(job2.getTitle());
            job2Company.setText(job2.getCompany());
            job2Location.setText(job2.getLocation());
            job2Salary.setText(job2.getYearlySalaryStr());
            job2Bonus.setText(job2.getYearlyBonusStr());
            job2Retire.setText(job2.getRetirementMatchPercentStr());
            job2Relocation.setText(job2.getRelocationStipendStr());
            job2Training.setText(job2.getTrainingFundStr());
        }
    }

    public void returnToMain(View view){
        startActivity(new Intent(this,MainMenu.class));
    }

    private void buildJobsList(int selectedJobId){
        List<Offer> offers = getSortedOffers();
        for(Offer offer : offers){
            addOfferListing(offer, offer.getId() == selectedJobId);
        }
    }

    private void addOfferListing(Offer offer, boolean isChecked){
        View displayOfferListing = getLayoutInflater().inflate(R.layout.display_offer_listing, null, false);
        CheckBox checkBox = (CheckBox) displayOfferListing.findViewById(R.id.checkBox);
        TextView title = (TextView) displayOfferListing.findViewById(R.id.txt_displayTitle);
        TextView company = (TextView) displayOfferListing.findViewById(R.id.txt_displayCompany);
        TextView currentJob = (TextView) displayOfferListing.findViewById(R.id.txt_displayCurrentJob);
        TextView id = (TextView) displayOfferListing.findViewById(R.id.txt_displayId);

        checkBox.setChecked(isChecked);
        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;

                if(checkBox.isChecked()) {
                    if (numChecked >= 2) {
                        checkBox.setChecked(false);
                    } else {
                        numChecked++;
                    }
                } else {
                    numChecked--;
                }
            }
        });

        title.setText(offer.getTitle());
        company.setText(offer.getCompany());
        if(offer.isCurrentJob()){
            currentJob.setVisibility(View.VISIBLE);
        }
        else{
            currentJob.setVisibility(View.INVISIBLE);
        }
        id.setText(offer.getIdStr());

        jobListHolder.addView(displayOfferListing);
    }

    //Sort offers
    private List<Offer> getSortedOffers(){
        List<Offer> offers = offersDb.getOffers();
        ComparisonSettings settings = settingsDb.getComparisonSettings();
        OfferScoreComparator offerComparator = new OfferScoreComparator(settings);
        Collections.sort(offers, Collections.reverseOrder(offerComparator));
        return offers;
    }
}