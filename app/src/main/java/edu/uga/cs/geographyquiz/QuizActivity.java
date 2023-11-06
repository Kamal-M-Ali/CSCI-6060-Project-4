package edu.uga.cs.geographyquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

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
    private RadioGroup radioGroup2;
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

        ////////////////////////////////////////////////////////////////////////////////////////////
        /*
        //generate 6 random numbers without repeats
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            numbers.add(i);
        }//end for
        Collections.shuffle(numbers, new Random());
         //hold the 6 random numbers here
        for (int i = 0; i < count; i++) {
            int randomNumber = numbers.get(i);
            rand[i] = randomNumber;
        }//end for
        ////////////////////////////////////////////////////////////////////////////////////////////
         */

        textView = findViewById(R.id.textView);
        radioGroup = findViewById((R.id.radioGroup));

        /*
        QuestionSet qset = new QuestionSet(rand[0],rand[1],rand[2],
                rand[3],rand[4],rand[5]);

        Questions q1 = geographyQuizData.createQuestion(rand[0]);

        String str = String.valueOf(q1.getQuestionId());
         */
        // descendant activity enable the up button
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        geographyQuizData.open();

        //textView.setText(str);
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

    /* QUIZ FRAGMENT STUFF
    public QuizFragment()
    {
        // required default constructor
    }

    public static QuizFragment newInstance( int questionIndex ) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putInt( "question", questionIndex );
        fragment.setArguments( args );
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false );
    }
     */

    private void showQuestion() {
        textView.setText("Which of the following is the capital city of "
                + questionList.get(quiz.getProgress()).getState() + "?");

        // set the buttons to be the city names in a shuffled order so its not the same each time
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
