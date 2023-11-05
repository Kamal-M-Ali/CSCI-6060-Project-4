package edu.uga.cs.geographyquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.geographyquiz.pojo.Quiz;

/**
 * This is the activity used when the user is viewing past quizzes. It will display each quiz's
 * score and time taken.
 */
public class ViewActivity extends MenuActivity {
    private static final String DEBUG_TAG = "ViewActivity";
    private RecyclerView recyclerView;
    private TextView textViewEmpty;
    private List<Quiz> quizList;
    private GeographyQuizData geographyQuizData;

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
        setContentView(R.layout.activity_view);

        // descendant activity enable the up button
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        // defining variables
        recyclerView = findViewById(R.id.recyclerView);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        quizList = new ArrayList<>();
        geographyQuizData = new GeographyQuizData(getApplicationContext());

        // create a linear layout manager for the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        // open the database
        geographyQuizData.open();

        // asynchronously fetch past quiz results
        new QuizDBReader().execute();
    }

    /**
     * An AsyncTask class (it extends AsyncTask) to perform DB reading of quizzes, asynchronously.
     */
    private class QuizDBReader extends AsyncTask<Void, List<Quiz>> {
        /**
         * This method will run as a background process to read from db. It returns a list of
         * retrieved JobLead objects. It will be automatically invoked by Android, when we call the
         * execute method. in the onCreate callback (the job leads review activity is started).
         * @param params
         * @return a list of Quiz objects
         */
        @Override
        protected List<Quiz> doInBackground(Void... params) {
            List<Quiz> quizList2 = geographyQuizData.retrievePastQuizzes();
            Log.d(DEBUG_TAG, DEBUG_TAG + ": Quizzes retrieved: " + quizList2.size() );
            return quizList2;
        }

        /**
         * This method will be automatically called by Android once the db reading background process
         * is finished. It will then create and set an adapter to provide values for the RecyclerView.
         * onPostExecute is like the notify method in an asynchronous method call discussed in class.
         * @param quizList2 a list of Quiz objects
         */
        @Override
        protected void onPostExecute(List<Quiz> quizList2) {
            Log.d(DEBUG_TAG, DEBUG_TAG + ": quizList.size(): " + quizList2.size());
            quizList.addAll(quizList2);

            // create the RecyclerAdapter and set it for the RecyclerView
            QuizRecyclerAdapter recyclerAdapter = new QuizRecyclerAdapter(quizList);
            recyclerView.setAdapter(recyclerAdapter);

            // show a message if there are no results
            textViewEmpty.setVisibility(quizList.size() == 0 ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Open the database when the application resumes.
     */
    @Override
    public void onResume() {
        super.onResume();
        if(geographyQuizData != null && !geographyQuizData.isDBOpen()) {
            geographyQuizData.open();
            Log.d(DEBUG_TAG, DEBUG_TAG + ".onResume(): opening DB");
        }
    }

    /**
     * Close the database when the application is paused.
     */
    @Override
    public void onPause() {
        super.onPause();
        if(geographyQuizData != null) {
            geographyQuizData.close();
            Log.d(DEBUG_TAG, DEBUG_TAG + ".onPause(): closing DB");
        }
    }
}