package com.universalapp.sankalp.learningapp.model.leadersBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeadersBoardResponse {

    @SerializedName("score")
    @Expose
    private List<LeadersBoardScore> score = null;
    @SerializedName("user_score")
    @Expose
    private LeadersBoardUserScore userScore;

    public List<LeadersBoardScore> getScore() {
        return score;
    }

    public void setScore(List<LeadersBoardScore> score) {
        this.score = score;
    }

    public LeadersBoardUserScore getUserScore() {
        return userScore;
    }

    public void setUserScore(LeadersBoardUserScore userScore) {
        this.userScore = userScore;
    }

}
