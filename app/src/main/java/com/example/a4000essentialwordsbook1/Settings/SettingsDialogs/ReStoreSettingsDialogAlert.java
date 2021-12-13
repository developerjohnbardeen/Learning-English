package com.example.a4000essentialwordsbook1.Settings.SettingsDialogs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Settings.SettingListanerInterface.ReSetSettingsInterface;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReStoreSettingsDialogAlert extends DialogFragment implements View.OnClickListener {
    private TextView messageTxtView, confirmBtn, rejectBtn;

    private ReSetSettingsInterface restInterface;

    public static ReStoreSettingsDialogAlert newInstance(){
        return new ReStoreSettingsDialogAlert();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_restore_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsById(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.restore_settings_confirmation_settings_button):
                restoreAllSettings();
                dismiss();
                break;
            case (R.id.restore_settings_reject_button):
                dismiss();
                break;
        }
    }


    private void restoreAllSettings(){
        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->{
            clearAutoNighModePreferences();
            clearEntrancePlayPreferences();
            clearDisplayTranslationPreferences();
            cleartextSizePreferences();
            clearStoryTextSizePreferences();
            clearTextFontTypePreferences();
            clearStoryTextFontTypePreferences();
            clearQuizTimerPreferences();
            clearStoryWordPlayAudiosPreferences();
        });
    }

    private void clearAutoNighModePreferences(){
        final String autoNighPreferencesName = SettingsPreferencesNotes.SETTINGS_AUTO_NIGH_PREFERENCES;
        SharedPreferences autoNighModePreference = requireActivity().getSharedPreferences(autoNighPreferencesName, Context.MODE_PRIVATE);
        autoNighModePreference.edit().clear().apply();
    }
    private void clearEntrancePlayPreferences(){
        final String plyAudioPreferenceName = SettingsPreferencesNotes.SETTINGS_AUTO_PLAY_AUDIO_PREFERENCES;
        SharedPreferences plyAudioPreferences = requireActivity().getSharedPreferences(plyAudioPreferenceName, Context.MODE_PRIVATE);
        plyAudioPreferences.edit().clear().apply();
    }
    private void clearDisplayTranslationPreferences(){
        final String displayTranslationPreferences = SettingsPreferencesNotes.SETTINGS_DISPLAY_TRANSLATION_PREFERENCES;
        SharedPreferences displayPreference = requireActivity().getSharedPreferences(displayTranslationPreferences, Context.MODE_PRIVATE);
        displayPreference.edit().clear().apply();
    }
    private void cleartextSizePreferences(){
        final String textViewSizePreferencesName = SettingsPreferencesNotes.SETTINGS_TEXT_VIEW_SIZE_PREFERENCES;
        SharedPreferences textSizePreferences = requireActivity().getSharedPreferences(textViewSizePreferencesName, Context.MODE_PRIVATE);
        textSizePreferences.edit().clear().apply();
    }
    private void clearStoryTextSizePreferences(){
        final String storyTextSizePreferencesName = SettingsPreferencesNotes.SETTINGS_STORY_TEXT_VIEW_SIZE_PREFERENCES;
        SharedPreferences storyTextSizePreferences = requireActivity().getSharedPreferences(storyTextSizePreferencesName, Context.MODE_PRIVATE);
        storyTextSizePreferences.edit().clear().apply();
    }
    private void clearTextFontTypePreferences(){
        final String fontTypePreferencesName = SettingsPreferencesNotes.SETTINGS_TEXT_FONT_TYPE_PREFERENCES;
        SharedPreferences fontTypePreferences = requireActivity().getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        fontTypePreferences.edit().clear().apply();
    }
    private void clearStoryTextFontTypePreferences(){
        final String storyFontTypePreferencesName = SettingsPreferencesNotes.SETTING_STORY_FONT_TYPE_PREFERENCES;
        SharedPreferences storyFontTypePreferences = requireActivity().getSharedPreferences(storyFontTypePreferencesName, Context.MODE_PRIVATE);
        storyFontTypePreferences.edit().clear().apply();
    }
    private void clearQuizTimerPreferences(){
        final String quizDurationPreferenceName = SettingsPreferencesNotes.SETTINGS_QUIZ_TIME_DURATION_PREFERENCE;
        SharedPreferences quizDurationPreference = requireActivity().getSharedPreferences(quizDurationPreferenceName, Context.MODE_PRIVATE);
        quizDurationPreference.edit().clear().apply();
    }
    private void clearStoryWordPlayAudiosPreferences(){
        final String plyWordAudioStoryPreferencesName = SettingsPreferencesNotes.PLAY_WORDS_AUDIO_STORY_PREFERENCES_NAME;
        SharedPreferences playWordAudioPreferences = requireActivity().getSharedPreferences(plyWordAudioStoryPreferencesName, Context.MODE_PRIVATE);
        playWordAudioPreferences.edit().clear().apply();
    }

    private void findViewsById(View view){
        messageTxtView = view.findViewById(R.id.restore_settings_text_view);
        confirmBtn = view.findViewById(R.id.restore_settings_confirmation_settings_button);
        rejectBtn = view.findViewById(R.id.restore_settings_reject_button);

        thisOnClickListener();
    }
    private void thisOnClickListener(){
        messageTxtView.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        rejectBtn.setOnClickListener(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        restInterface = (ReSetSettingsInterface) activity;
    }
}
