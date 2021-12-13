package com.example.a4000essentialwordsbook1.Settings.SettingsDialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookTwo;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Settings.SettingListanerInterface.AddNoteInterFace;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.FontTypeFiles.GlobalFonts;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;



public class AddNoteDialogFragment extends DialogFragment implements View.OnClickListener{

    private ImageView imageTv;
    private String imgVal;
    private String wordVal, phoneticVal, noteVal;
    private TextFieldBoxes addNoteTxtField;
    private TextView wordTxt, phtcTxt;
    private TextView saveBtn, rejectBtn;
    private ExtendedEditText addNoteEditTextView;
    private int[] dbInfoLists;
    private String[] editTxtValue;
    private AddNoteInterFace addNoteInterFace;
    private final int tmLineMines = 500;

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

    public static AddNoteDialogFragment newInstance(String[] editTxtValue, int[] dbInfoList){
        AddNoteDialogFragment addNoteDialogFragment = new AddNoteDialogFragment();
        Bundle addNoteBundle = new Bundle();
        addNoteBundle.putIntArray("dbInfoList", dbInfoList);
        addNoteBundle.putStringArray("editTxtValue", editTxtValue);
        addNoteDialogFragment.setArguments(addNoteBundle);
        return addNoteDialogFragment;
    }



    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbInfoLists = requireArguments().getIntArray("dbInfoList");
        editTxtValue = requireArguments().getStringArray("editTxtValue");
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addNoteFindViewsById(view);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Activity activity = (Activity) context;
        addNoteInterFace = (AddNoteInterFace) activity;
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.add_note_add_note_dialog_fragment_save_btn):
                addedNoteFunctions();
                dismiss();
                break;
            case (R.id.add_note_dialog_fragment_reject_btn):
                dismiss();
                break;
        }
    }

    private void addedNoteFunctions(){
        ExecutorService thread = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        thread.execute(() ->{
            savingAddedNoteToDatabase(dbNum(), unitNum(), wordId());
            handler.post(() ->{
               addNoteInterFace.addNote(addedNoteVal());
            });
        });
    }
    private void savingAddedNoteToDatabase(int dbNum, int unitNum, int wordId){

        SQLiteDatabase db = wordListDatabase(dbNum).getWritableDatabase();
        ContentValues noteContentValue = new ContentValues();

        noteContentValue.put(DB_NOTES.EXTRA_NOTE, addedNoteVal());

        db.update(DB_NOTES.NEUTRAL_WORD_TABLE + unitNum,
                noteContentValue, DB_NOTES.WORD_ID + " = ? " , new String[]{Integer.toString(wordId)});
        db.close();
    }
    private SQLiteOpenHelper wordListDatabase(int databaseNum){
        if (databaseNum == 1){
            return new WordDatabaseBookOne(requireActivity());
        }else if (databaseNum == 2){
            return new WordDatabaseBookTwo(requireActivity());
        }else if (databaseNum == 3){
            return new WordDatabaseBookThree(requireActivity());
        }else if (databaseNum == 4){
            return new WordDatabaseBookFour(requireActivity());
        }else if (databaseNum == 5){
            return new WordDatabaseBookFive(requireActivity());
        }else {
            return new WordDatabaseBookSix(requireActivity());
        }
    }

    private String addedNoteVal(){
        return addNoteEditTextView.getText().toString();
    }

    private int dbNum(){
        return dbInfoLists[0];
    }
    private int unitNum(){
        return dbInfoLists[1];
    }
    private int wordId(){
        return dbInfoLists[2];
    }


    private void setImagesResource(){
        final String appPath = requireActivity().getApplicationInfo().dataDir;

        final File imgMainPath = new File("Image Files");
        final File imageDir = new File(Environment.DIRECTORY_DOWNLOADS, File.separator + "4000 Essential Words");


        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + dbInfoLists[0]);
        final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + dbInfoLists[1]);

        final File imgName = new File(wordUnitImgBookPath, File.separator + "." + new File(imgVal).getName());
        final File imgFile = new File(Environment.getExternalStoragePublicDirectory(imageDir.toString()), imgName.toString());


        if (imgFile.exists()){
            Drawable imgDrawable = Drawable.createFromPath(imgFile.toString());
            Glide.with(requireActivity())
                    .load(imgDrawable)
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(imageTv);
        }else {
            Glide.with(requireActivity())
                    .load(imgVal)
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(imageTv);
        }
    }

    private void addNoteFindViewsById(View view){

        imageTv = view.findViewById(R.id.dialog_fragment_add_note_roundImageView);

        wordTxt = view.findViewById(R.id.dialog_fragment_add_note_word_text_view);
        phtcTxt = view.findViewById(R.id.dialog_fragment_add_note_phonetic_text_view);

        addNoteTxtField = view.findViewById(R.id.dialog_fragment_add_note_text_field_box);
        addNoteEditTextView = view.findViewById(R.id.dialog_fragment_add_note_word_extend_edit_text);

        saveBtn = view.findViewById(R.id.add_note_add_note_dialog_fragment_save_btn);
        rejectBtn = view.findViewById(R.id.add_note_dialog_fragment_reject_btn);
        viewsPreInitializer();
        addNoteOnThisClickListener();
    }
    private void viewsPreInitializer(){
        ExecutorService thread = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        thread.execute(() ->{
            extraNoteFromDb(wordId());

            handler.post(() ->{
                addNoteEditTextView.setText(noteVal);
                wordTxt.setText(wordVal);
                phtcTxt.setText(phoneticVal);
                textViewAttributeSetter();
                setImagesResource();
            });
        });
    }

    private void textViewAttributeSetter(){
        englishTextViewTypeFaceSetter();
        persianTextViewTypeFaceSetter();
    }
    private void englishTextViewTypeFaceSetter(){
        wordTxt.setTypeface(engTypeFace(), engTextStyle());
        phtcTxt.setTypeface(engTypeFace(), engTextStyle());
    }
    private void persianTextViewTypeFaceSetter(){
        addNoteEditTextView.setTypeface(perTypeFace(), perTextStyle());
    }


    private Typeface perTypeFace(){
        return ResourcesCompat.getFont(requireActivity(), perFontFace());
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
        fontTypePreferences = requireActivity().getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getBoolean(perTxtBolderKey, false);
    }
    private int perFontVal(){
        fontTypePreferences = requireActivity().getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getInt(perListPositionKey, 1);
    }

    private Typeface engTypeFace(){
        return ResourcesCompat.getFont(requireActivity(), engFontFace());
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
        fontTypePreferences = requireActivity().getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getBoolean(engTxtBolderKey, false);
    }
    private int engFontVal(){
        fontTypePreferences = requireActivity().getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getInt(engListPositionKey, 1);
    }



    @SuppressLint("Range")
    private void extraNoteFromDb(int newWordId){


        SQLiteDatabase database = wordListDatabase(dbNum()).getReadableDatabase();
        Cursor cursor = database.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitNum(),
                new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.BOOK_NUMBER,
                        DB_NOTES.UNIT_NUMBER, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                        DB_NOTES.EXTRA_NOTE},
                DB_NOTES.WORD_ID + " = ? ", new String[]{Integer.toString(newWordId)}, null, null, null);
        if (cursor != null && cursor.getCount() != 0){

            while (cursor.moveToNext()){


                int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                imgVal = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                noteVal = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXTRA_NOTE));
                wordVal = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));
                phoneticVal = cursor.getString(cursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));

            }
            database.close();
            cursor.close();
        }
    }
    private void addNoteOnThisClickListener(){
        saveBtn.setOnClickListener(this);
        rejectBtn.setOnClickListener(this);
    }
}
