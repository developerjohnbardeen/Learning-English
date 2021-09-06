package com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordSlideCardViewActivity;
import com.example.a4000essentialwordsbook1.Models.WordModel;

import java.util.ArrayList;

public class WordListRecyclerView extends RecyclerView.Adapter<WordListRecyclerView.WordViewHolder>{
    private final Context wordContext;
    private final LayoutInflater inflater;
    private final ArrayList<WordModel> wordList;
    private final int unitNum, dbNum;

    public  WordListRecyclerView(Context context, ArrayList<WordModel> wordList, int unitNum, int dbNum){
        this.wordContext = context;
        this.inflater = LayoutInflater.from(context);
        this.wordList = wordList;
        this.unitNum = unitNum;
        this.dbNum = dbNum;
    }


    @NonNull
    @Override
    public WordListRecyclerView.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.word_list_recyclerview, parent, false);
        return new WordViewHolder(view, wordContext);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListRecyclerView.WordViewHolder holder, int position) {


        WordModel listModel = wordList.get(position);

        Glide.with(wordContext)
                .load(listModel.getWordImage())
                .placeholder(R.drawable.loadimg)
                .error(R.drawable.loadimg)
                .into(holder.image);

        holder.word.setText(listModel.getWord());
        holder.phonetic.setText(listModel.getPhonetic());
        holder.translateWord.setText(listModel.getTranslateWord());


        holder.wordCardView.setOnClickListener(v -> {
            Intent wordDetailedIntent = new Intent(wordContext, WordSlideCardViewActivity.class);
            wordDetailedIntent.putExtra("unitNumber", unitNum);
            wordDetailedIntent.putExtra("dbNumber", dbNum);
            wordDetailedIntent.putExtra("wordId", position);
            wordContext.startActivity(wordDetailedIntent);
        });
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }


    public static class WordViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView word;
        TextView phonetic;
        TextView translateWord;
        CardView wordCardView;
        Context context;

        public WordViewHolder(@NonNull View itemView, Context viewContext) {
            super(itemView);
            this.context = viewContext;
            image = itemView.findViewById(R.id.word_img);
            word = itemView.findViewById(R.id.word_txt);
            phonetic = itemView.findViewById(R.id.phonetic_word_txt);
            translateWord = itemView.findViewById(R.id.translate_word_txt);
            wordCardView = itemView.findViewById(R.id.card_view_word);

        }
    }

    private class RecyclerViewAsyncTask extends AsyncTask<Integer, WordModel, Void>{

        private final WordViewHolder holder;

        public RecyclerViewAsyncTask(WordViewHolder holder){
            this.holder = holder;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onProgressUpdate(WordModel... model) {
            super.onProgressUpdate(model);

            Glide.with(wordContext)
                    .load(model[0].getWordImage())
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.image);

            holder.word.setText(model[0].getWord());
            holder.phonetic.setText(model[0].getPhonetic());
            holder.translateWord.setText(model[0].getTranslateWord());
        }

        @Override
        protected Void doInBackground(Integer... position) {

            WordModel model = wordList.get(position[0]);

            publishProgress(model);


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}