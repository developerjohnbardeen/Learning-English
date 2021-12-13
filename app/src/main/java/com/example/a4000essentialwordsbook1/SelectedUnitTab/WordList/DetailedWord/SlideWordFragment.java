package com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.a4000essentialwordsbook1.Linsteners.AudioPlayerListener;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordDetailedInterfaces.DisplayTranslationInterface;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordDetailedInterfaces.EditedTranslationInterface;
import com.example.a4000essentialwordsbook1.Settings.SettingListanerInterface.AddNoteInterFace;
import com.example.a4000essentialwordsbook1.SpannableString.TextViewBolder;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.FontTypeFiles.GlobalFonts;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;
import java.io.File;
import java.util.ArrayList;


public class SlideWordFragment extends Fragment implements View.OnClickListener,
        EditedTranslationInterface, DisplayTranslationInterface, AddNoteInterFace {


    private WordModel modelList;
    private final int startAnimationInt = 300;
    private final int endAnimationInt = 300;
    private ImageView imageWord;
    private CardView plyWordUingCardView, plyDefUingCardView, plyExmplUingCardView;
    private CardView addedNoteCardView;
    private TextView addedNoteTextView;
    private TextView definition, translateDefinition,
            example, translateExample, word, wordPhonetic, wordTranslate, defDivider;
    private RelativeLayout defRelLayout, exmplRelLayout;
    private TextView defTitle, exmplTitle;
    private ImageButton imgTrnBtn;
    private boolean flag = false;
    private boolean autoTrnFlag = false;
    private boolean[] shwFarsiFlags;
    private boolean shwAtAll, shwWordFlag, shwDefFlag, shwExmplFlag;
    private ArrayList<Integer> markedFlagList;
    private AudioPlayerListener audioPlayerListener;
    private int position;
    private SharedPreferences displayPreference;
    private final String displayTranslationsPreferencesName = SettingsPreferencesNotes.SETTINGS_DISPLAY_TRANSLATION_PREFERENCES;
    private final String dsplyTrnsltKey = SettingsPreferencesNotes.DISPLAY_IN_THE_BEGINNING_KEY;
    private final String dsplyWordKey = SettingsPreferencesNotes.DISPLAY_WORD_TRANSLATION_KEY;
    private final String dsplyDefKey = SettingsPreferencesNotes.DISPLAY_DEFINITION_TRANSLATION_KEY;
    private final String dsplyExmplKey = SettingsPreferencesNotes.DISPLAY_EXAMPLE_TRANSLATION_KEY;


    private final int[] perFontList = GlobalFonts.perFontList;
    private final int[] engFontList = GlobalFonts.engFontList;


    private SharedPreferences fontTypePreferences;
    private SharedPreferences textSizePreferences;
    private final String fontTypePreferencesName = SettingsPreferencesNotes.SETTINGS_TEXT_FONT_TYPE_PREFERENCES;
    private final String textViewSizePreferencesName = SettingsPreferencesNotes.SETTINGS_TEXT_VIEW_SIZE_PREFERENCES;
    private final String engListPositionKey = SettingsPreferencesNotes.ENGLISH_LIST_POSITION_KEY;
    private final String perListPositionKey = SettingsPreferencesNotes.PERSIAN_LIST_POSITION_KEY;
    private final String txtViewSizeKey = SettingsPreferencesNotes.PERSIAN_TEXT_VIEW_SIZE_KEY;
    private final String engTxtViewSizeKey = SettingsPreferencesNotes.ENGLISH_TEXT_VIEW_SIZE_KEY;
    private final String engTxtBolderKey = SettingsPreferencesNotes.ENGLISH_TEXT_BOLDER_KEY;
    private final String perTxtBolderKey = SettingsPreferencesNotes.PERSIAN_TEXT_BOLDER_KEY;

    private SharedPreferences autoPlayAudioPreferences;
    private final String autoPlayAudioPreferencesName = SettingsPreferencesNotes.AUTO_PLAY_AUDIO_DETAILED_WORD_PREFERENCES;
    private final String plySpeedKey = SettingsPreferencesNotes.SPEED_PLAY_KEY;
    private final String plyWordKey = SettingsPreferencesNotes.PLAY_DETAILED_WORD_KEY;
    private final String plyDefKey = SettingsPreferencesNotes.PLAY_DETAILED_DEFINITION_WORD_KEY;
    private final String plyExmplLKey = SettingsPreferencesNotes.PLAY_DETAILED_EXAMPLE_WORD_KEY;
    private final String plyAgainKey = SettingsPreferencesNotes.PLAY_AGAIN_AT_END_KEY;
    private final String plyNxtUnitKey = SettingsPreferencesNotes.PLAY_NEXT_UNIT_KEY;
    private final String plyDsplyTrnsltKey = SettingsPreferencesNotes.PLAY_DISPLAY_TRANSLATIONS_KEY;
    private final String plyDsplyWordKey = SettingsPreferencesNotes.PLAY_DISPLAY_WORD_KEY;
    private final String plyDsplyDefKey = SettingsPreferencesNotes.PLAY_DISPLAY_DEFINITION_KEY;
    private final String plyDsplyExmplKey = SettingsPreferencesNotes.PLAY_DISPLAY_EXAMPLE_KEY;




    public static SlideWordFragment newInstance(WordModel model,ArrayList<Integer> markedFlagList,
                                                boolean[] shwFarsiFlags, int position){
        SlideWordFragment slideWordFragment = new SlideWordFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", model);
        bundle.putIntegerArrayList("markedFlagList", markedFlagList);
        bundle.putBooleanArray("shwFarsiFlags", shwFarsiFlags);
        bundle.putInt("position", position);
        slideWordFragment.setArguments(bundle);
        return slideWordFragment;
    }


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelList = requireArguments().getParcelable("model");
        markedFlagList = requireArguments().getIntegerArrayList("markedFlagList");
        shwFarsiFlags = requireArguments().getBooleanArray("shwFarsiFlags");
        position = requireArguments().getInt("position");
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
        View view = inflater.inflate(R.layout.fragment_word_detail_slide, container, false);
        viewsFindById(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewsValuesSetter(modelList);
    }


    @Override
    public void translationEditor(String wrdTrnslt, String defTrnslt, String exmplTrnslt) {
        wordTranslate.setText(wrdTrnslt);
        translateDefinition.setText(defTrnslt);
        translateExample.setText(exmplTrnslt);
    }

    @Override
    public void addNote(String note) {
        addedNoteFunctions(note);
    }
    private void addedNoteFunctions(String note){
        if (note != null && note.length() > 0) {
            addedNoteCardView.setVisibility(View.VISIBLE);
            addedNoteTextView.setText(note);
        }else {
            addedNoteCardView.setVisibility(View.GONE);
            addedNoteTextView.setText("");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            audioPlayerListener = (AudioPlayerListener) activity;
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.word_detailed_translate):
            case (R.id.word_detailed_translate_btn):
                translateTextViewVisibility();
                break;
            case (R.id.word_image_detailed):
            case (R.id.image_and_word_card_View):
                audioPlayerListener.wordAudioPlayer(false);
                break;
            case (R.id.word_detailed_definition):
            case (R.id.word_detailed_def_divider):
            case (R.id.word_detailed_definition_translate):
            case (R.id.definition_slide_word_detailed):
            case (R.id.word_detailed_definition_layout):
                audioPlayerListener.definitionAudioPlayer(false);
                break;
            case (R.id.word_detailed_example):
            case (R.id.word_detailed_ex):
            case (R.id.word_detailed_example_translate):
            case (R.id.example_slide_word_detailed):
            case (R.id.word_example_detailed_layout):
                audioPlayerListener.exampleAudioPlayer(false);
                break;
        }
    }




    private void viewsValuesSetter(WordModel list){
        final String appPath = requireActivity().getApplicationInfo().dataDir;
        final File imageDir = new File(Environment.DIRECTORY_DOWNLOADS, File.separator + "4000 Essential Words");

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + modelList.getBookNum());
        final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + modelList.getUnitNum());

        final File imgName = new File(wordUnitImgBookPath, File.separator + "." + new File(modelList.getImgUri()).getName());
        final File imgFile = new File(Environment.getExternalStoragePublicDirectory(imageDir.toString()), imgName.toString());


        if (imgFile.exists()){
            Drawable imgDrawable = Drawable.createFromPath(imgFile.toString());
            Glide.with(requireActivity())
                    .load(imgDrawable)
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(imageWord);
        }else {
            Glide.with(requireActivity())
                    .load(list.getImgUri())
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.loadimg)
                    .into(imageWord);
        }


        //imageWord.setImageResource(list.getWordImage());
        word.setText(list.getWord());
        wordPhonetic.setText(list.getPhonetic());
        wordTranslate.setText(list.getTranslateWord());
        definition.setText(list.getDefinition());
        translateDefinition.setText(list.getTranslateDef());
        example.setText(list.getExample());
        translateExample.setText(list.getTranslateExmpl());
        stringBolder(list.getDefinition(), list.getExample(), list.getWord());
    }


    private void stringBolder(String defWord,
                              String exmplWord,String word){

        TextViewBolder bolder = new TextViewBolder(requireActivity());
            bolder.defTextBolder(definition, defWord, word);
        bolder.exmplTextBolder(example, exmplWord, word);
    }




    @Override
    public void displayTranslations(boolean wordFlag, boolean defFlag, boolean exmplFlag) {
        shwWordFlag = wordFlag;
        shwDefFlag = defFlag;
        shwExmplFlag = exmplFlag;
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



    private void fadeBackViewAnimation(){
        if (isWordShwPref()){
            visibilitySetterTranslateWordAnimation();
            inVisibilitySetterTranslationImageButton();
        }
        if (isDefShwPref()){visibilitySetterTranslateDefinitionAnimation();}
        if (isExmplShwPref()){visibilitySetterTranslateExampleAnimation();}

    }
    private void fadeOutViewAnimation(){
        if (isWordShwPref()){
            inVisibilitySetterTranslateWordAnimation();
            visibilitySetterTranslationImageButton();
        }
        if (isDefShwPref()){inVisibilitySetterTranslateDefinitionAnimation();}
        if (isExmplShwPref()){inVisibilitySetterTranslateExampleAnimation();}
    }

    private int heightPlus(){
        int heightPlus = Resources.getSystem().getDisplayMetrics().heightPixels;
        return (int)(heightPlus * 0.02);
    }


    private void visibilitySetterTranslateWordAnimation(){
        wordTranslate.animate()
                .y(wordPhonetic.getHeight() + heightPlus())
                .alpha(1.0f)
                .setDuration(startAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        wordTranslate.setVisibility(View.VISIBLE);
                    }
                }).start();
    }
    private void visibilitySetterTranslateDefinitionAnimation(){
        translateDefinition.animate()
                .y(definition.getHeight() + heightPlus())
                .alpha(1.0f)
                .setDuration(startAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        translateDefinition.setVisibility(View.VISIBLE);
                    }
                }).start();
    }
    private void visibilitySetterTranslateExampleAnimation(){
        translateExample.animate()
                .y(example.getHeight() + heightPlus())
                .alpha(1.0f)
                .setDuration(startAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        translateExample.setVisibility(View.VISIBLE);
                    }}).start();
    }
    private void visibilitySetterTranslationImageButton(){
        imgTrnBtn.animate()
                .y(wordPhonetic.getHeight() + heightPlus())
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


    private void inVisibilitySetterTranslateWordAnimation(){
        wordTranslate.animate()
                .translationY(wordTranslate.getHeight())
                .alpha(0.0f)
                .setDuration(endAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        wordTranslate.setVisibility(View.INVISIBLE);
                    }
                }).start();
    }
    private void inVisibilitySetterTranslateDefinitionAnimation(){
        translateDefinition.animate()
                .translationY(translateDefinition.getHeight())
                .alpha(0.0f)
                .setDuration(endAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        translateDefinition.setVisibility(View.GONE);
                    }
                }).start();
    }
    private void inVisibilitySetterTranslateExampleAnimation(){
        translateExample.animate()
                .translationY(translateExample.getHeight())
                .alpha(0.0f)
                .setDuration(endAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        translateExample.setVisibility(View.GONE);
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
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        onResumeFunctions();
    }
    private void onResumeFunctions(){
        textViewsVisibilityFunctions();
        displayTranslationFunctions();
        thisOnClick();
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
        if (!isWordShwPref()){wordTranslate.setVisibility(View.GONE);}
        if (!isDefShwPref()){translateDefinition.setVisibility(View.GONE);}
        if (!isExmplShwPref()){translateExample.setVisibility(View.GONE);}
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
    public void onDestroyView() {
        super.onDestroyView();
        flag = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void viewsFindById(View view){
        imageWord = view.findViewById(R.id.word_image_detailed);
        word = view.findViewById(R.id.word_detailed);
        wordPhonetic = view.findViewById(R.id.word_detailed_phonetic);
        wordTranslate = view.findViewById(R.id.word_detailed_translate);
        definition = view.findViewById(R.id.word_detailed_definition);
        defTitle = view.findViewById(R.id.word_detailed_def);
        defDivider = view.findViewById(R.id.word_detailed_def_divider);
        translateDefinition = view.findViewById(R.id.word_detailed_definition_translate);
        example = view.findViewById(R.id.word_detailed_example);
        exmplTitle = view.findViewById(R.id.word_detailed_ex);
        translateExample = view.findViewById(R.id.word_detailed_example_translate);
        //trnsltBtn = requireActivity().findViewById(R.id.slide_word_detail_all_translate_btn);
        imgTrnBtn = view.findViewById(R.id.word_detailed_translate_btn);
        plyWordUingCardView = view.findViewById(R.id.image_and_word_card_View);
        plyDefUingCardView = view.findViewById(R.id.definition_slide_word_detailed);
        plyExmplUingCardView = view.findViewById(R.id.example_slide_word_detailed);
        defRelLayout = view.findViewById(R.id.word_detailed_definition_layout);
        addedNoteCardView = view.findViewById(R.id.note_slide_word_detailed_card_view);
        addedNoteTextView = view.findViewById(R.id.note_added_detailed_text_view);//added note
        exmplRelLayout = view.findViewById(R.id.word_example_detailed_layout);
        addedNoteFunctions(modelList.getAddNote());
        textViewsValueSetterFunctions();

    }

    private void thisOnClick(){
        imgTrnBtn.setOnClickListener(this);
        wordTranslate.setOnClickListener(this);
        plyWordUingCardView.setOnClickListener(this);
        plyDefUingCardView.setOnClickListener(this);
        plyExmplUingCardView.setOnClickListener(this);
        definition.setOnClickListener(this);
        translateDefinition.setOnClickListener(this);
        example.setOnClickListener(this);
        translateExample.setOnClickListener(this);
        imageWord.setOnClickListener(this);
        imageWord.setOnClickListener(this);
        word.setOnClickListener(this);
        wordPhonetic.setOnClickListener(this);
        wordTranslate.setOnClickListener(this);
        definition.setOnClickListener(this);
        defTitle.setOnClickListener(this);
        defDivider.setOnClickListener(this);
        translateDefinition.setOnClickListener(this);
        example.setOnClickListener(this);
        exmplTitle.setOnClickListener(this);
        translateExample.setOnClickListener(this);
        imgTrnBtn.setOnClickListener(this);
        plyWordUingCardView.setOnClickListener(this);
        plyDefUingCardView.setOnClickListener(this);
        plyExmplUingCardView.setOnClickListener(this);
        defRelLayout.setOnClickListener(this);
        exmplRelLayout.setOnClickListener(this);
    }


    private void textViewsValueSetterFunctions(){
        TextViewsSizeSetterFunctions();
        textViewsFontSetterFunctions();
    }

    private void textViewsFontSetterFunctions(){
        engTxtViewFontSetter();
        perTxtViewFontSetter();
    }
    private void engTxtViewFontSetter(){
        word.setTypeface(engTypeFace(), engTextStyle());
        wordPhonetic.setTypeface(engTypeFace(), engTextStyle());
        definition.setTypeface(engTypeFace(), engTextStyle());
        example.setTypeface(engTypeFace(), engTextStyle());
        defTitle.setTypeface(engTypeFace(), engTextStyle());
        exmplTitle.setTypeface(engTypeFace(), engTextStyle());
    }
    private int engTextStyle(){
        if (getEngBolderPreference()){
            return Typeface.BOLD;
        }else {
            return Typeface.NORMAL;
        }
    }
    private void perTxtViewFontSetter(){
        wordTranslate.setTypeface(perTypeFace(), perTextStyle());
        translateDefinition.setTypeface(perTypeFace(), perTextStyle());
        translateExample.setTypeface(perTypeFace(), perTextStyle());
        addedNoteTextView.setTypeface(perTypeFace(), perTextStyle());
    }
    private int perTextStyle(){
        if (getPerBolderPreference()){
            return Typeface.BOLD;
        }else {
            return Typeface.NORMAL;
        }
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
        word.setTextSize(engTxtSize());
        wordPhonetic.setTextSize(engTxtSize());
        definition.setTextSize(engTxtSize());
        example.setTextSize(engTxtSize());
        defTitle.setTextSize(engTxtSize());
        exmplTitle.setTextSize(engTxtSize());
    }
    private void perTxtSizeSetter(){
        wordTranslate.setTextSize(perTxtSize());
        translateDefinition.setTextSize(perTxtSize());
        translateExample.setTextSize(perTxtSize());
        addedNoteTextView.setTextSize(perTxtSize());
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


    private boolean getEngBolderPreference(){
        fontTypePreferences = requireActivity().getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getBoolean(engTxtBolderKey, false);
    }
    private boolean getPerBolderPreference(){
        fontTypePreferences = requireActivity().getSharedPreferences(fontTypePreferencesName, Context.MODE_PRIVATE);
        return fontTypePreferences.getBoolean(perTxtBolderKey, false);
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
