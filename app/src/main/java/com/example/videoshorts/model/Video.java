package com.example.videoshorts.model;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Video {

    public Video() {
    }

    public Video(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public Video(String videoTitle, Channel channel, Integer totalViews) {
        this.videoTitle = videoTitle;
        this.channel = channel;
        this.totalViews = totalViews;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cateId")
    @Expose
    private Integer cateId;
    @SerializedName("channelId")
    @Expose
    private Integer channelId;
    @SerializedName("videoTitle")
    @Expose
    private String videoTitle;
    @SerializedName("videoDesc")
    @Expose
    private String videoDesc;
    @SerializedName("videoMedia")
    @Expose
    private String videoMedia;
    @SerializedName("videoImage")
    @Expose
    private String videoImage;
    @SerializedName("imageThumb")
    @Expose
    private Object imageThumb;
    @SerializedName("imageSmall")
    @Expose
    private Object imageSmall;
    @SerializedName("videoTime")
    @Expose
    private String videoTime;
    @SerializedName("publishTime")
    @Expose
    private Long publishTime;
    @SerializedName("totalViews")
    @Expose
    private Integer totalViews;
    @SerializedName("totalLikes")
    @Expose
    private Integer totalLikes;
    @SerializedName("totalShares")
    @Expose
    private Integer totalShares;
    @SerializedName("totalComments")
    @Expose
    private Integer totalComments;
    @SerializedName("resolution")
    @Expose
    private Integer resolution;
    @SerializedName("aspecRatio")
    @Expose
    private String aspecRatio;
    @SerializedName("isAdaptive")
    @Expose
    private Integer isAdaptive;
    @SerializedName("isLike")
    @Expose
    private Integer isLike;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("cate")
    @Expose
    private Object cate;
    @SerializedName("channel")
    @Expose
    private Channel channel;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("hashId")
    @Expose
    private String hashId;
    @SerializedName("list_resolution")
    @Expose
    private Object listResolution;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public String getVideoMedia() {
        return videoMedia;
    }

    public void setVideoMedia(String videoMedia) {
        this.videoMedia = videoMedia;
    }

    public String getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }

    public Object getImageThumb() {
        return imageThumb;
    }

    public void setImageThumb(Object imageThumb) {
        this.imageThumb = imageThumb;
    }

    public Object getImageSmall() {
        return imageSmall;
    }

    public void setImageSmall(Object imageSmall) {
        this.imageSmall = imageSmall;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getTotalViews() {
        return totalViews;
    }

    public String getTotalViewsString() {
        return " - " + totalViews + " lượt xem";
    }

    public void setTotalViews(Integer totalViews) {
        this.totalViews = totalViews;
    }

    public Integer getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(Integer totalLikes) {
        this.totalLikes = totalLikes;
    }

    public Integer getTotalShares() {
        return totalShares;
    }

    public void setTotalShares(Integer totalShares) {
        this.totalShares = totalShares;
    }

    public Integer getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Integer totalComments) {
        this.totalComments = totalComments;
    }

    public Integer getResolution() {
        return resolution;
    }

    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public String getAspecRatio() {
        return aspecRatio;
    }

    public void setAspecRatio(String aspecRatio) {
        this.aspecRatio = aspecRatio;
    }

    public Integer getIsAdaptive() {
        return isAdaptive;
    }

    public void setIsAdaptive(Integer isAdaptive) {
        this.isAdaptive = isAdaptive;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getCate() {
        return cate;
    }

    public void setCate(Object cate) {
        this.cate = cate;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public Object getListResolution() {
        return listResolution;
    }

    public void setListResolution(Object listResolution) {
        this.listResolution = listResolution;
    }
}
