package edu.uga.cs.geographyquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.uga.cs.geographyquiz.pojo.QuestionSet;
import edu.uga.cs.geographyquiz.pojo.Questions;
import edu.uga.cs.geographyquiz.pojo.Quiz;

/**
 * The quiz taking activity. Does not extend MenuActivity since the project requirements state to not
 * present the user with the button to exit the app while the quiz is in progress.
 */
public class QuizActivity extends AppCompatActivity {
    private TextView textView;
    private TextView textView2;
    private RadioGroup radioGroup;
    private RadioGroup radioGroup2;
    private GeographyQuizData geographyQuizData;
    int count = 6;
    int[] randomQuestions = new int[count];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        geographyQuizData = new GeographyQuizData(getApplicationContext());

        ////////////////////////////////////////////////////////////////////////////////////////////
        //generate 6 random numbers without repeats
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            numbers.add(i);
        }//end for
        Collections.shuffle(numbers, new Random());
         //hold the 6 random numbers here
        for (int i = 0; i < count; i++) {
            int randomNumber = numbers.get(i);
            randomQuestions[i] = randomNumber;
        }//end for
        ////////////////////////////////////////////////////////////////////////////////////////////

        textView = findViewById(R.id.textView);
        radioGroup = findViewById((R.id.radioGroup));

        QuestionSet qset = new QuestionSet(randomQuestions[0],randomQuestions[1],randomQuestions[2],
                randomQuestions[3],randomQuestions[4],randomQuestions[5]);

        Questions q1 = geographyQuizData.createQuestion(randomQuestions[0]);

        String str = q1.getState();



        // descendant activity enable the up button
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        geographyQuizData.open();
        //new QuizDBReader().execute();
        textView.setText(str);

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
