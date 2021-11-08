package com.example.a4000essentialwordsbook1.SearchWordsClasses;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookTwo;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SearchWordsActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView searchRecyclerView;
    private SearchWordsAdapter searchAdapter;
    private AppCompatEditText searchEditText;
    private ImageView backButton, clearAllButton;
    private ArrayList<WordModel> searchList;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_words);
        findViewsById();
        searchFunction();
    }



    private void searchFunction() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                clearAllButtonVisibility(sequence);
                String searchWord = sequence.toString();
                searchThreadExecutor(searchWord);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void clearAllButtonVisibility(CharSequence sequence){
        int wordCount = sequence.toString().length();
        if (wordCount != 0){
            clearAllButton.setVisibility(View.VISIBLE);
        }else {
            clearAllButton.setVisibility(View.GONE);
        }

    }




    private void searchThreadExecutor(String searchWord){
        int wordLength = searchWord.length();
        if (wordLength > 0) {
            ExecutorService searchThread = Executors.newSingleThreadExecutor();
            Handler searchHandler = new Handler(Looper.getMainLooper());
            searchThread.execute(() -> {
                callingDataBasesForSearching(searchWord);
                searchHandler.post(this::initializeSearchRecyclerView);
            });
        }else {
            searchList.clear();
            initializeSearchRecyclerView();
        }
    }

    private void callingDataBasesForSearching(String searchWord){
        searchList = new ArrayList<>();
        callingDataBaseBookOneForSearching(searchWord);
        /*callingDataBaseBookTwoForSearching(searchWord);
        callingDataBaseBookThreeForSearching(searchWord);
        callingDataBaseBookFourForSearching(searchWord);
        callingDataBaseBookFiveForSearching(searchWord);
        callingDataBaseBookSixForSearching(searchWord);*/
    }

    private void callingDataBaseBookOneForSearching(String searchWord){
        WordDatabaseBookOne databaseBookOne = new WordDatabaseBookOne(this);
        SQLiteDatabase db = databaseBookOne.getReadableDatabase();
        Cursor searchCursor = null;
        boolean isWordExists;

        for (int i = 1 ; i <= 30 ; i++){
            searchCursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE +i,
                    new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD, DB_NOTES.WORD_IMG,
                            DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD,
                            DB_NOTES.EASY_FLAG, DB_NOTES.HARD_FLAG, DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER},
                    null, null, null, null, null);

            if (searchCursor != null && searchCursor.getCount() !=0){
                while (searchCursor.moveToNext()){
                    WordModel model = new WordModel();

                    String word = searchCursor.getString(searchCursor.getColumnIndex(DB_NOTES.WORD));
                    isWordExists = word.contains(searchWord);
                    if (isWordExists) {
                        queryCursor(searchCursor, model, word);
                    }
                }
            }
        }

        assert searchCursor != null;
        searchCursor.close();
        db.close();
    }

    private SQLiteOpenHelper wordListDatabase(int databaseNum){
        if (databaseNum == 1){
            return new WordDatabaseBookOne(this);
        }else if (databaseNum == 2){
            return new WordDatabaseBookTwo(this);
        }else if (databaseNum == 3){
            return new WordDatabaseBookThree(this);
        }else if (databaseNum == 4){
            return new WordDatabaseBookFour(this);
        }else if (databaseNum == 5){
            return new WordDatabaseBookFive(this);
        }else {
            return new WordDatabaseBookSix(this);
        }
    }

    private void callingDataBaseBookTwoForSearching(String searchWord){
        WordDatabaseBookTwo database = new WordDatabaseBookTwo(this);
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor searchCursor = null;
        boolean isWordExists;

        for (int i = 1 ; i <= 30 ; i++){
            searchCursor = db.query("UNIT_" +i,
                    new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD, DB_NOTES.WORD_IMG,
                            DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD,
                            DB_NOTES.EASY_FLAG, DB_NOTES.HARD_FLAG, DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER},
                    null, null, null, null, null);

            if (searchCursor != null && searchCursor.getCount() !=0){
                while (searchCursor.moveToNext()){
                    WordModel model = new WordModel();

                    String word = searchCursor.getString(searchCursor.getColumnIndex(DB_NOTES.WORD));
                    isWordExists = word.contains(searchWord);
                    if (isWordExists) {
                        queryCursor(searchCursor, model, word);
                    }
                }
            }
        }

        assert searchCursor != null;
        searchCursor.close();
        db.close();

    }
    private void callingDataBaseBookThreeForSearching(String searchWord){
        WordDatabaseBookThree database = new WordDatabaseBookThree(this);
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor searchCursor = null;
        boolean isWordExists;

        for (int i = 1 ; i <= 30 ; i++){
            searchCursor = db.query("UNIT_" +i,
                    new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD, DB_NOTES.WORD_IMG,
                            DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD,
                            DB_NOTES.EASY_FLAG, DB_NOTES.HARD_FLAG, DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER},
                    null, null, null, null, null);

            if (searchCursor != null && searchCursor.getCount() !=0){
                while (searchCursor.moveToNext()){
                    WordModel model = new WordModel();

                    String word = searchCursor.getString(searchCursor.getColumnIndex(DB_NOTES.WORD));
                    isWordExists = word.contains(searchWord);
                    if (isWordExists) {
                        queryCursor(searchCursor, model, word);
                    }
                }
            }
        }

        assert searchCursor != null;
        searchCursor.close();
        db.close();

    }
    private void callingDataBaseBookFourForSearching(String searchWord){
        WordDatabaseBookFour database = new WordDatabaseBookFour(this);
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor searchCursor = null;
        boolean isWordExists;

        for (int i = 1 ; i <= 30 ; i++){
            searchCursor = db.query("UNIT_" +i,
                    new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD, DB_NOTES.WORD_IMG,
                            DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD,
                            DB_NOTES.EASY_FLAG, DB_NOTES.HARD_FLAG, DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER},
                    null, null, null, null, null);

            if (searchCursor != null && searchCursor.getCount() !=0){
                while (searchCursor.moveToNext()){
                    WordModel model = new WordModel();

                    String word = searchCursor.getString(searchCursor.getColumnIndex(DB_NOTES.WORD));
                    isWordExists = word.contains(searchWord);
                    if (isWordExists) {
                        queryCursor(searchCursor, model, word);
                    }
                }
            }
        }

        assert searchCursor != null;
        searchCursor.close();
        db.close();

    }
    private void callingDataBaseBookFiveForSearching(String searchWord){
        WordDatabaseBookFive database = new WordDatabaseBookFive(this);
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor searchCursor = null;
        boolean isWordExists;

        for (int i = 1 ; i <= 30 ; i++){
            searchCursor = db.query("UNIT_" +i,
                    new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD, DB_NOTES.WORD_IMG,
                            DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD,
                            DB_NOTES.EASY_FLAG, DB_NOTES.HARD_FLAG, DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER},
                    null, null, null, null, null);

            if (searchCursor != null && searchCursor.getCount() !=0){
                while (searchCursor.moveToNext()){
                    WordModel model = new WordModel();

                    String word = searchCursor.getString(searchCursor.getColumnIndex(DB_NOTES.WORD));
                    isWordExists = word.contains(searchWord);
                    if (isWordExists) {
                        queryCursor(searchCursor, model, word);
                    }
                }
            }
        }

        assert searchCursor != null;
        searchCursor.close();
        db.close();

    }

    private void callingDataBaseBookSixForSearching(String searchWord){
        WordDatabaseBookSix database = new WordDatabaseBookSix(this);
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor searchCursor = null;
        boolean isWordExists;

        for (int i = 1 ; i <= 30 ; i++){
            searchCursor = db.query("UNIT_" +i,
                    new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD, DB_NOTES.WORD_IMG,
                            DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD,
                            DB_NOTES.EASY_FLAG, DB_NOTES.HARD_FLAG, DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER},
                    null, null, null, null, null);

            if (searchCursor != null && searchCursor.getCount() !=0){
                while (searchCursor.moveToNext()){
                    WordModel model = new WordModel();

                    String word = searchCursor.getString(searchCursor.getColumnIndex(DB_NOTES.WORD));
                    isWordExists = word.contains(searchWord);
                    if (isWordExists) {
                        queryCursor(searchCursor, model, word);
                    }
                }
            }
        }

        assert searchCursor != null;
        searchCursor.close();
        db.close();

    }

    private void queryCursor(Cursor searchCursor, WordModel model, String word){

        String phonetic = searchCursor.getString(searchCursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));
        String translateWord = searchCursor.getString(searchCursor.getColumnIndex(DB_NOTES.TRANSLATE_WORD));
        String definition = searchCursor.getString(searchCursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));
        String translateDefinition = searchCursor.getString(searchCursor.getColumnIndex(DB_NOTES.DEFINITION_TRANSLATE_WORD));
        String example = searchCursor.getString(searchCursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));
        String translateExample = searchCursor.getString(searchCursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));
        int id = searchCursor.getInt(searchCursor.getColumnIndex(DB_NOTES.WORD_ID));
        int image = searchCursor.getInt(searchCursor.getColumnIndex(DB_NOTES.WORD_IMG));
        int hardFlag = searchCursor.getInt(searchCursor.getColumnIndex(DB_NOTES.HARD_FLAG));
        int easyFlag = searchCursor.getInt(searchCursor.getColumnIndex(DB_NOTES.EASY_FLAG));
        int bookNum = searchCursor.getInt(searchCursor.getColumnIndex(DB_NOTES.BOOK_NUMBER));
        int unitNum = searchCursor.getInt(searchCursor.getColumnIndex(DB_NOTES.UNIT_NUMBER));

        model.setId(id);
        model.setWordImage(image);
        model.setHardFlag(hardFlag);
        model.setEasyFlag(easyFlag);
        model.setBookNum(bookNum);
        model.setUnitNum(unitNum);
        model.setWord(word);
        model.setPhonetic(phonetic);
        model.setTranslateWord(translateWord);
        model.setDefinition(definition);
        model.setTranslateDef(translateDefinition);
        model.setExample(example);
        model.setTranslateExmpl(translateExample);

        searchList.add(model);
    }
    private void initializeSearchRecyclerView(){
        SearchWordsAdapter adapter = new SearchWordsAdapter(this, searchList);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchRecyclerView.setAdapter(adapter);
    }




    private void findViewsById(){
        searchRecyclerView = findViewById(R.id.recycler_search_view);
        searchEditText = findViewById(R.id.search_compat_edit_text);
        backButton = findViewById(R.id.search_back_button);
        clearAllButton = findViewById(R.id.search_delete_all_words);
        thisSetOnClickListener();
    }

    private void thisSetOnClickListener(){
        backButton.setOnClickListener(this);
        clearAllButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.search_delete_all_words):
                clearAllSearchEditText();
                break;
            case (R.id.search_back_button):
                onBackPressed();
                break;
        }
    }

    private void clearAllSearchEditText(){
        if (searchEditText.isFocused()){
            searchEditText.setText("");
        }
    }
}
