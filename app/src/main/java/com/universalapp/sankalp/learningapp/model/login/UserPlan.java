package com.universalapp.sankalp.learningapp.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPlan {


    @SerializedName("order_id")
    @Expose
    private String orderId;
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
    @SerializedName("valid_from")
    @Expose
    private String validFrom;
    @SerializedName("valid_to")
    @Expose
    private String validTo;
    @SerializedName("transaction_date")
    @Expose
    private String transactionDate;
    @SerializedName("active")
    @Expose
    private String active;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
