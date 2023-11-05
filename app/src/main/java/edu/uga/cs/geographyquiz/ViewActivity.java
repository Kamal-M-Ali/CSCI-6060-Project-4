package edu.uga.cs.geographyquiz;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;

public class ViewActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        // descendant activity enable the up button
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
    }
}