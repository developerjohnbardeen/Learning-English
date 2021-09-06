package com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.DataBases.WordDatabaseOpenHelper;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WordListFragment extends Fragment {
    private final Context wordContext;
    private ArrayList<WordModel> wordList;
    private int unitNum, dbNum;
    private RecyclerView wordRecyclerView;

    public WordListFragment(Context context, int unitNum, int dbNumb){
        this.wordContext = context;
        this.unitNum = unitNum;
        this.dbNum = dbNumb;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_word_list, container, false);
        viewFinderById(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        wordFragmentThread();
    }


    @Override
    public void onResume() {
        super.onResume();
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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }




    private void wordFragmentThread(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            WordListDataReceiver();
            handler.post(() -> {
                WordListRecyclerView wordAdapter = new WordListRecyclerView(wordContext, wordList, unitNum, dbNum);
                wordRecyclerView.setLayoutManager(new GridLayoutManager(wordContext, 2));
                wordAdapter.notifyDataSetChanged();
                wordRecyclerView.setAdapter(wordAdapter);
            });
        });
    }


    private void WordListDataReceiver(){
        //WordDatabaseOpenHelper wordDatabase = new WordDatabaseOpenHelper(wordContext);
        if (unitNum == 0){
            unitNum = 1;
        }
        SQLiteDatabase db = wordListDatabase(dbNum).getReadableDatabase();
        wordList = new ArrayList<>();

        Cursor cursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitNum,
                new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD ,DB_NOTES.TRANSLATE_WORD ,
                        DB_NOTES.DEFINITION_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.AUDIO_WORD},
                null, null, null, null, null);


        if (cursor != null && cursor.getCount() != 0){
            while (cursor.moveToNext()){

                WordModel listModel =  new WordModel();

                int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                int wordImg = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                int audio = cursor.getInt(cursor.getColumnIndex(DB_NOTES.AUDIO_WORD));
                String word = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));
                String phonetic = cursor.getString(cursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));
                String translate_word = cursor.getString(cursor.getColumnIndex(DB_NOTES.TRANSLATE_WORD));
                String definition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));
                String example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));
                String translate_example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));

                listModel.setId(id);
                listModel.setWordImage(wordImg);
                listModel.setAudio(audio);
                listModel.setWord(word);
                listModel.setPhonetic(phonetic);
                listModel.setTranslateWord(translate_word);
                listModel.setDefinition(definition);
                listModel.setExample(example);
                listModel.setTranslateExmpl(translate_example);
                wordList.add(listModel);
            }
        }
        assert cursor != null;
        cursor.close();
        db.close();
    }

    private SQLiteOpenHelper wordListDatabase(int databaseNum){
        if (databaseNum == 1){
            return new WordDatabaseBookOne(wordContext);
        }else if (databaseNum == 2){
            return new WordDatabaseBookTwo(wordContext);
        }else if (databaseNum == 3){
            return new WordDatabaseBookThree(wordContext);
        }else if (databaseNum == 4){
            return new WordDatabaseBookFour(wordContext);
        }else if (databaseNum == 5){
            return new WordDatabaseBookFive(wordContext);
        }else {
            return new WordDatabaseBookSix(wordContext);
        }
    }


    private void viewFinderById(View view){
        wordRecyclerView = view.findViewById(R.id.word_list_recyclerview);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
