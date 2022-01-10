package com.example.a4000essentialwordsbook1.DownloadClasses.Downloads;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.a4000essentialwordsbook1.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DownloadActivity extends AppCompatActivity {


    private FragmentManager fragmentManager;
    private ViewPager2 downViewPager;
    private TabLayout downTabLayout;
    private RelativeLayout backPressedLayout;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        allFunctions();
    }


    private void allFunctions() {
        findViewById();

        pagerAdapterSetter();
        tabLayoutFunction();
    }


    private void pagerAdapterSetter() {
        fragmentManager = getSupportFragmentManager();
        DownloadPagerAdapter downPagerAdapter = new DownloadPagerAdapter(fragmentManager, getLifecycle());
        downViewPager.requestDisallowInterceptTouchEvent(true);
        downViewPager.setAdapter(downPagerAdapter);
    }


    private void tabLayoutFunction() {

        downTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        downTabLayout.setTabMode(TabLayout.MODE_FIXED);
        final String[] pagerTitle = {"Book 1", "Book 2", "Book 3", "Book 4", "Book 5", "Book 6"};

        new TabLayoutMediator(downTabLayout, downViewPager,
                (((tab, position) -> tab.setText(pagerTitle[position])))
        ).attach();


        downTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                downViewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        backPressedLayout.setOnClickListener(view -> onBackPressed());
    }

    private void findViewById() {
        downViewPager = findViewById(R.id.activity_download_viewPager);
        downTabLayout = findViewById(R.id.activity_download_tabs);

        backPressedLayout = findViewById(R.id.activity_download_tab_layout_bck_bttn_layout);
    }

}
