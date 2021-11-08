package com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.a4000essentialwordsbook1.Linsteners.TextProvider;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordDetailedInterfaces.DisplayTranslationInterface;

import java.util.ArrayList;




public class WordSlideFragmentPagerAdapter extends FragmentStateAdapter {

    private final Context context;
    private static ArrayList<WordModel> models;
    private final ArrayList<Integer> markedFlagList;
    private final TextProvider mProvider;
    private static boolean[] shwFarsiFlags;
    private int newPosition = 0;




    public WordSlideFragmentPagerAdapter(Context context, ArrayList<WordModel> models,
                                         ArrayList<Integer> markedFlagList, boolean[] shwFarsiFlags,
                                         @NonNull FragmentManager fm, Lifecycle lifecycle,
                                         TextProvider tProvider) {
        super(fm, lifecycle);
        this.context = context;
        WordSlideFragmentPagerAdapter.models = models;
        WordSlideFragmentPagerAdapter.shwFarsiFlags = shwFarsiFlags;
        this.markedFlagList = markedFlagList;
        this.mProvider = tProvider;
    }



    public static void listChanger(ArrayList<WordModel> list){models = list;}
    public static void shwFarsiFlagsList(boolean[] shwFlagsLists){shwFarsiFlags = shwFlagsLists;}


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        /*while (position < models.size()){
            return new SlideWordFragment(context, models.get(position));
        }*/
        return SlideWordFragment.newInstance(models.get(position), markedFlagList,  shwFarsiFlags, position);
    }

    @Override
    public long getItemId(int position) {
        return position + newPosition;
    }

    @Override
    public int getItemCount() {
        return mProvider.getCount();
    }
}
