package com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.SkippedAnswer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.SkippedModel;
import com.example.a4000essentialwordsbook1.R;

import java.util.ArrayList;

public class SkippedAnswerFragment extends Fragment {
    private final ArrayList<WordModel> skippedList;
    private final int[] dbInfoList;
    private RecyclerView skippedRecyclerView;
    private final String quizType;


    public SkippedAnswerFragment(String quizType, ArrayList<WordModel> list, int[] dbInfoList){
        this.skippedList = list;
        this.dbInfoList = dbInfoList;
        this.quizType = quizType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skipped_answer, container, false);
        skippedRecyclerView = view.findViewById(R.id.skipped_answer_recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewValuesFunction(view);
    }

    private void recyclerViewValuesFunction(View view){
        RecyclerViewSkippedAnswer skippedAdapter = new RecyclerViewSkippedAnswer(requireActivity(), quizType, skippedList, dbInfoList);
        skippedRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 1));
        skippedRecyclerView.setAdapter(skippedAdapter);
    }

}
