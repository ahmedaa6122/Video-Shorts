package com.example.videoshorts.api;

import com.example.videoshorts.model.ListVideo;
import com.example.videoshorts.model.VideoData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiVideo {
    Gson gson = new GsonBuilder().create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://freeapi.kakoak.tls.tl/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    ApiVideo API_VIDEO = retrofit.create(ApiVideo.class);

    @Headers({"Accept-Language:en"})
    @GET("video-service/v1/video/hot")
    Call<ListVideo> getDataVideo(@Query("msisdn") String msisdn,
                                 @Query("timestamp") int timestamp,
                                 @Query("security") int security,
                                 @Query("page") int page,
                                 @Query("size") int size,
                                 @Query("lastHashId") String lastHashId);

    @Headers({"Accept-Language:en"})
    @GET("video-service/v1/video/{id}/info")
    Call<VideoData> getVideoDetail(@Path("id") long videoId,
                                   @Query("msisdn") String msisdn,
                                   @Query("timestamp") int timestamp,
                                   @Query("security") int security);

    @Headers({"Accept-Language:en"})
    @GET("video-service/v1/video/{id}/related")
    Call<ListVideo> getVideoRelated(@Path("id") long videoId,
                                    @Query("msisdn") String msisdn,
                                    @Query("timestamp") int timestamp,
                                    @Query("security") int security,
                                    @Query("page") int page,
                                    @Query("size") int size,
                                    @Query("lastHashId") String lastHashId);

    @Headers({"Accept-Language:en"})
    @GET("video-service/v1/video/search")
    Call<ListVideo> getDataVideoSearch(@Query("msisdn") String msisdn,
                                       @Query("timestamp") int timestamp,
                                       @Query("security") int security,
                                       @Query("page") int page,
                                       @Query("size") int size,
                                       @Query("q") String q,
                                       @Query("lastHashId") String lastHashId);
}
