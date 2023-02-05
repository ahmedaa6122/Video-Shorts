package com.example.videoshorts.viewModel;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.videoshorts.model.VideoWatch;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListVideoWatchedViewModel extends ViewModel {

    private final MutableLiveData<VideoWatch> video = new MutableLiveData<>();
    private final List<VideoWatch> arrayList = new ArrayList<>();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference();

    public MutableLiveData<VideoWatch> getVideo() {
        return video;
    }

    public List<VideoWatch> getArrayList() {
        return arrayList;
    }

    public void getListVideWatched(Context context) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    video.setValue(dataSnapshot.getValue(VideoWatch.class));
                    arrayList.add(video.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Load history watch error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
