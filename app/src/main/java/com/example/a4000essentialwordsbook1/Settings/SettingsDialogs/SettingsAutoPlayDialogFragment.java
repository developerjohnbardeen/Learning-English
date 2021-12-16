package com.example.a4000essentialwordsbook1.Settings.SettingsDialogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.DialogFragment;

import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;
import com.google.android.material.card.MaterialCardView;

public class SettingsAutoPlayDialogFragment extends DialogFragment implements View.OnClickListener {
    private AppCompatCheckBox plyAllCheckBox, plyWrdCheckBox, plyDefCheckBox, plyExmplCheckBox;
    private AppCompatCheckBox settingsCheckBox;

    private final boolean trueFlag = true;
    private final boolean falseFlag = false;
    private MaterialCardView confirmationTxtView, rejectTxtView;

    private SharedPreferences plyAudioPreferences;
    private final String plyAudioPreferenceName = SettingsPreferencesNotes.SETTINGS_AUTO_PLAY_AUDIO_PREFERENCES;
    private final String plyAllKey = SettingsPreferencesNotes.AUTO_PLAY_ALL_KEY;
    private final String wordPlyKey = SettingsPreferencesNotes.WORD_PLAY_KEY;
    private final String definitionPlyKey = SettingsPreferencesNotes.DEFINITION_PLAY_KEY;
    private final String examplePlyKey = SettingsPreferencesNotes.EXAMPLE_PLAY_KEY;



    public static SettingsAutoPlayDialogFragment newInstance(){
        SettingsAutoPlayDialogFragment fragment = new SettingsAutoPlayDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_settings_auto_play , container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        findDialogViewsById(view);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.auto_play_all_audios_dialog_check_box):
                checkAllBoxValueSetter();
            break;
            case (R.id.play_word_audios_check_box):
                wordCheckBoxValueSetter();
                break;
            case (R.id.auto_play_definition_settings_check_box):
                defCheckBxValueSetter();
                break;
            case (R.id.auto_play_example_settings_check_box):
                exmplCheckBoxValueSetter();
                break;
            case (R.id.auto_play_confirm_settings_button):
                allPreferencesValueSetter();
                dismiss();
                break;
            case (R.id.auto_play_settings_reject_button):
                dismiss();
                break;
        }
    }

    private void checkAllBoxValueSetter(){
        final boolean plyAllFlag = plyAllCheckBox.isChecked();
        if (isAllChecked()){
            setCheckBoxAllTrue();
        }else {
            setCheckBoxAllFalse();
        }
    }
    private void setCheckBoxAllTrue(){
        setWrdDefExmplCheckBoxTrue();
    }
    private void setWrdDefExmplCheckBoxTrue(){
        plyAllCheckBox.setChecked(trueFlag);
        plyWrdCheckBox.setChecked(trueFlag);
        plyDefCheckBox.setChecked(trueFlag);
        plyExmplCheckBox.setChecked(trueFlag);
    }


    private void setCheckBoxAllFalse(){
        setWrdDefExmplChechBoxFalse();
    }
    private void setWrdDefExmplChechBoxFalse(){
        plyAllCheckBox.setChecked(falseFlag);
        plyWrdCheckBox.setChecked(falseFlag);
        plyDefCheckBox.setChecked(falseFlag);
        plyExmplCheckBox.setChecked(falseFlag);
    }


    private void wordCheckBoxValueSetter(){
        final boolean plyWrdFlag = plyWrdCheckBox.isChecked();
        if (plyWrdFlag){
            setCheckBoxWordTrue();
        }else {
            setCheckBoxWordFalse();
        }
    }
    private void setCheckBoxWordTrue(){
        plyWrdCheckBox.setChecked(true);
        final boolean defFlag = plyDefCheckBox.isChecked();
        final boolean exmplFlag = plyExmplCheckBox.isChecked();
        if (defFlag && exmplFlag){
            plyAllCheckBox.setChecked(true);
        }
    }
    private void setCheckBoxWordFalse(){
        plyWrdCheckBox.setChecked(false);
        plyAllCheckBox.setChecked(false);
    }


    private void defCheckBxValueSetter(){
        final boolean plyDefFlag = plyDefCheckBox.isChecked();
        if (plyDefFlag){
            setDefCheckBoxTrue();
        }else {
            setDefCheckBoxFalse();
        }
    }
    private void setDefCheckBoxTrue(){
        plyDefCheckBox.setChecked(trueFlag);
        final boolean wordFlag = plyWrdCheckBox.isChecked();
        final boolean exmplFlag = plyExmplCheckBox.isChecked();
        if (wordFlag && exmplFlag){
            plyAllCheckBox.setChecked(trueFlag);
        }
    }
    private void setDefCheckBoxFalse(){
        plyDefCheckBox.setChecked(falseFlag);
        plyAllCheckBox.setChecked(falseFlag);
    }


    private void exmplCheckBoxValueSetter(){
        final boolean plyExmplFlag = plyExmplCheckBox.isChecked();
        if (plyExmplFlag){
            setExmplCheckBoxTrue();
        }else {
            setExmplCheckBoxFalse();
        }
    }
    private void setExmplCheckBoxTrue(){
        plyExmplCheckBox.setChecked(trueFlag);
        final boolean wordFlag = plyWrdCheckBox.isChecked();
        final boolean defFlag = plyDefCheckBox.isChecked();
        if (wordFlag && defFlag) {
            plyAllCheckBox.setChecked(trueFlag);
        }
    }
    private void setExmplCheckBoxFalse(){
        plyExmplCheckBox.setChecked(falseFlag);
        plyAllCheckBox.setChecked(falseFlag);
    }






    private void findDialogViewsById(View view){
        plyAllCheckBox = view.findViewById(R.id.auto_play_all_audios_dialog_check_box);
        plyWrdCheckBox = view.findViewById(R.id.play_word_audios_check_box);
        plyDefCheckBox = view.findViewById(R.id.auto_play_definition_settings_check_box);
        plyExmplCheckBox = view.findViewById(R.id.auto_play_example_settings_check_box);
        confirmationTxtView = view.findViewById(R.id.auto_play_confirm_settings_button);
        rejectTxtView = view.findViewById(R.id.auto_play_settings_reject_button);
        settingsCheckBox = requireActivity().findViewById(R.id.settings_auto_play_check_box);
        initializeCheckBoxesValue();
        thisDialogClickLister();
    }
    private void initializeCheckBoxesValue(){
        if (isPlyAllFlag()) {
            allCheckBoxSetToTrue();
        }else {
            plyWrdCheckBox.setChecked(isPlyWrdFlag());
            plyDefCheckBox.setChecked(isPlyDefFlag());
            plyExmplCheckBox.setChecked(isPlyExmplFlag());
        }
    }
    private void allCheckBoxSetToTrue(){
        plyAllCheckBox.setChecked(trueFlag);
        plyWrdCheckBox.setChecked(trueFlag);
        plyDefCheckBox.setChecked(trueFlag);
        plyExmplCheckBox.setChecked(trueFlag);
    }

    private boolean isPlyAllFlag(){
        plyAudioPreferences = requireActivity().getSharedPreferences(plyAudioPreferenceName, Context.MODE_PRIVATE);
        return plyAudioPreferences.getBoolean(plyAllKey, false);
    }
    private boolean isPlyWrdFlag(){
        plyAudioPreferences = requireActivity().getSharedPreferences(plyAudioPreferenceName, Context.MODE_PRIVATE);
        return plyAudioPreferences.getBoolean(wordPlyKey, trueFlag);
    }
    private boolean isPlyDefFlag(){
        plyAudioPreferences = requireActivity().getSharedPreferences(plyAudioPreferenceName, Context.MODE_PRIVATE);
        return plyAudioPreferences.getBoolean(definitionPlyKey, false);
    }
    private boolean isPlyExmplFlag(){
        plyAudioPreferences = requireActivity().getSharedPreferences(plyAudioPreferenceName, Context.MODE_PRIVATE);
        return plyAudioPreferences.getBoolean(examplePlyKey, false);
    }

    private void thisDialogClickLister(){
        plyAllCheckBox.setOnClickListener(this);
        plyWrdCheckBox.setOnClickListener(this);
        plyDefCheckBox.setOnClickListener(this);
        plyExmplCheckBox.setOnClickListener(this);
        confirmationTxtView.setOnClickListener(this);
        rejectTxtView.setOnClickListener(this); }

    private void allPreferencesValueSetter(){
        plyAudioPreferences = requireActivity().getSharedPreferences(plyAudioPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor plyAudioEdit = plyAudioPreferences.edit();
        plyAudioEdit.putBoolean(plyAllKey, isAllChecked());
        plyAudioEdit.putBoolean(wordPlyKey, isWordChecked());
        plyAudioEdit.putBoolean(definitionPlyKey, isDefChecked());
        plyAudioEdit.putBoolean(examplePlyKey, isExmplChecked());
        plyAudioEdit.apply();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        autoPlayCheckBoxValueSetter();
    }
    private void autoPlayCheckBoxValueSetter(){
        settingsCheckBox.setChecked(isAnyDisplayed());
    }
    private boolean isAnyDisplayed(){
        plyAudioPreferences = requireActivity().getSharedPreferences(plyAudioPreferenceName, Context.MODE_PRIVATE);
        final boolean allFlag = plyAudioPreferences.getBoolean(plyAllKey, falseFlag);
        final boolean wrdFlag = plyAudioPreferences.getBoolean(wordPlyKey, trueFlag);
        final boolean defFlag = plyAudioPreferences.getBoolean(definitionPlyKey, falseFlag);
        final boolean exmplFlag = plyAudioPreferences.getBoolean(examplePlyKey, falseFlag);

        return allFlag || wrdFlag || defFlag || exmplFlag;
    }

    private boolean isAllChecked(){return plyAllCheckBox.isChecked();}
    private boolean isWordChecked(){return plyWrdCheckBox.isChecked();}
    private boolean isDefChecked(){return plyDefCheckBox.isChecked();}
    private boolean isExmplChecked(){return plyExmplCheckBox.isChecked();}

}
