package com.example.a4000essentialwordsbook1.QuizFile.QuizRezult;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.CorrectModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.SkippedModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.WrongModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.CorrectAnswer.CorrectAnswerFragment;
import com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.QuizPieChartResult.QuizPieChartResultFragment;
import com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.SkippedAnswer.SkippedAnswerFragment;
import com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.WrongAnswer.WrongAnswerFragment;

import java.util.ArrayList;

public class QuizResultPagerAdapter extends FragmentStateAdapter {
    private final String quizType;
    private final int[] resultCounts = new int[3];
    private final ArrayList<WordModel> correctList;
    private final ArrayList<WordModel> wrongList;
    private final ArrayList<WordModel> skippedList;
    private final int[] dbInfoList;

    public QuizResultPagerAdapter(String quizType, ArrayList<WordModel> correctList,
                                  ArrayList<WordModel> wrongList, ArrayList<WordModel> skippedList, int[] dbInfoList,
                                  FragmentManager fm, Lifecycle lifecycle){
        super(fm, lifecycle);
        this.correctList = correctList;
        this.wrongList = wrongList;
        this.skippedList = skippedList;
        this.dbInfoList = dbInfoList;
        this.quizType = quizType;
        resultCounts[0] = correctList.size();
        resultCounts[1] = wrongList.size();
        resultCounts[2] = skippedList.size();
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new QuizPieChartResultFragment(resultCounts, dbInfoList);
        }else if (position == 1){
            return new CorrectAnswerFragment(quizType, correctList, dbInfoList);
        }else if (position == 2){
            return new WrongAnswerFragment(quizType, wrongList, dbInfoList);
        }else {
            return new SkippedAnswerFragment(quizType, skippedList, dbInfoList);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
