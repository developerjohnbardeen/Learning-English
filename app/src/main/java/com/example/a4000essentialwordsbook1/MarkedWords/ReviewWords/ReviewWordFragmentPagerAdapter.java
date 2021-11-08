package com.example.a4000essentialwordsbook1.MarkedWords.ReviewWords;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.a4000essentialwordsbook1.Linsteners.TextProvider;
import com.example.a4000essentialwordsbook1.Models.WordModel;



public class ReviewWordFragmentPagerAdapter extends FragmentStateAdapter {
    private final TextProvider mProvider;
    private long baseId = 0;
    private static boolean[] shwFarsiFlags;
    private static WordModel models;

    public ReviewWordFragmentPagerAdapter(Lifecycle lifecycle,
                                          boolean[] shwFarsiFlags,
                                          @NonNull FragmentManager fm,
                                          TextProvider mProvider) {
        super(fm, lifecycle);
        this.mProvider = mProvider;
        ReviewWordFragmentPagerAdapter.shwFarsiFlags = shwFarsiFlags;
    }


    public static void listChanger(WordModel list){models = list;}
    public static void shwFarsiFlagsList(boolean[] shwFlagsLists){
        shwFarsiFlags = shwFlagsLists;
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return FragmentReviewWord.newInstance(mProvider.getWordModel(models, position), shwFarsiFlags);
    }

    @Override
    public long getItemId(int position) {
        // give an ID different from position when position has been changed
        return baseId + position;
    }

    @Override
    public int getItemCount() {
        return mProvider.getCount();
    }

    public void notifyChangeInPosition(int n) {
        // shift the ID returned by getItemId outside the range of all previous fragments
        baseId += getItemCount() + n;
    }


}
/*    @NonNull
    @Override
    public Fragment getItem(int position) {

        return FragmentReviewWord.newInstance(context, mProvider.getWordModel(position));
    }*/
/*    //this is called when notifyDataSetChanged() is called
    @Override
    public int getItemPosition(@NonNull Object object) {
        // refresh all fragments when data set changed
        return POSITION_NONE;
    }*/
/*    @Override
    public int getCount() {
        return mProvider.getCount();
    }*/