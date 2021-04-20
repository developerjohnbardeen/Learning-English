package com.example.a4000essentialwordsbook1.SampleClasses;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordModel;

import java.util.ArrayList;

public class SampleSectionPagerAdapter extends FragmentPagerAdapter {
    private final Context context;
    private final ArrayList<WordModel> models;

    public SampleSectionPagerAdapter(Context context, ArrayList<WordModel> models, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        while (position < models.size()) {
            return new SampleFragment(context, models.get(position));
        }

        return null;
    }

    @Override
    public int getCount() {
        return models.size();
    }
}
