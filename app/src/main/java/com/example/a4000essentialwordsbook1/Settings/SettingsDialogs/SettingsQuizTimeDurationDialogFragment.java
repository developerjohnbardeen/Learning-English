package com.example.a4000essentialwordsbook1.Settings.SettingsDialogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.DialogFragment;

import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;
import com.google.android.material.card.MaterialCardView;

public class SettingsQuizTimeDurationDialogFragment extends DialogFragment implements View.OnClickListener {

    private MaterialCardView cnfmBtn, rjctBtn, cclTimerBtn;
    private NumberPicker numberPicker;
    private int intTime;
    private AppCompatCheckBox quzTimerCheckBox;

    private SharedPreferences quizDurationPreference;
    private final String quizDurationPreferenceName = SettingsPreferencesNotes.SETTINGS_QUIZ_TIME_DURATION_PREFERENCE;
    private final String quizDurationKey = SettingsPreferencesNotes.QUIZ_TIME_DURATION_KEY;
    private final String cancelTimerKey = SettingsPreferencesNotes.CANCEL_QUIZ_TIMER_KEY;

    public static SettingsQuizTimeDurationDialogFragment newInstance() {
        SettingsQuizTimeDurationDialogFragment quizDurationFragment = new SettingsQuizTimeDurationDialogFragment();
        return quizDurationFragment;
    }


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_quiz_timer_duration , container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogFindViewById(view);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.quiz_timer_duration_confirmation_settings_button):
                preferencesTimerDuration();
                dismiss();
            break;
            case (R.id.quiz_timer_duration_settings_reject_button):
                dismiss();
                break;
            case (R.id.quiz_timer_duration_settings_cancel_timer_button):
                cancelTimerPreference();
                dismiss();
                break;
        }
    }






    private void preferencesTimerDuration(){
        quizDurationPreference = requireActivity().getSharedPreferences(quizDurationPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor durationEdit = quizDurationPreference.edit();
        durationEdit.putInt(quizDurationKey, intTime);
        durationEdit.putBoolean(cancelTimerKey, false);
        durationEdit.apply();
    }
    private void cancelTimerPreference(){
        quizDurationPreference = requireActivity().getSharedPreferences(quizDurationPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor cancelTimerEdit = quizDurationPreference.edit();
        cancelTimerEdit.putBoolean(cancelTimerKey, true);
        cancelTimerEdit.apply();
    }

    private void dialogFindViewById(View view){
        numberPicker = view.findViewById(R.id.quiz_duration_number_picker);
        cnfmBtn = view.findViewById(R.id.quiz_timer_duration_confirmation_settings_button);
        rjctBtn = view.findViewById(R.id.quiz_timer_duration_settings_reject_button);
        cclTimerBtn = view.findViewById(R.id.quiz_timer_duration_settings_cancel_timer_button);
        quzTimerCheckBox = requireActivity().findViewById(R.id.quiz_timer_duration_check_box);

        timerDurationFunctions();
        thisDialogOnClickListener();
    }
    private void timerDurationFunctions(){
        numberPicker.setMinValue(5);
        numberPicker.setMaxValue(15);
        numberPicker.setValue(quizTime());

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                intTime = newVal;
            }
        });
    }
    private int quizTime(){
        quizDurationPreference = requireActivity().getSharedPreferences(quizDurationPreferenceName, Context.MODE_PRIVATE);
        return quizDurationPreference.getInt(quizDurationKey, 7);
    }

    private void thisDialogOnClickListener(){
        cnfmBtn.setOnClickListener(this);
        rjctBtn.setOnClickListener(this);
        cclTimerBtn.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        quizTimerCheckBoxValueSetter();
    }
    private void quizTimerCheckBoxValueSetter(){
        quzTimerCheckBox.setChecked(!isTimerCancel());
    }
    private boolean isTimerCancel(){
        quizDurationPreference = requireActivity().getSharedPreferences(quizDurationPreferenceName, Context.MODE_PRIVATE);
        return quizDurationPreference.getBoolean(cancelTimerKey, false);
    }
}
