package com.universalapp.sankalp.learningapp.model.standard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class StandardResponse {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("standard")
    @Expose
    private List<StandardList> standard = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<StandardList> getStandard() {
        return standard;
    }

    public void setStandard(List<StandardList> standard) {
        this.standard = standard;
    }
}
