package com.universalapp.sankalp.learningapp.model.chapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChapterDetails {
    @SerializedName("chapter_id")
    @Expose
    private String chapterId;
    @SerializedName("chapter_name")
    @Expose
    private String chapterName;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("medium_id")
    @Expose
    private String mediumId;
    @SerializedName("standard_id")
    @Expose
    private String standardId;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("material")
    @Expose
    private String material;

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getMediumId() {
        return mediumId;
    }

    public void setMediumId(String mediumId) {
        this.mediumId = mediumId;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
