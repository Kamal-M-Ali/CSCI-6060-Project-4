package edu.uga.cs.geographyquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
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
    private GeographyQuizData geographyQuizData;
    private Quiz quiz;
    private QuestionSet questionSet;
    private List<Question> questionList;
    private ViewPager2 viewPager2;
    private int prevResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        geographyQuizData = new GeographyQuizData(getApplicationContext());
        geographyQuizData.open();


        // check for partial quiz data here
        new QuizActivity.QuizDBReader().execute();

        quiz = new Quiz();
        questionSet = new QuestionSet();
        questionList = new ArrayList<>();
        viewPager2 = findViewById(R.id.viewPager2);
        prevResult = 0;

        // disable up button when taking a quiz
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(false);


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
            //questionList.add(new Question()); // add a dummy quiz for registering final swipe

            // TODO: this now needs to be updated in the case that this was the restoration of a partial quiz
            questionSet.setQ1(questionList.get(0).getQuestionId());
            questionSet.setQ2(questionList.get(1).getQuestionId());
            questionSet.setQ3(questionList.get(2).getQuestionId());
            questionSet.setQ4(questionList.get(3).getQuestionId());
            questionSet.setQ5(questionList.get(4).getQuestionId());
            questionSet.setQ6(questionList.get(5).getQuestionId());

            QuestionRecyclerAdapter adapter = new QuestionRecyclerAdapter(questionList, quiz);
            viewPager2.setAdapter(adapter);
            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                private boolean settled = false;

                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                    // out of bounds swipe detection
                    // (see https://stackoverflow.com/questions/64224874/detect-swiping-out-of-bounds-in-androids-viewpager2)
                    if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                        settled = false;
                    }
                    if (state == ViewPager2.SCROLL_STATE_SETTLING) {
                        settled = true;
                    }
                    if (state == ViewPager2.SCROLL_STATE_IDLE && !settled) {
                        quiz.setProgress(quiz.getProgress() + 1); // they finished the last question
                        new QuizWriter().execute(new Pair<>(quiz, questionSet)); // write the result to the db
                    }
                }

                /**
                 * This method will be invoked when a new page becomes selected. Animation is not
                 * necessarily complete.
                 *
                 * @param position Position index of the new selected page.
                 */
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    if (position == 0) return; // skip if its the start of the quiz

                    switch (quiz.getResult() - prevResult) {
                        case 0:
                            Toast.makeText(getApplicationContext(), "Sorry, you missed both questions. +0 points.", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), "You got one question correct. +1 point.", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), "Good job! You got both questions correct. +2 points.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    Log.d(DEBUG_TAG, "Current quiz result: " + quiz.getResult());
                    prevResult = quiz.getResult();
                    quiz.setProgress(quiz.getProgress() + 1);
                }
            });
        }
    }

    /**
     * An AsyncTask class (it extends AsyncTask) to perform DB writing of quizzes, asynchronously.
     */
    public class QuizWriter extends AsyncTask<Pair<Quiz, QuestionSet>, Pair<Quiz, QuestionSet>> {
        /**
         * This method will run as a background process to write into db. It will be automatically
         * invoked by Android, when we call the execute method.
         * @param takes a Java Pair object containing the Quiz and QuestionSet
         * @return
         */
        @SafeVarargs
        @Override
        protected final Pair<Quiz, QuestionSet> doInBackground(Pair<Quiz, QuestionSet>... takes) {
            geographyQuizData.storeQuiz(takes[0]);
            return takes[0];
        }

        /**
         * This method will be automatically called by Android once the writing to the database
         * in a background process has finished.  Note that doInBackground returns a Quiz object.
         * That object will be passed as argument to onPostExecute.
         * onPostExecute is like the notify method in an asynchronous method call discussed in class.
         * @param takes
         */
        @Override
        protected void onPostExecute(Pair<Quiz, QuestionSet> takes) {
            Log.d(DEBUG_TAG, "Quiz saved: " + takes.first);
            Log.d(DEBUG_TAG, "Question set save: " + takes.second);

            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("RESULTS", takes.first.getResult().intValue());
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isFinishing()) {
            // save the quiz
            //new QuizWriter().execute(new Pair<>(quiz, questionSet));
        }

        if(geographyQuizData != null) {
            geographyQuizData.close();
            Log.d(DEBUG_TAG, DEBUG_TAG + ".onPause(): closing DB");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(geographyQuizData != null && !geographyQuizData.isDBOpen()) {
            if (quiz == null) {
                // TODO: check for in progress quiz to load
            }

            geographyQuizData.open();
            Log.d(DEBUG_TAG, DEBUG_TAG + ".onResume(): opening DB");
        }
    }
}
