package com.example.videoshorts.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.videoshorts.R;
import com.example.videoshorts.adapter.ListVideoHistoryAdapter;
import com.example.videoshorts.databinding.FragmentLibraryBinding;
import com.example.videoshorts.model.VideoWatch;
import com.example.videoshorts.view.activity.LogInActivity;
import com.example.videoshorts.viewModel.ListVideoWatchedViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public class LibraryFragment extends Fragment {

    private FragmentLibraryBinding binding;
    private ListVideoHistoryAdapter listVideoHistoryAdapter;

    public LibraryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        ListVideoWatchedViewModel listVideoWatchedViewModel = new ViewModelProvider(requireActivity()).get(ListVideoWatchedViewModel.class);

        // load icon account google
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(requireActivity());
        if (signInAccount != null) {
            Glide.with(requireActivity()).load(signInAccount.getPhotoUrl()).into(binding.imgAvatar);
        }

        // method handle when user click exit
        Logout();

        binding.rvListHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        // get list video watched from database
        listVideoWatchedViewModel.getListVideWatched(requireActivity());

        listVideoHistoryAdapter = new ListVideoHistoryAdapter(listVideoWatchedViewModel.getArrayList(), requireActivity(), getActivity());
        binding.rvListHistory.setAdapter(listVideoHistoryAdapter);
        binding.pbLoadHistoryVideo.setVisibility(View.GONE);
        binding.rvListHistory.setVisibility(View.VISIBLE);

        final Observer<VideoWatch> observer = videoWatch -> binding.rvListHistory.setAdapter(listVideoHistoryAdapter);
        listVideoWatchedViewModel.getVideo().observe(requireActivity(), observer);

        //delete a video in history watched
        deleteAVideo();

        return binding.getRoot();
    }

    private void deleteAVideo() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    int position = viewHolder.getAdapterPosition();
                    listVideoHistoryAdapter.removeItem(position);
                    ToastUtils.showShort(getString(R.string.notify_remove_success));
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rvListHistory);
    }

    //handle event when logout
    private void Logout() {
        binding.imgLogOut.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setTitle("Confirm logout")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(requireActivity(), LogInActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    }).setNegativeButton("No", (dialog, which) -> {
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }
}