package com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.CorrectAnswer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.CorrectModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.SkippedAnswer.RecyclerViewSkippedAnswer;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.WordListRecyclerView;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.FontTypeFiles.GlobalFonts;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;

import java.io.File;
import java.util.ArrayList;


public class RecyclerViewCorrectAnswer extends RecyclerView.Adapter<RecyclerViewCorrectAnswer.ViewHolder> {
    private final Context caContext;
    private final LayoutInflater inflater;
    private final ArrayList<WordModel> correctList;
    private final int dbNum, unitNum;

    private final String quizType;
    private final String engType = "engToPer",
            persianType = "perToEng", photoWordType = "picWord";

    private final int[] perFontList = GlobalFonts.perFontList;
    private final int[] engFontList = GlobalFonts.engFontList;

    private SharedPreferences fontTypePreferences;
    private SharedPreferences textSizePreferences;
    private final String fontTypePreferencesName = SettingsPreferencesNotes.SETTINGS_TEXT_FONT_TYPE_PREFERENCES;
    private final String textViewSizePreferencesName = SettingsPreferencesNotes.SETTINGS_TEXT_VIEW_SIZE_PREFERENCES;
    private final String engListPositionKey = SettingsPreferencesNotes.ENGLISH_LIST_POSITION_KEY;
    private final String perListPositionKey = SettingsPreferencesNotes.PERSIAN_LIST_POSITION_KEY;
    private final String txtViewSizeKey = SettingsPreferencesNotes.PERSIAN_TEXT_VIEW_SIZE_KEY;
    private final String engTxtViewSizeKey = SettingsPreferencesNotes.ENGLISH_TEXT_VIEW_SIZE_KEY;
    private final String engTxtBolderKey = SettingsPreferencesNotes.ENGLISH_TEXT_BOLDER_KEY;
    private final String perTxtBolderKey = SettingsPreferencesNotes.PERSIAN_TEXT_BOLDER_KEY;


    public RecyclerViewCorrectAnswer(Context context,String quizType, ArrayList<WordModel> list, int[] dbInfoList){
        this.caContext = context;
        this.inflater = LayoutInflater.from(context);
        this.correctList = list;
        this.quizType = quizType;
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
        WordModel correctModel = correctList.get(position);
        String word = correctModel.getWord();
        String answerWord = correctModel.getCorrectWord();
        viewValuesSetter(word, answerWord, position, holder);

    }

    private void viewValuesSetter(String word, String answerWord, int position, ViewHolder holder){

        //holder.tvImage.setImageResource(image);
        if (quizType.equalsIgnoreCase("picWord")){
            setImageResources(holder, correctList.get(position));
            holder.tvImage.setVisibility(View.VISIBLE);
            holder.tvWord.setVisibility(View.INVISIBLE);
        }
        holder.tvWord.setText(word);
        holder.answerWord.setText(answerWord);

        holder.tvImage.setOnClickListener(v ->
                Toast.makeText(caContext, word + " -- " + answerWord, Toast.LENGTH_SHORT).show());

        textViewAttributeSetter(holder);
    }
    private void setImageResources(ViewHolder holder, WordModel model){
        final String appPath = caContext.getApplicationInfo().dataDir;
        final File imageDir = new File(Environment.DIRECTORY_DOWNLOADS, File.separator + "4000 Essential Words");

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + model.getBookNum());
        final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + model.getUnitNum());

        final File imgName = new File(wordUnitImgBookPath, File.separator + "." + new File(model.getImgUri()).getName());
        final File imgFile = new File(Environment.getExternalStoragePublicDirectory(imageDir.toString()), imgName.toString());


        if (imgFile.exists()){
            Drawable imgDrawable = Drawable.createFromPath(imgFile.toString());
            Glide.with(caContext)
                    .load(imgDrawable)
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.tvImage);
        }else {
            Glide.with(caContext)
                    .load(model.getImgUri())
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.tvImage);
        }
    }



    private void textViewAttributeSetter(ViewHolder holder){

        holder.tvWord.setTypeface(typefaceQuestionDeterminer(), txtStyleQuestionDeterminer());
        holder.answerWord.setTypeface(typefaceOptionsDeterminer(), txtStyleOptionsDeterminer());

    }

    private Typeface typefaceQuestionDeterminer(){
        if (quizType.equalsIgnoreCase(photoWordType) || quizType.equalsIgnoreCase(engType)){
            return engTypeFace();
        }else{
            //quizType.equalsIgnoreCase(persianType)
            return perTypeFace();
        }
    }
    private int txtStyleQuestionDeterminer(){
        if (quizType.equalsIgnoreCase(photoWordType) || quizType.equalsIgnoreCase(engType)){
            return engTextStyle();
        }else{
            //quizType.equalsIgnoreCase(persianType)
            return perTextStyle();
        }
    }

    private Typeface typefaceOptionsDeterminer(){
        if (quizType.equalsIgnoreCase(photoWordType) || quizType.equalsIgnoreCase(engType)){
            return perTypeFace();
        }else{
            //quizType.equalsIgnoreCase(persianType)
            return engTypeFace();
        }
    }
    private int txtStyleOptionsDeterminer(){
        if (quizType.equalsIgnoreCase(photoWordType) || quizType.equalsIgnoreCase(engType)){
            return perTextStyle();
        }else{
            //quizType.equalsIgnoreCase(persianType)
            return engTextStyle();
        }
    }


    private Typeface perTypeFace(){
        return ResourcesCompat.getFont(caContext, perFontFace());
    }
    private int perFontFace(){
        return perFontList[perFontVal()];
    }
    private int perTextStyle(){
        if (getPerBolderPreference()){
            return Typeface.BOLD;
        }else {
            return Typeface.NORMAL;
        }
    }
    private boolean getPerBolderPreference(){
        fontTypePreferences = caContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getBoolean(perTxtBolderKey, false);
    }
    private int perFontVal(){
        fontTypePreferences = caContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getInt(perListPositionKey, 1);
    }

    private Typeface engTypeFace(){
        return ResourcesCompat.getFont(caContext, engFontFace());
    }
    private int engFontFace(){
        return engFontList[engFontVal()];
    }
    private int engTextStyle(){
        if (getEngBolderPreference()){
            return Typeface.BOLD;
        }else {
            return Typeface.NORMAL;
        }
    }
    private boolean getEngBolderPreference(){
        fontTypePreferences = caContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getBoolean(engTxtBolderKey, false);
    }
    private int engFontVal(){
        fontTypePreferences = caContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getInt(engListPositionKey, 1);
    }


}
