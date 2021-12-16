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

public class PlayWordsAudioStoryFragment extends DialogFragment implements View.OnClickListener {
    private AppCompatCheckBox plyAllWordCheckBox, plyWordCheckBox, plyDefCheckBox, plyExmplCheckBox;
    private AppCompatCheckBox anyPlayedCheckBox;
    private MaterialCardView confirmBtn, rejectBtn;

    private SharedPreferences playWordAudioPreferences;
    private final String plyWordAudioStoryPreferencesName = SettingsPreferencesNotes.PLAY_WORDS_AUDIO_STORY_PREFERENCES_NAME;
    private final String plyAllAudioStoryKey = SettingsPreferencesNotes.PLY_ALL_WORD_AUDIO_STORY_KEY;
    private final String plyWordAudioStoryKey = SettingsPreferencesNotes.PLY_WORD_AUDIO_STORY_KEY;
    private final String plyDefAudioStoryKey = SettingsPreferencesNotes.PLY_DEFINITION_AUDIO_STORY_KEY;
    private final String plyExmplAudioStoryKey = SettingsPreferencesNotes.PLY_EXAMPLE_AUDIO_STORY_KEY;

    public static PlayWordsAudioStoryFragment newInstance() {
        PlayWordsAudioStoryFragment fragment = new PlayWordsAudioStoryFragment();
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
        return inflater.inflate(R.layout.dialog_play_words_audio_story, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogFindViewsById(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.play_all_audio_story_check_box):
                plyAllWordCheckBoxFunctions();
                break;
            case (R.id.play_word_audio_story_check_box):
                plyWordCheckBoxFunctions();
                break;
            case (R.id.play_definition_audio_story_check_box):
                plyDefCheckBoxFunctions();
                break;
            case (R.id.play_example_audio_story_check_box):
                plyExmplCheckBoxFunctions();
                break;
            case (R.id.play_word_auto_story_button):
                anyPlayedCheckBox.setChecked(isAnyChecked());
                settingsPreferencesValueSetter();
                dismiss();
                break;
            case (R.id.reject_play_word_auto_story_button):
                dismiss();
                break;
        }
    }

    private void plyAllWordCheckBoxFunctions(){
        if (isAllChecked()){
            plyAllWordCheckBox.setChecked(true);
            plyWordCheckBox.setChecked(true);
            plyDefCheckBox.setChecked(true);
            plyExmplCheckBox.setChecked(true);
        }else {
            plyAllWordCheckBox.setChecked(false);
            plyWordCheckBox.setChecked(false);
            plyDefCheckBox.setChecked(false);
            plyExmplCheckBox.setChecked(false);
        }
    }


    private void plyWordCheckBoxFunctions(){
        if (isWordChecked()){
            setPlyWordCheckBoxValueTrue();
        }else {
            setPlyWordCheckBoxValueFalse();
        }
    }
    private void setPlyWordCheckBoxValueTrue(){
        if (isDefChecked() && isExmplChecked()){
            plyAllWordCheckBox.setChecked(true);
        }
    }
    private void setPlyWordCheckBoxValueFalse(){
        plyAllWordCheckBox.setChecked(false);

    }



    private void plyDefCheckBoxFunctions(){
        if (isDefChecked()){
            setPlyDefCheckBoxValueTure();
        }else {
            setPlyDefCheckBoxValueFalse();
        }
    }
    private void setPlyDefCheckBoxValueTure(){
        if (isWordChecked() && isExmplChecked()){
            plyAllWordCheckBox.setChecked(true);
        }
    }
    private void setPlyDefCheckBoxValueFalse(){
        plyAllWordCheckBox.setChecked(false);
    }


    private void plyExmplCheckBoxFunctions(){
        if (isExmplChecked()){
            setPlyExmplCheckBoxValueTrue();
        }else {
            setPlyExmplCheckBoxValueFalse();
        }

    }
    private void setPlyExmplCheckBoxValueTrue(){
        if (isWordChecked() && isDefChecked()){
            plyAllWordCheckBox.setChecked(true);
        }
    }
    private void setPlyExmplCheckBoxValueFalse(){
        plyAllWordCheckBox.setChecked(false);
    }



    private void settingsPreferencesValueSetter(){
        playWordAudioPreferences = requireActivity().getSharedPreferences(plyWordAudioStoryPreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor plyAudioEdit = playWordAudioPreferences.edit();
        plyAudioEdit.putBoolean(plyAllAudioStoryKey, isAllChecked());
        plyAudioEdit.putBoolean(plyWordAudioStoryKey, isWordChecked());
        plyAudioEdit.putBoolean(plyDefAudioStoryKey, isDefChecked());
        plyAudioEdit.putBoolean(plyExmplAudioStoryKey, isExmplChecked());
        plyAudioEdit.apply();
    }


    private void dialogFindViewsById(View view){
        plyAllWordCheckBox = view.findViewById(R.id.play_all_audio_story_check_box);
        plyWordCheckBox = view.findViewById(R.id.play_word_audio_story_check_box);
        plyDefCheckBox = view.findViewById(R.id.play_definition_audio_story_check_box);
        plyExmplCheckBox = view.findViewById(R.id.play_example_audio_story_check_box);
        confirmBtn = view.findViewById(R.id.play_word_auto_story_button);
        rejectBtn = view.findViewById(R.id.reject_play_word_auto_story_button);
        anyPlayedCheckBox = requireActivity().findViewById(R.id.settings_play_story_bold_words_check_box);

        checkBoxInitializer();
        thisDialogOnClickListener();
    }
    private void thisDialogOnClickListener(){
        plyAllWordCheckBox.setOnClickListener(this);
        plyWordCheckBox.setOnClickListener(this);
        plyDefCheckBox.setOnClickListener(this);
        plyExmplCheckBox.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        rejectBtn.setOnClickListener(this);
    }

    private void checkBoxInitializer(){
        plyAllWordCheckBox.setChecked(isAllInitiliazed());
        plyWordCheckBox.setChecked(isWordInitialized());
        plyDefCheckBox.setChecked(isDefInitialized());
        plyExmplCheckBox.setChecked(isExmplInitialized());
    }


    private boolean isAllChecked(){return plyAllWordCheckBox.isChecked();}
    private boolean isWordChecked(){return plyWordCheckBox.isChecked();}
    private boolean isDefChecked(){return plyDefCheckBox.isChecked();}
    private boolean isExmplChecked(){return plyExmplCheckBox.isChecked();}


    private boolean isAllInitiliazed(){
        playWordAudioPreferences = requireActivity().getSharedPreferences(plyWordAudioStoryPreferencesName, Context.MODE_PRIVATE);
        return playWordAudioPreferences.getBoolean(plyAllAudioStoryKey, false);
    }
    private boolean isWordInitialized(){
    playWordAudioPreferences = requireActivity().getSharedPreferences(plyWordAudioStoryPreferencesName, Context.MODE_PRIVATE);
    return playWordAudioPreferences.getBoolean(plyWordAudioStoryKey, true);
    }
    private boolean isDefInitialized(){
    playWordAudioPreferences = requireActivity().getSharedPreferences(plyWordAudioStoryPreferencesName, Context.MODE_PRIVATE);
    return playWordAudioPreferences.getBoolean(plyDefAudioStoryKey,false);
    }
    private boolean isExmplInitialized(){
    playWordAudioPreferences = requireActivity().getSharedPreferences(plyWordAudioStoryPreferencesName, Context.MODE_PRIVATE);
    return playWordAudioPreferences.getBoolean(plyExmplAudioStoryKey,false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private boolean isAnyChecked(){
        return plyWordCheckBox.isChecked() || plyDefCheckBox.isChecked() || plyExmplCheckBox.isChecked();
    }
}
