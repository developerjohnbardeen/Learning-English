package com.example.a4000essentialwordsbook1.QuizFile.QuizRezult;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.CorrectModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.SkippedModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.WrongModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.CorrectAnswer.CorrectAnswerFragment;
import com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.SkippedAnswer.SkippedAnswerFragment;
import com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.WrongAnswer.WrongAnswerFragment;

import java.util.ArrayList;

public class QuizResultPagerAdapter extends FragmentPagerAdapter {
    private final Context qrContext;
    private final ArrayList<CorrectModel> correctList;
    private final ArrayList<WrongModel> wrongList;
    private final ArrayList<SkippedModel> skippedList;

    public QuizResultPagerAdapter(Context context,
                                  ArrayList<CorrectModel> correctList, ArrayList<WrongModel> wrongList, ArrayList<SkippedModel> skippedList,
                                  FragmentManager fm){
        super(fm);
        this.qrContext = context;
        this.correctList = correctList;
        this.wrongList = wrongList;
        this.skippedList = skippedList;

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
        if (position == 0){
            return new CorrectAnswerFragment(qrContext, correctList);
        }else if (position == 1){
            return new WrongAnswerFragment(qrContext, wrongList);
        }else {
            return new SkippedAnswerFragment(qrContext, skippedList);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
