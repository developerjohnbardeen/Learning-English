package com.example.a4000essentialwordsbook1.SelectedUnitTab.StoryList;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.a4000essentialwordsbook1.MarkedWords.MarkedWordActivity;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.StoryList.StoryWordDialog.StoryWordTouchedDialog;
import com.example.a4000essentialwordsbook1.Settings.SettingsPreferencesActivity;
import com.example.a4000essentialwordsbook1.SpannableString.FindWordsListStory;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.FontTypeFiles.GlobalFonts;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class StoryListFragment extends Fragment implements View.OnClickListener{
    private TextView storyContentTxtView, storyTitleTxt, perStoryTitle;
    private ImageView storyImageView;
    private String unitEngStory, farsiStoryTitle,unitStoryTitle;
    private int unitNum, dbNum;
    private int  unitImage;
    private boolean flag = false;
    private final String[] wordsList = new String[20];
    private final String[] phoneticList = new String[20];
    private ArrayList<WordModel> wordModelList;
    private SpanStringBold storyBolder;

    private final String[] perStringList = GlobalFonts.perStringList;
    private final int[] perFontList = GlobalFonts.perFontList;
    private final String[] engStringList = GlobalFonts.engStringList;
    private final int[] engFontList = GlobalFonts.engFontList;

    private final String engLangValueStr = "english";
    private final String perLangValueStr = "persian";

    private SharedPreferences storyFontTypePreferences;
    private final String storyFontTypePreferencesName = SettingsPreferencesNotes.SETTING_STORY_FONT_TYPE_PREFERENCES;
    private final String stryEngFontKey = SettingsPreferencesNotes.STORY_ENGLISH_FONT_TYPE_KEY;
    private final String stryPerFontKey = SettingsPreferencesNotes.STORY_PERSIAN_FONT_TYPE_KEY;
    private final String stryFntTypeRadioBtnKey = SettingsPreferencesNotes.STORY_ENG_OR_PER_RADIO_BUTTON_KEY;
    private final String engListPositionKey = SettingsPreferencesNotes.STORY_ENG_PICKER_FONT_VALUE_KEY;
    private final String perListPositionKey = SettingsPreferencesNotes.STORY_PER_PICKER_FONT_VALUE_KEY;

    SharedPreferences storyTextSizePreferences;
    private final String storyTextSizePreferencesName = SettingsPreferencesNotes.SETTINGS_STORY_TEXT_VIEW_SIZE_PREFERENCES;
    private final String storyTextSizeKey = SettingsPreferencesNotes.STORY_TEXT_VIEW_SIZE_KEY;


    public StoryListFragment(int dbNumb, int unitNum){
        this.unitNum = unitNum;
        this.dbNum = dbNumb;
    }

    public static StoryListFragment newInstance(int dbNumb, int unitNum){
        return new StoryListFragment(dbNumb, unitNum);
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_story, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        viewsFinderById(view);
        wordsListReceiverThread();
        setComponentValues();
    }


    private void wordsListReceiverThread(){
        Handler handler = new Handler(Looper.getMainLooper());

        ExecutorService serviceThread = Executors.newFixedThreadPool(2);
        serviceThread.execute(() ->{
            unitStoryReceiver(unitNum, dbNum);
            handler.post(() ->{
                storyTitleTxt.setText(unitStoryTitle);
                perStoryTitle.setText(farsiStoryTitle);
                storyImageView.setImageResource(unitImage);
            });});
        serviceThread.execute(() ->{
            wordsListReceiver();
            textViewBolder();
        });
        serviceThread.shutdown();
    }
    private void textViewBolder(){
        storyBolder = new SpanStringBold();
        storyBolder.storyWordsBolder(unitEngStory, wordsList, phoneticList, wordModelList);
    }


    private void unitStoryReceiver(int unitId, int dbId){
        SQLiteDatabase storyDB = unitListDatabase(dbId).getReadableDatabase();

        Cursor strCursor = storyDB.query(DB_NOTES.UNIT_TABLE,
                new String[]{DB_NOTES.UNIT_ENG_STORY, DB_NOTES.UNIT_PERSIAN_STORY, DB_NOTES.FARSI_UNIT_TITLE ,DB_NOTES.UNIT_TITLE, DB_NOTES.UNIT_IMG},
                DB_NOTES.UNIT_ID + " = ? " , new String[]{Integer.toString(unitId)},
                null, null, null);

        if (strCursor != null && strCursor.getCount() != 0) {
            while (strCursor.moveToNext()) {
                unitEngStory = strCursor.getString(strCursor.getColumnIndex(DB_NOTES.UNIT_ENG_STORY));
                unitStoryTitle = strCursor.getString(strCursor.getColumnIndex(DB_NOTES.UNIT_TITLE));
                farsiStoryTitle = strCursor.getString(strCursor.getColumnIndex(DB_NOTES.FARSI_UNIT_TITLE));
                unitImage = strCursor.getInt(strCursor.getColumnIndex(DB_NOTES.UNIT_IMG));
            }
        }
        assert strCursor != null;
        storyDB.close();
        strCursor.close();

    }
    private void wordsListReceiver(){
        int index = 0;
        if (unitNum == 0){
            unitNum = 1;
        }
        SQLiteDatabase db =  wordListDatabase(dbNum).getReadableDatabase();
        wordModelList = new ArrayList<>();

        Cursor cursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitNum,
                new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG,DB_NOTES.WORD_START, DB_NOTES.WORD_END,
                        DB_NOTES.DEF_START, DB_NOTES.DEF_END, DB_NOTES.EXMPL_START, DB_NOTES.EXMPL_END ,
                        DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                        DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD,
                        DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD}
                ,null, null, null, null, null, null);

        if (cursor != null && cursor.getCount() != 0){
            while (cursor.moveToNext()){
                WordModel modelList = new WordModel();
                int wordId = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                //int wordImage = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                //int wordStart = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_START));
                //int wordEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_END));
                //int defStart = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_START));
                //int defEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_END));
                //int exmplStart = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_START));
                //int exmplEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_END));
                String wordName = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));
                //String wordTranslate = cursor.getString(cursor.getColumnIndex(DB_NOTES.TRANSLATE_WORD));
                String wordPhonetic = cursor.getString(cursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));
                //String wordDefinition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));
                //String wordExample = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));
                //String wordTranslateExmpl = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));
                //String definitionTranslate = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_TRANSLATE_WORD));
                //String exampleTranslate = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));

                modelList.setId(wordId);
                //modelList.setWordImage(wordImage);
                //modelList.setWrdStart(wordStart);
                //modelList.setWrdEnd(wordEnd);
                //modelList.setDefStart(defStart);
                //modelList.setDefEnd(defEnd);
                //modelList.setExmplStart(exmplStart);
                //modelList.setExmplEnd(exmplEnd);
                modelList.setWord(wordName);
                //modelList.setTranslateWord(wordTranslate);
                modelList.setPhonetic(wordPhonetic);
                //modelList.setDefinition(wordDefinition);
                //modelList.setTranslateDef(definitionTranslate);
                //modelList.setTranslateExmpl(exampleTranslate);
                //modelList.setExample(wordExample);
                //modelList.setTranslateExmpl(wordTranslateExmpl);

                wordModelList.add(modelList);
                wordsList[index] = wordName;
                phoneticList[index] = wordPhonetic;
                index++;
            }
        }
        assert cursor != null;
        db.close();
        cursor.close();
    }
    private SQLiteOpenHelper unitListDatabase(int dbId){
        if (dbId == 1){
            return new UnitDatabaseBookOne(requireActivity());
        }else if (dbId == 2){
            return new UnitDatabaseBookTwo(requireActivity());
        }else if (dbId == 3){
            return new UnitDatabaseBookThree(requireActivity());
        }else if (dbId == 4){
            return new UnitDatabaseBookFour(requireActivity());
        }else if (dbId == 5){
            return new UnitDatabaseBookFive(requireActivity());
        }else {
            return new UnitDatabaseBookSix(requireActivity());
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




    @Override
    public void onClick(View v) {
        if (!flag) {
            String text = requireActivity().getString(R.string.story_content);
            storyContentTxtView.setText(text.trim());
            flag = true;
        }else {
            spanString();
            flag = false;
        }
    }


    private void spanString(){
        SpannableString span = storyBolder.getSpan();
        storyContentTxtView.setText(span);
        storyContentTxtView.setClickable(true);
        storyContentTxtView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void viewsFinderById(View view){
        storyTitleTxt = view.findViewById(R.id.title_story);
        perStoryTitle = view.findViewById(R.id.persian_title_story);
        storyImageView = view.findViewById(R.id.image_Story_fragment);
        storyContentTxtView = view.findViewById(R.id.text_view_content_story);
        ImageButton btn = requireActivity().findViewById(R.id.click_button);
        storyTextViewValueSetter();
        btn.setOnClickListener(this);
    }


    private void storyTextViewValueSetter(){
        storyTitlesValueSetter();
        storyContentTextViewValueSetter();
    }

    private void storyTitlesValueSetter(){
        if (isLanEnglish()){
            storyTitleTxt.setTypeface(typeface());
            // ?? perStoryTitle.setTypeface(typeface());
        }else {
            // ?? storyTitleTxt.setTypeface(typeface());
            perStoryTitle.setTypeface(typeface());
        }
        storyTitleTxt.setTextSize(txtSize());
        perStoryTitle.setTextSize(txtSize());
    }
    private void storyContentTextViewValueSetter(){
        storyContentTxtView.setTextSize(txtSize());
        storyContentTxtView.setTypeface(typeface());
    }
    private Typeface typeface(){
        return ResourcesCompat.getFont(requireActivity(), txtViewFont());
    }


    private void setComponentValues(){
        storyContentTxtView.setText(requireActivity().getString(R.string.story_content));
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.alternate_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.alternate_setting_menu):
                settingStartActivity();
                return true;
            case (R.id.alternate_download_menu):
                Toast.makeText(requireActivity(), "Download Files is Under Process!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void settingStartActivity(){
        Intent settingIntent = new Intent(requireActivity(), SettingsPreferencesActivity.class);
        startActivity(settingIntent);
    }
    private void reviewMarkedWordStartActivity(){
        Intent reviewIntent = new Intent(requireActivity(), MarkedWordActivity.class);
        startActivity(reviewIntent);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

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
    public void onDestroyView() {
        super.onDestroyView();
    }




    class SpanStringBold{
        private SpannableString span;
        private final ArrayList<Integer> startList = new ArrayList<>();
        private final ArrayList<Integer> endList = new ArrayList<>();
        private Matcher matcher;
        private boolean flag = false;


        public void storyWordsBolder(String mainWord, String[] wordsList, String[] phoneticList, ArrayList<WordModel> modelList){
            span = new SpannableString(mainWord);
            ExecutorService serviceThread = Executors.newFixedThreadPool(2);
            Handler handler = new Handler(Looper.getMainLooper());
            serviceThread.execute(() -> wordStoryBolder(span, mainWord, wordsList, phoneticList, modelList));
            serviceThread.execute(() -> {
                findPersianSentenceStoryThread(mainWord);
                handler.post(this::colorSetterToPersianSentences);
            });
            serviceThread.shutdown();
        }




        private void wordStoryBolder(SpannableString span, String txtContext, String[] wordsList, String[] phoneticList, ArrayList<WordModel> modelList){

            FindWordsListStory findWordsListStory = new FindWordsListStory();
            findWordsListStory.storyWordsFinder(txtContext, wordsList, phoneticList);
            int[] start = findWordsListStory.getStart();
            int[] end = findWordsListStory.getEnd();

            for (int i = 0 ; i < start.length ; i++){

                int finalI = i;
                ClickableSpan storyOnclick = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        storySingleWordDialog(modelList.get(finalI));
                    }

                    @Override
                    public void updateDrawState(TextPaint txt){
                        txt.setUnderlineText(false);
                        txt.setColor(Color.rgb(0, 100, 0));
                        txt.setFakeBoldText(true);
                        txt.isFakeBoldText();
                    }
                };

                span.setSpan(
                        storyOnclick
                        , start[i]
                        , end[i]
                        , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }


        private void findPersianSentenceStoryThread(String mainWord){
            Pattern pattern = Pattern.compile("\n\n");
            matcher = pattern.matcher(mainWord);
            persianBolder();
        }
        private void persianBolder(){
            while (matcher.find()){
                if (!flag){
                    startList.add(matcher.start());
                    flag = true;
                }else {
                    endList.add(matcher.start());
                    flag = false;
                }
            }

        }
        private void colorSetterToPersianSentences(){
            for (int i = 0 ; i <startList.size(); i++) {
                span.setSpan(
                        new ForegroundColorSpan(ContextCompat.getColor(requireActivity(), R.color.perColor))
                        , startList.get(i)
                        , endList.get(i)
                        , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }


        public SpannableString getSpan() {
            final char[] list = {' ',' ',' '};
            return span;
        }




        private void storySingleWordDialog(WordModel wordModels){
            final int[] dbInfoList = new int[]{dbNum, unitNum};
            StoryWordTouchedDialog storySingleWordDialog = StoryWordTouchedDialog.newInstance(wordModels, dbInfoList);
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            Fragment prev = requireActivity().getSupportFragmentManager().findFragmentByTag("storyDialog");
            if (prev != null){
                ft.remove(prev);
            }
            storySingleWordDialog.show(ft, "storyDialog");

        }
    }


    private int txtViewFont(){
        if (isLanEnglish()){
            return engFontList[engFontVal()];
        }else {
            return perFontList[perFontVal()];
        }
    }
    private int txtSize(){
        storyTextSizePreferences = requireActivity().getSharedPreferences(storyTextSizePreferencesName, Context.MODE_PRIVATE);
        return storyTextSizePreferences.getInt(storyTextSizeKey, 20);
    }


    private boolean isLanEnglish(){
        return fontLanguageType().equalsIgnoreCase(engLangValueStr);
    }
    private String fontLanguageType(){
        storyFontTypePreferences = requireActivity().getSharedPreferences(storyFontTypePreferencesName, Context.MODE_PRIVATE);
        return storyFontTypePreferences.getString(stryFntTypeRadioBtnKey, engLangValueStr);
    }
    private int engFontVal(){
        storyFontTypePreferences = requireActivity().getSharedPreferences(storyFontTypePreferencesName, Context.MODE_PRIVATE);
        return storyFontTypePreferences.getInt(engListPositionKey, 0);
    }
    private int perFontVal(){
        storyFontTypePreferences = requireActivity().getSharedPreferences(storyFontTypePreferencesName, Context.MODE_PRIVATE);
        return storyFontTypePreferences.getInt(perListPositionKey, 0);
    }
}