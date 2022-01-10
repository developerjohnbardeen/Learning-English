package com.example.a4000essentialwordsbook1.DownloadClasses.Downloads;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DownloadPagerAdapter extends FragmentStateAdapter {

    public DownloadPagerAdapter(FragmentManager fm, Lifecycle lifecycle) {
        super(fm, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new DownLoadFragment(position);
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
