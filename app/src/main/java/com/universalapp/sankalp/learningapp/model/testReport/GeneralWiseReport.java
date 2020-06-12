package com.universalapp.sankalp.learningapp.model.testReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralWiseReport {


    @SerializedName("quiz_id")
    @Expose
    private String quizId;
    @SerializedName("total_questions")
    @Expose
    private String totalQuestions;
    @SerializedName("total_attempted")
    @Expose
    private String totalAttempted;
    @SerializedName("total_correct")
    @Expose
    private String totalCorrect;

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(String totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getTotalAttempted() {
        return totalAttempted;
    }

    public void setTotalAttempted(String totalAttempted) {
        this.totalAttempted = totalAttempted;
    }

    public String getTotalCorrect() {
        return totalCorrect;
    }

    public void setTotalCorrect(String totalCorrect) {
        this.totalCorrect = totalCorrect;
    }

}
