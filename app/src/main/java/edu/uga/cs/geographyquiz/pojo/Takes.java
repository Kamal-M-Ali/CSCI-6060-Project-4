package edu.uga.cs.geographyquiz.pojo;

/**
 * A POJO class representing a Takes record in the database. This is the many-to-many relationship
 * record between a Quiz and QuestionSet record. The primary key is equal to (quizId, questionSetId)
 * and both attributes will be set to -1 if not persisted in the database yet; otherwise they will
 * be equal to foreign key values referencing a record in the Quiz and QuestionSet tables respectively.
 */
public class Takes {
    private Integer quizId;
    private Integer questionSetId;

    Takes() {
        this.quizId = -1;
        this.questionSetId = -1;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Integer getQuestionSetId() {
        return questionSetId;
    }

    public void setQuestionSetId(Integer questionSetId) {
        this.questionSetId = questionSetId;
    }

    @Override
    public String toString() {
        return "Takes{" +
                "quizId=" + quizId +
                ", questionSetId=" + questionSetId +
                '}';
    }
}
