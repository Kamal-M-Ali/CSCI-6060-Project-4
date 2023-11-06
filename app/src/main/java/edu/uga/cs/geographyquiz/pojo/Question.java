package edu.uga.cs.geographyquiz.pojo;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A POJO class representing a Questions record in the database. These all are already stored
 * in the database.
 */
public class Question {
    private Integer questionId;
    private String state;
    private String capitalCity;
    private String secondCity;
    private String thirdCity;
    private Integer statehood;
    private Integer capitalSince;
    private Integer sizeRank;

    private SQLiteDatabase db;
    private SQLiteOpenHelper quizDBHelper;
    private static final String[] allColumns = {
            edu.uga.cs.geographyquiz.DBHelper.QUESTIONS_COLUMN_ID,
            edu.uga.cs.geographyquiz.DBHelper.QUESTIONS_COLUMN_STATE,
            edu.uga.cs.geographyquiz.DBHelper.QUESTIONS_COLUMN_CAPITAL,
            edu.uga.cs.geographyquiz.DBHelper.QUESTIONS_COLUMN_SECOND,
            edu.uga.cs.geographyquiz.DBHelper.QUESTIONS_COLUMN_THIRD,
            edu.uga.cs.geographyquiz.DBHelper.QUESTIONS_COLUMN_STATEHOOD,
            edu.uga.cs.geographyquiz.DBHelper.QUESTIONS_COLUMN_SINCE,
            edu.uga.cs.geographyquiz.DBHelper.QUESTIONS_COLUMN_SIZE_RANK
    };

    public Question(Integer questionId, String state, String capitalCity, String secondCity, String thirdCity, Integer statehood, Integer capitalSince, Integer sizeRank) {
        this.questionId = questionId;
        this.state = state;
        this.capitalCity = capitalCity;
        this.secondCity = secondCity;
        this.thirdCity = thirdCity;
        this.statehood = statehood;
        this.capitalSince = capitalSince;
        this.sizeRank = sizeRank;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public String getSecondCity() {
        return secondCity;
    }

    public void setSecondCity(String secondCity) {
        this.secondCity = secondCity;
    }

    public String getThirdCity() {
        return thirdCity;
    }

    public void setThirdCity(String thirdCity) {
        this.thirdCity = thirdCity;
    }

    public Integer getStatehood() {
        return statehood;
    }

    public void setStatehood(Integer statehood) {
        this.statehood = statehood;
    }

    public Integer getCapitalSince() {
        return capitalSince;
    }

    public void setCapitalSince(Integer capitalSince) {
        this.capitalSince = capitalSince;
    }

    public Integer getSizeRank() {
        return sizeRank;
    }

    public void setSizeRank(Integer sizeRank) {
        this.sizeRank = sizeRank;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "questionId=" + questionId +
                ", state='" + state + '\'' +
                ", capitalCity='" + capitalCity + '\'' +
                ", secondCity='" + secondCity + '\'' +
                ", thirdCity='" + thirdCity + '\'' +
                ", statehood=" + statehood +
                ", capitalSince=" + capitalSince +
                ", sizeRank=" + sizeRank +
                '}';
    }
}
