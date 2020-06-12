package com.universalapp.sankalp.learningapp.model.membershipPack;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MembershipDetail {

    @SerializedName("plan_id")
    @Expose
    private String planId;
    @SerializedName("plan_name")
    @Expose
    private String planName;
    @SerializedName("access_level")
    @Expose
    private String accessLevel;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("validity")
    @Expose
    private String validity;
    @SerializedName("validity_type")
    @Expose
    private String validityType;
    @SerializedName("material")
    @Expose
    private String material;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("quiz")
    @Expose
    private String quiz;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("upgradation_level")
    @Expose
    private String upgradationLevel;
    @SerializedName("plan_detail_id")
    @Expose
    private String planDetailId;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getValidityType() {
        return validityType;
    }

    public void setValidityType(String validityType) {
        this.validityType = validityType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getQuiz() {
        return quiz;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUpgradationLevel() {
        return upgradationLevel;
    }

    public void setUpgradationLevel(String upgradationLevel) {
        this.upgradationLevel = upgradationLevel;
    }

    public String getPlanDetailId() {
        return planDetailId;
    }

    public void setPlanDetailId(String planDetailId) {
        this.planDetailId = planDetailId;
    }
}
