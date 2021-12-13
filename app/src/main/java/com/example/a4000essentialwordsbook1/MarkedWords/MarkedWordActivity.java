package com.example.a4000essentialwordsbook1.MarkedWords;



import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordSlideCardViewActivity;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.AutoPlayDialogFragment;
import com.example.a4000essentialwordsbook1.Settings.SettingsPreferencesActivity;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.AutoPlayNotes;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MarkedWordActivity extends AppCompatActivity implements View.OnClickListener, MarkedWordRemover, MarkedWordPosition{
    String msg = "4000 Essential word is on :";
    private RecyclerView mrkRecyclerView;
    private ArrayList<WordModel> markedList;
    private RelativeLayout backBtnLayout;
    private final String sDbNumber = ExtraNotes.DB_NUMBER;
    private final String sUnitNumber = ExtraNotes.UNIT_NUMBER;
    private int dbNum, unitNum, menuDbNum;
    private int[] dbInfoList;
    private int rclItemPosition;
    private ImageView settingIcon, autoPlayIcon;
    private RecyclerViewMarkedWords markedWordAdapter;
    private Toolbar markedWordToolbar;
    private final int timeMines = 100;
    private MediaPlayer singleMediaPlayer;
    private final static String markedWordActivity = AutoPlayNotes.MARKED_WORD_ACTIVITY;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marked_words);
        viewsFinderById();
        extrasGetter();
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.marked_word_tab_layout_bck_bttn_layout):
                onBackPressed();
                break;
            case (R.id.marked_word_settings_launcher):
                settingStartActivity();
                break;
            case (R.id.marked_word_auto_play_launcher):
                autoPlayWord();
                Toast.makeText(this, "AutoPlay Is Under Process", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void settingStartActivity(){
        Intent settingIntent = new Intent(this, SettingsPreferencesActivity.class);
        startActivity(settingIntent);
    }
    private void autoPlayWord() {
        AutoPlayDialogFragment playDialogFragment =
                AutoPlayDialogFragment.newInstanceMarkedWordActivity(markedWordActivity, markedList, dbInfoList);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("autoPlayAudio");
        if (prev != null) {
            ft.remove(prev);
        }
        playDialogFragment.show(ft, "autoPlayAudio");
    }


    @Override
    public void recyclerItemPosition(int position) {
        rclItemPosition = position;
    }
    @Override
    public void removeMarkedWord(int dbId, int unitId, int position, int wordId) {
        recyclerDataRemover(position);
        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> removingItem(dbId ,unitId ,wordId));
        Toast.makeText(this, "Item Has Been Removed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void audioPlayer(int position) {
        ExecutorService audioThread = Executors.newSingleThreadExecutor();
        audioThread.execute(() ->{
            singleMediaPlayDestroyer();
            audioPlayerCreator(wordAudioReceiver(dbNum, unitNum));
            singleMediaPlayHandler.post(wordNormalRunnable(position));
        });
    }
    private void singleMediaPlayDestroyer(){
        try {
            if (singleMediaPlayer.isPlaying() && (singleMediaPlayer != null)) {
                singleMediaPlayer.pause();
                singleMediaPlayer.stop();
                singleMediaPlayer.reset();
                singleMediaPlayer.release();

                singleMediaPlayHandler.removeCallbacks(singleAudioThread);

            }
        }catch (Exception e){
            Log.e("MarkedAudioError", "" + e);
        }
    }


    private Runnable wordNormalRunnable(int position){
        return () -> singlePlayAudio(wrdStart(position), wrdDuration(position));
    }
    private int wrdStart(int position){
        final int size = markedList.size();
        return markedList.get(position).getWrdStart();
    }
    private int wrdDuration(int position){return markedList.get(position).getWrdEnd() - wrdStart(position);}


    private void singlePlayAudio(int start, int end) {

        final boolean isPlaying = singleMediaPlayer.isPlaying();
        if (isPlaying) {
            singleMediaPlayHandler.removeCallbacks(singleAudioThread);
            singleMediaPlayer.pause();
            singleMediaPlayer.seekTo(start);
        }
        singleAudioThread = () -> {
            singleMediaPlayer.pause();
            singleMediaPlayer.seekTo(start);
        };
        singleMediaPlayer.seekTo(start);
        singleMediaPlayer.start();

        singleMediaPlayHandler.postDelayed(singleAudioThread, end);
    }
    private final Handler singleMediaPlayHandler = new Handler(Looper.getMainLooper());
    private  Runnable singleAudioThread;

    @SuppressLint("Range")
    private String wordAudioReceiver(int dbNum, int unitNum){
        String audioAddress ="";

        SQLiteDatabase db = unitDatabase(dbNum).getReadableDatabase();

        Cursor cursor = db.query(DB_NOTES.UNIT_TABLE,
                new String[]{DB_NOTES.UNIT_COMPLETE_WORD_AUDIO},
                DB_NOTES.UNIT_ID + " = ? ", new String[]{Integer.toString(unitNum)},
                null, null, null);


        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                audioAddress = cursor.getString(cursor.getColumnIndex(DB_NOTES.UNIT_COMPLETE_WORD_AUDIO));
            }
        }


        assert cursor != null;
        cursor.close();
        db.close();

        return audioAddress;
    }
    private SQLiteOpenHelper unitDatabase(int dbNumber){
        if (dbNumber == 1){
            return new UnitDatabaseBookOne(MarkedWordActivity.this);
        }else if (dbNumber == 2){
            return new UnitDatabaseBookTwo(MarkedWordActivity.this);
        }else if (dbNumber == 3){
            return new UnitDatabaseBookThree(MarkedWordActivity.this);
        }else if (dbNumber == 4){
            return new UnitDatabaseBookFour(MarkedWordActivity.this);
        }else if (dbNumber == 5){
            return new UnitDatabaseBookFive(MarkedWordActivity.this);
        }else {
            return new UnitDatabaseBookSix(MarkedWordActivity.this);
        }
    }

    private void audioPlayerCreator(String plyAudio){
        final File audioDir = new File(Environment.DIRECTORY_DOWNLOADS, File.separator + "4000 Essential Words");

        final File audioMainPath = new File("Audio Files");
        final File audioWordPath = new File(audioMainPath, File.separator + "Word Audios");


        final File audioWordBookPath = new File(audioWordPath, File.separator + "Book_" + dbNum);
        final File secondSubFile = new File(audioWordBookPath, File.separator + "." + new File(plyAudio).getName());

        final String plyPath = Environment.getExternalStoragePublicDirectory(audioDir.toString()).toString() + File.separator + secondSubFile.toString();
        singleMediaPlayer = MediaPlayer.create(MarkedWordActivity.this, Uri.parse(plyPath));
    }


    public void removingItem(int dbId, int unitId, int wordId){
        SQLiteDatabase db = wordListDatabase(dbId).getWritableDatabase();
        ContentValues markedFlagValue = new ContentValues();
        markedFlagValue.put(DB_NOTES.HARD_FLAG, 0);
        db.update(DB_NOTES.NEUTRAL_WORD_TABLE + unitId,
                markedFlagValue, DB_NOTES.WORD_ID + " = ? ", new String[]{Integer.toString(wordId)});
        db.close();
    }

    private void recyclerDataRemover(int position){
        markedList.remove(position);
        markedWordAdapter.notifyItemRemoved(position);
    }



    //Menu Settings Code
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.marked_word_book_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case (R.id.menu_book_all):
                allMarkedWordThread();
                menuDbNum = 0;
                return true;
            case (R.id.menu_book_first):
                specificMarkedWordThread(1);
                menuDbNum = 1;
                return true;
            case (R.id.menu_book_second):
                specificMarkedWordThread(2);
                menuDbNum = 2;
                return true;
            case (R.id.menu_book_third):
                specificMarkedWordThread(3);
                menuDbNum = 3;
                return true;
            case (R.id.menu_book_fourth):
                specificMarkedWordThread(4);
                menuDbNum = 4;
                return true;
            case (R.id.menu_book_fifth):
                specificMarkedWordThread(5);
                menuDbNum = 5;
                return true;
            case (R.id.menu_book_sixth):
                specificMarkedWordThread(6);
                menuDbNum = 6;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void allMarkedWordThread(){
        ExecutorService allWordThread = Executors.newSingleThreadExecutor();
        Handler allWordHandler = new Handler(Looper.getMainLooper());
        allWordThread.execute(() ->{
            allMarkedWordReceiver();
            allWordHandler.post(this::recyclerViewMarkedLogicCode);
        });
    }
    private void specificMarkedWordThread(int dbId){
        ExecutorService spcWordThread = Executors.newSingleThreadExecutor();
        Handler spcHandler = new Handler(Looper.getMainLooper());
        spcWordThread.execute(() ->{
            specificMarkedWordReceiver(dbId);
            spcHandler.post(this::recyclerViewMarkedLogicCode);
        });
    }
    @SuppressLint("Range")
    private void allMarkedWordReceiver(){
        Cursor cursor = null;
        SQLiteDatabase db = null;
        markedList = new ArrayList<>();
        for (int dbId = 1 ; dbId <= 6 ; dbId ++) {
            db = wordListDatabase(dbId).getReadableDatabase();
            for (int unitId = 1; unitId <= 30; unitId++) {

                cursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitId,
                        new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.WORD, DB_NOTES.BOOK_NUMBER,
                                DB_NOTES.UNIT_NUMBER, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                                DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD,
                                DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.HARD_FLAG,
                                DB_NOTES.WORD_START, DB_NOTES.WORD_END, DB_NOTES.DEF_START, DB_NOTES.DEF_END,
                                DB_NOTES.EXMPL_START, DB_NOTES.EXMPL_END, DB_NOTES.EXTRA_NOTE},
                        DB_NOTES.HARD_FLAG + " > ?", new String[]{Integer.toString(0)}, null, null, null);


                if (cursor != null && cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {

                        WordModel listModel = new WordModel();

                        final int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                        final String img = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                        final int hardFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.HARD_FLAG));
                        final int databaseNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.BOOK_NUMBER));
                        final int tableNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.UNIT_NUMBER));
                        final int wordStat = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_START));
                        final int wordEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_END));
                        final int defStart = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_START));
                        final int defEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_END));
                        final int exmplStart = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_START));
                        final int exmplEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_END));
                        final String word = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));
                        final String phonetic = cursor.getString(cursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));
                        final String translate_word = cursor.getString(cursor.getColumnIndex(DB_NOTES.TRANSLATE_WORD));
                        final String definition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));
                        final String translateDef = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_TRANSLATE_WORD));
                        final String example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));
                        final String translate_example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));
                        final String usetNote = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXTRA_NOTE));

                        listModel.setId(id);
                        listModel.setImgUri(img);
                        listModel.setHardFlag(hardFlag);
                        listModel.setBookNum(databaseNum);
                        listModel.setUnitNum(tableNum);
                        listModel.setWrdStart(wordStat - timeMines);
                        listModel.setWrdEnd(wordEnd - timeMines);
                        listModel.setDefStart(defStart - timeMines);
                        listModel.setDefEnd(defEnd - timeMines);
                        listModel.setExmplStart(exmplStart - timeMines);
                        listModel.setExmplEnd(exmplEnd - timeMines);
                        listModel.setWord(word);
                        listModel.setPhonetic(phonetic);
                        listModel.setTranslateWord(translate_word);
                        listModel.setDefinition(definition);
                        listModel.setTranslateDef(translateDef);
                        listModel.setExample(example);
                        listModel.setTranslateExmpl(translate_example);
                        listModel.setAddNote(usetNote);
                        markedList.add(listModel);
                    }
                }
            }
        }
        assert cursor != null;
        cursor.close();
        db.close();

    }
    @SuppressLint("Range")
    private void specificMarkedWordReceiver(int dbId){
        Cursor cursor = null;
        markedList = new ArrayList<>();
        SQLiteDatabase db = wordListDatabase(dbId).getReadableDatabase();

        for (int unitId = 1 ; unitId <= 30 ; unitId ++) {

            cursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitId,
                    new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.WORD, DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER,
                            DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD, DB_NOTES.DEFINITION_WORD,
                            DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD,
                            DB_NOTES.HARD_FLAG, DB_NOTES.WORD_START, DB_NOTES.WORD_END, DB_NOTES.DEF_START,
                            DB_NOTES.DEF_END, DB_NOTES.EXMPL_START, DB_NOTES.EXMPL_END, DB_NOTES.EXTRA_NOTE},
                    DB_NOTES.HARD_FLAG + " > ?", new String[]{Integer.toString(0)}, null, null, null);


            if (cursor != null && cursor.getCount() != 0) {
                while (cursor.moveToNext()) {

                    WordModel listModel = new WordModel();

                    final int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                    final String img = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                    final int databaseId = cursor.getInt(cursor.getColumnIndex(DB_NOTES.BOOK_NUMBER));
                    final int tableId = cursor.getInt(cursor.getColumnIndex(DB_NOTES.UNIT_NUMBER));
                    final int hardFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.HARD_FLAG));
                    final int wordStat = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_START));
                    final int wordEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_END));
                    final int defStart = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_START));
                    final int defEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_END));
                    final int exmplStart = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_START));
                    final int exmplEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_END));
                    final String word = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));
                    final String phonetic = cursor.getString(cursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));
                    final String translate_word = cursor.getString(cursor.getColumnIndex(DB_NOTES.TRANSLATE_WORD));
                    final String definition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));
                    final String translateDef = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_TRANSLATE_WORD));
                    final String example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));
                    final String translate_example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));
                    final String usetNote = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXTRA_NOTE));

                    listModel.setId(id);
                    listModel.setImgUri(img);
                    listModel.setBookNum(databaseId);
                    listModel.setUnitNum(tableId);
                    listModel.setHardFlag(hardFlag);
                    listModel.setWrdStart(wordStat - timeMines);
                    listModel.setWrdEnd(wordEnd - timeMines);
                    listModel.setDefStart(defStart - timeMines);
                    listModel.setDefEnd(defEnd - timeMines);
                    listModel.setExmplStart(exmplStart - timeMines);
                    listModel.setExmplEnd(exmplEnd - timeMines);
                    listModel.setWord(word);
                    listModel.setPhonetic(phonetic);
                    listModel.setTranslateWord(translate_word);
                    listModel.setDefinition(definition);
                    listModel.setTranslateDef(translateDef);
                    listModel.setExample(example);
                    listModel.setTranslateExmpl(translate_example);
                    listModel.setAddNote(usetNote);
                    markedList.add(listModel);
                }
            }
        }

        assert cursor != null;
        cursor.close();
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

    private void recyclerViewMarkedLogicCode(){
        markedWordAdapter =
                new RecyclerViewMarkedWords(MarkedWordActivity.this, markedList, dbInfoList, this, this);
        mrkRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mrkRecyclerView.setAdapter(markedWordAdapter);
        //mrkRecyclerView.scrollToPosition(itemPosition(rclItemPosition));

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        final int orientation = newConfig.orientation;
        Toast.makeText(this, "" + orientation, Toast.LENGTH_LONG).show();
    }

    private int itemPosition(int rclItemPosition){
        if (rclItemPosition > 0){
            return (rclItemPosition - 1);
        }else {
            return rclItemPosition;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        onStartDataReceiver();
    }

    private void onStartDataReceiver(){
        if (menuDbNum == 0) {
            allMarkedWordThread();
        }else {
            specificMarkedWordThread(menuDbNum);
        }
    }

    private void extrasGetter(){
        dbInfoList = new int[2];
        Intent dbIntent = getIntent();
        Intent unitIntent = getIntent();

        dbNum = dbIntent.getIntExtra(sDbNumber, 1);
        unitNum = unitIntent.getIntExtra(sUnitNumber, 1);
        dbInfoList[0] = dbNum;
        dbInfoList[1] = unitNum;
    }

    private void viewsFinderById(){
        mrkRecyclerView = findViewById(R.id.marked_word_recyclerview);
        backBtnLayout = findViewById(R.id.marked_word_tab_layout_bck_bttn_layout);
        settingIcon = findViewById(R.id.marked_word_settings_launcher);
        autoPlayIcon = findViewById(R.id.marked_word_auto_play_launcher);
        markedWordToolbar = findViewById(R.id.marked_word_toolbar);
        setSupportActionBar(markedWordToolbar);
        thisViewClickListener();
    }

    private void thisViewClickListener(){
        backBtnLayout.setOnClickListener(this);
        settingIcon.setOnClickListener(this);
        autoPlayIcon.setOnClickListener(this);

    }
}
