package com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.CreateTables.BluePrints.WordBluePrint.CreateTablesBluePrint;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.CreateTables.InsertTablesValues;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.CreateTables.TablesNote;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;

public class WordDatabaseBookTwo extends SQLiteOpenHelper {

    private static final int DB_VERSION = DB_NOTES.DB_VERSION;
    private static final String BOOK_TWO_WORD_DATABASE = TablesNote.WORD_DATABASE_BOOK_2;;
    private final Context dbContext;
    private final InsertTablesValues insert = new InsertTablesValues();


    public WordDatabaseBookTwo(Context context){
        super(context, BOOK_TWO_WORD_DATABASE , null , DB_VERSION);
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

        /*insertWordValueTableTwo(db, oldVersion, newVersion);
        insertWordValueTableThree(db, oldVersion, newVersion);
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

}
