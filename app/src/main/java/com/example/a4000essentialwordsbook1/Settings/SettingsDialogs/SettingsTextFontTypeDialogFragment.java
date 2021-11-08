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
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.FontTypeFiles.GlobalFonts;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;

public class SettingsTextFontTypeDialogFragment extends DialogFragment implements View.OnClickListener {
    private TextView defTitleSmpl, defSmplTxtView, defTrnsltSmplTxtView;
    private TextView confirmBtn, rejectBtn;
    private Spinner engFontSpinnerList, perFontSpinnerList;


    private final String[] perStringList = GlobalFonts.perStringList;
    private final int[] perFontList = GlobalFonts.perFontList;
    private final String[] engStringList = GlobalFonts.engStringList;
    private final int[] engFontList = GlobalFonts.engFontList;


    private SharedPreferences fontTypePreferences;
    private final String fontTypePreferencesName = SettingsPreferencesNotes.SETTINGS_TEXT_FONT_TYPE_PREFERENCES;
    private final String engListPositionKey = SettingsPreferencesNotes.ENGLISH_LIST_POSITION_KEY;
    private final String perListPositionKey = SettingsPreferencesNotes.PERSIAN_LIST_POSITION_KEY;



    public static SettingsTextFontTypeDialogFragment newInstance(){
        SettingsTextFontTypeDialogFragment textFontTypeFragment = new SettingsTextFontTypeDialogFragment();
        return textFontTypeFragment;
    }


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_text_font_type , container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogFindViewsById(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.story_font_type_confirmation_settings_button):
                fontTypePreferencesValueSetter();
                dismiss();
                break;
            case (R.id.story_font_type_settings_reject_button):
                dismiss();
                break;
        }
    }

    private void fontTypePreferencesValueSetter(){
        fontTypePreferences = requireActivity().getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor fontTypeEdit = fontTypePreferences.edit();
        fontTypeEdit.putInt(engListPositionKey, getEngValue());
        fontTypeEdit.putInt(perListPositionKey, getPerValue());
        fontTypeEdit.apply();
    }
    private int getPerValue(){return perFontSpinnerList.getSelectedItemPosition();}
    private int getEngValue(){return engFontSpinnerList.getSelectedItemPosition();}


    private void dialogFindViewsById(View view){
        defTitleSmpl = view.findViewById(R.id.sample_definition_text_view);
        defSmplTxtView = view.findViewById(R.id.sample_definition_content_text_view);
        defTrnsltSmplTxtView = view.findViewById(R.id.sample_definition_translation_text_view);
        engFontSpinnerList = view.findViewById(R.id.settings_english_font_type_picker);
        perFontSpinnerList = view.findViewById(R.id.settings_persian_font_type_picker);
        confirmBtn = view.findViewById(R.id.story_font_type_confirmation_settings_button);
        rejectBtn = view.findViewById(R.id.story_font_type_settings_reject_button);

        fontPickersFunctions();
        thisDialogClickListener();
    }
    private void thisDialogClickListener(){
        confirmBtn.setOnClickListener(this);
        rejectBtn.setOnClickListener(this);
    }

    private void fontPickersFunctions(){
        englishFontPickerFunctions();
        persianFontPickerFunctions();
    }


    private void englishFontPickerFunctions(){
        engFontPickerValueSetter();
        englishFontPickerListener();
    }
    private void engFontPickerValueSetter(){
        ArrayAdapter spinnerAdapter = new ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, engStringList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        engFontSpinnerList.setAdapter(spinnerAdapter);
        engFontSpinnerList.setSelection(getEngSpinVal());
    }
    private void englishFontPickerListener(){
        engFontSpinnerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                engTxtViewsSamples(engFontList[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void engTxtViewsSamples(int font){
        defTitleTxtViewSample(font);
        defContentTxtViewSample(font);
    }
    private void defTitleTxtViewSample(int font){
        Typeface engTypeFace = ResourcesCompat.getFont(requireActivity(), font);
        defTitleSmpl.setTypeface(engTypeFace);
    }
    private void defContentTxtViewSample(int font){
        Typeface engTypeFace = ResourcesCompat.getFont(requireActivity(), font);
        defSmplTxtView.setTypeface(engTypeFace);
    }
    private int getEngSpinVal(){
        fontTypePreferences = requireActivity().getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getInt(engListPositionKey, 0);
    }


    private void persianFontPickerFunctions(){
        persianFontPickerValueSetter();
        persianFontPickerListener();
    }
    private void persianFontPickerValueSetter(){
        ArrayAdapter spinnerAdapter = new ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, perStringList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        perFontSpinnerList.setAdapter(spinnerAdapter);
        perFontSpinnerList.setSelection(getPerSpinVal());
    }
    private void persianFontPickerListener(){
        perFontSpinnerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                defTranslationTxtViewSample(perFontList[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void defTranslationTxtViewSample(int font){
        Typeface perTypeFace = ResourcesCompat.getFont(requireActivity(), font);
        defTrnsltSmplTxtView.setTypeface(perTypeFace);
    }
    private int getPerSpinVal(){
        fontTypePreferences = requireActivity().getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getInt(perListPositionKey, 1);
    }




}
