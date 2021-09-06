package com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.CreateTables.UnitBluePrint;

import android.database.sqlite.SQLiteDatabase;

import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;

public class CreateUnitBluePrint {

    private static final int UNIT_DB_VERSION = 1;
    private static final String UNIT_DATABASE = "UNIT_DATABASE";
    private static final String UNIT_TABLE = DB_NOTES.UNIT_TABLE;
    private static final String UNIT_ID = DB_NOTES.UNIT_ID; //use it is UNIT_NUMBER as well
    private static final String UNIT_IMG = DB_NOTES.UNIT_IMG;
    private static final String UNIT_TITLE = DB_NOTES.UNIT_TITLE;
    private static final String UNIT_AUDIO = DB_NOTES.UNIT_AUDIO;
    private static final String UNIT_COMPLETE_WORD_AUDIO = DB_NOTES.UNIT_COMPLETE_WORD_AUDIO;
    private static final String UNIT_STORY = DB_NOTES.UNIT_STORY;



    private String SQLITE_UNIT_TABLE(int tableNum) {
         return "CREATE TABLE " + UNIT_TABLE + tableNum + " (" +
                UNIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UNIT_IMG + " INTEGER, " +
                UNIT_AUDIO + " INTEGER, " +
                UNIT_COMPLETE_WORD_AUDIO + " INTEGER, " +
                UNIT_STORY + " TEXT, " +
                UNIT_TITLE + " TEXT);";
    }
}
