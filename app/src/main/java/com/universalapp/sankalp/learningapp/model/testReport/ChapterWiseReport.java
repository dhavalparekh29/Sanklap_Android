package com.universalapp.sankalp.learningapp.model.testReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChapterWiseReport {

    @SerializedName("quiz_id")
    @Expose
    private String quizId;
    @SerializedName("chapter_id")
    @Expose
    private String chapterId;
    @SerializedName("chapter_name")
    @Expose
    private String chapterName;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;
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

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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
