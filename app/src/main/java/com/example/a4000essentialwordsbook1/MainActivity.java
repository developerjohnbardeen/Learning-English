package com.example.a4000essentialwordsbook1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.a4000essentialwordsbook1.MarkedWords.MarkedWordActivity;
import com.example.a4000essentialwordsbook1.NavigationClasses.SubNavigationDrawer.NavRecyclerView.SubNavModel;
import com.example.a4000essentialwordsbook1.SearchWordsClasses.SearchWordsActivity;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.DetailedWord.WordSlideCardViewActivity;
import com.example.a4000essentialwordsbook1.Settings.SettingsPreferencesActivity;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.PreferencesNotes.ResumeStudyPreferences;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.PreferencesNotes.SharedPreferencesNotes;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.SettingsPreferencesNotes.SettingsPreferencesNotes;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*final File imgMainPath = new File(appPath, "Image Files");
    final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
    final File unitImgPath = new File(imgMainPath, File.separator + "Word Images");
    final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + dbNum);
    final File unitImgBookPath = new File(unitImgPath, File.separator + "Book_" + dbNum);

    final File audioMainPath = new File(appPath, "Audio Files");
    final File audioWordPath = new File(audioMainPath, File.separator + "Word Audios");
    final File audioUnitPath = new File(audioMainPath, File.separator + "Unit Audios");
    final File audioWordBookPath = new File(audioWordPath, File.separator + "Book_" + dbNum);
    final File audioUnitBookPath = new File(audioUnitPath, File.separator + "Book_" + dbNum);*/


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
    private final String strDbNumber = ExtraNotes.DB_NUMBER;
    private final String strUnitNumber = ExtraNotes.UNIT_NUMBER;
    private final String strWordId = ExtraNotes.WORD_ID;
    private int wordId, unitNum, dbNum;

    //resume preferences
    private SharedPreferences resumeSharedPreferences;
    private final String resumePreferencesName = ResumeStudyPreferences.RESUMEPREFERENCES;
    private final String bookStr = ResumeStudyPreferences.BOOKNUMBER;
    private final String unitStr = ResumeStudyPreferences.UNITNUMBER;
    private final String resumeWordStr = ResumeStudyPreferences.RESUMEWORD;
    private final String wordPositionStr = ResumeStudyPreferences.WORDPOSITION;
    private int pBookNum, pUnitNum;
    private String word2Resume;
    private SharedPreferences autoNighModePreference;
    private final String autoNightPreferenceName = SettingsPreferencesNotes.SETTINGS_AUTO_NIGH_PREFERENCES;
    private final String autoNightModeKey = SettingsPreferencesNotes.AUTO_NIGHT_MODE_KEY;
    private final String lightModeKey = SettingsPreferencesNotes.LIGHT_MODE_KEY;
    private final String nightModeKey = SettingsPreferencesNotes.NIGHT_MODE_KEY;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        themeMode();
        allFunctions();
        permissionRequester();
        toastIdAddress();

    }

    private void toastIdAddress() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        Toast.makeText(this, "" + ipAddress, Toast.LENGTH_LONG).show();
    }


    private final String[] uriList = new String[20];
    private final String[] audioUrlList = new String[30];


    private void downloadFunctions() {
        haveStoragePermission();
        ExecutorService thread = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        thread.execute(() ->{
            if (hasInternetAccess() && hasReadPermission() && hasReadPermission()) {
                WordListDataReceiver();
                wordAudioReceiver(1);
                imageDownloader(uriList);
                audioDownloader(audioUrlList);
            }
        });
    }


    @SuppressLint("Range")
    private void wordAudioReceiver(int dbNumber){
        SQLiteDatabase db = unitListDatabase(dbNumber).getReadableDatabase();
        int index = 0;

        Cursor awCursor = db.query(DB_NOTES.UNIT_TABLE,
                new String[]{DB_NOTES.UNIT_COMPLETE_WORD_AUDIO},
                null, null,
                null, null, null);

        if (awCursor != null && awCursor.getCount() != 0){
            while (awCursor.moveToNext()){
                audioUrlList[index] = awCursor.getString(awCursor.getColumnIndex(DB_NOTES.UNIT_COMPLETE_WORD_AUDIO));
                index++;
            }
        }

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


    @SuppressLint("Range")
    private void imageDownloader(String[] imgUrlList){
        final String appPath = MainActivity.this.getApplicationInfo().dataDir;

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Word Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + 1);
        final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + 1);


        for (int index = 0 ; index < 20 ; index++) {
            Uri downloadUri = Uri.parse(imgUrlList[index]);

            final File secondSubFile = new File(wordUnitImgBookPath, File.separator + "." + new File(imgUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(appPath) , secondSubFile.toString());


            final boolean imgExists = file.exists();
            if (!imgExists) {

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(imgUrlList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(appPath, secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                dm.enqueue(request);

                final long downloadId = dm.enqueue(request);
                ExecutorService thread = Executors.newSingleThreadExecutor();
                thread.execute(() ->{

                    while (true){
                        DownloadManager.Query dq = new DownloadManager.Query();
                        dq.setFilterById(downloadId);

                        Cursor cursor = dm.query(dq);
                        cursor.moveToFirst();

                        int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));

                        if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){

                            break;

                        } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                            break;

                        }else if (downloadStatus == DownloadManager.STATUS_RUNNING){

                        }else if (downloadStatus == DownloadManager.STATUS_PAUSED){

                        }

                        runOnUiThread(() ->{});
                        cursor.close();

                    }
                });

            }
        }
    }
    @SuppressLint("Range")
    private void storyImageDownloader(String[] storyImgList){
        final String appPath = MainActivity.this.getApplicationInfo().dataDir;

        final File imgMainPath = new File("Image Files");
        final File wordImgPath = new File(imgMainPath, File.separator + "Story Images");
        final File wordImgBookPath = new File(wordImgPath, File.separator + "Book_" + 1);
        final File wordUnitImgBookPath = new File(wordImgBookPath, File.separator + "Unit_" + 1);


        for (int index = 0 ; index < 20 ; index++) {
            Uri downloadUri = Uri.parse(storyImgList[index]);

            final File secondSubFile = new File(wordUnitImgBookPath, File.separator + "." + new File(storyImgList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(appPath) , secondSubFile.toString());


            final boolean imgExists = file.exists();
            if (!imgExists) {

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(storyImgList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(appPath, secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                dm.enqueue(request);

                final long downloadId = dm.enqueue(request);
                ExecutorService thread = Executors.newSingleThreadExecutor();
                thread.execute(() ->{

                    while (true){
                        DownloadManager.Query dq = new DownloadManager.Query();
                        dq.setFilterById(downloadId);

                        Cursor cursor = dm.query(dq);
                        cursor.moveToFirst();

                        int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));

                        if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL){

                            break;

                        } else if (downloadStatus == DownloadManager.STATUS_FAILED) {
                            break;

                        }else if (downloadStatus == DownloadManager.STATUS_RUNNING){

                        }else if (downloadStatus == DownloadManager.STATUS_PAUSED){

                        }

                        runOnUiThread(() ->{});
                        cursor.close();

                    }
                });

            }
        }
    }

    private void audioDownloader(String[] audioUrlList){
        final String appPath = MainActivity.this.getApplicationInfo().dataDir;


        final File audioMainPath = new File("Audio Files");
        final File audioUnitPath = new File(audioMainPath, File.separator + "Word Audios");
        final File audioWordBookPath = new File(audioUnitPath, File.separator + "Book_" + 1);

        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(audioWordBookPath, File.separator + "." + new File(audioUrlList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(appPath) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                Uri downloadUri = Uri.parse(audioUrlList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(audioUrlList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(appPath, secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                dm.enqueue(request);
            }
        }
    }
    private void unitAudioDownloader(String[] unitAudioList){
        final String appPath = MainActivity.this.getApplicationInfo().dataDir;


        final File audioMainPath = new File("Audio Files");
        final File audioUnitPath = new File(audioMainPath, File.separator + "Unit Audios");
        final File audioUnitBookPath = new File(audioUnitPath, File.separator + "Book_" + 1);

        for (int index = 0 ; index < 30 ; index++) {
            final File secondSubFile = new File(audioUnitBookPath, File.separator + "." + new File(unitAudioList[index]).getName());
            final File file = new File(Environment.getExternalStoragePublicDirectory(appPath) , secondSubFile.toString());
            final boolean imgExists = file.exists();
            if (!imgExists) {
                Uri downloadUri = Uri.parse(unitAudioList[index]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                File path = new File(unitAudioList[index]);
                String imageName = path.getName();
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle(imageName)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(appPath, secondSubFile.toString());


                DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                dm.enqueue(request);
            }
        }
    }

    @SuppressLint("Range")
    private void WordListDataReceiver(){
        unitNum = (unitNum == 0) ? 1 : unitNum;
        int index = 0;

        SQLiteDatabase db = wordListDatabase(1).getReadableDatabase();


        Cursor cursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + 1,
                new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD_IMG, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD ,DB_NOTES.TRANSLATE_WORD ,
                        DB_NOTES.DEFINITION_WORD, DB_NOTES.EXAMPLE_WORD, DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.AUDIO_WORD},
                null, null, null, null, null);


        if (cursor != null && cursor.getCount() != 0){
            while (cursor.moveToNext()){


                final int id = cursor.getInt(cursor.getColumnIndex(DB_NOTES.WORD_ID));
                final String wordImg = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD_IMG));
                final String word = cursor.getString(cursor.getColumnIndex(DB_NOTES.WORD));
                final String phonetic = cursor.getString(cursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));
                final String translate_word = cursor.getString(cursor.getColumnIndex(DB_NOTES.TRANSLATE_WORD));
                final String definition = cursor.getString(cursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));
                final String example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));
                final String translate_example = cursor.getString(cursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));


                uriList[index] = wordImg;
                index++;
            }
        }
        assert cursor != null;
        cursor.close();
        db.close();
    }
    private SQLiteOpenHelper wordListDatabase(int databaseNum){
        if (databaseNum == 1){
            return new WordDatabaseBookOne(MainActivity.this);
        }else if (databaseNum == 2){
            return new WordDatabaseBookTwo(MainActivity.this);
        }else if (databaseNum == 3){
            return new WordDatabaseBookThree(MainActivity.this);
        }else if (databaseNum == 4){
            return new WordDatabaseBookFour(MainActivity.this);
        }else if (databaseNum == 5){
            return new WordDatabaseBookFive(MainActivity.this);
        }else {
            return new WordDatabaseBookSix(MainActivity.this);
        }
    }
    public  void haveStoragePermission() {
        final String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WAKE_LOCK};
        ActivityCompat.requestPermissions(this,permissions, 1);
    }

    private void permissionRequester() {
        if (!hasInternetAccess()) {
            ActivityCompat.requestPermissions(this, permissions(), 1);
        }
        if (!hasWifiAccess()) {
            ActivityCompat.requestPermissions(this, permissions(), 1);
        }
        if (!hasWritePermission()) {
            ActivityCompat.requestPermissions(this, permissions(), 1);
        }
        if (!hasReadPermission()) {
            ActivityCompat.requestPermissions(this, permissions(), 1);
        }
    }

    private boolean hasReadPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasWritePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasInternetAccess() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasWifiAccess() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    private String[] permissions() {
        return new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };
    }





    private void themeMode(){
        if (isDarkMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
    private boolean isDarkMode(){
        autoNighModePreference = getSharedPreferences(autoNightPreferenceName, MODE_PRIVATE);
        return autoNighModePreference.getBoolean(nightModeKey, false);
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


    private void darkModeSharedPreferences(boolean flag){
        autoNighModePreference = getSharedPreferences(autoNightPreferenceName, MODE_PRIVATE);
        SharedPreferences.Editor darkModeEdit = autoNighModePreference.edit();
        darkModeEdit.putBoolean(nightModeKey, flag);
        darkModeEdit.putBoolean(lightModeKey, !flag);
        darkModeEdit.apply();
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
                inviteFriendShareContent();
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

    private void inviteFriendShareContent(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "برنامه را با دوستان خود به اشتراک بگذارید");
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, "معرفی برنامه به دوستان..");
        startActivity(shareIntent);
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

    @Override
    protected void onResume() {
        super.onResume();
    }
}