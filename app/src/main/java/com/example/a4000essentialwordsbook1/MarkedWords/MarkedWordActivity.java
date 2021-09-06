package com.example.a4000essentialwordsbook1.MarkedWords;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordSlideCardViewActivity;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.DataBases.WordDatabaseOpenHelper;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import java.util.ArrayList;




public class MarkedWordActivity extends AppCompatActivity implements View.OnClickListener {
    String msg = "4000 Essential word is on :";
    private RecyclerView mrkRecyclerView;
    private ArrayList<WordModel> markedList;
    private RelativeLayout backBtnLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marked_words);
        viewsFinderById();
        new MarkedAsyncThread().execute();
    }



    private class MarkedAsyncThread extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            initialization();
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            RecyclerViewMarkedWords markedWordAdapter = new RecyclerViewMarkedWords(MarkedWordActivity.this, markedList);
            mrkRecyclerView.setLayoutManager(new LinearLayoutManager(MarkedWordActivity.this));
            mrkRecyclerView.setAdapter(markedWordAdapter);
            //mrkRecyclerView.scrollToPosition(15);
        }

        private void initialization(){
            WordDatabaseOpenHelper wordDatabase = new WordDatabaseOpenHelper(MarkedWordActivity.this);
            SQLiteDatabase db = wordDatabase.getReadableDatabase();
            markedList = new ArrayList<>();

            Cursor cursor = db.query(DB_NOTES.WORD_TABLE,
                    new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                            DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.HARD_FLAG},
                    DB_NOTES.HARD_FLAG + " > ?", new String[]{Integer.toString(0)}, null, null, null);


            if (cursor != null && cursor.getCount() != 0){
                while (cursor.moveToNext()){

                    WordModel listModel = new WordModel();
                    int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                    int img = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                    int hardFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.HARD_FLAG));
                    String word = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));
                    String phonetic = cursor.getString(cursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));
                    String translate_word = cursor.getString(cursor.getColumnIndex(DB_NOTES.TRANSLATE_WORD));
                    String definition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));
                    String translateDef = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_TRANSLATE_WORD));
                    String example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));
                    String translate_example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));

                    listModel.setId(id);
                    listModel.setWordImage(img);
                    listModel.setHardFlag(hardFlag);
                    listModel.setWord(word);
                    listModel.setPhonetic(phonetic);
                    listModel.setTranslateWord(translate_word);
                    listModel.setDefinition(definition);
                    listModel.setTranslateDef(translateDef);
                    listModel.setExample(example);
                    listModel.setTranslateExmpl(translate_example);

                    markedList.add(listModel);
                }
            }
            assert cursor != null;
            cursor.close();
            db.close();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            cancel(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void viewsFinderById(){
        mrkRecyclerView = findViewById(R.id.marked_word_recyclerview);
        backBtnLayout = findViewById(R.id.marked_word_tab_layout_bck_bttn_layout);
        thisViewClickListener();
    }

    private void thisViewClickListener(){
        backBtnLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.marked_word_tab_layout_bck_bttn_layout):
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        new MarkedAsyncThread().execute();
    }


}
