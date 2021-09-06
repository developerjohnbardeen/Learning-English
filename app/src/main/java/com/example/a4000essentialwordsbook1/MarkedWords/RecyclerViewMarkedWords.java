package com.example.a4000essentialwordsbook1.MarkedWords;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.MarkedWords.ReviewWords.MainReviewMarkedWordActivity;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.DataBases.WordDatabaseOpenHelper;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import java.util.ArrayList;

public class RecyclerViewMarkedWords extends RecyclerView.Adapter<RecyclerViewMarkedWords.ViewHolder> implements View.OnClickListener{
    private final ArrayList<WordModel> list;
    private final Context context;
    private final LayoutInflater inflater;


    public RecyclerViewMarkedWords(Context context, ArrayList<WordModel> list){
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerViewMarkedWords.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.marked_word_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMarkedWords.ViewHolder holder, int position) {
        viewsGetterAndSetter(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void removingItem(int position){

        WordDatabaseOpenHelper markedDatabase = new WordDatabaseOpenHelper(context);
        SQLiteDatabase db = markedDatabase.getWritableDatabase();
        ContentValues markedFlagValue = new ContentValues();
        markedFlagValue.put(DB_NOTES.HARD_FLAG, 0);
        db.update(DB_NOTES.WORD_TABLE,
                markedFlagValue, DB_NOTES.WORD_ID + " = ? ", new String[]{Integer.toString(position)});
        db.close();
    }


    private void viewsGetterAndSetter(ViewHolder holder, int position){
        WordModel markedList = list.get(position);

        int image = markedList.getWordImage();
        String word = markedList.getWord();
        String phonetic = markedList.getPhonetic();

        viewValuesSetter(holder, position, image, word, phonetic);
        viewOnClickListener(holder, position, markedList);
    }

    private void viewValuesSetter(ViewHolder holder, int position, int image, String word, String phonetic){

        Glide.with(context)
                .load(image)
                .placeholder(R.drawable.loadimg)
                .error(R.drawable.loadimg)
                .into(holder.wordImage);


        holder.wordName.setText(word);
        holder.wordPhonetic.setText(phonetic);
        holder.cardView.setOnClickListener(v -> intentActivity(position));

    }

    private void viewOnClickListener(ViewHolder holder, int position, WordModel markedList){
        int id = markedList.getId();
        holder.imgButton.setOnClickListener(v -> {
            list.remove(position);
            notifyDataSetChanged();
            Runnable removeThread = () -> removingItem(id);
            removeThread.run();
        });
    }

    private void intentActivity(int position){
        Intent intent = new Intent(context, MainReviewMarkedWordActivity.class);
        intent.putParcelableArrayListExtra("reviewList", list);
        intent.putExtra("cardPosition", position);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView wordImage;
        ImageButton imgButton;
        TextView wordName;
        TextView wordTranslate;
        TextView wordPhonetic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.marked_word_card_view);
            wordImage = itemView.findViewById(R.id.img_word_marked);
            wordName = itemView.findViewById(R.id.marked_words_name);
            wordPhonetic = itemView.findViewById(R.id.marked_words_phonetic);
            imgButton = itemView.findViewById(R.id.marked_btn_word_changer);
            wordTranslate = itemView.findViewById(R.id.marked_words_translate);

         }
    }
}
