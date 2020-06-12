package com.universalapp.sankalp.learningapp.model.medium;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediumDetails {

    @SerializedName("medium_id")
    @Expose
    private String mediumId;
    @SerializedName("medium_name")
    @Expose
    private String mediumName;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("standard_id")
    @Expose
    private String standardId;


    public MediumDetails(String mediumId, String mediumName) {
        this.mediumId = mediumId;
        this.mediumName = mediumName;
    }

    public String getMediumId() {
        return mediumId;
    }

    public void setMediumId(String mediumId) {
        this.mediumId = mediumId;
    }

    public String getMediumName() {
        return mediumName;
    }

    public void setMediumName(String mediumName) {
        this.mediumName = mediumName;
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

}
