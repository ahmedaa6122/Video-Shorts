package com.example.videoshorts.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.videoshorts.R;
import com.example.videoshorts.adapter.ListVideoSearchAdapter;
import com.example.videoshorts.databinding.FragmentSearchBinding;
import com.example.videoshorts.model.Video;
import com.example.videoshorts.viewModel.ListVideoSearchViewModel;

import java.util.List;

public class SearchFragment extends Fragment {
    private ListVideoSearchAdapter listVideoSearchAdapter;
    private ListVideoSearchViewModel listVideoSearchViewModel;
    private FragmentSearchBinding binding;
    private int currentPage = 0;

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
        listVideoSearchViewModel = new ViewModelProvider(requireActivity()).get(ListVideoSearchViewModel.class);
        searchVideo();
        searchVideoWhenEnterEditText();
        return binding.getRoot();
    }

    private void searchVideoWhenEnterEditText() {
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentPage = 0;
                String content = binding.edtSearch.getText().toString().trim();
                if (content.isEmpty()) {
                    listVideoSearchViewModel.deleteArrayList();
                    binding.search2.setVisibility(View.VISIBLE);
                    binding.nsvSearch.setVisibility(View.GONE);
                } else {
                    binding.idPBLoadingSearch.setVisibility(View.VISIBLE);
                    listVideoSearchViewModel.deleteArrayList();
                    loadVideoSearch(content);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.ivSearchOrDelete.setImageResource(R.drawable.ic_delete_text);
                binding.ivSearchOrDelete.setClickable(true);
                binding.ivSearchOrDelete.setOnClickListener(view -> {
                    binding.edtSearch.setText("");
                    binding.ivSearchOrDelete.setClickable(false);
                    binding.ivSearchOrDelete.setImageResource(R.drawable.ic_search_video);
                    currentPage = 0;
                });
            }
        });
    }

    private void searchVideo() {
        binding.edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.idPBLoadingSearch.setVisibility(View.VISIBLE);
                String content = binding.edtSearch.getText().toString();
                if (content.isEmpty()) {
                    Toast.makeText(getActivity(), "Do not empty", Toast.LENGTH_SHORT).show();
                } else {
                    currentPage = 0;
                    listVideoSearchViewModel.deleteArrayList();
                    loadVideoSearch(content);
                }
            }
            return false;
        });
    }

    private void loadVideoSearch(String content) {
        // initiative value
        binding.search2.setVisibility(View.GONE);
        listVideoSearchViewModel.getApi(currentPage, content, requireActivity());
        listVideoSearchAdapter = new ListVideoSearchAdapter(listVideoSearchViewModel.getVideoArrayList(), requireActivity(), getActivity());
        binding.rvListSearch.setAdapter(listVideoSearchAdapter);
        binding.nsvSearch.setVisibility(View.VISIBLE);

        // observer change and handle
        final Observer<List<Video>> observer = list -> binding.rvListSearch.setAdapter(listVideoSearchAdapter);
        listVideoSearchViewModel.getListVideos().observe(requireActivity(), observer);

        // handle event when user scroll recyclerview
        binding.nsvSearch.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                currentPage++;
                binding.idPBLoadingSearch.setVisibility(View.VISIBLE);
                listVideoSearchViewModel.getApi(currentPage, content, requireActivity());
            }
        });
    }
}