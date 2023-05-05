package com.example.videoshorts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videoshorts.R;
import com.example.videoshorts.model.Video;
import com.example.videoshorts.viewModel.DetailVideoViewModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListVideoRelativeAdapter extends RecyclerView.Adapter<ListVideoRelativeAdapter.ViewHolder> {
    private final List<Video> videoList;
    private final Context context;

    public ListVideoRelativeAdapter(@NonNull List<Video> videoList, Context context) {
        videoList.clear();
        this.videoList = videoList;
        this.context = context;
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
        holder.views.setText(video.getTotalViews() + " lượt xem");
        Glide.with(context).load(video.getVideoImage()).into(holder.videoImage);
        int m = Integer.parseInt(video.getVideoTime()) / 60;
        int s = Integer.parseInt(video.getVideoTime()) - m * 60;
        String time = "";
        if (s < 10) {
            time = "0" + s;
        } else {
            time += s;
        }
        Glide.with(context).load(video.getChannel().getChannelAvatar()).into(holder.channelAvatar);
        holder.videoTime.setText(m + ":" + time);
        holder.itemVideo.setOnClickListener(v -> {
            DetailVideoViewModel detailVideoViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(DetailVideoViewModel.class);
            detailVideoViewModel.setIdVideoClick(video.getId());
        });
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
        private final LinearLayout itemVideo;
        private final CircleImageView channelAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoName = itemView.findViewById(R.id.tv_name_video);
            nameChannel = itemView.findViewById(R.id.tv_name_channel);
            views = itemView.findViewById(R.id.tv_totalViews);
            videoImage = itemView.findViewById(R.id.iv_img_video);
            videoTime = itemView.findViewById(R.id.tv_duration_video);
            itemVideo = itemView.findViewById(R.id.item_video);
            channelAvatar = itemView.findViewById(R.id.civ_img_thumb);
        }
    }
}
