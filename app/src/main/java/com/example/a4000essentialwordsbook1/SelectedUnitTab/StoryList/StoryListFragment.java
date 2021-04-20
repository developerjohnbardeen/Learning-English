package com.example.a4000essentialwordsbook1.SelectedUnitTab.StoryList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.WordDatabase.WordDatabaseOpenHelper;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordModel;
import com.example.a4000essentialwordsbook1.SpannableString.SpanStringBold;
import com.example.a4000essentialwordsbook1.UnitSqliteOpenHelper;

import java.util.ArrayList;

public class StoryListFragment extends Fragment{
    private final Context strContext ;
    private TextView storyContentTxtView, storyTitleTxt;
    private ImageView storyImageView;
    private ImageButton btn;
    private String unitContentStory, unitStoryTitle;
    private final int unitNumber;
    private int  unitImage;
    private PerAsyncTask asyncTask;
    private final String[] wordsList = new String[20];
    private final ArrayList<WordModel> wordModelList = new ArrayList<>();
    private final Runnable wordsListThread = this::wordsListReceiver;


    public StoryListFragment(Context context, int unitNum){
        this.strContext = context;
        this.unitNumber = unitNum;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_story, parent, false);
        Runnable storyThread = () -> unitStoryReceiver(unitNumber);
        storyThread.run();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        viewsFinderById(view);
        setComponentValues();
        wordsListThread.run();
        asyncTask = new PerAsyncTask(strContext, unitContentStory,wordsList, wordModelList);
        asyncTask.execute();
    }



    private void unitStoryReceiver(int position){
        UnitSqliteOpenHelper storyDatabase = new UnitSqliteOpenHelper(strContext);
        SQLiteDatabase storyDB = storyDatabase.getReadableDatabase();

        Cursor strCursor = storyDB.query(DB_NOTES.UNIT_TABLE,
                new String[]{DB_NOTES.UNIT_STORY, DB_NOTES.UNIT_TITLE, DB_NOTES.UNIT_IMG},
                DB_NOTES.UNIT_ID + " = " + position,
                null, null, null, null);

        if (strCursor != null && strCursor.getCount() != 0){
            while (strCursor.moveToNext()){
                unitContentStory = strCursor.getString(strCursor.getColumnIndex(DB_NOTES.UNIT_STORY));
                unitStoryTitle = strCursor.getString(strCursor.getColumnIndex(DB_NOTES.UNIT_TITLE));
                unitImage = strCursor.getInt(strCursor.getColumnIndex(DB_NOTES.UNIT_IMG));
            }
        }
        assert strCursor != null;
        storyDB.close();
        strCursor.close();
    }

    private void viewsFinderById(View view){
        storyTitleTxt = view.findViewById(R.id.title_story);
        storyImageView = view.findViewById(R.id.image_Story_fragment);
        storyContentTxtView = view.findViewById(R.id.text_view_content_story);
        btn = requireActivity().findViewById(R.id.click_button);
    }

    private void setComponentValues(){
        storyContentTxtView.setText(strContext.getString(R.string.story_content));
        storyTitleTxt.setText(unitStoryTitle);
        storyImageView.setImageResource(unitImage);
    }

    private void wordsListReceiver(){
        int i = 0;
        WordDatabaseOpenHelper wordsListDatabase = new WordDatabaseOpenHelper(strContext);
        SQLiteDatabase db = wordsListDatabase.getReadableDatabase();

        Cursor cursor = db.query(DB_NOTES.WORD_TABLE,
                new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                        DB_NOTES.DEFINITION_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD}
                ,null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() != 0){
            while (cursor.moveToNext()){
                WordModel modelList = new WordModel();
                int wordId = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                int wordImage = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_IMG));;
                String wordName = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));;
                String wordPhonetic = cursor.getString(cursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));;
                String wordDefinition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));;
                String wordExample = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));;
                String wordTranslateExmpl = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));;

                modelList.setId(wordId);
                modelList.setWordImage(wordImage);
                modelList.setWord(wordName);
                modelList.setPhonetic(wordPhonetic);
                modelList.setDefinition(wordDefinition);
                modelList.setExample(wordExample);
                modelList.setTranslateExmpl(wordTranslateExmpl);

                wordModelList.add(modelList);

                wordsList[i] = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));
                i++;
            }
        }

        assert cursor != null;
        db.close();
        cursor.close();
    }



    private class PerAsyncTask extends AsyncTask<Void, Void, Void> implements  View.OnClickListener{
        private final String txt;
        private final Context context;
        private SpanStringBold storyBolder;
        private final String[] wordsList;
        private final ArrayList<WordModel> modelList;
        private boolean flag = true;


        public PerAsyncTask(Context context, String txt, String[] wordList, ArrayList<WordModel> list){
            this.context = context;
            this.txt = txt;
            this.wordsList = wordList;
            this.modelList = list;
        }

        @Override
        public void onPreExecute(){
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            storyBolder = new SpanStringBold(context);
            storyBolder.storyWordsBolder(txt, wordsList, modelList);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            btn = ((Activity) context).findViewById(R.id.click_button);
            btn.setOnClickListener(this);
            storyBolder.persianFinder.cancel(true);
        }

        @Override
        public void onClick(View v) {
            if (!flag) {
                String text = context.getString(R.string.story_content);
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
        asyncTask.cancel(true);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        asyncTask.cancel(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}