package com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.CorrectAnswer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.CorrectModel;
import com.example.a4000essentialwordsbook1.R;

import java.util.ArrayList;


public class RecyclerViewCorrectAnswer extends RecyclerView.Adapter<RecyclerViewCorrectAnswer.ViewHolder> {
    private final Context caContext;
    private final LayoutInflater inflater;
    private final ArrayList<CorrectModel> correctList;
    private final int dbNum, unitNum;


    public RecyclerViewCorrectAnswer(Context context, ArrayList<CorrectModel> list, int[] dbInfoList){
        this.caContext = context;
        this.inflater = LayoutInflater.from(context);
        this.correctList = list;
        this.dbNum = dbInfoList[0];
        this.unitNum = dbInfoList[1];
    }


    @NonNull
    @Override
    public RecyclerViewCorrectAnswer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.correct_answer_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCorrectAnswer.ViewHolder holder, int position) {
        viewValuesGetterAndSetter(position, holder);
    }

    @Override
    public int getItemCount() {
        return correctList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView tvImage;
        TextView tvWord;
        TextView answerWord;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvImage = itemView.findViewById(R.id.correct_tv_image_view);
            tvWord = itemView.findViewById(R.id.correct_txt_tv_word);
            answerWord = itemView.findViewById(R.id.correct_txt_answer_word);

        }
    }

    private void viewValuesGetterAndSetter(int position, ViewHolder holder){
        CorrectModel correctModel = correctList.get(position);
        int image = correctModel.getTvImage();
        String word = correctModel.getTvWord();
        String answerWord = correctModel.getCorrectWord();
        viewValuesSetter(word, answerWord, image, holder);
    }

    private void viewValuesSetter(String word, String answerWord, int image, ViewHolder holder){

        holder.tvImage.setImageResource(image);
        if (image > 0){
            holder.tvImage.setVisibility(View.VISIBLE);
            holder.tvWord.setVisibility(View.INVISIBLE);
        }
        holder.tvWord.setText(word);
        holder.answerWord.setText(answerWord);

        holder.tvImage.setOnClickListener(v ->
                Toast.makeText(caContext, word + " -- " + answerWord, Toast.LENGTH_SHORT).show());
    }

}
