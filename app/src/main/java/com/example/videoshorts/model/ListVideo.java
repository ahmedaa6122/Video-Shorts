package com.example.videoshorts.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ListVideo implements Serializable {
    @SerializedName("data")
    private List<Video> listVideo;

    public List<Video> getListVideo() {
        return listVideo;
    }

}
