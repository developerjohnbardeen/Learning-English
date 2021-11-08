package com.example.a4000essentialwordsbook1.SelectedUnitTab;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.QuizList.QuizListFragment;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.StoryList.StoryListFragment;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.WordListFragment;


public class UnitSectionPagerAdapter extends FragmentStateAdapter {
    private final int unitNum;
    private final int dbNum;


    public UnitSectionPagerAdapter(int unitNum, int dbNum,
                                   FragmentManager fm, Lifecycle lifecycle){
        super(fm, lifecycle);
        this.unitNum = unitNum;
        this.dbNum = dbNum;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new WordListFragment(unitNum, dbNum);
        else if (position == 1) {
            //return new StoryListFragment(dbNum, unitNum);
            return StoryListFragment.newInstance(dbNum, unitNum);
        }
        else
            return new QuizListFragment(unitNum, dbNum);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
