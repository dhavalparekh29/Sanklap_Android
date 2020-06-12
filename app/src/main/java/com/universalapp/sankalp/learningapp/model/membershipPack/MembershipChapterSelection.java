package com.universalapp.sankalp.learningapp.model.membershipPack;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MembershipChapterSelection {


    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("chapter_id")
    @Expose
    private String chapterId;

    public MembershipChapterSelection(String subjectId, String chapterId) {
        this.subjectId = subjectId;
        this.chapterId = chapterId;
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

}
