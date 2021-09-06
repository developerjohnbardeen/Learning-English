package com.example.a4000essentialwordsbook1.SearchWordsClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.R;

import java.util.ArrayList;

public class SearchWordsAdapter extends RecyclerView.Adapter<SearchWordsAdapter.SearchViewHolder> {

    private final Context searchContext;
    private final ArrayList<WordModel> searchList;
    private final LayoutInflater inflater;




    public SearchWordsAdapter(Context context, ArrayList<WordModel> models){
        this.searchContext = context;
        this.searchList = models;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SearchWordsAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_item_activity, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        WordModel model = searchList.get(position);
        searchValueSetter(holder, model);
    }

    private void searchValueSetter(SearchViewHolder holder, WordModel model){
        searchImageViewValueSetter(holder, model);
        searchTextViewValueSetter(holder, model);

    }

    private void searchImageViewValueSetter(SearchViewHolder holder, WordModel model){
        Glide.with(searchContext)
                .load(model.getWordImage())
                .placeholder(R.drawable.loadimg)
                .error(R.drawable.loadimg)
                .into(holder.searchImage);
    }
    private void searchTextViewValueSetter(SearchViewHolder holder, WordModel model){
        holder.wordTitle.setText(model.getWord());
        wordStudyStatus(holder, model);
        holder.bookTitle.setText("Book " + model.getBookNum());
        holder.unitTitle.setText("Unit " + model.getUnitNum());
    }
    private void wordStudyStatus(SearchViewHolder holder, WordModel model){
        if (model.getEasyFlag() > 0){
            holder.studyStatusTitle.setText("Already know");
        }else {
            holder.studyStatusTitle.setText("Need To Review");
        }
    }


    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView searchImage;
        TextView wordTitle, bookTitle, unitTitle;
        TextView studyStatusTitle;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            wordTitle = itemView.findViewById(R.id.search_item_word);
            searchImage = itemView.findViewById(R.id.search_item_image_view);
            bookTitle = itemView.findViewById(R.id.search_item_book);
            unitTitle = itemView.findViewById(R.id.search_item_unit);
            studyStatusTitle = itemView.findViewById(R.id.search_item_study_status);
        }
    }


    @Override
    public int getItemCount() {
        return searchList.size();
    }
}
