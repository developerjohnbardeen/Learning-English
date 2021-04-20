package com.example.a4000essentialwordsbook1.UpdateDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.WordDatabase.WordDatabaseOpenHelper;

public class UpdateBookWordDatabase {

    private final Context context;

    public UpdateBookWordDatabase(Context context){
        this.context = context;
    }

    public void bookDatabaseUpdate(String columnName, int columnId, int value){


        WordDatabaseOpenHelper markedDatabase = new WordDatabaseOpenHelper(context);
        SQLiteDatabase db = markedDatabase.getWritableDatabase();
        ContentValues markedFlagValue = new ContentValues();
        markedFlagValue.put(columnName, value);
        db.update(DB_NOTES.WORD_TABLE,
                markedFlagValue, DB_NOTES.WORD_ID + " = ? ", new String[]{Integer.toString(columnId)});
        db.close();

    }
}
