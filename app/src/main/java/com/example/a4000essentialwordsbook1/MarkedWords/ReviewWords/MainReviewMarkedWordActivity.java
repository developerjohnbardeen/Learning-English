package com.example.a4000essentialwordsbook1.MarkedWords.ReviewWords;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordModel;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;

public class MainReviewMarkedWordActivity extends AppCompatActivity implements View.OnClickListener {
    private int viewPosition;

    private ViewPager reviewWordViewPager;
    private TabLayout reviewWordTabIndicator;
    private Button iKnowBtn, needToKnowBtn;
    private ImageButton translateBtn;
    private ImageView plyImageBtn;
    private ProgressBar musicSeekBar;
    private TextView totalTimeTxt, remainingTimeTxt;
    private ArrayList<WordModel> reviewList;

    private MediaPlayer newMediaPlayer;
    private int start;
    private int end = 5000;
    ArrayList<Integer> startList = new ArrayList<>();
    private final Handler handler = new Handler();


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_review_marked_word);

        reviewList = new ArrayList<>();
        startList.add(5000);
        startList.add(10000);
        startList.add(15000);
        startList.add(20000);
        startList.add(25000);


        newMediaPlayer = MediaPlayer.create(this, R.raw.a);

        //functions' order matters
        extrasGetter();
        viewsFinder();
        sampleVoid();
    }


    private void sampleVoid(){
        ReviewWordSlidePagerAdapter reviewPagerAdapter = new ReviewWordSlidePagerAdapter(this, reviewList);
        reviewWordViewPager.setAdapter(reviewPagerAdapter);

        ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
            boolean first = true;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if ((first && positionOffset == 0) && positionOffsetPixels == 0 && position == 0){
                    onPageSelected(0);
                    first = false;
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
        reviewWordViewPager.setOnPageChangeListener(changeListener);
        reviewWordViewPager.setCurrentItem(viewPosition, true);
        reviewWordTabIndicator.setupWithViewPager(reviewWordViewPager, true);
    }

    private void extrasGetter(){
        Intent pIntent = getIntent();
        reviewList = this.getIntent().getParcelableArrayListExtra("reviewList");
        viewPosition = pIntent.getIntExtra("cardPosition",0);
    }

    private void viewsFinder(){
        reviewWordViewPager = findViewById(R.id.view_pager_review_marked_word);
        reviewWordTabIndicator = findViewById(R.id.review_marked_word_tab_indicator_view_pager);
        iKnowBtn = findViewById(R.id.review_marked_word_easy_button);
        needToKnowBtn = findViewById(R.id.review_marked_word_hard_button);
        translateBtn = findViewById(R.id.review_marked_word_translate);
        plyImageBtn = findViewById(R.id.review_marked_word_detailed_card_ply_image);
        plyImageBtn.setOnClickListener(this);

        musicSeekBar = findViewById(R.id.review_marked_word_detailed_music_seek_bar);
        remainingTimeTxt = findViewById(R.id.review_marked_word_progress_time_text);
        totalTimeTxt = findViewById(R.id.review_marked_word_audio_total_time_text);
    }

    private void playAudio() {
        boolean playing = newMediaPlayer.isPlaying();

        if (playing){
            handler.removeCallbacks(audioThread);
            newMediaPlayer.pause();
            newMediaPlayer.seekTo(start);
        }
        newMediaPlayer.seekTo(start);
        newMediaPlayer.start();
        handler.postDelayed(audioThread, end);
    }

    private final Runnable audioThread = new Runnable() {
        @Override
        public void run() {
            newMediaPlayer.pause();
            newMediaPlayer.seekTo(start);

        }
    };


    @Override
    public void onClick(View v) {
        playAudio();
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

}
