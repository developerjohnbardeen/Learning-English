package com.example.a4000essentialwordsbook1.DataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.CreateTables.BluePrints.WordBluePrint.CreateTablesBluePrint;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.CreateTables.InsertTablesValues;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.CreateTables.TablesNote;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;


public class WordDatabaseOpenHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = DB_NOTES.DB_VERSION;
    private static final String BOOK_ONE_WORD_DATABASE = TablesNote.WORD_DATABASE_BOOK_1;
    private static final String WORD_TABLE = "UNIT_1";

    private static final String WORD_ID = DB_NOTES.WORD_ID;
    private static final String WORD_IMG = DB_NOTES.WORD_IMG;
    private static final String WORD = DB_NOTES.WORD;
    private static final String PHONETIC_WORD = DB_NOTES.PHONETIC_WORD;
    private static final String TRANSLATE_WORD = DB_NOTES.TRANSLATE_WORD;
    private static final String DEFINITION_WORD = DB_NOTES.DEFINITION_WORD;
    private static final String DEFINITION_TRANSLATE_WORD = DB_NOTES.DEFINITION_TRANSLATE_WORD;
    private static final String EXAMPLE_WORD = DB_NOTES.EXAMPLE_WORD;
    private static final String EXAMPLE_TRANSLATE_WORD = DB_NOTES.EXAMPLE_TRANSLATE_WORD;
    private static final String AUDIO_WORD = DB_NOTES.AUDIO_WORD;
    private static final String HARD_FLAG = DB_NOTES.HARD_FLAG;
    private static final String EASY_FLAG = DB_NOTES.EASY_FLAG;
    public static final String BOOK_NUMBER = DB_NOTES.BOOK_NUMBER;
    public static final String UNIT_NUMBER = DB_NOTES.UNIT_NUMBER;

    public Context dbContext;
    private final InsertTablesValues insert = new InsertTablesValues();

    private static final String CREATE_TABLE_WORD = "CREATE TABLE " + WORD_TABLE + " (" +
            WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            WORD_IMG + " INTEGER, " +
            AUDIO_WORD + " INTEGER, " +
            HARD_FLAG + " INTEGER, " +
            EASY_FLAG + " INTEGER, " +
            BOOK_NUMBER + " INTEGER, " +
            UNIT_NUMBER + " INTEGER, " +
            WORD + " TEXT, " +
            PHONETIC_WORD + " TEXT, " +
            TRANSLATE_WORD + " TEXT, " +
            DEFINITION_WORD + " TEXT, " +
            DEFINITION_TRANSLATE_WORD + " TEXT, " +
            EXAMPLE_WORD + " TEXT, " +
            EXAMPLE_TRANSLATE_WORD + " TEXT);";

    public WordDatabaseOpenHelper(Context context){
        super(context, BOOK_ONE_WORD_DATABASE , null , DB_VERSION);
        this.dbContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        wordUpdateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = 1 ; i <= 30 ; i++){
            db.execSQL("DROP TABLE IF EXISTS UNIT_" + i);
        }
        wordUpdateDatabase(db, oldVersion, newVersion);
    }

    public void wordUpdateDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        createTables(db, oldVersion, newVersion);
    }

    private void createTables(SQLiteDatabase db, int oldVersion, int newVersion){
        insertAllWordsValesTables(db, oldVersion, newVersion);
    }


    private void insertAllWordsValesTables(SQLiteDatabase db, int oldVersion, int newVersion){
        CreateTablesBluePrint bluePrint;
        for (int i = 1 ; i <= 30 ; i++){
            bluePrint = new CreateTablesBluePrint();
            String CREATE_TABLES = bluePrint.CREATE_WORD_TABLE_(i);
            db.execSQL(CREATE_TABLES);
        }
        insertWordValueTableOne(db, oldVersion, newVersion);
        insertWordValueTableTwo(db, oldVersion, newVersion);

        /*insertWordValueTableThree(db, oldVersion, newVersion);
        insertWordValueTableFour(db, oldVersion, newVersion);
        insertWordValueTableFive(db, oldVersion, newVersion);
        insertWordValueTableSix(db, oldVersion, newVersion);
        insertWordValueTableSeven(db, oldVersion, newVersion);
        insertWordValueTableEight(db, oldVersion, newVersion);
        insertWordValueTableNine(db, oldVersion, newVersion);
        insertWordValueTableTen(db, oldVersion, newVersion);
        insertWordValueTableEleven(db, oldVersion, newVersion);
        insertWordValueTableTwelve(db, oldVersion, newVersion);
        insertWordValueTableThirteen(db, oldVersion, newVersion);
        insertWordValueTableFourteen(db, oldVersion, newVersion);
        insertWordValueTableFifteen(db, oldVersion, newVersion);
        insertWordValueTableSixteen(db, oldVersion, newVersion);
        insertWordValueTableSeventeen(db, oldVersion, newVersion);
        insertWordValueTableEighteen(db, oldVersion, newVersion);
        insertWordValueTableNineteen(db, oldVersion, newVersion);
        insertWordValueTableTwenty(db, oldVersion, newVersion);
        insertWordValueTableTwentyOne(db, oldVersion, newVersion);
        insertWordValueTableTwentyTow(db, oldVersion, newVersion);
        insertWordValueTableTwentyThree(db, oldVersion, newVersion);
        insertWordValueTableTwentyFour(db, oldVersion, newVersion);
        insertWordValueTableTwentyFive(db, oldVersion, newVersion);
        insertWordValueTableTwentySix(db, oldVersion, newVersion);
        insertWordValueTableTwentySeven(db, oldVersion, newVersion);
        insertWordValueTableTwentyEight(db, oldVersion, newVersion);
        insertWordValueTableTwentyNine(db, oldVersion, newVersion);
        insertWordValueTableThirty(db, oldVersion, newVersion);*/

    }
    private void insertWordValueTableOne(SQLiteDatabase db, int oldVersion, int newVersion){

    }


    private void insertWordValueTableTwo(SQLiteDatabase db, int oldVersion, int newVersion){

    }


    private void insertWordValueTableThree(SQLiteDatabase db, int oldVersion, int newVersion){
    }
    private void insertWordValueTableFour(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    private void insertWordValueTableFive(SQLiteDatabase db, int oldVersion, int newVersion){
    }
    private void insertWordValueTableSix(SQLiteDatabase db, int oldVersion, int newVersion){
    }
    private void insertWordValueTableSeven(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableEight(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableNine(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableTen(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableEleven(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableTwelve(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableThirteen(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableFourteen(SQLiteDatabase db, int oldVersion, int newVersion){
    }
    private void insertWordValueTableFifteen(SQLiteDatabase db, int oldVersion, int newVersion){
    }
    private void insertWordValueTableSixteen(SQLiteDatabase db, int oldVersion, int newVersion){
    }
    private void insertWordValueTableSeventeen(SQLiteDatabase db, int oldVersion, int newVersion){


    }
    private void insertWordValueTableEighteen(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableNineteen(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableTwenty(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableTwentyOne(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableTwentyTow(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableTwentyThree(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableTwentyFour(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableTwentyFive(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableTwentySix(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableTwentySeven(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableTwentyEight(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableTwentyNine(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    private void insertWordValueTableThirty(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void insertAllWordTables(SQLiteDatabase db, int imgWord, int audio, int hardFlag, int easyFlag,int bookNum, int unitNum,
                                    String word, String phonetic, String wordTranslate,
                                    String definition, String definitionTranslate, String example, String exmplTranslate){

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(AUDIO_WORD, audio);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
        unitOneValue.put(BOOK_NUMBER, bookNum);
        unitOneValue.put(UNIT_NUMBER, unitNum);
        unitOneValue.put(WORD, word);
        unitOneValue.put(PHONETIC_WORD, phonetic);
        unitOneValue.put(TRANSLATE_WORD, wordTranslate);
        unitOneValue.put(DEFINITION_WORD, definition);
        unitOneValue.put(DEFINITION_TRANSLATE_WORD, definitionTranslate);
        unitOneValue.put(EXAMPLE_WORD, example);
        unitOneValue.put(EXAMPLE_TRANSLATE_WORD, exmplTranslate);
        db.insert(WORD_TABLE, null, unitOneValue);
    }

}