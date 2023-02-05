package com.example.videoshorts.viewModel;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.videoshorts.api.ApiVideo;
import com.example.videoshorts.model.ListVideo;
import com.example.videoshorts.model.Video;
import com.example.videoshorts.model.VideoData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailVideoViewModel extends ViewModel {
    private final MutableLiveData<Video> video = new MutableLiveData<>();
    private final MutableLiveData<List<Video>> listVideos = new MutableLiveData<>();
    private final ArrayList<Video> videoArrayList = new ArrayList<>();
    private final MutableLiveData<Long> idVideoClick = new MutableLiveData<>();

    public MutableLiveData<List<Video>> getListVideos() {
        return listVideos;
    }

    public ArrayList<Video> getVideoArrayList() {
        return videoArrayList;
    }

    public void setDetailVideo(long id, Context context) {
        ApiVideo.API_VIDEO.getVideoDetail(id, "0966409095", 123, 123).enqueue(new Callback<VideoData>() {
            @Override
            public void onResponse(@NonNull Call<VideoData> call, @NonNull Response<VideoData> response) {
                if (response.body() != null && response.body().getVideo() != null) {
                    video.setValue(Objects.requireNonNull(response.body()).getVideo());
                } else {
                    video.setValue(new Video());
                }
            }

            @Override
            public void onFailure(@NonNull Call<VideoData> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                video.setValue(new Video());
            }
        });
    }

    public void getListVideoRelative(long id, int page, Context context) {
        ApiVideo.API_VIDEO.getVideoRelated(id, "0969633777", 123, 123, page, 10, "12asd").enqueue(new Callback<ListVideo>() {
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

    public MutableLiveData<Video> getVideo() {
        return video;
    }

    public void setIdVideoClick(long id) {
        idVideoClick.setValue(id);
    }

    public MutableLiveData<Long> getIdVideoClick() {
        return idVideoClick;
    }
}
