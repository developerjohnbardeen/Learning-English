package com.example.a4000essentialwordsbook1.SelectedUnitTab;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import com.example.a4000essentialwordsbook1.AudioTimeUtil.TimeUtil;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.UnitSqliteOpenHelper;
import com.google.android.material.tabs.TabLayout;
import java.util.Objects;



public class ActivitySelectedTab extends AppCompatActivity implements View.OnClickListener {
    private androidx.appcompat.widget.Toolbar toolbar;
    private TabLayout tabLayout;
    private TextView fixed_unit;
    private TextView unitTextNumber;
    private TextView titleTab;
    private ImageView imgBackBtn;
    private ViewPager viewPager;
    private UnitSectionPagerAdapter pagerAdapter;
    private RelativeLayout backBtnLayout;
    private final String msg = "Android : ";
    private int unitNum = 0;
    private int unitAudio = 0;
    private CardView cardViewContainer;
    private TextView txt;


    private MediaPlayer mediaPlayer;
    private TextView duration;
    private TextView totalTimeTextView;

    private long timeElapsed = 0;
    private long finalTime = 0;
    private final Handler durationHandler = new Handler();
    private SeekBar seekbar;
    private View view;
    private ImageView plyImg;
    private long current_pos;
    private TimeUtil utils;





    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_tab_activity);

        //functions' order matters
        viewsFinderById();
        view = new View(this);
        plyImg.setOnClickListener(this);
        extrasValueGetter();
        unitTextNumber.setText(" " + unitNum + ": ");
        pagerAdapterSetter();


        audioThread.run();
        initializeView();
        toolbarBackArrow();
        customTabLayout();
    }

    public void pagerAdapterSetter(){
        pagerAdapter = new UnitSectionPagerAdapter(unitNum ,ActivitySelectedTab.this, getSupportFragmentManager());
        viewPager.setAnimationCacheEnabled(true);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    private final Runnable audioThread = () -> audioFileReceiverFromDatabase(unitNum);

    public void audioFileReceiverFromDatabase(int position){
        UnitSqliteOpenHelper unitAudioDatabase = new UnitSqliteOpenHelper(this);
        SQLiteDatabase db = unitAudioDatabase.getReadableDatabase();
        Cursor audioCursor = db.query(DB_NOTES.UNIT_TABLE,
                new String[]{DB_NOTES.UNIT_AUDIO}
                , DB_NOTES.UNIT_ID + " = " + position,
                null, null, null, null );

        if (audioCursor != null && audioCursor.getCount() != 0){
            while (audioCursor.moveToNext()) {
                unitAudio = audioCursor.getInt(audioCursor.getColumnIndex(DB_NOTES.UNIT_AUDIO));
            }
        }

        db.close();
        assert audioCursor != null;
        audioCursor.close();
    }



    public void initializeView(){
        duration = findViewById(R.id.progress_time_text);
        totalTimeTextView = findViewById(R.id.audio_total_time_text);
        seekbar = findViewById(R.id.music_seek_bar);
        utils = new TimeUtil();


        mediaPlayer = MediaPlayer.create(this, unitAudio);
        finalTime = mediaPlayer.getDuration();


        seekbar.setMax((int) finalTime);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                current_pos = seekBar.getProgress();
                mediaPlayer.seekTo((int) current_pos);
            }
        });
        seekbar.setClickable(true);
        seekbar.setFocusable(true);
        seekbar.setEnabled(true);
    }

    public void play(View views){
        mediaPlayer.start();
        timeElapsed = mediaPlayer.getCurrentPosition();
        seekbar.setProgress((int) timeElapsed);
        durationHandler.postDelayed(updateSeekBarTime, 10);
        plyImg.setImageResource(R.drawable.mx_pause_normal);
    }

    private final Runnable updateSeekBarTime = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            timeElapsed = mediaPlayer.getCurrentPosition();
            seekbar.setProgress((int) timeElapsed);
            @SuppressLint("DefaultLocale")
            String stringTotalTime = "" + utils.milliSecondsToTimer(finalTime);
            @SuppressLint("DefaultLocale")
            String leftTime = "" + utils.milliSecondsToTimer(timeElapsed);
            duration.setText(leftTime);
            totalTimeTextView.setText(stringTotalTime);
            durationHandler.postDelayed(this, 10);
        }
    };


    public void pause(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            plyImg.setImageResource(R.drawable.mx_play_normal);
        }
    }



    @Override
    public void onClick(View v) {
        if (mediaPlayer.isPlaying()){
            pause(view);
        }else {
            play(view);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
        //Toast.makeText(this, "MainActivity is on start..", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
    }

    //arrow back icon
    public void toolbarBackArrow(){

        setSupportActionBar(toolbar);
        backBtnLayout.setOnClickListener(v -> onBackPressed());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        titleTab.setText(R.string.word_list);
                        cardViewGetDownAnimation();
                        pause(view);
                        plyImg.setImageResource(R.drawable.mx_play_normal);

                        break;
                    case 1:
                        titleTab.setText(R.string.story);
                        cardViewGetUpAnimation();
                        break;
                    case 2:
                        titleTab.setText(R.string.quiz);
                        cardViewGetDownAnimation();
                        pause(view);
                        plyImg.setImageResource(R.drawable.mx_play_normal);
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Toast.makeText(ActivitySelectedTab.this, "the state is " + state, Toast.LENGTH_SHORT).show();
            }
        });
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


    //Menu Settings Code
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.word_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case (R.id.item_1):
                Toast.makeText(this, "Menu Item one", Toast.LENGTH_SHORT).show();

                return true;
            case (R.id.item_2):
                Toast.makeText(this, "Menu Item two", Toast.LENGTH_SHORT).show();
                return true;
            case (R.id.item_3):
                Toast.makeText(this, "Menu Item three", Toast.LENGTH_SHORT).show();
                return true;
            case (R.id.item_4):
                Toast.makeText(this, "Menu Item four", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void extrasValueGetter(){
        Intent intent = getIntent();
        unitNum = intent.getIntExtra("unitNumber", 0);
    }

    public void viewsFinderById(){

        cardViewContainer = findViewById(R.id.story_component_container);
        plyImg = findViewById(R.id.card_image);
        //toolbarTitle();
        toolbar = findViewById(R.id.toolbar);
        backBtnLayout = findViewById(R.id.tab_layout_bck_bttn_layout);
        viewPager = findViewById(R.id.viewPager);
        imgBackBtn = findViewById(R.id.img_back_btn);
        //textViews
        fixed_unit = findViewById(R.id.unit);
        unitTextNumber = findViewById(R.id.unit_selected_number);
        titleTab = findViewById(R.id.title_tab);
        tabLayout = findViewById(R.id.tabs);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
        pause(view);
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
        pause(view);
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
        initializeView();
    }

    public void mediaPlayerOnDestroy(){
        if (mediaPlayer != null) {
            pause(view);
            mediaPlayer.release();
            durationHandler.removeCallbacks(updateSeekBarTime);
            mediaPlayer = null;
        }
    }
}