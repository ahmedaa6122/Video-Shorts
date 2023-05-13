package com.example.videoshorts.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videoshorts.adapter.HistorySearchAdapter;
import com.example.videoshorts.adapter.ListVideoSearchAdapter;
import com.example.videoshorts.database.SaveSearchDatabase;
import com.example.videoshorts.databinding.FragmentSearchBinding;
import com.example.videoshorts.listenner.EventClick;
import com.example.videoshorts.model.SaveSearch;
import com.example.videoshorts.model.Video;
import com.example.videoshorts.viewModel.ListVideoSearchViewModel;

import java.util.List;

public class SearchFragment extends Fragment implements EventClick {
    private ListVideoSearchAdapter listVideoSearchAdapter;
    private ListVideoSearchViewModel listVideoSearchViewModel;
    private FragmentSearchBinding binding;
    private int currentPage = 0;
    private HistorySearchAdapter adapter;
    private Boolean checkWhere = true;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        // initiative value
        listVideoSearchViewModel = new ViewModelProvider(requireActivity()).get(ListVideoSearchViewModel.class);
        listVideoSearchAdapter = new ListVideoSearchAdapter(requireActivity(), getActivity());
        binding.rvListSearch.setAdapter(listVideoSearchAdapter);
        adapter = new HistorySearchAdapter(this);
        binding.historySearch.setAdapter(adapter);

        // observer change and handle
        final Observer<List<Video>> observer = list -> listVideoSearchAdapter.addListVideo(list);
        listVideoSearchViewModel.getListVideos().observe(requireActivity(), observer);

        // Handle search videos by name
        searchVideo();

        // Handle when click into searchView
        clickSearchView();
        return binding.getRoot();
    }

    private void clickSearchView() {
        // Handle when focus into searchView
        binding.svSearch.setOnQueryTextFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                List<SaveSearch> list = SaveSearchDatabase.getInstance(requireActivity()).saveSearchDao().getAll();
                adapter.setList(list);
                binding.historySearch.setVisibility(View.VISIBLE);
            } else {
                binding.historySearch.setVisibility(View.GONE);
            }
        });
    }

    private void searchVideo() {
        // Handle when click button search on keyboard
        binding.svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                // Reset some feature
                listVideoSearchAdapter.clearListVideo();
                binding.historySearch.setVisibility(View.GONE);

                if (checkWhere) {
                    // Save key search into database
                    SaveSearchDatabase.getInstance(requireActivity()).saveSearchDao().insertData(new SaveSearch(query));
                }

                // Search videos by name
                currentPage = 0;
                loadVideoSearch(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void loadVideoSearch(String content) {
        // initiative value
        checkWhere = true;
        listVideoSearchViewModel.getApi(currentPage, content, requireActivity());
        binding.nsvSearch.setVisibility(View.VISIBLE);

        // handle event when user scroll recyclerview
        binding.nsvSearch.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                currentPage++;
                binding.idPBLoadingSearch.setVisibility(View.VISIBLE);
                listVideoSearchViewModel.getApi(currentPage, content, requireActivity());
            }
        });
    }

    @Override
    public void onClickSearch(String content) {
        checkWhere = false;
        binding.svSearch.setQuery(content, true);
        binding.historySearch.setVisibility(View.GONE);
    }

    @Override
    public void onClickDelete(SaveSearch object, int position) {
        SaveSearchDatabase.getInstance(requireActivity()).saveSearchDao().deleteHistorySearch(object);
        adapter.notifyItemRemoved(position);
        adapter.deleteHistory(position);
    }
}