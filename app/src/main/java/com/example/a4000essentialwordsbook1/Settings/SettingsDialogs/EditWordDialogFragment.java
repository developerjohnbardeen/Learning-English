package com.example.a4000essentialwordsbook1.Settings.SettingsDialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookTwo;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordDetailedInterfaces.SaveEditsInterface;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.FontTypeFiles.GlobalFonts;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class EditWordDialogFragment extends DialogFragment implements View.OnClickListener{
    private TextFieldBoxes wordTxtField, defTxtFieldBox, exmplTxtFieldBox;
    private ImageView imageTv;
    private String imgVal;
    private TextView wordTxt, phtcTxt;
    private TextView saveBtn, rejectBtn;
    private ExtendedEditText wrdEditTxt, defEditTxt, exmplEditText;
    private int[] dbInfoLists;
    private String[] editTxtValue;
    private SaveEditsInterface savedEditsInterface;


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


    public static EditWordDialogFragment newInstance(String[] editTxtValue,String imgVal, int[] dbInfoList){
        EditWordDialogFragment fragment = new EditWordDialogFragment();
        Bundle wordBundle = new Bundle();
        wordBundle.putIntArray("dbInfoList", dbInfoList);
        wordBundle.putStringArray("editTxtValue", editTxtValue);
        wordBundle.putString("imgVal", imgVal);
        fragment.setArguments(wordBundle);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        editTxtValue = getArguments().getStringArray("editTxtValue");
        dbInfoLists = getArguments().getIntArray("dbInfoList");
        imgVal = getArguments().getString("imgVal");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }



    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_edit_word, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findDialogFragmentView(view);
        editTextsValuesSetter();
    }


    private void editTextsValuesSetter(){
        wrdEditTxt.setText(wrdTranslation());
        defEditTxt.setText(defTranslation());
        exmplEditText.setText(exmplTranslation());
        wordTxt.setText(word());
        phtcTxt.setText(phonetic());
    }
    private String wrdTranslation(){
        return editTxtValue[0];
    }
    private String defTranslation(){
        return editTxtValue[1];
    }
    private String exmplTranslation(){
        return editTxtValue[2];
    }
    private String word(){return editTxtValue[3];}
    private String phonetic(){return editTxtValue[4];}




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.dialog_fragment_save_btn):
                translationEditor();
                dismiss();
                break;
            case (R.id.dialog_fragment_reject_btn):
                dismiss();
                break;
        }
    }

    private void translationEditor(){
        ExecutorService editThread = Executors.newSingleThreadExecutor();
        Handler editHandler = new Handler(Looper.getMainLooper());

        editThread.execute(() ->{
            editingWordAndDefAndExmple(dbNum(), unitNum(), wordId());
            editHandler.post(() ->
                    savedEditsInterface.editsSaved(editWord(), editDefinition(), editExample()));
        });
    }

    private void editingWordAndDefAndExmple(int dbId, int unitId, int wordId){

        SQLiteDatabase db = wordListDatabase(dbId).getWritableDatabase();
        ContentValues markedFlagValue = new ContentValues();

        markedFlagValue.put(DB_NOTES.TRANSLATE_WORD, editWord());
        markedFlagValue.put(DB_NOTES.DEFINITION_TRANSLATE_WORD, editDefinition());
        markedFlagValue.put(DB_NOTES.EXAMPLE_TRANSLATE_WORD, editExample());

        db.update(DB_NOTES.NEUTRAL_WORD_TABLE + unitId,
                markedFlagValue, DB_NOTES.WORD_ID + " = ? ", new String[]{Integer.toString(wordId)});
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
    private String editWord() {
        return wrdEditTxt.getText().toString();
    }
    private String editDefinition() {
        return defEditTxt.getText().toString();
    }
    private String editExample() {
        return exmplEditText.getText().toString();
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


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            savedEditsInterface = (SaveEditsInterface) activity;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void findDialogFragmentView(View view){

        imageTv = view.findViewById(R.id.dialog_fragment_roundImageView);
        //imageTv.setImageResource(imgVal);
        setImageResources();

        wordTxt = view.findViewById(R.id.dialog_fragment_word_text_view);
        phtcTxt = view.findViewById(R.id.dialog_fragment_phonetic_text_view);

        wordTxtField = view.findViewById(R.id.dialog_fragment_word_text_field_box);
        defTxtFieldBox = view.findViewById(R.id.dialog_fragment_definition_text_field_box);
        exmplTxtFieldBox = view.findViewById(R.id.dialog_fragment_example_text_field_box);

        saveBtn = view.findViewById(R.id.dialog_fragment_save_btn);
        rejectBtn = view.findViewById(R.id.dialog_fragment_reject_btn);

        wrdEditTxt = view.findViewById(R.id.dialog_fragment_word_extend_edit_text);
        defEditTxt = view.findViewById(R.id.dialog_fragment_definition_extend_edit_text);
        exmplEditText = view.findViewById(R.id.dialog_fragment_example_extend_edit_text);


        textViewAttributeSetter();
        thisOnClickListener();

/*        wordTxtField.setPrimaryColor(R.color.textColor);
        defTxtFieldBox.setPrimaryColor(R.color.textColor);
        exmplTxtFieldBox.setPrimaryColor(R.color.textColor);*/

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
        wrdEditTxt.setTypeface(perTypeFace(), perTextStyle());
        defEditTxt.setTypeface(perTypeFace(), perTextStyle());
        exmplEditText.setTypeface(perTypeFace(), perTextStyle());
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


    private void setImageResources(){
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
    private void thisOnClickListener(){
        saveBtn.setOnClickListener(this);
        rejectBtn.setOnClickListener(this);
    }
}
