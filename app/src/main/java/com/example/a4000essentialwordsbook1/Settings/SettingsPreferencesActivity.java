package com.example.a4000essentialwordsbook1.Settings;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.a4000essentialwordsbook1.DownloadClasses.DownloadFilesActivity;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Settings.SettingListanerInterface.ReSetSettingsInterface;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.AutoNighDayModeThemDialog;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.DisplayTranslationDialog;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.PlayWordsAudioStoryFragment;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.ReStoreSettingsDialogAlert;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.SettingsAutoPlayDialogFragment;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.SettingsQuizTimeDurationDialogFragment;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.SettingsStoryTextSizeDialogFragment;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.SettingsTextFontTypeDialogFragment;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.SettingsTextSizeDialogFragment;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.StoryTextFontTypeDialogFragment;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;

public class SettingsPreferencesActivity extends AppCompatActivity implements View.OnClickListener, ReSetSettingsInterface {
    private RelativeLayout autoNightModeLayout, autoPlayLayout, displayTranslationLayout, restoreSettingsLayout;
    private RelativeLayout txtSizeLayout, fontTypeLayout, quizTimerLayout, storyTxtSizeLayout;
    private RelativeLayout plyAudioStoryWordLayout, downloadFilesLayout;
    private AppCompatCheckBox autoNightCheckBox, autoPlayCheckBox, shwTrnslCheckBox, quzTimerCheckBox;
    private RelativeLayout storyFontLayout;

    private final String plyAudioPreferencesName = SettingsPreferencesNotes.SETTINGS_AUTO_PLAY_AUDIO_PREFERENCES;
    private final String plyAllKey = SettingsPreferencesNotes.AUTO_PLAY_ALL_KEY;
    private final String wordPlyKey = SettingsPreferencesNotes.WORD_PLAY_KEY;
    private final String definitionPlyKey = SettingsPreferencesNotes.DEFINITION_PLAY_KEY;
    private final String examplePlyKey = SettingsPreferencesNotes.EXAMPLE_PLAY_KEY;

    private final String displayTranslationPreferences = SettingsPreferencesNotes.SETTINGS_DISPLAY_TRANSLATION_PREFERENCES;
    private final String displayEntranceTrnslKey = SettingsPreferencesNotes.DISPLAY_IN_THE_BEGINNING_KEY;
    private final String displayWordTrnslKey = SettingsPreferencesNotes.DISPLAY_WORD_TRANSLATION_KEY;
    private final String displayDefTrnslKey = SettingsPreferencesNotes.DISPLAY_DEFINITION_TRANSLATION_KEY;
    private final String displayExmplTrnslKey = SettingsPreferencesNotes.DISPLAY_EXAMPLE_TRANSLATION_KEY;
    private final String displayStoryTrnslKey = SettingsPreferencesNotes.DISPLAY_STORY_TRANSLATION_KEY;

    private final String fontTypePreferences = SettingsPreferencesNotes.SETTINGS_TEXT_FONT_TYPE_PREFERENCES;
    private final String fontTypeKey = SettingsPreferencesNotes.TEXT_FONT_TYPE_KEY;

    private SharedPreferences quizDurationPreference;
    private final String quizDurationPreferenceName = SettingsPreferencesNotes.SETTINGS_QUIZ_TIME_DURATION_PREFERENCE;
    private final String cancelTimerKey = SettingsPreferencesNotes.CANCEL_QUIZ_TIMER_KEY;


    private RelativeLayout sttngOnBckPressedLayout;
    private TextView sttngTxtViewTitle;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //setTheme(android.R.style.ThemeOverlay_Material_Dark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        activityFindViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.settings_auto_dark_mode_parent_layout):
                autoNighModeDialog();
                break;
            case (R.id.settings_auto_play_audio_parent_layout):
                autoPlaySettingsDialogFragment();
                break;
            case (R.id.settings_display_translation_parent_layout):
                displayTranslationDialog();
                break;
            case (R.id.settings_text_size_parent_layout):
                textSizeSettingsDialogFragment();
                break;
            case (R.id.settings_text_size_story_parent_layout):
                storyTextSizeSettingsDialogFragment();
                break;
            case (R.id.settings_texT_font_type_parent_layout):
                textFontTypeDialogFragment();
                break;
            case (R.id.settings_texT_font_type_story_parent_layout):
                storyFontTypeDialogFragment();
                break;
            case (R.id.settings_play_word_audio_story_parent_layout):
                playAudioWordStoryDialogFragment();
                break;
            case (R.id.settings_quiz_timer_parent_layout):
                quizTimerFunctions();
                break;
            case (R.id.restore_settings_parent_layout):
                restoreSettingsDialog();
                break;
            case (R.id.download_settings_parent_layout):
                downloadActivityStarter();
                break;
        }
    }

    private void restoreSettingsDialog(){
        ReStoreSettingsDialogAlert restoreSettingsDialog = ReStoreSettingsDialogAlert.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("restoreSettingsDialog");
        if (prev != null){
            ft.remove(prev);
        }
        restoreSettingsDialog.show(ft, "restoreSettingsDialog");
    }



    private void autoNighModeDialog(){
        AutoNighDayModeThemDialog modeThemDialogFragment = AutoNighDayModeThemDialog.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("autoNighModeDialog");
        if (prev != null){
            ft.remove(prev);
        }
        modeThemDialogFragment.show(ft, "autoNighModeDialog");
    }

    private void autoPlaySettingsDialogFragment(){
        SettingsAutoPlayDialogFragment autoPlayDialogFragment = SettingsAutoPlayDialogFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("autoPlaySettingsDialog");
        if (prev != null){
            ft.remove(prev);
        }
        autoPlayDialogFragment.show(ft, "autoPlaySettingsDialog");
    }

    private void displayTranslationDialog(){
        DisplayTranslationDialog translationDialog = DisplayTranslationDialog.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("translationDialog");
        if (prev != null){
            ft.remove(prev);
        }
        translationDialog.show(ft, "translationDialog");
    }

    private void textSizeSettingsDialogFragment(){
        SettingsTextSizeDialogFragment textSizeDialogFragment = SettingsTextSizeDialogFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("textSizePickerDialog");
        if (prev != null){
            ft.remove(prev);
        }
        textSizeDialogFragment.show(ft, "textSizePickerDialog");
    }
    private void storyTextSizeSettingsDialogFragment(){
        SettingsStoryTextSizeDialogFragment storyTextSizeDialog = SettingsStoryTextSizeDialogFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("storyTextSizePickerDialog");
        if (prev != null){
            ft.remove(prev);
        }
        storyTextSizeDialog.show(ft, "storyTextSizePickerDialog");
    }

    private void textFontTypeDialogFragment(){
        SettingsTextFontTypeDialogFragment fontTypeDialogFragment = SettingsTextFontTypeDialogFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("textFontTypeDialog");
        if (prev != null){
            ft.remove(prev);
        }
        fontTypeDialogFragment.show(ft, "textFontTypeDialog");
    }

    private void storyFontTypeDialogFragment(){
        StoryTextFontTypeDialogFragment storyFontDialogFragment = StoryTextFontTypeDialogFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("storyFontTypeDialog");
        if (prev != null){
            ft.remove(prev);
        }
        storyFontDialogFragment.show(ft, "storyFontTypeDialog");
    }

    private void quizTimerFunctions(){
        quizTimerDurationDialogFragment();
    }
    private void quizTimerDurationDialogFragment(){
        SettingsQuizTimeDurationDialogFragment quizTimerDialog = SettingsQuizTimeDurationDialogFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("quizTimerDurationDialog");
        if (prev != null){
            ft.remove(prev);
        }
        quizTimerDialog.show(ft, "quizTimerDurationDialog");
    }

    private void playAudioWordStoryDialogFragment(){
        PlayWordsAudioStoryFragment dialogFragment = PlayWordsAudioStoryFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("playWordAudioStoryDialog");
        if (prev != null){
            ft.remove(prev);
        }
        dialogFragment.show(ft, "playWordAudioStoryDialog");
    }

    private void downloadActivityStarter(){
        Intent downloadIntent = new Intent(this, DownloadFilesActivity.class);
        startActivity(downloadIntent);
    }






    private void activityFindViews(){
        autoNightModeLayout = findViewById(R.id.settings_auto_dark_mode_parent_layout);
        autoPlayLayout = findViewById(R.id.settings_auto_play_audio_parent_layout);
        displayTranslationLayout = findViewById(R.id.settings_display_translation_parent_layout);
        txtSizeLayout = findViewById(R.id.settings_text_size_parent_layout);
        storyTxtSizeLayout = findViewById(R.id.settings_text_size_story_parent_layout);
        fontTypeLayout = findViewById(R.id.settings_texT_font_type_parent_layout);
        quizTimerLayout = findViewById(R.id.settings_quiz_timer_parent_layout);
        storyFontLayout = findViewById(R.id.settings_texT_font_type_story_parent_layout);
        plyAudioStoryWordLayout = findViewById(R.id.settings_play_word_audio_story_parent_layout);
        restoreSettingsLayout = findViewById(R.id.restore_settings_parent_layout);
        downloadFilesLayout = findViewById(R.id.download_settings_parent_layout);

        quzTimerCheckBox = findViewById(R.id.quiz_timer_duration_check_box);
        autoNightCheckBox = findViewById(R.id.settings_auto_dark_mode_check_box);
        autoPlayCheckBox = findViewById(R.id.settings_auto_play_check_box);
        shwTrnslCheckBox = findViewById(R.id.settings_display_translation_check_box);

        sttngTxtViewTitle = findViewById(R.id.settings_title);

        sttngOnBckPressedLayout = findViewById(R.id.settings_tab_layout_bck_bttn_layout);
        sttngOnBckPressedLayout.setOnClickListener(view -> onBackPressed());
        sttngTxtViewTitle.setText(R.string.settings_title_eng_str);


        onThisClickListener();
        quizTimerCheckBoxValueSetter();
        displayTranslationsCheckBoxChecker();
        audioPlayCheckBoxChecker();
    }
    private void onThisClickListener(){
        autoNightModeLayout.setOnClickListener(this);
        autoPlayLayout.setOnClickListener(this);
        storyTxtSizeLayout.setOnClickListener(this);
        displayTranslationLayout.setOnClickListener(this);
        plyAudioStoryWordLayout.setOnClickListener(this);
        restoreSettingsLayout.setOnClickListener(this);
        downloadFilesLayout.setOnClickListener(this);
        txtSizeLayout.setOnClickListener(this);
        fontTypeLayout.setOnClickListener(this);
        quizTimerLayout.setOnClickListener(this);
        storyFontLayout.setOnClickListener(this);
    }
    private void quizTimerCheckBoxValueSetter(){
        quzTimerCheckBox.setChecked(!isTimerCancel());
    }
    private boolean isTimerCancel(){
        quizDurationPreference = getSharedPreferences(quizDurationPreferenceName, Context.MODE_PRIVATE);
        return quizDurationPreference.getBoolean(cancelTimerKey, false);
    }

    private void displayTranslationsCheckBoxChecker(){
        shwTrnslCheckBox.setChecked(isAnyDisplayedTranslation());
    }
    private boolean isAnyDisplayedTranslation(){
        SharedPreferences displayPreference = getSharedPreferences(displayTranslationPreferences, Context.MODE_PRIVATE);
        final boolean shwWordFlag = displayPreference.getBoolean(displayWordTrnslKey, true);
        final boolean shwDefFlag = displayPreference.getBoolean(displayDefTrnslKey, false);
        final boolean shwExmplFlag = displayPreference.getBoolean(displayExmplTrnslKey, false);
        final boolean shwStoryFlag = displayPreference.getBoolean(displayStoryTrnslKey, false);

        return shwWordFlag || shwDefFlag || shwExmplFlag || shwStoryFlag;
    }

    private void audioPlayCheckBoxChecker(){
        autoPlayCheckBox.setChecked(isAnyAudioPlayed());
    }

    private boolean isAnyAudioPlayed(){
        SharedPreferences plyAudioPreferences = getSharedPreferences(plyAudioPreferencesName, Context.MODE_PRIVATE);
        final boolean allFlag = plyAudioPreferences.getBoolean(plyAllKey, false);
        final boolean wrdFlag = plyAudioPreferences.getBoolean(wordPlyKey, true);
        final boolean defFlag = plyAudioPreferences.getBoolean(definitionPlyKey, false);
        final boolean exmplFlag = plyAudioPreferences.getBoolean(examplePlyKey, false);

        return allFlag || wrdFlag || defFlag || exmplFlag;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void reStart() {
        Intent intent = new Intent(SettingsPreferencesActivity.this, SettingsPreferencesActivity.class);
        startActivity(intent);
        finish();
    }
}