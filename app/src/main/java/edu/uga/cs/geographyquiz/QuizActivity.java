package edu.uga.cs.geographyquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * The quiz taking activity. Does not extend MenuActivity since the project requirements state to not
 * present the user with the button to exit the app while the quiz is in progress.
 */
public class QuizActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // descendant activity enable the up button
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
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
}