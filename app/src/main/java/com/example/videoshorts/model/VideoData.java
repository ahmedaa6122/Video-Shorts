package com.example.videoshorts.model;

import com.google.gson.annotations.SerializedName;

public class VideoData {
    @SerializedName("data")
    private Video video;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

}