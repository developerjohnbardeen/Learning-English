package com.example.a4000essentialwordsbook1.SampleClasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SampleClass extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<WordModel> models;
    private int position;
    public SendFlagInterface sendFlagInterface;
    private boolean trnsltFlag = false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);


        models = new ArrayList<>();
        ImageButton btn = findViewById(R.id.sample_translate_btn);
        btn.setOnClickListener(this);

        Intent intent = getIntent();
        models = intent.getParcelableArrayListExtra("sampleList");
        position = intent.getIntExtra("int", 0);
        fragmentViewPagerAdapter();
    }

    private void fragmentViewPagerAdapter(){
        ViewPager viewPager = findViewById(R.id.sample_view_pager);
        TabLayout tab = findViewById(R.id.tab_sample);
        SampleSectionPagerAdapter pagerAdapter = new SampleSectionPagerAdapter(this, models, getSupportFragmentManager());
        tab.setupWithViewPager(viewPager, true);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(position);

    }


    @Override
    public void onClick(View v) {
        if (!trnsltFlag){
            sendFlagInterface.sendFlag(false);
            trnsltFlag = true;
        }else {
            sendFlagInterface.sendFlag(true);
            trnsltFlag = false;
        }
    }



    public interface SendFlagInterface{
        void sendFlag(boolean flag);
    }
    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);

        try {
            sendFlagInterface = (SendFlagInterface) fragment;
        }catch (RuntimeException e){
            throw new RuntimeException(fragment.toString() + "some thing went wrong");
        }
    }


}










