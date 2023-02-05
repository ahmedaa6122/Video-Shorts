package com.example.videoshorts.model;

public class VideoWatch {
    int idVideo;
    String videoName;
    String channelName;
    int totalView;
    String imageUrl;
    String timeVideo;

    public VideoWatch() {
    }

    public VideoWatch(int idVideo, String videoName, String channelName, int totalView, String imageUrl, String timeVideo) {
        this.idVideo = idVideo;
        this.videoName = videoName;
        this.channelName = channelName;
        this.totalView = totalView;
        this.imageUrl = imageUrl;
        this.timeVideo = timeVideo;
    }

    public String getTimeVideo() {
        return timeVideo;
    }

    public int getIdVideo() {
        return idVideo;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getChannelName() {
        return channelName;
    }

    public int getTotalView() {
        return totalView;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
