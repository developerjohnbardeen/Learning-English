package com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.CreateTables.BluePrints.WordBluePrint;

import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;

public class CreateTablesBluePrint {

    public static final String WORD_ID = DB_NOTES.WORD_ID;
    public static final String WORD_IMG = DB_NOTES.WORD_IMG;
    public static final String WORD = DB_NOTES.WORD;
    public static final String PHONETIC_WORD = DB_NOTES.PHONETIC_WORD;
    public static final String TRANSLATE_WORD = DB_NOTES.TRANSLATE_WORD;
    public static final String DEFINITION_WORD = DB_NOTES.DEFINITION_WORD;
    public static final String DEFINITION_TRANSLATE_WORD = DB_NOTES.DEFINITION_TRANSLATE_WORD;
    public static final String EXAMPLE_WORD = DB_NOTES.EXAMPLE_WORD;
    public static final String EXAMPLE_TRANSLATE_WORD = DB_NOTES.EXAMPLE_TRANSLATE_WORD;
    public static final String EXTRA_NOTE = DB_NOTES.EXTRA_NOTE;
    public static final String HARD_FLAG = DB_NOTES.HARD_FLAG;
    public static final String EASY_FLAG = DB_NOTES.EASY_FLAG;
    public static final String WORD_START = DB_NOTES.WORD_START;
    public static final String WORD_END = DB_NOTES.WORD_END;
    public static final String DEF_START = DB_NOTES.DEF_START;
    public static final String DEF_END = DB_NOTES.DEF_END;
    public static final String EXMPL_START = DB_NOTES.EXMPL_START;
    public static final String EXMPL_END = DB_NOTES.EXMPL_END;
    public static final String AUDIO_WORD = DB_NOTES.AUDIO_WORD;
    public static final String BOOK_NUMBER = DB_NOTES.BOOK_NUMBER;
    public static final String UNIT_NUMBER = DB_NOTES.UNIT_NUMBER;

    public String CREATE_WORD_TABLE_(int tableNum){
        return "CREATE TABLE " + " UNIT_" + tableNum + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                WORD_START + " INTEGER, " +
                WORD_END + " INTEGER, " +
                DEF_START + " INTEGER, " +
                DEF_END + " INTEGER, " +
                EXMPL_START + " INTEGER, " +
                EXMPL_END + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT, " +
                EXTRA_NOTE + " TEXT);";

    }
}
