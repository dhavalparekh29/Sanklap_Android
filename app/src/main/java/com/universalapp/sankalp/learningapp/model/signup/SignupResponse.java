package com.universalapp.sankalp.learningapp.model.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.universalapp.sankalp.learningapp.model.login.LoginUser;
import com.universalapp.sankalp.learningapp.model.user.UserDetails;

public class SignupResponse {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("user")
    @Expose
    private LoginUser user;
    @SerializedName("msg")
    @Expose
    private String msg;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
