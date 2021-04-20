package com.example.a4000essentialwordsbook1.UpdateDatabases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.WordDatabase.WordDatabaseOpenHelper;
import com.google.android.exoplayer2.util.NalUnitUtil;

import java.util.ArrayList;

public class CheckWordDatabase {
    private final Context context;
    private ArrayList<Integer> markedIntValue;

    public CheckWordDatabase(Context context){
        this.context = context;
    }

    public void favoriteChecker(String table, String columnId, String itemChecked){
        WordDatabaseOpenHelper flagChecker = new WordDatabaseOpenHelper(context);
        markedIntValue = new ArrayList<>();
        SQLiteDatabase db = flagChecker.getReadableDatabase();
        Cursor cursor = db.query(table,
                new String[]{columnId, itemChecked},
                null, null, null, null, null);

        if (cursor != null  && cursor.getCount() != 0){
            while (cursor.moveToNext()){
                markedIntValue.add(cursor.getInt(cursor.getColumnIndex(itemChecked)));
            }
        }

        assert cursor != null;
        cursor.close();
        db.close();
    }


    public ArrayList<Integer> getMarkedIntValue() {
        return markedIntValue;
    }
}

