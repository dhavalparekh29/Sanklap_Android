package com.universalapp.sankalp.learningapp.model.chapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChapterResponse {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("chapter")
    @Expose
    private List<ChapterDetails> chapter = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<ChapterDetails> getChapter() {
        return chapter;
    }

    public void setChapter(List<ChapterDetails> chapter) {
        this.chapter = chapter;
    }

}
