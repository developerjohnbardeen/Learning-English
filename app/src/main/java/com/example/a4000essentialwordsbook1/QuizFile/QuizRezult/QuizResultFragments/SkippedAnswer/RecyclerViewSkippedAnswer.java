package com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.SkippedAnswer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.SkippedModel;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.UpdateDatabases.UpdateWordDatabase;

import java.util.ArrayList;

public class RecyclerViewSkippedAnswer extends RecyclerView.Adapter<RecyclerViewSkippedAnswer.ViewHolder> implements View.OnClickListener{

    private final Context skpContext;
    private final ArrayList<SkippedModel> skippedList;
    private final LayoutInflater inflater;

    //view components
    private String skippedWord;
    private String correctWord;
    private int bookImage;

    public RecyclerViewSkippedAnswer(Context context, ArrayList<SkippedModel> list){
        this.skpContext = context;
        this.inflater = LayoutInflater.from(context);
        this.skippedList = list;

    }



    @NonNull
    @Override
    public RecyclerViewSkippedAnswer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.skipped_answer_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewSkippedAnswer.ViewHolder holder, int position) {

        viewValuesGetter(position);
        viewValuesSetter(holder, position);
    }

    @Override
    public int getItemCount() {
        return skippedList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvWord;
        TextView trsltWord;
        ImageView bookImg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.skipped_tv_word);
            trsltWord = itemView.findViewById(R.id.skipped_trsl_word);
            bookImg = itemView.findViewById(R.id.skipped_book_image);
        }
    }

    private void viewValuesGetter(int position){
        SkippedModel skippedModel = skippedList.get(position);
        skippedWord = skippedModel.getTvWord();
        correctWord = skippedModel.getCorrectWord();
    }

    private void viewValuesSetter(ViewHolder holder, int id){
        holder.tvWord.setText(skippedWord + " -> " + id);
        holder.trsltWord.setText(correctWord);
        holder.bookImg.setImageResource(bookImage);
        viewOnClickListener(holder);
    }

    private void viewOnClickListener(ViewHolder holder){
        holder.bookImg.setOnClickListener(this);
    }

    private void bookWordFunction(){
        UpdateWordDatabase updateWordDatabase = new UpdateWordDatabase(skpContext);
        String flagColumn = DB_NOTES.HARD_FLAG;
        updateWordDatabase.wordDatabaseUpdate(flagColumn, 0, 1);

    }

    private void removeBookWordFunction(){

    }



    @Override
    public void onClick(View v) {
        bookWordFunction();
    }
}
