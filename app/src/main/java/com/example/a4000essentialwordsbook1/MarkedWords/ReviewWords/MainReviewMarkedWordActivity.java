package com.example.a4000essentialwordsbook1.MarkedWords.ReviewWords;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
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
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordDetailedInterfaces.EditedTranslationInterface;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordDetailedInterfaces.SaveEditsInterface;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.AutoPlayDialogFragment;
import com.example.a4000essentialwordsbook1.Settings.SettingListanerInterface.AutoPlayInterface;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.EditWordDialogFragment;
import com.example.a4000essentialwordsbook1.Settings.SettingsPreferencesActivity;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.AutoPlayNotes;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.AutoPlayTypeNotes.AutoPlayTypeNote;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;
import com.example.a4000essentialwordsbook1.UpdateDatabases.UpdateWordDatabase;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;




public class MainReviewMarkedWordActivity extends AppCompatActivity implements View.OnClickListener,
        TextProvider, FragmentReviewWord.RemoveCardItem, SaveEditsInterface, AutoPlayInterface, AudioPlayerListener {
    private int vpCurrentItem;

    private ViewPager2 reviewWordViewPager;
    private ViewPager2.OnPageChangeCallback changeListener;
    private FragmentManager rvwFragmentManager;
    private TabLayoutMediator tabLayoutMediator;
    private TabLayout reviewWordTabIndicator;
    private Button iKnowBtn, askMeAgainBtn;
    private ImageView plyImageBtn, settingIcon, autoPlayIcon;
    private ImageButton rvwBtnTranslation;
    private RelativeLayout backPressLayout;
    private Toolbar mainReviewToolBar;
    private SeekBar vpSeekBar;
    private TextView totalTimeTxt, remainingTimeTxt;
    private ReviewWordFragmentPagerAdapter fragmentPagerAdapter;
    private ArrayList<WordModel> reviewList;
    private final static String keyActivity = AutoPlayNotes.ACTIVITY_NAME;
    private final static String dbInfoKey = AutoPlayNotes.DB_INFO_DIALOG_LIST_KEY;
    private final String mainReviewWordActivity = AutoPlayNotes.MAIN_REVIEW_MARKED_WORD_ACTIVITY;
    private final String autoPlayFlagKey = ExtraNotes.AUTO_PLAY_FLAG_KEY;
    private final String sDbNumber = ExtraNotes.DB_NUMBER;
    private final String sUnitNumber = ExtraNotes.UNIT_NUMBER;
    private final String autoPlayFlagListKey = ExtraNotes.AUTO_PLAY_BOOLEAN_LIST;
    private final String sWordId = ExtraNotes.WORD_ID;
    private final String wrdStartNote = AutoPlayTypeNote.WRD_START;
    private final String wrdDuraionNote = AutoPlayTypeNote.WORD_DURATION;
    private final String defStartNote = AutoPlayTypeNote.DEFINITION_START;
    private final String defDurationNote = AutoPlayTypeNote.DEFINITION_DURATION;
    private final String exmplStartNote = AutoPlayTypeNote.EXAMPLE_START;
    private final String exmplDurationNote = AutoPlayTypeNote.EXAMPLE_DURATION;
    private boolean wordPlay, defPlay, exmplPlay, allPlay;
    private MediaPlayer mainMediaPlayer, singleMediaPlayer, twoMediaPlayer;
    private int plyAudio, mediaPosition;
    private int wrdDuration, defDuration, exmplDuration;
    private TimeUtil timeUtil;
    private long ttlMediaTime, timeElapsed;
    private ExecutorService autoPlayThread;
    private Handler autoPlayHandler, plyAgainHandler;
    private int dbNum, unitNum;
    private int[] dbInfoList;
    private int startAll, endAll, ttlIndex;
    private final int tmLineMines = 80;
    private int mainMediaPosition , seekBarPosition;
    private boolean autoPlayFlag, trnslFlag, mediaFlag;
    private boolean allPlayFlag, wordPlayFlag, defPlayFlag, exmplPlayFlag, plyAgainFlag, plyNxtFlag;
    private boolean[] flagListAutoPly, shwFarsiFlags;
    private WordModel wordModel;
    private EditedTranslationInterface editorInterFace;
    private MainReviewTranslationInterface translationInterface;

    private SharedPreferences plyAudioPreferences;
    private final String plyAudioPreferenceName = SettingsPreferencesNotes.SETTINGS_AUTO_PLAY_AUDIO_PREFERENCES;
    private final String wordPlyKey = SettingsPreferencesNotes.WORD_PLAY_KEY;
    private final String definitionPlyKey = SettingsPreferencesNotes.DEFINITION_PLAY_KEY;
    private final String examplePlyKey = SettingsPreferencesNotes.EXAMPLE_PLAY_KEY;
    private final int audioDelay = 500;




    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_review_marked_word);
        allFunctions();
    }


    private void allFunctions(){
        //functions' order matters
        extrasGetter();
        viewsFinder();
        mainReviewWordsViewPagerAdapter();
    }

    /*********************************************************************************************/

    private void twoMediaAutoPlay(int firstStart, int firstEnd, int secondStart, int secondEnd) {

        if (twoMediaPlayer.isPlaying()){
            twoMediaPlayHandler.removeCallbacks(twoAudioOneThread);
            twoMediaPlayHandler.removeCallbacks(twoAudioTwoThread);
            twoMediaPlayer.pause();
            twoMediaPlayer.seekTo(firstStart);
        }

        twoAudioOneThread = () -> {
            twoMediaPlayer.pause();
            twoMediaPlayer.seekTo(secondStart);
            twoMediaPlayer.start();
            twoMediaPlayHandler.postDelayed(twoAudioTwoThread, secondEnd);
        };
        twoAudioTwoThread = () -> {
            twoMediaPlayer.pause();
            twoMediaPlayer.seekTo(firstStart);
            autoViewPagerScroller();
        };
        twoMediaPlayer.seekTo(firstStart);
        twoMediaPlayer.start();
        twoMediaPlayHandler.postDelayed(twoAudioOneThread, firstEnd);
    }

    private final Handler twoMediaPlayHandler = new Handler(Looper.getMainLooper());
    private  Runnable twoAudioOneThread;
    private  Runnable twoAudioTwoThread;

    /**********************************************************************************************/

    private void singlePlayAudio(int start, int end) {
        new Handler(Looper.getMainLooper()).postDelayed(() ->{
            if (singleMediaPlayer != null) {
                boolean isPlaying = singleMediaPlayer.isPlaying();

                if (isPlaying) {
                    mediaPlayHandler.removeCallbacks(singleAudioThread);
                    singleMediaPlayer.pause();
                    singleMediaPlayer.seekTo(start);
                }
                singleAudioThread = () -> {
                    singleMediaPlayer.pause();
                    singleMediaPlayer.seekTo(start);
                    autoViewPagerScroller();
                };
                singleMediaPlayer.seekTo(start);
                singleMediaPlayer.start();

                if (singleMediaPlayer.isPlaying()){mediaPlayHandler.postDelayed(singleAudioThread, end);}
            }
        }, 200);
    }
    private final Handler mediaPlayHandler = new Handler(Looper.getMainLooper());
    private  Runnable singleAudioThread;
    private void autoViewPagerScroller(){
        if (plyAgainFlag) {
            int newPosition = (plusOneItem() == lstSize()) ? 0 : plusOneItem();
            this.reviewWordViewPager.setCurrentItem(newPosition, true);
        }else if (autoPlayFlag){
            this.reviewWordViewPager.setCurrentItem(currentItem() + 1, true);
        }
    }

    /**********************************************************************************************/


    private void wordAudioReceiver(int dbNumber, int unitNumber){
        SQLiteDatabase db = unitListDatabase(dbNumber).getReadableDatabase();

        Cursor awCursor = db.query(DB_NOTES.UNIT_TABLE,
                new String[]{DB_NOTES.UNIT_COMPLETE_WORD_AUDIO},
                DB_NOTES.UNIT_ID + " = ? ", new String[]{Integer.toString(unitNumber)},
                null, null, null);

        if (awCursor != null && awCursor.getCount() != 0){
            while (awCursor.moveToNext()){
                plyAudio = awCursor.getInt(awCursor.getColumnIndex(DB_NOTES.UNIT_COMPLETE_WORD_AUDIO));
            }
        }
        allAudioPlayers(plyAudio);
        mainMediaPlayer();

        assert awCursor != null;
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
    private void allAudioPlayers(int plyAudio){
        mainMediaPlayer = MediaPlayer.create(this, plyAudio);
        singleMediaPlayer = MediaPlayer.create(this, plyAudio);
        twoMediaPlayer = MediaPlayer.create(this, plyAudio);
    }
    private void mainMediaPlayer(){

        final int newSeekPosition = Math.max(seekBarPosition, 0);
        timeUtil = new TimeUtil();
        vpSeekBar.setMax(currentDuration(currentItem()));
        vpSeekBar.setProgress(newSeekPosition);
        final int newPosition = currentWrdStart(currentItem());
        mainMediaPlayer.seekTo(newPosition);
        new Handler(Looper.getMainLooper()).post(() -> plyImageBtn.setImageResource(R.drawable.mx_play_normal));

        vpSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                endSeekBarHandler.removeCallbacks(endSeekBarThread);
                final int mdPosition = (currentWrdStart(currentItem()) + seekBar.getProgress());
                mainMediaPlayer.seekTo(mdPosition);
                endSeekBarHandler.postDelayed(endSeekBarThread, endHandlerDelay());
            }
        });

    }


    private void mainReviewWordsViewPagerAdapter(){

        rvwFragmentManager = getSupportFragmentManager();
        rvwFragmentManager.addFragmentOnAttachListener((fragmentManager, fragment) -> {
            try {
                editorInterFace = (FragmentReviewWord) fragment;
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        fragmentPagerAdapter = new ReviewWordFragmentPagerAdapter(
                getLifecycle(), shwFarsiFlags, rvwFragmentManager, this);
        reviewWordViewPager.setAdapter(fragmentPagerAdapter);
        /*new Handler(Looper.getMainLooper()).postDelayed(() -> {
            reviewWordViewPager.setCurrentItem(vpCurrentItem, true);
        }, 100);*/


        reviewWordViewPagerOnChangeListener();
        reviewWordViewPager.setCurrentItem(vpCurrentItem, false);
        tabLayoutFunctions();
    }
    private void tabLayoutFunctions(){
        String[] tabNames = new String[reviewList.size()];
        tabLayoutMediator = new TabLayoutMediator(reviewWordTabIndicator, reviewWordViewPager,
                ((tab, position) -> tab.setText(tabNames[position])));
        tabLayoutMediator.attach();
    }
    private void reviewWordViewPagerOnChangeListener(){
        changeListener = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (reviewList.size() > 0) {
                    onPageSelectedFunctions();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        };
        reviewWordViewPager.registerOnPageChangeCallback(changeListener);
    }
    private void onPageSelectedFunctions(){
        mainMediaPosition = 0;
        seekBarPosition = 0;
        if (plyAgainFlag && !isFinishing() || autoPlayFlag){
            mdPlayersDestroyerAndAudioReceiver();
            autoMediaPlayer();
        }else {
            audioReceiverAndPlayer();
        }
    }
    private void audioReceiverAndPlayer(){
        mdPlayersDestroyerAndAudioReceiver();
        if (canAudioPlayItSelf()) {
            entranceAudioPlayFunctions();
        }
    }
    private void mdPlayersDestroyerAndAudioReceiver(){
        mediaPlayerOnDestroy();
        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> wordAudioReceiver(dbNumber(), unitNumber()));
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.main_review_marked_word_easy_button):
                removeCardItem();
                break;
            case (R.id.main_review_marked_word_detailed_card_ply_image):
                playOrPauseMainPlayer();
                break;
            case (R.id.main_marked_word_settings_launcher):
                settingStartActivity();
                break;
            case (R.id.main_marked_word_auto_play_launcher):
                autoPlayWord();
                Toast.makeText(this, "AutoPlay Is Under Process", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.main_marked_word_tab_layout_bck_bttn_layout):
                onBackPressed();
                break;
            case (R.id.main_review_slide_word_detail_all_translate_btn):
                trnslFlag = !trnslFlag;
                translationInterface.displayTranslations(trnslFlag);
                break;
            case (R.id.main_review_marked_word_hard_button):
                askAgainViewPagerScroller();
                break;
        }
    }


    private void askAgainViewPagerScroller(){

            if (canAskAgainPress()) {
                new Handler(Looper.getMainLooper()).postDelayed(() ->{
                    if ((currentItem() + 1) == reviewList.size()) {
                        reviewWordViewPager.setCurrentItem(0, true);
                    } else {
                        reviewWordViewPager.setCurrentItem(currentItem() + 1, true);
                    }
                }, 300);

            }else {
                Toast.makeText(this, "برنامه قادر به رد کردن لغت درهنگام پخش صوت نیست, لطفا بعدا دوباره امتحان کنین!", Toast.LENGTH_SHORT).show();
            }
    }

    private boolean canAskAgainPress(){
        boolean mainIsPlaying = true;
        boolean singleIsPlaying = true;
        boolean twoIsPlaying = true;

        try {
            if (mainMediaPlayer != null) {mainIsPlaying = mainMediaPlayer.isPlaying();}
            if (singleMediaPlayer != null) {singleIsPlaying = singleMediaPlayer.isPlaying();}
            if (twoMediaPlayer != null) {twoIsPlaying = twoMediaPlayer.isPlaying();}
        }catch (Exception e){e.printStackTrace(); }


        return !mainIsPlaying && !singleIsPlaying && !twoIsPlaying && !autoPlayFlag && !plyAgainFlag;
    }



    private void playOrPauseMainPlayer(){
        preDoMainPlayer();

        if (mainMediaPlayer.isPlaying()){
            pauseMainPlayer();
        }else {
            allAgainCanceler();
            playMainPlayer();
        }
    }

    private void preDoMainPlayer(){
        pauseMediaPlayers();
    }

    private void pauseMainPlayer(){
        if (mainMediaPlayer != null && mainMediaPlayer.isPlaying()) {
            mainMediaPlayer.pause();
            endSeekBarHandler.removeCallbacks(endSeekBarThread);
            durationHandler.removeCallbacks(updateVpSeekBar);
            plyImageBtn.setImageResource(R.drawable.mx_play_normal);
        }
    }
    private void playMainPlayer(){
        if (mainMediaPosition == 0){mainMediaPosition = currentWrdStart(currentItem());}
        mainMediaPlayer.seekTo(mainMediaPosition);
        vpSeekBar.setProgress(seekBarPosition);

        mainMediaPlayer.start();
        timeElapsed = mainMediaPlayer.getCurrentPosition() - currentWrdStart(currentItem());
        vpSeekBar.setProgress((int)timeElapsed);
        durationHandler.postDelayed(updateVpSeekBar, 100);
        plyImageBtn.setImageResource(R.drawable.mx_pause_normal);

        endSeekBarHandler.postDelayed(endSeekBarThread, endHandlerDelay());
    }
    private int endHandlerDelay(){
        return (currentExmplEnd(currentItem()) - mainMediaPlayer.getCurrentPosition());
    }
    private final Runnable updateVpSeekBar = new Runnable() {
        @Override
        public void run() {
            timeElapsed = mainMediaPlayer.getCurrentPosition() - reviewList.get(currentItem()).getWrdStart();
            mainMediaPosition = mainMediaPlayer.getCurrentPosition();
            seekBarPosition = vpSeekBar.getProgress();
            vpSeekBar.setProgress((int) timeElapsed);
            String totalPlayerTime = "" + timeUtil.milliSecondsToTimer(currentDuration(currentItem()));
            String leftTime = "" + timeUtil.milliSecondsToTimer(timeElapsed);
            remainingTimeTxt.setText(leftTime);
            totalTimeTxt.setText(totalPlayerTime);
            durationHandler.postDelayed(this, 100);
        }
    };
    private final Handler durationHandler = new Handler(Looper.getMainLooper());

    private final Handler endSeekBarHandler = new Handler(Looper.getMainLooper());
    private final Runnable endSeekBarThread = () ->{
        if (mainMediaPlayer != null) {
            final String val = "0:00";
            remainingTimeTxt.setText(val);
            plyImageBtn.setImageResource(R.drawable.mx_play_normal);
            mainMediaPlayer.pause();
            mainMediaPlayer.seekTo(currentWrdStart(currentItem()));
            vpSeekBar.setProgress(0);
            mainMediaPosition = 0;
            seekBarPosition = 0;
            this.durationHandler.removeCallbacks(this.updateVpSeekBar);
        }
    };


    private int currentWrdStart(int index){
        return reviewList.get(index).getWrdStart();
    }
    private int currentExmplEnd(int index){return reviewList.get(index).getExmplEnd();}
    private int currentDuration(int index){
        return reviewList.get(index).getExmplEnd() - reviewList.get(index).getWrdStart();
    }


    private void removeCardItem(){
        int columnId = reviewList.get(currentItem()).getId();
        updateHardFlagDatabase(columnId);
        reviewList.remove(currentItem());
        fragmentPagerAdapter.notifyItemRemoved(currentItem());
        fragmentPagerAdapter.notifyChangeInPosition(currentItem());
        reviewListSizeChecker();
    }
    private void updateHardFlagDatabase(int columnId){
        dbInfoList = new int[2];
        dbInfoList[0] = reviewList.get(reviewWordViewPager.getCurrentItem()).getBookNum();
        dbInfoList[1] = reviewList.get(reviewWordViewPager.getCurrentItem()).getUnitNum();
        UpdateWordDatabase updateHardFlag = new UpdateWordDatabase(this, dbInfoList);
        String columnName = DB_NOTES.HARD_FLAG;
        updateHardFlag.wordDatabaseUpdate(columnName, columnId, 0);
    }
    private void reviewListSizeChecker(){
        if (reviewList.size() == 0){
            onBackPressed();
        }
    }

    private void settingStartActivity(){
        Intent settingIntent = new Intent(this, SettingsPreferencesActivity.class);
        startActivity(settingIntent);
    }
    private void autoPlayWord() {
        AutoPlayDialogFragment playDialogFragment =
                AutoPlayDialogFragment.newInstance(mainReviewWordActivity, dbInfoList);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("autoPlayAudio");
        if (prev != null) {
            ft.remove(prev);
        }
        playDialogFragment.show(ft, "autoPlayAudio");
    }


    /**********************************************************************************************/

    @Override
    public void autoPlayer(boolean[] autoPlayFlagList) {
        autoPlayDeterminer(autoPlayFlagList);
        autoPlayFlag = !plyAgainFlag;
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
            autoTwoPlayOnceOrAgainDeterminer(autoPlyWordAndDefList());
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
        new Handler(Looper.getMainLooper()).postDelayed(() ->{
            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayHandler = new Handler(Looper.getMainLooper());

            mediaPlayerOnDestroy();

            autoPlayThread.execute(() -> {
                wordAudioReceiver(dbNumber(), unitNumber());
                shwTranslationsInAutoPlay();

                autoPlayHandler.postDelayed(() ->{
                    if (!isFinishing()){playAllAudios(currentItem());}
                },200);

            });

        }, 100);
    }
    private void autoPlayAllAgainPlaying(){
        new Handler().postDelayed(() ->{

            this.autoPlayThread = Executors.newSingleThreadExecutor();
            this.autoPlayHandler = new Handler(Looper.getMainLooper());
            mediaPlayerOnDestroy();

            autoPlayThread.execute(() ->{
                wordAudioReceiver(dbNumber(), unitNumber());
                shwTranslationsInAutoPlay();
                autoPlayHandler.postDelayed(() ->{
                    if (canPlyAgain()){playAllAudios(currentItem());}
                }, 200);
            });

        }, 100);
    }

    private void allAgainCanceler() {
        if (autoPlayThread != null) {this.autoPlayThread.shutdownNow();}
    }
    

    private void playAllAudios(int finalIndex){
        mediaPlayHandler.postDelayed(() -> singlePlayAudio(currentWrdStart(finalIndex), allDuration(finalIndex)), audioDelay);
    }
    private int allDuration(int finalIndex){
        return exmplEnd(finalIndex) - currentWrdStart(finalIndex);
    }
    private int lstSize(){return reviewList.size();}
    private int plusOneItem(){return (currentItem() + 1);}





    private void autoTwoPlayOnceOrAgainDeterminer(String[] valStr){
        if (plyAgainFlag){
            autoPlayAgainTwoAudio(valStr[0], valStr[1], valStr[2], valStr[3]);
        }else {
            autoPlayTwoAudio(valStr[0], valStr[1], valStr[2], valStr[3]);
        }
    }

    private void autoPlayTwoAudio(String startFirst, String durationFirst, String startSecond, String durationSecond){
        new Handler(Looper.getMainLooper()).postDelayed(() ->{
            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayHandler = new Handler(Looper.getMainLooper());

            mediaPlayerOnDestroy();

            autoPlayThread.execute(() ->{

                wordAudioReceiver(dbNumber(), unitNumber());
                shwTranslationsInAutoPlay();

                autoPlayHandler.postDelayed(() ->{
                playTwoAudios(currentItem(), startFirst, durationFirst, startSecond, durationSecond);
            }, 200);

            });
        },100);
    }
    private void autoPlayAgainTwoAudio(String startFirst, String durationFirst, String startSecond, String durationSecond){
        new Handler(Looper.getMainLooper()).postDelayed(() ->{
            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayHandler = new Handler(Looper.getMainLooper());
            mediaPlayerOnDestroy();

            autoPlayThread.execute(() ->{
                shwTranslationsInAutoPlay();
                wordAudioReceiver(dbNumber(), unitNumber());
                autoPlayHandler.postDelayed(() ->{
                    if (!isFinishing()){playTwoAudios(currentItem(), startFirst, durationFirst, startSecond, durationSecond);}
                }, 200);

            });
        }, 100);
    }
    private void playTwoAudios(int finalIndex, String startFirst, String durationFirst, String startSecond, String durationSecond){
        final int startOne = startAudio(startFirst, finalIndex);
        final int endOne = durationAudio(durationFirst, finalIndex);
        final int startTwo = startAudio(startSecond, finalIndex);
        final int endTwo = durationAudio(durationSecond, finalIndex);
        twoMediaAutoPlay(startOne, endOne, startTwo, endTwo);
    }

    private int ttlTwoDuration(int finalIndex, String durationFirst, String durationSecond){
        return durationAudio(durationFirst, finalIndex) + durationAudio(durationSecond, finalIndex);
    }


    private int startAudio(String type, int index){
        if (type.equalsIgnoreCase(wrdStartNote)){
            return wordStart(index);
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


    /**********************************************************************************************/

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
            //pauseMediaPlayers();
            allAgainCanceler();
            if (autoPlyFlag){
                autoWordPlayer();
            }else {
                farsiReSetter();
                normalWordAudioPlayer();
            }
        }, 100);
    }
    private void autoWordPlayer(){
        new Handler(Looper.getMainLooper()).postDelayed(() ->{

            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayHandler = new Handler(Looper.getMainLooper());
            mediaPlayerOnDestroy();
            autoPlayThread.execute(() ->{
                wordAudioReceiver(dbNumber(), unitNumber());
                shwTranslationsInAutoPlay();
                autoPlayHandler.postDelayed(this::normalWordAudioPlayer, 200);
            });

        }, 100);
    }
    private void wordPlayerOnceOrAgain(){

        new Handler(Looper.getMainLooper()).postDelayed(() ->{
            shwTranslationsInAutoPlay();

            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayHandler = new Handler(Looper.getMainLooper());
            mediaPlayerOnDestroy();
            autoPlayThread.execute(() ->{
                wordAudioReceiver(dbNumber(), unitNumber());
                shwTranslationsInAutoPlay();
                autoPlayHandler.postDelayed(() ->{if (!isFinishing()){normalWordAudioPlayer();}}, 200);
            });

        }, 100);
    }
    private void normalWordAudioPlayer(){
        new Handler(Looper.getMainLooper()).post(() -> plyImageBtn.setImageResource(R.drawable.mx_play_normal));
        mediaPlayHandler.post(wordNormalRunnable);
    }
    private final Runnable wordNormalRunnable = () -> singlePlayAudio(wrdStart(), wrdDuration());
    private int wrdStart(){return reviewList.get(currentItem()).getWrdStart();}
    private int wrdDuration(){return reviewList.get(currentItem()).getWrdEnd() - wrdStart();}



    private void definitionPlayerOnceOrAgainDeterminer(){
        if (plyAgainFlag){
            definitionPlayerOnceOrAgain();
        }else {
            definitionAutoPlayer();
        }
    }

    @Override
    public void definitionAudioPlayer(boolean autoPlyFlag) {
        new Handler(Looper.getMainLooper()).postDelayed(() ->{

            allAgainCanceler();
            if (autoPlyFlag){
                definitionAutoPlayer();
            }else {
                farsiReSetter();
                normalDefinitionAudiPlayer();
            }

        }, 100);
    }
    private void definitionAutoPlayer(){
        new Handler(Looper.getMainLooper()).postDelayed(() ->{
            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayHandler = new Handler(Looper.getMainLooper());

            mediaPlayerOnDestroy();
            autoPlayThread.execute(() ->{
                wordAudioReceiver(dbNumber(), unitNumber());
                shwTranslationsInAutoPlay();
                autoPlayHandler.postDelayed(this::normalDefinitionAudiPlayer, 200);
            });
        }, 100);
    }
    private void definitionPlayerOnceOrAgain(){
        new Handler(Looper.getMainLooper()).postDelayed(() ->{
            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayHandler = new Handler(Looper.getMainLooper());

            mediaPlayerOnDestroy();
            autoPlayThread.execute(() ->{
                wordAudioReceiver(dbNumber(), unitNumber());
                shwTranslationsInAutoPlay();
                autoPlayHandler.postDelayed(() ->{if (!isFinishing()){normalDefinitionAudiPlayer();}}, 200);
            });
        }, 100);
    }
    private void normalDefinitionAudiPlayer(){
        mediaPlayHandler.post(defNormalRunnable);
    }
    private final Runnable defNormalRunnable = () -> singlePlayAudio(defStart(), defDuration());
    private int defStart(){return reviewList.get(currentItem()).getDefStart();}
    private int defDuration(){return reviewList.get(currentItem()).getDefEnd() - defStart();}





    private void examplePlayerOnceOrAgainDeterminer(){
        if (plyAgainFlag){
            examplePlayerOnceOrAgain();
        }else {
            exampleAutoPlayer();
        }
    }

    @Override
    public void exampleAudioPlayer(boolean autoPlyFlag) {
       new Handler(Looper.getMainLooper()).postDelayed(() ->{
           allAgainCanceler();
           if (autoPlyFlag){
               exampleAutoPlayer();
           }else {
               farsiReSetter();
               normalExampleAudioPlayer();
           }
       }, 100);
    }
    private void exampleAutoPlayer(){
        new Handler(Looper.getMainLooper()).postDelayed(() ->{

            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayHandler = new Handler(Looper.getMainLooper());
            mediaPlayerOnDestroy();

            autoPlayThread.execute(() ->{
                wordAudioReceiver(dbNumber(), unitNumber());
                shwTranslationsInAutoPlay();
                autoPlayHandler.postDelayed(this::normalExampleAudioPlayer, 200);
            });


        }, 100);
    }
    private void examplePlayerOnceOrAgain(){
        new Handler(Looper.getMainLooper()).postDelayed(() ->{

            autoPlayThread = Executors.newSingleThreadExecutor();
            autoPlayHandler = new Handler(Looper.getMainLooper());
            mediaPlayerOnDestroy();

            autoPlayThread.execute(() ->{
                wordAudioReceiver(dbNumber(), unitNumber());
                shwTranslationsInAutoPlay();
                autoPlayHandler.postDelayed(() ->{if (!isFinishing()){normalExampleAudioPlayer();}}, 200);
            });


        }, 100);
    }
    private void normalExampleAudioPlayer(){
        mediaPlayHandler.post(normalExmplRunnable);
    }
    private final Runnable normalExmplRunnable = () -> singlePlayAudio(exmplStart(), exmplDuration());
    private int exmplStart(){return reviewList.get(currentItem()).getExmplStart();}
    private int exmplDuration(){return reviewList.get(currentItem()).getExmplEnd() - exmplStart();}


    private void farsiReSetter(){
        shwFarsiFlags = new boolean[]{false, false, false, false};
        shwTranslationsInAutoPlay();
    }


    /**********************************************************************************************/



    private int dbNumber(){return reviewList.get(currentItem()).getBookNum();}
    private int unitNumber(){return reviewList.get(currentItem()).getUnitNum();}
    private int currentItem(){return reviewWordViewPager.getCurrentItem();}





    private void viewPagerConditionInAutoPlay(){
        //reviewWordViewPagerOnChangeListener();
        int newPosition = currentItem();
        tabLayoutMediator.detach();
        reviewWordViewPager.setCurrentItem(0, false);
        reviewWordViewPager.setCurrentItem(newPosition, false);
        tabLayoutMediator.attach();
    }
    private void threadSleep(int duration){
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public WordModel getWordModel(WordModel model, int position) {
        return reviewList.get(position);
    }

    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public void removeItem() {
        removeCardItem();
    }

    private void viewPagerScroller(Handler handler, int currentItem){
        handler.postDelayed(() -> reviewWordViewPager.setCurrentItem(currentItem, true), 100);
    }




    private void allMarkedWordThread(){
        ExecutorService allWordThread = Executors.newSingleThreadExecutor();
        Handler allWordHandler = new Handler(Looper.getMainLooper());
        allWordThread.execute(() ->{
            allMarkedWordReceiver();
            allWordHandler.post(this::mainReviewWordsViewPagerAdapter);
        });
    }
    private void specificMarkedWordThread(int dbId){
        ExecutorService spcWordThread = Executors.newSingleThreadExecutor();
        Handler spcHandler = new Handler(Looper.getMainLooper());
        spcWordThread.execute(() ->{
            specificMarkedWordReceiver(dbId);
            spcHandler.post(this::mainReviewWordsViewPagerAdapter);
        });
    }
    private void allMarkedWordReceiver(){
        Cursor cursor = null;
        SQLiteDatabase db = null;
        reviewList = new ArrayList<>();
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

                        reviewList.add(listModel);
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
        reviewList = new ArrayList<>();
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

                    reviewList.add(listModel);
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


    private int wordDuration(int index){return (wordEnd(index) - wordStart(index));}
    private int wordStart(int index){return reviewList.get(index).getWrdStart();}
    private int wordEnd(int index){return reviewList.get(index).getWrdEnd();}

    private int defDuration(int index){
        return (defEnd(index) - defStart(index));
    }
    private int defStart(int index){
        return reviewList.get(index).getDefStart();
    }
    private int defEnd(int index){
        return reviewList.get(index).getDefEnd();
    }

    private int exmplDuration(int index){
        return (exmplEnd(index) - exmplStart(index));
    }
    private int exmplStart(int index){
        return reviewList.get(index).getExmplStart();
    }
    private int exmplEnd(int index){
        return reviewList.get(index).getExmplEnd();
    }


    private void shwTranslationsInAutoPlay(){
        ReviewWordFragmentPagerAdapter.shwFarsiFlagsList(shwFarsiFlags);
    }


    private void editWordDialog(){
        EditWordDialogFragment ewDialogFragment =
                EditWordDialogFragment.newInstance(editTextValuesExtractor(), dbInfoList());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null){
            ft.remove(prev);
        }
        ewDialogFragment.show(ft, "dialog");
    }
    private String[] editTextValuesExtractor(){
        String[] edtTxtValues = new String[5];
        edtTxtValues[0] = reviewList.get(currentItem()).getTranslateWord();
        edtTxtValues[1] = reviewList.get(currentItem()).getTranslateDef();
        edtTxtValues[2] = reviewList.get(currentItem()).getTranslateExmpl();
        edtTxtValues[3] = reviewList.get(currentItem()).getWord();
        edtTxtValues[4] = reviewList.get(currentItem()).getPhonetic();
        return edtTxtValues;
    }
    private int[] dbInfoList(){
        int[] dbInfoList = new int[3];
        dbInfoList[0] = reviewList.get(currentItem()).getBookNum();
        dbInfoList[1] = reviewList.get(currentItem()).getUnitNum();
        dbInfoList[2] = reviewList.get(currentItem()).getId();
        return dbInfoList;
    }


    private void entranceAudioPlayFunctions(){
        if (isAllPlay()) {
            playAllAudios(currentItem());
        } else if (isWordPly() && !isDefPly() && !isExmplPly()) {
            normalWordAudioPlayer();
        } else if (!isWordPly() && isDefPly() && !isExmplPly()) {
            normalDefinitionAudiPlayer();
        } else if (!isWordPly() && !isDefPly() && isExmplPly()) {
            normalExampleAudioPlayer();
        } else if (isWordPly() && isDefPly() && !isExmplPly()) {
            playTwoAudios(currentItem(), wrdStartNote, wrdDuraionNote, defStartNote, defDurationNote);
        } else if (isWordPly() && !isDefPly() && isExmplPly()) {
            playTwoAudios(currentItem(), wrdStartNote, wrdDuraionNote, exmplStartNote, exmplDurationNote);
        } else if (!isWordPly() && isDefPly() && isExmplPly()) {
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

    private boolean isWordPlayedOnly(){return wordPlayFlag && !defPlayFlag && !exmplPlayFlag;}
    private boolean isDefPlayedOnly(){return defPlayFlag && !exmplPlayFlag && !wordPlayFlag;}
    private boolean isExmplPlayedOnly(){return exmplPlayFlag && !wordPlayFlag && !defPlayFlag;}

    private boolean isWordAndDef(){return wordPlayFlag && defPlayFlag && !exmplPlayFlag;}
    private boolean isWordAndExmpl(){return wordPlayFlag && exmplPlayFlag && !defPlayFlag;}
    private boolean isDefAndExmpl(){return defPlayFlag && exmplPlayFlag && !wordPlayFlag;}

    private boolean canAudioPlayItSelf(){
        boolean isPlaying = false;
        try {
            isPlaying = mainMediaPlayer != null && mainMediaPlayer.isPlaying();
        }catch (Exception e){
            e.printStackTrace();
        }

        return !isPlaying && !plyAgainFlag && !autoPlayFlag;
        //return !allPlayFlag && !wordPlayFlag && !defPlayFlag && !exmplPlayFlag && !isPlaying;
    }

    @Override
    public void editsSaved(String wordTranslation, String defTranslation, String exmplTranslation) {
        reSetterViewPager(wordTranslation, defTranslation, exmplTranslation);
    }
    private void reSetterViewPager(String word, String def, String exmpl){
        ExecutorService reThread = Executors.newSingleThreadExecutor();
        Handler reHandler = new Handler(Looper.getMainLooper());
        reThread.execute(() ->{
            final int newWordId = reviewList.get(currentItem()).getId();
            reReceiverWordDatabase(newWordId);
            reHandler.post(() ->{
                //ReviewWordFragmentPagerAdapter.listChanger(reviewList.get(currentItem()));
                getWordModel(reviewList.get(currentItem()), currentItem());
                editorInterFace.translationEditor(word, def, exmpl);
            });
        });
    }
    private void reReceiverWordDatabase(int newWordId){

        SQLiteDatabase database = wordListDatabase(dbNumber()).getReadableDatabase();
        Cursor cursor = database.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitNumber(),
                new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD,
                        DB_NOTES.WORD_START, DB_NOTES.WORD_END, DB_NOTES.DEF_START, DB_NOTES.DEF_END, DB_NOTES.EXMPL_START, DB_NOTES.EXMPL_END,
                        DB_NOTES.DEFINITION_WORD, DB_NOTES.DEFINITION_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.AUDIO_WORD,
                        DB_NOTES.HARD_FLAG, DB_NOTES.EASY_FLAG},
                DB_NOTES.WORD_ID + " = ? ", new String[]{Integer.toString(newWordId)}, null, null, null);
        if (cursor != null && cursor.getCount() != 0){

            while (cursor.moveToNext()){
                wordModel = new WordModel();

                int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                int imgWord = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                int bookIdNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.BOOK_NUMBER));
                int unitIdNum = cursor.getInt(cursor.getColumnIndex(DB_NOTES.UNIT_NUMBER));
                int audio = cursor.getInt(cursor.getColumnIndex(DB_NOTES.AUDIO_WORD));
                int hardFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.HARD_FLAG));
                int easyFlag = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EASY_FLAG));
                int wrdStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_START));
                int wrdEnd  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_END));
                int defStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_START));
                int defEnd  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.DEF_END));
                int exmStart  = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_START));
                int exmEnd = cursor.getInt(cursor.getColumnIndex(DB_NOTES.EXMPL_END));
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
                reviewList.set(currentItem(), wordModel);
            }
            database.close();
            cursor.close();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (!plyAgainFlag || !autoPlayFlag) {
            pauseMediaPlayers();
        }
        final boolean isOnPause = this.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED);
    }

    private void pauseMediaPlayers() {
        if (mainMediaPlayer != null) {
            mediaPosition = mainMediaPlayer.getCurrentPosition();
        }
        pauseMainPlayer();
        //pauseManiMediaPlayer();
        pauseTwoMediaPlayer();
        singleMediaPlayer();

    }
    private void pauseManiMediaPlayer(){
        if (mainMediaPlayer != null && mainMediaPlayer.isPlaying()){
            mainMediaPlayer.pause();

            durationHandler.removeCallbacks(endSeekBarThread);
            endSeekBarHandler.removeCallbacks(updateVpSeekBar);

        }
    }
    private void pauseTwoMediaPlayer(){
        if (twoMediaPlayer != null && twoMediaPlayer.isPlaying()){
            twoMediaPlayer.pause();
            twoMediaPlayHandler.removeCallbacks(twoAudioOneThread);
            twoMediaPlayHandler.removeCallbacks(twoAudioTwoThread);
        }
    }
    private void singleMediaPlayer(){
        if (singleMediaPlayer != null && singleMediaPlayer.isPlaying()){
            singleMediaPlayer.pause();
            mediaPlayHandler.removeCallbacks(singleAudioThread);
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
        singleMediaDestroyer();
    }
    private void mainMediaPlayDestroyer(){
        if (mainMediaPlayer != null && mainMediaPlayer.isPlaying()) {
            mainMediaPlayer.pause();
            mainMediaPlayer.stop();
            mainMediaPlayer.release();

            durationHandler.removeCallbacks(updateVpSeekBar);
            endSeekBarHandler.removeCallbacks(endSeekBarThread);
        }
    }
    private void twoMediaPlayDestroyer(){
        if (twoMediaPlayer != null) {
            if (twoMediaPlayer.isPlaying()) {
                twoMediaPlayHandler.removeCallbacks(twoAudioOneThread);
                twoMediaPlayHandler.removeCallbacks(twoAudioTwoThread);

                twoMediaPlayer.pause();
                twoMediaPlayer.stop();
                twoMediaPlayer.reset();
                twoMediaPlayer.release();
            }
        }
    }
    private void singleMediaDestroyer(){
        try {
            if (singleMediaPlayer != null) {
                if (singleMediaPlayer.isPlaying()) {
                /*autoPlayHandler.removeCallbacks(defAutoRun);
                autoPlayHandler.removeCallbacks(exmplAutoRun);*/
                    mediaPlayHandler.removeCallbacks(normalExmplRunnable);
                    mediaPlayHandler.removeCallbacks(wordNormalRunnable);
                    mediaPlayHandler.removeCallbacks(defNormalRunnable);
                    mediaPlayHandler.removeCallbacks(singleAudioThread);

                    singleMediaPlayer.pause();
                    singleMediaPlayer.stop();
                    singleMediaPlayer.reset();
                    singleMediaPlayer.release();
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }





    private void viewsFinder(){
        reviewWordViewPager = findViewById(R.id.main_view_pager_review_marked_word);
        reviewWordViewPager.setUserInputEnabled(false);
        reviewWordTabIndicator = findViewById(R.id.main_review_marked_word_tab_indicator_view_pager);
        mainReviewToolBar = findViewById(R.id.main_marked_word_toolbar);
        iKnowBtn = findViewById(R.id.main_review_marked_word_easy_button);
        askMeAgainBtn = findViewById(R.id.main_review_marked_word_hard_button);
        plyImageBtn = findViewById(R.id.main_review_marked_word_detailed_card_ply_image);
        vpSeekBar = findViewById(R.id.main_review_marked_word_detailed_music_seek_bar);
        remainingTimeTxt = findViewById(R.id.main_review_marked_word_progress_time_text);
        totalTimeTxt = findViewById(R.id.main_review_marked_word_audio_total_time_text);
        settingIcon = findViewById(R.id.main_marked_word_settings_launcher);
        autoPlayIcon = findViewById(R.id.main_marked_word_auto_play_launcher);
        backPressLayout = findViewById(R.id.main_marked_word_tab_layout_bck_bttn_layout);
        rvwBtnTranslation = findViewById(R.id.main_review_slide_word_detail_all_translate_btn);
        setSupportActionBar(mainReviewToolBar);
        if (autoPlayFlag){autoMediaPlayer();}
        reviewWordViewPager.setCurrentItem(vpCurrentItem, true);
        thisClickListener();
    }
    private void extrasGetter(){
        Intent pIntent = getIntent();
        Intent dbIntent = getIntent();
        Intent unitIntent = getIntent();
        reviewList = this.getIntent().getParcelableArrayListExtra("reviewList");
        vpCurrentItem = pIntent.getIntExtra("cardPosition",0);
        dbNum = dbIntent.getIntExtra(sDbNumber, 0);
        unitNum = unitIntent.getIntExtra(sUnitNumber, 1);
        autoPlayFlag = unitIntent.getBooleanExtra(autoPlayFlagKey, false);
        flagListAutoPly = unitIntent.getBooleanArrayExtra(autoPlayFlagListKey);
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
    private void thisClickListener(){
        iKnowBtn.setOnClickListener(this);
        askMeAgainBtn.setOnClickListener(this);
        plyImageBtn.setOnClickListener(this);
        settingIcon.setOnClickListener(this);
        autoPlayIcon.setOnClickListener(this);
        backPressLayout.setOnClickListener(this);
        rvwBtnTranslation.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private boolean canPlyAgain(){return !isFinishing() && plyAgainFlag;}


    //Menu Settings Code
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_review_word_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case (R.id.main_review_edit_word_menu):
                editWordDialog();
                return true;
            case (R.id.main_review_add_note_menu):
                Toast.makeText(this, "Add Note Is Under Process", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

