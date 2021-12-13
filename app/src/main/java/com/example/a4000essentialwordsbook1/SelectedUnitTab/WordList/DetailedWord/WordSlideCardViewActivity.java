package com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
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
import com.example.a4000essentialwordsbook1.Linsteners.AudioPlayerListener;
import com.example.a4000essentialwordsbook1.Linsteners.TextProvider;
import com.example.a4000essentialwordsbook1.SearchWordsClasses.SearchWordsActivity;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordDetailedInterfaces.DisplayTranslationInterface;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordDetailedInterfaces.EditedTranslationInterface;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordDetailedInterfaces.SaveEditsInterface;
import com.example.a4000essentialwordsbook1.Settings.SettingListanerInterface.AddNoteInterFace;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.AddNoteDialogFragment;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.AutoPlayDialogFragment;
import com.example.a4000essentialwordsbook1.Settings.SettingListanerInterface.AutoPlayInterface;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.EditWordDialogFragment;
import com.example.a4000essentialwordsbook1.Settings.SettingsPreferencesActivity;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.AutoPlayNotes;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.AutoPlayTypeNotes.AutoPlayTypeNote;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.MarkedWords.MarkedWordActivity;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.PreferencesNotes.ResumeStudyPreferences;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;
import com.example.a4000essentialwordsbook1.UpdateDatabases.CheckWordDatabase;
import com.example.a4000essentialwordsbook1.UpdateDatabases.UpdateWordDatabase;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WordSlideCardViewActivity extends AppCompatActivity implements View.OnClickListener,
        TextProvider, SaveEditsInterface, AutoPlayInterface, AudioPlayerListener, AddNoteInterFace {
    private ViewPager2 wordViewPager;

    //CardView Container Components
    private MediaPlayer singleMediaPlayer, twoMediaPlayer, mainPlayer;
    private float autoPlayerSpeedVal = 1.0f;
    private RelativeLayout easyHardLayout, speedMeterLayout;
    private TimeUtil timeUtil;
    private long ttlMediaTime, timeElapsed;
    private ArrayList<Integer> markedFlagList;
    private ArrayList<WordModel> listWordModel;
    private ImageView vpPlyImg, reviewImg, searchImgView;
    private SeekBar vpSeekBar;
    private TextView vpTxtDuration, vpTxtTotalTime, sample;
    private TextView numDeterminer;

    private ImageView vrySlwImageView, slwImageView, normalImageView, fstImageView, vryFstImageView;
    private ImageView speedMeterImgView;
    private TextView vrySlwTextView, slwTextView, normalTextView, fstTextView, vryFstTextView;
    private float speedMeter = 1.0f;

    private Handler autoPlayHandler;
    private int unitNum, dbNum;
    private int mediaPosition;
    private String plyAudio;
    private Button hardBtn, easyBtn;
    private CardView marked_and_speedMetter;
    private TabLayout tabIndicator;
    private final String strDbNumber = ExtraNotes.DB_NUMBER;
    private final String strUnitNumber = ExtraNotes.UNIT_NUMBER;
    private final String strWordId = ExtraNotes.WORD_ID;
    private final String autoPlayFlagListKey = ExtraNotes.AUTO_PLAY_BOOLEAN_LIST;
    private final String autoPlayFlagKey = ExtraNotes.AUTO_PLAY_FLAG_KEY;
    private final String speedMeterKey = ExtraNotes.SPEED_METER_KEY;
    private final String detailedWordSlideActivity = AutoPlayNotes.WORD_SLIDE_CARD_VIEW_ACTIVITY;
    private final String wrdStartNote = AutoPlayTypeNote.WRD_START;
    private final String wrdDuraionNote = AutoPlayTypeNote.WORD_DURATION;
    private final String defStartNote = AutoPlayTypeNote.DEFINITION_START;
    private final String defDurationNote = AutoPlayTypeNote.DEFINITION_DURATION;
    private final String exmplStartNote = AutoPlayTypeNote.EXAMPLE_START;
    private final String exmplDurationNote = AutoPlayTypeNote.EXAMPLE_DURATION;
    private boolean allPlayFlag, wordPlayFlag, defPlayFlag, exmplPlayFlag, plyAgainFlag, plyNxtFlag;

    private boolean autoPlayFlag;
    private boolean[] flagListAutoPly;
    private boolean[] shwFarsiFlags;
    //public SendFlagInterface sendFlagInterface;
    private WordSlideFragmentPagerAdapter sectionAdapter;
    private RelativeLayout backPressLayout;
    private final int tmLineMines = 500;
    private int vpCurrentItem;
    private WordModel wordModel;
    private ViewPager2.OnPageChangeCallback viewPager2OnPageChangeCallback;
    private ExecutorService autoPlayThread;
    private TabLayoutMediator layoutMediator;
    private FragmentManager manager;
    private EditedTranslationInterface editorInterFace;
    private AddNoteInterFace addNoteInterFace;
    private DisplayTranslationInterface displayTranslation;

    private SharedPreferences plyAudioPreferences;
    private final String plyAudioPreferenceName = SettingsPreferencesNotes.SETTINGS_AUTO_PLAY_AUDIO_PREFERENCES;
    private final String wordPlyKey = SettingsPreferencesNotes.WORD_PLAY_KEY;
    private final String definitionPlyKey = SettingsPreferencesNotes.DEFINITION_PLAY_KEY;
    private final String examplePlyKey = SettingsPreferencesNotes.EXAMPLE_PLAY_KEY;


    private SharedPreferences autoPlayAudioPreferences;
    private final String autoPlayAudioPreferencesName = SettingsPreferencesNotes.AUTO_PLAY_AUDIO_DETAILED_WORD_PREFERENCES;
    private final String plySpeedKey = SettingsPreferencesNotes.SPEED_PLAY_KEY;



    //resume preferences
    SharedPreferences resumeSharedPreferences;
    private final String resumePreferencesName = ResumeStudyPreferences.RESUMEPREFERENCES;
    private String bookStr = ResumeStudyPreferences.BOOKNUMBER;
    private String unitStr = ResumeStudyPreferences.UNITNUMBER;
    private String resumeWordStr = ResumeStudyPreferences.RESUMEWORD;
    private String wordPositionStr = ResumeStudyPreferences.WORDPOSITION;
    private int pBookNum, pUnitNum;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_cardivew_viewpager_activity);
        allFunctions();
    }


    private void allFunctions(){
        //Function's order matters
        extrasGetter();
        viewsFinderById();
        threadsRunners();
    }

    private void allAudioPlayers(String plyAudio){
        final String appPath = this.getApplicationInfo().dataDir;
        final File audioDir = new File(Environment.DIRECTORY_DOWNLOADS, File.separator + "4000 Essential Words");

        final File audioMainPath = new File("Audio Files");
        final File audioWordPath = new File(audioMainPath, File.separator + "Word Audios");


        final File audioWordBookPath = new File(audioWordPath, File.separator + "Book_" + dbNum);
        final File secondSubFile = new File(audioWordBookPath, File.separator + "." + new File(plyAudio).getName());

        final String plyPath = Environment.getExternalStoragePublicDirectory(audioDir.toString()).toString() + File.separator + secondSubFile.toString();
        mainPlayer = MediaPlayer.create(WordSlideCardViewActivity.this, Uri.parse(plyPath));
        mainPlayer.start();
        mainPlayer.pause();
        twoMediaPlayer = MediaPlayer.create(WordSlideCardViewActivity.this, Uri.parse(plyPath));
        singleMediaPlayer = MediaPlayer.create(WordSlideCardViewActivity.this, Uri.parse(plyPath));
        mainMediaPlayer();
    }


    /**********************************************************************************************/


    private void mainMediaPlayer(){
        try {
            timeUtil = new TimeUtil();
            ttlMediaTime = mainPlayer.getDuration();
            vpSeekBar.setMax((int) ttlMediaTime);
            mainPlayer.seekTo(mediaPosition);

            vpSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    endSeekBarHandler.removeCallbacks(endSeekBarThread);
                    mediaPosition = seekBar.getProgress();
                    mainPlayer.seekTo(mediaPosition);
                    endSeekBarHandler.postDelayed(endSeekBarThread,(int) (mainPlayer.getDuration() / speedMeter));
                }
            });

            vpSeekBar.setClickable(true);
            vpSeekBar.setFocusable(true);
            vpSeekBar.setEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**********************************************************************************************/

    private void singlePlayAudio(int start, int end) {
        pauseMainPlayer();
        twoMediaPlayersPause();

        final boolean isPlaying = singleMediaPlayer.isPlaying();
        if (isPlaying) {
            singleMediaPlayHandler.removeCallbacks(singleAudioThread);
            singleMediaPlayer.pause();
            singleMediaPlayer.seekTo(start);
        }
        singleAudioThread = () -> {
            singleMediaPlayer.pause();
            singleMediaPlayer.seekTo(start);
            autoViewPagerScroller();
        };
        singleMediaPlayer.seekTo(start);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            singleMediaPlayer.setPlaybackParams(autoPlayBackParams());
        }
        singleMediaPlayer.start();
        singleMediaPlayHandler.postDelayed(singleAudioThread, (int) (end / autoPlayerSpeedVal));
    }
    private final Handler singleMediaPlayHandler = new Handler(Looper.getMainLooper());
    private  Runnable singleAudioThread;


    /**********************************************************************************************/

    private void twoMediaAutoPlay(int firstStart, int firstEnd, int secondStart, int secondEnd) {
        pauseMainPlayer();
        singleMediaPlayerPause();

        if (twoMediaPlayer.isPlaying()){
            twoMediaPlayHandler.removeCallbacks(twoAudioOneThread);
            twoMediaPlayHandler.removeCallbacks(twoAudioTwoThread);
            twoMediaPlayer.pause();
            twoMediaPlayer.seekTo(firstStart);
        }

        twoAudioOneThread = () -> {
            twoMediaPlayer.pause();
            twoMediaPlayer.seekTo(secondStart);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                twoMediaPlayer.setPlaybackParams(autoPlayBackParams());
            }
            twoMediaPlayer.start();
            twoMediaPlayHandler.postDelayed(twoAudioTwoThread, (int) (secondEnd / autoPlayerSpeedVal));
            Log.d("secondEnd", "" + secondEnd);
        };
        twoAudioTwoThread = () -> {
            twoMediaPlayer.pause();
            twoMediaPlayer.seekTo(firstStart);
            autoViewPagerScroller();
        };
        twoMediaPlayer.seekTo(firstStart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            twoMediaPlayer.setPlaybackParams(autoPlayBackParams());
        }
        twoMediaPlayer.start();
        twoMediaPlayHandler.postDelayed(twoAudioOneThread, (int) (firstEnd / autoPlayerSpeedVal));
        Log.d("firstEnd", "" + firstEnd);
    }
    private final Handler twoMediaPlayHandler = new Handler(Looper.getMainLooper());
    private  Runnable twoAudioOneThread;
    private  Runnable twoAudioTwoThread;

    private void autoViewPagerScroller(){
        if (plyAgainFlag) {
            final int newPosition = (plusOneItem() == lstSize()) ? 0 : plusOneItem();
            this.wordViewPager.setCurrentItem(newPosition, true);
        }else if (autoPlayFlag){
            this.wordViewPager.setCurrentItem(currentItem() + 1, true);
        }
    }

    /**********************************************************************************************/


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.slide_word_detailed_card_ply_image):
                playOrPauseMainPlayer();
                break;
            case (R.id.slide_word_auto_play):
                dialogFragmentAutoPlayWord();
                break;
            case (R.id.slide_word_hard_button):
                updateWordDatabaseThread(DB_NOTES.HARD_FLAG,1);
                updateWordDatabaseThread(DB_NOTES.EASY_FLAG, 0);
                break;
            case (R.id.slide_word_easy_button):
                updateWordDatabaseThread(DB_NOTES.HARD_FLAG,0);
                updateWordDatabaseThread(DB_NOTES.EASY_FLAG, 1);
                break;
            case (R.id.slide_word_detail_marked_and_speed_meter):
                hardEasyCardViewAnimationDeterminer();
                break;
            case (R.id.slide_word_card_view_tab_layout_bck_bttn_layout):
                onBackPressed();
                break;
            case (R.id.slide_word_search_launcher):
                startSearchActivity();
                break;
        }
    }
    private void hardEasyCardViewAnimationDeterminer(){
        if (hardEasyFlag){
            cardViewGetDownAnimation();
        }else {
            if (speedMeterFlag){
                downAnimationSpeedMeterCardView();
                new Handler(Looper.getMainLooper()).postDelayed(this::cardViewGetUpAnimation, 350);
            }else {
                cardViewGetUpAnimation();
            }
        }
    }

    private boolean hardEasyFlag;
    private boolean speedMeterFlag;


    private void cardViewGetUpAnimation(){
        ValueAnimator animator = ValueAnimator.ofFloat(500f, 0f);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.setDuration(300);
        animator.addUpdateListener(animation -> {
            final float progress = (float) animation.getAnimatedValue();
            easyHardLayout.setVisibility(View.VISIBLE);
            easyHardLayout.setTranslationY(progress);
        });
        animator.start();
        hardEasyFlag = true;
    }
    private void cardViewGetDownAnimation(){
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 500f);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.setDuration(300);
        animator.addUpdateListener(animation -> {
            final float progress = (float) animation.getAnimatedValue();
            easyHardLayout.setTranslationY(progress);
        });
        animator.start();
        hardEasyFlag = false;
    }



    private void dialogFragmentAutoPlayWord() {
        AutoPlayDialogFragment playDialogFragment = AutoPlayDialogFragment.newInstance(detailedWordSlideActivity, dbInfoList());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("autoPlayAudio");
        if (prev != null) {
            ft.remove(prev);
        }
        playDialogFragment.show(ft, "autoPlayAudio");
    }

    private void playOrPauseMainPlayer(){
        preDoMainPlayer();

        if (mainPlayer.isPlaying()){
            pauseMainPlayer();
        }else {
            allAgainCanceler();
            playMainPlayer();
        }
    }
    private void preDoMainPlayer(){
        twoMediaPlayersPause();
        singleMediaPlayerPause();
    }

    private void playMainPlayer(){
        if (mainPlayer.getCurrentPosition() == 0){
            mainPlayer.seekTo(wordStart(currentItem()));
        }
        mainPlayer.start();
        timeElapsed = mainPlayer.getCurrentPosition();
        vpSeekBar.setProgress((int) timeElapsed);
        durationHandler.postDelayed(updateVpSeekBar, 100);
        vpPlyImg.setImageResource(R.drawable.mx_pause_normal);

        final int endPlayerDelay = (int) (mainPlayer.getDuration() / speedMeter);
        endSeekBarHandler.postDelayed(endSeekBarThread, endPlayerDelay);
    }
    private void pauseMainPlayer(){
        new Handler(Looper.getMainLooper()).post(() ->{
            try {
                if (mainPlayer != null) {
                    mainPlayer.pause();
                    durationHandler.removeCallbacks(updateVpSeekBar);
                    endSeekBarHandler.removeCallbacks(endSeekBarThread);
                    vpPlyImg.setImageResource(R.drawable.mx_play_normal);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        });
    }
    private final Runnable updateVpSeekBar = new Runnable() {
        @Override
        public void run() {
            timeElapsed = mainPlayer.getCurrentPosition();
            vpSeekBar.setProgress((int) timeElapsed);
            String totalPlayerTime = "" + timeUtil.milliSecondsToTimer(ttlMediaTime);
            String leftTime = "" + timeUtil.milliSecondsToTimer(timeElapsed);
            vpTxtDuration.setText(leftTime);
            vpTxtTotalTime.setText(totalPlayerTime);
            durationHandler.postDelayed(this, 100);
        }
    };
    private final Handler durationHandler = new Handler(Looper.getMainLooper());
    private final Handler endSeekBarHandler = new Handler(Looper.getMainLooper());
    private final Runnable endSeekBarThread = () -> {
        if (mainPlayer != null){
            final String newLeftTime = "0:00";
            vpTxtDuration.setText(newLeftTime);
            vpPlyImg.setImageResource(R.drawable.mx_play_normal);
            mainPlayer.pause();
            mainPlayer.seekTo(0);
            vpSeekBar.setProgress(0);
            this.durationHandler.removeCallbacks(this.updateVpSeekBar);
        }
    };


    private void markedWordActivity(){
        Intent intent = new Intent(this, MarkedWordActivity.class);

        intent.putExtra(strDbNumber, dbNum);
        intent.putExtra(strUnitNumber, unitNum);
        startActivity(intent);
    }


    private void updateWordDatabaseThread(String columnName, int value){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() ->updateWordDatabase(columnName, value));
        executor.shutdown();
    }
    public void updateWordDatabase(String columnName, int valFlag){
        int position = (currentItem() + 1);
        int[] dbInfoNum = {dbNum, unitNum};
        UpdateWordDatabase updateWordDB = new UpdateWordDatabase(this, dbInfoNum);
        updateWordDB.wordDatabaseUpdate(columnName, position, valFlag);

        String word = listWordModel.get(currentItem()).getWord();
        fillResumeStudyPreferences(word);
    }
    private void fillResumeStudyPreferences(String word2Resume){
        resumeSharedPreferences = getSharedPreferences(resumePreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor resumeStudyEdit = resumeSharedPreferences.edit();

        resumeStudyEdit.putString(resumeWordStr, word2Resume);
        resumeStudyEdit.putInt(bookStr, pBookNum);
        resumeStudyEdit.putInt(unitStr, pUnitNum);
        resumeStudyEdit.putInt(wordPositionStr, currentItem());

        resumeStudyEdit.apply();
    }


    private void threadsRunners(){
        flagCheckerThread();
        wordAudioReceiverThread();
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
            CheckWordDatabase databaseChecker = new CheckWordDatabase(this, dbNum);
            String table = (DB_NOTES.NEUTRAL_WORD_TABLE + unitNum);
            String columnId = DB_NOTES.WORD_ID;
            String columnCheck = DB_NOTES.HARD_FLAG;
            databaseChecker.favoriteChecker(table, columnId, columnCheck);
            markedFlagList = new ArrayList<>();
            markedFlagList = databaseChecker.getMarkedIntValue();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void wordAudioReceiverThread(){
        ExecutorService wordDatabaseThread = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        wordDatabaseThread.execute(() -> {
            wordAudioReceiver(dbNum, unitNum);
            handler.post(() ->{
                if (autoPlayFlag) {
                    autoMediaPlayer();
                }
            });
        });
    }
    @SuppressLint("Range")
    private void wordAudioReceiver(int dbId, int unitId){
        SQLiteDatabase db = unitListDatabase(dbId).getReadableDatabase();

        Cursor awCursor = db.query(DB_NOTES.UNIT_TABLE,
                new String[]{DB_NOTES.UNIT_COMPLETE_WORD_AUDIO},
                DB_NOTES.UNIT_ID + " = ? ", new String[]{Integer.toString(unitId)},
                null, null, null);

        if (awCursor.getCount() != 0){
            while (awCursor.moveToNext()){
                plyAudio = awCursor.getString(awCursor.getColumnIndex(DB_NOTES.UNIT_COMPLETE_WORD_AUDIO));
            }
            allAudioPlayers(plyAudio);
        }
        awCursor.close();
        db.close();
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

    private void wordListSliderThread(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            wordDatabaseReceiver();
            handler.post(this::viewPagerSetter);
        });
    }
    @SuppressLint("Range")
    private void wordDatabaseReceiver(){
        unitNum = (unitNum == 0) ? 1 : unitNum;

        SQLiteDatabase database = wordListDatabase(dbNum).getReadableDatabase();
        listWordModel = new ArrayList<>();

        Cursor cursor = database.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitNum,
                new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                        DB_NOTES.WORD_START, DB_NOTES.WORD_END, DB_NOTES.DEF_START, DB_NOTES.DEF_END, DB_NOTES.EXMPL_START, DB_NOTES.EXMPL_END,
                        DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.AUDIO_WORD,
                        DB_NOTES.HARD_FLAG, DB_NOTES.EASY_FLAG, DB_NOTES.EXTRA_NOTE},
                null, null, null, null, null);

        if (cursor != null && cursor.getCount() != 0){

            while (cursor.moveToNext()){
                wordModel = new WordModel();

                final int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                final String imgWord = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                final int bookIdNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.BOOK_NUMBER));
                final int unitIdNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.UNIT_NUMBER));
                final int hardFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.HARD_FLAG));
                final int easyFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EASY_FLAG));
                final int wrdStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_START));
                final int wrdEnd  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_END));
                final int defStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_START));
                final int defEnd  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_END));
                final int exmStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_START));
                final int exmEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_END));
                final String word = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));
                final String phonetic = cursor.getString(cursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));
                final String translateWord = cursor.getString(cursor.getColumnIndex(DB_NOTES.TRANSLATE_WORD));
                final String definition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));
                final String translateDefinition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_TRANSLATE_WORD));
                final String example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));
                final String translateExample = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));
                final String userNote = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXTRA_NOTE));


                wordModel.setId(id);
                wordModel.setImgUri(imgWord);
                wordModel.setBookNum(bookIdNum);
                wordModel.setUnitNum(unitIdNum);
                wordModel.setHardFlag(hardFlag);
                wordModel.setEasyFlag(easyFlag);
                wordModel.setWrdStart(wrdStart - tmLineMines);
                wordModel.setWrdEnd(wrdEnd- tmLineMines);
                wordModel.setDefStart(defStart- tmLineMines);
                wordModel.setDefEnd(defEnd- tmLineMines);
                wordModel.setExmplStart(exmStart- tmLineMines);
                wordModel.setExmplEnd(exmEnd- tmLineMines);

                wordModel.setWord(word);
                wordModel.setPhonetic(phonetic);
                wordModel.setTranslateWord(translateWord);
                wordModel.setDefinition(definition);
                wordModel.setTranslateDef(translateDefinition);
                wordModel.setExample(example);
                wordModel.setTranslateExmpl(translateExample);
                wordModel.setAddNote(userNote);
                listWordModel.add(wordModel);
                pBookNum = dbNum;
                pUnitNum = unitNum;

            }
            database.close();
            cursor.close();
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


    ///**** ViewPager Functions ****///
    private void viewPagerSetter(){
        manager = getSupportFragmentManager();
        manager.addFragmentOnAttachListener((fragmentManager, fragment) -> {
            try {
                editorInterFace = (SlideWordFragment) fragment;
                addNoteInterFace = (SlideWordFragment) fragment;
                displayTranslation = (SlideWordFragment) fragment;
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        sectionAdapter = new WordSlideFragmentPagerAdapter(
                WordSlideCardViewActivity.this, listWordModel, markedFlagList, shwFarsiFlags,
                manager , getLifecycle(), this);

        wordViewPager.setAdapter(sectionAdapter);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            wordViewPager.setCurrentItem(vpCurrentItem, false);
        }, 100);


        viewPagerOnClickListenerSetter();
        wordViewPager.setCurrentItem(vpCurrentItem, false);
        tabLayoutFun();
    }
    private void viewPagerOnClickListenerSetter() {
        viewPager2OnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                onPageSelectedFunctions();
                if (hardEasyFlag){
                    cardViewGetDownAnimation();
                }
                downAnimationSpeedMeterCardView();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        };
        wordViewPager.registerOnPageChangeCallback(viewPager2OnPageChangeCallback);
    }
    private void onPageSelectedFunctions(){

        if (plyAgainFlag && !isFinishing() || autoPlayFlag){
            autoMediaPlayer();
        }else {
            if (canAudioPlayItSelf()) {
                entranceAudioPlayFunctions();
            }
        }
    }

    private void tabLayoutFun(){

        String[] viewPagerTitle = new String[20];

        layoutMediator = new TabLayoutMediator(tabIndicator, wordViewPager,
                ((tab, position) -> tab.setText(viewPagerTitle[position])));
        layoutMediator.attach();
    }


    @Override
    public void addNote(String note) {
        addNoteValueReSetter(note);
    }
    private void addNoteValueReSetter(String note){
        ExecutorService thread = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        thread.execute(() ->{
            final int newWordId = listWordModel.get(currentItem()).getId();
            databaseReReceiverForAddedNote(newWordId);
            handler.post(() ->{
                WordSlideFragmentPagerAdapter.listChanger(listWordModel);
                addNoteInterFace.addNote(note);
            });
        });

    }
    @SuppressLint("Range")
    private void databaseReReceiverForAddedNote(int newWordId){
        unitNum = (unitNum == 0) ? 1 : unitNum;

        SQLiteDatabase database = wordListDatabase(dbNum).getReadableDatabase();
        Cursor cursor = database.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitNum,
                new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                        DB_NOTES.WORD_START, DB_NOTES.WORD_END, DB_NOTES.DEF_START, DB_NOTES.DEF_END, DB_NOTES.EXMPL_START, DB_NOTES.EXMPL_END,
                        DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.AUDIO_WORD,
                        DB_NOTES.HARD_FLAG, DB_NOTES.EASY_FLAG, DB_NOTES.EXTRA_NOTE},
                DB_NOTES.WORD_ID + " = ? ", new String[]{Integer.toString(newWordId)}, null, null, null);
        if (cursor != null && cursor.getCount() != 0){

            while (cursor.moveToNext()){
                wordModel = new WordModel();

                final int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                final String imgWord = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                final int bookIdNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.BOOK_NUMBER));
                final int unitIdNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.UNIT_NUMBER));
                final int audio = cursor.getInt(cursor.getColumnIndex(DB_NOTES.AUDIO_WORD));
                final int hardFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.HARD_FLAG));
                final int easyFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EASY_FLAG));
                final int wrdStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_START));
                final int wrdEnd  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_END));
                final int defStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_START));
                final int defEnd  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_END));
                final int exmStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_START));
                final int exmEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_END));
                final String word = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));
                final String phonetic = cursor.getString(cursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));
                final String translateWord = cursor.getString(cursor.getColumnIndex(DB_NOTES.TRANSLATE_WORD));
                final String definition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));
                final String translateDefinition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_TRANSLATE_WORD));
                final String example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));
                final String translateExample = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));
                final String usetNote = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXTRA_NOTE));


                wordModel.setId(id);
                wordModel.setImgUri(imgWord);
                wordModel.setAudio(audio);
                wordModel.setBookNum(bookIdNum);
                wordModel.setUnitNum(unitIdNum);
                wordModel.setHardFlag(hardFlag);
                wordModel.setEasyFlag(easyFlag);
                wordModel.setWrdStart(wrdStart - tmLineMines);
                wordModel.setWrdEnd(wrdEnd- tmLineMines);
                wordModel.setDefStart(defStart- tmLineMines);
                wordModel.setDefEnd(defEnd- tmLineMines);
                wordModel.setExmplStart(exmStart- tmLineMines);
                wordModel.setExmplEnd(exmEnd- tmLineMines);
                wordModel.setWord(word);
                wordModel.setPhonetic(phonetic);
                wordModel.setTranslateWord(translateWord);
                wordModel.setDefinition(definition);
                wordModel.setTranslateDef(translateDefinition);
                wordModel.setExample(example);
                wordModel.setTranslateExmpl(translateExample);
                wordModel.setAddNote(usetNote);
                listWordModel.set(currentItem(), wordModel);
                pBookNum = dbNum;
                pUnitNum = unitNum;
            }
            database.close();
            cursor.close();
        }
    }


    @Override
    public void editsSaved(String wordTranslation, String defTranslation, String exmplTranslation) {
        reSetterViewPager(wordTranslation, defTranslation, exmplTranslation);
    }
    private void reSetterViewPager(String word, String def, String exmpl){
        ExecutorService reThread = Executors.newSingleThreadExecutor();
        Handler reHandler = new Handler(Looper.getMainLooper());
        reThread.execute(() ->{

            final int newWordId = listWordModel.get(currentItem()).getId();
            reReceiverWordDatabase(newWordId);
            reHandler.post(() ->{
                WordSlideFragmentPagerAdapter.listChanger(listWordModel);
                editorInterFace.translationEditor(word, def, exmpl);
            });
        });
    }
    @SuppressLint("Range")
    private void reReceiverWordDatabase(int newWordId){
        unitNum = (unitNum == 0) ? 1 : unitNum;

        SQLiteDatabase database = wordListDatabase(dbNum).getReadableDatabase();
        Cursor cursor = database.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitNum,
                new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                        DB_NOTES.WORD_START, DB_NOTES.WORD_END, DB_NOTES.DEF_START, DB_NOTES.DEF_END, DB_NOTES.EXMPL_START, DB_NOTES.EXMPL_END,
                        DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.AUDIO_WORD,
                        DB_NOTES.HARD_FLAG, DB_NOTES.EASY_FLAG, DB_NOTES.EXTRA_NOTE},
                DB_NOTES.WORD_ID + " = ? ", new String[]{Integer.toString(newWordId)}, null, null, null);
        if (cursor != null && cursor.getCount() != 0){

            while (cursor.moveToNext()){
                wordModel = new WordModel();

                final int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                final String imgWord = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                final int bookIdNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.BOOK_NUMBER));
                final int unitIdNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.UNIT_NUMBER));
                final int audio = cursor.getInt(cursor.getColumnIndex(DB_NOTES.AUDIO_WORD));
                final int hardFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.HARD_FLAG));
                final int easyFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EASY_FLAG));
                final int wrdStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_START));
                final int wrdEnd  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_END));
                final int defStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_START));
                final int defEnd  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_END));
                final int exmStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_START));
                final int exmEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_END));
                final String word = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));
                final String phonetic = cursor.getString(cursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));
                final String translateWord = cursor.getString(cursor.getColumnIndex(DB_NOTES.TRANSLATE_WORD));
                final String definition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));
                final String translateDefinition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_TRANSLATE_WORD));
                final String example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));
                final String translateExample = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));
                final String userNote = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXTRA_NOTE));


                wordModel.setId(id);
                wordModel.setImgUri(imgWord);
                wordModel.setAudio(audio);
                wordModel.setBookNum(bookIdNum);
                wordModel.setUnitNum(unitIdNum);
                wordModel.setHardFlag(hardFlag);
                wordModel.setEasyFlag(easyFlag);
                wordModel.setWrdStart(wrdStart - tmLineMines);
                wordModel.setWrdEnd(wrdEnd- tmLineMines);
                wordModel.setDefStart(defStart- tmLineMines);
                wordModel.setDefEnd(defEnd- tmLineMines);
                wordModel.setExmplStart(exmStart- tmLineMines);
                wordModel.setExmplEnd(exmEnd- tmLineMines);
                wordModel.setWord(word);
                wordModel.setPhonetic(phonetic);
                wordModel.setTranslateWord(translateWord);
                wordModel.setDefinition(definition);
                wordModel.setTranslateDef(translateDefinition);
                wordModel.setExample(example);
                wordModel.setTranslateExmpl(translateExample);
                wordModel.setAddNote(userNote);
                listWordModel.set(currentItem(), wordModel);
                pBookNum = dbNum;
                pUnitNum = unitNum;
            }
            database.close();
            cursor.close();
        }
    }




    /***********************************************************************************************/

    @Override
    public void autoPlayer(boolean[] autoPlayFlagList, float mediaSpeed) {

        autoPlayDeterminer(autoPlayFlagList);
        autoPlayFlag = !plyAgainFlag;
        autoPlayerSpeedVal = mediaSpeed;
        autoMediaPlayer();
    }
    private void autoMediaPlayer(){
        if (allPlayFlag) {
            autoPlayOnceOrAgainDeterminer();
        } else if (isWordPlayedOnly()) {
            wordPlayerOnceOrAgainDeterminer();
        } else if (isDefPlayedOnly()) {
            definitionPlayerOnceOrAgainDeterminer();
        } else if (isExmplPlayedOnly()) {
            examplePlayerOnceOrAgainDeterminer();
        }else if (isWordAndDef()) {
            autoTwoPlayOnceOrAgainDeterminer(autoPlyWordAndDefList());// ?? 4
        } else if (isWordAndExmpl()) {
            autoTwoPlayOnceOrAgainDeterminer(autoPlyWordAndExmplList());
        }else if (isDefAndExmpl()) {
            autoTwoPlayOnceOrAgainDeterminer(autoPlyDefAndExmplList());
        }
    }
    private String[] autoPlyWordAndDefList(){
        return new String[]{wrdStartNote, wrdDuraionNote, defStartNote, defDurationNote};
    }
    private String[] autoPlyWordAndExmplList(){
        return new String[] {wrdStartNote, wrdDuraionNote, exmplStartNote, exmplDurationNote};
    }
    private String[] autoPlyDefAndExmplList(){
        return new String[] {defStartNote, defDurationNote, exmplStartNote, exmplDurationNote};
    }


    private void autoPlayOnceOrAgainDeterminer(){
        if (plyAgainFlag){
            autoPlayAllAgainPlaying();
        }else {
            autoPlayAllPlaying();
        }
    }

    private void autoPlayAllPlaying(){
        shwTranslationsInAutoPlay();
        twoMediaPlayersPause();
        playAllAudios(currentItem());
    }
    private void autoPlayAllAgainPlaying(){
        shwTranslationsInAutoPlay();
        if (canPlyAgain()){playAllAudios(currentItem());}
    }

    private void playAllAudios(int finalIndex){
            singleMediaPlayHandler.postDelayed(() -> singlePlayAudio(wordStart(finalIndex), allDuration(finalIndex)), 500);
    }
    private int allDuration(int finalIndex){
        return wordDuration(finalIndex) + defDuration(finalIndex) + exmplDuration(finalIndex);
    }

    private void allAgainCanceler() {
        if (autoPlayThread != null) {this.autoPlayThread.shutdownNow();}
    }



    private void autoTwoPlayOnceOrAgainDeterminer(String[] valStr){
        if (plyAgainFlag){
            autoPlayAgainTwoAudio(valStr[0], valStr[1], valStr[2], valStr[3]);
        }else {
            autoPlayTwoAudio(valStr[0], valStr[1], valStr[2], valStr[3]);// ?? 5
        }
    }

    private void autoPlayTwoAudio(String startFirst, String durationFirst, String startSecond, String durationSecond){
        playTwoAudios(currentItem(), startFirst, durationFirst, startSecond, durationSecond);// ?? 6
    }
    private void playTwoAudios(int finalIndex, String startFirst, String durationFirst, String startSecond, String durationSecond){
        shwTranslationsInAutoPlay();
        final int startOne = startAudio(startFirst, finalIndex);//?? 7
        final int endOne = durationAudio(durationFirst, finalIndex);
        final int startTwo = startAudio(startSecond, finalIndex);
        final int endTwo = durationAudio(durationSecond, finalIndex);
        twoMediaAutoPlay(startOne, endOne, startTwo, endTwo);
    }


    private void autoPlayAgainTwoAudio(String startFirst, String durationFirst, String startSecond, String durationSecond){
        shwTranslationsInAutoPlay();
        if (!isFinishing()){playTwoAudios(currentItem(), startFirst, durationFirst, startSecond, durationSecond);}
    }


    private int startAudio(String type, int index){
        if (type.equalsIgnoreCase(wrdStartNote)){
            return wordStart(index);//?? 8
        }else if (type.equalsIgnoreCase(defStartNote)){
            return defStart(index);
        }else {
            return exmplStart(index);
        }
    }
    private int durationAudio(String type, int index){
        if (type.equalsIgnoreCase(wrdDuraionNote)){
            return wordDuration(index);
        }else if (type.equalsIgnoreCase(defDurationNote)){
            return defDuration(index);
        }else {
            return exmplDuration(index);
        }
    }



    /***********************************************************************************************/



    private void wordPlayerOnceOrAgainDeterminer(){
        if (plyAgainFlag){
            wordPlayerOnceOrAgain();
        }else {
            autoWordPlayer();
        }
    }

    @Override
    public void wordAudioPlayer(boolean autoPlyFlag) {
        new Handler(Looper.getMainLooper()).postDelayed(() ->{
            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayHandler = new Handler(Looper.getMainLooper());

            autoPlayThread.execute(() -> autoPlayHandler.post(() ->{
                if (autoPlyFlag){
                    autoWordPlayer();
                }else {
                    farsiReSetter();
                    normalWordAudioPlayer();
                }
            }));
        }, 100);
    }
    private void autoWordPlayer(){
        twoMediaPlayersPause();
        normalWordAudioPlayer();
        shwTranslationsInAutoPlay();
    }


    private void wordPlayerOnceOrAgain(){
        onPauseFunctions();
        shwTranslationsInAutoPlay();
        if (!isFinishing()){normalWordAudioPlayer();}
    }
    private void normalWordAudioPlayer(){
        new Handler(Looper.getMainLooper()).post(() -> vpPlyImg.setImageResource(R.drawable.mx_play_normal));
        singleMediaPlayHandler.post(wordNormalRunnable);
    }
    private final Runnable wordNormalRunnable = () -> singlePlayAudio(wrdStart(), wrdDuration());
    private int wrdStart(){return listWordModel.get(currentItem()).getWrdStart();}
    private int wrdDuration(){return listWordModel.get(currentItem()).getWrdEnd() - wrdStart();}


    private void definitionPlayerOnceOrAgainDeterminer(){
        if (plyAgainFlag){
            definitionPlayerOnceOrAgain();
        }else {
            definitionAutoPlayer();
        }
    }

    @Override
    public void definitionAudioPlayer(boolean autoPlyFlag) {
        allAgainCanceler();
        if (autoPlyFlag){
            definitionAutoPlayer();
        }else {
            farsiReSetter();
            normalDefinitionAudiPlayer();
        }
    }
    private void definitionAutoPlayer(){
        twoMediaPlayersPause();
        normalDefinitionAudiPlayer();
        shwTranslationsInAutoPlay();
    }
    private void definitionPlayerOnceOrAgain(){
        new Handler(Looper.getMainLooper()).postDelayed(() ->{
            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayHandler = new Handler(Looper.getMainLooper());

            autoPlayThread.execute(() ->{
                shwTranslationsInAutoPlay();
                if (!isFinishing()){normalDefinitionAudiPlayer();}
            });
        }, 100);
    }
    private void normalDefinitionAudiPlayer(){
        new Handler(Looper.getMainLooper()).post(() -> vpPlyImg.setImageResource(R.drawable.mx_play_normal));
        singleMediaPlayHandler.post(defNormalRunnable);
    }
    private final Runnable defNormalRunnable = () -> singlePlayAudio(defStart(), defDuration());
    private int defStart(){return listWordModel.get(currentItem()).getDefStart();}
    private int defDuration(){return listWordModel.get(currentItem()).getDefEnd() - defStart();}




    private void examplePlayerOnceOrAgainDeterminer(){
        if (plyAgainFlag){
            examplePlayerOnceOrAgain();
        }else {
            exampleAutoPlayer();
        }
    }

    @Override
    public void exampleAudioPlayer(boolean autoPlyFlag) {
        ExecutorService thread = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        thread.execute(() ->{
            allAgainCanceler();
            handler.post(() ->{
                if (autoPlyFlag){
                    exampleAutoPlayer();
                }else {
                    farsiReSetter();
                    normalExampleAudioPlayer();
                }
            });
        });
    }
    private void exampleAutoPlayer(){
        twoMediaPlayersPause();
        normalExampleAudioPlayer();
        shwTranslationsInAutoPlay();
    }
    private void examplePlayerOnceOrAgain(){
        new Handler(Looper.getMainLooper()).postDelayed(() ->{
            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayHandler = new Handler(Looper.getMainLooper());

            autoPlayThread.execute(() ->{
                shwTranslationsInAutoPlay();
                if (!isFinishing()){normalExampleAudioPlayer();}
            });
        }, 100);
    }
    private void normalExampleAudioPlayer(){
        new Handler(Looper.getMainLooper()).post(() -> vpPlyImg.setImageResource(R.drawable.mx_play_normal));
        singleMediaPlayHandler.post(normalExmplRunnable);
    }
    private final Runnable normalExmplRunnable = () -> singlePlayAudio(exmplStart(), exmplDuration());
    private int exmplStart(){return listWordModel.get(currentItem()).getExmplStart();}
    private int exmplDuration(){return listWordModel.get(currentItem()).getExmplEnd() - exmplStart();}




    private void farsiReSetter(){
        shwFarsiFlags = new boolean[]{false, false, false, false};
        shwTranslationsInAutoPlay();
    }
    /***********************************************************************************************/













    private int wordDuration(int index){return (wordEnd(index) - wordStart(index));}
    private int wordStart(int index){return listWordModel.get(index).getWrdStart();}// ?? 9
    private int wordEnd(int index){return listWordModel.get(index).getWrdEnd();}


    private int defDuration(int index){
        return (defEnd(index) - defStart(index));
    }
    private int defStart(int index){
        return listWordModel.get(index).getDefStart();
    }
    private int defEnd(int index){
        return listWordModel.get(index).getDefEnd();
    }


    private int exmplDuration(int index){
        return (exmplEnd(index) - exmplStart(index));
    }
    private int exmplStart(int index){
        return listWordModel.get(index).getExmplStart();
    }
    private int exmplEnd(int index){
        return listWordModel.get(index).getExmplEnd();
    }







    @Override
    protected void onPause() {
        super.onPause();
        if (!plyAgainFlag){
            onPauseFunctions();
        }
        final boolean isOnPause = this.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED);
    }

    private void onPauseFunctions(){
        mainPlayerOnPause();
    }
    private void mainPlayerOnPause(){
        if (mainPlayer != null){mediaPosition = mainPlayer.getCurrentPosition();}
        pauseMediaPlayers();
    }
    private void pauseMediaPlayers(){
        pauseMainPlayer();
        twoMediaPlayersPause();
        singleMediaPlayerPause();
    }
    private void twoMediaPlayersPause(){
        if (twoMediaPlayer != null){
            twoMediaPlayer.pause();
            twoMediaPlayHandler.removeCallbacks(twoAudioOneThread);
            twoMediaPlayHandler.removeCallbacks(twoAudioTwoThread);
        }
    }
    private void singleMediaPlayerPause(){
        if ((singleMediaPlayer != null) && singleMediaPlayer.isPlaying()){
            singleMediaPlayer.pause();
            singleMediaPlayHandler.removeCallbacks(singleAudioThread);
        }
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        mediaPlayerOnDestroy();
    }

    private void mediaPlayerOnDestroy(){
        mainMediaPlayDestroyer();
        twoMediaPlayDestroyer();
        singleMediaPlayDestroyer();
    }
    private void mainMediaPlayDestroyer(){
        if (mainPlayer.isPlaying()){
            mainPlayer.pause();
            mainPlayer.reset();
            mainPlayer.stop();
            mainPlayer.release();

            durationHandler.removeCallbacks(updateVpSeekBar);

        }
    }
    private void twoMediaPlayDestroyer(){
        if ((twoMediaPlayer != null) && twoMediaPlayer.isPlaying()){
            twoMediaPlayer.pause();
            twoMediaPlayer.reset();
            twoMediaPlayer.stop();
            twoMediaPlayer.release();

            twoMediaPlayHandler.removeCallbacks(twoAudioOneThread);
            twoMediaPlayHandler.removeCallbacks(twoAudioTwoThread);
        }
    }
    private void singleMediaPlayDestroyer(){
        if (singleMediaPlayer.isPlaying()) {
            singleMediaPlayer.pause();
            singleMediaPlayer.stop();
            singleMediaPlayer.reset();
            singleMediaPlayer.release();

            singleMediaPlayHandler.removeCallbacks(wordNormalRunnable);
            singleMediaPlayHandler.removeCallbacks(defNormalRunnable);
            singleMediaPlayHandler.removeCallbacks(normalExmplRunnable);
            singleMediaPlayHandler.removeCallbacks(singleAudioThread);

        }
    }




    @Override
    public void onStop() {
        super.onStop();
        vpCurrentItem = wordViewPager.getCurrentItem();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.clear();
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.slide_word_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case (R.id.slide_word_settings_menu):
                startSettingActivity();
                return true;
            case (R.id.slide_word_edit_word_menu):
                editWordDialog();
                Toast.makeText(this, "Edit Word Is Under Process", Toast.LENGTH_SHORT).show();
                return true;
            case (R.id.slide_word_add_note_menu):
                addNoteDialog();
                Toast.makeText(this, "Add Note Is Under Process", Toast.LENGTH_SHORT).show();
                return true;
            case (R.id.slide_word_share_word_menu):
                //Toast.makeText(this, "Sharing Word Is Under Process", Toast.LENGTH_SHORT).show();
                shareIconChanger();
                return true;
            case (R.id.slide_word_review_menu):
                markedWordActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareIconChanger(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareContentValue());
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, "لغت را با دوستان خود به اشتراک بزار..");
        startActivity(shareIntent);
    }
    private String shareContentValue(){
        final String word = " لغت : " + "\"" + listWordModel.get(currentItem()).getWord() + "  ";
        final String phonetic =  listWordModel.get(currentItem()).getPhonetic() + " \" " + "\n" ;
        final String wordTranslate = " ترجمه لغت : " + "\"" + listWordModel.get(currentItem()).getTranslateWord() + " \" " + "\n";
        final String Definition = " تعریف : \n" + "\"" + listWordModel.get(currentItem()).getDefinition() + " \" " + "\n" ;
        final String definitionTranslation = " معنی تعریف : \n" + "\"" + listWordModel.get(currentItem()).getTranslateDef() + " \" " + "\n";
        final String example = " مثال : \n" + "\"" + listWordModel.get(currentItem()).getExample() + " \" " + "\n" ;
        final String exampleTranslation = " معنی مثال : \n" + "\"" + listWordModel.get(currentItem()).getTranslateExmpl() + " \" " + "\n";

        return word + phonetic + wordTranslate +
                Definition + definitionTranslation +
                example + exampleTranslation;
    }


    private void editWordDialog(){
        EditWordDialogFragment ewDialogFragment =
                EditWordDialogFragment.newInstance(editTextValuesExtractor(), imgUrlVal(), dbInfoList());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("editDialog");
        if (prev != null){
            ft.remove(prev);
        }
        ewDialogFragment.show(ft, "editDialog");
    }
    private String imgUrlVal(){
        return listWordModel.get(currentItem()).getImgUri();
    }

    private void addNoteDialog(){
        AddNoteDialogFragment addNoteDialogFragment =
                AddNoteDialogFragment.newInstance(editTextValuesExtractor(), dbInfoList());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("addNoteDialog");
        if (prev != null){
            ft.remove(prev);
        }
        addNoteDialogFragment.show(ft, "addNoteDialog");
    }

    private String[] editTextValuesExtractor(){
        String[] edtTxtValues = new String[5];
        edtTxtValues[0] = listWordModel.get(currentItem()).getTranslateWord();
        edtTxtValues[1] = listWordModel.get(currentItem()).getTranslateDef();
        edtTxtValues[2] = listWordModel.get(currentItem()).getTranslateExmpl();
        edtTxtValues[3] = listWordModel.get(currentItem()).getWord();
        edtTxtValues[4] = listWordModel.get(currentItem()).getPhonetic();
        return edtTxtValues;
    }
    private int[] dbInfoList(){
        int[] dbInfoList = new int[3];
        dbInfoList[0] = dbNum;
        dbInfoList[1] = unitNum;
        dbInfoList[2] = listWordModel.get(currentItem()).getId();
        return dbInfoList;
    }




    private void startSearchActivity(){
        Intent searchIntent = new Intent(this, SearchWordsActivity.class);
        startActivity(searchIntent);
    }
    private void startSettingActivity(){
        Intent settingIntent = new Intent(this, SettingsPreferencesActivity.class);
        startActivity(settingIntent);
    }

    private void viewsFinderById(){
        hardBtn = findViewById(R.id.slide_word_hard_button);
        easyBtn = findViewById(R.id.slide_word_easy_button);
        vpSeekBar = findViewById(R.id.slide_word_detailed_music_seek_bar);
        vpTxtDuration = findViewById(R.id.slide_word_detailed_progress_time_text);
        vpTxtTotalTime = findViewById(R.id.slide_word_detailed_audio_total_time_text);
        vpPlyImg = findViewById(R.id.slide_word_detailed_card_ply_image);
        backPressLayout = findViewById(R.id.slide_word_card_view_tab_layout_bck_bttn_layout);
        reviewImg = findViewById(R.id.slide_word_auto_play);
        marked_and_speedMetter = findViewById(R.id.slide_word_detail_marked_and_speed_meter);
        wordViewPager = findViewById(R.id.slide_word_card_view_viewpager);
        tabIndicator = findViewById(R.id.slide_word_indicator_view_pager);
        sample = findViewById(R.id.slide_word_card_view_unit);
        searchImgView = findViewById(R.id.slide_word_search_launcher);
        numDeterminer = findViewById(R.id.slide_word_card_view_unit_selected_number);
        easyHardLayout = findViewById(R.id.easy_or_har_buttons_layout);
        speedMeterLayout = findViewById(R.id.speed_meter_main_layout);
        Toolbar slideWordToolBar = findViewById(R.id.slide_word_card_view_toolbar);

        //peedImageView
        speedMeterImgView = findViewById(R.id.speed_meter_image_view);
        vrySlwImageView = findViewById(R.id.speed_very_slow_image_view);
        slwImageView = findViewById(R.id.speed_slow_image_view);
        normalImageView = findViewById(R.id.speed_normal_image_view);
        fstImageView = findViewById(R.id.speed_fast_image_view);
        vryFstImageView = findViewById(R.id.speed_too_fast_image_view);

        //speedTextView
        vrySlwTextView = findViewById(R.id.speed_very_slow_text_view);
        slwTextView = findViewById(R.id.speed_slow_text_view);
        normalTextView = findViewById(R.id.speed_normal_text_view);
        fstTextView = findViewById(R.id.speed_fast_text_view);
        vryFstTextView = findViewById(R.id.speed_too_fast_text_view);

        //if (autoPlayFlag){autoMediaPlayer();}// ?? 3

        wordViewPager.setCurrentItem(vpCurrentItem, false);

        numDeterminer.setText(String.valueOf(unitNum));
        setSupportActionBar(slideWordToolBar);
        viesOnClickListener();
        speedDeterminerFunctions();
    }
    private void extrasGetter(){
        Intent vpPIntent = getIntent();
        Intent intent = getIntent();
        Intent dbIntent = getIntent();
        dbNum = dbIntent.getIntExtra(strDbNumber, 0);
        unitNum = intent.getIntExtra(strUnitNumber, 0);
        vpCurrentItem = vpPIntent.getIntExtra(strWordId, 0);
        autoPlayFlag = vpPIntent.getBooleanExtra(autoPlayFlagKey, false);
        flagListAutoPly = vpPIntent.getBooleanArrayExtra(autoPlayFlagListKey);
        autoPlayerSpeedVal = vpPIntent.getFloatExtra(speedMeterKey, 1f);
        autoPlayDeterminer(flagListAutoPly);
    }
    private void autoPlayDeterminer(boolean[] flagList){
        allPlayFlag = flagList != null && flagList[0];
        wordPlayFlag = flagList != null &&  flagList[1];
        defPlayFlag = flagList != null &&  flagList[2];
        exmplPlayFlag = flagList != null &&  flagList[3];
        plyAgainFlag = flagList != null &&  flagList[4];
        plyNxtFlag = flagList != null &&  flagList[5];

        shwFarsiFlags =
                new boolean[] {
                        flagList != null && flagList[6]
                        ,flagList != null && flagList[7]
                        ,flagList != null &&  flagList[8]
                        ,flagList != null &&  flagList[9]};
    }
    private void viesOnClickListener(){
        vpPlyImg.setOnClickListener(this);
        reviewImg.setOnClickListener(this);
        hardBtn.setOnClickListener(this);
        easyBtn.setOnClickListener(this);
        marked_and_speedMetter.setOnClickListener(this);
        searchImgView.setOnClickListener(this);
        backPressLayout.setOnClickListener(this);
        sample.setOnClickListener(this);
    }

    private void speedDeterminerFunctions(){
        speedMeterImgView.setOnClickListener(v ->{
            cardViewGetDownAnimation();
            new Handler(Looper.getMainLooper()).postDelayed(this::upAnimationSpeedMeterCardView, 350);
        });


        vrySlwImageView.setOnClickListener(v -> {
            mediaSpeedSetter(0.5f);
            speedTextColorDeterminer(1);
            cardViewsAnimationDeterminer();

        });
        slwImageView.setOnClickListener(v -> {
            mediaSpeedSetter(0.75f);
            speedTextColorDeterminer(2);
            cardViewsAnimationDeterminer();
        });
        normalImageView.setOnClickListener(v -> {
            mediaSpeedSetter(1.0f);
            speedTextColorDeterminer(3);
            cardViewsAnimationDeterminer();
        });
        fstImageView.setOnClickListener(v -> {
            mediaSpeedSetter(1.5f);
            speedTextColorDeterminer(4);
            cardViewsAnimationDeterminer();
        });
        vryFstImageView.setOnClickListener(v -> {
            mediaSpeedSetter(2.0f);
            speedTextColorDeterminer(5);
            cardViewsAnimationDeterminer();
        });
    }
    private void mediaSpeedSetter(float speed){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            PlaybackParams playbackParams = new PlaybackParams();
            playbackParams.setSpeed(speed);
            mainPlayer.setPlaybackParams(playbackParams);
            speedMeter = speed;
            playMainPlayer();
        }

    }
    private PlaybackParams autoPlayBackParams(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            PlaybackParams playbackParams = new PlaybackParams();
            return playbackParams.setSpeed(autoPlayerSpeedVal);
        }else{
            return null;
        }
    }
    private void cardViewsAnimationDeterminer(){
        downAnimationSpeedMeterCardView();
        //new Handler(Looper.getMainLooper()).postDelayed(this::cardViewGetUpAnimation, 350);
    }

    private void downAnimationSpeedMeterCardView(){
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 500f);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(300);
        animator.addUpdateListener(animation -> {
            final float progress = (float) animation.getAnimatedValue();
            speedMeterLayout.setTranslationY(progress);
            new Handler(Looper.getMainLooper()).postDelayed(() -> speedMeterLayout.setVisibility(View.GONE), 350);
        });
        animator.start();
        speedMeterFlag = false;
    }
    private void upAnimationSpeedMeterCardView(){
        ValueAnimator animator = ValueAnimator.ofFloat(500f, 0f);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.setDuration(300);
        animator.addUpdateListener(animation -> {
            final float progress = (float) animation.getAnimatedValue();
            speedMeterLayout.setVisibility(View.VISIBLE);
            speedMeterLayout.setTranslationY(progress);
        });
        animator.start();
        speedMeterFlag = true;
    }


    private void speedTextColorDeterminer(int txtPosition){
        switch (txtPosition){
            case 1:
                tooSlowTxtViewBolder();
                break;
            case 2:
                slowTxtViewBolder();
                break;
            case 3:
                normalTxtViewBolder();
                break;
            case 4:
                fastTxtViewBolder();
                break;
            case 5:
                tooFastTxtViewBolder();
                break;
        }
    }
    private void tooSlowTxtViewBolder(){
        vrySlwTextView.setTextColor(ContextCompat.getColor(this, R.color.perColor));
        slwTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));
        normalTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));
        fstTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));
        vryFstTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));

        vrySlwTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        slwTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        normalTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        fstTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        vryFstTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        speedMeterImgView.setImageResource(speedImage(1));
    }
    private void slowTxtViewBolder(){
        slwTextView.setTextColor(ContextCompat.getColor(this, R.color.perColor));
        vrySlwTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));
        normalTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));
        fstTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));
        vryFstTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));

        slwTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        vrySlwTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        normalTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        fstTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        vryFstTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        speedMeterImgView.setImageResource(speedImage(2));
    }
    private void normalTxtViewBolder(){
        normalTextView.setTextColor(ContextCompat.getColor(this, R.color.perColor));
        vrySlwTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));
        slwTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));
        fstTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));
        vryFstTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));

        vrySlwTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        slwTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        normalTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        fstTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        vryFstTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        speedMeterImgView.setImageResource(speedImage(3));
    }
    private void fastTxtViewBolder(){
        fstTextView.setTextColor(ContextCompat.getColor(this, R.color.perColor));
        vrySlwTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));
        slwTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));
        normalTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));
        vryFstTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));

        vrySlwTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        slwTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        normalTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        fstTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        vryFstTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        speedMeterImgView.setImageResource(speedImage(4));
    }
    private void tooFastTxtViewBolder(){
        vryFstTextView.setTextColor(ContextCompat.getColor(this, R.color.perColor));
        vrySlwTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));
        slwTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));
        normalTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));
        fstTextView.setTextColor(ContextCompat.getColor(this, R.color.setting_gray));

        vrySlwTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        slwTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        normalTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        fstTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        vryFstTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        speedMeterImgView.setImageResource(speedImage(5));
    }

    private int speedImage(int position){
        if (position == 5){
            return R.drawable.speed_too_fast_icon;
        }else if (position == 4){
            return R.drawable.speed_fast_icon;
        }else if (position == 3){
            return R.drawable.speed_normal_icon;
        }else if (position == 2){
            return R.drawable.speed_slow_icon;
        }else {
            return R.drawable.speed_too_slow_icon;
        }
    }



    private boolean isWordPlayedOnly(){return wordPlayFlag && !defPlayFlag && !exmplPlayFlag;}
    private boolean isDefPlayedOnly(){return defPlayFlag && !exmplPlayFlag && !wordPlayFlag;}
    private boolean isExmplPlayedOnly(){return exmplPlayFlag && !wordPlayFlag && !defPlayFlag;}


    private boolean isWordAndDef(){return wordPlayFlag && defPlayFlag && !exmplPlayFlag;}
    private boolean isWordAndExmpl(){return wordPlayFlag && exmplPlayFlag && !defPlayFlag;}
    private boolean isDefAndExmpl(){return defPlayFlag && exmplPlayFlag && !wordPlayFlag;}






    private void entranceAudioPlayFunctions(){

        if (isAllPlay()){
         playAllAudios(currentItem());
        }else if (isWordPly() && !isDefPly() && !isExmplPly()){
        normalWordAudioPlayer();
        }else if (!isWordPly() && isDefPly() && !isExmplPly()){
        normalDefinitionAudiPlayer();
        }else if (!isWordPly() && !isDefPly() && isExmplPly()){
        normalExampleAudioPlayer();
        }else if (isWordPly() && isDefPly() && !isExmplPly()){
         playTwoAudios(currentItem(), wrdStartNote, wrdDuraionNote, defStartNote, defDurationNote);
        }else if (isWordPly() && !isDefPly() && isExmplPly()){
        playTwoAudios(currentItem(), wrdStartNote, wrdDuraionNote, exmplStartNote, exmplDurationNote);
        }else if (!isWordPly() && isDefPly() && isExmplPly()){
         playTwoAudios(currentItem(), defStartNote, defDurationNote, exmplStartNote, exmplDurationNote);
        }
    }

    private boolean isAllPlay(){return isWordPly() && isDefPly() && isExmplPly();}
    private boolean isWordPly(){
        plyAudioPreferences = getSharedPreferences(plyAudioPreferenceName, MODE_PRIVATE);
        return plyAudioPreferences.getBoolean(wordPlyKey, true);
    }
    private boolean isDefPly(){
        plyAudioPreferences = getSharedPreferences(plyAudioPreferenceName, MODE_PRIVATE);
        return plyAudioPreferences.getBoolean(definitionPlyKey, false);
    }
    private boolean isExmplPly(){
        plyAudioPreferences = getSharedPreferences(plyAudioPreferenceName, MODE_PRIVATE);
        return plyAudioPreferences.getBoolean(examplePlyKey, false);
    }

    private void shwTranslationsInAutoPlay(){
        WordSlideFragmentPagerAdapter.shwFarsiFlagsList(shwFarsiFlags);
    }



    private boolean canAudioPlayItSelf(){
        boolean isPlaying = false;
        try {
            isPlaying = mainPlayer != null && mainPlayer.isPlaying();
        }catch (Exception e){e.printStackTrace();}

        return !isPlaying && !autoPlayFlag && !plyAgainFlag;
    }

    private int currentItem(){return wordViewPager.getCurrentItem();}
    private boolean canPlyAgain(){return !isFinishing() && plyAgainFlag;}

    private int lstSize(){ return listWordModel.size();}
    private int plusOneItem(){return currentItem() + 1;}


    @Override
    public WordModel getWordModel(WordModel model, int position) {
        return listWordModel.get(position);
    }

    @Override
    public int getCount() {
        return listSize();
    }
    private int listSize(){return listWordModel.size();}

}