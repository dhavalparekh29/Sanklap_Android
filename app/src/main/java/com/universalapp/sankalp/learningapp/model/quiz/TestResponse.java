package com.universalapp.sankalp.learningapp.model.quiz;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestResponse {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("time_per_question")
    @Expose
    private Long timePerQuestion;
    @SerializedName("total_questions")
    @Expose
    private Long totalQuestions;
    @SerializedName("questions")
    @Expose
    private List<QuestionDetails> questions = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Long getTimePerQuestion() {
        return timePerQuestion;
    }

    public void setTimePerQuestion(Long timePerQuestion) {
        this.timePerQuestion = timePerQuestion;
    }

    public Long getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Long totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public List<QuestionDetails> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDetails> questions) {
        this.questions = questions;
    }
}
