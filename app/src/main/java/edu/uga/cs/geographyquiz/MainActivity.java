package edu.uga.cs.geographyquiz;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "Country Quiz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(DEBUG_TAG, "MainActivity.onCreate(): savedInstanceState: " + savedInstanceState);

        setContentView(R.layout.activity_main);

        //create a toolbar
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Take me back!");
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }
}