package com.example.a4000essentialwordsbook1.SelectedUnitTab.StoryList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookTwo;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.DataBases.WordDatabaseOpenHelper;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.SpannableString.SpanStringBold;
import com.example.a4000essentialwordsbook1.DataBases.UnitSqliteOpenHelper;

import org.greenrobot.eventbus.util.ErrorDialogManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StoryListFragment extends Fragment implements View.OnClickListener{
    private final Context strContext ;
    private TextView storyContentTxtView, storyTitleTxt;
    private ImageView storyImageView;
    private String unitContentStory, unitStoryTitle;
    private int unitNumber;
    private int dbNum;
    private int  unitImage;
    private boolean flag = true;
    private final String[] wordsList = new String[20];
    private ArrayList<WordModel> wordModelList;
    private SpanStringBold storyBolder;


    public StoryListFragment(Context context, int unitNum, int dbNumb){
        this.strContext = context;
        this.unitNumber = unitNum;
        this.dbNum = dbNumb;
    }


    @SuppressLint("SetTextI18n")
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
            unitStoryReceiver(unitNumber);
            handler.post(() ->{
                storyTitleTxt.setText(unitStoryTitle);
                storyImageView.setImageResource(unitImage);
            });});
        serviceThread.execute(() ->{
            wordsListReceiver();
            textViewBolder();
        });
        serviceThread.shutdown();
    }


    private void unitStoryReceiver(int position){
        try {
            UnitSqliteOpenHelper storyDatabase = new UnitSqliteOpenHelper(strContext);
            SQLiteDatabase storyDB = storyDatabase.getReadableDatabase();

            Cursor strCursor = storyDB.query(DB_NOTES.UNIT_TABLE,
                    new String[]{DB_NOTES.UNIT_STORY, DB_NOTES.UNIT_TITLE, DB_NOTES.UNIT_IMG},
                    DB_NOTES.UNIT_ID + " = " + position,
                    null, null, null, null);

            if (strCursor != null && strCursor.getCount() != 0) {
                while (strCursor.moveToNext()) {
                    unitContentStory = strCursor.getString(strCursor.getColumnIndex(DB_NOTES.UNIT_STORY));
                    unitStoryTitle = strCursor.getString(strCursor.getColumnIndex(DB_NOTES.UNIT_TITLE));
                    unitImage = strCursor.getInt(strCursor.getColumnIndex(DB_NOTES.UNIT_IMG));
                }
            }
            assert strCursor != null;
            storyDB.close();
            strCursor.close();
        }catch (SQLiteException ignored){
        }
    }

    private void wordsListReceiver(){
        int i = 0;
        //WordDatabaseOpenHelper wordsListDatabase = new WordDatabaseOpenHelper(strContext);
        if (unitNumber == 0){
            unitNumber = 1;
        }
        SQLiteDatabase db =  wordListDatabase(dbNum).getReadableDatabase();
        wordModelList = new ArrayList<>();

        Cursor cursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitNumber,
                new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                        DB_NOTES.DEFINITION_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD}
                ,null, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                WordModel modelList = new WordModel();
                int wordId = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                int wordImage = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                String wordName = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));
                String wordPhonetic = cursor.getString(cursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));
                String wordDefinition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));
                String wordExample = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));
                String wordTranslateExmpl = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));

                modelList.setId(wordId);
                modelList.setWordImage(wordImage);
                modelList.setWord(wordName);
                modelList.setPhonetic(wordPhonetic);
                modelList.setDefinition(wordDefinition);
                modelList.setExample(wordExample);
                modelList.setTranslateExmpl(wordTranslateExmpl);

                wordModelList.add(modelList);

                wordsList[i] = wordName;
                i++;
            }
        }
        db.close();
        cursor.close();
    }

    private SQLiteOpenHelper wordListDatabase(int databaseNum){
        if (databaseNum == 1){
            return new WordDatabaseBookOne(strContext);
        }else if (databaseNum == 2){
            return new WordDatabaseBookTwo(strContext);
        }else if (databaseNum == 3){
            return new WordDatabaseBookThree(strContext);
        }else if (databaseNum == 4){
            return new WordDatabaseBookFour(strContext);
        }else if (databaseNum == 5){
            return new WordDatabaseBookFive(strContext);
        }else {
            return new WordDatabaseBookSix(strContext);
        }
    }

    private void textViewBolder(){
        try {

            storyBolder = new SpanStringBold(strContext);
            storyBolder.storyWordsBolder(unitContentStory, wordsList, wordModelList);
        }catch (Exception ignored) {
        }
    }

    @Override
    public void onClick(View v) {
        if (!flag) {
            String text = strContext.getString(R.string.story_content);
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
        storyImageView = view.findViewById(R.id.image_Story_fragment);
        storyContentTxtView = view.findViewById(R.id.text_view_content_story);
        ImageButton btn = requireActivity().findViewById(R.id.click_button);
        btn.setOnClickListener(this);
    }

    private void setComponentValues(){
        storyContentTxtView.setText(strContext.getString(R.string.story_content));
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
        //asyncTask.cancel(true);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        //asyncTask.cancel(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}