package edu.uga.cs.geographyquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.geographyquiz.pojo.QuestionSet;
import edu.uga.cs.geographyquiz.pojo.Quiz;
import edu.uga.cs.geographyquiz.pojo.Takes;

/**
 * This class facilitates database interactions.
 */
public class GeographyQuizData {
    public static final String DEBUG_TAG = "GeographyQuizData";
    private SQLiteDatabase db;
    private final SQLiteOpenHelper dbHelper;
    private static final String[] QUESTIONS_COLUMNS = {
            DBHelper.QUESTIONS_COLUMN_ID,
            DBHelper.QUESTIONS_COLUMN_STATE,
            DBHelper.QUESTIONS_COLUMN_CAPITAL,
            DBHelper.QUESTIONS_COLUMN_SECOND,
            DBHelper.QUESTIONS_COLUMN_THIRD,
            DBHelper.QUESTIONS_COLUMN_STATEHOOD,
            DBHelper.QUESTIONS_COLUMN_SINCE,
            DBHelper.QUESTIONS_COLUMN_SIZE_RANK
    };
    private static final String[] QUIZ_COLUMNS = {
            DBHelper.QUIZ_COLUMN_ID,
            DBHelper.QUIZ_COLUMN_DATE,
            DBHelper.QUIZ_COLUMN_RESULT,
            DBHelper.QUIZ_COLUMN_PROGRESS
    };
    private static final String[] QUESTION_SET_COLUMNS = {
            DBHelper.QUESTION_SET_COLUMN_ID,
            DBHelper.QUESTION_SET_COLUMN_Q1,
            DBHelper.QUESTION_SET_COLUMN_Q2,
            DBHelper.QUESTION_SET_COLUMN_Q3,
            DBHelper.QUESTION_SET_COLUMN_Q4,
            DBHelper.QUESTION_SET_COLUMN_Q5,
            DBHelper.QUESTION_SET_COLUMN_Q6
    };

    public GeographyQuizData(Context context)
    {
        this.dbHelper = DBHelper.getInstance(context);
    }

    /**
     * Opens the database if it is not currently open.
     */
    public void open() {
        if (db != null) {
            db = dbHelper.getWritableDatabase();
            Log.d(DEBUG_TAG, "Database open");
        }
    }

    /**
     * Will close the database if it is currently open.
     */
    public void close() {
        if(dbHelper != null) {
            dbHelper.close();
            Log.d(DEBUG_TAG, "Database closed");
        }
    }

    /**
     * @return true if the database is open; false otherwise
     */
    public boolean isDBOpen() { return db.isOpen(); }


    /**
     * This method is used to restore persistent objects stored as rows in the Quiz table in the
     * database. For each retrieved row we create a new Quiz (Java POJO object) instance and add it
     * to the list.
     * @return a list of Quiz objects
     */
    public List<Quiz> retrievePastQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();

        int columnIndex;
        try (Cursor cursor = db.query(DBHelper.TABLE_QUIZ, QUIZ_COLUMNS,
                null, null, null, null, null)) {

            // get all quiz results
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    if (cursor.getColumnCount() >= QUIZ_COLUMNS.length) {
                        // get all attribute values of this quiz
                        columnIndex = cursor.getColumnIndex(DBHelper.QUIZ_COLUMN_ID);
                        int id = cursor.getInt(columnIndex);
                        columnIndex = cursor.getColumnIndex(DBHelper.QUIZ_COLUMN_DATE);
                        String datetime = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex(DBHelper.QUIZ_COLUMN_PROGRESS);
                        int progress = cursor.getInt(columnIndex);
                        columnIndex = cursor.getColumnIndex(DBHelper.QUIZ_COLUMN_RESULT);
                        int result = cursor.getInt(columnIndex);

                        // create a new Quiz object and set its state to the retrieved values
                        Quiz quiz = new Quiz(datetime, progress, result);
                        quiz.setQuizId(id); // set the id (the primary key) of this object
                        // add it to the list
                        quizzes.add(quiz);
                        Log.d(DEBUG_TAG, "Retrieved Quiz: " + quiz);
                    }
                }
            }

            if (cursor != null) {
                Log.d(DEBUG_TAG, "Number of records from DB: " + cursor.getCount());
            } else
                Log.d(DEBUG_TAG, "Number of records from DB: 0");
        } catch (Exception e) {
            Log.d(DEBUG_TAG, "Exception caught: " + e);
        }

        // return the list of retrieved quizzes
        return quizzes;
    }

    public Takes storeQuiz(Quiz quiz, QuestionSet questionSet) {
        return null;
    }
}
