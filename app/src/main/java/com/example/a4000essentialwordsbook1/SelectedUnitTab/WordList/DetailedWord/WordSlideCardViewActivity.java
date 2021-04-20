package com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import com.example.a4000essentialwordsbook1.AudioTimeUtil.TimeUtil;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.MarkedWords.MarkedWordActivity;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.WordDatabase.WordDatabaseOpenHelper;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordModel;
import com.example.a4000essentialwordsbook1.UnitSqliteOpenHelper;
import com.example.a4000essentialwordsbook1.UpdateDatabases.CheckWordDatabase;
import com.example.a4000essentialwordsbook1.UpdateDatabases.UpdateWordDatabase;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class WordSlideCardViewActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager wordViewPager;


    //CardView Container Components
    private TimeUtil vpUtils;
    private MediaPlayer vpMediaPlayer;
    private final Handler vpDurationHandler= new Handler();
    private ArrayList<Integer> markedFlagList;
    private ArrayList<WordModel> listWordModel;
    private CardView vpCardViewContainer;
    private ImageView vpPlyImg;
    private SeekBar vpSeekBar;
    private TextView vpTxtDuration, vpTxtTotalTime;
    private long currentPos, intElapsedTime = 0, intTotalTime = 0;
    private View vpView;
    private int vpPosition, unitNum;
    private int plyAudio = R.raw.a;
    private final Runnable flagCheckerThread = this::flagChecker;
    private Button hardBtn, easyBtn, fBtn, translateBtn;
    private TabLayout tabIndicator;
    private WordListSliderThread sliderThread;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_cardivew_viewpager_activity);

        //Function's order matters
        extrasGetter();
        viewsFinder();
        viesOnClickListener();
        threadsRunners();
        audioPlayer();
    }


    public void audioPlayer(){

        vpUtils = new TimeUtil();
        vpMediaPlayer = MediaPlayer.create(this, plyAudio);
        intTotalTime = vpMediaPlayer.getDuration();
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
                vpMediaPlayer.seekTo((int) currentPos);
            }
        });

        vpSeekBar.setClickable(true);
        vpSeekBar.setFocusable(true);
        vpSeekBar.setEnabled(true);
    }

    public void playAudio(View view){
        vpMediaPlayer.start();
        intElapsedTime = vpMediaPlayer.getCurrentPosition();
        vpSeekBar.setProgress((int) intElapsedTime);
        vpDurationHandler.postDelayed( updateSeekBar,10);
        vpPlyImg.setImageResource(R.drawable.mx_pause_normal);
    }

    private final Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            intElapsedTime = vpMediaPlayer.getCurrentPosition();
            vpSeekBar.setProgress((int) intElapsedTime);
            String vpStringTotalTime = "" + vpUtils.milliSecondsToTimer(intTotalTime);
            String vpLeftTime = "" + vpUtils.milliSecondsToTimer(intElapsedTime);

            vpTxtDuration.setText(vpLeftTime);
            vpTxtTotalTime.setText(vpStringTotalTime);
            vpDurationHandler.postDelayed(this, 10);
        }
    };


    public void pause(View view) {
        if (vpMediaPlayer != null) {
            vpMediaPlayer.pause();
            vpPlyImg.setImageResource(R.drawable.mx_play_normal);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.word_detailed_card_ply_image):
                if (vpMediaPlayer.isPlaying()) {
                    pause(view);
                } else {
                    playAudio(view);
                }
                break;
            case (R.id.favorite_launcher_btn):
                Intent intent = new Intent(this, MarkedWordActivity.class);
                startActivity(intent);
                break;
            case (R.id.hard_button):
                Runnable thread = this::updateWordDatabase;
                thread.run();
                break;
            case (R.id.easy_button):
                Toast.makeText(this, "you have known this word!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void updateWordDatabase(){
        int position = (wordViewPager.getCurrentItem() + 1);
        String flagColumn = DB_NOTES.HARD_FLAG;
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
        UpdateWordDatabase updateWordDB = new UpdateWordDatabase(this);
        updateWordDB.wordDatabaseUpdate(flagColumn, position, 1);
    }




    private class WordListSliderThread extends AsyncTask<Void, Void, Void>{

        private Context context;

        public WordListSliderThread(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            sampleData();
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            WordSliderAdapter adapter = new WordSliderAdapter(listWordModel, markedFlagList,  context, vpPosition);
            wordViewPager.setAdapter(adapter);

            wordViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Runnable wordAudioThread = () -> wordAudioReceiver(position + 1);
                    wordAudioThread.run();
                }

                @Override
                public void onPageScrollStateChanged(int state) {}
            });

            wordViewPager.setCurrentItem(vpPosition, true);
        }

        public void sampleData(){

            WordDatabaseOpenHelper vpWordDatabase = new WordDatabaseOpenHelper(context);
            SQLiteDatabase allDB = vpWordDatabase.getReadableDatabase();
             listWordModel = new ArrayList<>();

            Cursor cursor = allDB.query(DB_NOTES.WORD_TABLE,
                    new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                            DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.AUDIO_WORD, DB_NOTES.HARD_FLAG},
                    null, null, null, null, null);

            if (cursor != null && cursor.getCount() != 0){

                while (cursor.moveToNext()){
                    WordModel wordModel = new WordModel();

                    int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                    int imgWord = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                    int audio = cursor.getInt(cursor.getColumnIndex(DB_NOTES.AUDIO_WORD));
                    int markedFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.HARD_FLAG));
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
                    wordModel.setMarkedFlag(markedFlag);
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
                allDB.close();
                cursor.close();
            }
        }
    }



    public void wordAudioReceiver(int position){
        UnitSqliteOpenHelper wordAudioDatabase = new UnitSqliteOpenHelper(this);
        SQLiteDatabase db = wordAudioDatabase.getReadableDatabase();

        Cursor awCursor = db.query(DB_NOTES.UNIT_TABLE,
                new String[]{DB_NOTES.UNIT_AUDIO}, DB_NOTES.UNIT_ID + " = ? ",
                new String[]{Integer.toString(position)}, null, null, null);

        if (awCursor != null && awCursor.getCount() != 0){
            while (awCursor.moveToNext()){
                plyAudio = awCursor.getInt(awCursor.getColumnIndex(DB_NOTES.UNIT_AUDIO));
            }
        }
        assert awCursor != null;
        awCursor.close();
        db.close();
    }


    public void flagChecker(){
        CheckWordDatabase databaseChecker = new CheckWordDatabase(this);
        String table = DB_NOTES.WORD_TABLE;
        String columnId = DB_NOTES.WORD_ID;
        String itemCheck = DB_NOTES.HARD_FLAG;
        databaseChecker.favoriteChecker(table, columnId, itemCheck);
        markedFlagList = new ArrayList<>();
        markedFlagList = databaseChecker.getMarkedIntValue();
    }


    private void extrasGetter(){
        Intent vpPIntent = getIntent();
        vpPosition = vpPIntent.getIntExtra( "CARD_POSITION", 0);
        Intent intent = getIntent();
        unitNum = intent.getIntExtra("unitNumber", 0);
    }

    private void viewsFinder(){
        vpView = new View(this);
        vpCardViewContainer = findViewById(R.id.card_view_word_detailed_container);
        hardBtn = findViewById(R.id.hard_button);
        easyBtn = findViewById(R.id.easy_button);
        vpSeekBar = findViewById(R.id.word_detailed_music_seek_bar);
        vpTxtDuration = findViewById(R.id.word_detailed_progress_time_text);
        vpTxtTotalTime = findViewById(R.id.word_detailed_audio_total_time_text);
        vpPlyImg = findViewById(R.id.word_detailed_card_ply_image);
        fBtn = findViewById(R.id.favorite_launcher_btn);

        wordViewPager = findViewById(R.id.slide_card_view_viewpager);
        tabIndicator = findViewById(R.id.indicator_view_pager);
        tabIndicator.setupWithViewPager(wordViewPager, true);
    }

    private void viesOnClickListener(){
        vpPlyImg.setOnClickListener(this);
        fBtn.setOnClickListener(this);
        hardBtn.setOnClickListener(this);
        easyBtn.setOnClickListener(this);
    }

    private void threadsRunners(){
        flagCheckerThread.run();
        sliderThread = new WordListSliderThread(this);
        sliderThread.execute();
    }




    @Override
    public void onDestroy(){
        super.onDestroy();
        mediaPlayerOnDestroy();
        sliderThread.cancel(true);
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
        if (vpMediaPlayer != null) {
            pause(vpView);
            vpMediaPlayer.release();
            vpDurationHandler.removeCallbacks(updateSeekBar);
            vpMediaPlayer = null;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        audioPlayer();
    }
}
