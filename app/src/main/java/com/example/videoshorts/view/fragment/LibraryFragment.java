package com.example.videoshorts.view.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.videoshorts.dialog.DialogLogOutFragment;
import com.example.videoshorts.model.VideoWatch;
import com.example.videoshorts.view.activity.MainActivity;
import com.example.videoshorts.viewModel.ListVideoWatchedViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

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

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    Paint p = new Paint();
                    View itemView = viewHolder.itemView;
                    float height = itemView.getBottom() - itemView.getTop();
                    float width = height / 3;
                    p.setColor(Color.RED);
                    RectF background = new RectF(itemView.getRight() + dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    c.drawRect(background, p);
                    Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_delete);
                    float margin = (dX / 4 - width) / 2;
                    RectF iconDest = new RectF(itemView.getRight() + margin, itemView.getTop() + width, itemView.getRight() + (margin + width), itemView.getBottom() - width);
                    c.drawBitmap(icon, null, iconDest, p);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX / 4, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rvListHistory);
    }

    //handle event when logout
    private void Logout() {
        binding.imgLogOut.setOnClickListener(v -> new DialogLogOutFragment((MainActivity) requireActivity()).show(getChildFragmentManager(), null));
    }
}