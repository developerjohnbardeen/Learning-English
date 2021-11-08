package com.example.a4000essentialwordsbook1.MarkedWords;



import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookTwo;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.AutoPlayDialogFragment;
import com.example.a4000essentialwordsbook1.Settings.SettingsPreferencesActivity;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.AutoPlayNotes;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;
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
    private void allMarkedWordReceiver(){
        Cursor cursor = null;
        SQLiteDatabase db = null;
        markedList = new ArrayList<>();
        for (int dbId = 1 ; dbId <= 6 ; dbId ++) {
            db = wordListDatabase(dbId).getReadableDatabase();
            for (int unitId = 1; unitId <= 30; unitId++) {

                cursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitId,
                        new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.WORD, DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER,
                                DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                                DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.HARD_FLAG,
                                DB_NOTES.WORD_START, DB_NOTES.WORD_END, DB_NOTES.DEF_START, DB_NOTES.DEF_END, DB_NOTES.EXMPL_START, DB_NOTES.EXMPL_END},
                        DB_NOTES.HARD_FLAG + " > ?", new String[]{Integer.toString(0)}, null, null, null);


                if (cursor != null && cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {

                        WordModel listModel = new WordModel();
                        int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                        int img = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                        int hardFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.HARD_FLAG));
                        int databaseNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.BOOK_NUMBER));
                        int tableNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.UNIT_NUMBER));
                        int wordStat = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_START));
                        int wordEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_END));
                        int defStart = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_START));
                        int defEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_END));
                        int exmplStart = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_START));
                        int exmplEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_END));
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
                        listModel.setBookNum(databaseNum);
                        listModel.setUnitNum(tableNum);
                        listModel.setWrdStart(wordStat - 500);
                        listModel.setWrdEnd(wordEnd - 500);
                        listModel.setDefStart(defStart - 500);
                        listModel.setDefEnd(defEnd - 500);
                        listModel.setExmplStart(exmplStart - 500);
                        listModel.setExmplEnd(exmplEnd - 500);
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
            }
        }
        assert cursor != null;
        cursor.close();
        db.close();

    }
    private void specificMarkedWordReceiver(int dbId){
        Cursor cursor = null;
        markedList = new ArrayList<>();
        SQLiteDatabase db = wordListDatabase(dbId).getReadableDatabase();

        for (int unitId = 1 ; unitId <= 30 ; unitId ++) {

            cursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitId,
                    new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.WORD, DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER,
                            DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                            DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.HARD_FLAG,
                            DB_NOTES.WORD_START, DB_NOTES.WORD_END, DB_NOTES.DEF_START, DB_NOTES.DEF_END, DB_NOTES.EXMPL_START, DB_NOTES.EXMPL_END,},
                    DB_NOTES.HARD_FLAG + " > ?", new String[]{Integer.toString(0)}, null, null, null);


            if (cursor != null && cursor.getCount() != 0) {
                while (cursor.moveToNext()) {

                    WordModel listModel = new WordModel();
                    int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                    int img = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                    int databaseId = cursor.getInt(cursor.getColumnIndex(DB_NOTES.BOOK_NUMBER));
                    int tableId = cursor.getInt(cursor.getColumnIndex(DB_NOTES.UNIT_NUMBER));
                    int hardFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.HARD_FLAG));
                    int wordStat = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_START));
                    int wordEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_END));
                    int defStart = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_START));
                    int defEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_END));
                    int exmplStart = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_START));
                    int exmplEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_END));
                    String word = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));
                    String phonetic = cursor.getString(cursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));
                    String translate_word = cursor.getString(cursor.getColumnIndex(DB_NOTES.TRANSLATE_WORD));
                    String definition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));
                    String translateDef = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_TRANSLATE_WORD));
                    String example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));
                    String translate_example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));

                    listModel.setId(id);
                    listModel.setWordImage(img);
                    listModel.setBookNum(databaseId);
                    listModel.setUnitNum(tableId);
                    listModel.setHardFlag(hardFlag);
                    listModel.setWrdStart(wordStat);
                    listModel.setWrdEnd(wordEnd);
                    listModel.setDefStart(defStart);
                    listModel.setDefEnd(defEnd);
                    listModel.setExmplStart(exmplStart);
                    listModel.setExmplEnd(exmplEnd);
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
