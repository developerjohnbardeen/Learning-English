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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.a4000essentialwordsbook1.Models.WordModel;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.Settings.SettingsDialogs.AutoPlayDialogFragment;
import com.example.a4000essentialwordsbook1.Settings.SettingsPreferencesActivity;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.AutoPlayNotes;
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
    private TextView qrTitleTab;
    private ImageView settingsImgView, autoPlayImgView;
    private int dbNum, unitNum;
    private int[] dbInfoList;
    private final String quizResultActivity = AutoPlayNotes.QUIZ_RESULT_ACTIVITY;

    private final String sDbNumber = ExtraNotes.DB_NUMBER;
    private final String sUnitNumber = ExtraNotes.UNIT_NUMBER;
    private final String sWordId = ExtraNotes.WORD_ID;
    private final String sQuizType = ExtraNotes.QUIZ_TYPE;
    private String quizType;
    private final String sCorrectList = ExtraNotes.CORRECT_LIST;
    private final String sWrongList = ExtraNotes.WRONG_LIST;
    private final String sSkippedList = ExtraNotes.SKIPPED_LIST;

    //List
    private ArrayList<WordModel> correctList;
    private ArrayList<WordModel> wrongList;
    private ArrayList<WordModel> skippedList;



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
        qrPagerAdapter = new QuizResultPagerAdapter(quizType,
                correctList , wrongList, skippedList, dbInfoList,
                getSupportFragmentManager(), getLifecycle());

        qrViewPager.setAdapter(qrPagerAdapter);
        qrViewPager.requestDisallowInterceptTouchEvent(true);


        new TabLayoutMediator(qrTabLayout, qrViewPager,
                ((tab, position) -> {
                    //tab.setText(tabTitle(position));
                    tab.setIcon(tabIcon(position));
                }
                )).attach();

        tabLayoutIconSetter();
    }
    private String tabTitle(int position){
        final String[] viewPagerTitle = {"Pie", "Correct", "Wrong", "Skipped"};
        final String[] secondPart = {"Chart", "Answer", "Answer", "Answer"};

        if (position == 0){
            return viewPagerTitle[position].substring(0, 1).toUpperCase() +
                    viewPagerTitle[position].substring(1) +
                    " " +
                    secondPart[position].substring(0, 1).toUpperCase() +
                    secondPart[position].substring(1);
        }else {
            return viewPagerTitle[position].substring(0, 1).toUpperCase() + viewPagerTitle[position].substring(1);
        }
    }
    private int tabIcon(int position){
        final int[] tabIcons = {R.drawable.ic_baseline_pie_chart_24
                ,R.drawable.ic_baseline_correct_answer_24
                ,R.drawable.ic_baseline_wrong_answer_24
                ,R.drawable.ic_skip_icon};
        return tabIcons[position];
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
                qrTitleTab.setText(tabTitle(position));
                autoPlayIconVisibility(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void autoPlayIconVisibility(int position) {
        if (position >= 2) {
            autoPlayImgView.setVisibility(View.VISIBLE);
        } else {
            autoPlayImgView.setVisibility(View.GONE);
        }
    }


    private void dataListReceiver() {
        correctList = this.getIntent().getParcelableArrayListExtra(sCorrectList);
        wrongList = this.getIntent().getParcelableArrayListExtra(sWrongList);
        skippedList = this.getIntent().getParcelableArrayListExtra(sSkippedList);
    }

    private void extrasGetter() {
        dbInfoList = new int[2];
        Intent dbIntent = getIntent();
        Intent unitIntent = getIntent();
        dbNum = dbIntent.getIntExtra(sDbNumber, 1);
        unitNum = unitIntent.getIntExtra(sUnitNumber, 1);
        quizType = unitIntent.getStringExtra(sQuizType);
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


        thisOnClickListener();
    }
    private String appBarTitle(){
        final String firstPart = "Quiz";
        final String secondPart = "Result";
        return firstPart.substring(0, 1).toUpperCase() +
               firstPart.substring(1) +
                " " +
                secondPart.substring(0, 1).toUpperCase() +
                secondPart.substring(1);
    }

    private void thisOnClickListener(){
        autoPlayImgView.setOnClickListener(this);
        settingsImgView.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.quiz_result_settings_img_view):
                startSettingsActivity();
                break;
            case (R.id.quiz_result_auto_play_img_view):
                Toast.makeText(this, "Auto Play Is On The Process..", Toast.LENGTH_SHORT).show();
                autoPlayWordDialog();
                break;
        }
    }
    private void autoPlayWordDialog() {
        AutoPlayDialogFragment playDialogFragment =
                AutoPlayDialogFragment.quizResultNewInstance(quizResultActivity, newList(), dbDialogInfoList());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("autoPlayAudio");
        if (prev != null) {
            ft.remove(prev);
        }
        playDialogFragment.show(ft, "autoPlayAudio");
    }

    private int[] dbDialogInfoList() {
        int[] list = new int[2];
        list[0] = dbNum;
        list[1] = unitNum;

        return list;
    }

    private ArrayList<WordModel> list() {
        ArrayList<WordModel> list = new ArrayList<>();
        if (currentItem() == 2) {
            list = wrongList;
        } else if (currentItem() == 3) {
            list = skippedList;
        }
        return list;
    }

    private ArrayList<WordModel> newList() {
        if (currentItem() == 2) {
            return wrongList;
        } else {
            return skippedList;
        }
    }


    private void startSettingsActivity() {
        Intent settingsIntent = new Intent(this, SettingsPreferencesActivity.class);
        startActivity(settingsIntent);
    }

    private int currentItem() {
        return qrViewPager.getCurrentItem();
    }
}
