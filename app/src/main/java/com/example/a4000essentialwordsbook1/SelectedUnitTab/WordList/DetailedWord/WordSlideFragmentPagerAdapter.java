package com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.a4000essentialwordsbook1.Linsteners.DefinitionPlayListener;
import com.example.a4000essentialwordsbook1.Linsteners.ExamplePlayListener;
import com.example.a4000essentialwordsbook1.Linsteners.PlaySingleTrackInterface;
import com.example.a4000essentialwordsbook1.Linsteners.TextProvider;
import com.example.a4000essentialwordsbook1.Linsteners.WordPlayListener;
import com.example.a4000essentialwordsbook1.Models.WordModel;

import java.util.ArrayList;

public class WordSlideFragmentPagerAdapter extends FragmentPagerAdapter {

    private final Context context;
    private final ArrayList<WordModel> models;
    private final ArrayList<Integer> flagList;
    private final TextProvider mProvider;
    private int newPosition = 0;

    private final WordPlayListener wordListener;
    private final DefinitionPlayListener definitionListener;
    private final ExamplePlayListener exampleListener;


    public WordSlideFragmentPagerAdapter(Context context, ArrayList<WordModel> models,
                                         ArrayList<Integer> flagList,
                                         @NonNull FragmentManager fm, int behavior,
                                         TextProvider tProvider,
                                         WordPlayListener wordListener, DefinitionPlayListener definitionListener, ExamplePlayListener exampleListener) {
        super(fm, behavior);
        this.context = context;
        this.models = models;
        this.flagList = flagList;
        this.mProvider = tProvider;
        this.wordListener = wordListener ;
        this.definitionListener = definitionListener ;
        this.exampleListener = exampleListener ;

    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        /*while (position < models.size()){
            return new SlideWordFragment(context, models.get(position));
        }*/
        return SlideWordFragment.newInstance(context, models.get(position),
                wordListener,
                definitionListener,
                exampleListener);
    }

    @Override
    public long getItemId(int position) {
        return position + newPosition;
    }

    public void notifyChangeInPosition(int n) {
        // shift the ID returned by getItemId outside the range of all previous fragments
        newPosition += getCount() + n;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        // refresh all fragments when data set changed
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mProvider.getCount();
    }
}
