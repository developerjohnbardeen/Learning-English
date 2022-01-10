package com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordSlideCardViewActivity;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.FontTypeFiles.GlobalFonts;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;

import java.io.File;
import java.util.ArrayList;




public class WordListRecyclerView extends RecyclerView.Adapter<WordListRecyclerView.WordViewHolder>{
    private final Context wordContext;
    private final LayoutInflater inflater;
    private final ArrayList<WordModel> wordList;
    private final int unitNum, dbNum;
    private final String strDbNumber = ExtraNotes.DB_NUMBER;
    private final String strUnitNumber = ExtraNotes.UNIT_NUMBER;
    private final String strWordId = ExtraNotes.WORD_ID;

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

        final String appPath = wordContext.getApplicationInfo().dataDir;


        final File imageDir = new File(Environment.DIRECTORY_DOWNLOADS, File.separator + "4000 Essential Words");

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + listModel.getBookNum());
        final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + listModel.getUnitNum());

        final File imgName = new File(wordUnitImgBookPath, File.separator + "." + new File(listModel.getImgUri()).getName());
        final File imgFile = new File(Environment.getExternalStoragePublicDirectory(imageDir.toString()), imgName.toString());


        if (imgFile.exists()){
            Drawable imgDrawable = Drawable.createFromPath(imgFile.toString());
            Glide.with(wordContext)
                    .load(imgDrawable)
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.imageTv);
        }else {
            Glide.with(wordContext)
                    .load(listModel.getImgUri())
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(holder.imageTv);
        }

        holder.wordTitle.setText(listModel.getWord());
        holder.phonetic.setText(listModel.getPhonetic());
        holder.translateWord.setText(listModel.getTranslateWord());


        holder.wordCardView.setOnClickListener(v -> {
            Intent wordDetailedIntent = new Intent(wordContext, WordSlideCardViewActivity.class);
            wordDetailedIntent.putExtra(strDbNumber, dbNum);
            wordDetailedIntent.putExtra(strUnitNumber, unitNum);
            wordDetailedIntent.putExtra(strWordId, holder.getLayoutPosition());
            wordContext.startActivity(wordDetailedIntent);
        });
        textViewAttributeSetter(holder);
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }


    public static class WordViewHolder extends RecyclerView.ViewHolder {

        ImageView imageTv;
        TextView wordTitle;
        TextView phonetic;
        TextView translateWord;
        CardView wordCardView;
        Context context;

        public WordViewHolder(@NonNull View itemView, Context viewContext) {
            super(itemView);
            this.context = viewContext;
            imageTv = itemView.findViewById(R.id.word_img);
            wordTitle = itemView.findViewById(R.id.word_txt);
            phonetic = itemView.findViewById(R.id.phonetic_word_txt);
            translateWord = itemView.findViewById(R.id.translate_word_txt);
            wordCardView = itemView.findViewById(R.id.card_view_word);

        }
    }



    private void textViewAttributeSetter(WordViewHolder holder){
        englishTextViewTypeFaceSetter(holder);
        persianTextViewTypeFaceSetter(holder);
    }
    private void englishTextViewTypeFaceSetter(WordViewHolder holder){
        holder.wordTitle.setTypeface(engTypeFace(), engTextStyle());
        holder.phonetic.setTypeface(engTypeFace(), engTextStyle());
    }
    private void persianTextViewTypeFaceSetter(WordViewHolder holder){
        holder.translateWord.setTypeface(perTypeFace(), perTextStyle());
    }


    private Typeface perTypeFace(){
        return ResourcesCompat.getFont(wordContext, perFontFace());
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
        fontTypePreferences = wordContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getBoolean(perTxtBolderKey, false);
    }
    private int perFontVal(){
        fontTypePreferences = wordContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getInt(perListPositionKey, 1);
    }

    private Typeface engTypeFace(){
        return ResourcesCompat.getFont(wordContext, engFontFace());
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
        fontTypePreferences = wordContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getBoolean(engTxtBolderKey, false);
    }
    private int engFontVal(){
        fontTypePreferences = wordContext.getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getInt(engListPositionKey, 1);
    }

}