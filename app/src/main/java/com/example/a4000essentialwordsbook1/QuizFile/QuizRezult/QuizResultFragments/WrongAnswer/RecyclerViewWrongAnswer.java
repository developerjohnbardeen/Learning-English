package com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.WrongAnswer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.MarkedWords.MarkedWordActivity;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.WrongModel;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.UpdateDatabases.UpdateWordDatabase;

import java.util.ArrayList;




public class RecyclerViewWrongAnswer extends RecyclerView.Adapter<RecyclerViewWrongAnswer.ViewHolder>{
    private final Context wrongContext;
    private final LayoutInflater inflater;
    private final ArrayList<WrongModel> wrongList;

    private Intent intent;



    public RecyclerViewWrongAnswer(Context context, ArrayList<WrongModel> list){
        this.wrongContext = context;
        this.wrongList = list;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerViewWrongAnswer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.wrong_answer_recycler_view, parent, false);
        return new ViewHolder(view, wrongContext);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewWrongAnswer.ViewHolder holder, int position) {
        intent = new Intent(wrongContext, MarkedWordActivity.class);
        viewValuesGetterAndSetter(holder, position);

    }

    @Override
    public int getItemCount() {
        return wrongList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView tvImage;
        TextView tvWord;
        TextView wrongWord;
        TextView correctWord;
        Button bookImg;
        Context mContext;


        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.mContext = context;
            tvImage = itemView.findViewById(R.id.wrong_tv_image_view);
            tvWord = itemView.findViewById(R.id.wrong_tv_word);
            wrongWord = itemView.findViewById(R.id.user_wrong_answer);
            correctWord = itemView.findViewById(R.id.txt_wrong_correct_answer);
            bookImg = itemView.findViewById(R.id.wrong_book_image);
        }

    }

    private void viewValuesGetterAndSetter(ViewHolder holder, int position){
        WrongModel wrongModel = wrongList.get(position);

        int image = wrongModel.getTvImage();
        if (image > 0){
            holder.tvImage.setVisibility(View.VISIBLE);
            holder.tvWord.setVisibility(View.INVISIBLE);
        }

        int columnId = wrongModel.getId();
        int hardFlag = wrongModel.getHardFlag();
        String word = wrongModel.getTvWord();
        String wrongWord = wrongModel.getWrongWord();
        String correctWord = wrongModel.getCorrectWord();

        viewValuesSetter(holder, image, columnId, word,
                wrongWord, correctWord, hardFlag, wrongModel);
    }

    private void viewValuesSetter(ViewHolder holder,
                                  int image, int columnId, String word,
                                  String wrongWord, String correctWord,
                                  int hardFlag, WrongModel model){

        if (model.getHardFlag() == 1){
            holder.bookImg.setBackgroundResource(R.drawable.ic_baseline_bookmark_24);
        }else if (model.getHardFlag() == 0){
            holder.bookImg.setBackgroundResource(R.drawable.ic_baseline_bookmark_border_24);
        }

        holder.tvImage.setImageResource(image);
        holder.tvWord.setText(word);
        holder.wrongWord.setText(wrongWord);
        holder.correctWord.setText(correctWord);

        viewOnClickListener(holder, columnId, hardFlag, model);
    }


    private void viewOnClickListener(ViewHolder holder, int columnId, int flag, WrongModel model){
        holder.tvWord.setOnClickListener(v -> wrongContext.startActivity(intent));
        holder.tvImage.setOnClickListener(v -> wrongContext.startActivity(intent));


        holder.bookImg.setOnClickListener(v -> {
            if (model.getHardFlag() == 0){
                bookWordFunction(columnId, 1);
                holder.bookImg.setBackgroundResource(R.drawable.ic_baseline_bookmark_24);
                model.setHardFlag(1);
                holder.bookImg.setSelected(true);
            }else if (model.getHardFlag() == 1){
                bookWordFunction(columnId, 0);
                holder.bookImg.setBackgroundResource(R.drawable.ic_baseline_bookmark_border_24);
                model.setHardFlag(0);
                holder.bookImg.setSelected(false);
            }
        });

    }

    private void bookWordFunction(int columnId, int value){
        UpdateWordDatabase updateWordDatabase = new UpdateWordDatabase(wrongContext);
        String flagColumn = DB_NOTES.HARD_FLAG;
        updateWordDatabase.wordDatabaseUpdate(flagColumn, columnId , value);
    }

}