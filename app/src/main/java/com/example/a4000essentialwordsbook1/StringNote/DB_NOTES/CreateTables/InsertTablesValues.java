package com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.CreateTables;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;

public class InsertTablesValues {

    public static final String WORD_IMG = DB_NOTES.WORD_IMG;
    public static final String WORD = DB_NOTES.WORD;
    public static final String PHONETIC_WORD = DB_NOTES.PHONETIC_WORD;
    public static final String TRANSLATE_WORD = DB_NOTES.TRANSLATE_WORD;
    public static final String DEFINITION_WORD = DB_NOTES.DEFINITION_WORD;
    public static final String DEFINITION_TRANSLATE_WORD = DB_NOTES.DEFINITION_TRANSLATE_WORD;
    public static final String EXAMPLE_WORD = DB_NOTES.EXAMPLE_WORD;
    public static final String EXAMPLE_TRANSLATE_WORD = DB_NOTES.EXAMPLE_TRANSLATE_WORD;
    public static final String HARD_FLAG = DB_NOTES.HARD_FLAG;
    public static final String EASY_FLAG = DB_NOTES.EASY_FLAG;
    public static final String AUDIO_WORD = DB_NOTES.AUDIO_WORD;
    public static final String BOOK_NUMBER = DB_NOTES.BOOK_NUMBER;
    public static final String UNIT_NUMBER = DB_NOTES.UNIT_NUMBER;
    public static final String WORD_START = DB_NOTES.WORD_START;
    public static final String WORD_END = DB_NOTES.WORD_END;
    public static final String DEF_START = DB_NOTES.DEF_START;
    public static final String DEF_END = DB_NOTES.DEF_END;
    public static final String EXMPL_START = DB_NOTES.EXMPL_START;
    public static final String EXMPL_END = DB_NOTES.EXMPL_END;
    public static final String EXTRA_NOTE = DB_NOTES.EXTRA_NOTE;


    public static final String WORD_TABLE_1 = DB_NOTES.WORD_TABLE_1;
    public static final String WORD_TABLE_2 = DB_NOTES.WORD_TABLE_2;
    public static final String WORD_TABLE_3 = DB_NOTES.WORD_TABLE_3;
    public static final String WORD_TABLE_4 = DB_NOTES.WORD_TABLE_4;
    public static final String WORD_TABLE_5 = DB_NOTES.WORD_TABLE_5;
    public static final String WORD_TABLE_6 = DB_NOTES.WORD_TABLE_6;
    public static final String WORD_TABLE_7 = DB_NOTES.WORD_TABLE_7;
    public static final String WORD_TABLE_8 = DB_NOTES.WORD_TABLE_8;
    public static final String WORD_TABLE_9 = DB_NOTES.WORD_TABLE_9;
    public static final String WORD_TABLE_10 = DB_NOTES.WORD_TABLE_10;
    public static final String WORD_TABLE_11 = DB_NOTES.WORD_TABLE_11;
    public static final String WORD_TABLE_12 = DB_NOTES.WORD_TABLE_12;
    public static final String WORD_TABLE_13 = DB_NOTES.WORD_TABLE_13;
    public static final String WORD_TABLE_14 = DB_NOTES.WORD_TABLE_14;
    public static final String WORD_TABLE_15 = DB_NOTES.WORD_TABLE_15;
    public static final String WORD_TABLE_16 = DB_NOTES.WORD_TABLE_16;
    public static final String WORD_TABLE_17 = DB_NOTES.WORD_TABLE_17;
    public static final String WORD_TABLE_18 = DB_NOTES.WORD_TABLE_18;
    public static final String WORD_TABLE_19 = DB_NOTES.WORD_TABLE_19;
    public static final String WORD_TABLE_20 = DB_NOTES.WORD_TABLE_20;
    public static final String WORD_TABLE_21 = DB_NOTES.WORD_TABLE_21;
    public static final String WORD_TABLE_22 = DB_NOTES.WORD_TABLE_22;
    public static final String WORD_TABLE_23 = DB_NOTES.WORD_TABLE_23;
    public static final String WORD_TABLE_24 = DB_NOTES.WORD_TABLE_24;
    public static final String WORD_TABLE_25 = DB_NOTES.WORD_TABLE_25;
    public static final String WORD_TABLE_26 = DB_NOTES.WORD_TABLE_26;
    public static final String WORD_TABLE_27 = DB_NOTES.WORD_TABLE_27;
    public static final String WORD_TABLE_28 = DB_NOTES.WORD_TABLE_28;
    public static final String WORD_TABLE_29 = DB_NOTES.WORD_TABLE_29;
    public static final String WORD_TABLE_30 = DB_NOTES.WORD_TABLE_30;

    public void insertAllWordTables_1(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                      int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_1, null, unitOneValue);
    }


    public void insertAllWordTables_2(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                      int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                      String word, String phonetic, String wordTranslate,
                                      String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitTwoValue = new ContentValues();
        unitTwoValue.put(WORD_IMG, imgWord);
        unitTwoValue.put(HARD_FLAG, hardFlag);
        unitTwoValue.put(EASY_FLAG, easyFlag);
        unitTwoValue.put(BOOK_NUMBER, bookNum);
        unitTwoValue.put(UNIT_NUMBER, unitNum);
        unitTwoValue.put(WORD_START, wrdStart);
        unitTwoValue.put(WORD_END, wrdEnd);
        unitTwoValue.put(DEF_START, defStart);
        unitTwoValue.put(DEF_END, defEnd);
        unitTwoValue.put(EXMPL_START, exmplStart);
        unitTwoValue.put(EXMPL_END, exmplEndl);
        unitTwoValue.put(WORD, word);
        unitTwoValue.put(PHONETIC_WORD, phonetic);
        unitTwoValue.put(TRANSLATE_WORD, wordTranslate);
        unitTwoValue.put(DEFINITION_WORD, definition);
        unitTwoValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitTwoValue.put(EXAMPLE_WORD, example);
        unitTwoValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitTwoValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_2, null, unitTwoValue);
    }

    public void insertAllWordTables_3(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                      int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                      String word, String phonetic, String wordTranslate,
                                      String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_3, null, unitOneValue);
    }

    public void insertAllWordTables_4(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                      int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                      String word, String phonetic, String wordTranslate,
                                      String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_4, null, unitOneValue);
    }

    public void insertAllWordTables_5(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                      int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                      String word, String phonetic, String wordTranslate,
                                      String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_5, null, unitOneValue);
    }

    public void insertAllWordTables_6(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                      int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                      String word, String phonetic, String wordTranslate,
                                      String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_6, null, unitOneValue);
    }

    public void insertAllWordTables_7(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                      int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                      String word, String phonetic, String wordTranslate,
                                      String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_7, null, unitOneValue);
    }

    public void insertAllWordTables_8(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                      int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                      String word, String phonetic, String wordTranslate,
                                      String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_8, null, unitOneValue);
    }

    public void insertAllWordTables_9(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                      int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                      String word, String phonetic, String wordTranslate,
                                      String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_9, null, unitOneValue);
    }

    public void insertAllWordTables_10(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_10, null, unitOneValue);
    }

    public void insertAllWordTables_11(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_11, null, unitOneValue);
    }

    public void insertAllWordTables_12(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_12, null, unitOneValue);
    }

    public void insertAllWordTables_13(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_13, null, unitOneValue);
    }

    public void insertAllWordTables_14(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_14, null, unitOneValue);
    }

    public void insertAllWordTables_15(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_15, null, unitOneValue);
    }

    public void insertAllWordTables_16(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_16, null, unitOneValue);
    }

    public void insertAllWordTables_17(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);

        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_17, null, unitOneValue);
    }

    public void insertAllWordTables_18(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_18, null, unitOneValue);
    }

    public void insertAllWordTables_19(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_19, null, unitOneValue);
    }

    public void insertAllWordTables_20(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_20, null, unitOneValue);
    }

    public void insertAllWordTables_21(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_21, null, unitOneValue);
    }

    public void insertAllWordTables_22(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_22, null, unitOneValue);
    }

    public void insertAllWordTables_23(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_23, null, unitOneValue);
    }

    public void insertAllWordTables_24(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_24, null, unitOneValue);
    }

    public void insertAllWordTables_25(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_25, null, unitOneValue);
    }

    public void insertAllWordTables_26(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_26, null, unitOneValue);
    }

    public void insertAllWordTables_27(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_27, null, unitOneValue);
    }

    public void insertAllWordTables_28(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_28, null, unitOneValue);
    }

    public void insertAllWordTables_29(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD_START, wrdStart);
        unitOneValue.put(WORD_END, wrdEnd);
        unitOneValue.put(DEF_START, defStart);
        unitOneValue.put(DEF_END, defEnd);
        unitOneValue.put(EXMPL_START, exmplStart);
        unitOneValue.put(EXMPL_END, exmplEndl);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitOneValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_29, null, unitOneValue);
    }

    public void insertAllWordTables_30(SQLiteDatabase db, String imgWord, int hardFlag, int easyFlag, int bookNum, int unitNum,
                                       int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEndl,
                                       String word, String phonetic, String wordTranslate,
                                       String definition, String definitionTranslate, String example, String exmplTranslate, String extraNote) {

        ContentValues unitThirtyValue = new ContentValues();
        unitThirtyValue.put(WORD_IMG, imgWord);
        unitThirtyValue.put(HARD_FLAG, hardFlag);
        unitThirtyValue.put(EASY_FLAG, easyFlag);
        unitThirtyValue.put(BOOK_NUMBER, bookNum);
        unitThirtyValue.put(UNIT_NUMBER, unitNum);
        unitThirtyValue.put(WORD_START, wrdStart);
        unitThirtyValue.put(WORD_END, wrdEnd);
        unitThirtyValue.put(DEF_START, defStart);
        unitThirtyValue.put(DEF_END, defEnd);
        unitThirtyValue.put(EXMPL_START, exmplStart);
        unitThirtyValue.put(EXMPL_END, exmplEndl);
        unitThirtyValue.put(WORD, word);
        unitThirtyValue.put(PHONETIC_WORD, phonetic);
        unitThirtyValue.put(TRANSLATE_WORD, wordTranslate);
        unitThirtyValue.put(DEFINITION_WORD, definition);
        unitThirtyValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitThirtyValue.put(EXAMPLE_WORD, example);
        unitThirtyValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        unitThirtyValue.put(EXTRA_NOTE, extraNote);
        db.insert(WORD_TABLE_30, null, unitThirtyValue);
    }

}