package com.example.a4000essentialwordsbook1.Settings.SettingsDialogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.FontTypeFiles.GlobalFonts;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;
import com.google.android.material.card.MaterialCardView;

public class StoryTextFontTypeDialogFragment extends DialogFragment implements View.OnClickListener {

    private MaterialCardView confirmBtn, rejectBtn;
    private TextView sampleTextView;
    private AppCompatCheckBox engBolderCheckBox, perBolderCheckBox;
    private Spinner fntTypeSpinner;
    private ArrayAdapter spinnerAdapter;
    private AppCompatRadioButton engFontTypeRadioBtn, perFntTypeRadioBtn;
    private String engFont, perFont, whtLang;
    private final String engLangValueStr = "english";
    private final String perLangValueStr = "persian";
    private int engSpinVal, perSpinVal;


    private final String[] perStringList = GlobalFonts.perStringList;
    private final int[] perFontList = GlobalFonts.perFontList;
    private final String[] engStringList = GlobalFonts.engStringList;
    private final int[] engFontList = GlobalFonts.engFontList;


    private SharedPreferences storyFontTypePreferences;
    private final String storyFontTypePreferencesName = SettingsPreferencesNotes.SETTING_STORY_FONT_TYPE_PREFERENCES;
    private final String stryEngFontKey = SettingsPreferencesNotes.STORY_ENGLISH_FONT_TYPE_KEY;
    private final String stryPerFontKey = SettingsPreferencesNotes.STORY_PERSIAN_FONT_TYPE_KEY;
    private final String stryFntTypeRadioBtnKey = SettingsPreferencesNotes.STORY_ENG_OR_PER_RADIO_BUTTON_KEY;
    private final String engListPositionKey = SettingsPreferencesNotes.STORY_ENG_PICKER_FONT_VALUE_KEY;
    private final String perListPositionKey = SettingsPreferencesNotes.STORY_PER_PICKER_FONT_VALUE_KEY;
    private final String textBolderKey= SettingsPreferencesNotes.STORY_TEXT_BOLDER_KEY;

    SharedPreferences storyTextSizePreferences;
    private final String storyTextSizePreferencesName = SettingsPreferencesNotes.SETTINGS_STORY_TEXT_VIEW_SIZE_PREFERENCES;
    private final String storyTextSizeKey = SettingsPreferencesNotes.STORY_TEXT_VIEW_SIZE_KEY;



    public static StoryTextFontTypeDialogFragment newInstance(){
        StoryTextFontTypeDialogFragment storyFontFragment = new StoryTextFontTypeDialogFragment();
        return storyFontFragment;
    }


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_story_text_font , container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogFindViewsById(view);
    }

    private void dialogFindViewsById(View view){
        confirmBtn = view.findViewById(R.id.story_font_type_confirmation_settings_button);
        rejectBtn = view.findViewById(R.id.story_font_type_settings_reject_button);
        engFontTypeRadioBtn = view.findViewById(R.id.english_font_radio_button);
        perFntTypeRadioBtn = view.findViewById(R.id.persian_font_radio_button);
        sampleTextView = view.findViewById(R.id.story_txt_sample_font_type_text_view);
        sampleTextView.setTextSize(storyTxtSize());
        fntTypeSpinner = view.findViewById(R.id.story_font_picker);
        engBolderCheckBox = view.findViewById(R.id.story_check_box_text_view_bolder);
    }

    @Override
    public void onResume() {
        super.onResume();
        onResumeFunctions();
    }
    private void onResumeFunctions(){
        textBolderCheckBoxInitializer();
        valueInitializerFunctions();
        thisDialogClickListener();
    }

    private void textBolderCheckBoxInitializer(){
        engBolderCheckBox.setChecked(getBolderPreference());
    }



    private void valueInitializerFunctions(){
        radioButtonInitializer();
        fontTypePickerFunctions();
    }
    private void radioButtonInitializer(){
        if (getStryFntTypeRadioBtnKey().equalsIgnoreCase(engLangValueStr)){
            engFontTypeRadioBtn.setChecked(true);
            perFntTypeRadioBtn.setChecked(false);
        }else {
            perFntTypeRadioBtn.setChecked(true);
            engFontTypeRadioBtn.setChecked(false);
        }
    }

    private void fontTypePickerFunctions(){
        fontTypeInitializer();
        fontTypePickerListener();
    }
    private void fontTypeInitializer(){

        if (isPrefEngRadioBtn()){
            engSpinnerValueSetter();
        }else {
            perSpinnerValueSetter();
        }
    }

    private void engSpinnerValueSetter(){
        spinnerAdapter = new ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, engStringList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fntTypeSpinner.setAdapter(spinnerAdapter);
        fntTypeSpinner.setSelection(getEngSpinVal());
    }
    private void perSpinnerValueSetter(){
        spinnerAdapter = new ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, perStringList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fntTypeSpinner.setAdapter(spinnerAdapter);
        fntTypeSpinner.setSelection(getPerSpinVal());
    }

    private int getEngSpinVal(){
        storyFontTypePreferences = requireActivity().getSharedPreferences(storyFontTypePreferencesName, Context.MODE_PRIVATE);
        return storyFontTypePreferences.getInt(engListPositionKey, 0);
    }
    private int getPerSpinVal(){
        storyFontTypePreferences = requireActivity().getSharedPreferences(storyFontTypePreferencesName, Context.MODE_PRIVATE);
        return storyFontTypePreferences.getInt(perListPositionKey, 0);
    }


    private void fontTypePickerListener(){
        fntTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RadioButtonStatusDeterminer(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void RadioButtonStatusDeterminer(int position){
        if (isEngRadioBtn()){
            engSpinVal = position;
            smplTextViewFontDeterminer(getFontEng(engSpinVal));
        }else {
            perSpinVal = position;
            smplTextViewFontDeterminer(getFontPer(perSpinVal));
        }
    }
    private Typeface typeface;
    private void smplTextViewFontDeterminer(int font){
        typeface = ResourcesCompat.getFont(requireActivity(), font);
        sampleTextView.setTypeface(typeface, textStyle());
    }
    private int textStyle(){
        if (isBolderChecked()){
            return Typeface.BOLD;
        }else {
            return Typeface.NORMAL;
        }
    }
    private int getFontEng(int position){return engFontList[position];}
    private int getFontPer(int position){return perFontList[position];}






    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.story_font_type_confirmation_settings_button):
                storyFontPreferencesValueSetter();
                dismiss();
                break;
            case (R.id.story_font_type_settings_reject_button):
                dismiss();
                break;
            case (R.id.english_font_radio_button):
                engRadioBtnFunctions();
                break;
            case (R.id.persian_font_radio_button):
                perRadioBtnFunctions();
                break;
            case (R.id.story_check_box_text_view_bolder):
                sampleTextView.setTypeface(typeface, textStyle());
                break;
        }
    }

    private void engRadioBtnFunctions(){
        sampleTextView.setText(getEngLanguage());
        engRadioValueSetter();
        engSpinnerValueSetter();
    }
    private int getEngLanguage(){return R.string.eng_sample_story_text_str;}
    private void engRadioValueSetter(){
        engFontTypeRadioBtn.setChecked(true);
        perFntTypeRadioBtn.setChecked(false);
        whtLang = engLangValueStr;
    }

    private void perRadioBtnFunctions(){
        sampleTextView.setText(getPerLanguage());
        perRadioValueSetter();
        perSpinnerValueSetter();
    }
    private int getPerLanguage(){return R.string.per_sample_story_text_str;}
    private void perRadioValueSetter(){
        perFntTypeRadioBtn.setChecked(true);
        engFontTypeRadioBtn.setChecked(false);
        whtLang = perLangValueStr;
    }



    private void storyFontPreferencesValueSetter(){
        storyFontTypePreferences = requireActivity().getSharedPreferences(storyFontTypePreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor storyFontEdit = storyFontTypePreferences.edit();
        storyFontEdit.putString(stryFntTypeRadioBtnKey, whtLang);
        storyFontEdit.putInt(engListPositionKey, engSpinVal);
        storyFontEdit.putInt(perListPositionKey, perSpinVal);
        storyFontEdit.putBoolean(textBolderKey, isBolderChecked());
        storyFontEdit.apply();
    }



    private String getStryPerFontKey(){
        storyFontTypePreferences = requireActivity().getSharedPreferences(storyFontTypePreferencesName, Context.MODE_PRIVATE);
        return storyFontTypePreferences.getString(stryPerFontKey, "null");
    }
    private String getStryFntTypeRadioBtnKey(){
        storyFontTypePreferences = requireActivity().getSharedPreferences(storyFontTypePreferencesName, Context.MODE_PRIVATE);
        return storyFontTypePreferences.getString(stryFntTypeRadioBtnKey, engLangValueStr);
    }

    private boolean isPrefEngRadioBtn(){
        return getStryFntTypeRadioBtnKey().equalsIgnoreCase(engLangValueStr);
    }
    private boolean isEngRadioBtn(){
        return engFontTypeRadioBtn.isChecked();
    }
    private boolean isPerRadioBtn(){
        return getStryPerFontKey().equalsIgnoreCase(perLangValueStr);
    }

    private void clearPreferencesAllValue(){
        storyFontTypePreferences = requireActivity().getSharedPreferences(storyFontTypePreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor clearEdit = storyFontTypePreferences.edit().clear();
        clearEdit.apply();
    }
    private void thisDialogClickListener(){
        confirmBtn.setOnClickListener(this);
        rejectBtn.setOnClickListener(this);
        sampleTextView.setOnClickListener(this);
        engFontTypeRadioBtn.setOnClickListener(this);
        perFntTypeRadioBtn.setOnClickListener(this);
        engBolderCheckBox.setOnClickListener(this);
    }

    private int storyTxtSize(){
        storyTextSizePreferences = requireActivity().getSharedPreferences(storyTextSizePreferencesName, Context.MODE_PRIVATE);
        return storyTextSizePreferences.getInt(storyTextSizeKey, 18);
    }

    private boolean isBolderChecked(){return engBolderCheckBox.isChecked();}
    private boolean getBolderPreference(){
        storyFontTypePreferences = requireActivity().getSharedPreferences(storyFontTypePreferencesName, Context.MODE_PRIVATE);
        return storyFontTypePreferences.getBoolean(textBolderKey, false);
    }

}