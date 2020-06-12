package com.universalapp.sankalp.learningapp.model.quiz.submitQuiz;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubmitQuizRequest {

    @SerializedName("standard_id")
    @Expose
    private String standardId;
    @SerializedName("medium_id")
    @Expose
    private String mediumId;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("chapter_id")
    @Expose
    private String chapterId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("quiz_type")
    @Expose
    private String quizType;
    @SerializedName("total_questions")
    @Expose
    private Integer totalQuestions;
    @SerializedName("total_attempted")
    @Expose
    private Integer totalAttempted;
    @SerializedName("total_correct")
    @Expose
    private Integer totalCorrect;
    @SerializedName("questions")
    @Expose
    private List<SubmitQuizQuestionDetails> questions = null;

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getMediumId() {
        return mediumId;
    }

    public void setMediumId(String mediumId) {
        this.mediumId = mediumId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Integer getTotalAttempted() {
        return totalAttempted;
    }

    public void setTotalAttempted(Integer totalAttempted) {
        this.totalAttempted = totalAttempted;
    }

    public Integer getTotalCorrect() {
        return totalCorrect;
    }

    public void setTotalCorrect(Integer totalCorrect) {
        this.totalCorrect = totalCorrect;
    }

    public List<SubmitQuizQuestionDetails> getQuestions() {
        return questions;
    }

    public void setQuestions(List<SubmitQuizQuestionDetails> questions) {
        this.questions = questions;
    }
}
