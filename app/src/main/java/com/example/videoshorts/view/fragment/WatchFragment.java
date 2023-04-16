package com.example.videoshorts.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videoshorts.adapter.ListVideoAdapter;
import com.example.videoshorts.databinding.FragmentWatchBinding;
import com.example.videoshorts.model.Video;
import com.example.videoshorts.viewModel.ListVideoViewModel;

import java.util.List;

public class WatchFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ListVideoAdapter listVideoAdapter;
    private ListVideoViewModel listVideoViewModel;
    private FragmentWatchBinding binding;
    private int currentPage = 0;
    private int refreshPageNumber = 1;

    public WatchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentWatchBinding.inflate(inflater, container, false);
        listVideoViewModel = new ViewModelProvider(requireActivity()).get(ListVideoViewModel.class);

        // initiative value
        listVideoViewModel.getApi(currentPage, requireActivity());
        listVideoAdapter = new ListVideoAdapter(requireActivity(), getActivity());
        binding.rvListVideos.setAdapter(listVideoAdapter);

        // observer change and handle
        final Observer<List<Video>> observer = videos -> listVideoAdapter.setListVideo(videos);
        listVideoViewModel.getListVideos().observe(requireActivity(), observer);

        // handle event when user scroll recyclerview
        binding.idNestedSV.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                currentPage++;
                binding.idPBLoading.setVisibility(View.VISIBLE);
                listVideoViewModel.getApi(currentPage, requireActivity());
            }
        });

        binding.srlReload.setOnRefreshListener(this);

        return binding.getRoot();
    }

    @Override
    public void onRefresh() {
        // reload list video
        listVideoAdapter.reLoadListVideo();
        listVideoViewModel.getApi(refreshPageNumber, requireActivity());

        // set time delay
        Handler handler = new Handler();
        handler.postDelayed(() -> binding.srlReload.setRefreshing(false), 2000);

        // increase value of refreshPageNumber
        refreshPageNumber++;
    }
}