package edu.uga.cs.geographyquiz.pojo;

/**
 * A POJO class representing a Questions record in the database. These all are already stored
 * in the database.
 */
public class Questions {
    private Integer questionId;
    private String state;
    private String capitalCity;
    private String secondCity;
    private String thirdCity;
    private Integer statehood;
    private Integer capitalSince;
    private Integer sizeRank;

    public Questions(Integer questionId, String state, String capitalCity, String secondCity, String thirdCity, Integer statehood, Integer capitalSince, Integer sizeRank) {
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
