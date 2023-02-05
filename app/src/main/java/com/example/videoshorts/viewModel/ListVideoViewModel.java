package com.example.videoshorts.viewModel;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.videoshorts.api.ApiVideo;
import com.example.videoshorts.model.ListVideo;
import com.example.videoshorts.model.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListVideoViewModel extends ViewModel {
    private final MutableLiveData<List<Video>> listVideos = new MutableLiveData<>();
    private final ArrayList<Video> videoArrayList = new ArrayList<>();

    public MutableLiveData<List<Video>> getListVideos() {
        return listVideos;
    }

    public ArrayList<Video> getVideoArrayList() {
        return videoArrayList;
    }

    public void getApi(int page, Context context) {
        ApiVideo.API_VIDEO.getDataVideo("0969633777", 123, 123, page, 10, "13asd")
                .enqueue(new Callback<ListVideo>() {
                    @Override
                    public void onResponse(@NonNull Call<ListVideo> call, @NonNull Response<ListVideo> response) {
                        if (response.body() != null && response.body().getListVideo() != null) {
                            listVideos.setValue(response.body().getListVideo());
                            videoArrayList.addAll(Objects.requireNonNull(listVideos.getValue()));
                        } else {
                            listVideos.setValue(new ArrayList<>());
                            videoArrayList.addAll(Objects.requireNonNull(listVideos.getValue()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ListVideo> call, @NonNull Throwable t) {
                        Toast.makeText(context, "Video load error", Toast.LENGTH_SHORT).show();
                        listVideos.setValue(new ArrayList<>());
                        videoArrayList.addAll(Objects.requireNonNull(listVideos.getValue()));
                    }
                });
    }
}