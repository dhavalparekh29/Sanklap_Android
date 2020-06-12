package com.universalapp.sankalp.learningapp.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.universalapp.sankalp.learningapp.model.medium.MediumDetails;
import com.universalapp.sankalp.learningapp.model.standard.StandardList;

import java.util.List;

public class LoginResponse {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("user")
    @Expose
    private LoginUser user;
    @SerializedName("user_plan")
    @Expose
    private UserPlan userPlan;

    @SerializedName("standard")
    @Expose
    private StandardList standard;
    @SerializedName("medium")
    @Expose
    private MediumDetails medium;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public LoginUser getUser() {
        return user;
    }

    public void setUser(LoginUser user) {
        this.user = user;
    }

    public UserPlan getUserPlan() {
        return userPlan;
    }

    public void setUserPlan(UserPlan userPlan) {
        this.userPlan = userPlan;
    }


    public StandardList getStandard() {
        return standard;
    }

    public void setStandard(StandardList standard) {
        this.standard = standard;
    }

    public MediumDetails getMedium() {
        return medium;
    }

    public void setMedium(MediumDetails medium) {
        this.medium = medium;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
