package com.universalapp.sankalp.learningapp.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPlanDetail {

    @SerializedName("order_detail_id")
    @Expose
    private String orderDetailId;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("chapter_id")
    @Expose
    private Object chapterId;

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Object getChapterId() {
        return chapterId;
    }

    public void setChapterId(Object chapterId) {
        this.chapterId = chapterId;
    }

}
