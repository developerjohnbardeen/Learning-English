package com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.WordDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.a4000essentialwordsbook1.R;


public class WordDatabaseOpenHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String WORD_DATABASE = "WORD_DATABASE";
    private static final String WORD_TABLE = "UNIT_1";

    private static final String WORD_ID = "_id";
    private static final String WORD_IMG = "WORD_IMAGE";
    private static final String WORD = "WORD";
    private static final String PHONETIC_WORD = "PHONETIC";
    private static final String TRANSLATE_WORD = "TRANSLATE_WORD";
    private static final String DEFINITION_WORD = "DEFINITION_WORD";
    private static final String DEFINITION_TRANSLATE_WORD = "DEFINITION_TRANSLATE_WORD";
    private static final String EXAMPLE_WORD = "EXAMPLE_WORD";
    private static final String EXAMPLE_TRANSLATE_WORD = "EXAMPLE_TRANSLATE_WORD";
    private static final String AUDIO_WORD = "AUDIO_WORD";
    private static final String HARD_FLAG = "HARD_FLAG";
    private static final String EASY_FLAG = "EASY_FLAG";
    public Context dbContext;

    private static final String CREATE_TABLE_WORD = "CREATE TABLE " + WORD_TABLE + " (" +
            WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            WORD_IMG + " INTEGER, " +
            AUDIO_WORD + " INTEGER, " +
            HARD_FLAG + " INTEGER, " +
            EASY_FLAG + " INTEGER, " +
            WORD + " TEXT, " +
            PHONETIC_WORD + " TEXT, " +
            TRANSLATE_WORD + " TEXT, " +
            DEFINITION_WORD + " TEXT, " +
            DEFINITION_TRANSLATE_WORD + " TEXT, " +
            EXAMPLE_WORD + " TEXT, " +
            EXAMPLE_TRANSLATE_WORD + " TEXT);";

    public WordDatabaseOpenHelper(Context context){
        super(context, WORD_DATABASE , null , DB_VERSION);
        this.dbContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        wordUpdateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WORD_TABLE);
        wordUpdateDatabase(db, oldVersion, newVersion);
    }

    public void wordUpdateDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(CREATE_TABLE_WORD);

        insertWordTableOne(db, R.drawable.afraied, R.raw.a,0, 0,   dbContext.getString(R.string.afraid), dbContext.getString(R.string.afraid_pho), dbContext.getString(R.string.afraid_translate), dbContext.getString(R.string.afraid_defi), dbContext.getString(R.string.afraid_dfntn_trnslt), dbContext.getString(R.string.afraid_exmpl), dbContext.getString(R.string.afraid_exmpl_tran));
        insertWordTableOne(db, R.drawable.agree, R.raw.a,0, 0,    dbContext.getString(R.string.agree), dbContext.getString(R.string.phon_agree),dbContext.getString(R.string.agree_meaning), dbContext.getString(R.string.agree_defini),dbContext.getString(R.string.agree_dfntn_trnslt), dbContext.getString(R.string.agree_exmpl), dbContext.getString(R.string.agree_exmpl_meaning));
        insertWordTableOne(db, R.drawable.angry, R.raw.a,0, 0,    dbContext.getString(R.string.angry), dbContext.getString(R.string.angry_phon),dbContext.getString(R.string.angry_meaning), dbContext.getString(R.string.angry_defi),dbContext.getString(R.string.angry_dfntn_trnslt), dbContext.getString(R.string.angry_exmpl), dbContext.getString(R.string.angry_exmpl_meaning));
        insertWordTableOne(db, R.drawable.arrive, R.raw.a,0, 0,    dbContext.getString(R.string.arrive), dbContext.getString(R.string.arrive_pho), dbContext.getString(R.string.arrive_translate), dbContext.getString(R.string.arrive_dfntn),dbContext.getString(R.string.arrive_dfntn_trnslt), dbContext.getString(R.string.arrive_exmpl), dbContext.getString(R.string.arrive_exmpe_trnslt));
        insertWordTableOne(db, R.drawable.attack, R.raw.a, 0, 0,   dbContext.getString(R.string.attack), dbContext.getString(R.string.attack_pho),dbContext.getString(R.string.attack_trnslt), dbContext.getString(R.string.attack_dfntn),dbContext.getString(R.string.attack_dfntn_trnslt), dbContext.getString(R.string.attack_exmpl), dbContext.getString(R.string.attack_exmpl_trnslt));
        insertWordTableOne(db, R.drawable.bottom, R.raw.a,  0, 0,  dbContext.getString(R.string.bottom), dbContext.getString(R.string.bottom_pho),dbContext.getString(R.string.bottom_trnslt), dbContext.getString(R.string.bottom_dfntn),dbContext.getString(R.string.bottom_dfntn_trnslt), dbContext.getString(R.string.bottom_exmpl), dbContext.getString(R.string.bottom_exmpl_trnslt));
        insertWordTableOne(db, R.drawable.clever, R.raw.a, 0, 0,   dbContext.getString(R.string.clever), dbContext.getString(R.string.clever_pho), dbContext.getString(R.string.clever_trnslt), dbContext.getString(R.string.clever_dfntn),dbContext.getString(R.string.clever_dfntn_trnslt), dbContext.getString(R.string.clever_exmpl), dbContext.getString(R.string.clever_exmple_trnslt));
        insertWordTableOne(db, R.drawable.cruel, R.raw.a, 0, 0,   dbContext.getString(R.string.cruel), dbContext.getString(R.string.cruel_pho),dbContext.getString(R.string.cruel_trnslt), dbContext.getString(R.string.cruel_dfntn),dbContext.getString(R.string.cruel_dfntn_trnslt), dbContext.getString(R.string.cruel_exmpl), dbContext.getString(R.string.cruel_exmpl_trnslt));
        insertWordTableOne(db, R.drawable.finally_1, R.raw.a,  0, 0,  dbContext.getString(R.string.finally_1), dbContext.getString(R.string.finally_pho),dbContext.getString(R.string.finally_trnsle), dbContext.getString(R.string.finally_dfntn),dbContext.getString(R.string.finally_dfntn_trnslt), dbContext.getString(R.string.finally_exmpl), dbContext.getString(R.string.finally_exmpl_trnslt));
        insertWordTableOne(db, R.drawable.hide, R.raw.a,  0, 0,  dbContext.getString(R.string.hide), dbContext.getString(R.string.hide_pho), dbContext.getString(R.string.hide_trnslt), dbContext.getString(R.string.hide_dfntn),dbContext.getString(R.string.hide_dfntn_trnslt), dbContext.getString(R.string.hide_exmpl), dbContext.getString(R.string.hide_exmpl_trnslt));
        insertWordTableOne(db, R.drawable.hunt, R.raw.a,  0, 0,  dbContext.getString(R.string.hunt), dbContext.getString(R.string.hunt_pho),dbContext.getString(R.string.hunt_trnslt), dbContext.getString(R.string.hunt_dfntn),dbContext.getString(R.string.hunt_dfntn_trnslt), dbContext.getString(R.string.hunt_exmpl), dbContext.getString(R.string.hunt_exmpl_trnlt));
        insertWordTableOne(db, R.drawable.lot, R.raw.a,  0, 0,  dbContext.getString(R.string.lot), dbContext.getString(R.string.lot_pho),dbContext.getString(R.string.lot_trnslt), dbContext.getString(R.string.lot_dfntn),dbContext.getString(R.string.lot_dfntn_trnslt), dbContext.getString(R.string.lot_exmpl), dbContext.getString(R.string.lot_exmpl_trnslt));
        insertWordTableOne(db, R.drawable.middle, R.raw.a, 0, 0,   dbContext.getString(R.string.middle), dbContext.getString(R.string.middle_pho), dbContext.getString(R.string.middle_trnslt), dbContext.getString(R.string.middle_dfntn),dbContext.getString(R.string.middle_dfntn_trnslt), dbContext.getString(R.string.middle_exmpl), dbContext.getString(R.string.middle_exmpl_trnslt));
        insertWordTableOne(db, R.drawable.moment, R.raw.a,  0, 0,  dbContext.getString(R.string.moment), dbContext.getString(R.string.moment_pho),dbContext.getString(R.string.moment_trnslt), dbContext.getString(R.string.moment_dfntn),dbContext.getString(R.string.moment_dfntn_trnslt), dbContext.getString(R.string.moment_exmpl), dbContext.getString(R.string.moment_exmpl_trnslt));
        insertWordTableOne(db, R.drawable.pleased, R.raw.a, 0, 0,   dbContext.getString(R.string.pleased), dbContext.getString(R.string.pleased_pho),dbContext.getString(R.string.pleased_trnslt), dbContext.getString(R.string.pleased_dfntn),dbContext.getString(R.string.pleased_dfntn_trnslt), dbContext.getString(R.string.pleased_exmpl), dbContext.getString(R.string.pleased_exmpl_trnslt));
        insertWordTableOne(db, R.drawable.promise, R.raw.a,0, 0,   dbContext.getString(R.string.promise), dbContext.getString(R.string.promise_pho), dbContext.getString(R.string.promise_trnslt), dbContext.getString(R.string.promise_dfntn), dbContext.getString(R.string.promise_dfntn_trnslt), dbContext.getString(R.string.promise_exmpl), dbContext.getString(R.string.promise_exmpl_trnslt));
        insertWordTableOne(db, R.drawable.reply, R.raw.a, 0, 0,   dbContext.getString(R.string.reply), dbContext.getString(R.string.reply_pho),dbContext.getString(R.string.reply_trnslt), dbContext.getString(R.string.reply_dfntn), dbContext.getString(R.string.reply_dfntn_trnslt), dbContext.getString(R.string.reply_exmpl), dbContext.getString(R.string.reply_exmpl_trnslt));
        insertWordTableOne(db, R.drawable.safe, R.raw.a,  0, 0,  dbContext.getString(R.string.safe), dbContext.getString(R.string.safe_pho),dbContext.getString(R.string.safe_trnslt), dbContext.getString(R.string.safe_dfntn), dbContext.getString(R.string.safe_dfntn_trnslt), dbContext.getString(R.string.safe_exmpl), dbContext.getString(R.string.safe_exmpl_trnslt));
        insertWordTableOne(db, R.drawable.trick, R.raw.a,  0, 0,  dbContext.getString(R.string.trick), dbContext.getString(R.string.trick_pho), dbContext.getString(R.string.trick_trnslt), dbContext.getString(R.string.trick_dfntn), dbContext.getString(R.string.trick_dfntn_trnslt), dbContext.getString(R.string.trick_exmpl), dbContext.getString(R.string.trick_exmpl_trnslt));
        insertWordTableOne(db, R.drawable.well, R.raw.a,  0, 0,  dbContext.getString(R.string.well), dbContext.getString(R.string.well_pho),dbContext.getString(R.string.well_trnslt), dbContext.getString(R.string.well_dfntn),dbContext.getString(R.string.well_dfntn_trnslt), dbContext.getString(R.string.well_exmpl), dbContext.getString(R.string.well_exmpl_trnslt));


    }

    private void insertWordTableOne(SQLiteDatabase db, int imgWord, int audio, int hardFlag, int easyFlag,
                                    String word, String phonetic, String wordTranslate,
                                    String definition, String definitionTranslate, String example, String exmplTranslate){

        ContentValues unitOneValue = new ContentValues();
        unitOneValue.put(WORD_IMG, imgWord);
        unitOneValue.put(AUDIO_WORD, audio);
        unitOneValue.put(HARD_FLAG, hardFlag);
        unitOneValue.put(EASY_FLAG, easyFlag);
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
