package com.example.a4000essentialwordsbook1.Settings.SettingsDialogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.DialogFragment;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;



public class SettingsTextSizeDialogFragment extends DialogFragment implements View.OnClickListener{
    private NumberPicker numPicker, engNumPicker;
    private TextView rjctBtn, cnfmBtn;
    private TextView endPickerNameTxtView, perPickerNameTxtView;
    private AppCompatCheckBox bothLanCheckBox;

    private SharedPreferences textSizePreferences;
    private SharedPreferences.Editor txtSizeEdit;
    private SharedPreferences.Editor engTxtSizeEdit;
    private final String textViewSizePreferencesName = SettingsPreferencesNotes.SETTINGS_TEXT_VIEW_SIZE_PREFERENCES;
    private final String txtViewSizeKey = SettingsPreferencesNotes.PERSIAN_TEXT_VIEW_SIZE_KEY;
    private final String engTxtViewSizeKey = SettingsPreferencesNotes.ENGLISH_TEXT_VIEW_SIZE_KEY;
    private final String bothLanguageKey = SettingsPreferencesNotes.BOTH_LANGUAGE_KEY;

    public static SettingsTextSizeDialogFragment newInstance(){
        SettingsTextSizeDialogFragment textSizeFragment = new SettingsTextSizeDialogFragment();
        return textSizeFragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_text_size_pikcer , container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogFindViewById(view);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.txt_size_confirmation_settings_button):
                applySharePreferences();
                dismiss();
                break;
            case (R.id.txt_size_settings_reject_button):
                dismiss();
                break;
            case (R.id.english_and_persian_both_check_box):
                engLanguageComponentsVisibilitySetter();
                break;
        }
    }

    private void engLanguageComponentsVisibilitySetter(){
        if (bothLanCheckBox.isChecked()){
            engNumPicker.setVisibility(View.INVISIBLE);
            endPickerNameTxtView.setVisibility(View.INVISIBLE);
            bothLanCheckBox.setChecked(true);
            bothLanCheckBox.isChecked();
            //textViewSampleSizeInitializerRealTime();
        }else {
            engNumPicker.setVisibility(View.VISIBLE);
            endPickerNameTxtView.setVisibility(View.VISIBLE);
            bothLanCheckBox.setChecked(false);
            bothLanCheckBox.isChecked();
            //engTextViewSampleSizeInitializerRealTime();
        }
    }





    private void dialogFindViewById(View view){
        numPicker = view.findViewById(R.id.text_size_number_picker);
        engNumPicker = view.findViewById(R.id.english_text_size_number_picker);
        rjctBtn = view.findViewById(R.id.txt_size_settings_reject_button);
        cnfmBtn = view.findViewById(R.id.txt_size_confirmation_settings_button);
        bothLanCheckBox = view.findViewById(R.id.english_and_persian_both_check_box);

        endPickerNameTxtView = view.findViewById(R.id.settings_english_text_size_picker_name);
        perPickerNameTxtView = view.findViewById(R.id.settings_text_size_picker_name);

        allInitializerValuerSetter();
        textSizePickerFunctions();
        textViewSizeInitializer();
        thisDialogOnClickListener();

    }

    private void thisDialogOnClickListener(){
        rjctBtn.setOnClickListener(this);
        cnfmBtn.setOnClickListener(this);
        bothLanCheckBox.setOnClickListener(this);
    }

    private void allInitializerValuerSetter(){
        bothLanCheckBoxInitializer();
    }
    private void textViewSizeInitializer(){
        endPickerNameTxtView.setTextSize(engTxtSizePref());
        perPickerNameTxtView.setTextSize(txtSizePref());
    }



    private void bothLanCheckBoxInitializer(){
        bothLanCheckBox.setChecked(isBothLang());
        if (isBothLang()){
            invisibilityEngLangComponents();
        }else {
            visibilityEngLangComponents();
        }
    }

    private void invisibilityEngLangComponents(){
        engNumPicker.setVisibility(View.INVISIBLE);
        endPickerNameTxtView.setVisibility(View.INVISIBLE);
    }
    private void visibilityEngLangComponents(){
        engNumPicker.setVisibility(View.VISIBLE);
        endPickerNameTxtView.setVisibility(View.VISIBLE);
    }

    private void textSizePickerFunctions(){
        defaultValues();
        engDefaultVales();
        numberPickerListener();
        engNumberPickerListener();
    }
    private void defaultValues(){
        numPicker.setMinValue(12);
        numPicker.setMaxValue(25);
        numPicker.setValue(txtSizePref());
    }

    private void engDefaultVales(){
        engNumPicker.setMinValue(12);
        engNumPicker.setMaxValue(25);
        engNumPicker.setValue(engTxtSizePref());
    }









    private void numberPickerListener(){

        numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //engTxtSize = (bothLanCheckBox.isChecked()) ?  engTxtSizePref() : newVal;
                boolean flag = bothLanCheckBox.isChecked();
                perPickerNameTxtView.setTextSize(newVal);
                textviewSizePreferencesFun(newVal, flag);
                if (flag){
                    engTextViewSizePreferencesFun(newVal, true);
                }
            }
        });
    }

    private void textviewSizePreferencesFun(int newVal, boolean flag){
        textSizePreferences = requireActivity().getSharedPreferences(textViewSizePreferencesName, Context.MODE_PRIVATE);
        txtSizeEdit = textSizePreferences.edit();
        txtSizeEdit.putInt(txtViewSizeKey, newVal);
        txtSizeEdit.putBoolean(bothLanguageKey, flag);
    }


    private void engNumberPickerListener(){
        engNumPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                boolean flag = bothLanCheckBox.isChecked();
                endPickerNameTxtView.setTextSize(newVal);
                engTextViewSizePreferencesFun(newVal, flag);
            }
        });
    }
    private void engTextViewSizePreferencesFun(int newVal, boolean flag){
        textSizePreferences = requireActivity().getSharedPreferences(textViewSizePreferencesName, Context.MODE_PRIVATE);
        engTxtSizeEdit = textSizePreferences.edit();
        engTxtSizeEdit.putInt(engTxtViewSizeKey, newVal);
        engTxtSizeEdit.putBoolean(bothLanguageKey, flag);

    }
    private void applySharePreferences(){
        if (txtSizeEdit != null) {
            txtSizeEdit.apply();
        }else {
            textviewSizePreferencesFun(txtSizePref(), bothLanCheckBox.isChecked());
            txtSizeEdit.apply();
            if (bothLanCheckBox.isChecked()){
                engTextViewSizePreferencesFun(txtSizePref(), bothLanCheckBox.isChecked());
                engTxtSizeEdit.apply();
            }
        }
        if (engTxtSizeEdit != null) {engTxtSizeEdit.apply();}
    }


    private int txtSizePref(){
        textSizePreferences = requireActivity().getSharedPreferences(textViewSizePreferencesName, Context.MODE_PRIVATE);
        return textSizePreferences.getInt(txtViewSizeKey, 18);
    }
    private int engTxtSizePref(){
        textSizePreferences = requireActivity().getSharedPreferences(textViewSizePreferencesName, Context.MODE_PRIVATE);
        return textSizePreferences.getInt(engTxtViewSizeKey, 18);
    }

    private boolean isBothLang(){
        textSizePreferences = requireActivity().getSharedPreferences(textViewSizePreferencesName, Context.MODE_PRIVATE);
        return textSizePreferences.getBoolean(bothLanguageKey, true);
    }

}
