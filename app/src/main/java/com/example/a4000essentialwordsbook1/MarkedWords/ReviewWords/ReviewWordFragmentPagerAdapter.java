package com.example.a4000essentialwordsbook1.MarkedWords.ReviewWords;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.a4000essentialwordsbook1.Linsteners.TextProvider;
import com.example.a4000essentialwordsbook1.Models.WordModel;

import java.util.ArrayList;

public class ReviewWordFragmentPagerAdapter extends FragmentPagerAdapter {
    private final Context context;
    private final ArrayList<WordModel> wordList;
    private final TextProvider mProvider;
    private long baseId = 0;

    public ReviewWordFragmentPagerAdapter(Context context, ArrayList<WordModel> models,
                                          @NonNull FragmentManager fm, int behavior,
                                          TextProvider mProvider) {
        super(fm, behavior);
        this.context = context;
        this.wordList = models;
        this.mProvider = mProvider;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        /*while (position < wordList.size()){
            return new FragmentReviewWord(context, wordList.get(position));
        }
        return null;*/
        return FragmentReviewWord.newInstance(context, mProvider.getWordModel(position));
    }

    //this is called when notifyDataSetChanged() is called
    @Override
    public int getItemPosition(@NonNull Object object) {
        // refresh all fragments when data set changed
        return POSITION_NONE;
    }

    @Override
    public long getItemId(int position) {
        // give an ID different from position when position has been changed
        return baseId + position;
    }

    public void notifyChangeInPosition(int n) {
        // shift the ID returned by getItemId outside the range of all previous fragments
        baseId += getCount() + n;
    }

    @Override
    public int getCount() {
        return mProvider.getCount();
    }
}
