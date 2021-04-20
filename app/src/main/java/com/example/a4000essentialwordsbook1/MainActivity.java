package com.example.a4000essentialwordsbook1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String msg = "Android : ";
    private RecyclerView uRecyclerView;
    private MainThread mainThread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewsFinderById();

        mainThread = new MainThread();
        mainThread.execute();
    }





    private class MainThread extends AsyncTask<Void, Boolean, Void>{
        private ArrayList<UnitModel> unitList;

        @Override
        protected Void doInBackground(Void... voids) {
            unitDataReceiver();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            uRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
            UnitRecyclerView unitAdapter = new UnitRecyclerView(MainActivity.this, unitList);
            uRecyclerView.setAdapter(unitAdapter);

        }

        private void unitDataReceiver(){
            UnitSqliteOpenHelper uSQLiteDatabase = new UnitSqliteOpenHelper(MainActivity.this);

            SQLiteDatabase db = uSQLiteDatabase.getReadableDatabase();
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
        mainThread.cancel(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
        mainThread.cancel(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void viewsFinderById(){
        uRecyclerView = findViewById(R.id.main_recyclerview);
    }
}