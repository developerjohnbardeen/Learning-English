package com.example.a4000essentialwordsbook1.UpdateDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookTwo;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;

public class UpdateBookWordDatabase {

    private final Context context;
    private int dbNum = 0;
    private int unitNum = 0;

    public UpdateBookWordDatabase(Context context){
        this.context = context;
    }

    public void bookDatabaseUpdate(String columnName, int columnId, int value){


        SQLiteDatabase db = wordListDatabase(0).getWritableDatabase();
        ContentValues markedFlagValue = new ContentValues();
        markedFlagValue.put(columnName, value);
        db.update(DB_NOTES.WORD_TABLE,
                markedFlagValue, DB_NOTES.WORD_ID + " = ? ", new String[]{Integer.toString(columnId)});
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
}
