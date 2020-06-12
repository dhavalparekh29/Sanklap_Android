package com.universalapp.sankalp.learningapp.model.standard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StandardList {

    @SerializedName("standard_id")
    @Expose
    private String standardId;
    @SerializedName("standard_name")
    @Expose
    private String standardName;
    @SerializedName("active")
    @Expose
    private String active;

    public String getStandardId() {
        return standardId;
    }

    public StandardList(String standardId, String standardName) {
        this.standardId = standardId;
        this.standardName = standardName;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
