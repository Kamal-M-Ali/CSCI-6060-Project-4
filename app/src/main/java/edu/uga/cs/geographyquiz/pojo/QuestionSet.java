package edu.uga.cs.geographyquiz.pojo;


import androidx.annotation.NonNull;

/**
 * A POJO class representing a QuestionSet record in the database. The primary key will be -1 if it
 * has not been persisted by the database yet; otherwise it will equal the db table's primary key
 * value.
 */
public class QuestionSet {
    private Integer questionSetId;
    private Integer q1;
    private Integer q2;
    private Integer q3;
    private Integer q4;
    private Integer q5;
    private Integer q6;

    // NOTE: The database schema requires all questions in the question set to be non null.
    public QuestionSet() {
        this.q1 = null;
        this.q2 = null;
        this.q3 = null;
        this.q4 = null;
        this.q5 = null;
        this.q6 = null;
    }

    public QuestionSet(Integer q1, Integer q2, Integer q3, Integer q4, Integer q5, Integer q6) {
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
    }

    public Integer getQuestionSetId() {
        return questionSetId;
    }

    public void setQuestionSetId(Integer questionSetId) {
        this.questionSetId = questionSetId;
    }

    public Integer getQ1() {
        return q1;
    }

    public void setQ1(Integer q1) {
        this.q1 = q1;
    }

    public Integer getQ2() {
        return q2;
    }

    public void setQ2(Integer q2) {
        this.q2 = q2;
    }

    public Integer getQ3() {
        return q3;
    }

    public void setQ3(Integer q3) {
        this.q3 = q3;
    }

    public Integer getQ4() {
        return q4;
    }

    public void setQ4(Integer q4) {
        this.q4 = q4;
    }

    public Integer getQ5() {
        return q5;
    }

    public void setQ5(Integer q5) {
        this.q5 = q5;
    }

    public Integer getQ6() {
        return q6;
    }

    public void setQ6(Integer q6) {
        this.q6 = q6;
    }

    @Override
    public String toString() {
        return "QuestionSet{" +
                "questionSetId=" + questionSetId +
                ", q1=" + q1 +
                ", q2=" + q2 +
                ", q3=" + q3 +
                ", q4=" + q4 +
                ", q5=" + q5 +
                ", q6=" + q6 +
                '}';
    }
}
