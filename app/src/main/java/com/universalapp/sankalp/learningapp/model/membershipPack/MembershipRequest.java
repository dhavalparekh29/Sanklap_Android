package com.universalapp.sankalp.learningapp.model.membershipPack;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MembershipRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("plan_id")
    @Expose
    private String planId;
    @SerializedName("plan_detail_id")
    @Expose
    private String planDetailId;
    @SerializedName("standard_id")
    @Expose
    private String standardId;
    @SerializedName("medium_id")
    @Expose
    private String mediumId;
    @SerializedName("MID")
    @Expose
    private String mid;
    @SerializedName("ORDER_ID")
    @Expose
    private String orderID;
    @SerializedName("subjects")
    @Expose
    private List<MembershipSubjectSelection> subjects = null;

    @SerializedName("chapters")
    @Expose
    private List<MembershipChapterSelection> chapters = null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
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

    public List<MembershipSubjectSelection> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<MembershipSubjectSelection> subjects) {
        this.subjects = subjects;
    }

    public List<MembershipChapterSelection> getChapters() {
        return chapters;
    }

    public void setChapters(List<MembershipChapterSelection> chapters) {
        this.chapters = chapters;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPlanDetailId() {
        return planDetailId;
    }

    public void setPlanDetailId(String planDetailId) {
        this.planDetailId = planDetailId;
    }
}
