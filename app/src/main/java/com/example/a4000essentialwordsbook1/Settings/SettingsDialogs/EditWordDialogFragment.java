package com.example.a4000essentialwordsbook1.Settings.SettingsDialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookTwo;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordDetailedInterfaces.SaveEditsInterface;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class EditWordDialogFragment extends DialogFragment implements View.OnClickListener{
    private TextFieldBoxes wordTxtField, defTxtFieldBox, exmplTxtFieldBox;
    private TextView wordTxt, phtcTxt;
    private TextView saveBtn, rejectBtn;
    private ExtendedEditText wrdEditTxt, defEditTxt, exmplEditText;
    private int[] dbInfoLists;
    private String[] editTxtValue;
    private SaveEditsInterface savedEditsInterface;


    public static EditWordDialogFragment newInstance(String[] editTxtValue, int[] dbInfoList){
        EditWordDialogFragment fragment = new EditWordDialogFragment();
        Bundle wordBundle = new Bundle();
        wordBundle.putIntArray("dbInfoList", dbInfoList);
        wordBundle.putStringArray("editTxtValue", editTxtValue);
        fragment.setArguments(wordBundle);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        editTxtValue = getArguments().getStringArray("editTxtValue");
        dbInfoLists = getArguments().getIntArray("dbInfoList");
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
        wordTxtField = view.findViewById(R.id.dialog_fragment_word_text_field_box);
        defTxtFieldBox = view.findViewById(R.id.dialog_fragment_definition_text_field_box);
        exmplTxtFieldBox = view.findViewById(R.id.dialog_fragment_example_text_field_box);
        wordTxt = view.findViewById(R.id.dialog_fragment_word_text_view);
        phtcTxt = view.findViewById(R.id.dialog_fragment_phonetic_text_view);
        saveBtn = view.findViewById(R.id.dialog_fragment_save_btn);
        rejectBtn = view.findViewById(R.id.dialog_fragment_reject_btn);

        wrdEditTxt = view.findViewById(R.id.dialog_fragment_word_extend_edit_text);
        defEditTxt = view.findViewById(R.id.dialog_fragment_definition_extend_edit_text);
        exmplEditText = view.findViewById(R.id.dialog_fragment_example_extend_edit_text);
        thisOnClickListener();

        wordTxtField.setPrimaryColor(R.color.black);

    }
    private void thisOnClickListener(){
        saveBtn.setOnClickListener(this);
        rejectBtn.setOnClickListener(this);
    }
}
