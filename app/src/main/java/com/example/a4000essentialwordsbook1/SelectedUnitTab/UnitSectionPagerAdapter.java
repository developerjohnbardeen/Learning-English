package com.example.a4000essentialwordsbook1.SelectedUnitTab;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.a4000essentialwordsbook1.SelectedUnitTab.QuizList.QuizListFragment;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.StoryList.StoryListFragment;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.WordListFragment;

public class UnitSectionPagerAdapter extends FragmentPagerAdapter {
    private final Context fContext;
    private final int unitNum;
    private final int dbNum;


    public UnitSectionPagerAdapter(int unitNum, int dbNum, Context context, FragmentManager fm){
        super(fm);
        this.fContext = context;
        this.unitNum = unitNum;
        this.dbNum = dbNum;
    }

    @Override
    public CharSequence getPageTitle(int position){
        if (position == 0)
            return "";
        else if (position == 1)
            return "";
        else
            return "";
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 0)
            return new WordListFragment(fContext, unitNum, dbNum);
        else if (position == 1) {
            return new StoryListFragment(fContext, unitNum, dbNum);
        }
        else
            return new QuizListFragment(fContext, unitNum, dbNum);
    }

    @Override
    public int getCount() {
        return 3;
    }


}
