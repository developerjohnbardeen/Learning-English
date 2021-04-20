package com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.WrongAnswer;

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

import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.WrongModel;
import com.example.a4000essentialwordsbook1.R;

import java.util.ArrayList;

public class WrongAnswerFragment extends Fragment {
    private final Context wngContext;
    private final ArrayList<WrongModel> wrongList;
    private RecyclerView wrongRecyclerView;


    public WrongAnswerFragment(Context context, ArrayList<WrongModel> list){
        this.wngContext = context;
        this.wrongList = list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wrong_answer, container, false);
        wrongRecyclerView = view.findViewById(R.id.wrong_answer_recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewFunctions(view);
    }

    private void recyclerViewFunctions(View view){
        RecyclerViewWrongAnswer wrongAnswerAdapter = new RecyclerViewWrongAnswer(wngContext, wrongList);
        wrongRecyclerView.setLayoutManager(new GridLayoutManager(wngContext, 1));
        wrongRecyclerView.setAdapter(wrongAnswerAdapter);
    }
}
