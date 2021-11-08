package com.example.a4000essentialwordsbook1.SelectedUnitTab;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;
import com.example.a4000essentialwordsbook1.AudioTimeUtil.TimeUtil;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookTwo;
import com.example.a4000essentialwordsbook1.MarkedWords.MarkedWordActivity;
import com.example.a4000essentialwordsbook1.SearchWordsClasses.SearchWordsActivity;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.AutoPlayDialogFragment;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.AutoPlayNotes;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ActivitySelectedTab extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private TextView unitTextNumber;
    private ViewPager2 viewPager;
    private RelativeLayout backBtnLayout;
    private final String msg = "Android : ";
    private int dbNum, unitNum, unitAudio = R.raw.a;
    private CardView cardViewContainer;
    private MediaPlayer storyMediaPlayer;
    private TextView duration, totalTimeTextView;
    private long timeElapsed = 0, finalTime = 0;
    private SeekBar seekbar;
    private final String sDbNumber = ExtraNotes.DB_NUMBER;
    private final String sUnitNumber = ExtraNotes.UNIT_NUMBER;
    private final String sWordId = ExtraNotes.WORD_ID;
    private final String activityName = AutoPlayNotes.ACTIVITY_NAME;
    private final String dbInfoKey = AutoPlayNotes.DB_INFO_DIALOG_LIST_KEY;
    private final String selectedTabActivity = AutoPlayNotes.SELECTED_TAB_ACTIVITY;
    private boolean autoPlyFlag = false;
    private ImageView plyImg, searchIcon, alternateIcon;
    private long currentPos;
    private TimeUtil utils;
    private FragmentManager fmManager;
    private int viewPosition = -1;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_tab_activity);


        //functions' order matters
        viewsFinderById();
        extrasValueGetter();
        String strUnitNum = Integer.toString(unitNum);
        unitTextNumber.setText(strUnitNum);
        pagerAdapterSetter();
        toolbarBackArrow();
        customTabLayout();
    }

    public void pagerAdapterSetter(){
        fmManager = getSupportFragmentManager();
        UnitSectionPagerAdapter pagerAdapter =
                new UnitSectionPagerAdapter(unitNum, dbNum,
                        fmManager, getLifecycle());
        viewPager.requestDisallowInterceptTouchEvent(true);
        viewPager.setAdapter(pagerAdapter);
        String[] viewPagerTitle = {"", "", "", ""};
        new TabLayoutMediator(tabLayout, viewPager,
                ((tab, position) -> tab.setText(viewPagerTitle[position])
                )).attach();
    }

    private void mediaPlayerFunctions(){
        ExecutorService mediaThread = Executors.newSingleThreadExecutor();
        Handler mediaHandler = new Handler(Looper.getMainLooper());

        mediaThread.execute(() ->{
            audioFileReceiverFromDatabase(dbNum, unitNum);
            mediaHandler.post(this::initializeMediaPlayer);
        });
    }

    public void audioFileReceiverFromDatabase(int dbId, int unitId){
        SQLiteDatabase db = unitListDatabase(dbId).getReadableDatabase();
        Cursor audioCursor = db.query(DB_NOTES.UNIT_TABLE, new String[]{DB_NOTES.UNIT_AUDIO}
                , DB_NOTES.UNIT_ID + " = ?", new String[]{Integer.toString(unitId)},
                null, null, null );
        if (audioCursor != null && audioCursor.getCount() != 0){
            while (audioCursor.moveToNext()) {
                unitAudio = audioCursor.getInt(audioCursor.getColumnIndex(DB_NOTES.UNIT_AUDIO));
            }
        }
        db.close();
        assert audioCursor != null;
        audioCursor.close();
    }
    private SQLiteOpenHelper unitListDatabase(int dbId){
        if (dbId == 1){
            return new UnitDatabaseBookOne(this);
        }else if (dbId == 2){
            return new UnitDatabaseBookTwo(this);
        }else if (dbId == 3){
            return new UnitDatabaseBookThree(this);
        }else if (dbId == 4){
            return new UnitDatabaseBookFour(this);
        }else if (dbId == 5){
            return new UnitDatabaseBookFive(this);
        }else {
            return new UnitDatabaseBookSix(this);
        }
    }
    private void initializeMediaPlayer(){
        storyMediaPlayer = MediaPlayer.create(this, unitAudio);
        utils = new TimeUtil();
        finalTime = storyMediaPlayer.getDuration();
        seekbar.setMax((int) finalTime);
        storyMediaPlayer.seekTo((int) currentPos);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                currentPos = seekBar.getProgress();
                storyMediaPlayer.seekTo((int) currentPos);
            }
        });
        seekbar.setClickable(true);
        seekbar.setFocusable(true);
        seekbar.setEnabled(true);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.ply_img_btn):
                playOrPauseStory();
                break;
            case (R.id.selected_search_launcher):
                searchStartActivity();
                break;
            case (R.id.selected_alternate_launcher):
                alternateFunctions();
                break;
        }

    }

    private void playOrPauseStory(){
        if (storyMediaPlayer.isPlaying()){
            pause();
        }else {
            play();
        }
    }
    private void searchStartActivity(){
        Intent searchIntent = new Intent(this, SearchWordsActivity.class);
        startActivity(searchIntent);
    }
    private void alternateFunctions(){
        if (autoPlyFlag){
            autoPlayWordDialog();
        }else {
            reviewMarkedWordStartActivity();
        }
    }
    private void autoPlayWordDialog() {
        AutoPlayDialogFragment playDialogFragment =
                AutoPlayDialogFragment.newInstance(selectedTabActivity ,dbDialogInfoList());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("autoPlayAudio");
        if (prev != null) {
            ft.remove(prev);
        }
        playDialogFragment.show(ft, "autoPlayAudio");
    }
    private int[] dbDialogInfoList(){
        int[] list = new int[2];
        list[0] = dbNum;
        list[1] = unitNum;

        return list;
    }

    private void reviewMarkedWordStartActivity(){
        Intent reviewIntent = new Intent(this, MarkedWordActivity.class);
        startActivity(reviewIntent);
    }


    public void play(){
        storyMediaPlayer.start();
        timeElapsed = storyMediaPlayer.getCurrentPosition();
        seekbar.setProgress((int) timeElapsed);
        durationHandler.postDelayed(updateSeekBarTime, 100);
        plyImg.setImageResource(R.drawable.mx_pause_normal);
    }
    public void pause() {
        if (storyMediaPlayer != null) {
            storyMediaPlayer.pause();
            plyImg.setImageResource(R.drawable.mx_play_normal);
        }
    }
    private final Runnable updateSeekBarTime = new Runnable() {
        @Override
        public void run() {
            timeElapsed = storyMediaPlayer.getCurrentPosition();
            seekbar.setProgress((int) timeElapsed);
            String stringTotalTime = "" + utils.milliSecondsToTimer(finalTime);
            String leftTime = "" + utils.milliSecondsToTimer(timeElapsed);
            duration.setText(leftTime);
            totalTimeTextView.setText(stringTotalTime);
            durationHandler.postDelayed(this, 100);
        }
    };
    private final Handler durationHandler = new Handler(Looper.getMainLooper());




    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
    }

    @Override
    protected void onResume() {
        super.onResume();

        mediaPlayerFunctions();
        wordLisDataReSetter();
        Log.d(msg, "The onResume() event");
    }

    private void wordLisDataReSetter(){
        fmManager.addFragmentOnAttachListener(new FragmentOnAttachListener() {
            @Override
            public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {

            }
        });
    }

    //arrow back icon
    public void toolbarBackArrow(){

        setSupportActionBar(toolbar);
        backBtnLayout.setOnClickListener(v -> {
            viewPosition = 0;
            onBackPressed();
        });
        String[] viewPagerTitle = {"", "", "", ""};
        //tabLayout.setupWithViewPager(viewPager);
        viewPager.requestDisallowInterceptTouchEvent(true);
        new TabLayoutMediator(tabLayout, viewPager,
                ((tab, position) -> tab.setText(viewPagerTitle[position])
                )).attach();

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void customTabLayout(){
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.word_list);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.story_icon);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.ic_puzzle);


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        viewPager2ZeroPosition();
                        break;
                    case 1:
                        viewPager2FirstPosition();
                        break;
                    case 2:
                        viewPager2SecondPosition();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        //viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
    }
    private void viewPager2ZeroPosition(){
        cardViewGetDownAnimation();
        pause();
        alternateIcon.setImageResource(R.drawable.ic_quiz_result_play_circle_outline_24);
        plyImg.setImageResource(R.drawable.mx_play_normal);
        autoPlyFlag = true;
    }
    private void viewPager2FirstPosition(){
        cardViewGetUpAnimation();
        alternateIcon.setImageResource(R.drawable.ic_baseline_bookmark_24);
        autoPlyFlag = false;
    }
    private void viewPager2SecondPosition(){
        cardViewGetDownAnimation();
        autoPlyFlag = false;
        pause();
        plyImg.setImageResource(R.drawable.mx_play_normal);
        alternateIcon.setImageResource(R.drawable.ic_baseline_bookmark_24);
    }



    public void cardViewGetUpAnimation(){
        ValueAnimator animator = ValueAnimator.ofFloat(500f, 0f);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.setDuration(300);
        animator.addUpdateListener(animation -> {
            float progress = (float) animation.getAnimatedValue();
            cardViewContainer.setVisibility(View.VISIBLE);
            cardViewContainer.setTranslationY(progress);
        });
        animator.start();
    }
    public void cardViewGetDownAnimation(){
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 500f);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.setDuration(300);
        animator.addUpdateListener(animation -> {
            float progress = (float) animation.getAnimatedValue();
            cardViewContainer.setTranslationY(progress);
        });
        animator.start();
    }



    public void extrasValueGetter(){
        Intent intent = getIntent();
        dbNum = intent.getIntExtra(sDbNumber, 0);
        unitNum = intent.getIntExtra(sUnitNumber, 0);
    }

    @Override
    public void onBackPressed() {
        if (viewPosition != 0) {
            viewPosition = viewPager.getCurrentItem();
        }
        if (viewPosition == 0){
            super.onBackPressed();
        }else {
            viewPager.setCurrentItem(0, true);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
        pause();
        currentPos = storyMediaPlayer.getCurrentPosition();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
        onStopFunctions();
    }

    private void onStopFunctions(){
        pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
        mediaPlayerOnDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void mediaPlayerOnDestroy(){
        if (storyMediaPlayer != null) {
            pause();
            storyMediaPlayer.release();
            durationHandler.removeCallbacks(updateSeekBarTime);
            storyMediaPlayer = null;
        }

    }

    public void viewsFinderById(){
        cardViewContainer = findViewById(R.id.story_component_container);
        toolbar = findViewById(R.id.toolbar);
        backBtnLayout = findViewById(R.id.tab_layout_bck_bttn_layout);
        viewPager = findViewById(R.id.viewPager);
        unitTextNumber = findViewById(R.id.unit_selected_tab_number);
        tabLayout = findViewById(R.id.tabs);
        duration = findViewById(R.id.progress_time_text);
        totalTimeTextView = findViewById(R.id.audio_total_time_text);
        seekbar = findViewById(R.id.music_seek_bar);
        plyImg = findViewById(R.id.ply_img_btn);
        searchIcon = findViewById(R.id.selected_search_launcher);
        alternateIcon = findViewById(R.id.selected_alternate_launcher);

        thisOnClickListener();
    }
    private void thisOnClickListener(){
        plyImg.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
        alternateIcon.setOnClickListener(this);
    }


}