package com.example.a4000essentialwordsbook1.SelectedUnitTab.StoryList.StoryWordDialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookTwo;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookTwo;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.AutoPlayTypeNotes.AutoPlayTypeNote;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.FontTypeFiles.GlobalFonts;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class StoryWordTouchedDialog extends DialogFragment implements View.OnClickListener {
    private TextView word, wordPhonetic, definition, example;
    private TextView defTxtTv, defTxtDivider, exmplTXTTv, exmplDivider;
    private TextView wordTranslate, translateDefinition, translateExample;
    private ImageView wordImage, cancelImg;
    private ImageButton plyAudioBtn, translateBtn;
    private WordModel model;
    private final int tmLineMines = 80;
    private ExecutorService autoPlayThread;
    private MediaPlayer singleMediaPlayer;
    private int plyAudio;
    private boolean trnsltFlag = false;
    private final int startAnimationInt = 300;
    private final int endAnimationInt = 300;
    private int[] dbInfoList;

    private final int[] perFontList = GlobalFonts.perFontList;
    private final int[] engFontList = GlobalFonts.engFontList;

    private final String wrdStartNote = AutoPlayTypeNote.WRD_START;
    private final String wrdDuraionNote = AutoPlayTypeNote.WORD_DURATION;
    private final String defStartNote = AutoPlayTypeNote.DEFINITION_START;
    private final String defDurationNote = AutoPlayTypeNote.DEFINITION_DURATION;
    private final String exmplStartNote = AutoPlayTypeNote.EXAMPLE_START;
    private final String exmplDurationNote = AutoPlayTypeNote.EXAMPLE_DURATION;

    private SharedPreferences playWordAudioPreferences;
    private final String plyWordAudioStoryPreferencesName = SettingsPreferencesNotes.PLAY_WORDS_AUDIO_STORY_PREFERENCES_NAME;
    private final String plyWordAudioStoryKey = SettingsPreferencesNotes.PLY_WORD_AUDIO_STORY_KEY;
    private final String plyDefAudioStoryKey = SettingsPreferencesNotes.PLY_DEFINITION_AUDIO_STORY_KEY;
    private final String plyExmplAudioStoryKey = SettingsPreferencesNotes.PLY_EXAMPLE_AUDIO_STORY_KEY;

    private SharedPreferences fontTypePreferences;
    private SharedPreferences textSizePreferences;
    private final String fontTypePreferencesName = SettingsPreferencesNotes.SETTINGS_TEXT_FONT_TYPE_PREFERENCES;
    private final String textViewSizePreferencesName = SettingsPreferencesNotes.SETTINGS_TEXT_VIEW_SIZE_PREFERENCES;
    private final String engListPositionKey = SettingsPreferencesNotes.ENGLISH_LIST_POSITION_KEY;
    private final String perListPositionKey = SettingsPreferencesNotes.PERSIAN_LIST_POSITION_KEY;
    private final String txtViewSizeKey = SettingsPreferencesNotes.PERSIAN_TEXT_VIEW_SIZE_KEY;
    private final String engTxtViewSizeKey = SettingsPreferencesNotes.ENGLISH_TEXT_VIEW_SIZE_KEY;

    public static StoryWordTouchedDialog newInstance(WordModel model, int[] dbInfoList){
        StoryWordTouchedDialog fragment = new StoryWordTouchedDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", model);
        bundle.putIntArray("dbInfoList", dbInfoList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = requireArguments().getParcelable("model");
        dbInfoList = requireArguments().getIntArray("dbInfoList");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.word_pop_up_activity, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewsFinderById(view);
        databaseReaderBackThread();
    }

    private void databaseReaderBackThread(){
        wordListReaderDatabase();
        wordAudioReaderDatabase();
    }
    private void wordAudioReaderDatabase(){
        ExecutorService audioThread = Executors.newSingleThreadExecutor();
        Handler audioHandler = new Handler(Looper.getMainLooper());

        audioThread.execute(() -> wordAudioReceiver(dbInfoList[0], dbInfoList[1]));

    }
    private void wordListReaderDatabase(){
        ExecutorService threadWordList = Executors.newSingleThreadExecutor();
        Handler handlerWordList = new Handler(Looper.getMainLooper());

        threadWordList.execute(() -> {
            wordDatabaseReceiver(dbInfoList[0], dbInfoList[1]);
            handlerWordList.post(() -> setViewsValues(model));
        });
    }










    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.word_pop_up_ply_audio_btn):
                playWordAudioStory();
                break;
            case (R.id.word_pop_up_translate_btn):
                translateTextViewVisibility();
                break;
            case (R.id.word_pop_up_definition):
                Toast.makeText(requireActivity(), "this is word_pop_up_definition", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.word_pop_up_example):
                Toast.makeText(requireActivity(), "this is word_pop_up_example", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.cancel_pop_up):
                dismiss();
                break;
        }
    }

    private void translateTextViewVisibility(){
        if (!trnsltFlag){
            fadeBackViewAnimation();
            trnsltFlag = true;
        }else {
            fadeOutViewAnimation();
            trnsltFlag = false;
        }
    }


    private void fadeOutViewAnimation(){
        inVisibilitySetterTranslateWordAnimation();
        inVisibilitySetterTranslateDefinitionAnimation();
        inVisibilitySetterTranslateExampleAnimation();
    }
    private void fadeBackViewAnimation(){
        visibilitySetterTranslateWordAnimation();
        visibilitySetterTranslateExampleAnimation();
        visibilitySetterTranslateDefinitionAnimation();
    }



    private void visibilitySetterTranslateWordAnimation(){
        wordTranslate.animate()
                .y(wordPhonetic.getHeight() +10)
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
                .y(definition.getHeight() + 10)
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
                .y(example.getHeight() + 10)
                .alpha(1.0f)
                .setDuration(startAnimationInt)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        translateExample.setVisibility(View.VISIBLE);
                    }}).start();
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



    private void playWordAudioStory(){
        if (canAllBePlayed()) {
            autoPlayAllPlaying();
        } else if (isWordPlayedOnly()) {
            autoWordPlayer();
        } else if (isDefPlayedOnly()) {
            definitionAutoPlayer();
        } else if (isExmplPlayedOnly()) {
            exampleAutoPlayer();
        }else if (isWordAndDef()) {
            autoPlayTwoAudio(wrdStartNote, wrdDuraionNote, defStartNote, defDurationNote);
        } else if (isWordAndExmpl()) {
            autoPlayTwoAudio(wrdStartNote, wrdDuraionNote, exmplStartNote, exmplDurationNote);
        }else if (isDefAndExmpl()) {
            autoPlayTwoAudio(defStartNote, defDurationNote, exmplStartNote, exmplDurationNote);
        }
    }


    private void autoPlayAllPlaying(){
        new Handler(Looper.getMainLooper()).postDelayed(() ->{
            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayThread.execute(() -> {
                mediaPlayHandler.postDelayed(() -> singlePlayAudio(wordStart(), wordDuration()), 500);
                threadSleep(wordDuration());
                mediaPlayHandler.postDelayed(() -> singlePlayAudio(defStart(), defDuration()), 500);
                threadSleep(defDuration());
                mediaPlayHandler.postDelayed(() -> singlePlayAudio(exmplStart(), exmplDuration()), 500);
                threadSleep(exmplDuration());
            });
        }, 100);
    }
    private void autoPlayTwoAudio(String startFirst, String durationFirst, String startSecond, String durationSecond){
        new Handler(Looper.getMainLooper()).postDelayed(() ->{
            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayThread.execute(() ->{
                firstAutoRun = () -> singlePlayAudio(startAudio(startFirst), durationAudio(durationFirst));
                mediaPlayHandler.postDelayed(firstAutoRun , 500);
                threadSleep(durationAudio(durationFirst));
                secondAutoRun = () -> singlePlayAudio(startAudio(startSecond), durationAudio(durationSecond));
                mediaPlayHandler.postDelayed(secondAutoRun, 500);
                threadSleep(durationAudio(durationSecond));
            });
        },100);
    }
    private Runnable firstAutoRun;
    private Runnable secondAutoRun;
    private int startAudio(String type){
        if (type.equalsIgnoreCase(wrdStartNote)){
            return wordStart();
        }else if (type.equalsIgnoreCase(defStartNote)){
            return defStart();
        }else {
            return exmplStart();
        }
    }
    private int durationAudio(String type){
        if (type.equalsIgnoreCase(wrdDuraionNote)){
            return wordDuration();
        }else if (type.equalsIgnoreCase(defDurationNote)){
            return defDuration();
        }else {
            return exmplDuration();
        }

    }


    private void autoWordPlayer(){
        new Handler(Looper.getMainLooper()).postDelayed(() ->{

            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayThread.execute(() ->{
                wordAutoRun = () -> singlePlayAudio(wordStart(), wordDuration());
                mediaPlayHandler.postDelayed(wordAutoRun, 500);
                threadSleep(wordDuration());
            });
        }, 100);
    }
    private Runnable wordAutoRun;
    private void definitionAutoPlayer(){
        new Handler(Looper.getMainLooper()).postDelayed(() ->{
            ExecutorService autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayThread.execute(() ->{
                defAutoRun = () -> singlePlayAudio(defStart(), defDuration());
                mediaPlayHandler.postDelayed(defAutoRun, 500);
                threadSleep(defDuration());

            });
        }, 100);
    }
    private Runnable defAutoRun;
    private void exampleAutoPlayer(){
        new Handler(Looper.getMainLooper()).postDelayed(() ->{

            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayThread.execute(() ->{
                exmplAutoRun = () -> singlePlayAudio(defStart(), defDuration());
                mediaPlayHandler.postDelayed(exmplAutoRun, 500);
                threadSleep(exmplDuration());
            });
        }, 100);
    }
    private Runnable exmplAutoRun;


    private void viewsFinderById(View view){
        wordImage = view.findViewById(R.id.word_image_pop_up);

        word = view.findViewById(R.id.word_pop_up);
        wordPhonetic = view.findViewById(R.id.word_pop_up_phonetic);
        definition = view.findViewById(R.id.word_pop_up_definition);
        example = view.findViewById(R.id.word_pop_up_example);
        defTxtTv = view.findViewById(R.id.word_pop_up_def);
        defTxtDivider = view.findViewById(R.id.word_pop_up_def_divider);
        exmplTXTTv = view.findViewById(R.id.word_pop_up_ex);
        exmplDivider = view.findViewById(R.id.word_pop_up_ex_divider);

        wordTranslate = view.findViewById(R.id.pop_up_word_translate);
        translateDefinition = view.findViewById(R.id.word_pop_up_detailed_definition_translate);
        translateExample = view.findViewById(R.id.word_pop_up_detailed_example_translate);




        plyAudioBtn = view.findViewById(R.id.word_pop_up_ply_audio_btn);
        translateBtn = view.findViewById(R.id.word_pop_up_translate_btn);
        cancelImg = view.findViewById(R.id.cancel_pop_up);
        textViewsValuesSetter();
        thiDialogItemOnClickListener();
    }

    private void textViewsValuesSetter(){
        textViewsSizeSetter();
        textViewsFontSetter();
    }
    private void textViewsSizeSetter(){
        engTxtViewSizeSetter();
        perTxtViewSizeSetter();
    }
    private void engTxtViewSizeSetter(){
        word.setTextSize(engTxtSize());
        wordPhonetic.setTextSize(engTxtSize());
        definition.setTextSize(engTxtSize());
        example.setTextSize(engTxtSize());
        defTxtTv.setTextSize(engTxtSize());
        defTxtDivider.setTextSize(engTxtSize());
        exmplTXTTv.setTextSize(engTxtSize());
        exmplDivider.setTextSize(engTxtSize());
    }
    private void perTxtViewSizeSetter(){
        wordTranslate.setTextSize(perTxtSize());
        translateDefinition.setTextSize(perTxtSize());
        translateExample.setTextSize(perTxtSize());
    }

    private void textViewsFontSetter(){
        txtViewPerFontSetter();
        txtViewEngFontSetter();
    }
    private void txtViewEngFontSetter(){
        word.setTypeface(engTypeFace());
        wordPhonetic.setTypeface(engTypeFace());
        definition.setTypeface(engTypeFace());
        example.setTypeface(engTypeFace());
        defTxtTv.setTypeface(engTypeFace());
        defTxtDivider.setTypeface(engTypeFace());
        exmplTXTTv.setTypeface(engTypeFace());
        exmplDivider.setTypeface(engTypeFace());
    }
    private void txtViewPerFontSetter(){
        wordTranslate.setTypeface(perTypeFace());
        translateDefinition.setTypeface(perTypeFace());
        translateExample.setTypeface(perTypeFace());
    }

    private Typeface engTypeFace(){return ResourcesCompat.getFont(requireActivity(), getEngFont());}
    private Typeface perTypeFace(){return ResourcesCompat.getFont(requireActivity(), getPerFont());}

    private int getEngFont(){return engFontList[engFontVal()];}
    private int getPerFont(){return perFontList[perFontVal()];}

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
    return textSizePreferences.getInt(engTxtViewSizeKey, 18);}
    private int perTxtSize(){
    textSizePreferences = requireActivity().getSharedPreferences(textViewSizePreferencesName, Context.MODE_PRIVATE);
    return textSizePreferences.getInt(txtViewSizeKey, 18);}

    private void setViewsValues(WordModel modelList){
        wordImage.setImageResource(modelList.getWordImage());
        String txt = modelList.getWord();
        word.setText(txt);
        wordPhonetic.setText(modelList.getPhonetic());
        definition.setText(modelList.getDefinition());
        example.setText(modelList.getExample());
        wordTranslate.setText(modelList.getTranslateWord());
        translateDefinition.setText(modelList.getTranslateDef());
        translateExample.setText(modelList.getTranslateExmpl());
    }
    private void thiDialogItemOnClickListener(){
        plyAudioBtn.setOnClickListener(this);
        translateBtn.setOnClickListener(this);
        definition.setOnClickListener(this);
        example.setOnClickListener(this);
        cancelImg.setOnClickListener(this);
    }



    private void wordAudioReceiver(int dbNum, int unitNum){
        SQLiteDatabase db = unitListDatabase(dbNum).getReadableDatabase();

        Cursor awCursor = db.query(DB_NOTES.UNIT_TABLE,
                new String[]{DB_NOTES.UNIT_COMPLETE_WORD_AUDIO},
                DB_NOTES.UNIT_ID + " = ? ", new String[]{Integer.toString(unitNum)},
                null, null, null);

        if (awCursor != null && awCursor.getCount() != 0){
            while (awCursor.moveToNext()){
                plyAudio = awCursor.getInt(awCursor.getColumnIndex(DB_NOTES.UNIT_COMPLETE_WORD_AUDIO));
            }
            audioPlayers(plyAudio);
        }
        assert awCursor != null;
        awCursor.close();
        db.close();
    }
    private SQLiteOpenHelper unitListDatabase(int unitId){
        if (unitId == 1){
            return new UnitDatabaseBookOne(requireActivity());
        }else if (unitId == 2){
            return new UnitDatabaseBookTwo(requireActivity());
        }else if (unitId == 3){
            return new UnitDatabaseBookThree(requireActivity());
        }else if (unitId == 4){
            return new UnitDatabaseBookFour(requireActivity());
        }else if (unitId == 5){
            return new UnitDatabaseBookFive(requireActivity());
        }else {
            return new UnitDatabaseBookSix(requireActivity());
        }
    }

    private void wordDatabaseReceiver(int dbNum, int unitNum){
        if (unitNum == 0) {
            unitNum = 1;
        }
        SQLiteDatabase database = wordListDatabase(dbNum).getReadableDatabase();

        Cursor cursor = database.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitNum,
                new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                        DB_NOTES.WORD_START, DB_NOTES.WORD_END, DB_NOTES.DEF_START, DB_NOTES.DEF_END, DB_NOTES.EXMPL_START, DB_NOTES.EXMPL_END,
                        DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.AUDIO_WORD,
                        DB_NOTES.HARD_FLAG, DB_NOTES.EASY_FLAG},
                DB_NOTES.WORD + " = ? ", new String[]{model.getWord()},
                null, null, null);

        if (cursor != null && cursor.getCount() != 0){

            while (cursor.moveToNext()){

                int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                int imgWord = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                int bookIdNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.BOOK_NUMBER));
                int unitIdNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.UNIT_NUMBER));
                int wrdStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_START));
                int wrdEnd  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_END));
                int defStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_START));
                int defEnd  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_END));
                int exmStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_START));
                int exmEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_END));
                String word = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));
                //String phonetic = cursor.getString(cursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));
                String translateWord = cursor.getString(cursor.getColumnIndex(DB_NOTES.TRANSLATE_WORD));
                String definition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));
                String translateDefinition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_TRANSLATE_WORD));
                String example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));
                String translateExample = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));


                model.setId(id);
                model.setWordImage(imgWord);
                model.setBookNum(bookIdNum);
                model.setUnitNum(unitIdNum);
                model.setWrdStart(wrdStart - tmLineMines);
                model.setWrdEnd(wrdEnd- tmLineMines);
                model.setDefStart(defStart- tmLineMines);
                model.setDefEnd(defEnd- tmLineMines);
                model.setExmplStart(exmStart- tmLineMines);
                model.setExmplEnd(exmEnd- tmLineMines);

                model.setWord(word);
                //model.setPhonetic(phonetic);
                model.setTranslateWord(translateWord);
                model.setDefinition(definition);
                model.setTranslateDef(translateDefinition);
                model.setExample(example);
                model.setTranslateExmpl(translateExample);

            }
            database.close();
            cursor.close();
        }
    }
    private SQLiteOpenHelper wordListDatabase(int databaseNum){
        if (databaseNum == 1){
            return new WordDatabaseBookOne(requireActivity());
        }else if (databaseNum == 2){
            return new WordDatabaseBookTwo(requireActivity());
        }else if (databaseNum == 3){
            return new WordDatabaseBookThree(requireActivity());
        }else if (databaseNum == 4){
            return new WordDatabaseBookFour(requireActivity());
        }else if (databaseNum == 5){
            return new WordDatabaseBookFive(requireActivity());
        }else {
            return new WordDatabaseBookSix(requireActivity());
        }
    }

    private void audioPlayers(int plyAudio){
        singleMediaPlayer = MediaPlayer.create(requireActivity(), plyAudio);
    }


    private void singlePlayAudio(int start, int end) {
        try {
            boolean isPlaying = singleMediaPlayer.isPlaying();

            if (isPlaying) {
                mediaPlayHandler.removeCallbacks(audioThread);
                singleMediaPlayer.pause();
                singleMediaPlayer.seekTo(start);
            }
            audioThread = () -> {
                singleMediaPlayer.pause();
                singleMediaPlayer.seekTo(start);
            };
            singleMediaPlayer.seekTo(start);
            singleMediaPlayer.start();
            mediaPlayHandler.postDelayed(audioThread, end);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private final Handler mediaPlayHandler = new Handler(Looper.getMainLooper());
    private  Runnable audioThread;

    private int wordDuration(){return (wordEnd() - wordStart());}
    private int wordStart(){return model.getWrdStart();}
    private int wordEnd(){return model.getWrdEnd();}

    private int defDuration(){return (defEnd() - defStart());}
    private int defStart(){return model.getDefStart();}
    private int defEnd(){return model.getDefEnd();}

    private int exmplDuration(){return (exmplEnd() - exmplStart());}
    private int exmplStart(){return model.getExmplStart();}
    private int exmplEnd(){return model.getExmplEnd();}


    private boolean canAllBePlayed(){
        return canWordPlayed() && canDefPlayed() && canExmplPlayed();
    }
    private boolean canWordPlayed(){
        playWordAudioPreferences = requireActivity().getSharedPreferences(plyWordAudioStoryPreferencesName, Context.MODE_PRIVATE);
        return playWordAudioPreferences.getBoolean(plyWordAudioStoryKey, true);
    }
    private boolean canDefPlayed(){
        playWordAudioPreferences = requireActivity().getSharedPreferences(plyWordAudioStoryPreferencesName, Context.MODE_PRIVATE);
        return playWordAudioPreferences.getBoolean(plyDefAudioStoryKey, false);
    }
    private boolean canExmplPlayed(){
        playWordAudioPreferences = requireActivity().getSharedPreferences(plyWordAudioStoryPreferencesName, Context.MODE_PRIVATE);
        return playWordAudioPreferences.getBoolean(plyExmplAudioStoryKey, false);
    }

    private boolean isWordAndDef(){return canWordPlayed() && canDefPlayed() && !canExmplPlayed();}
    private boolean isWordAndExmpl(){return canWordPlayed() && canExmplPlayed() && !canDefPlayed();}
    private boolean isDefAndExmpl(){return canDefPlayed() && canExmplPlayed() && !canWordPlayed();}


    private boolean isWordPlayedOnly(){return canWordPlayed() && !canDefPlayed() && !canExmplPlayed();}
    private boolean isDefPlayedOnly(){return canDefPlayed() && !canExmplPlayed() && !canWordPlayed();}
    private boolean isExmplPlayedOnly(){return canExmplPlayed() && !canWordPlayed() && !canDefPlayed();}

    private void threadSleep(int duration){
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        wordMediaPlayerDestroyer();
    }

    private void wordMediaPlayerDestroyer(){
        try {
            boolean isDefPlaying = singleMediaPlayer.isPlaying();
            if (isDefPlaying) {
                mediaPlayHandler.removeCallbacks(audioThread);
                singleMediaPlayer.stop();
                singleMediaPlayer.reset();
                singleMediaPlayer.release();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
