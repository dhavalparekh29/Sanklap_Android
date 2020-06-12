package com.universalapp.sankalp.learningapp.model.subject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectDetails {

    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("standard_id")
    @Expose
    private String standardId;
    @SerializedName("medium_id")
    @Expose
    private String mediumId;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

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

}