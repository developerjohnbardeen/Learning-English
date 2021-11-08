package com.example.a4000essentialwordsbook1.QuizFile.QuizRezult;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.CorrectModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.SkippedModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.WrongModel;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Settings.SettingsPreferencesActivity;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Objects;

public class QuizMainResultActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar qrToolbar;
    private TabLayout qrTabLayout;
    private ViewPager2 qrViewPager;
    private RelativeLayout arBackBtnLayout;
    private TextView qrTitleTab, unitTitleNumber;
    private ImageView settingsImgView, autoPlayImgView;
    private int dbNum, unitNum;
    private int[] dbInfoList;

    private final String sDbNumber = ExtraNotes.DB_NUMBER;
    private final String sUnitNumber = ExtraNotes.UNIT_NUMBER;
    private final String sWordId = ExtraNotes.WORD_ID;
    private final String sQuizType = ExtraNotes.QUIZ_TYPE;
    private final String sCorrectList = ExtraNotes.CORRECT_LIST;
    private final String sWrongList = ExtraNotes.WRONG_LIST;
    private final String sSkippedList = ExtraNotes.SKIPPED_LIST;

    //List
    private ArrayList<CorrectModel> correctList;
    private ArrayList<WrongModel> wrongList;
    private ArrayList<SkippedModel> skippedList;



    private QuizResultPagerAdapter qrPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main_rezult);


        //functions' order matters
        extrasGetter();
        dataListReceiver();
        viewFounder();
        viewPagerAdapterSetter();
        toolbarBackArrow();
    }


    private void viewPagerAdapterSetter(){
        qrPagerAdapter = new QuizResultPagerAdapter(QuizMainResultActivity.this
                , correctList , wrongList, skippedList, dbInfoList,
                getSupportFragmentManager(), getLifecycle());

        //qrViewPager.setAnimationCacheEnabled(true);
        //qrViewPager.requestDisallowInterceptTouchEvent(true);
        qrViewPager.setAdapter(qrPagerAdapter);
        //qrTabLayout.setupWithViewPager(qrViewPager);
        qrViewPager.requestDisallowInterceptTouchEvent(true);

        String[] viewPagerTitle = {"", "", "", ""};

        new TabLayoutMediator(qrTabLayout, qrViewPager,
                ((tab, position) ->
                    tab.setText(viewPagerTitle[position])
                )).attach();

        tabLayoutIconSetter();
    }

    private void toolbarBackArrow(){
        setSupportActionBar(qrToolbar);
        arBackBtnLayout.setOnClickListener(v -> finish());
    }


    public void tabLayoutIconSetter(){
        Objects.requireNonNull(Objects.requireNonNull(qrTabLayout.getTabAt(0)).setIcon(R.drawable.ic_baseline_pie_chart_24));
        Objects.requireNonNull(Objects.requireNonNull(qrTabLayout.getTabAt(1)).setIcon(R.drawable.ic_baseline_correct_answer_24));
        Objects.requireNonNull(Objects.requireNonNull(qrTabLayout.getTabAt(2)).setIcon(R.drawable.ic_baseline_wrong_answer_24));
        Objects.requireNonNull(Objects.requireNonNull(qrTabLayout.getTabAt(3)).setIcon(R.drawable.ic_skip_icon));
        tabIconsVisibility();
    }

    private void tabIconsVisibility(){

        qrViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (position == 0){
                    autoPlayImgView.setVisibility(View.GONE);
                    qrTitleTab.setText(R.string.pie_chart_result);
                }else if(position == 1){
                    autoPlayImgView.setVisibility(View.GONE);
                    qrTitleTab.setText(R.string.correct_answers);
                }else if(position == 2){
                    autoPlayImgView.setVisibility(View.VISIBLE);
                    qrTitleTab.setText(R.string.wrong_answers);
                }else {
                    autoPlayImgView.setVisibility(View.VISIBLE);
                    qrTitleTab.setText(R.string.skipped_answers);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }


    private void dataListReceiver(){
        correctList = this.getIntent().getParcelableArrayListExtra(sCorrectList);
        wrongList = this.getIntent().getParcelableArrayListExtra(sWrongList);
        skippedList = this.getIntent().getParcelableArrayListExtra(sSkippedList);
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


    private void viewFounder(){
        qrToolbar = findViewById(R.id.quiz_result_toolbar);
        qrTabLayout = findViewById(R.id.quiz_result_tabs);
        qrViewPager = findViewById(R.id.quiz_result_viewPager);
        arBackBtnLayout = findViewById(R.id.quiz_result_tab_layout_bck_bttn_layout);
        qrTitleTab = findViewById(R.id.quiz_result_title_tab);
        settingsImgView = findViewById(R.id.quiz_result_settings_img_view);
        autoPlayImgView = findViewById(R.id.quiz_result_auto_play_img_view);
        //unitTitleNumber = findViewById(R.id.unit_selected_number);

        unitTitleFun();
        thisOnClickListener();
    }

    private void thisOnClickListener(){
        autoPlayImgView.setOnClickListener(this);
        settingsImgView.setOnClickListener(this);
    }

    private void unitTitleFun(){
        String unitTitle = Integer.toString(unitNum);
        //unitTitleNumber.setText(unitTitle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.quiz_result_settings_img_view):
                startSettingsActivity();
                break;
            case (R.id.quiz_result_auto_play_img_view):
                Toast.makeText(this, "Auto Play Is On The Process..", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void startSettingsActivity(){
        Intent settingsIntent = new Intent(this, SettingsPreferencesActivity.class);
        startActivity(settingsIntent);
    }
}
