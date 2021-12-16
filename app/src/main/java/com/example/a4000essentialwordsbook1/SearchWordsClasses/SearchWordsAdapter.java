package com.example.a4000essentialwordsbook1.SearchWordsClasses;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.R;

import java.io.File;
import java.util.ArrayList;

public class SearchWordsAdapter extends RecyclerView.Adapter<SearchWordsAdapter.SearchViewHolder> {

    private final Context searchContext;
    private final ArrayList<WordModel> searchList;
    private final LayoutInflater inflater;
    private final ActivityStarterInterface activityInterface;





    public SearchWordsAdapter(Context context, ArrayList<WordModel> models, ActivityStarterInterface activityInterface){
        this.searchContext = context;
        this.searchList = models;
        this.inflater = LayoutInflater.from(context);
        this.activityInterface = activityInterface;
    }

    @NonNull
    @Override
    public SearchWordsAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_item_activity, parent, false);
        return new SearchViewHolder(view, searchList, activityInterface);
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
        final String appPath = searchContext.getApplicationInfo().dataDir;
        final File imageDir = new File(Environment.DIRECTORY_DOWNLOADS, File.separator + "4000 Essential Words");

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + model.getBookNum());
        final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + model.getUnitNum());

        final File imgName = new File(wordUnitImgBookPath, File.separator + "." + new File(model.getImgUri()).getName());
        final File imgFile = new File(Environment.getExternalStoragePublicDirectory(imageDir.toString()), imgName.toString());


        if (imgFile.exists()){
            Drawable imgDrawable = Drawable.createFromPath(imgFile.toString());
            Glide.with(searchContext)
                    .load(imgDrawable)
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.searchImage);
        }else {
            Glide.with(searchContext)
                    .load(model.getImgUri())
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.searchImage);
        }
    }
    private void searchTextViewValueSetter(SearchViewHolder holder, WordModel model){
        holder.wordTitle.setText(model.getWord());
        wordStudyStatus(holder, model);
        holder.bookTitle.setText("Book " + model.getBookNum());
        holder.unitTitle.setText("Unit " + model.getUnitNum());
    }
    private void wordStudyStatus(SearchViewHolder holder, WordModel model){
        if (model.getEasyFlag() == 1){
            holder.studyStatusTitle.setText("Already know");
            holder.studyStatusTitle.setTextColor(ContextCompat.getColor(searchContext, R.color.correctColor));
        }else {
            holder.studyStatusTitle.setText("Need To Review");
            holder.studyStatusTitle.setTextColor(ContextCompat.getColor(searchContext, R.color.needToReviewColor));
        }
    }


    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView searchImage;
        TextView wordTitle, bookTitle, unitTitle;
        TextView studyStatusTitle;
        CardView itemCardView;
        private final ActivityStarterInterface activityInterface;
        private final ArrayList<WordModel> searchList;

        public SearchViewHolder(@NonNull View itemView, ArrayList<WordModel> searchList, ActivityStarterInterface activityInterface) {
            super(itemView);
            this.searchList = searchList;
            this.activityInterface = activityInterface;
            findViewsById(itemView);
        }

        private void findViewsById(View itemView) {
            itemCardView = itemView.findViewById(R.id.search_item_car_view);
            wordTitle = itemView.findViewById(R.id.search_item_word);
            searchImage = itemView.findViewById(R.id.search_item_image_view);
            bookTitle = itemView.findViewById(R.id.search_item_book);
            unitTitle = itemView.findViewById(R.id.search_item_unit);
            studyStatusTitle = itemView.findViewById(R.id.search_item_study_status);
            itemClickListener();
        }

        private void itemClickListener(){
            itemCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getBindingAdapterPosition();
                    activityInterface.activityInterface(position);
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return searchList.size();
    }
}
