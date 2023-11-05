package edu.uga.cs.geographyquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SplashActivity extends AppCompatActivity {

    private Button splashButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //create a toolbar
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            //ab.setTitle("Push the Button!");
            ab.setDisplayHomeAsUpEnabled(false);
        }
        String strInfo;
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.splash_text);
            byte[] b = new byte[0];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in_s.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            in_s.close();
            strInfo = new String(byteArrayOutputStream.toByteArray(), "UTF-8");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TextView textView = findViewById((R.id.textView));
        textView.setText( strInfo );



        splashButton = findViewById(R.id.button);

        /**
         * When this button is pressed it will take the user to a new activity that will display
         * an overview about the country indicated by the spinner
         *
         * @param v this view will collect intents to display in the new activity
         */
        splashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
