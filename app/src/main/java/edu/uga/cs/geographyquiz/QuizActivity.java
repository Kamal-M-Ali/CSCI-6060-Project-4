package edu.uga.cs.geographyquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import edu.uga.cs.geographyquiz.pojo.QuestionSet;
import edu.uga.cs.geographyquiz.pojo.Question;
import edu.uga.cs.geographyquiz.pojo.Quiz;

/**
 * The quiz taking activity. Does not extend MenuActivity since the project requirements state to not
 * present the user with the button to exit the app while the quiz is in progress.
 */
public class QuizActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "QuizActivity";
    private TextView textView;
    private TextView textView2;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioGroup radioGroup2;
    private RadioButton radioButtonT;
    private RadioButton radioButtonF;
    private GeographyQuizData geographyQuizData;
    private Quiz quiz;
    private QuestionSet questionSet;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        geographyQuizData = new GeographyQuizData(getApplicationContext());

        quiz = new Quiz();
        questionSet = new QuestionSet();
        questionList = new ArrayList<>();

        textView = findViewById(R.id.textView);
        radioGroup = findViewById((R.id.radioGroup));
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButtonT = findViewById(R.id.radioButtonT);
        radioButtonF = findViewById(R.id.radioButtonF);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup2 = findViewById(R.id.radioGroup2);

        //Need to implement storing the answers to the database
        //listener to take in which answer the user chose
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId contains the ID of the selected RadioButton
                RadioButton selectedRadioButton = findViewById(checkedId);
                if (selectedRadioButton != null) {
                    String selectedCity = selectedRadioButton.getText().toString();
                    if(selectedCity.equalsIgnoreCase(questionList.get(quiz.getProgress()).getCapitalCity())) {
                        Toast.makeText(QuizActivity.this, "Correct! It is the capital", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(QuizActivity.this, "Incorrect! It is not the capital", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //listener to take in which answer the user chose
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId contains the ID of the selected RadioButton
                RadioButton selectedRadioButton = findViewById(checkedId);
                if (selectedRadioButton != null) {
                    String selectedTorF = selectedRadioButton.getText().toString();
                    if(selectedTorF.equalsIgnoreCase("True") &&
                            questionList.get(quiz.getProgress()).getSizeRank() == 1 ) {
                        Toast.makeText(QuizActivity.this, "Correct! It is also the largest city.", Toast.LENGTH_SHORT).show();
                    }
                    else if(selectedTorF.equalsIgnoreCase("False") &&
                            questionList.get(quiz.getProgress()).getSizeRank() == 1 ) {
                        Toast.makeText(QuizActivity.this, "Incorrect! It is also the largest city.", Toast.LENGTH_SHORT).show();
                    }
                    else if(selectedTorF.equalsIgnoreCase("True") &&
                            questionList.get(quiz.getProgress()).getSizeRank() != 1 ) {
                        Toast.makeText(QuizActivity.this, "Incorrect! It is NOT also the largest city.", Toast.LENGTH_SHORT).show();
                    }
                    else if(selectedTorF.equalsIgnoreCase("False") &&
                            questionList.get(quiz.getProgress()).getSizeRank() != 1 ) {
                        Toast.makeText(QuizActivity.this, "Correct! It is NOT also the largest city.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(QuizActivity.this, "You should not ever reach here", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        // descendant activity enable the up button
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        geographyQuizData.open();

        // i think need a check here if there is in progress quiz and if so get the quiz object saved
        // then quiz NATURAL JOIN takes NATURAL JOIN question set which will then hold the references
        // to the saved quiz questions, i might be able to help with this if necessary
        new QuizDBReader().execute();

        // will also need an async QuizDBWriter i think theres an example in the job leads app
    }

    /**
     * An AsyncTask class (it extends AsyncTask) to perform DB reading of quizzes, asynchronously.
     */
    private class QuizDBReader extends AsyncTask<Void, List<Question>> {
        /**
         * This method will run as a background process to read from db. It returns a row from
         * the questions db based on the passed in questionID (random # between 1 and 50)
         * @param params
         * @return a list of Quiz objects
         */
        @Override
        protected List<Question> doInBackground(Void... params) {
            //generate 6 random numbers without repeats
            List<Integer> numbers = new ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                numbers.add(i);
            }//end for
            Collections.shuffle(numbers, new Random());
            //hold the 6 random numbers here
            int count = 6;
            int[] rand = new int[count];
            for (int i = 0; i < count; i++) {
                int randomNumber = numbers.get(i);
                rand[i] = randomNumber;
            }//end for

            // update the question sets foreign keys
            questionSet.setQ1(rand[0]);
            questionSet.setQ2(rand[1]);
            questionSet.setQ2(rand[2]);
            questionSet.setQ2(rand[3]);
            questionSet.setQ2(rand[4]);
            questionSet.setQ2(rand[5]);

            Log.d(DEBUG_TAG, "Questions retrieved");
            return geographyQuizData.createQuestions(rand);
        }

        /**
         * This method will be automatically called by Android once the db reading background
         * process is finished. It will then populate the question and answer information for the
         * quiz question
         * @param questionList2 a list of question objects
         */
        @Override
        protected void onPostExecute(List<Question> questionList2) {
            Log.d(DEBUG_TAG, DEBUG_TAG + ": questionList2.size(): " + questionList2.size());
            questionList.addAll(questionList2);

            // set the first question
            showQuestion();
        }
    }

    private void showQuestion() {
        textView.setText("Which of the following is the capital city of "
                + questionList.get(quiz.getProgress()).getState() + "?");
        //get the three cities for the question in an array
        String [] shuffle = { questionList.get(quiz.getProgress()).getCapitalCity(),
                questionList.get(quiz.getProgress()).getSecondCity(),
                questionList.get(quiz.getProgress()).getThirdCity()};
        //shuffle the answer order
        List<String> answers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            answers.add(shuffle[i]);
        }//end for
        //shuffle the answer order
        Collections.shuffle(answers, new Random());
        //hold the 3 shuffled answers here
        int count = 3;
        String[] rand = new String[count];
        for (int i = 0; i < count; i++) {
            String randomAnswer = answers.get(i);
            rand[i] = randomAnswer;
        }//end for

        // set the buttons to be the city names in a shuffled order so its not the same each time
        radioButton1.setText(rand[0]);
        radioButton2.setText(rand[1]);
        radioButton3.setText(rand[2]);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // save in progress quiz
    }

    @Override
    protected void onResume() {
        super.onResume();
        // load in progress quiz
    }
}
