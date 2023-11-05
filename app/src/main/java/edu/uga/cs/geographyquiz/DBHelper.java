package edu.uga.cs.geographyquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A singleton class that extends SQLiteOpenHelper, which Android uses to create, upgrade, delete an
 * SQLite database in an app.
 *
 * Access the only instance via the getInstance method.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = "DBHelper";
    private static final String DB_NAME = "GeographyQuiz.db";
    private static final int DB_VERSION = 1;

    // defining columns
    public static final String TABLE_QUESTIONS = "Questions";
    public static final String QUESTIONS_COLUMN_ID = "questionId";
    public static final String QUESTIONS_COLUMN_STATE = "state";
    public static final String QUESTIONS_COLUMN_CAPITAL = "capitalCity";
    public static final String QUESTIONS_COLUMN_SECOND = "secondCity";
    public static final String QUESTIONS_COLUMN_THIRD = "thirdCity";
    public static final String QUESTIONS_COLUMN_STATEHOOD = "statehood";
    public static final String QUESTIONS_COLUMN_SINCE = "capitalSince";
    public static final String QUESTIONS_COLUMN_SIZE_RANK = "sizeRank";
    public static final String TABLE_QUIZ = "Quiz";
    public static final String QUIZ_COLUMN_ID = "quizId";
    public static final String QUIZ_COLUMN_DATE = "date";
    public static final String QUIZ_COLUMN_RESULT = "result";
    public static final String QUIZ_COLUMN_PROGRESS = "progress";
    public static final String TABLE_QUESTION_SET = "QuestionSet";
    public static final String QUESTION_SET_COLUMN_ID = "questionSetId";
    public static final String QUESTION_SET_COLUMN_Q1 = "q1";
    public static final String QUESTION_SET_COLUMN_Q2 = "q2";
    public static final String QUESTION_SET_COLUMN_Q3 = "q3";
    public static final String QUESTION_SET_COLUMN_Q4 = "q4";
    public static final String QUESTION_SET_COLUMN_Q5 = "q5";
    public static final String QUESTION_SET_COLUMN_Q6 = "q6";
    public static final String TABLE_TAKES = "Takes";
    public static final String TAKES_COLUMN_QUIZ_ID = "quizId";
    public static final String TAKES_COLUMN_QUESTION_SET_ID = "questionSetId";

    /*private static final String CREATE_QUESTIONS =
            "CREATE TABLE " + TABLE_QUESTIONS + " ("
                + QUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QUESTIONS_COLUMN_STATE + " TEXT, "
                + QUESTIONS_COLUMN_CAPITAL + " TEXT, "
                + QUESTIONS_COLUMN_SECOND + " TEXT, "
                + QUESTIONS_COLUMN_THIRD + " TEXT, "
                + QUESTIONS_COLUMN_STATEHOOD + " INTEGER, "
                + QUESTIONS_COLUMN_SINCE + " INTEGER, "
                + QUESTIONS_COLUMN_SIZE_RANK + " INTEGER)";
    private static final String CREATE_QUIZ =
            "CREATE TABLE " + TABLE_QUIZ + " ("
                + QUIZ_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QUIZ_COLUMN_DATE + " TEXT, "
                + QUIZ_COLUMN_RESULT + " INTEGER, "
                + QUIZ_COLUMN_PROGRESS + " INTEGER)";
    private static final String CREATE_QUESTION_SET =
            "CREATE TABLE " + TABLE_QUESTION_SET + " ("
                + QUESTION_SET_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QUESTION_SET_COLUMN_Q1 + " INTEGER REFERENCES " + TABLE_QUESTIONS + "(" + QUESTIONS_COLUMN_ID + "), "
                + QUESTION_SET_COLUMN_Q2 + " INTEGER REFERENCES " + TABLE_QUESTIONS + "(" + QUESTIONS_COLUMN_ID + "), "
                + QUESTION_SET_COLUMN_Q3 + " INTEGER REFERENCES " + TABLE_QUESTIONS + "(" + QUESTIONS_COLUMN_ID + "), "
                + QUESTION_SET_COLUMN_Q4 + " INTEGER REFERENCES " + TABLE_QUESTIONS + "(" + QUESTIONS_COLUMN_ID + "), "
                + QUESTION_SET_COLUMN_Q5 + " INTEGER REFERENCES " + TABLE_QUESTIONS + "(" + QUESTIONS_COLUMN_ID + "), "
                + QUESTION_SET_COLUMN_Q6 + " INTEGER REFERENCES " + TABLE_QUESTIONS + "(" + QUESTIONS_COLUMN_ID + "))";
    private static final String CREATE_TAKES =
            "CREATE TABLE " + TABLE_TAKES + " ("
            + TAKES_COLUMN_QUIZ_ID + " INTEGER REFERENCES " + TABLE_QUIZ + "(" + QUIZ_COLUMN_ID + "), "
            + TAKES_COLUMN_QUESTION_SET_ID + " INTEGER REFERENCES " + TABLE_QUESTION_SET + "(" + QUESTION_SET_COLUMN_ID + "), "
            + "PRIMARY KEY(" + TAKES_COLUMN_QUIZ_ID + "," + TAKES_COLUMN_QUESTION_SET_ID + "))";*/

    // reference to only private instance
    private static DBHelper instance;

    /**
     * Private constructor. Creates the database if it doesn't exist yet, will only be called
     * once per application.
     * @param context the caller's context
     */
    private DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);

        File file = context.getDatabasePath(DB_NAME);
        if (!file.exists()) {
            try {
                getReadableDatabase();
                close();
                copyDatabase(context);

                Log.d(DEBUG_TAG, "Copied database.");
            } catch (IOException e) {
                Log.e(DEBUG_TAG, "error copying database: " + e.getMessage());
            }
        }
    }

    /**
     * This method is used to access this class's instance, following the singleton design pattern.
     * @param context the caller's context
     * @return the GeographyQuizDBHelper instance
     */
    public static synchronized DBHelper getInstance(Context context)
    {
        if (instance == null)
            instance = new DBHelper(context.getApplicationContext());
        return instance;
    }

    /**
     * This is a method to copy the database from the assets folder into the devices database folder.
     * @param context the caller's context
     * @throws IOException
     */
    private void copyDatabase(Context context) throws IOException
    {
        InputStream is = context.getAssets().open(DB_NAME);
        String fileName = context.getDatabasePath(DB_NAME).getPath();
        OutputStream fos = new FileOutputStream(fileName);

        byte[] buffer = new byte[1024 * 32];
        int length;
        while ((length = is.read(buffer)) > 0)
            fos.write(buffer, 0, length);

        fos.flush();
        fos.close();
        fos.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

}
