package edu.uga.cs.geographyquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends MenuActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // descendant activity enable up button, this will take the user back to the main activity
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        textView = findViewById(R.id.textView3);

        int correct = getIntent().getIntExtra("RESULTS", 0);
        double score = correct / 12.0 * 100;
        StringBuilder res = new StringBuilder();
        res.append(String.format("You got %d out of 12 questions correct for a score of %.2f%%.", correct, score));

        if (correct <= 4)
            res.append(" Better luck next time!");
        else if (correct <= 8)
            res.append(" Not bad!");
        else if (correct <= 11)
            res.append(" Good job!");
        else
            res.append(" A perfect score!");

        textView.setText(res.toString());
    }
}