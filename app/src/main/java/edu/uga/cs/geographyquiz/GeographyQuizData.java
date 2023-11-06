package edu.uga.cs.geographyquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.geographyquiz.pojo.QuestionSet;
import edu.uga.cs.geographyquiz.pojo.Questions;
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

    /**
     * This method creates a question based on the passed in questionID which. questionID is a
     * random number from 1-50
     * @param questionID a random int from 1-50 representing the state ID
     * @return
     */
    public Questions createQuestion(int questionID) {
        Questions questions = new Questions(questionID, "null", "null",
                "null", "null", -1, -1, -1);
        int colIndex;
        try (Cursor cursor = db.query(DBHelper.TABLE_QUESTIONS, QUESTIONS_COLUMNS,
                DBHelper.QUESTIONS_COLUMN_ID + "=" + questionID, null, null, null, null)) {

            // get all quiz data
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.getColumnCount() >= QUIZ_COLUMNS.length) {
                    colIndex = cursor.getColumnIndex( DBHelper.QUESTIONS_COLUMN_STATE );
                    String state = cursor.getString( colIndex );
                    colIndex = cursor.getColumnIndex( DBHelper.QUESTIONS_COLUMN_CAPITAL );
                    String capital = cursor.getString((colIndex));
                    colIndex = cursor.getColumnIndex( DBHelper.QUESTIONS_COLUMN_SECOND );
                    String second = cursor.getString((colIndex));
                    colIndex = cursor.getColumnIndex( DBHelper.QUESTIONS_COLUMN_THIRD );
                    String third = cursor.getString((colIndex));
                    colIndex = cursor.getColumnIndex( DBHelper.QUESTIONS_COLUMN_STATEHOOD);
                    int stateHood = Integer.parseInt(cursor.getString((colIndex)));
                    colIndex = cursor.getColumnIndex( DBHelper.QUESTIONS_COLUMN_SINCE);
                    int since = Integer.parseInt(cursor.getString((colIndex)));
                    colIndex = cursor.getColumnIndex( DBHelper.QUESTIONS_COLUMN_SIZE_RANK);
                    int rank = Integer.parseInt(cursor.getString((colIndex)));
                    questions = new Questions(questionID, state, capital,
                            second, third, stateHood, since, rank);
                    }
            }
            if (cursor != null) {
                Log.d(DEBUG_TAG, "Retrieved row data");
            } else
                Log.d(DEBUG_TAG, "Did not retrieve row data");
        } catch (Exception e) {
            Log.e(DEBUG_TAG, "Exception caught: " + e);
        }
        return questions;
    }

    /**
     * The class's public constructor. Call to create a GeographyQuizData object.
     * @param context the caller's context
     */
    public GeographyQuizData(Context context)
    {
        this.dbHelper = DBHelper.getInstance(context);
    }

    /**
     * Opens the database.
     */
    public void open() {
        db = dbHelper.getWritableDatabase();
        Log.d(DEBUG_TAG, "Database open");
    }

    /**
     * Closes the database.
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
    public boolean isDBOpen() { return db != null && db.isOpen(); }




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
            Log.e(DEBUG_TAG, "Exception caught: " + e);
        }

        // return the list of retrieved quizzes
        return quizzes;
    }

    public Takes storeQuiz(Quiz quiz, QuestionSet questionSet) {
        return null;
    }
}
