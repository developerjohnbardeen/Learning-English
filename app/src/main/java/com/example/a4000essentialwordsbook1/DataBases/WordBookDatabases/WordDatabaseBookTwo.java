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
        /*insert.insertAllWordTables_1(db, R.drawable.anxious, R.raw.a , 0, 0, 2 , 1, 37895, 39685, 39685, 44457, 44457, 50081,  dbContext.getString(R.string.anxious), dbContext.getString(R.string.anxious_pho), dbContext.getString(R.string.anxious_translate), dbContext.getString(R.string.anxious_defi), dbContext.getString(R.string.anxious_dfntn_trnslt), dbContext.getString(R.string.anxious_exmpl), dbContext.getString(R.string.anxious_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.awful, R.raw.a , 0, 0, 2 , 1, 50081, 51795, 51795, 55402, 55402, 59919,  dbContext.getString(R.string.awful), dbContext.getString(R.string.awful_pho), dbContext.getString(R.string.awful_translate), dbContext.getString(R.string.awful_defi), dbContext.getString(R.string.awful_dfntn_trnslt), dbContext.getString(R.string.awful_exmpl), dbContext.getString(R.string.awful_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.consist, R.raw.a , 0, 0, 2 , 1, 59919, 61688, 61688, 66157, 66157, 72944,  dbContext.getString(R.string.consist), dbContext.getString(R.string.consist_pho), dbContext.getString(R.string.consist_translate), dbContext.getString(R.string.consist_defi), dbContext.getString(R.string.consist_dfntn_trnslt), dbContext.getString(R.string.consist_exmpl), dbContext.getString(R.string.consist_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.desire, R.raw.a , 0, 0, 2 , 1, 72944, 74833, 74833, 77848, 77848, 83308,  dbContext.getString(R.string.desire), dbContext.getString(R.string.desire_pho), dbContext.getString(R.string.desire_translate), dbContext.getString(R.string.desire_defi), dbContext.getString(R.string.desire_dfntn_trnslt), dbContext.getString(R.string.desire_exmpl), dbContext.getString(R.string.desire_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.eager, R.raw.a , 0, 0, 2 , 1, 83308, 84984, 84984, 89652, 89652, 94542,  dbContext.getString(R.string.eager), dbContext.getString(R.string.eager_pho), dbContext.getString(R.string.eager_translate), dbContext.getString(R.string.eager_defi), dbContext.getString(R.string.eager_dfntn_trnslt), dbContext.getString(R.string.eager_exmpl), dbContext.getString(R.string.eager_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.household, R.raw.a , 0, 0, 2 , 1, 94542, 96550, 96550, 101047, 101047, 106778,  dbContext.getString(R.string.household), dbContext.getString(R.string.household_pho), dbContext.getString(R.string.household_translate), dbContext.getString(R.string.household_defi), dbContext.getString(R.string.household_dfntn_trnslt), dbContext.getString(R.string.household_exmpl), dbContext.getString(R.string.household_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.intent, R.raw.a , 0, 0, 2 , 1, 106778, 108511, 108511, 112014, 112014, 116713,  dbContext.getString(R.string.intent), dbContext.getString(R.string.intent_pho), dbContext.getString(R.string.intent_translate), dbContext.getString(R.string.intent_defi), dbContext.getString(R.string.intent_dfntn_trnslt), dbContext.getString(R.string.intent_exmpl), dbContext.getString(R.string.intent_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.landscape, R.raw.a , 0, 0, 2 , 1, 116713, 118446, 118446, 122688, 122688, 127174,  dbContext.getString(R.string.landscape), dbContext.getString(R.string.landscape_pho), dbContext.getString(R.string.landscape_translate), dbContext.getString(R.string.landscape_defi), dbContext.getString(R.string.landscape_dfntn_trnslt), dbContext.getString(R.string.landscape_exmpl), dbContext.getString(R.string.landscape_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.lift, R.raw.a , 0, 0, 2 , 1, 127174, 128802, 128802, 132277, 132277, 136588,  dbContext.getString(R.string.lift), dbContext.getString(R.string.lift_pho), dbContext.getString(R.string.lift_translate), dbContext.getString(R.string.lift_defi), dbContext.getString(R.string.lift_dfntn_trnslt), dbContext.getString(R.string.lift_exmpl), dbContext.getString(R.string.lift_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.load, R.raw.a , 0, 0, 2 , 1, 136588, 138302, 138302, 142279, 142279, 146879,  dbContext.getString(R.string.load), dbContext.getString(R.string.load_pho), dbContext.getString(R.string.load_translate), dbContext.getString(R.string.load_defi), dbContext.getString(R.string.load_dfntn_trnslt), dbContext.getString(R.string.load_exmpl), dbContext.getString(R.string.load_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.lung, R.raw.a , 0, 0, 2 , 1, 146879, 148725, 148725, 154264, 154264, 159908,  dbContext.getString(R.string.lung), dbContext.getString(R.string.lung_pho), dbContext.getString(R.string.lung_translate), dbContext.getString(R.string.lung_defi), dbContext.getString(R.string.lung_dfntn_trnslt), dbContext.getString(R.string.lung_exmpl), dbContext.getString(R.string.lung_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.motion, R.raw.a , 0, 0, 2 , 1, 159908, 161508, 161508, 165125, 165125, 169882,  dbContext.getString(R.string.motion), dbContext.getString(R.string.motion_pho), dbContext.getString(R.string.motion_translate), dbContext.getString(R.string.motion_defi), dbContext.getString(R.string.motion_dfntn_trnslt), dbContext.getString(R.string.motion_exmpl), dbContext.getString(R.string.motion_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.pace, R.raw.a , 0, 0, 2 , 1, 169882, 171615, 171615, 176036, 176036, 181308,  dbContext.getString(R.string.pace), dbContext.getString(R.string.pace_pho), dbContext.getString(R.string.pace_translate), dbContext.getString(R.string.pace_defi), dbContext.getString(R.string.pace_dfntn_trnslt), dbContext.getString(R.string.pace_exmpl), dbContext.getString(R.string.pace_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.polite, R.raw.a , 0, 0, 2 , 1, 181308, 183041, 183041, 187481, 187481, 193401,  dbContext.getString(R.string.polite), dbContext.getString(R.string.polite_pho), dbContext.getString(R.string.polite_translate), dbContext.getString(R.string.polite_defi), dbContext.getString(R.string.polite_dfntn_trnslt), dbContext.getString(R.string.polite_exmpl), dbContext.getString(R.string.polite_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.possess, R.raw.a , 0, 0, 2 , 1, 193401, 195143, 195143, 199167, 199167, 205859,  dbContext.getString(R.string.possess), dbContext.getString(R.string.possess_pho), dbContext.getString(R.string.possess_translate), dbContext.getString(R.string.possess_defi), dbContext.getString(R.string.possess_dfntn_trnslt), dbContext.getString(R.string.possess_exmpl), dbContext.getString(R.string.possess_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.rapidly, R.raw.a , 0, 0, 2 , 1, 205859, 207677, 207677, 212146, 212146, 216718,  dbContext.getString(R.string.rapidly), dbContext.getString(R.string.rapidly_pho), dbContext.getString(R.string.rapidly_translate), dbContext.getString(R.string.rapidly_defi), dbContext.getString(R.string.rapidly_dfntn_trnslt), dbContext.getString(R.string.rapidly_exmpl), dbContext.getString(R.string.rapidly_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.remark, R.raw.a , 0, 0, 2 , 1, 216718, 218441, 218441, 221538, 221538, 226988,  dbContext.getString(R.string.remark), dbContext.getString(R.string.remark_pho), dbContext.getString(R.string.remark_translate), dbContext.getString(R.string.remark_defi), dbContext.getString(R.string.remark_dfntn_trnslt), dbContext.getString(R.string.remark_exmpl), dbContext.getString(R.string.remark_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.seek, R.raw.a , 0, 0, 2 , 1, 226988, 228607, 228607, 231656, 231656, 237105,  dbContext.getString(R.string.seek), dbContext.getString(R.string.seek_pho), dbContext.getString(R.string.seek_translate), dbContext.getString(R.string.seek_defi), dbContext.getString(R.string.seek_dfntn_trnslt), dbContext.getString(R.string.seek_exmpl), dbContext.getString(R.string.seek_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.shine, R.raw.a , 0, 0, 2 , 1, 237105, 239046, 239046, 142635, 142635, 247144,  dbContext.getString(R.string.shine), dbContext.getString(R.string.shine_pho), dbContext.getString(R.string.shine_translate), dbContext.getString(R.string.shine_defi), dbContext.getString(R.string.shine_dfntn_trnslt), dbContext.getString(R.string.shine_exmpl), dbContext.getString(R.string.shine_exmpl_tran));
        insert.insertAllWordTables_1(db, R.drawable.spill, R.raw.a , 0, 0, 2 , 1, 247144, 248953, 248953, 253081, 253081, 255920,  dbContext.getString(R.string.spill), dbContext.getString(R.string.spill_pho), dbContext.getString(R.string.spill_translate), dbContext.getString(R.string.spill_defi), dbContext.getString(R.string.spill_dfntn_trnslt), dbContext.getString(R.string.spill_exmpl), dbContext.getString(R.string.spill_exmpl_tran));*/
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
