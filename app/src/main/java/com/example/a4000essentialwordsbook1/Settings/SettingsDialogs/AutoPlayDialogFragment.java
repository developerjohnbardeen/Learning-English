package com.example.a4000essentialwordsbook1.Settings.SettingsDialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.a4000essentialwordsbook1.MarkedWords.ReviewWords.MainReviewMarkedWordActivity;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordSlideCardViewActivity;
import com.example.a4000essentialwordsbook1.Settings.SettingListanerInterface.AutoPlayInterface;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.AutoPlayNotes;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;
import com.google.android.material.card.MaterialCardView;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;


public class AutoPlayDialogFragment extends DialogFragment implements View.OnClickListener {
    private MaterialCardView autoPlayBtn, rejectBtn, saveAndPlayBtn;
    private TextView replyWordsTextView;

    private ImageView tooSlowImgView, slowImgView, normalImgView, fastImgView, tooFastImgView;
    private TextView tooSlowTextView, slowTextView, normalTextView, fastTextView, tooFastTextView;

    private RelativeLayout plyNxtUnitLayout;
    private LinearLayoutCompat extraDisplayTranslationLayout;
    private AppCompatCheckBox wrdPlyCheckBox, defPlyCheckBox, exmplPlyCheckBox;
    private AppCompatCheckBox plyAgainUnitCheckBox, plyNxtUnitCheckBox, displayTranslationCheckBox;
    private AppCompatCheckBox wordTrnslCheckBox, defTrnslCheckBox, exmplTrnslCheckBox;
    private AutoPlayInterface autoPlayInterface;
    private float plySpeedValue = 1f;
    private final static String keyActivity = AutoPlayNotes.ACTIVITY_NAME;
    private final static String dbInfoKey = AutoPlayNotes.DB_INFO_DIALOG_LIST_KEY;
    private String nameActivity;
    private final static String selectedTabActivity = AutoPlayNotes.SELECTED_TAB_ACTIVITY;
    private final static String detailedWordSlideActivity = AutoPlayNotes.WORD_SLIDE_CARD_VIEW_ACTIVITY;
    private final static String markedWordActivity = AutoPlayNotes.MARKED_WORD_ACTIVITY;
    private final static String mainReviewActivity = AutoPlayNotes.MAIN_REVIEW_MARKED_WORD_ACTIVITY;
    private final static String quizResultActivity = AutoPlayNotes.QUIZ_RESULT_ACTIVITY;
    private final static String speedMeterKey = ExtraNotes.SPEED_METER_KEY;
    private ArrayList<WordModel> wordList;

    private SharedPreferences autoPlayAudioPreferences;
    private final String autoPlayAudioPreferencesName = SettingsPreferencesNotes.AUTO_PLAY_AUDIO_DETAILED_WORD_PREFERENCES;
    private final String plyWordKey = SettingsPreferencesNotes.PLAY_DETAILED_WORD_KEY;
    private final String plyDefKey = SettingsPreferencesNotes.PLAY_DETAILED_DEFINITION_WORD_KEY;
    private final String plyExmplLKey = SettingsPreferencesNotes.PLAY_DETAILED_EXAMPLE_WORD_KEY;
    private final String plyAgainKey = SettingsPreferencesNotes.PLAY_AGAIN_AT_END_KEY;
    private final String plyNxtUnitKey = SettingsPreferencesNotes.PLAY_NEXT_UNIT_KEY;
    private final String speedPlayKey = SettingsPreferencesNotes.SPEED_PLAY_KEY;
    private final String dsplyTrnsltKey = SettingsPreferencesNotes.PLAY_DISPLAY_TRANSLATIONS_KEY;
    private final String dsplyWordKey = SettingsPreferencesNotes.PLAY_DISPLAY_WORD_KEY;
    private final String dsplyDefKey = SettingsPreferencesNotes.PLAY_DISPLAY_DEFINITION_KEY;
    private final String dsplyExmplKey = SettingsPreferencesNotes.PLAY_DISPLAY_EXAMPLE_KEY;





    public static AutoPlayDialogFragment quizResultNewInstance(String activityName,ArrayList<WordModel> modelLists, int[] dbDialogInfoList){
        AutoPlayDialogFragment autoPlayDialogFragment = new AutoPlayDialogFragment();
        Bundle autoPlayBundle = new Bundle();
        autoPlayBundle.putString(keyActivity, activityName);
        autoPlayBundle.putIntArray(dbInfoKey, dbDialogInfoList);
        autoPlayBundle.putParcelableArrayList("reviewList", modelLists);
        autoPlayDialogFragment.setArguments(autoPlayBundle);
        return autoPlayDialogFragment;
    }
    public static AutoPlayDialogFragment newInstance(String activityName, int[] dbDialogInfoList){
        AutoPlayDialogFragment autoPlayDialogFragment = new AutoPlayDialogFragment();
        Bundle autoPlayBundle = new Bundle();
        autoPlayBundle.putString(keyActivity, activityName);
        autoPlayBundle.putIntArray(dbInfoKey, dbDialogInfoList);
        autoPlayDialogFragment.setArguments(autoPlayBundle);
        return autoPlayDialogFragment;
    }
    public static AutoPlayDialogFragment newInstanceMarkedWordActivity(String activityName, ArrayList<WordModel> list, int[] dbDialogInfoList){
        AutoPlayDialogFragment fragment = new AutoPlayDialogFragment();
        Bundle autoPlayBundle = new Bundle();
        autoPlayBundle.putString(keyActivity, activityName);
        autoPlayBundle.putIntArray(dbInfoKey, dbDialogInfoList);
        autoPlayBundle.putParcelableArrayList("reviewList", list);
        fragment.setArguments(autoPlayBundle);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wordList = requireArguments().getParcelableArrayList("reviewList");
        nameActivity = requireArguments().getString(keyActivity);
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.auto_play_dialog_fragment, container, false);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findAutoPlayDialogFragmentViews(view);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.display_translation_dialog_check_box):
                displayTranslationsMoreOptions();
                break;
            case (R.id.auto_play_button):
                intentActivity(nameActivity);
                dismiss();
                break;
            case (R.id.auto_play_reject_button):
                dismiss();
                break;
            case (R.id.save_and_play_button):
                saveAndPlayFunctions();
                dismiss();
                break;

        }
    }




    private void saveAndPlayFunctions(){
        sharedPreferencesValueSetter();
        intentActivity(nameActivity);
    }

    private void intentActivity(String nameAct){
        Intent plyIntent;
        switch (nameAct) {
            case selectedTabActivity:
                plyIntent = new Intent(requireActivity(), WordSlideCardViewActivity.class);
                extraSetter(plyIntent);
                requireActivity().startActivity(plyIntent);
                break;
            case detailedWordSlideActivity:
                alreadyInDetailedWordSlide();
                break;
            case quizResultActivity:
            case markedWordActivity:
                plyIntent = new Intent(requireActivity(), MainReviewMarkedWordActivity.class);
                extraSetter(plyIntent);
                if (wordList.size() > 0){
                    requireActivity().startActivity(plyIntent);
                }else {
                    Toast.makeText(requireActivity(), "کلمه ای برای پخش وجود ندارد!", Toast.LENGTH_SHORT).show();
                }
                break;
            case mainReviewActivity:
                alreadyInMainReviewWord();
                break;
        }
    }
    private void extraSetter(Intent intent){
        String strDbNumber = ExtraNotes.DB_NUMBER;
        intent.putExtra(strDbNumber, dbNum());
        String strUnitNumber = ExtraNotes.UNIT_NUMBER;
        intent.putExtra(strUnitNumber, unitNum());
        String autoPlayFlagKey = ExtraNotes.AUTO_PLAY_FLAG_KEY;
        intent.putExtra(autoPlayFlagKey, true);
        String strWordId = ExtraNotes.WORD_ID;
        intent.putExtra(strWordId, 0);
        intent.putParcelableArrayListExtra("reviewList", wordList);
        intent.putExtra(speedMeterKey, plySpeedValue);
        String autoPlayFlagListKey = ExtraNotes.AUTO_PLAY_BOOLEAN_LIST;
        intent.putExtra(autoPlayFlagListKey, setCheckBoxesStatusFlags());
    }
    private boolean[] setCheckBoxesStatusFlags(){
        return new boolean[]{
                isAllAudioPlayed(),
                isWordPlyCheckBox(),
                isDefPlyCheckBox(),
                isExmplPlyCheckBox(),
                isPlyAgainCheckBox(),
                isPlyNxtCheckBox(),
                isAllTranslationDisplayedCheckBox(),
                isWordShwCheckBox(),
                isDefShwCheckBox(),
                isExmplShwCheckBox()};
    }

    private void displayTranslationsMoreOptions(){
        if (displayTranslationCheckBox.isChecked()){
            extraDisplayTranslationLayout.setVisibility(View.VISIBLE);
        }else {
            extraDisplayTranslationLayout.setVisibility(View.GONE);
        }
    }





    private void alreadyInDetailedWordSlide(){
        autoPlayInterface.autoPlayer(setCheckBoxesStatusFlags(), plySpeedValue);
    }

    private void alreadyInMainReviewWord(){
            autoPlayInterface.autoPlayer(setCheckBoxesStatusFlags(), plySpeedValue);
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            autoPlayInterface = (AutoPlayInterface) activity;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private int dbNum(){return dbInfoGetter()[0];}
    private int unitNum(){return dbInfoGetter()[1];}
    private int[] dbInfoGetter(){
        return requireArguments().getIntArray(dbInfoKey);
    }



    private void findAutoPlayDialogFragmentViews(View view){
        autoPlayBtn = view.findViewById(R.id.auto_play_button);
        rejectBtn = view.findViewById(R.id.auto_play_reject_button);
        replyWordsTextView = view.findViewById(R.id.replay_unit_txt_View);
        extraDisplayTranslationLayout = view.findViewById(R.id.extra_options_display_translations_parent_child_layout);
        saveAndPlayBtn = view.findViewById(R.id.save_and_play_button);
        plyNxtUnitLayout = view.findViewById(R.id.play_next_unit_dialog_child_layout);

        wrdPlyCheckBox = view.findViewById(R.id.auto_play_word_check_box);
        defPlyCheckBox = view.findViewById(R.id.auto_play_definition_check_box);
        exmplPlyCheckBox = view.findViewById(R.id.auto_play_example_check_box);
        plyAgainUnitCheckBox = view.findViewById(R.id.play_again_unit_check_box);
        plyNxtUnitCheckBox = view.findViewById(R.id.play_next_unit_check_box);
        displayTranslationCheckBox = view.findViewById(R.id.display_translation_dialog_check_box);
        wordTrnslCheckBox = view.findViewById(R.id.display_word_translation_check_box);
        defTrnslCheckBox = view.findViewById(R.id.display_definition_translation_check_box);
        exmplTrnslCheckBox = view.findViewById(R.id.display_example_translation_check_box);



        //speed meter imageView
        tooSlowImgView = view.findViewById(R.id.dialog_auto_play_speed_very_slow_image_view);
        slowImgView = view.findViewById(R.id.dialog_auto_play_speed_slow_image_view);
        normalImgView = view.findViewById(R.id.dialog_auto_play_speed_normal_image_view);
        fastImgView = view.findViewById(R.id.dialog_auto_play_speed_fast_image_view);
        tooFastImgView = view.findViewById(R.id.dialog_auto_play_speed_too_fast_image_view);

        //speed meter textView
        tooSlowTextView = view.findViewById(R.id.auto_play_dialog_too_slow_text_view);
        slowTextView = view.findViewById(R.id.auto_play_dialog_speed_slow_text_view);
        normalTextView = view.findViewById(R.id.dialog_auto_play_speed_normal_text_view);
        fastTextView = view.findViewById(R.id.dialog_auto_play_speed_fast_text_view);
        tooFastTextView = view.findViewById(R.id.dialog_auto_play_speed_too_fast_text_view);

        ComponentsValueInitializer();
        plyNextUnitLayoutVisibility(nameActivity);
        thisOnClickListener();

    }


    private void plyNextUnitLayoutVisibility(String activityName){
        final boolean markedWordFlag = activityName.equalsIgnoreCase(markedWordActivity);
        final boolean mainReviewFlag = activityName.equalsIgnoreCase(mainReviewActivity);
        final boolean quizResultFlag = activityName.equalsIgnoreCase(quizResultActivity);
        if (mainReviewFlag || markedWordFlag || quizResultFlag){
            plyNxtUnitLayout.setVisibility(View.GONE);
            replyWordsTextView.setText(R.string.replay_words_audio_file_str);
        }
    }
    private void thisOnClickListener(){
        autoPlayBtn.setOnClickListener(this);
        rejectBtn.setOnClickListener(this);
        saveAndPlayBtn.setOnClickListener(this);
        wrdPlyCheckBox.setOnClickListener(this);
        defPlyCheckBox.setOnClickListener(this);
        exmplPlyCheckBox.setOnClickListener(this);
        displayTranslationCheckBox.setOnClickListener(this);
        wordTrnslCheckBox.setOnClickListener(this);
        defTrnslCheckBox.setOnClickListener(this);
        exmplTrnslCheckBox.setOnClickListener(this);

        speedImageViewClickListener();

    }

    private void speedImageViewClickListener(){
        //preSpeedImageViewSize();

        tooSlowImageViewClickListener();
        slowImageViewClickListener();
        normalSpeedImageViewClickListener();
        fastImageViewClickListener();
        tooFastImageViewClickListener();
    }

    private View secondImageView;
    private TextView secondTxtView;


    private void tooSlowImageViewClickListener(){
        tooSlowImgView.setOnClickListener(view -> {
            if (view != secondImageView) {
                speedViewsAnimation(view, secondImageView);
                speedTextViewsAnimation(tooSlowTextView, secondTxtView);

                secondImageView = view;
                secondTxtView = tooSlowTextView;
                plySpeedValue = 0.5f;
            }
        });
    }
    private void slowImageViewClickListener(){
        slowImgView.setOnClickListener(view -> {
            if (view != secondImageView) {
                speedViewsAnimation(view, secondImageView);
                speedTextViewsAnimation(slowTextView, secondTxtView);

                secondImageView = view;
                secondTxtView = slowTextView;
                plySpeedValue = 0.75f;
            }
        });
    }
    private void normalSpeedImageViewClickListener(){
        normalImgView.setOnClickListener(view -> {
            if (view != secondImageView) {
                speedViewsAnimation(view, secondImageView);
                speedTextViewsAnimation(normalTextView, secondTxtView);

                secondImageView = view;
                secondTxtView = normalTextView;
                plySpeedValue = 1f;
            }
        });
    }
    private void fastImageViewClickListener(){
        fastImgView.setOnClickListener(view -> {
            if (view != secondImageView) {
                speedViewsAnimation(view, secondImageView);
                speedTextViewsAnimation(fastTextView, secondTxtView);

                secondImageView = view;
                secondTxtView = fastTextView;
                plySpeedValue = 1.5f;
            }
        });
    }
    private void tooFastImageViewClickListener(){
        tooFastImgView.setOnClickListener(view -> {
            if (view != secondImageView) {
                speedViewsAnimation(view, secondImageView);
                speedTextViewsAnimation(tooFastTextView, secondTxtView);

                secondImageView = view;
                secondTxtView = tooFastTextView;
                plySpeedValue = 2f;
            }
        });
    }


    private final float strtSize = 1f;
    private final float endSize = 1.2f;
    private final int animDuration = 150;

    private void speedViewsAnimation(View mainView, View secondView){
        increaseSpeedViewsSize(mainView);
        decreaseSpeedViewsSize(secondView);
    }
    private void increaseSpeedViewsSize(View view){
        final ValueAnimator increaseAnimator = ValueAnimator.ofFloat(strtSize, endSize);
        increaseAnimator.setDuration(animDuration);

        increaseAnimator.addUpdateListener(animation -> {
            view.setScaleX((float) animation.getAnimatedValue());
            view.setScaleY((float) animation.getAnimatedValue());
        });


        increaseAnimator.start();
    }
    private void decreaseSpeedViewsSize(View view){
        if (view != null) {
            final ValueAnimator decreaseAnimator = ValueAnimator.ofFloat(endSize, strtSize);
            decreaseAnimator.setDuration(animDuration);


            decreaseAnimator.addUpdateListener(animation -> {
                view.setScaleX((float) animation.getAnimatedValue());
                view.setScaleY((float) animation.getAnimatedValue());
            });


            decreaseAnimator.start();
        }
    }

    private void speedTextViewsAnimation(TextView mainTxtView, TextView secondTextView){
        increaseSpeedTextViewViewsSize(mainTxtView);
        decreaseSpeedTextViewsSize(secondTextView);
    }
    private void increaseSpeedTextViewViewsSize(TextView view){
        final ValueAnimator increaseAnimator = ValueAnimator.ofFloat(strtSize, endSize);
        increaseAnimator.setDuration(animDuration);

        view.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent));

        increaseAnimator.addUpdateListener(animation -> {
            view.setScaleX((float) animation.getAnimatedValue());
            view.setScaleY((float) animation.getAnimatedValue());
        });


        increaseAnimator.start();
    }
    private void decreaseSpeedTextViewsSize(TextView view){
        if (view != null) {
            final ValueAnimator decreaseAnimator = ValueAnimator.ofFloat(endSize, strtSize);
            decreaseAnimator.setDuration(animDuration);
            view.setTextColor(ContextCompat.getColor(requireActivity(),
                    R.color.setting_gray));

            decreaseAnimator.addUpdateListener(animation -> {
                view.setScaleX((float) animation.getAnimatedValue());
                view.setScaleY((float) animation.getAnimatedValue());
            });


            decreaseAnimator.start();
        }
    }






    private void ComponentsValueInitializer(){
        playWordCheckBoxInitializer();
        playDefCheckBoxInitializer();
        playExmplCheckBoxInitializer();
        playAgainCheckBoxInitializer();
        playNextUnitCheckBoxInitializer();
        speedMeterPlayInitializer();
        displayTranslationsCheckBoxInitializer();
        farsiTranslationVisibilityMoreOptionsLayout();
        displayWordFarsiCheckBoxInitializer();
        displayDefFarsiCheckBoxInitializer();
        displayExmplFardiCheckBoxInitializer();
    }
    private void playWordCheckBoxInitializer(){
        wrdPlyCheckBox.setChecked(isWordPly());
    }
    private void playDefCheckBoxInitializer(){
        defPlyCheckBox.setChecked(isDefPly());
    }
    private void playExmplCheckBoxInitializer(){
        exmplPlyCheckBox.setChecked(isExmplPly());
    }
    private void playAgainCheckBoxInitializer(){
        plyAgainUnitCheckBox.setChecked(isPlyAgain());
    }
    private void playNextUnitCheckBoxInitializer(){
        plyNxtUnitCheckBox.setChecked(isPlyNxt());
    }
    private void speedMeterPlayInitializer(){
        if (speedMeterVal() == 0.5f) {
            speedMeterPreInitializer(tooSlowImgView, tooSlowTextView, 0.5f);
        } else if (speedMeterVal() == 0.75f) {
            speedMeterPreInitializer(slowImgView, slowTextView, 0.75f);
        } else if (speedMeterVal() == 1.0f) {
            speedMeterPreInitializer(normalImgView, normalTextView, 1.0f);
        } else if (speedMeterVal() == 1.5f) {
            speedMeterPreInitializer(fastImgView, fastTextView, 1.5f);
        } else if (speedMeterVal() == 2.0f) {
            speedMeterPreInitializer(tooFastImgView, tooFastTextView, 2.0f);
        }
    }
    private void speedMeterPreInitializer(ImageView imageView, TextView textView, float speedVal){
        speedViewsAnimation(imageView, null);
        speedTextViewsAnimation(textView, null);

        secondImageView = imageView;
        secondTxtView = textView;
        plySpeedValue = speedVal;
    }
    private void farsiTranslationVisibilityMoreOptionsLayout(){
        if (isAllTranslationDisplayed()){
            extraDisplayTranslationLayout.setVisibility(View.VISIBLE);
        }else {
            extraDisplayTranslationLayout.setVisibility(View.GONE);
        }
    }
    private void displayTranslationsCheckBoxInitializer(){
        displayTranslationCheckBox.setChecked(isAllTranslationDisplayed());
    }
    private void displayWordFarsiCheckBoxInitializer(){
        wordTrnslCheckBox.setChecked(isWordShw());
    }
    private void displayDefFarsiCheckBoxInitializer(){
        defTrnslCheckBox.setChecked(isDefShw());
    }
    private void displayExmplFardiCheckBoxInitializer(){
        exmplTrnslCheckBox.setChecked(isExmplShw());
    }


    private void sharedPreferencesValueSetter(){
        autoPlayAudioPreferences = requireActivity().getSharedPreferences(autoPlayAudioPreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor autoPlyEdit = autoPlayAudioPreferences.edit();
        autoPlyEdit.putBoolean(plyWordKey, isWordPlyCheckBox());
        autoPlyEdit.putBoolean(plyDefKey, isDefPlyCheckBox());
        autoPlyEdit.putBoolean(plyExmplLKey, isExmplPlyCheckBox());
        autoPlyEdit.putBoolean(plyAgainKey, isPlyAgainCheckBox());
        autoPlyEdit.putBoolean(plyNxtUnitKey, isPlyNxtCheckBox());
        autoPlyEdit.putFloat(speedPlayKey, plySpeedValue);
        autoPlyEdit.putBoolean(dsplyTrnsltKey, isAllTranslationDisplayedCheckBox());
        autoPlyEdit.putBoolean(dsplyWordKey, isWordShwCheckBox());
        autoPlyEdit.putBoolean(dsplyDefKey, isDefShwCheckBox());
        autoPlyEdit.putBoolean(dsplyExmplKey, isExmplShwCheckBox());
        autoPlyEdit.apply();
    }


    private boolean isAllAudioPlayed(){return isWordPlyCheckBox() && isDefPlyCheckBox() && isExmplPlyCheckBox();}

    private boolean isWordPlyCheckBox(){return wrdPlyCheckBox.isChecked();}
    private boolean isDefPlyCheckBox(){return defPlyCheckBox.isChecked();}
    private boolean isExmplPlyCheckBox(){return exmplPlyCheckBox.isChecked();}
    private boolean isPlyAgainCheckBox(){return plyAgainUnitCheckBox.isChecked();}
    private boolean isPlyNxtCheckBox(){return plyNxtUnitCheckBox.isChecked();}
    private boolean isAllTranslationDisplayedCheckBox(){return displayTranslationCheckBox.isChecked();}
    private boolean isWordShwCheckBox(){return wordTrnslCheckBox.isChecked();}
    private boolean isDefShwCheckBox(){return defTrnslCheckBox.isChecked();}
    private boolean isExmplShwCheckBox(){return exmplTrnslCheckBox.isChecked();}


    private boolean isWordPly(){
        autoPlayAudioPreferences = requireActivity().getSharedPreferences(autoPlayAudioPreferencesName, Context.MODE_PRIVATE);
        return autoPlayAudioPreferences.getBoolean(plyWordKey, true);
    }
    private boolean isDefPly(){
        autoPlayAudioPreferences = requireActivity().getSharedPreferences(autoPlayAudioPreferencesName, Context.MODE_PRIVATE);
        return autoPlayAudioPreferences.getBoolean(plyDefKey, false);
    }
    private boolean isExmplPly(){
        autoPlayAudioPreferences = requireActivity().getSharedPreferences(autoPlayAudioPreferencesName, Context.MODE_PRIVATE);
        return autoPlayAudioPreferences.getBoolean(plyExmplLKey, false);
    }
    private boolean isPlyAgain(){
        autoPlayAudioPreferences = requireActivity().getSharedPreferences(autoPlayAudioPreferencesName, Context.MODE_PRIVATE);
        return autoPlayAudioPreferences.getBoolean(plyAgainKey, false);
    }
    private boolean isPlyNxt(){
        autoPlayAudioPreferences = requireActivity().getSharedPreferences(autoPlayAudioPreferencesName, Context.MODE_PRIVATE);
        return autoPlayAudioPreferences.getBoolean(plyNxtUnitKey, false);
    }
    private float speedMeterVal(){
        autoPlayAudioPreferences = requireActivity().getSharedPreferences(autoPlayAudioPreferencesName, Context.MODE_PRIVATE);
        return autoPlayAudioPreferences.getFloat(speedPlayKey, 1.0f);
    }
    private boolean isAllTranslationDisplayed(){
        autoPlayAudioPreferences = requireActivity().getSharedPreferences(autoPlayAudioPreferencesName, Context.MODE_PRIVATE);
        return autoPlayAudioPreferences.getBoolean(dsplyTrnsltKey, false);
    }
    private boolean isWordShw(){
        autoPlayAudioPreferences = requireActivity().getSharedPreferences(autoPlayAudioPreferencesName, Context.MODE_PRIVATE);
        return autoPlayAudioPreferences.getBoolean(dsplyWordKey, false);
    }
    private boolean isDefShw(){
        autoPlayAudioPreferences = requireActivity().getSharedPreferences(autoPlayAudioPreferencesName, Context.MODE_PRIVATE);
        return autoPlayAudioPreferences.getBoolean(dsplyDefKey, false);
    }
    private boolean isExmplShw(){
        autoPlayAudioPreferences = requireActivity().getSharedPreferences(autoPlayAudioPreferencesName, Context.MODE_PRIVATE);
        return autoPlayAudioPreferences.getBoolean(dsplyExmplKey, false);
    }
}
