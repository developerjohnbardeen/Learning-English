  package com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.example.a4000essentialwordsbook1.AudioTimeUtil.TimeUtil;
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
import com.example.a4000essentialwordsbook1.Linsteners.DefinitionPlayListener;
import com.example.a4000essentialwordsbook1.Linsteners.ExamplePlayListener;
import com.example.a4000essentialwordsbook1.Linsteners.PlaySingleTrackInterface;
import com.example.a4000essentialwordsbook1.Linsteners.TextProvider;
import com.example.a4000essentialwordsbook1.Linsteners.WordPlayListener;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.MarkedWords.MarkedWordActivity;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.UpdateDatabases.CheckWordDatabase;
import com.example.a4000essentialwordsbook1.UpdateDatabases.UpdateWordDatabase;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WordSlideCardViewActivity extends AppCompatActivity implements View.OnClickListener,
        TextProvider, WordPlayListener, DefinitionPlayListener, ExamplePlayListener {
    private ViewPager wordViewPager;

    //CardView Container Components
    private TimeUtil vpUtils;
    private MediaPlayer wordMediaPlayer, defMediaPlayer, exmplMediaPlayer;
    private ArrayList<Integer> markedFlagList, startList, initAudioList;
    private ArrayList<WordModel> listWordModel;
    private CardView vpCardViewContainer;
    private ImageView vpPlyImg, reviewImg;
    private SeekBar vpSeekBar;
    private TextView vpTxtDuration, vpTxtTotalTime, sample;
    private long currentPos, intElapsedTime = 0, intTotalTime = 0;
    private View vpView;
    private int wordId, unitNum, dbNum;
    private int plyAudio = R.raw.a;
    private Button hardBtn, easyBtn, fBtn;
    private ImageButton  translateBtn;
    private TabLayout tabIndicator;
    //private WordListSliderThread sliderThread;
    public SendFlagInterface sendFlagInterface;
    private WordSlideFragmentPagerAdapter sectionAdapter;
    private RelativeLayout backPressLayout;
    private boolean translateFlag = false ;
    private int strtWord, endWord, strtDef, endDef, strtExmpl, endExmpl;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_cardivew_viewpager_activity);
        allFunctions();
    }

    private void allFunctions(){
        //Function's order matters
        //sampleList();//dum method
        extrasGetter();
        viewsFinderById();
        viesOnClickListener();
        threadsRunners();
        allAudioPlayers();
    }

    private void allAudioPlayers(){
        wordMediaPlayer = MediaPlayer.create(this, plyAudio);
        defMediaPlayer = MediaPlayer.create(this, plyAudio);
        exmplMediaPlayer = MediaPlayer.create(this, plyAudio);
    }


    /************************************************************************************************/

   /* public void wordAudioPlayer(){
        vpUtils = new TimeUtil();
        wordMediaPlayer = MediaPlayer.create(this, plyAudio);
        intTotalTime = wordMediaPlayer.getDuration();
        vpSeekBar.setMax((int) intTotalTime);

        vpSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                currentPos = seekBar.getProgress();
                wordMediaPlayer.seekTo((int) currentPos);
            }
        });
        vpSeekBar.setClickable(true);
        vpSeekBar.setFocusable(true);
        vpSeekBar.setEnabled(true);
    }

    public void wordPlayAudio(View view){
        wordMediaPlayer.start();
        intElapsedTime = wordMediaPlayer.getCurrentPosition();
        vpSeekBar.setProgress((int) intElapsedTime);
        wordVPDurationHandler.postDelayed( wordUpdateSeekBar,10);
        vpPlyImg.setImageResource(R.drawable.mx_pause_normal);
    }
    private final Handler wordVPDurationHandler= new Handler(Looper.getMainLooper());
    private final Runnable wordUpdateSeekBar = new Runnable() {
        @Override
        public void run() {
            intElapsedTime = wordMediaPlayer.getCurrentPosition();
            vpSeekBar.setProgress((int) intElapsedTime);
            String vpStringTotalTime = "" + vpUtils.milliSecondsToTimer(intTotalTime);
            String vpLeftTime = "" + vpUtils.milliSecondsToTimer(intElapsedTime);

            vpTxtDuration.setText(vpLeftTime);
            vpTxtTotalTime.setText(vpStringTotalTime);
            wordVPDurationHandler.postDelayed(this, 10);
        }
    };
    public void wordPause(View view) {
        if (wordMediaPlayer != null) {
            wordMediaPlayer.pause();
            vpPlyImg.setImageResource(R.drawable.mx_play_normal);
        }
    }


    private void wordTrackPlayer(int start, int end){
        boolean isPlaying = wordMediaPlayer.isPlaying();

        if (isPlaying){
            wordHandler.removeCallbacks(wordTracKThread);
            wordMediaPlayer.pause();
            wordMediaPlayer.seekTo(start);
        }

        wordMediaPlayer.seekTo(start);
        wordMediaPlayer.start();

        wordHandler.postDelayed(wordTracKThread, end);
        wordTracKThread = () -> {
            wordMediaPlayer.pause();
            wordMediaPlayer.seekTo(start);
        };

    }
    private final Handler wordHandler = new Handler(Looper.getMainLooper());
    private Runnable wordTracKThread;*/

    private void wordPlayAudio(int start, int end) {
        boolean isPlaying = wordMediaPlayer.isPlaying();

        if (isPlaying){
            wordHandler.removeCallbacks(audioThread);
            wordMediaPlayer.pause();
            wordMediaPlayer.seekTo(start);
        }
        audioThread = () -> {
            wordMediaPlayer.pause();
            wordMediaPlayer.seekTo(start);
        };
        wordMediaPlayer.seekTo(start);
        wordMediaPlayer.start();
        wordHandler.postDelayed(audioThread, end);
    }
    private final Handler wordHandler = new Handler(Looper.getMainLooper());
    private  Runnable audioThread;

    private void releaseMediaPlayer(){
        wordHandler.removeCallbacks(audioThread);
        wordMediaPlayer.stop();
        wordMediaPlayer.reset();
        wordMediaPlayer.release();
    }

    /************************************************************************************************/

    private void defPlayAudio(int start, int end) {
        boolean isPlaying = defMediaPlayer.isPlaying();

        if (isPlaying){
            defHandler.removeCallbacks(defAudioThread);
            defMediaPlayer.pause();
            defMediaPlayer.seekTo(start);
        }
        defAudioThread = () -> {
            defMediaPlayer.pause();
            defMediaPlayer.seekTo(start);
        };

        defMediaPlayer.seekTo(start);
        defMediaPlayer.start();
        defHandler.postDelayed(defAudioThread, end);
    }
    private final Handler defHandler = new Handler(Looper.getMainLooper());
    private  Runnable defAudioThread;


    /************************************************************************************************/

    private void exmplPlayAudio(int start, int end) {
        boolean isPlaying = exmplMediaPlayer.isPlaying();

        if (isPlaying){
            exmplHandler.removeCallbacks(exmplAudioThread);
            exmplMediaPlayer.pause();
            exmplMediaPlayer.seekTo(start);
        }
        exmplAudioThread = () -> {
            exmplMediaPlayer.pause();
            exmplMediaPlayer.seekTo(start);
        };
        exmplMediaPlayer.seekTo(start);
        exmplMediaPlayer.start();
        exmplHandler.postDelayed(exmplAudioThread, end);
    }
    private final Handler exmplHandler = new Handler(Looper.getMainLooper());
    private  Runnable exmplAudioThread;

    /************************************************************************************************/



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.word_detailed_card_ply_image):
                /*if (vpMediaPlayer.isPlaying()) {
                    pause(view);
                } else {
                    playAudio(view);
                }*/
                break;
            case (R.id.slide_review_launcher):
                Intent intent = new Intent(this, MarkedWordActivity.class);
                startActivity(intent);
                break;
            case (R.id.hard_button):
                updateWordDatabaseThread(1);
                break;
            case (R.id.easy_button):
                updateWordDatabaseThread(0);
                break;
            case (R.id.word_detail_all_translate_btn):
                translateFlagChecker();
                break;
            case (R.id.slide_cardview_tab_layout_bck_bttn_layout):
                onBackPressed();
                break;
            case (R.id.slide_card_view_unit):
                callBack();
                break;
        }
    }

    private void callBack(){
        int itemPosition = (wordViewPager.getCurrentItem() + 1);
        wordViewPager.setCurrentItem( itemPosition,true);
    }

    private void translateFlagChecker(){
        if (!translateFlag){
            sendFlagInterface.sendFlag(false);
            translateFlag = true;
        }else {
            sendFlagInterface.sendFlag(true);
            translateFlag = false;
        }
    }


    private void updateWordDatabaseThread(int value){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() ->updateWordDatabase(value));
        executor.shutdown();
    }
    public void updateWordDatabase(int value){
        int position = (wordViewPager.getCurrentItem() + 1);
        String flagColumn = DB_NOTES.HARD_FLAG;
        UpdateWordDatabase updateWordDB = new UpdateWordDatabase(this);
        updateWordDB.wordDatabaseUpdate(flagColumn, position, value);
    }


    private void threadsRunners(){
        flagCheckerThread();
        wordListSliderThread();
    }

    private void flagCheckerThread(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            flagChecker();
            handler.post(() -> {
            });
        });
        executor.shutdown();
    }
    public void flagChecker(){
        try {
            CheckWordDatabase databaseChecker = new CheckWordDatabase(this);
            String table = (DB_NOTES.NEUTRAL_WORD_TABLE + unitNum);
            String columnId = DB_NOTES.WORD_ID;
            String itemCheck = DB_NOTES.HARD_FLAG;
            databaseChecker.favoriteChecker(table, columnId, itemCheck);
            markedFlagList = new ArrayList<>();
            markedFlagList = databaseChecker.getMarkedIntValue();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void wordListSliderThread(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ExecutorService audioExecutor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            wordDatabaseReceiver();

            handler.post(() -> {
                FragmentManager manager = getSupportFragmentManager();
                sectionAdapter = new WordSlideFragmentPagerAdapter(
                        WordSlideCardViewActivity.this, listWordModel, markedFlagList,
                        manager , wordId, this , this, this, this);
                wordViewPager.setAdapter(sectionAdapter);


                wordViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    boolean firstFlag = true;

                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        if ((firstFlag && positionOffset == 0) && positionOffsetPixels == 0 && position == 0){
                            onPageSelected(0);
                            firstFlag = false;
                        }
                    }

                    @Override
                    public void onPageSelected(int position) {
                        audioTimeLine(position);
                    }
                    @Override
                    public void onPageScrollStateChanged(int state) {}
                });

                wordViewPager.setCurrentItem(wordId, true);
            });
        });
        audioExecutor.execute(() -> wordAudioReceiver(unitNum));
    }
    private void wordAudioReceiver(int position){
        //UnitSqliteOpenHelper wordAudioDatabase = new UnitSqliteOpenHelper(this);
        SQLiteDatabase db = unitListDatabase(unitNum).getReadableDatabase();

        Cursor awCursor = db.query(DB_NOTES.UNIT_TABLE,
                new String[]{DB_NOTES.UNIT_COMPLETE_WORD_AUDIO},
                DB_NOTES.UNIT_ID + " = ? ", new String[]{Integer.toString(position)},
                null, null, null);

        if (awCursor != null && awCursor.getCount() != 0){
            while (awCursor.moveToNext()){
                plyAudio = awCursor.getInt(awCursor.getColumnIndex(DB_NOTES.UNIT_COMPLETE_WORD_AUDIO));
            }
        }
        assert awCursor != null;
        awCursor.close();
        db.close();
    }
    public void wordDatabaseReceiver(){
        //WordDatabaseOpenHelper vpWordDatabase = new WordDatabaseOpenHelper(WordSlideCardViewActivity.this);
        if (unitNum == 0) {
            unitNum = 1;
        }
        SQLiteDatabase database = wordListDatabase(dbNum).getReadableDatabase();
        listWordModel = new ArrayList<>();

        Cursor cursor = database.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitNum,
                new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                        DB_NOTES.WORD_START, DB_NOTES.WORD_END, DB_NOTES.DEF_START, DB_NOTES.DEF_END, DB_NOTES.EXMPL_START, DB_NOTES.EXMPL_END,
                        DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.AUDIO_WORD,
                        DB_NOTES.HARD_FLAG, DB_NOTES.EASY_FLAG},
                null, null, null, null, null);

        if (cursor != null && cursor.getCount() != 0){

            while (cursor.moveToNext()){
                WordModel wordModel = new WordModel();

                int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                int imgWord = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                int audio = cursor.getInt(cursor.getColumnIndex(DB_NOTES.AUDIO_WORD));
                int hardFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.HARD_FLAG));
                int easyFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EASY_FLAG));
                int wrdStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_START));;
                int wrdEnd  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_END));
                int defStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_START));
                int defEnd  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_END));
                int exmplStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_START));
                int exmplEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_END));
                String word = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));
                String phonetic = cursor.getString(cursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));
                String translateWord = cursor.getString(cursor.getColumnIndex(DB_NOTES.TRANSLATE_WORD));
                String definition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));
                String translateDefinition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_TRANSLATE_WORD));
                String example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));
                String translateExample = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));



                wordModel.setId(id);
                wordModel.setWordImage(imgWord);
                wordModel.setAudio(audio);
                wordModel.setHardFlag(hardFlag);
                wordModel.setEasyFlag(easyFlag);
                wordModel.setWrdStart(wrdStart);
                wordModel.setWrdEnd(wrdEnd);
                wordModel.setDefStart(defStart);
                wordModel.setDefEnd(defEnd);
                wordModel.setExmplStart(exmplStart);
                wordModel.setExmplEnd(exmplEnd);
                wordModel.setWord(word);
                wordModel.setPhonetic(phonetic);
                wordModel.setTranslateWord(translateWord);
                wordModel.setDefinition(definition);
                wordModel.setTranslateDef(translateDefinition);
                wordModel.setExample(example);
                wordModel.setTranslateExmpl(translateExample);
                //plyAudio = audio;
                listWordModel.add(wordModel);

            }
            database.close();
            cursor.close();
        }
    }
    private SQLiteOpenHelper unitListDatabase(int unitId){
        if (unitId == 1){
            return new UnitDatabaseBookOne(this);
        }else if (unitId == 2){
            return new UnitDatabaseBookTwo(this);
        }else if (unitId == 3){
            return new UnitDatabaseBookThree(this);
        }else if (unitId == 4){
            return new UnitDatabaseBookFour(this);
        }else if (unitId == 5){
            return new UnitDatabaseBookFive(this);
        }else {
            return new UnitDatabaseBookSix(this);
        }
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



    private void audioTimeLine(int position){
        wordTimeLine(position);
        definitionTimeLine(position);
        exampleTimeLine(position);
    }
    private void wordTimeLine(int position){
        int start = (listWordModel.get(position).getWrdStart());
        int end = (listWordModel.get(position).getWrdEnd());
        int duration = end - start;

        strtWord = start;
        endWord = duration;
    }
    private void definitionTimeLine(int position){
        int start = (listWordModel.get(position).getDefStart());
        int end = (listWordModel.get(position).getDefEnd());
        int duration = end - start;

        strtDef = start;
        endDef = duration;
    }
    private void exampleTimeLine(int position){
        int start = (listWordModel.get(position).getExmplStart());
        int end = (listWordModel.get(position).getExmplEnd());
        int duration = end - start;

        strtExmpl = start;
        endExmpl = duration;
    }





    @Override
    public void wordCanPlay(boolean plyFlag) {
        wordHandler.postDelayed(() ->{
            wordPlayAudio(strtWord, endWord);
        }, 500);
    }

    @Override
    public void definitionCanPlay(boolean plyFlag) {
        defHandler.postDelayed(() ->{
            defPlayAudio(strtDef, endDef);
        }, 500);
    }

    @Override
    public void exampleCanPlay(boolean plyFlag) {
        exmplHandler.postDelayed(() ->{
            exmplPlayAudio(strtExmpl, endExmpl);
        }, 500);
    }







    @Override
    public void onDestroy(){
        super.onDestroy();
/*        //mediaPlayerOnDestroy();
        //sliderThread.cancel(true);
        if (defHandler != null){
            defHandler.removeCallbacks(defDelayRunnable);
        }
        releaseMediaPlayer();*/

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        allFunctions();
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayerOnDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        mediaPlayerOnDestroy();
    }

    public void mediaPlayerOnDestroy(){
        wordMediaPlayerDestroyer();
        defMediaPlayerDestroyer();
        exmpleMediaPlayerDestroyer();
    }

    private void wordMediaPlayerDestroyer(){
        try {
            boolean isDefPlaying = wordMediaPlayer.isPlaying();

            if (isDefPlaying) {
                wordHandler.removeCallbacks(audioThread);
                wordMediaPlayer.stop();
                wordMediaPlayer.reset();
                wordMediaPlayer.release();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void defMediaPlayerDestroyer(){
        try {
            boolean isDefPlaying = defMediaPlayer.isPlaying();

            if (isDefPlaying) {
                defHandler.removeCallbacks(defAudioThread);
                defMediaPlayer.stop();
                defMediaPlayer.reset();
                defMediaPlayer.release();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void exmpleMediaPlayerDestroyer(){
        try {
            boolean isDefPlaying = exmplMediaPlayer.isPlaying();

            if (isDefPlaying) {
                defHandler.removeCallbacks(exmplAudioThread);
                exmplMediaPlayer.stop();
                exmplMediaPlayer.reset();
                exmplMediaPlayer.release();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void extrasGetter(){
        Intent vpPIntent = getIntent();
        Intent intent = getIntent();
        Intent dbIntent = getIntent();
        wordId = vpPIntent.getIntExtra( "wordId", 0);
        unitNum = intent.getIntExtra("unitNumber", 0);
        dbNum = dbIntent.getIntExtra("dbNumber", 1);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public WordModel getWordModel(int position) {
        return listWordModel.get(position);
    }

    @Override
    public int getCount() {
        return listWordModel.size();
    }







    public interface SendFlagInterface{
        void sendFlag(boolean flag);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);

        try {
            sendFlagInterface = (SlideWordFragment) fragment;
        }catch (RuntimeException e){
            throw new RuntimeException(fragment.toString() + "some thing went wrong");
        }
    }



    private void viewsFinderById(){
        vpView = new View(this);
        vpCardViewContainer = findViewById(R.id.card_view_word_detailed_container);
        hardBtn = findViewById(R.id.hard_button);
        easyBtn = findViewById(R.id.easy_button);
        vpSeekBar = findViewById(R.id.word_detailed_music_seek_bar);
        vpTxtDuration = findViewById(R.id.word_detailed_progress_time_text);
        vpTxtTotalTime = findViewById(R.id.word_detailed_audio_total_time_text);
        vpPlyImg = findViewById(R.id.word_detailed_card_ply_image);
        //fBtn = findViewById(R.id.favorite_launcher_btn);
        backPressLayout = findViewById(R.id.slide_cardview_tab_layout_bck_bttn_layout);
        reviewImg = findViewById(R.id.slide_review_launcher);
        translateBtn = findViewById(R.id.word_detail_all_translate_btn);

        wordViewPager = findViewById(R.id.slide_card_view_viewpager);
        tabIndicator = findViewById(R.id.indicator_view_pager);
        tabIndicator.setupWithViewPager(wordViewPager, true);
        sample = findViewById(R.id.slide_card_view_unit);

    }
    private void viesOnClickListener(){
        vpPlyImg.setOnClickListener(this);
        //fBtn.setOnClickListener(this);
        reviewImg.setOnClickListener(this);
        hardBtn.setOnClickListener(this);
        easyBtn.setOnClickListener(this);
        translateBtn.setOnClickListener(this);
        backPressLayout.setOnClickListener(this);
        sample.setOnClickListener(this);
    }
}