package com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.SkippedAnswer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4000essentialwordsbook1.MarkedWords.MarkedWordActivity;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.WrongModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.WrongAnswer.RecyclerViewWrongAnswer;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.SkippedModel;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.UpdateDatabases.UpdateWordDatabase;

import java.util.ArrayList;

public class RecyclerViewSkippedAnswer extends RecyclerView.Adapter<RecyclerViewSkippedAnswer.ViewHolder> implements View.OnClickListener{

    private final Context skpContext;
    private final ArrayList<SkippedModel> skippedList;
    private final LayoutInflater inflater;
    private final int[] dbInfoList;

    //view components
    private String skippedWord;
    private String correctWord;
    private int bookImage;

    public RecyclerViewSkippedAnswer(Context context, ArrayList<SkippedModel> list, int[] dbInfoList){
        this.skpContext = context;
        this.inflater = LayoutInflater.from(context);
        this.skippedList = list;
        this.dbInfoList = dbInfoList;
    }



    @NonNull
    @Override
    public RecyclerViewSkippedAnswer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.skipped_answer_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewSkippedAnswer.ViewHolder holder, int position) {
        viewValuesGetterAndSetter(holder, position);
    }

    @Override
    public int getItemCount() {
        return skippedList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView tvWord;
        TextView titleWord;
        TextView correctWord;
        ImageView bookImg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.skipped_tv_image_view);
            titleWord = itemView.findViewById(R.id.skipped_tv_word);
            correctWord = itemView.findViewById(R.id.skipped_correct_word);
            bookImg = itemView.findViewById(R.id.skipped_book_image);
        }
    }


    private void viewValuesGetterAndSetter(ViewHolder holder, int position){
        SkippedModel skippedModel = skippedList.get(position);

        int image = skippedModel.getTvImage();
        if (image > 0){
            holder.tvWord.setVisibility(View.VISIBLE);
            holder.titleWord.setVisibility(View.INVISIBLE);
        }

        int columnId = skippedModel.getWordId();
        int hardFlag = skippedModel.getHardFlag();
        String word = skippedModel.getTvWord();
        String wrongWord = skippedModel.getCorrectWord();
        String correctWord = skippedModel.getCorrectWord();

        viewValuesSetter(holder, image, columnId, word,
                wrongWord, correctWord, skippedModel);
    }

    private void viewValuesSetter(ViewHolder holder,
                                  int image, int columnId, String word,
                                  String wrongWord, String correctWord,
                                  SkippedModel model){

        if (model.getHardFlag() == 1){
            holder.bookImg.setBackgroundResource(R.drawable.ic_baseline_bookmark_24);
        }else if (model.getHardFlag() == 0){
            holder.bookImg.setBackgroundResource(R.drawable.ic_baseline_bookmark_border_24);
        }

        holder.tvWord.setImageResource(image);
        holder.titleWord.setText(word);
        holder.correctWord.setText(correctWord);


        viewOnClickListener(holder, columnId, model);
    }


    private void viewOnClickListener(ViewHolder holder, int columnId, SkippedModel model){
        Intent intent = new Intent(skpContext, MarkedWordActivity.class);
        holder.tvWord.setOnClickListener(v -> skpContext.startActivity(intent));
        holder.titleWord.setOnClickListener(v -> skpContext.startActivity(intent));


        holder.bookImg.setOnClickListener(v -> {
            if (model.getHardFlag() == 0){
                bookWordFunction(columnId, 1);
                holder.bookImg.setBackgroundResource(R.drawable.ic_baseline_bookmark_24);
                model.setHardFlag(1);
                holder.bookImg.setSelected(true);
            }else if (model.getHardFlag() == 1){
                bookWordFunction(columnId,0);
                holder.bookImg.setBackgroundResource(R.drawable.ic_baseline_bookmark_border_24);
                model.setHardFlag(0);
                holder.bookImg.setSelected(false);
            }
        });

    }

/*    private void viewValuesGetter(int position){
        SkippedModel skippedModel = skippedList.get(position);
        skippedWord = skippedModel.getTvWord();
        correctWord = skippedModel.getCorrectWord();
    }

    private void viewValuesSetter(ViewHolder holder, int id){
        holder.titleWord.setText(skippedWord);
        holder.trsltWord.setText(correctWord);
        holder.bookImg.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
        viewOnClickListener(holder);
    }*/

    private void viewOnClickListener(ViewHolder holder){
        holder.bookImg.setOnClickListener(this);
    }

    private void bookWordFunction(int columnId, int val){
        UpdateWordDatabase updateWordDatabase = new UpdateWordDatabase(skpContext, dbInfoList);
        String flagColumn = DB_NOTES.HARD_FLAG;
        updateWordDatabase.wordDatabaseUpdate(flagColumn, columnId, val);

    }


    @Override
    public void onClick(View v) {
        bookWordFunction(1,1);
    }
}
