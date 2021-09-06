package com.example.a4000essentialwordsbook1.MarkedWords.ReviewWords;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.a4000essentialwordsbook1.Linsteners.TextProvider;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.UpdateDatabases.UpdateWordDatabase;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;

public class MainReviewMarkedWordActivity extends AppCompatActivity implements View.OnClickListener, TextProvider, FragmentReviewWord.RemoveCardItem {
    private int viewPosition;

    private ViewPager reviewWordViewPager;
    private TabLayout reviewWordTabIndicator;
    private Button iKnowBtn, needToKnowBtn;
    private ImageButton translateBtn;
    private ImageView plyImageBtn;
    private ProgressBar musicSeekBar;
    private TextView totalTimeTxt, remainingTimeTxt;
    private ReviewWordFragmentPagerAdapter fragmentPagerAdapter;
    private ArrayList<WordModel> reviewList;

    private MediaPlayer newMediaPlayer;
    private int start;
    private final int end = 5000;
    private final ArrayList<Integer> startList = new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_review_marked_word);

        //functions' order matters
        sampleAudioValues();
        newMediaPlayer = MediaPlayer.create(this, R.raw.a);
        extrasGetter();
        viewsFinder();
        sampleVoid();
    }


    private void sampleVoid(){
        fragmentPagerAdapter = new ReviewWordFragmentPagerAdapter(this, reviewList,
                this.getSupportFragmentManager(), 0, this);
        reviewWordViewPager.setAdapter(fragmentPagerAdapter);

        ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
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
                start = startList.get(position);
                needToKnowBtn.setOnClickListener(v ->
                        Toast.makeText(MainReviewMarkedWordActivity.this, "" + position, Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };


        reviewWordViewPager.addOnPageChangeListener(changeListener);
        reviewWordViewPager.setCurrentItem(viewPosition, true);
        reviewWordTabIndicator.setupWithViewPager(reviewWordViewPager, true);
    }



    private void playAudio() {
        boolean isPlaying = newMediaPlayer.isPlaying();

        if (isPlaying){
            handler.removeCallbacks(audioThread);
            newMediaPlayer.pause();
            newMediaPlayer.seekTo(start);
        }
        newMediaPlayer.seekTo(start);
        newMediaPlayer.start();
        handler.postDelayed(audioThread, end);
    }
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable audioThread = new Runnable() {
        @Override
        public void run() {
            newMediaPlayer.pause();
            newMediaPlayer.seekTo(start);
        }
    };



    private void removeCardItem(){
        int position = reviewWordViewPager.getCurrentItem();
        int columnId = reviewList.get(position).getId();
        updateHardFlagDatabase(columnId);
        reviewList.remove(position);
        fragmentPagerAdapter.notifyChangeInPosition(1);
        fragmentPagerAdapter.notifyDataSetChanged();
        reviewListSizeChecker();
    }
    private void reviewListSizeChecker(){
        if (reviewList.size() == 0){
            onBackPressed();
        }
    }

    private void updateHardFlagDatabase(int columnId){
        UpdateWordDatabase updateHardFlag = new UpdateWordDatabase(this);
        String columnName = DB_NOTES.HARD_FLAG;
        updateHardFlag.wordDatabaseUpdate(columnName, columnId, 0);
    }


    @Override
    public void onDestroy(){
        releaseMediaPlayer();
        super.onDestroy();
    }

    private void releaseMediaPlayer(){
        handler.removeCallbacks(audioThread);
        newMediaPlayer.stop();
        newMediaPlayer.reset();
        newMediaPlayer.release();
    }


    private void sampleAudioValues(){

        reviewList = new ArrayList<>();
        startList.add(5000);
        startList.add(10000);
        startList.add(15000);
        startList.add(20000);
        startList.add(25000);
    }


    private void viewsFinder(){
        reviewWordViewPager = findViewById(R.id.view_pager_review_marked_word);
        reviewWordTabIndicator = findViewById(R.id.review_marked_word_tab_indicator_view_pager);
        iKnowBtn = findViewById(R.id.review_marked_word_easy_button);
        needToKnowBtn = findViewById(R.id.review_marked_word_hard_button);
        //translateBtn = findViewById(R.id.review_marked_word_translate);
        plyImageBtn = findViewById(R.id.review_marked_word_detailed_card_ply_image);

        musicSeekBar = findViewById(R.id.review_marked_word_detailed_music_seek_bar);
        remainingTimeTxt = findViewById(R.id.review_marked_word_progress_time_text);
        totalTimeTxt = findViewById(R.id.review_marked_word_audio_total_time_text);

        thisClickListener();
    }
    private void thisClickListener(){
        iKnowBtn.setOnClickListener(this);
        plyImageBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.review_marked_word_easy_button):
                removeCardItem();
                break;
            case (R.id.review_marked_word_detailed_card_ply_image):
                playAudio();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void extrasGetter(){
        Intent pIntent = getIntent();
        reviewList = this.getIntent().getParcelableArrayListExtra("reviewList");
        viewPosition = pIntent.getIntExtra("cardPosition",0);
    }

    @Override
    public WordModel getWordModel(int position) {
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
}
