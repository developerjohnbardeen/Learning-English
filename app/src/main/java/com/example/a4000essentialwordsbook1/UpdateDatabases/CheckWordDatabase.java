package com.example.a4000essentialwordsbook1.UpdateDatabases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookTwo;

import java.util.ArrayList;

public class CheckWordDatabase {
    private final Context context;
    private final int dbNum;
    private ArrayList<Integer> markedIntValue;

    public CheckWordDatabase(Context context, int dbNum){
        this.context = context;
        this.dbNum = dbNum;
    }

    public void favoriteChecker(String table, String columnId, String itemChecked){
        markedIntValue = new ArrayList<>();
        SQLiteDatabase db = wordListDatabase(dbNum).getReadableDatabase();
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

    private SQLiteOpenHelper wordListDatabase(int databaseNum){
        if (databaseNum == 1){
            return new WordDatabaseBookOne(context);
        }else if (databaseNum == 2){
            return new WordDatabaseBookTwo(context);
        }else if (databaseNum == 3){
            return new WordDatabaseBookThree(context);
        }else if (databaseNum == 4){
            return new WordDatabaseBookFour(context);
        }else if (databaseNum == 5){
            return new WordDatabaseBookFive(context);
        }else {
            return new WordDatabaseBookSix(context);
        }
    }


    public ArrayList<Integer> getMarkedIntValue() {
        return markedIntValue;
    }
}

