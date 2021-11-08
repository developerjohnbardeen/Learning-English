package com.example.a4000essentialwordsbook1.SelectedBook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases.UnitDatabaseBookTwo;
import com.example.a4000essentialwordsbook1.MarkedWords.MarkedWordActivity;
import com.example.a4000essentialwordsbook1.Models.UnitModel;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.SearchWordsClasses.SearchWordsActivity;
import com.example.a4000essentialwordsbook1.Settings.SettingsPreferencesActivity;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.ExtraNotes;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SelectedBookActivity extends AppCompatActivity implements View.OnClickListener {

    private final String msg = "Android : ";
    private RecyclerView uRecyclerView;
    private RelativeLayout backBtnLayout;
    private ArrayList<UnitModel> unitList;
    private Handler handler;
    private final String sDbNumber = ExtraNotes.DB_NUMBER;
    private final String sUnitNumber = ExtraNotes.UNIT_NUMBER;
    private final String sWordId = ExtraNotes.WORD_ID;
    private ImageView searchIcon, reviewIcon, settingIcon;
    private TextView backBtnTxtView, bookTitle, bookNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_book);
        viewsFinderById();
        thread();
    }



    private void thread(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            int dbNum = intDatabaseNum();
            unitDataReceiver(dbNum);
            handler.post(() -> {
                uRecyclerView.setLayoutManager(new GridLayoutManager(SelectedBookActivity.this, 2));
                AdapterSelectedBook unitAdapter = new AdapterSelectedBook(SelectedBookActivity.this, unitList, dbNum);
                uRecyclerView.setAdapter(unitAdapter);
            });
        });
    }






    private void unitDataReceiver(int databaseNum){
        SQLiteDatabase db = database(databaseNum).getReadableDatabase();
        unitList = new ArrayList<>();

        Cursor uCursor = db.query(DB_NOTES.UNIT_TABLE,
                new String[]{DB_NOTES.UNIT_ID, DB_NOTES.UNIT_IMG, DB_NOTES.UNIT_TITLE, DB_NOTES.UNIT_AUDIO},
                null, null, null, null, null);

        if (uCursor != null && uCursor.getCount() != 0){
            while (uCursor.moveToNext()){
                UnitModel modelList = new UnitModel();

                int id = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.UNIT_ID));
                int img = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.UNIT_IMG));
                int audio = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.UNIT_AUDIO));
                String title = uCursor.getString(uCursor.getColumnIndex(DB_NOTES.UNIT_TITLE));

                modelList.setuId(id);
                modelList.setUnitImg(img);
                modelList.setUnitAudio(audio);
                modelList.setUnitTitle(title);

                unitList.add(modelList);
            }
        }
        assert uCursor != null;
        uCursor.close();
        db.close();

    }

    private SQLiteOpenHelper database(int databaseNum){
        if (databaseNum == 1){
            return new UnitDatabaseBookOne(this);
        }else if (databaseNum == 2){
            return new UnitDatabaseBookTwo(this);
        }else if (databaseNum == 3){
            return new UnitDatabaseBookThree(this);
        }else if (databaseNum == 4){
            return new UnitDatabaseBookFour(this);
        }else if (databaseNum == 5){
            return new UnitDatabaseBookFive(this);
        }else {
            return new UnitDatabaseBookSix(this);
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_selected_book, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case (R.id.hard_selected_book_item):
                Toast.makeText(this, "Hard Words Study Is Under Process", Toast.LENGTH_SHORT).show();
                return true;
            case (R.id.marked_selected_book_item):
                return true;
            case (R.id.settings_selected_book_item):
                settingStartActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/


    private void setBookNum(){
        String databaseNum = Integer.toString(intDatabaseNum());
        bookNum.setText(databaseNum);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.selected_book_search_launcher):
                bookSearchClickListener();
                break;
            case (R.id.selected_book_review_launcher):
                bookReviewClickListener();
                break;
            case (R.id.selected_book_tab_layout_bck_bttn_layout):
                onBackPressed();
                break;
            case (R.id.selected_book_settings_launcher):
                settingStartActivity();
                break;
        }
    }

    private void settingStartActivity(){
        Intent settingIntent = new Intent(this, SettingsPreferencesActivity.class);
        startActivity(settingIntent);
    }
    private void bookSearchClickListener(){
        Intent searchIntent = new Intent(this, SearchWordsActivity.class);
        startActivity(searchIntent);
    }
    private void bookReviewClickListener(){
        Intent reviewIntent = new Intent(this, MarkedWordActivity.class);
        startActivity(reviewIntent);
    }

    private int intDatabaseNum(){
        Intent intent = getIntent();
        return intent.getIntExtra(sDbNumber, 1);
    }

    private void viewsFinderById(){
        uRecyclerView = findViewById(R.id.selected_book_recyclerview);
        searchIcon = findViewById(R.id.selected_book_search_launcher);
        reviewIcon = findViewById(R.id.selected_book_review_launcher);
        settingIcon = findViewById(R.id.selected_book_settings_launcher);
        backBtnTxtView = findViewById(R.id.selected_book_tab_layout_txt_back_button);
        bookTitle = findViewById(R.id.selected_book_text_title);
        bookNum = findViewById(R.id.selected_book_text_view);
        backBtnLayout = findViewById(R.id.selected_book_tab_layout_bck_bttn_layout);
        Toolbar toolbar = findViewById(R.id.selected_book_toolbar);
        setSupportActionBar(toolbar);
        thisOnClickListener();
    }
    private void thisOnClickListener(){
        reviewIcon.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
        backBtnLayout.setOnClickListener(this);
        settingIcon.setOnClickListener(this);
        setBookNum();
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

}
