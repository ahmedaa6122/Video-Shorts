package com.example.videoshorts.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoshorts.R;
import com.example.videoshorts.listenner.EventClick;
import com.example.videoshorts.model.SaveSearch;

import java.util.ArrayList;
import java.util.List;

public class HistorySearchAdapter extends RecyclerView.Adapter<HistorySearchAdapter.ViewHolder> {
    private final List<SaveSearch> list = new ArrayList<>();
    private final EventClick callback;

    public HistorySearchAdapter(EventClick callback) {
        this.callback = callback;
    }

    public void setList(List<SaveSearch> list) {
        if (this.list.size() != 0) {
            this.list.clear();
        }
        this.list.addAll(list);
    }

    public void deleteHistory(int position) {
        this.list.remove(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String key = list.get(position).getContent();
        holder.content.setText(key);
        holder.root.setOnClickListener(view -> callback.onClickSearch(key));
        holder.delete.setOnClickListener(view -> callback.onClickDelete(list.get(position), position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView content;
        private final TextView delete;
        private final RelativeLayout root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.tvContent);
            delete = itemView.findViewById(R.id.tvDelete);
            root = itemView.findViewById(R.id.root);
        }
    }
}
