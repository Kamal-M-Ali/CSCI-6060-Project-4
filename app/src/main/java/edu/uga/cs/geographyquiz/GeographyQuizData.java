package edu.uga.cs.geographyquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.uga.cs.geographyquiz.pojo.QuestionSet;
import edu.uga.cs.geographyquiz.pojo.Question;
import edu.uga.cs.geographyquiz.pojo.Quiz;

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
            DBHelper.QUIZ_COLUMN_PROGRESS,
            DBHelper.QUIZ_COLUMN_RESULT
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
                null, null, null, null, DBHelper.QUIZ_COLUMN_ID + " DESC")) {

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
                        Quiz quiz = new Quiz(datetime, result, progress);
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

    /**
     * This method returns a list of questions to be asked to the user.
     * @param questionIds a list of random integers to select from the table.
     * @return
     */
    public List<Question> createQuestions(int[] questionIds) {
        List<Question> questions = new ArrayList<>();
        StringBuilder where = new StringBuilder();

        // build the where clause
        for (int i = 0; i < questionIds.length; ++i) {
            where.append(DBHelper.QUESTIONS_COLUMN_ID).append("=").append(questionIds[i]);
            if (i != questionIds.length - 1)
                where.append(" OR ");
        }

        int colIndex;
        try (Cursor cursor = db.query(DBHelper.TABLE_QUESTIONS, QUESTIONS_COLUMNS,
                where.toString(), null, null, null, null)) {

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    if (cursor.getColumnCount() >= QUIZ_COLUMNS.length) {
                        colIndex = cursor.getColumnIndex(DBHelper.QUESTIONS_COLUMN_ID);
                        int questionId = cursor.getInt(colIndex);
                        colIndex = cursor.getColumnIndex(DBHelper.QUESTIONS_COLUMN_STATE);
                        String state = cursor.getString(colIndex);
                        colIndex = cursor.getColumnIndex(DBHelper.QUESTIONS_COLUMN_CAPITAL);
                        String capital = cursor.getString((colIndex));
                        colIndex = cursor.getColumnIndex(DBHelper.QUESTIONS_COLUMN_SECOND);
                        String second = cursor.getString((colIndex));
                        colIndex = cursor.getColumnIndex(DBHelper.QUESTIONS_COLUMN_THIRD);
                        String third = cursor.getString((colIndex));
                        colIndex = cursor.getColumnIndex(DBHelper.QUESTIONS_COLUMN_STATEHOOD);
                        int stateHood = cursor.getInt(colIndex);
                        colIndex = cursor.getColumnIndex(DBHelper.QUESTIONS_COLUMN_SINCE);
                        int since = cursor.getInt(colIndex);
                        colIndex = cursor.getColumnIndex(DBHelper.QUESTIONS_COLUMN_SIZE_RANK);
                        int rank = cursor.getInt(colIndex);

                        Question question = new Question(questionId, state, capital, second, third, stateHood, since, rank);
                        questions.add(question);
                    }
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
     * Store a new quiz in the database
     * @param takes a Java Pair object containing the Quiz and QuestionSet
     */
    public void storeQuiz(Pair<Quiz, QuestionSet> takes)
    {
        // creating the new records to be inserted
        ContentValues quiz = new ContentValues();
        if (takes.first.getQuizId() != null && takes.first.getQuizId() > 0) {
            // check if its an already persisted record
            quiz.put(DBHelper.QUIZ_COLUMN_ID, takes.first.getQuizId());
        }
        quiz.put(DBHelper.QUIZ_COLUMN_DATE, new Date().toString());
        quiz.put(DBHelper.QUIZ_COLUMN_RESULT, takes.first.getResult());
        quiz.put(DBHelper.QUIZ_COLUMN_PROGRESS, takes.first.getProgress());

        ContentValues questionSet = new ContentValues();
        if (takes.second.getQuestionSetId() != null && takes.second.getQuestionSetId() > 0) {
            // check if its an already persisted record
            questionSet.put(DBHelper.QUESTION_SET_COLUMN_ID, takes.second.getQuestionSetId());
        }
        questionSet.put(DBHelper.QUESTION_SET_COLUMN_Q1, takes.second.getQ1());
        questionSet.put(DBHelper.QUESTION_SET_COLUMN_Q2, takes.second.getQ2());
        questionSet.put(DBHelper.QUESTION_SET_COLUMN_Q3, takes.second.getQ3());
        questionSet.put(DBHelper.QUESTION_SET_COLUMN_Q4, takes.second.getQ4());
        questionSet.put(DBHelper.QUESTION_SET_COLUMN_Q5, takes.second.getQ5());
        questionSet.put(DBHelper.QUESTION_SET_COLUMN_Q6, takes.second.getQ6());

        // store the new records in their respective tables
        long quizId = db.replace(DBHelper.TABLE_QUIZ, null, quiz);
        long questionSetId = db.replace(DBHelper.TABLE_QUESTION_SET, null, questionSet);

        ContentValues relationship = new ContentValues();
        relationship.put(DBHelper.TAKES_COLUMN_QUIZ_ID, quizId);
        relationship.put(DBHelper.TAKES_COLUMN_QUESTION_SET_ID, questionSetId);
        db.replace(DBHelper.TABLE_TAKES, null, relationship);

        // store the id (the primary key) in the Quiz and QuestionSet instances, as they are now persistent
        takes.first.setQuizId((int) quizId);
        takes.second.setQuestionSetId((int) questionSetId);

        Log.d(DEBUG_TAG, "Stored quiz in db: " + takes.first);
        Log.d(DEBUG_TAG, "Stored question set in db: " + takes.second);
        Log.d(DEBUG_TAG, "Stored takes relation in db: " + relationship);
    }

    /**
     * This method returns a list of questions to be asked to the user.
     * @return a Java Pair object representing the last quiz taken
     */
    public Pair<Quiz, QuestionSet> getLastQuiz() {
        Pair<Quiz, QuestionSet> takes = null;

        int columnIndex;
        try (Cursor cursor = db.rawQuery("SELECT * " +
                "FROM " + DBHelper.TABLE_QUIZ
                + " NATURAL JOIN " + DBHelper.TABLE_TAKES
                + " NATURAL JOIN " + DBHelper.TABLE_QUESTION_SET
                + " ORDER BY " + DBHelper.TAKES_COLUMN_QUIZ_ID + " DESC " +
                "LIMIT 1", null);) {

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                if (cursor.getColumnCount() >= QUIZ_COLUMNS.length + QUESTION_SET_COLUMNS.length) {
                    // build the quiz object
                    columnIndex = cursor.getColumnIndex(DBHelper.QUIZ_COLUMN_ID);
                    int quizId = cursor.getInt(columnIndex);
                    columnIndex = cursor.getColumnIndex(DBHelper.QUIZ_COLUMN_DATE);
                    String datetime = cursor.getString(columnIndex);
                    columnIndex = cursor.getColumnIndex(DBHelper.QUIZ_COLUMN_PROGRESS);
                    int progress = cursor.getInt(columnIndex);
                    columnIndex = cursor.getColumnIndex(DBHelper.QUIZ_COLUMN_RESULT);
                    int result = cursor.getInt(columnIndex);
                    Quiz quiz = new Quiz(datetime, result, progress);
                    quiz.setQuizId(quizId);

                    // build the question set object
                    columnIndex = cursor.getColumnIndex(DBHelper.QUESTION_SET_COLUMN_ID);
                    int questionSetId = cursor.getInt(columnIndex);
                    columnIndex = cursor.getColumnIndex(DBHelper.QUESTION_SET_COLUMN_Q1);
                    int q1 = cursor.getInt(columnIndex);
                    columnIndex = cursor.getColumnIndex(DBHelper.QUESTION_SET_COLUMN_Q2);
                    int q2 = cursor.getInt(columnIndex);
                    columnIndex = cursor.getColumnIndex(DBHelper.QUESTION_SET_COLUMN_Q3);
                    int q3 = cursor.getInt(columnIndex);
                    columnIndex = cursor.getColumnIndex(DBHelper.QUESTION_SET_COLUMN_Q4);
                    int q4 = cursor.getInt(columnIndex);
                    columnIndex = cursor.getColumnIndex(DBHelper.QUESTION_SET_COLUMN_Q5);
                    int q5 = cursor.getInt(columnIndex);
                    columnIndex = cursor.getColumnIndex(DBHelper.QUESTION_SET_COLUMN_Q6);
                    int q6 = cursor.getInt(columnIndex);
                    QuestionSet questionSet = new QuestionSet(q1, q2, q3, q4, q5, q6);
                    questionSet.setQuestionSetId(questionSetId);

                    takes = new Pair<>(quiz, questionSet);
                }
            }
            if (cursor != null) {
                Log.d(DEBUG_TAG, "Retrieved row data");
            } else
                Log.d(DEBUG_TAG, "Did not retrieve row data");
        } catch (Exception e) {
            Log.e(DEBUG_TAG, "Exception caught: " + e);
        }
        return takes;
    }
}
