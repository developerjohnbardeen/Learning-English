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



public class UpdateWordDatabase {
    private final Context context;
    private final int dbNum;
    private final int unitNum;

    public UpdateWordDatabase(Context context, int[] dbInfoList){
        this.context = context;
        //first index is dbNumber, second is unitNum
        this.dbNum = dbInfoList[0];
        this.unitNum = dbInfoList[1];
    }

    public void wordDatabaseUpdate(String columnName, int columnId, int valFlag){


        SQLiteDatabase db = wordListDatabase(dbNum).getWritableDatabase();
        ContentValues markedFlagValue = new ContentValues();
        markedFlagValue.put(columnName, valFlag);
        db.update(DB_NOTES.NEUTRAL_WORD_TABLE + unitNum, markedFlagValue,
                DB_NOTES.WORD_ID + " = ? ", new String[]{Integer.toString(columnId)});

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
