package com.example.a4000essentialwordsbook1.Settings.SettingsDialogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.DialogFragment;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Settings.SettingListanerInterface.ReSetSettingsInterface;
import com.example.a4000essentialwordsbook1.Settings.SettingsPreferencesActivity;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;

public class AutoNighDayModeThemDialog extends DialogFragment implements View.OnClickListener{
    private AppCompatRadioButton dftRadioBtn, nightRadioBtn, dayRadioBtn;
    //private boolean nighFlag, dayFlag;
    private TextView rejectBtn, confirmBtn;
    private boolean dayModeFlag, nighModeFlag;

    private SharedPreferences autoNighModePreference;
    private final String autoNightPreferenceName = SettingsPreferencesNotes.SETTINGS_AUTO_NIGH_PREFERENCES;
    private final String autoNightModeKey = SettingsPreferencesNotes.AUTO_NIGHT_MODE_KEY;
    private final String lightModeKey = SettingsPreferencesNotes.LIGHT_MODE_KEY;
    private final String nightModeKey = SettingsPreferencesNotes.NIGHT_MODE_KEY;

    private ReSetSettingsInterface reStartSettingsInterFace;


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
                dftDeviceModeChecker();
                dftDeviceNightModeFun();
                break;
            case (R.id.night_mode_radio_button):
                nightModeFun();
                break;
            case (R.id.day_mode_radio_button):
                dayModeFun();
                break;
            case(R.id.auto_night_mode_confirm_settings_button):
                reStartSettingActivity();
                dismiss();
                //reStartSettingsInterFace.reStart();
                break;
            case (R.id.auto_night_mode_reject_button):
                dismiss();
                break;
        }
    }

    private void reStartSettingActivity(){
        preferencesValueSetter();
        themeMode();
        Intent intent = new Intent(requireActivity(), SettingsPreferencesActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    private void themeMode(){
        if (isNighBoxChecked()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
    private boolean isDarkMode(){
        autoNighModePreference = requireActivity().getSharedPreferences(autoNightPreferenceName, Context.MODE_PRIVATE);
        return autoNighModePreference.getBoolean(nightModeKey, false);
    }


    private void preferencesValueSetter(){
        autoNighModePreference = requireActivity().getSharedPreferences(autoNightPreferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditMode = autoNighModePreference.edit();
        prefEditMode.putBoolean(autoNightModeKey, isDefBoxChecked());
        prefEditMode.putBoolean(nightModeKey, isNighBoxChecked());
        prefEditMode.putBoolean(lightModeKey, isDayBoxChecked());
        prefEditMode.apply();
    }

    private boolean isDefBoxChecked(){return dftRadioBtn.isChecked();}
    private boolean isNighBoxChecked(){return nightRadioBtn.isChecked();}
    private boolean isDayBoxChecked(){return dayRadioBtn.isChecked();}

    private void dftDeviceNightModeFun(){
        dftRadioBtn.setChecked(true);
        nightRadioBtn.setChecked(false);
        dayRadioBtn.setChecked(false);
    }
    private void dftDeviceModeChecker(){
        int nightModeFlags =
                requireActivity().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                Toast.makeText(requireActivity(), "UI_MODE_NIGHT_YES", Toast.LENGTH_SHORT).show();
                //preferencesValueSetter(true, true, false);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                Toast.makeText(requireActivity(), "UI_MODE_NIGHT_UNDEFINED_No", Toast.LENGTH_SHORT).show();
                //preferencesValueSetter(false, false, true);
                break;
        }
    }


    private void nightModeFun(){
        dftRadioBtn.setChecked(false);
        nightRadioBtn.setChecked(true);
        dayRadioBtn.setChecked(false);
    }
    private void dayModeFun(){
        dftRadioBtn.setChecked(false);
        nightRadioBtn.setChecked(false);
        dayRadioBtn.setChecked(true);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        Activity activity = (Activity) context;
        reStartSettingsInterFace = (ReSetSettingsInterface) activity;
        super.onAttach(context);
    }

    private void dialogFindViewById(View view){
        dftRadioBtn = view.findViewById(R.id.auto_night_mode_default_radio_button);
        nightRadioBtn = view.findViewById(R.id.night_mode_radio_button);
        dayRadioBtn = view.findViewById(R.id.day_mode_radio_button);
        rejectBtn = view.findViewById(R.id.auto_night_mode_confirm_settings_button);
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
        return autoNighModePreference.getBoolean(autoNightModeKey, true);
    }
    private boolean isNightFlag(){
        autoNighModePreference = requireActivity().getSharedPreferences(autoNightPreferenceName, Context.MODE_PRIVATE);
        return autoNighModePreference.getBoolean(nightModeKey, false);
    }
    private boolean isDayFlag(){
        autoNighModePreference = requireActivity().getSharedPreferences(autoNightPreferenceName, Context.MODE_PRIVATE);
        return autoNighModePreference.getBoolean(lightModeKey, false);
    }

    private void thisDialogOnClickListener(){
        dftRadioBtn.setOnClickListener(this);
        nightRadioBtn.setOnClickListener(this);
        dayRadioBtn.setOnClickListener(this);
        rejectBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
    }
}
