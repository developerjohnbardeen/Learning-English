package com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.SkippedAnswer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a4000essentialwordsbook1.MarkedWords.MarkedWordActivity;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.FontTypeFiles.GlobalFonts;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;
import com.example.a4000essentialwordsbook1.UpdateDatabases.UpdateWordDatabase;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewSkippedAnswer extends RecyclerView.Adapter<RecyclerViewSkippedAnswer.ViewHolder> implements View.OnClickListener{

    private final Context skpContext;
    private final ArrayList<WordModel> skippedList;
    private final LayoutInflater inflater;
    private final int[] dbInfoList;

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

    //view components
    private String skippedWord;
    private String correctWord;
    private int bookImage;

    public RecyclerViewSkippedAnswer(Context context, String quizType, ArrayList<WordModel> list, int[] dbInfoList){
        this.skpContext = context;
        this.inflater = LayoutInflater.from(context);
        this.skippedList = list;
        this.quizType = quizType;
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

        ImageView tvImgWord;
        TextView titleWord;
        TextView correctWord;
        Button bookImg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvImgWord = itemView.findViewById(R.id.skipped_tv_image_view);
            titleWord = itemView.findViewById(R.id.skipped_tv_word);
            correctWord = itemView.findViewById(R.id.skipped_correct_word);
            bookImg = itemView.findViewById(R.id.skipped_book_image);
        }
    }


    private void viewValuesGetterAndSetter(ViewHolder holder, int position){
        WordModel skippedModel = skippedList.get(position);

        int image = skippedModel.getWordImage();
        if (quizType.equalsIgnoreCase("picWord")){
            holder.tvImgWord.setVisibility(View.VISIBLE);
            holder.titleWord.setVisibility(View.INVISIBLE);
        }

        int columnId = skippedModel.getId();
        int hardFlag = skippedModel.getHardFlag();
        String word = skippedModel.getWord();
        String wrongWord = skippedModel.getCorrectWord();
        String correctWord = skippedModel.getCorrectWord();

        viewValuesSetter(holder, image, columnId, word,
                wrongWord, correctWord, skippedModel);

    }

    private void viewValuesSetter(ViewHolder holder,
                                  int image, int columnId, String word,
                                  String wrongWord, String correctWord,
                                  WordModel model){

        if (model.getHardFlag() == 1){
            holder.bookImg.setBackgroundResource(R.drawable.ic_baseline_bookmark_24);
        }else if (model.getHardFlag() == 0){
            holder.bookImg.setBackgroundResource(R.drawable.ic_baseline_bookmark_border_24);
        }

        //holder.tvImgWord.setImageResource(image);
        setImageResources(holder, model);
        holder.titleWord.setText(word);
        holder.correctWord.setText(model.getSkippedWord());

        textViewAttributeSetter(holder);
        viewOnClickListener(holder);
        viewOnClickListener(holder, columnId, model);
    }
    private void setImageResources(ViewHolder holder, WordModel model){
        final String appPath = skpContext.getApplicationInfo().dataDir;
        final File imageDir = new File(Environment.DIRECTORY_DOWNLOADS, File.separator + "4000 Essential Words");


        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + model.getBookNum());
        final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + model.getUnitNum());

        final File imgName = new File(wordUnitImgBookPath, File.separator + "." + new File(model.getImgUri()).getName());
        final File imgFile = new File(Environment.getExternalStoragePublicDirectory(imageDir.toString()), imgName.toString());


        if (imgFile.exists()){
            Drawable imgDrawable = Drawable.createFromPath(imgFile.toString());
            Glide.with(skpContext)
                    .load(imgDrawable)
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.tvImgWord);
        }else {
            Glide.with(skpContext)
                    .load(model.getImgUri())
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.tvImgWord);
        }
    }


    private void viewOnClickListener(ViewHolder holder, int columnId, WordModel model){
        Intent intent = new Intent(skpContext, MarkedWordActivity.class);
        holder.tvImgWord.setOnClickListener(v -> skpContext.startActivity(intent));
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

    private void textViewAttributeSetter(ViewHolder holder){

        holder.titleWord.setTypeface(typefaceQuestionDeterminer(), txtStyleQuestionDeterminer());
        holder.correctWord.setTypeface(typefaceOptionsDeterminer(), txtStyleOptionsDeterminer());

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
        return ResourcesCompat.getFont(skpContext, perFontFace());
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
        fontTypePreferences = skpContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getBoolean(perTxtBolderKey, false);
    }
    private int perFontVal(){
        fontTypePreferences = skpContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getInt(perListPositionKey, 1);
    }

    private Typeface engTypeFace(){
        return ResourcesCompat.getFont(skpContext, engFontFace());
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
        fontTypePreferences = skpContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getBoolean(engTxtBolderKey, false);
    }
    private int engFontVal(){
        fontTypePreferences = skpContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getInt(engListPositionKey, 1);
    }


}
