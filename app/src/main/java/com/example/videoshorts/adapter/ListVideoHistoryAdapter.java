package com.example.videoshorts.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videoshorts.R;
import com.example.videoshorts.model.VideoWatch;
import com.example.videoshorts.view.activity.DetailVideoActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ListVideoHistoryAdapter extends RecyclerView.Adapter<ListVideoHistoryAdapter.ViewHolder> {
    private final List<VideoWatch> arrayListWatched;
    private final Context context, getActivity;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public ListVideoHistoryAdapter(List<VideoWatch> arrayListWatched, Context context, Context getActivity) {
        this.arrayListWatched = arrayListWatched;
        this.context = context;
        this.getActivity = getActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoWatch videoWatch = arrayListWatched.get(position);
        holder.videoName.setText(videoWatch.getVideoName());
        holder.channelName.setText(videoWatch.getChannelName());
        holder.totalView.setText(videoWatch.getTotalView() + " lượt xem");
        Glide.with(context).load(videoWatch.getImageUrl()).into(holder.imageHistory);
        int m = Integer.parseInt(videoWatch.getTimeVideo()) / 60;
        int s = Integer.parseInt(videoWatch.getTimeVideo()) - m * 60;
        String time = "";
        if (s < 10) {
            time = "0" + s;
        } else {
            time += s;
        }
        holder.timeVideoHistory.setText(m + ":" + time);
        Intent i = new Intent(getActivity, DetailVideoActivity.class);
        i.putExtra("idVideo", videoWatch.getIdVideo());
        holder.itemHistory.setOnClickListener(v -> getActivity.startActivities(new Intent[]{i}));
    }

    @Override
    public int getItemCount() {
        return arrayListWatched.size();
    }

    public void removeItem(int position) {

        // delete video on realtime database
        DatabaseReference myRef = database.getReference(String.valueOf(arrayListWatched.get(position).getIdVideo()));
        myRef.removeValue();

        // delete video on recyclerview
        arrayListWatched.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView videoName;
        private final TextView channelName;
        private final TextView totalView;
        private final ImageView imageHistory;
        private final TextView timeVideoHistory;
        private final RelativeLayout itemHistory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoName = itemView.findViewById(R.id.tv_video_name_history);
            channelName = itemView.findViewById(R.id.tv_channel_name_history);
            totalView = itemView.findViewById(R.id.tv_total_video_history);
            imageHistory = itemView.findViewById(R.id.iv_img_video_history);
            timeVideoHistory = itemView.findViewById(R.id.tv_duration_video_history);
            itemHistory = itemView.findViewById(R.id.item_history);
        }
    }
}
