package com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.WordDatabase.WordDatabaseOpenHelper;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordModel;
import java.util.ArrayList;



public class WordListFragment extends Fragment {
    private final Context wordContext;
    private ArrayList<WordModel> wordList;
    private final int unitNum;
    private WordListFragmentAsyncTask wordListThread;

    public WordListFragment(Context context, int unitNum){
        this.wordContext = context;
        this.unitNum = unitNum;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_word_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        wordListThread = new WordListFragmentAsyncTask(wordContext, wordList, unitNum);
        wordListThread.execute();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        wordListThread.cancel(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        wordListThread.cancel(true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private static class WordListFragmentAsyncTask extends AsyncTask<Void, Void, Void>{
        private final Context context;
        private RecyclerView wordRecyclerView;
        private final int unitNum;
        private ArrayList<WordModel> wordList;


        public WordListFragmentAsyncTask(Context context, ArrayList<WordModel> wordList, int unitNum){
            this.context = context;
            this.wordList = wordList;
            this.unitNum = unitNum;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wordRecyclerView = ((Activity) context).findViewById(R.id.word_list_recyclerview);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            sampleData();
            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            WordListRecyclerView wordAdapter = new WordListRecyclerView(context, wordList, unitNum);
            wordRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            wordAdapter.notifyDataSetChanged();
            wordRecyclerView.setAdapter(wordAdapter);
        }

        private void sampleData(){
            WordDatabaseOpenHelper wordDatabase = new WordDatabaseOpenHelper(context);
            SQLiteDatabase db = wordDatabase.getReadableDatabase();
            wordList = new ArrayList<>();

            Cursor cursor = db.query(DB_NOTES.WORD_TABLE,
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



    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        wordListThread.cancel(true);
    }
}
