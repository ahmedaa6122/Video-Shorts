package com.example.videoshorts.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.videoshorts.R;
import com.example.videoshorts.adapter.ListVideoRelativeAdapter;
import com.example.videoshorts.databinding.ActivityDetailVideoBinding;
import com.example.videoshorts.model.Video;
import com.example.videoshorts.model.VideoWatch;
import com.example.videoshorts.viewModel.DetailVideoViewModel;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DetailVideoActivity extends AppCompatActivity {
    private ExoPlayer player;
    private ListVideoRelativeAdapter listVideoRelativeAdapter;
    private int currentPage = 0;
    private DetailVideoViewModel detailVideoViewModel;
    private ActivityDetailVideoBinding binding;
    private boolean isFullScreen = false;
    private ImageView fullScreen, rewind, forward;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set color for status bar
        getWindow().setStatusBarColor(ContextCompat.getColor(DetailVideoActivity.this, R.color.black));

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_video);

        // mapping from custom_controller.xml
        fullScreen = findViewById(R.id.iv_fullscreen);
        rewind = findViewById(R.id.exo_rewind);
        forward = findViewById(R.id.exo_forward);

        detailVideoViewModel = new ViewModelProvider(this).get(DetailVideoViewModel.class);
        binding.rvListVideoRelative.setLayoutManager(new LinearLayoutManager(this));
        player = new ExoPlayer.Builder(this).build();
        binding.player.setPlayer(player);

        //get value from WatchFragment
        Intent intent = getIntent();
        int id = intent.getIntExtra("idVideo", 165457);

        // play video when get data from WatchFragment
        playVideo(id);

        // initiative list video relative
        initialVideoRelative();

        // handle event when user scroll recyclerview
        binding.NSVListVideoRelative.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                currentPage++;
                binding.idPBLoadingRelative.setVisibility(View.VISIBLE);
                detailVideoViewModel.getListVideoRelative(id, currentPage, this);
            }
        });

        // handle when user click a item on recyclerView
        final Observer<Long> idObserver = aLong -> {
            playVideo(Math.toIntExact(aLong));
            initialVideoRelative();
        };
        detailVideoViewModel.getIdVideoClick().observe(this, idObserver);

        //handle event when press button back on screen
        backHome();

        // handle event when user want controller time video
        controllerTimeVideo();

        // handle when user want watch full screen
        setFullScreen();
    }

    public void controllerTimeVideo() {
        // rewind video 5s
        rewind.setOnClickListener(v -> player.seekTo(player.getCurrentPosition() - 5000));

        // forward video 5s
        forward.setOnClickListener(v -> player.seekTo(player.getCurrentPosition() + 5000));
    }

    public void setFullScreen() {
        fullScreen.setOnClickListener(v -> {
            ViewGroup.LayoutParams params = binding.player.getLayoutParams();
            if (!isFullScreen) {

                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                binding.player.setLayoutParams(params);

                binding.ivBackToListVideo.setVisibility(View.GONE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                fullScreen.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baselime_fullscreen_exit));
                isFullScreen = true;
            } else {

                params.height = 404;
                binding.player.setLayoutParams(params);

                binding.ivBackToListVideo.setVisibility(View.VISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                fullScreen.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_fullscreen));
                isFullScreen = false;
            }
        });
    }

    public void initializePlayer(String url) {
        if (player.isPlaying()) {
            player.stop();
        }
        MediaItem mediaItem = MediaItem.fromUri(url);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
    }

    //playVideo
    public void playVideo(int id) {

        // save id video watched
        myRef = database.getReference(String.valueOf(id));

        detailVideoViewModel.getListVideoRelative(id, currentPage, this);
        detailVideoViewModel.setDetailVideo(id, this);
        detailVideoViewModel.getVideo().observe(this, observer);
    }

    final Observer<Video> observer = video -> {
        String url = video.getVideoMedia();
        Video contentVideo = new Video(video.getVideoTitle(), video.getChannel(), video.getTotalViews());

        VideoWatch videoWatch = new VideoWatch(video.getId(), video.getVideoTitle(), video.getChannel().getChannelName(), video.getTotalViews(), video.getVideoImage(), video.getVideoTime());
        myRef.setValue(videoWatch);

        initializePlayer(url);
        binding.setContentVideo(contentVideo);
        Glide.with(this).load(video.getChannel().getChannelAvatar()).into(binding.civImgThumbInfo);
    };

    public void initialVideoRelative() {
        currentPage = 0;
        listVideoRelativeAdapter = new ListVideoRelativeAdapter(detailVideoViewModel.getVideoArrayList(), this);
        binding.rvListVideoRelative.setAdapter(listVideoRelativeAdapter);
        final Observer<List<Video>> listObserver = list -> binding.rvListVideoRelative.setAdapter(listVideoRelativeAdapter);
        detailVideoViewModel.getListVideos().observe(this, listObserver);
    }

    public void backHome() {
        binding.ivBackToListVideo.setOnClickListener(v -> {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
            finish();
        });
    }
}