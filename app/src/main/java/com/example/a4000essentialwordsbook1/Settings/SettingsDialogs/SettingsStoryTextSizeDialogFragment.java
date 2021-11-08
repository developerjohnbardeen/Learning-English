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
import androidx.fragment.app.DialogFragment;

import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;

public class SettingsStoryTextSizeDialogFragment extends DialogFragment implements View.OnClickListener{

    private NumberPicker stryNumPicker;
    private TextView stryConfirmBtn, stryRejectBtn;
    private TextView storySampleTextView;
    private int stryIntSize;

    SharedPreferences storyTextSizePreferences;
    private final String storyTextSizePreferencesName = SettingsPreferencesNotes.SETTINGS_STORY_TEXT_VIEW_SIZE_PREFERENCES;
    private final String storyTextSizeKey = SettingsPreferencesNotes.STORY_TEXT_VIEW_SIZE_KEY;

    public static SettingsStoryTextSizeDialogFragment newInstance(){
        SettingsStoryTextSizeDialogFragment storyTextSizeFragment = new SettingsStoryTextSizeDialogFragment();
        return storyTextSizeFragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_story_text_size_picker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogFindViewById(view);
        storyTextSizeNumPickerFun();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.story_txt_size_confirmation_settings_button):
                storyTextSizePreferencesValueSetter();
                dismiss();
                break;
            case (R.id.story_txt_size_settings_reject_button):
                dismiss();
                break;
        }
    }

    private void storyTextSizePreferencesValueSetter(){
        storyTextSizePreferences = requireActivity().getSharedPreferences(storyTextSizePreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor storySizeEdit = storyTextSizePreferences.edit();
        storySizeEdit.putInt(storyTextSizeKey, stryIntSize);
        storySizeEdit.apply();
    }


    private void storyTextSizeNumPickerFun(){
        stryNumPicker.setMinValue(12);
        stryNumPicker.setMaxValue(25);
        stryNumPicker.setValue(storyTxtSize());
        storyTextSizePickerListener();
    }

    private void storyTextSizePickerListener(){
        stryNumPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                stryIntSize = newVal;
                storySampleTextView.setTextSize(newVal);
            }
        });
    }



    private void dialogFindViewById(View view){
        stryNumPicker = view.findViewById(R.id.story_text_size_number_picker);
        storySampleTextView = view.findViewById(R.id.story_txt_sample_size_text_view);
        stryConfirmBtn = view.findViewById(R.id.story_txt_size_confirmation_settings_button);
        stryRejectBtn = view.findViewById(R.id.story_txt_size_settings_reject_button);
        valuesInitializer();
        thiDialogClickListener();
    }

    private void thiDialogClickListener(){
        stryConfirmBtn.setOnClickListener(this);
        stryRejectBtn.setOnClickListener(this);
    }

    private void valuesInitializer(){
        stryNumPicker.setValue(storyTxtSize());
        storySampleTextViewInitializer();
    }
    private void storySampleTextViewInitializer(){
        storySampleTextView.setTextSize(storyTxtSize());
    }
    private int storyTxtSize(){
        storyTextSizePreferences = requireActivity().getSharedPreferences(storyTextSizePreferencesName, Context.MODE_PRIVATE);
        return storyTextSizePreferences.getInt(storyTextSizeKey, 18);
    }

}
