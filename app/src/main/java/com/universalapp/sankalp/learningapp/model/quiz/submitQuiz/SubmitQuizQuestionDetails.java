package com.universalapp.sankalp.learningapp.model.quiz.submitQuiz;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitQuizQuestionDetails {

    @SerializedName("question_id")
    @Expose
    private Integer questionId;
    @SerializedName("answer")
    @Expose
    private Integer answer;
    @SerializedName("attempted")
    @Expose
    private Integer attempted;
    @SerializedName("correct")
    @Expose
    private Integer correct;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Integer getAttempted() {
        return attempted;
    }

    public void setAttempted(Integer attempted) {
        this.attempted = attempted;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }
}
