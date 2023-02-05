package com.example.videoshorts.model;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Channel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("channelName")
    @Expose
    private String channelName;
    @SerializedName("channelAvatar")
    @Expose
    private String channelAvatar;
    @SerializedName("headerBanner")
    @Expose
    private Object headerBanner;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("numFollows")
    @Expose
    private Object numFollows;
    @SerializedName("numVideos")
    @Expose
    private Object numVideos;
    @SerializedName("isOfficial")
    @Expose
    private Object isOfficial;
    @SerializedName("createdFrom")
    @Expose
    private Object createdFrom;
    @SerializedName("isFollow")
    @Expose
    private Object isFollow;
    @SerializedName("isOwner")
    @Expose
    private Object isOwner;
    @SerializedName("url")
    @Expose
    private Object url;
    @SerializedName("state")
    @Expose
    private Object state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelAvatar() {
        return channelAvatar;
    }

    public void setChannelAvatar(String channelAvatar) {
        this.channelAvatar = channelAvatar;
    }

    public Object getHeaderBanner() {
        return headerBanner;
    }

    public void setHeaderBanner(Object headerBanner) {
        this.headerBanner = headerBanner;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getNumFollows() {
        return numFollows;
    }

    public void setNumFollows(Object numFollows) {
        this.numFollows = numFollows;
    }

    public Object getNumVideos() {
        return numVideos;
    }

    public void setNumVideos(Object numVideos) {
        this.numVideos = numVideos;
    }

    public Object getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(Object isOfficial) {
        this.isOfficial = isOfficial;
    }

    public Object getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(Object createdFrom) {
        this.createdFrom = createdFrom;
    }

    public Object getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(Object isFollow) {
        this.isFollow = isFollow;
    }

    public Object getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Object isOwner) {
        this.isOwner = isOwner;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }
}
