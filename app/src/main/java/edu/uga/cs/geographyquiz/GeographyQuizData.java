package edu.uga.cs.geographyquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    // Open the database
    public void open() {
        db = dbHelper.getWritableDatabase();
        Log.d(DEBUG_TAG, "Database open");
    }

    // Close the database
    public void close() {
        if(dbHelper != null) {
            dbHelper.close();
            Log.d(DEBUG_TAG, "Database closed");
        }
    }

    public boolean isDBOpen() { return db.isOpen(); }

    public List<Quiz> retrieveAllQuizzes() {
        return null;
    }

    public Takes storeQuiz(Quiz quiz, QuestionSet questionSet) {
        return null;
    }
}
