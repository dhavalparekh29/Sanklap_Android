package com.universalapp.sankalp.learningapp.model.leadersBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeadersBoardScore {

    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("quiz_id")
    @Expose
    private String quizId;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
