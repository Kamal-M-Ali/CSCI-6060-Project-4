package edu.uga.cs.geographyquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;

/**
 * The main (splash) activity for navigating through the app.
 */
public class MainActivity extends MenuActivity {
    private Button takeQuizButton;
    private Button viewQuizButton;

    /**
     * Called by android. Creates the activity.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // defining views
        takeQuizButton = findViewById(R.id.buttonTakeQuiz);
        viewQuizButton = findViewById(R.id.buttonViewQuiz);

        // ancestor activity disable up button
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(false);
        // setting up event handlers
        takeQuizButton.setOnClickListener(view ->
                startActivity(new Intent(view.getContext(), QuizActivity.class))
        );

        viewQuizButton.setOnClickListener(view ->
                startActivity(new Intent(view.getContext(), ViewActivity.class))
        );
    }
}
