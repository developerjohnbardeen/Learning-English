package com.example.a4000essentialwordsbook1.QuizFile.QuizRezult.QuizResultFragments.WrongAnswer;

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
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.MarkedWords.MarkedWordActivity;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.WrongModel;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.FontTypeFiles.GlobalFonts;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;
import com.example.a4000essentialwordsbook1.UpdateDatabases.UpdateWordDatabase;

import java.io.File;
import java.util.ArrayList;




public class RecyclerViewWrongAnswer extends RecyclerView.Adapter<RecyclerViewWrongAnswer.ViewHolder>{
    private final String quizType;
    private final Context wrongContext;
    private final LayoutInflater inflater;
    private final int[] dbInfoList; //first index is dbNumber, second is unitNum
    private final ArrayList<WordModel> wrongList;

    private Intent intent;

    private final int[] perFontList = GlobalFonts.perFontList;
    private final int[] engFontList = GlobalFonts.engFontList;

    private final String engType = "engToPer",
            persianType = "perToEng", photoWordType = "picWord";


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



    public RecyclerViewWrongAnswer(String quizType, Context context, ArrayList<WordModel> list, int[] dbInfoList){
        this.wrongContext = context;
        this.wrongList = list;
        this.inflater = LayoutInflater.from(context);
        this.dbInfoList = dbInfoList;
        this.quizType = quizType;
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
        Button markedImg;
        Context mContext;


        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.mContext = context;
            tvImage = itemView.findViewById(R.id.wrong_tv_image_view);
            tvWord = itemView.findViewById(R.id.wrong_tv_word);
            wrongWord = itemView.findViewById(R.id.user_wrong_answer);
            correctWord = itemView.findViewById(R.id.txt_wrong_correct_answer);
            markedImg = itemView.findViewById(R.id.wrong_book_image);
        }
    }

    private void viewValuesGetterAndSetter(ViewHolder holder, int position){
        WordModel wrongModel = wrongList.get(position);

        int image = wrongModel.getWordImage();
        if (quizType.equalsIgnoreCase("picWord")){
            holder.tvImage.setVisibility(View.VISIBLE);
            holder.tvWord.setVisibility(View.INVISIBLE);
        }

        int columnId = wrongModel.getId();
        int hardFlag = wrongModel.getHardFlag();
        String word = wrongModel.getWord();
        String wrongWord = wrongModel.getWrongWord();
        String correctWord = wrongModel.getCorrectWord();

        viewValuesSetter(holder, image, columnId, word,
                wrongWord, correctWord, wrongModel);
    }

    private void viewValuesSetter(ViewHolder holder,
                                  int image, int columnId, String word,
                                  String wrongWord, String correctWord,
                                  WordModel model){

        if (model.getHardFlag() == 1){
            holder.markedImg.setBackgroundResource(R.drawable.ic_baseline_bookmark_24);
        }else if (model.getHardFlag() == 0){
            holder.markedImg.setBackgroundResource(R.drawable.ic_baseline_bookmark_border_24);
        }

        //holder.tvImage.setImageResource(image);
        setImageResources(holder, model);
        holder.tvWord.setText(word);
        holder.wrongWord.setText(wrongWord);
        holder.correctWord.setText(correctWord);

        textViewAttributeSetter(holder);
        viewOnClickListener(holder, columnId, model);
    }
    private void setImageResources(ViewHolder holder, WordModel model){
        final String appPath = wrongContext.getApplicationInfo().dataDir;
        final File imageDir = new File(Environment.DIRECTORY_DOWNLOADS, File.separator + "4000 Essential Words");

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + model.getBookNum());
        final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + model.getUnitNum());

        final File imgName = new File(wordUnitImgBookPath, File.separator + "." + new File(model.getImgUri()).getName());
        final File imgFile = new File(Environment.getExternalStoragePublicDirectory(imageDir.toString()), imgName.toString());


        if (imgFile.exists()){
            Drawable imgDrawable = Drawable.createFromPath(imgFile.toString());
            Glide.with(wrongContext)
                    .load(imgDrawable)
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.tvImage);
        }else {
            Glide.with(wrongContext)
                    .load(model.getImgUri())
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.tvImage);
        }
    }


    private void viewOnClickListener(ViewHolder holder, int columnId, WordModel model){
        holder.tvWord.setOnClickListener(v -> wrongContext.startActivity(intent));
        holder.tvImage.setOnClickListener(v -> wrongContext.startActivity(intent));


        holder.markedImg.setOnClickListener(v -> {
            if (model.getHardFlag() == 0){
                bookWordFunction(columnId, model, 1);
                holder.markedImg.setBackgroundResource(R.drawable.ic_baseline_bookmark_24);
                model.setHardFlag(1);
                holder.markedImg.setSelected(true);
            }else if (model.getHardFlag() == 1){
                bookWordFunction(columnId, model,0);
                holder.markedImg.setBackgroundResource(R.drawable.ic_baseline_bookmark_border_24);
                model.setHardFlag(0);
                holder.markedImg.setSelected(false);
            }
        });

    }

    private void bookWordFunction(int columnId, WordModel model, int value){
        UpdateWordDatabase updateWordDatabase = new UpdateWordDatabase(wrongContext, dbInfoList);
        String flagColumn = DB_NOTES.HARD_FLAG;
        updateWordDatabase.wordDatabaseUpdate(flagColumn, columnId , value);
    }

    private void textViewAttributeSetter(ViewHolder holder){

        holder.tvWord.setTypeface(typefaceQuestionDeterminer(), txtStyleQuestionDeterminer());
        holder.wrongWord.setTypeface(typefaceOptionsDeterminer(), txtStyleOptionsDeterminer());
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
        return ResourcesCompat.getFont(wrongContext, perFontFace());
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
        fontTypePreferences = wrongContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getBoolean(perTxtBolderKey, false);
    }
    private int perFontVal(){
        fontTypePreferences = wrongContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getInt(perListPositionKey, 1);
    }

    private Typeface engTypeFace(){
        return ResourcesCompat.getFont(wrongContext, engFontFace());
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
        fontTypePreferences = wrongContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getBoolean(engTxtBolderKey, false);
    }
    private int engFontVal(){
        fontTypePreferences = wrongContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getInt(engListPositionKey, 1);
    }

}