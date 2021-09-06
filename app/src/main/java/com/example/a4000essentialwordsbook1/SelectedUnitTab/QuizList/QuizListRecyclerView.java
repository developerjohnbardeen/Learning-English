package com.example.a4000essentialwordsbook1.SelectedUnitTab.QuizList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;

import java.util.ArrayList;

public class QuizListRecyclerView extends RecyclerView.Adapter<QuizListRecyclerView.QuizViewHolder> {
    private final Context quizContext;
    private final LayoutInflater inflater;
    private ArrayList<WordModel> quizList;

    public QuizListRecyclerView(Context context, ArrayList<WordModel> quizList){
        this.quizContext = context;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public QuizListRecyclerView.QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.quiz_list_recyclerview, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizListRecyclerView.QuizViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public static class QuizViewHolder extends RecyclerView.ViewHolder {
        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
