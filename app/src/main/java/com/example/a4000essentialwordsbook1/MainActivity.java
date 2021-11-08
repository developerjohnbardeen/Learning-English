package com.example.a4000essentialwordsbook1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.a4000essentialwordsbook1.MarkedWords.MarkedWordActivity;
import com.example.a4000essentialwordsbook1.NavigationClasses.SubNavigationDrawer.NavRecyclerView.SubNavModel;
import com.example.a4000essentialwordsbook1.SearchWordsClasses.SearchWordsActivity;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordSlideCardViewActivity;
import com.example.a4000essentialwordsbook1.Settings.SettingsPreferencesActivity;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.PreferencesNotes.ResumeStudyPreferences;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.PreferencesNotes.SharedPreferencesNotes;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView main_nav_recyclerview;
    private DrawerLayout drawerLayout;
    private NavigationView mainNavigationView;
    private SwitchCompat darkModeSwitch;
    private Toolbar toolbar;
    private ImageView imageView;
    private TextView searchTextview, resumeStudyTextview;
    private TextView rUnitTxt, rWordTxt, rBookTxt;
    private CardView resumeStudyCardView;
    private final String resumeStudyPreference = SharedPreferencesNotes.resumeStudyCardViewVisibilityPreferences;
    private final String visibilityFlag = SharedPreferencesNotes.visibilityFlag;
    private final String darkModePreferences = SharedPreferencesNotes.DarkModePreferences;
    private final String isDarkMode = SharedPreferencesNotes.isDarkMode;
    private final String strDbNumber = ExtraNotes.DB_NUMBER;
    private final String strUnitNumber = ExtraNotes.UNIT_NUMBER;
    private final String strWordId = ExtraNotes.WORD_ID;
    private int wordId, unitNum, dbNum;

    //resume preferences
    private SharedPreferences resumeSharedPreferences;
    private final String resumePreferencesName = ResumeStudyPreferences.RESUMEPREFERENCES;
    private String bookStr = ResumeStudyPreferences.BOOKNUMBER;
    private String unitStr = ResumeStudyPreferences.UNITNUMBER;
    private String resumeWordStr = ResumeStudyPreferences.RESUMEWORD;
    private String wordPositionStr = ResumeStudyPreferences.WORDPOSITION;
    private int pBookNum, pUnitNum;
    private String word2Resume;



    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        themeMode();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allFunctions();
        

        String deLang = Locale.getDefault().getDisplayLanguage();
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        //Toast.makeText(this, "default night Mode : " + nightMode, Toast.LENGTH_SHORT).show();
    }



    private void allFunctions(){
        findViewsById();
        preInitialization();
        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->{
            toggleDrawer();
            darkModeSwitchListener();
            initializeNavigationView();
        });
    }



    private void themeMode(){
        if (isDarkMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


    private void preInitialization(){
        ExecutorService thread = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        thread.execute(() ->{

            ArrayList<SubNavModel> list = new ArrayList<>();
            list.add(new SubNavModel(R.drawable.book_cover_1, this.getString(R.string.name_book_1)));
            list.add(new SubNavModel(R.drawable.book_cover_2, this.getString(R.string.name_book_2)));
            list.add(new SubNavModel(R.drawable.book_cover_3, this.getString(R.string.name_book_3)));
            list.add(new SubNavModel(R.drawable.book_cover_4, this.getString(R.string.name_book_4)));
            list.add(new SubNavModel(R.drawable.book_cover_5, this.getString(R.string.name_book_5)));
            list.add(new SubNavModel(R.drawable.book_cover_6, this.getString(R.string.name_book_6)));
            main_nav_recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
            MainRecyclerViewAdapter recyclerviewAdapter = new MainRecyclerViewAdapter(this, list);
            handler.post(() ->{
                main_nav_recyclerview.setAdapter(recyclerviewAdapter);
            });

        });
    }

    private void toggleDrawer(){
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.main_search_text_view):
                Intent intent = new Intent(this, SearchWordsActivity.class);
                startActivity(intent);
                break;
            case (R.id.resume_study_card_view):
                startResumeStudyActivity();
                break;
        }
    }

    private void startResumeStudyActivity(){
        Intent resumeStudyIntent = new Intent(this, WordSlideCardViewActivity.class);
        resumeStudyIntent.putExtra(strDbNumber, dbNum);
        resumeStudyIntent.putExtra(strUnitNumber, unitNum);
        resumeStudyIntent.putExtra(strWordId, wordId);
        startActivity(resumeStudyIntent);
    }


    private void donotknowFunction(){

        SharedPreferences sharedPreferences = getSharedPreferences(resumeStudyPreference, MODE_PRIVATE);
        SharedPreferences.Editor visibilityFlagEdit = sharedPreferences.edit();
        visibilityFlagEdit.putBoolean(visibilityFlag, true);
        visibilityFlagEdit.apply();
    }

    private void darkModeSharedPreferences(boolean flag){
        SharedPreferences darkModeSetting = getSharedPreferences(darkModePreferences, MODE_PRIVATE);
        SharedPreferences.Editor darkModeEdit = darkModeSetting.edit();
        darkModeEdit.putBoolean(isDarkMode, flag);
        darkModeEdit.apply();
    }

    private boolean isDarkMode(){
        SharedPreferences darkModeSetting = getSharedPreferences(darkModePreferences, MODE_PRIVATE);
        return darkModeSetting.getBoolean(isDarkMode, false);
    }

    private boolean isVisible(){
        SharedPreferences sharedFlag = getSharedPreferences(resumeStudyPreference, Context.MODE_PRIVATE);
        return sharedFlag.getBoolean(visibilityFlag, false);
    }


    private void navDarkModeSwitch(){
        mainNavigationView.getMenu().findItem(R.id.nav_dark_mode_switch).setActionView(new SwitchCompat(this));
        ((SwitchCompat) mainNavigationView.getMenu().findItem(R.id.nav_dark_mode_switch).getActionView()).setChecked(isDarkMode());
        darkModeSwitch = (SwitchCompat) mainNavigationView.getMenu().findItem(R.id.nav_dark_mode_switch).getActionView();
    }

    private void darkModeSwitchListener(){
        Intent darkModeIntent = new Intent(this, MainActivity.class);

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            darkModeSharedPreferences(isChecked);
            startActivity(darkModeIntent);
            finish();
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setEnabled(true);

        switch (item.getItemId()){
            case (R.id.nav_buy_app):
                Toast.makeText(this, "Buy this App", Toast.LENGTH_SHORT).show();
                closeDrawer();
                break;
            case (R.id.saved_words):
                Toast.makeText(this, "saved Words", Toast.LENGTH_SHORT).show();
                closeDrawer();
                break;
            case (R.id.statics):
                Toast.makeText(this, "Study Status", Toast.LENGTH_SHORT).show();
                closeDrawer();
                break;
            case (R.id.nav_contact_us):
                Toast.makeText(this, "Contact us", Toast.LENGTH_SHORT).show();
                closeDrawer();
                break;
            case (R.id.nav_invite_friends):
                Toast.makeText(this, "Invite Friends", Toast.LENGTH_SHORT).show();
                closeDrawer();
                break;
            case (R.id.nav_review_words):
                strtReviewWordsActivity();
                closeDrawer();
                break;
            case (R.id.nav_appendix):
                Toast.makeText(this, "Appendix", Toast.LENGTH_SHORT).show();
                deviceDefaultNightMode();
                closeDrawer();
                break;
            case (R.id.nav_settings):
                startSettingIntent();
                closeDrawer();
                break;
            case (R.id.nav_dark_mode_switch):
                Toast.makeText(this, "DarkMode", Toast.LENGTH_SHORT).show();
                closeDrawer();
                break;
            default:
                closeDrawer();
                break;
        }
        return true;
    }


    private void startSettingIntent(){
        Intent settingsIntent = new Intent(this, SettingsPreferencesActivity.class);
        startActivity(settingsIntent);
    }

    private void strtReviewWordsActivity(){
        Intent reviewIntent = new Intent(this, MarkedWordActivity.class);
        startActivity(reviewIntent);
    }

    private void deviceDefaultNightMode(){
        int nighModeFlag = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nighModeFlag){
            case Configuration.UI_MODE_NIGHT_YES:
                Toast.makeText(this, "Night Mode Is On", Toast.LENGTH_SHORT).show();
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                Toast.makeText(this, "Night Mode Is OFF", Toast.LENGTH_SHORT).show();
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                Toast.makeText(this, "Can't Detect Device NightMode", Toast.LENGTH_SHORT).show();
                break;
        }
    }




    private void initializeNavigationView(){
        mainNavigationView.setNavigationItemSelectedListener(this);
    }
    private void closeDrawer(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        retractResumeStudyPreferences();
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void retractResumeStudyPreferences(){
        resumeSharedPreferences = getSharedPreferences(resumePreferencesName, Context.MODE_PRIVATE);
        word2Resume = resumeSharedPreferences.getString(resumeWordStr, "لغتی برای ادامه یادگیری وجود ندارد");
        dbNum = resumeSharedPreferences.getInt(bookStr, 0);
        unitNum = resumeSharedPreferences.getInt(unitStr, 0);
        wordId = resumeSharedPreferences.getInt(wordPositionStr, 0);
        resumeCardViewComponentValueSetter(dbNum, unitNum, word2Resume);
        resumeStudyCardViewVisibility();
    }

    private void resumeCardViewComponentValueSetter(int strDbNum, int strUnitNum, String word){
        String dbNumValue = Integer.toString(strDbNum);
        String unitNumValue = Integer.toString(strUnitNum);
        rWordTxt.setText(word);
        rBookTxt.setText(dbNumValue);
        rUnitTxt.setText(unitNumValue);
    }

    private void resumeStudyCardViewVisibility(){
        if (dbNum == 0){
            resumeStudyCardView.setVisibility(View.GONE);
        }else {
            resumeStudyCardView.setVisibility(View.VISIBLE);
        }
    }

    private void findViewsById(){
        main_nav_recyclerview = findViewById(R.id.main_nav_recyclerview);
        drawerLayout = findViewById(R.id.main_nav_drawer_layout);
        mainNavigationView = findViewById(R.id.main_nav_navigation_view);
        navDarkModeSwitch();
        toolbar = findViewById(R.id.main_nav_second_toolbar);
        imageView = findViewById(R.id.main_image_view_icon);
        resumeStudyCardView = findViewById(R.id.resume_study_card_view);
        searchTextview = findViewById(R.id.main_search_text_view);
        resumeStudyTextview = findViewById(R.id.main_resume_study_text_view);

        rBookTxt = findViewById(R.id.resume_book_num_textview);
        rWordTxt = findViewById(R.id.resume_word_text_View);
        rUnitTxt = findViewById(R.id.resume_unit_num_text_view);

        onClickListenerThis();
    }

    private void onClickListenerThis(){
        imageView.setOnClickListener(this);
        searchTextview.setOnClickListener(this);
        resumeStudyCardView.setOnClickListener(this);
    }
}