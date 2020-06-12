package com.universalapp.sankalp.learningapp.model.subject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectResponse {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("subject")
    @Expose
    private List<SubjectDetails> subject = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<SubjectDetails> getSubject() {
        return subject;
    }

    public void setSubject(List<SubjectDetails> subject) {
        this.subject = subject;
    }
}
