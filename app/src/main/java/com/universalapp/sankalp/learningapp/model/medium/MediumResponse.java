package com.universalapp.sankalp.learningapp.model.medium;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MediumResponse {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("medium")
    @Expose
    private List<MediumDetails> medium = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<MediumDetails> getMedium() {
        return medium;
    }

    public void setMedium(List<MediumDetails> medium) {
        this.medium = medium;
    }
}
