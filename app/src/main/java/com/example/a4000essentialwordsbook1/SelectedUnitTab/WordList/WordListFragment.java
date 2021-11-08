package com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookTwo;
import com.example.a4000essentialwordsbook1.MarkedWords.MarkedWordActivity;
import com.example.a4000essentialwordsbook1.Settings.SettingsPreferencesActivity;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WordListFragment extends Fragment{

    private ArrayList<WordModel> wordList;
    private int unitNum, dbNum;
    private RecyclerView wordRecyclerView;

    public WordListFragment( int unitNum, int dbNumb){
        this.unitNum = unitNum;
        this.dbNum = dbNumb;
    }


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
    }


    private void viewFinderById(View view){
        wordRecyclerView = view.findViewById(R.id.word_list_recyclerview);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.word_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.item_0):
                settingStartActivity();
                return true;
            case (R.id.item_1):
                reviewMarkedWordStartActivity();
                return true;
            case (R.id.item_2):
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
    public void onResume() {
        wordFragmentThread();
        super.onResume();
    }
    private void wordFragmentThread(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            WordListDataReceiver();
            WordListRecyclerView wordAdapter = new WordListRecyclerView(requireActivity(), wordList, unitNum, dbNum);
            handler.post(() -> {
                wordRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
                wordRecyclerView.setAdapter(wordAdapter);
            });
        });
    }
    private void WordListDataReceiver(){
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
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
