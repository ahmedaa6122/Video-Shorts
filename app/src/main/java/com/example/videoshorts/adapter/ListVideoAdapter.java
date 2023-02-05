package com.example.videoshorts.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videoshorts.R;
import com.example.videoshorts.model.Video;
import com.example.videoshorts.view.activity.DetailVideoActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListVideoAdapter extends RecyclerView.Adapter<ListVideoAdapter.ViewHolder> {
    private final List<Video> videoList;
    private final Context context, getActivity;

    public ListVideoAdapter(List<Video> videoList, Context context, Context getActivity) {
        this.videoList = videoList;
        this.context = context;
        this.getActivity = getActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = videoList.get(position);
        holder.videoName.setText(video.getVideoTitle());
        holder.nameChannel.setText(video.getChannel().getChannelName());
        holder.views.setText(video.getTotalViews() + " views");
        Glide.with(context).load(video.getVideoImage()).into(holder.videoImage);
        int m = Integer.parseInt(video.getVideoTime()) / 60;
        int s = Integer.parseInt(video.getVideoTime()) - m * 60;
        String time = "";
        if (s < 10) {
            time = "0" + s;
        } else {
            time += s;
        }
        holder.videoTime.setText(m + ":" + time);
        Glide.with(context).load(video.getChannel().getChannelAvatar()).into(holder.channel_avatar);
        Intent i = new Intent(getActivity, DetailVideoActivity.class);
        i.putExtra("idVideo", video.getId());
        holder.videoItem.setOnClickListener(v -> getActivity.startActivities(new Intent[]{i}));
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView videoName;
        private final TextView nameChannel;
        private final TextView views;
        private final TextView videoTime;
        private final ImageView videoImage;
        private final LinearLayout videoItem;
        private final CircleImageView channel_avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoName = itemView.findViewById(R.id.tv_name_video);
            nameChannel = itemView.findViewById(R.id.tv_name_channel);
            views = itemView.findViewById(R.id.tv_totalViews);
            videoImage = itemView.findViewById(R.id.iv_img_video);
            videoTime = itemView.findViewById(R.id.tv_duration_video);
            videoItem = itemView.findViewById(R.id.item_video);
            channel_avatar = itemView.findViewById(R.id.civ_img_thumb);
        }
    }
}