package edu.uga.cs.geographyquiz.pojo;

import androidx.annotation.NonNull;

import java.util.Date;

/**
 * A POJO class representing a Quiz record in the database. The primary key will be -1 if it
 * has not been persisted by the database yet; otherwise it will equal the db table's primary key
 * value.
 */
public class Quiz {
    private Integer quizId;
    private String date;
    private Integer result;
    private Integer progress;

    public Quiz() {
        this.quizId = -1;
        this.date = new Date().toString();
        this.result = 0;
        this.progress = 0;
    }
    public Quiz(String date, Integer result, Integer progress) {
        this.quizId = -1;
        this.date = date;
        this.result = result;
        this.progress = progress;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizId=" + quizId +
                ", date='" + date + '\'' +
                ", result=" + result +
                ", progress=" + progress +
                '}';
    }
}
