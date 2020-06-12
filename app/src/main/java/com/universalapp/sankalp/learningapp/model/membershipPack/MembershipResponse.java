package com.universalapp.sankalp.learningapp.model.membershipPack;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MembershipResponse {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("plan")
    @Expose
    private List<MembershipDetail> plan = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<MembershipDetail> getPlan() {
        return plan;
    }

    public void setPlan(List<MembershipDetail> plan) {
        this.plan = plan;
    }

}
