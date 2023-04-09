package com.example.videoshorts.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.videoshorts.R;
import com.example.videoshorts.adapter.ViewPagerAdapter;
import com.example.videoshorts.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.videoshorts.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // set color for status bar
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.black));

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(this);
        binding.viewPager2.setAdapter(pagerAdapter);
        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        binding.bottomNav.getMenu().findItem(R.id.action_youtube).setChecked(true);
                        break;
                    case 1:
                        binding.bottomNav.getMenu().findItem(R.id.action_search).setChecked(true);
                        break;
                    case 2:
                        binding.bottomNav.getMenu().findItem(R.id.action_library).setChecked(true);
                        break;
                }
            }
        });
        binding.bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_youtube) {
                binding.viewPager2.setCurrentItem(0);
            } else if (item.getItemId() == R.id.action_search) {
                binding.viewPager2.setCurrentItem(1);
            } else {
                binding.viewPager2.setCurrentItem(2);
            }
            return true;
        });
    }
}