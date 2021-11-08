package com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.CorrectAnswer;

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

import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.CorrectModel;
import com.example.a4000essentialwordsbook1.R;

import java.util.ArrayList;

public class CorrectAnswerFragment extends Fragment {
    private final Context caContext;
    private final ArrayList<CorrectModel> correctList;
    private RecyclerView correctRecyclerView;
    private final int[] dbInfoList;

    public CorrectAnswerFragment(Context context, ArrayList<CorrectModel> list, int[] dbInfoList){
        this.caContext = context;
        this.correctList = list;
        this.dbInfoList = dbInfoList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_correct_answer, container, false);
        correctRecyclerView = view.findViewById(R.id.correct_answer_recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewValueSetter(view);
    }

    public void recyclerViewValueSetter(View view){
        RecyclerViewCorrectAnswer correctAnswerAdapter = new RecyclerViewCorrectAnswer(caContext, correctList, dbInfoList);
        correctRecyclerView.setLayoutManager(new GridLayoutManager(caContext, 1));
        //correctAnswerAdapter.notifyDataSetChanged();
        correctRecyclerView.setAdapter(correctAnswerAdapter);
    }


}
