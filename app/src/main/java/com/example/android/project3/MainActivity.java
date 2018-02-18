package com.example.android.project3;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String candidateName;
    static String outputMessage = "";
    int candidateAge = 0;
    int candidateHeight = 0;
    int candidateWeight = 0;
    int quizResult = 0;
    final static String OUTPUT_MESSAGE = "SavedStateOfOutputMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** recovering the instance state */
        if (savedInstanceState != null) {
            outputMessage = savedInstanceState.getString(OUTPUT_MESSAGE);
        } else {
            outputMessage = getText(R.string.bmi_outcome_text).toString();
        }
        setContentView(R.layout.activity_main);
        displayBmi(outputMessage);
    }

    /**
     * Save state for later callback
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(OUTPUT_MESSAGE, outputMessage);
        super.onSaveInstanceState(outState);
    }

    /**
     * This method is called when Calculate button is clicked
     * @param view
     */
    public void calculateBmi (View view) {
        String bmiReport = createMessage(view);
        if ((candidateName.equals("")) || (candidateAge == 0) || (candidateWeight == 0) || (candidateHeight == 0))  {
            Toast toastMessage = Toast.makeText(this, getText(R.string.outcome_correct), Toast.LENGTH_LONG);
            toastMessage.show();
        }
        displayBmi(bmiReport);
    }

    /**
     * Displays the output for BMI calculation
     * @param bmiMessage
     */
    private void displayBmi (String bmiMessage) {
        TextView displayBmiAndOutcome = (TextView) findViewById(R.id.text_bmi_outcome);
        displayBmiAndOutcome.setText(bmiMessage);
    }

    /**
     * Creates the output message for BMI calculation
     * @param view
     *
     */
    private String createMessage(View view) {
        String errorMessage = "";
        boolean checkOk = true;
        /**
         * Check if there is a name, weight, age and height.
         * Check if age, weight and height are within boundaries
         */
        EditText candidate_name = (EditText) findViewById(R.id.name_edit);
        EditText candidate_age = (EditText) findViewById(R.id.age_edit);
        EditText candidate_height = (EditText) findViewById(R.id.height_edit);
        EditText candidate_weight = (EditText) findViewById(R.id.weight_edit);
        if (candidate_name.getText().toString().equals("")) {
            checkOk = false;
            candidateName = "";
            errorMessage += getText(R.string.missing_name) + "\n";
        } else {
            candidateName = candidate_name.getText().toString();
        }
        if ((candidate_age.getText().toString().equals("")) ||
                (Integer.valueOf(candidate_age.getText().toString()) > 99) ||
                (Integer.valueOf(candidate_age.getText().toString()) < 1 )) {
            candidateAge = 0;
            Toast toastMessage = Toast.makeText(this, getText(R.string.age_values_accepted), Toast.LENGTH_LONG);
            toastMessage.show();
            checkOk = false;
            errorMessage += getText(R.string.age_values_accepted) + "\n";
        } else {
            candidateAge = Integer.valueOf(candidate_age.getText().toString());
        }
        if ((candidate_height.getText().toString().equals("")) ||
                (Integer.valueOf(candidate_height.getText().toString()) > 215) ||
                (Integer.valueOf(candidate_height.getText().toString()) < 150 )) {
            candidateHeight = 0;
            Toast toastMessage = Toast.makeText(this, getText(R.string.height_values_accepted), Toast.LENGTH_LONG);
            toastMessage.show();
            checkOk = false;
            errorMessage += getText(R.string.height_values_accepted) + "\n";
        } else {
            candidateHeight = Integer.valueOf(candidate_height.getText().toString());
        }
        if ((candidate_weight.getText().toString().equals("")) ||
                (Integer.valueOf(candidate_weight.getText().toString()) >199) ||
                (Integer.valueOf(candidate_weight.getText().toString()) <45)) {
            candidateWeight = 0;
            Toast toastMessage = Toast.makeText(this, getText(R.string.weight_values_accepted), Toast.LENGTH_LONG);
            toastMessage.show();
            checkOk = false;
            errorMessage += getText(R.string.weight_values_accepted) + "\n";
        } else {
            candidateWeight = Integer.valueOf(candidate_weight.getText().toString());
        }

        /**
         * Calculate BMI formula, set a message
         */
        int candidateBmiValue = 0;
        String candidateBmiOutcome = "";
        if (checkOk) {
            candidateBmiValue = 10000 * candidateWeight / (candidateHeight * candidateHeight);
        }
        if (candidateBmiValue < 15) {
            candidateBmiOutcome = getText(R.string.bmi_15).toString();
        } else if (candidateBmiValue < 19) {
            candidateBmiOutcome = getText(R.string.bmi_19).toString();
        } else if (candidateBmiValue < 25) {
            candidateBmiOutcome = getText(R.string.bmi_25).toString();
        } else if (candidateBmiValue < 30) {
            candidateBmiOutcome = getText(R.string.bmi_30).toString();
        } else if (candidateBmiValue < 35) {
            candidateBmiOutcome = getText(R.string.bmi_35).toString();
        } else if (candidateBmiValue >= 35) {
            candidateBmiOutcome = getText(R.string.bmi_over).toString();
        } else {
            candidateBmiOutcome = getText(R.string.bmi_error).toString();
        }

        /**
         * Check gender, habits, level of exercise
         */
        RadioButton radio_male = (RadioButton) findViewById(R.id.radio_male);
        RadioButton radio_female = (RadioButton) findViewById(R.id.radio_female);
        String personGender = "";
        if (radio_male.isChecked()) {
            personGender = getText(R.string.radio_male).toString();
        } else if (radio_female.isChecked()) {
            personGender = getText(R.string.radio_female).toString();
        } else {
            checkOk = false;
            errorMessage += getText(R.string.text_gender) + "\n";
        }
        CheckBox check_sleeping = (CheckBox) findViewById(R.id.check_sleeping);
        CheckBox check_drinking = (CheckBox) findViewById(R.id.check_drinking);
        CheckBox check_smoking = (CheckBox) findViewById(R.id.check_smoking);
        String badHabits = "";
        if (check_smoking.isChecked()) {
            badHabits += getText(R.string.bad_habits_smoking_outcome) + "\n";
        }
        if (check_drinking.isChecked()) {
            badHabits += getText(R.string.bad_habits_drinking_outcome) + "\n";
        }
        if (check_sleeping.isChecked()) {
            badHabits += getText(R.string.bad_habits_sleeping_outcome) + "\n";
        }
        if (badHabits.equals("")) {
            badHabits = getText(R.string.bad_habits_none_outcome) + "\n";
        }
        RadioButton radio_exercise_level_0 = (RadioButton) findViewById(R.id.radio_exercise_level_0);
        RadioButton radio_exercise_level_1 = (RadioButton) findViewById(R.id.radio_exercise_level_1);
        RadioButton radio_exercise_level_2 = (RadioButton) findViewById(R.id.radio_exercise_level_2);
        RadioButton radio_exercise_level_3 = (RadioButton) findViewById(R.id.radio_exercise_level_3);
        RadioButton radio_exercise_level_4 = (RadioButton) findViewById(R.id.radio_exercise_level_4);
        String levelOfExercise = "";
        if (radio_exercise_level_0.isChecked()) {
            levelOfExercise = getText(R.string.exercise_level_0_outcome).toString();
        } else if (radio_exercise_level_1.isChecked()) {
            levelOfExercise = getText(R.string.exercise_level_1_outcome).toString();
        } else if (radio_exercise_level_2.isChecked()) {
            levelOfExercise = getText(R.string.exercise_level_2_outcome).toString();
        } else if (radio_exercise_level_3.isChecked()) {
            levelOfExercise = getText(R.string.exercise_level_3_outcome).toString();
        } else if (radio_exercise_level_4.isChecked()) {
            levelOfExercise = getText(R.string.exercise_level_4_outcome).toString();
        } else {
            checkOk = false;
            errorMessage += getText(R.string.exercise_text);
        }
        /**
         * create message
         */
        outputMessage = candidateName;
        outputMessage += "\n" + getText(R.string.text_age) + candidateAge;
        outputMessage += "\n" + getText(R.string.text_height) + candidateHeight;
        outputMessage += "\n" + getText(R.string.text_weight) + candidateWeight;
        outputMessage += "\n" + getText(R.string.output_title_gender) + personGender;
        outputMessage += "\n" + badHabits;
        outputMessage += levelOfExercise;
        outputMessage += "\n" + getText(R.string.outcome_bmi_title) + candidateBmiValue;
        outputMessage += ", " + candidateBmiOutcome;
        if (checkOk) {
            return (outputMessage);
        } else {
            return (errorMessage);
        }
    }

    /**
     * This method is called when CLEAN DATA button is pressed
     * @param view
     */
    public void resetBmiPart(View view) {
        outputMessage = getText(R.string.bmi_outcome_text).toString();
        candidateName = "";
        candidateAge = 0;
        candidateHeight = 0;
        candidateWeight = 0;
        RadioGroup radio_gender = (RadioGroup) findViewById(R.id.radiogroup_gender);
        radio_gender.clearCheck();
        displayBmi(getText(R.string.bmi_outcome_text).toString());
        RadioGroup radio_exercise_level = (RadioGroup) findViewById(R.id.radiogroup_exercise);
        radio_exercise_level.clearCheck();
        CheckBox check_sleeping = (CheckBox) findViewById(R.id.check_sleeping);
        CheckBox check_drinking = (CheckBox) findViewById(R.id.check_drinking);
        CheckBox check_smoking = (CheckBox) findViewById(R.id.check_smoking);
        check_sleeping.setChecked(false);
        check_drinking.setChecked(false);
        check_smoking.setChecked(false);
        EditText candidate_age = (EditText) findViewById(R.id.age_edit);
        candidate_age.setText("");
        EditText candidate_height = (EditText) findViewById(R.id.height_edit);
        candidate_height.setText("");
        EditText candidate_weight = (EditText) findViewById(R.id.weight_edit);
        candidate_weight.setText("");
        EditText candidate_name = (EditText) findViewById(R.id.name_edit);
        candidate_name.setText("");
    }

    /**
     * This method is called when Check Quiz Answers button is clicked
     * @param view
     */
    public void checkQuizAnswers(View view) {
        RadioButton physical_training_correct = (RadioButton) findViewById(R.id.radio_physical_training_correct);
        RadioButton physical_training_wrong = (RadioButton) findViewById(R.id.radio_physical_training_wrong);
        RadioButton discomfort_short_break = (RadioButton) findViewById(R.id.radio_discomfort_short_break);
        RadioButton discomfort_doctor = (RadioButton) findViewById(R.id.radio_discomfort_consult_professional);
        RadioButton eat_correct = (RadioButton) findViewById(R.id.radio_eat_correct);
        RadioButton eat_wrong = (RadioButton) findViewById(R.id.radio_eat_wrong);
        String toast_compliment = "";
        boolean CheckOk = true;
        if (physical_training_correct.isChecked()) {
            quizResult += 1;
        } else if (physical_training_wrong.isChecked()) {
            quizResult += 0;
        } else {
            CheckOk = false;
        }
        if (discomfort_doctor.isChecked()) {
            quizResult += 1;
        } else if (discomfort_short_break.isChecked()) {
            quizResult += 0;
        } else {
            CheckOk = false;
        }
        if (eat_wrong.isChecked()) {
            quizResult += 1;
        } else if (eat_correct.isChecked()) {
            quizResult += 0;
        } else {
            CheckOk = false;
        }
        CheckBox get_checkup = (CheckBox) findViewById(R.id.check_get_checkup);
        CheckBox daily_training = (CheckBox) findViewById(R.id.check_daily_training);
        CheckBox eat_healthy = (CheckBox) findViewById(R.id.check_eat_healthy);
        if ((get_checkup.isChecked()) && (daily_training.isChecked()) && (eat_healthy.isChecked())) {
            quizResult += 1;
        }
        /** create a toast message depending on your score */
        if (quizResult == 0) {
            toast_compliment = getText(R.string.toast_compliment_0).toString();
        } else if (quizResult == 4) {
            toast_compliment = getText(R.string.toast_compliment_ok).toString();
        } else {
            toast_compliment = getText(R.string.toast_compliment_middle).toString();
        }
        if (CheckOk) {
            Toast toastScore = Toast.makeText(this, toast_compliment + "\n" + getText(R.string.your_score_is).toString() + quizResult + getText(R.string.out_of_all), Toast.LENGTH_LONG);
            toastScore.show();
            quizResult = 0;
        } else {
            Toast toastIncomplete = Toast.makeText(this, getText(R.string.complete_the_quiz), Toast.LENGTH_LONG);
            toastIncomplete.show();
        }
    }

    /**
     * This method is called when Reset Quiz button is clicked
     * @param view
     */
    public void clearQuiz(View view) {
        RadioGroup radiogroup_physical = (RadioGroup) findViewById(R.id.radiogroup_physical_training);
        radiogroup_physical.clearCheck();
        RadioGroup radiogroup_discomfort = (RadioGroup) findViewById(R.id.radiogroup_discomfort);
        radiogroup_discomfort.clearCheck();
        RadioGroup radiogroup_eat_before_marathon = (RadioGroup) findViewById(R.id.radiogroup_eat_before_marathon);
        radiogroup_eat_before_marathon.clearCheck();
        CheckBox get_checkup = (CheckBox) findViewById(R.id.check_get_checkup);
        get_checkup.setChecked(false);
        CheckBox daily_training = (CheckBox) findViewById(R.id.check_daily_training);
        daily_training.setChecked(false);
        CheckBox eat_healthy = (CheckBox) findViewById(R.id.check_eat_healthy);
        eat_healthy.setChecked(false);
        quizResult = 0;
    }
}
