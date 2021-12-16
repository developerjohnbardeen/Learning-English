package com.example.a4000essentialwordsbook1.Settings.SettingsDialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.DialogFragment;

import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;
import com.google.android.material.card.MaterialCardView;

public class DisplayTranslationDialog extends DialogFragment implements View.OnClickListener {
    private RelativeLayout entranceRel, wordRel, defRel, exmplRel;
    private MaterialCardView confirmBtn, rejectBtn;
    private final boolean trueFlag = true;
    private final boolean falseFlag = false;
    private AppCompatCheckBox shwEntranceCheckBox, shwWordCheckBox, shwDefCheckBox, shwExmplCheckBox, shwStoryCheckBox;
    private AppCompatCheckBox settingsShowCheckBox;

    private SharedPreferences displayPreference;
    private final String displayTranslationPreferences = SettingsPreferencesNotes.SETTINGS_DISPLAY_TRANSLATION_PREFERENCES;
    private final String displayInEntranceTrnslKey = SettingsPreferencesNotes.DISPLAY_IN_THE_BEGINNING_KEY;
    private final String displayWordTrnslKey = SettingsPreferencesNotes.DISPLAY_WORD_TRANSLATION_KEY;
    private final String displayDefTrnslKey = SettingsPreferencesNotes.DISPLAY_DEFINITION_TRANSLATION_KEY;
    private final String displayExmplTrnslKey = SettingsPreferencesNotes.DISPLAY_EXAMPLE_TRANSLATION_KEY;
    private final String displayStoryTrnslKey = SettingsPreferencesNotes.DISPLAY_STORY_TRANSLATION_KEY;


    public static DisplayTranslationDialog newInstance(){
        DisplayTranslationDialog fragment = new DisplayTranslationDialog();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return inflater.inflate(R.layout.dialog_display_stranslation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsById(view);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.display_translations_confirmation_settings_button):
                translationPreferencesValueSetter();
                dismiss();
                break;
            case (R.id.display_translations_settings_reject_button):
                dismiss();
                break;
            case (R.id.translate_word_dialog_check_box):
            case (R.id.translate_definition_dialog_check_box):
            case (R.id.translate_example_dialog_check_box):
                setEntranceRelVisibility();
                break;


        }
    }
    private void setEntranceRelVisibility(){
        if (isAnyShwChecked()){
            entranceRel.setVisibility(View.VISIBLE);
        }else {
            entranceRel.setVisibility(View.INVISIBLE);
        }
    }
    private boolean isAnyShwChecked(){
        return isBoxWordChecked() || isBoxDefChecked() || isBoxExmplChecked();
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
    }

    private void findViewsById(View view){
        entranceRel = view.findViewById(R.id.entrance_translate_dialog_parent_layout);
        wordRel = view.findViewById(R.id.translate_word_dialog_parent_layout);
        defRel = view.findViewById(R.id.translate_definition_dialog_parent_layout);
        exmplRel = view.findViewById(R.id.translate_example_dialog_parent_layout);

        settingsShowCheckBox = requireActivity().findViewById(R.id.settings_display_translation_check_box);
        shwEntranceCheckBox = view.findViewById(R.id.entrance_translate_dialog_check_box);
        shwWordCheckBox = view.findViewById(R.id.translate_word_dialog_check_box);
        shwDefCheckBox = view.findViewById(R.id.translate_definition_dialog_check_box);
        shwExmplCheckBox = view.findViewById(R.id.translate_example_dialog_check_box);
        shwStoryCheckBox = view.findViewById(R.id.translate_story_dialog_check_box);

        confirmBtn = view.findViewById(R.id.display_translations_confirmation_settings_button);
        rejectBtn = view.findViewById(R.id.display_translations_settings_reject_button);
        allCheckBoxesInitialization();
        entranceLayoutVisibility();
        thisOnClickListener();
    }


    private void allCheckBoxesInitialization(){
        shwEntranceCheckBox.setChecked(isShwInEntrance());
        shwWordCheckBox.setChecked(isShwWordFlag());
        shwDefCheckBox.setChecked(isShwDefFlag());
        shwExmplCheckBox.setChecked(isShwExmplFlag());
        shwStoryCheckBox.setChecked(isShwStoryFlag());
    }
    private void entranceLayoutVisibility(){
        if (isBoxWordChecked() || isBoxDefChecked() || isBoxExmplChecked()){
            entranceRel.setVisibility(View.VISIBLE);
        }else {
            entranceRel.setVisibility(View.GONE);
        }
    }


    private boolean isShwInEntrance(){
        displayPreference = requireActivity().getSharedPreferences(displayTranslationPreferences, Context.MODE_PRIVATE);
        return displayPreference.getBoolean(displayInEntranceTrnslKey, falseFlag);
    }
    private boolean isShwWordFlag(){
        displayPreference = requireActivity().getSharedPreferences(displayTranslationPreferences, Context.MODE_PRIVATE);
        return displayPreference.getBoolean(displayWordTrnslKey, trueFlag);
    }
    private boolean isShwDefFlag(){
        displayPreference = requireActivity().getSharedPreferences(displayTranslationPreferences, Context.MODE_PRIVATE);
        return displayPreference.getBoolean(displayDefTrnslKey, falseFlag);
    }
    private boolean isShwExmplFlag(){
        displayPreference = requireActivity().getSharedPreferences(displayTranslationPreferences, Context.MODE_PRIVATE);
        return displayPreference.getBoolean(displayExmplTrnslKey, trueFlag);
    }
    private boolean isShwStoryFlag(){
        displayPreference = requireActivity().getSharedPreferences(displayTranslationPreferences, Context.MODE_PRIVATE);
        return displayPreference.getBoolean(displayStoryTrnslKey, trueFlag);
    }


    private void thisOnClickListener(){
        entranceRel.setOnClickListener(this);
        wordRel.setOnClickListener(this);
        defRel.setOnClickListener(this);
        exmplRel.setOnClickListener(this);
        shwStoryCheckBox.setOnClickListener(this);
        shwEntranceCheckBox.setOnClickListener(this);
        shwWordCheckBox.setOnClickListener(this);
        shwDefCheckBox.setOnClickListener(this);
        shwExmplCheckBox.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        rejectBtn.setOnClickListener(this);
    }

    private void translationPreferencesValueSetter(){
        displayPreference = requireActivity().getSharedPreferences(displayTranslationPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor displayFarsiEdit = displayPreference.edit();
        displayFarsiEdit.putBoolean(displayInEntranceTrnslKey, entranceFlag());
        displayFarsiEdit.putBoolean(displayWordTrnslKey, isBoxWordChecked());
        displayFarsiEdit.putBoolean(displayDefTrnslKey, isBoxDefChecked());
        displayFarsiEdit.putBoolean(displayExmplTrnslKey, isBoxExmplChecked());
        displayFarsiEdit.putBoolean(displayStoryTrnslKey, isBoxStoryChecked());
        displayFarsiEdit.apply();
    }

    private boolean entranceFlag(){
        return isEntrancePrefChecked() && isBoxEntranceShwChecked();
    }

    private boolean isEntrancePrefChecked(){
        return isBoxWordChecked() || isBoxDefChecked() || isBoxExmplChecked() || isBoxStoryChecked();
    }


    private boolean isBoxEntranceShwChecked(){return shwEntranceCheckBox.isChecked();}
    private boolean isBoxWordChecked(){return shwWordCheckBox.isChecked();}
    private boolean isBoxDefChecked(){return shwDefCheckBox.isChecked();}
    private boolean isBoxExmplChecked(){return shwExmplCheckBox.isChecked();}
    private boolean isBoxStoryChecked(){return shwStoryCheckBox.isChecked();}


    @Override
    public void onDestroy() {
        super.onDestroy();
        settingsShowCheckBoxChecker();
    }

    private void settingsShowCheckBoxChecker(){
        settingsShowCheckBox.setChecked(isAllBoxedChecked());
    }
    private boolean isAllBoxedChecked(){
        displayPreference = requireActivity().getSharedPreferences(displayTranslationPreferences, Context.MODE_PRIVATE);
        final boolean shwEntranceFlag = displayPreference.getBoolean(displayInEntranceTrnslKey, falseFlag);
        final boolean shwWordFlag = displayPreference.getBoolean(displayWordTrnslKey, trueFlag);
        final boolean shwDefFlag = displayPreference.getBoolean(displayDefTrnslKey, falseFlag);
        final boolean shwExmplFlag = displayPreference.getBoolean(displayExmplTrnslKey, trueFlag);
        final boolean shwStoryFlag = displayPreference.getBoolean(displayStoryTrnslKey, trueFlag);

        return (shwEntranceFlag || shwWordFlag || shwDefFlag || shwExmplFlag || shwStoryFlag) ? trueFlag : falseFlag;
    }
}
