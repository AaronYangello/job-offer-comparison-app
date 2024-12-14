package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

//TODO Implement error/validity checks

public class EnterCurrentJobDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private OfferTableHelper db;

    private EditText title;
    private EditText company;
    private EditText costOfLiving;
    private EditText salary;
    private EditText bonus;
    private EditText benefits;
    private EditText relocation;
    private EditText training;
    private EditText city;
    private Spinner jobStateSpinner;
    private String state;

    String[] statesList = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Canal Zone", "Colorado",
            "Connecticut", "Delaware", "District of Columbia", "Florida", "Georgia", "Guam", "Hawaii",
            "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland",
            "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada",
            "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
            "Oklahoma", "Oregon", "Pennsylvania", "Puerto Rico", "Rhode Island", "South Carolina", "South Dakota",
            "Tennessee", "Texas", "Utah", "Vermont", "Virgin Islands", "Virginia", "Washington", "West Virginia",
            "Wisconsin", "Wyoming"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_current_job_details);
        getSupportActionBar().setTitle("Enter Current Job Details");

        db = new OfferTableHelper(this);

        jobStateSpinner = (Spinner) findViewById(R.id.text_offerState);
        jobStateSpinner.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, statesList);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobStateSpinner.setAdapter(ad);
        title = (EditText) findViewById(R.id.text_jobTitle);
        company = (EditText) findViewById(R.id.text_jobCompany);
        costOfLiving = (EditText) findViewById(R.id.text_jobCostOfLiving);
        salary = (EditText) findViewById(R.id.text_jobSalary);
        bonus = (EditText) findViewById(R.id.text_jobBonus);
        benefits = (EditText) findViewById(R.id.text_jobBenefits);
        relocation = (EditText) findViewById(R.id.text_jobRelocation);
        training = (EditText) findViewById(R.id.text_jobTraining);
        city = (EditText) findViewById(R.id.text_offerCity);

        //TODO run after activity loads
        populateFields();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        state = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(), statesList[i], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public boolean validateRequiredElements(){
        List<EditText> requiredElements = Arrays.asList(
                title, company, costOfLiving, salary, bonus, benefits, relocation, training, city
        );

        return requiredElements.stream()
                .map(element -> validateLengthAndSetError(element, "Required Field"))
                .filter(element -> !element).count() == 0;
    }

    public boolean validateLengthAndSetError(EditText element, String error){
        if(element != null && element.getText() != null && element.getText().toString().trim().length() == 0) {
            element.setError(error);
            return false;
        }
        return true;
    }

    public boolean validateBenefits(){
        String bonusStr = benefits.getText().toString();
        int theBenefits = Integer.parseInt(bonusStr);
        if (theBenefits <= 0 || theBenefits  >= 100) {
            benefits.setError("Benefits should be between 0 and 100");
            return false;
        }
        return true;
    }

    public boolean validateTraining(){
        String trainingStr = training.getText().toString();
        double theTraining = Double.parseDouble(trainingStr);
        if (theTraining <= 0 || theTraining  >= 18000) {
            training.setError("Training Fund should be between $0 and $18,000");
            return false;
        }
        return true;
    }


    public void save(View view) {
        boolean validBenefits = validateBenefits();
        boolean validTraining = validateTraining();

        if (validateRequiredElements() && validBenefits && validTraining) {
            db.updateCurrentJob(new Offer(
                    title.getText().toString().trim(),
                    company.getText().toString().trim(),
                    city.getText().toString().trim() + ", " + state,
                    Integer.parseInt(costOfLiving.getText().toString().trim()),
                    Double.parseDouble(salary.getText().toString().trim()),
                    Double.parseDouble(bonus.getText().toString().trim()),
                    Integer.parseInt(benefits.getText().toString().trim()),
                    Double.parseDouble(relocation.getText().toString().trim()),
                    Double.parseDouble(training.getText().toString().trim()),
                    true
            ));
            startActivity(new Intent(this, MainMenu.class));
        }
    }

    public void cancel(View view){
        startActivity(new Intent(this, MainMenu.class));
    }

    private void populateFields(){
        Offer currentJob = db.getCurrentJob();
        if(currentJob != null) {
            String[] cityState = currentJob.getLocation().split(",");

            ArrayAdapter ad = (ArrayAdapter) jobStateSpinner.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition = ad.getPosition(cityState[1].trim());
            jobStateSpinner.setSelection(spinnerPosition);
            title.setText(currentJob.getTitle());
            company.setText(currentJob.getCompany());
            costOfLiving.setText(currentJob.getCostOfLivingIndexStr());
            salary.setText(currentJob.getYearlySalaryStr());
            bonus.setText(currentJob.getYearlyBonusStr());
            benefits.setText(currentJob.getRetirementMatchPercentStr());
            relocation.setText(currentJob.getRelocationStipendStr());
            training.setText(currentJob.getTrainingFundStr());
            city.setText(cityState[0]);
        }
    }
}