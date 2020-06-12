package com.universalapp.sankalp.learningapp.model.membershipPack;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MembershipSubjectSelection {

    @SerializedName("subject_id")
    @Expose
    private String subjectId;

    public MembershipSubjectSelection(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
