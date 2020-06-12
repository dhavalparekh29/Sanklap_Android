package com.universalapp.sankalp.learningapp.model.video;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoDetail {

    @SerializedName("video_id")
    @Expose
    private String videoId;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("topic_id")
    @Expose
    private String topicId;
    @SerializedName("chapter_id")
    @Expose
    private String chapterId;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("medium_id")
    @Expose
    private String mediumId;
    @SerializedName("standard_id")
    @Expose
    private String standardId;
    @SerializedName("topic_name")
    @Expose
    private String topicName;
    @SerializedName("active")
    @Expose
    private String active;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
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

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
