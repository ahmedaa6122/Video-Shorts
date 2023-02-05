package com.example.videoshorts.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.videoshorts.view.fragment.LibraryFragment;
import com.example.videoshorts.view.fragment.SearchFragment;
import com.example.videoshorts.view.fragment.WatchFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new SearchFragment();
            case 2:
                return new LibraryFragment();
            default:
                return new WatchFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
