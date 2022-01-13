package com.example.a4000essentialwordsbook1.SelectedUnitTab;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SearchWordsClasses.SearchWordsActivity;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.AutoPlayDialogFragment;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.AutoPlayNotes;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ActivitySelectedTab extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private TextView unitTextNumber;
    private ViewPager2 viewPager2;
    private RelativeLayout backBtnLayout;
    private final String msg = "Android : ";
    private int dbNum, unitNum;
    private String unitAudio;
    private int mediaPosition;
    private CardView cardViewContainer;
    private MediaPlayer storyMediaPlayer;
    private TextView duration, totalTimeTextView, tabTitleTxtView;
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
    private int pagerPosition = 0;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_tab_activity);


        //functions' order matters
        viewsFinderById();
        allFunctions();
        
    }


    private void allFunctions(){
        //Functions' Order Matters

        extrasValueGetter();
        String strUnitNum = Integer.toString(unitNum);
        unitTextNumber.setText(strUnitNum);
        pagerAdapterSetter();
        toolbarBackArrow();
        customTabLayout();
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
            mediaPosition = storyMediaPlayer.getCurrentPosition();
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
        onResumeFunctions();
        Log.d(msg, "The onResume() event");
    }
    private void onResumeFunctions(){
        wordLisDataReSetter();
        thisOnClickListener();

        viewPager2.setCurrentItem(pagerPosition, false);
    }

    public void pagerAdapterSetter(){
        fmManager = getSupportFragmentManager();
        UnitSectionPagerAdapter pagerAdapter =
                new UnitSectionPagerAdapter(unitNum, dbNum,
                        fmManager, getLifecycle());
        viewPager2.requestDisallowInterceptTouchEvent(true);
        viewPager2.setAdapter(pagerAdapter);

    }
    private void mediaPlayerFunctions(){
        ExecutorService mediaThread = Executors.newSingleThreadExecutor();
        Handler mediaHandler = new Handler(Looper.getMainLooper());

        mediaThread.execute(() ->{
            audioFileReceiverFromDatabase(dbNum, unitNum);
            mediaHandler.post(this::initializeMediaPlayer);
        });
    }


    @SuppressLint("Range")
    public void audioFileReceiverFromDatabase(int dbId, int unitId){
        SQLiteDatabase db = unitListDatabase(dbId).getReadableDatabase();
        Cursor audioCursor = db.query(DB_NOTES.UNIT_TABLE, new String[]{DB_NOTES.UNIT_AUDIO}
                , DB_NOTES.UNIT_ID + " = ?", new String[]{Integer.toString(unitId)},
                null, null, null );
        if (audioCursor != null && audioCursor.getCount() != 0){
            while (audioCursor.moveToNext()) {
                unitAudio = audioCursor.getString(audioCursor.getColumnIndex(DB_NOTES.UNIT_AUDIO));
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

        final File audioDir = new File(Environment.DIRECTORY_DOWNLOADS, File.separator + "4000 Essential Words");

        final File audioMainPath = new File("Audio Files");
        final File audioWordPath = new File(audioMainPath, File.separator + "Unit Audios");


        final File audioWordBookPath = new File(audioWordPath, File.separator + "Book_" + dbNum);
        final File secondSubFile = new File(audioWordBookPath, File.separator + "." + new File(unitAudio).getName());

        final String plyPath = Environment.getExternalStoragePublicDirectory(audioDir.toString()).toString() + File.separator + secondSubFile.toString();

        final File path = new File(plyPath);
        path.exists();


        storyMediaPlayer = MediaPlayer.create(this, Uri.parse(plyPath));
        utils = new TimeUtil();
        finalTime = storyMediaPlayer.getDuration();
        seekbar.setMax((int) finalTime);
        seekbar.setProgress(mediaPosition);
        storyMediaPlayer.seekTo(mediaPosition);

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
            onBackPressed();
        });
        String[] viewPagerTitle = {"", "", "", ""};
        //tabLayout.setupWithViewPager(viewPager);
        viewPager2.requestDisallowInterceptTouchEvent(true);
        new TabLayoutMediator(tabLayout, viewPager2,
                ((tab, position) -> tab.setText(viewPagerTitle[position])
                )).attach();

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
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

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabTitleTxtView.setText(tabTitle(position));
                switch (position){
                    case 0:
                        viewPager2ZeroPosition();
                        break;
                    case 1:
                        viewPager2FirstPosition();
                        try {
                            mediaPlayerFunctions();
                        }catch (Exception e){Log.e("MediaError", "" + e);}
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

        new TabLayoutMediator(tabLayout, viewPager2,
                ((tab, position) -> {
                    //tab.setText(tabTitle(position));
                    tab.setIcon(tabIcons(position));
                }
                )).attach();

        //viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
    }
    private String tabTitle(int position){
        final String[] viewPagerTitle = {"word", "story", "quiz"};
        final String secondPart = "list";

        if (position == 0){
            return viewPagerTitle[position].substring(0, 1).toUpperCase() +
                    viewPagerTitle[position].substring(1) +
                    " " +
                    secondPart.substring(0, 1).toUpperCase() +
                    secondPart.substring(1);
        }else {
            return viewPagerTitle[position].substring(0, 1).toUpperCase() + viewPagerTitle[position].substring(1);
        }
    }
    private int tabIcons(int position){
        final int[] viewPagerIcon = {R.drawable.word_list, R.drawable.story_icon ,R.drawable.ic_puzzle};
        return viewPagerIcon[position];
    }

    private void viewPager2ZeroPosition(){
        cardViewGetDownAnimation();
        //pause();
        mediaPlayerOnDestroy();
        alternateIcon.setImageResource(R.drawable.ic_tool_bar_play_circle_filled_24);
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
        //pause();
        mediaPlayerOnDestroy();
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
        onBackPressedFunctions();
    }
    private void onBackPressedFunctions(){
        if (currentItem() == 0){
            super.onBackPressed();
        }else {
            viewPager2.setCurrentItem(0, true);
        }
    }

    private int currentItem(){return viewPager2.getCurrentItem();}


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
        pause();
        //currentPos = storyMediaPlayer.getCurrentPosition();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
        onStopFunctions();
    }

    private void onStopFunctions(){
        pause();
        pagerPosition = currentItem();
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
        try {
            if (storyMediaPlayer != null) {
                pause();
                storyMediaPlayer.release();
                durationHandler.removeCallbacks(updateSeekBarTime);
                storyMediaPlayer = null;
            }
        }catch (Exception e){e.printStackTrace();}

    }

    public void viewsFinderById(){
        cardViewContainer = findViewById(R.id.story_component_container);
        toolbar = findViewById(R.id.toolbar);
        tabTitleTxtView = findViewById(R.id.selected_book_tab_layout_txt_title);
        backBtnLayout = findViewById(R.id.tab_layout_bck_bttn_layout);
        viewPager2 = findViewById(R.id.viewPager);
        unitTextNumber = findViewById(R.id.unit_selected_tab_number);
        tabLayout = findViewById(R.id.tabs);
        duration = findViewById(R.id.progress_time_text);
        totalTimeTextView = findViewById(R.id.audio_total_time_text);
        seekbar = findViewById(R.id.music_seek_bar);
        plyImg = findViewById(R.id.ply_img_btn);
        searchIcon = findViewById(R.id.selected_search_launcher);
        alternateIcon = findViewById(R.id.selected_alternate_launcher);
    }
    private void thisOnClickListener(){
        plyImg.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
        alternateIcon.setOnClickListener(this);
    }


}