package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;

//TODO implement error checking and validation
public class UpdateComparisonSettings extends AppCompatActivity {
    private ComparisonSettingsTableHelper db;

    private EditText salaryWeight;
    private EditText bonusWeight;
    private EditText retirementWeight;
    private EditText relocationWeight;
    private EditText trainingWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_comparison_settings);
        getSupportActionBar().setTitle("Comparison Settings");

        db = new ComparisonSettingsTableHelper(this);

        salaryWeight = (EditText) findViewById(R.id.txt_salaryWeight);
        bonusWeight = (EditText) findViewById(R.id.txt_bonusWeight);
        retirementWeight = (EditText) findViewById(R.id.txt_retireWeight);
        relocationWeight = (EditText) findViewById(R.id.txt_relocateWeight);
        trainingWeight = (EditText) findViewById(R.id.txt_trainingWeight);

        populateSettings();
    }
    public boolean validateRequiredElements(){
        List<EditText> requiredElements = Arrays.asList(
                salaryWeight, bonusWeight, retirementWeight, relocationWeight, trainingWeight
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

    public void saveSettings(View view) {
        if (validateRequiredElements()) {
            db.updateComparisonSettings(new ComparisonSettings(
                    Integer.parseInt(salaryWeight.getText().toString().trim()),
                    Integer.parseInt(bonusWeight.getText().toString().trim()),
                    Integer.parseInt(retirementWeight.getText().toString().trim()),
                    Integer.parseInt(relocationWeight.getText().toString().trim()),
                    Integer.parseInt(trainingWeight.getText().toString().trim())
            ));

            startActivity(new Intent(this, MainMenu.class));
        }
    }

    public void cancelSettings(View view){
        startActivity(new Intent(this, MainMenu.class));
    }

    private void populateSettings(){
        ComparisonSettings settings = db.getComparisonSettings();

        if(settings != null){
            salaryWeight.setText(settings.getSalaryWeightStr());
            bonusWeight.setText(settings.getBonusWeightStr());
            retirementWeight.setText(settings.getRetirementMatchWeightStr());
            relocationWeight.setText(settings.getReloactionStipendWeightStr());
            trainingWeight.setText(settings.getTrainingFundWeightStr());
        }
    }
}