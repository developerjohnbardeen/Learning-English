package com.example.a4000essentialwordsbook1.QuizFile.QuizRezult;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.CorrectModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.SkippedModel;
import com.example.a4000essentialwordsbook1.QuizFile.QuizModels.WrongModel;
import com.example.a4000essentialwordsbook1.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

public class QuizMainResultActivity extends AppCompatActivity {
    private Toolbar qrToolbar;
    private TabLayout qrTabLayout;
    private ViewPager qrViewPager;
    private RelativeLayout arBackBtnLayout;
    private TextView qrTitleTab;

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
        dataListReceiver();
        viewFounder();
        viewPagerAdapterSetter();
        toolbarBackArrow();
    }


    private void viewPagerAdapterSetter(){
        qrPagerAdapter = new QuizResultPagerAdapter(QuizMainResultActivity.this
                , correctList , wrongList, skippedList,
                getSupportFragmentManager(), 0);

        qrViewPager.setAnimationCacheEnabled(true);
        qrViewPager.setAdapter(qrPagerAdapter);
        qrTabLayout.setupWithViewPager(qrViewPager);
        tabLayoutIconSetter();
    }

    private void toolbarBackArrow(){
        setSupportActionBar(qrToolbar);
        arBackBtnLayout.setOnClickListener(v -> finish());
    }



    private void viewFounder(){
        qrToolbar = findViewById(R.id.quiz_result_toolbar);
        qrTabLayout = findViewById(R.id.quiz_result_tabs);
        qrViewPager = findViewById(R.id.quiz_result_viewPager);
        arBackBtnLayout = findViewById(R.id.quiz_result_tab_layout_bck_bttn_layout);
        qrTitleTab = findViewById(R.id.quiz_result_title_tab);
    }

    public void tabLayoutIconSetter(){
        Objects.requireNonNull(Objects.requireNonNull(qrTabLayout.getTabAt(0)).setIcon(R.drawable.ic_baseline_pie_chart_24));
        Objects.requireNonNull(Objects.requireNonNull(qrTabLayout.getTabAt(1)).setIcon(R.drawable.ic_baseline_correct_answer_24));
        Objects.requireNonNull(Objects.requireNonNull(qrTabLayout.getTabAt(2)).setIcon(R.drawable.ic_baseline_wrong_answer_24));
        Objects.requireNonNull(Objects.requireNonNull(qrTabLayout.getTabAt(3)).setIcon(R.drawable.ic_skip_icon));
    }


    private void dataListReceiver(){
        correctListDataReceiver();
        wrongListDataReceiver();
        skippedListDataReceiver();
    }

    private void correctListDataReceiver(){
        correctList = this.getIntent().getParcelableArrayListExtra("correctList");
    }
    private void wrongListDataReceiver(){
        wrongList = this.getIntent().getParcelableArrayListExtra("wrongList");
    }
    private void skippedListDataReceiver(){
        skippedList = this.getIntent().getParcelableArrayListExtra("skippedList");
    }

}
