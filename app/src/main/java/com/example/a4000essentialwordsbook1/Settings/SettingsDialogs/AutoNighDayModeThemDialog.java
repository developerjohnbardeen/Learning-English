package com.example.a4000essentialwordsbook1.Settings.SettingsDialogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.DialogFragment;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;

public class AutoNighDayModeThemDialog extends DialogFragment implements View.OnClickListener{
    private AppCompatRadioButton dftRadioBtn, nightRadioBtn, dayRadioBtn;
    private final boolean trueFlag = true;
    private final boolean falseFlag = false;
    private TextView rejectBtn, confirmBtn;

    private SharedPreferences autoNighModePreference;
    private SharedPreferences.Editor prefEditMode;
    private final String autoNightPreferenceName = SettingsPreferencesNotes.SETTINGS_AUTO_NIGH_PREFERENCES;
    private final String autoNightModeKey = SettingsPreferencesNotes.AUTO_NIGHT_MODE_KEY;
    private final String lightModeKey = SettingsPreferencesNotes.LIGHT_MODE_KEY;
    private final String nightModeKey = SettingsPreferencesNotes.NIGHT_MODE_KEY;


    public static AutoNighDayModeThemDialog newInstance(){
        AutoNighDayModeThemDialog dialogFragment = new AutoNighDayModeThemDialog();
        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_auto_night_day_theme, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        dialogFindViewById(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.auto_night_mode_default_radio_button):
                dftDeviceNightModeFun();
                break;
            case (R.id.night_mode_radio_button):
                nightModeFun();
                break;
            case (R.id.day_mode_radio_button):
                dayModeFun();
                break;
            case(R.id.auto_night_mode_settings_button):
                try {
                    prefEditMode.apply();
                }catch (Exception e){
                    e.printStackTrace();
                }
                dismiss();
                break;
            case (R.id.auto_night_mode_reject_button):
                dismiss();
                break;
        }
    }



    private void preferencesValueSetter(boolean dftFlag, boolean nightFlag, boolean dayFlag){
        autoNighModePreference = requireActivity().getSharedPreferences(autoNightPreferenceName, Context.MODE_PRIVATE);
        prefEditMode = autoNighModePreference.edit();
        prefEditMode.putBoolean(autoNightModeKey, dftFlag);
        prefEditMode.putBoolean(nightModeKey, nightFlag);
        prefEditMode.putBoolean(lightModeKey, dayFlag);
    }

    private void dftDeviceNightModeFun(){
        dftRadioBtn.setChecked(trueFlag);
        nightRadioBtn.setChecked(falseFlag);
        dayRadioBtn.setChecked(falseFlag);
        preferencesValueSetter(trueFlag, falseFlag, falseFlag);
    }
    private void dftDeviceModeChecker(){
        int nightModeFlags =
                requireActivity().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                preferencesValueSetter(trueFlag, falseFlag, falseFlag);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                preferencesValueSetter(falseFlag, falseFlag, trueFlag);
                break;
        }
    }


    private void nightModeFun(){
        dftRadioBtn.setChecked(falseFlag);
        nightRadioBtn.setChecked(trueFlag);
        dayRadioBtn.setChecked(falseFlag);
        preferencesValueSetter(falseFlag, trueFlag, falseFlag);
    }
    private void dayModeFun(){
        dftRadioBtn.setChecked(falseFlag);
        nightRadioBtn.setChecked(falseFlag);
        dayRadioBtn.setChecked(trueFlag);
        preferencesValueSetter(falseFlag, falseFlag, trueFlag);
    }




    private void dialogFindViewById(View view){
        dftRadioBtn = view.findViewById(R.id.auto_night_mode_default_radio_button);
        nightRadioBtn = view.findViewById(R.id.night_mode_radio_button);
        dayRadioBtn = view.findViewById(R.id.day_mode_radio_button);
        rejectBtn = view.findViewById(R.id.auto_night_mode_settings_button);
        confirmBtn = view.findViewById(R.id.auto_night_mode_reject_button);
        radioButtonInitialize();
        thisDialogOnClickListener();
    }

    private void radioButtonInitialize(){
        dftRadioBtn.setChecked(isDftFlag());
        nightRadioBtn.setChecked(isNightFlag());
        dayRadioBtn.setChecked(isDayFlag());
    }
    private boolean isDftFlag(){
        autoNighModePreference = requireActivity().getSharedPreferences(autoNightPreferenceName, Context.MODE_PRIVATE);
        return autoNighModePreference.getBoolean(autoNightModeKey, trueFlag);
    }
    private boolean isNightFlag(){
        autoNighModePreference = requireActivity().getSharedPreferences(autoNightPreferenceName, Context.MODE_PRIVATE);
        return autoNighModePreference.getBoolean(nightModeKey, falseFlag);
    }
    private boolean isDayFlag(){
        autoNighModePreference = requireActivity().getSharedPreferences(autoNightPreferenceName, Context.MODE_PRIVATE);
        return autoNighModePreference.getBoolean(lightModeKey, falseFlag);
    }

    private void thisDialogOnClickListener(){
        dftRadioBtn.setOnClickListener(this);
        nightRadioBtn.setOnClickListener(this);
        dayRadioBtn.setOnClickListener(this);
        rejectBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
    }


}
