package com.example.a4000essentialwordsbook1.MarkedWords.ReviewWords;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.a4000essentialwordsbook1.Linsteners.AudioPlayerListener;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordDetailedInterfaces.EditedTranslationInterface;
import com.example.a4000essentialwordsbook1.SpannableString.TextViewBolder;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.FontTypeFiles.GlobalFonts;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.jackandphantom.circularimageview.CircleImage;
import com.scwang.smartrefresh.header.material.CircleImageView;


public class FragmentReviewWord extends Fragment implements View.OnClickListener , EditedTranslationInterface {
    private WordModel reviewModel;
    private CardView tenlstCardView, imgAndWordCardView;
    private CardView plyWordUsingCard;
    private ImageView plyWordUsingImage;
    private RoundedImageView tvImage;
    private LinearLayoutCompat imgLayout;
    private TextView txtWord, txtPhonetic, txtWordTranslate;
    private ImageButton plytWordBtn, removeItemBtn;
    private ImageButton imgBtnTranslate, imgTrnBtn;
    private TextView txtDefinition, txtTranslateDef, txtExample, txtTranslateExmpl;
    private TextView txtDefTitle, txtDefDivider, txtExmplTitle, txtExmplDivider;
    private final int startAnimationInt = 300;
    private final int endAnimationInt = 300;
    private boolean flag = false;
    private boolean shwAtAll, shwWordFlag, shwDefFlag, shwExmplFlag;
    private boolean[] shwFarsiFlags;
    private RemoveCardItem removeCardItem;
    private RelativeLayout exampleRelativeLayout, definitionRelativeLayout;
    private AudioPlayerListener audioPlayerListener;

    private final int[] perFontList = GlobalFonts.perFontList;
    private final int[] engFontList = GlobalFonts.engFontList;

    private SharedPreferences displayPreference;
    private final String displayTranslationsPreferencesName = SettingsPreferencesNotes.SETTINGS_DISPLAY_TRANSLATION_PREFERENCES;
    private final String dsplyTrnsltKey = SettingsPreferencesNotes.DISPLAY_IN_THE_BEGINNING_KEY;
    private final String dsplyWordKey = SettingsPreferencesNotes.DISPLAY_WORD_TRANSLATION_KEY;
    private final String dsplyDefKey = SettingsPreferencesNotes.DISPLAY_DEFINITION_TRANSLATION_KEY;
    private final String dsplyExmplKey = SettingsPreferencesNotes.DISPLAY_EXAMPLE_TRANSLATION_KEY;

    private SharedPreferences fontTypePreferences;
    private SharedPreferences textSizePreferences;
    private final String fontTypePreferencesName = SettingsPreferencesNotes.SETTINGS_TEXT_FONT_TYPE_PREFERENCES;
    private final String textViewSizePreferencesName = SettingsPreferencesNotes.SETTINGS_TEXT_VIEW_SIZE_PREFERENCES;
    private final String engListPositionKey = SettingsPreferencesNotes.ENGLISH_LIST_POSITION_KEY;
    private final String perListPositionKey = SettingsPreferencesNotes.PERSIAN_LIST_POSITION_KEY;
    private final String txtViewSizeKey = SettingsPreferencesNotes.PERSIAN_TEXT_VIEW_SIZE_KEY;
    private final String engTxtViewSizeKey = SettingsPreferencesNotes.ENGLISH_TEXT_VIEW_SIZE_KEY;

    public static FragmentReviewWord newInstance(WordModel model, boolean[] shwFarsiFlags){
        FragmentReviewWord fragmentReviewWord = new FragmentReviewWord();
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", model);
        bundle.putBooleanArray("shwFarsiFlags", shwFarsiFlags);
        fragmentReviewWord.setArguments(bundle);
        return fragmentReviewWord;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviewModel = requireArguments().getParcelable("model");
        shwFarsiFlags = requireArguments().getBooleanArray("shwFarsiFlags");
        displayFlagsValueSetter(shwFarsiFlags);
    }



    private void displayFlagsValueSetter(boolean[] shwFarsiFlags){
        shwAtAll = shwFarsiFlags[0];
        shwWordFlag = shwFarsiFlags[1];
        shwDefFlag = shwFarsiFlags[2];
        shwExmplFlag = shwFarsiFlags[3];
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_word_fragment , container, false);
        viewsFinderById(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        valuesGetter(reviewModel);
        thisOnClickListener();
    }


    private void valuesGetter(WordModel wordReviewList){

        int image = wordReviewList.getWordImage();
        int id = wordReviewList.getId();
        String word = wordReviewList.getWord();
        String phonetic = wordReviewList.getPhonetic();
        String wordTranslate = wordReviewList.getTranslateWord();
        String definition = wordReviewList.getDefinition();
        String translateDef = wordReviewList.getTranslateDef();
        String example = wordReviewList.getExample();
        String translateExmpl = wordReviewList.getTranslateExmpl();


        valuesSetter(image, id, word, phonetic, wordTranslate, definition,
                translateDef, example, translateExmpl);

    }

    private void valuesSetter(int image, int id, String word, String phonetic,
                              String wordTranslate, String definition, String translateDef,
                              String example, String translateExmpl){
        tvImage.setImageResource(image);
        txtWord.setText(word);
        txtPhonetic.setText(phonetic);
        txtWordTranslate.setText(wordTranslate);
        txtDefinition.setText(definition);
        txtTranslateDef.setText(translateDef);
        txtExample.setText(example);
        txtTranslateExmpl.setText(translateExmpl);

        stringBolder(txtDefinition, txtExample, definition , example, word);
    }

    private void stringBolder(TextView txtDefinition, TextView txtExample,
                              String defWord, String exmplWord, String word){

        TextViewBolder bolder = new TextViewBolder(requireActivity());
        bolder.defTextBolder(txtDefinition, defWord, word);
        bolder.exmplTextBolder(txtExample, exmplWord, word);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.main_review_slide_word_detail_all_translate_btn):
            case (R.id.word_review_detailed_translate_btn):
                translateTextViewVisibility();
                break;
            case (R.id.remove_review_word_item):
                removeCardItem.removeItem();
                break;
            case (R.id.word_review_detailed_ply_audio_btn):
            case (R.id.main_review_img_and_word_Card_View):
            case (R.id.word_review_detailed_image):
                audioPlayerListener.wordAudioPlayer(false);
                break;
            case (R.id.word_review_definition_layout):
            case (R.id.word_review_detailed_definition):
                audioPlayerListener.definitionAudioPlayer(false);
                break;
            case (R.id.word_review_example_layout):
            case (R.id.word_review_detailed_example):
                audioPlayerListener.exampleAudioPlayer(false);
                break;
        }
    }


    private void translateTextViewVisibility(){
        if (!flag){
            fadeBackViewAnimation();
            flag = true;
        }else {
            fadeOutViewAnimation();
            flag = false;
        }
    }


    private void fadeOutViewAnimation(){
        inVisibilitySetterTranslateWordAnimation();
        visibilitySetterTranslationImageButton();
        inVisibilitySetterTranslateDefinitionAnimation();
        inVisibilitySetterTranslateExampleAnimation();
    }
    private void fadeBackViewAnimation(){
        if (isWordShwPref()) {
            visibilitySetterTranslateWordAnimation();
            inVisibilitySetterTranslationImageButton();}
        if (isDefShwPref()) {visibilitySetterTranslateDefinitionAnimation();}
        if (isExmplShwPref()) {visibilitySetterTranslateExampleAnimation();}
    }



    private void visibilitySetterTranslateWordAnimation(){
        txtWordTranslate.animate()
                .y(txtPhonetic.getHeight() + heightPlus())
                .alpha(1.0f)
                .setDuration(startAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        txtWordTranslate.setVisibility(View.VISIBLE);
                    }
                }).start();
    }
    private void visibilitySetterTranslateDefinitionAnimation(){
        txtTranslateDef.animate()
                .y(txtDefinition.getHeight() + heightPlus())
                .alpha(1.0f)
                .setDuration(startAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        txtTranslateDef.setVisibility(View.VISIBLE);
                    }
                }).start();
    }
    private void visibilitySetterTranslateExampleAnimation(){
        txtTranslateExmpl.animate()
                .y(txtExample.getHeight() + heightPlus())
                .alpha(1.0f)
                .setDuration(startAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        txtTranslateExmpl.setVisibility(View.VISIBLE);
                    }}).start();
    }
    private void visibilitySetterTranslationImageButton(){
        imgTrnBtn.animate()
                .y(txtPhonetic.getHeight() + heightPlus())
                .alpha(1.0f)
                .setDuration(startAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        imgTrnBtn.setVisibility(View.VISIBLE);
                    }
                }).start();
    }


    private int heightPlus(){
        int heightPlus = Resources.getSystem().getDisplayMetrics().heightPixels;
        return (int)(heightPlus * 0.02);
    }

    private void inVisibilitySetterTranslateWordAnimation(){
        txtWordTranslate.animate()
                .translationY(txtWordTranslate.getHeight())
                .alpha(0.0f)
                .setDuration(endAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        txtWordTranslate.setVisibility(View.INVISIBLE);
                    }
                }).start();
    }
    private void inVisibilitySetterTranslateDefinitionAnimation(){
        txtTranslateDef.animate()
                .translationY(txtTranslateDef.getHeight())
                .alpha(0.0f)
                .setDuration(endAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        txtTranslateDef.setVisibility(View.GONE);
                    }
                }).start();
    }
    private void inVisibilitySetterTranslateExampleAnimation(){
        txtTranslateExmpl.animate()
                .translationY(txtTranslateExmpl.getHeight())
                .alpha(0.0f)
                .setDuration(endAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        txtTranslateExmpl.setVisibility(View.GONE);
                    }
                }).start();
    }
    private void inVisibilitySetterTranslationImageButton(){
        imgTrnBtn.animate()
                .translationY(-imgTrnBtn.getHeight())
                .alpha(0.0f)
                .setDuration(endAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        imgTrnBtn.setVisibility(View.GONE);
                    }
                }).start();
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }




    public interface RemoveCardItem{
        void removeItem();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        try {
            removeCardItem = (RemoveCardItem) activity;
            audioPlayerListener = (AudioPlayerListener) activity;
        }catch (Exception e){
            Toast.makeText(activity, "An Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        onPauseFun();
    }

    private void onPauseFun(){
        flag = false;
        fadeOutViewAnimation();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        textViewsVisibilityFunctions();
        displayTranslationFunctions();
    }
    private void displayTranslationFunctions(){
        if (shwAtAll) {
            if (shwWordFlag) {
                visibilitySetterTranslateWordAnimation();
                inVisibilitySetterTranslationImageButton();
            }
            if (shwDefFlag) {
                visibilitySetterTranslateDefinitionAnimation();
            }
            if (shwExmplFlag) {
                visibilitySetterTranslateExampleAnimation();
            }
        }
    }


    private void textViewsVisibilityFunctions(){
        if (isEntranceDisplayFarsiPref()) {
            entranceFarsiDisplay();
        }else {
            allFarsiTextViewsHasVisibility();
        }
    }

    private void allFarsiTextViewsHasVisibility(){
        if (!isWordShwPref()){txtWordTranslate.setVisibility(View.GONE);}
        if (!isDefShwPref()){txtTranslateDef.setVisibility(View.GONE);}
        if (!isExmplShwPref()){txtTranslateExmpl.setVisibility(View.GONE);}
    }

    private void entranceFarsiDisplay(){
        if(isWordShwPref()){wordTranslationTxtViewVisibility();}
        if(isDefShwPref()){
            inVisibilitySetterTranslateDefinitionAnimation();
            visibilitySetterTranslateDefinitionAnimation();
        }
        if(isExmplShwPref()){
            inVisibilitySetterTranslateExampleAnimation();
            visibilitySetterTranslateExampleAnimation();
        }
    }
    private void wordTranslationTxtViewVisibility(){
        inVisibilitySetterTranslateWordAnimation();
        visibilitySetterTranslateWordAnimation();
        inVisibilitySetterTranslationImageButton();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }




    private void viewsFinderById(View view){
        tvImage = view.findViewById(R.id.word_review_detailed_image);

        txtWord = view.findViewById(R.id.word_review_detailed_name);
        txtPhonetic = view.findViewById(R.id.word_review_detailed_phonetic);
        txtDefinition = view.findViewById(R.id.word_review_detailed_definition);
        txtExample = view.findViewById(R.id.word_review_detailed_example);
        txtDefTitle = view.findViewById(R.id.word_review_detailed_de);
        txtDefDivider = view.findViewById(R.id.word_review_detailed_def_divider);
        txtExmplTitle = view.findViewById(R.id.word_review_detailed_ex);
        txtExmplDivider = view.findViewById(R.id.word_review_detailed_ex_divider);


        txtWordTranslate = view.findViewById(R.id.word_review_translate_detailed);
        txtTranslateDef = view.findViewById(R.id.word_review_detailed_definition_translates);
        txtTranslateExmpl = view.findViewById(R.id.word_review_detailed_example_translate);


        removeItemBtn = view.findViewById(R.id.remove_review_word_item);
        plytWordBtn = view.findViewById(R.id.word_review_detailed_ply_audio_btn);
        imgLayout = view.findViewById(R.id.profile_img_container);
        exampleRelativeLayout = view.findViewById(R.id.word_review_example_layout);
        definitionRelativeLayout = view.findViewById(R.id.word_review_definition_layout);
        imgBtnTranslate = requireActivity().findViewById(R.id.main_review_slide_word_detail_all_translate_btn);
        imgAndWordCardView = view.findViewById(R.id.main_review_img_and_word_Card_View);
        imgTrnBtn = view.findViewById(R.id.word_review_detailed_translate_btn);
        imgLayout.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        TextViewsSizeSetterFunctions();
        textViewsValueSetterFunctions();
    }
    private void thisOnClickListener(){
        removeItemBtn.setOnClickListener(this);
        imgTrnBtn.setOnClickListener(this);
        plytWordBtn.setOnClickListener(this);
        txtDefinition.setOnClickListener(this);
        txtExample.setOnClickListener(this);
        exampleRelativeLayout.setOnClickListener(this);
        definitionRelativeLayout.setOnClickListener(this);
        imgBtnTranslate.setOnClickListener(this);
        tvImage.setOnClickListener(this);
        imgAndWordCardView.setOnClickListener(this);
    }


    private void textViewsValueSetterFunctions(){
        textViewsFontSetterFunctions();
        TextViewsSizeSetterFunctions();
    }

    private void textViewsFontSetterFunctions(){
        engTxtViewFontSetter();
        perTxtViewFontSetter();
    }
    private void engTxtViewFontSetter(){
        txtWord.setTypeface(engTypeFace());
        txtPhonetic.setTypeface(engTypeFace());
        txtDefinition.setTypeface(engTypeFace());
        txtExample.setTypeface(engTypeFace());
        txtDefTitle.setTypeface(engTypeFace());
        txtDefDivider.setTypeface(engTypeFace());
        txtExmplTitle.setTypeface(engTypeFace());
        txtExmplDivider.setTypeface(engTypeFace());
    }
    private void perTxtViewFontSetter(){
        txtWordTranslate.setTypeface(perTypeFace());
        txtTranslateDef.setTypeface(perTypeFace());
        txtTranslateExmpl.setTypeface(perTypeFace());
    }


    private Typeface engTypeFace(){
        return ResourcesCompat.getFont(requireActivity(), engFontFace());
    }
    private int engFontFace(){
        return engFontList[engFontVal()];
    }


    private Typeface perTypeFace(){
        return ResourcesCompat.getFont(requireActivity(), perFontFace());
    }
    private int perFontFace(){
        return perFontList[perFontVal()];
    }


    private void TextViewsSizeSetterFunctions(){
        engTxtSizeSetter();
        perTxtSizeSetter();
    }
    private void engTxtSizeSetter(){
        txtWord.setTextSize(engTxtSize());
        txtPhonetic.setTextSize(engTxtSize());
        txtDefinition.setTextSize(engTxtSize());
        txtExample.setTextSize(engTxtSize());
        txtDefTitle.setTextSize(engTxtSize());
        txtDefDivider.setTextSize(engTxtSize());
        txtExmplTitle.setTextSize(engTxtSize());
        txtExmplDivider.setTextSize(engTxtSize());
    }
    private void perTxtSizeSetter(){
        txtWordTranslate.setTextSize(perTxtSize());
        txtTranslateDef.setTextSize(perTxtSize());
        txtTranslateExmpl.setTextSize(perTxtSize());
    }

    @Override
    public void translationEditor(String wrdTrnslt, String defTrnslt, String exmplTrnslt) {
        txtWordTranslate.setText(wrdTrnslt);
        txtTranslateDef.setText(defTrnslt);
        txtTranslateExmpl.setText(exmplTrnslt);
    }





    private int engFontVal(){
        fontTypePreferences = requireActivity().getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getInt(engListPositionKey, 1);
    }
    private int perFontVal(){
        fontTypePreferences = requireActivity().getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getInt(perListPositionKey, 1);
    }
    private int engTxtSize(){
        textSizePreferences = requireActivity().getSharedPreferences(textViewSizePreferencesName, Context.MODE_PRIVATE);
        return textSizePreferences.getInt(engTxtViewSizeKey, 18);
    }
    private int perTxtSize(){
        textSizePreferences = requireActivity().getSharedPreferences(textViewSizePreferencesName, Context.MODE_PRIVATE);
        return textSizePreferences.getInt(txtViewSizeKey, 18);
    }


    private boolean isEntranceDisplayFarsiPref(){
        displayPreference = requireActivity().getSharedPreferences(displayTranslationsPreferencesName, Context.MODE_PRIVATE);
        return displayPreference.getBoolean(dsplyTrnsltKey, false);
    }

    private boolean isWordShwPref(){
        displayPreference = requireActivity().getSharedPreferences(displayTranslationsPreferencesName, Context.MODE_PRIVATE);
        return displayPreference.getBoolean(dsplyWordKey, true);
    }
    private boolean isDefShwPref(){
        displayPreference = requireActivity().getSharedPreferences(displayTranslationsPreferencesName, Context.MODE_PRIVATE);
        return displayPreference.getBoolean(dsplyDefKey, false);
    }
    private boolean isExmplShwPref(){
        displayPreference = requireActivity().getSharedPreferences(displayTranslationsPreferencesName, Context.MODE_PRIVATE);
        return displayPreference.getBoolean(dsplyExmplKey, true);
    }
}
