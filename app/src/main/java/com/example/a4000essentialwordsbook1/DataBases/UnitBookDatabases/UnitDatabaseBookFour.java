package com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.CreateTables.TablesNote;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;

public class UnitDatabaseBookFour extends SQLiteOpenHelper {
    private static final String UNIT_DATABASE = TablesNote.UNIT_DATABASE_BOOK_4;
    private static final int UNIT_DB_VERSION = DB_NOTES.DB_VERSION;
    private static final String UNIT_TABLE = DB_NOTES.UNIT_TABLE;
    private static final String UNIT_ID = DB_NOTES.UNIT_ID; //use it is UNIT_NUMBER as well
    private static final String UNIT_IMG = DB_NOTES.UNIT_IMG;
    private static final String UNIT_TITLE = DB_NOTES.UNIT_TITLE;
    private static final String UNIT_AUDIO = DB_NOTES.UNIT_AUDIO;
    private static final String UNIT_COMPLETE_WORD_AUDIO = DB_NOTES.UNIT_COMPLETE_WORD_AUDIO;
    private static final String UNIT_STORY = DB_NOTES.UNIT_STORY;
    private final Context uContext;

    private static final String SQLITE_UNIT_TABLE = "CREATE TABLE " + UNIT_TABLE + " (" +
            UNIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            UNIT_IMG + " INTEGER, " +
            UNIT_AUDIO + " INTEGER, " +
            UNIT_COMPLETE_WORD_AUDIO + " INTEGER, " +
            UNIT_STORY + " TEXT, " +
            UNIT_TITLE + " TEXT);";




    public UnitDatabaseBookFour(Context context) {
        super(context, UNIT_DATABASE, null, UNIT_DB_VERSION);
        this.uContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        unitUpdateDatabase(db, 0, UNIT_DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + UNIT_TABLE);
        unitUpdateDatabase(db, oldVersion, newVersion);
    }


    private void unitUpdateDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQLITE_UNIT_TABLE);

        insertUnitData(db, R.drawable.lionandrabit, R.raw.lionandrabit, R.raw. lion_complete_word_audio, "The Lion and the Rabbit", uContext.getString(R.string.story_unit_1));
        insertUnitData(db, R.drawable.laboratory, R.raw.laboratory, R.raw.laboratory_complete_word_audio,  "The Laboratory", uContext.getString(R.string.story_unit_2));
        insertUnitData(db, R.drawable.report, R.raw.report, R.raw.report_complete_word_audio,  "The Report", uContext.getString(R.string.story_unit_3));
        insertUnitData(db, R.drawable.dogbell, R.raw.dogbell, R.raw.dogbell_complete_word_audio,  "The Dog's Bell", uContext.getString(R.string.story_unit_4));
        insertUnitData(db, R.drawable.sunchilde, R.raw.sunchilde, R.raw.sunchilde_complete_word_audio,  "the Jackal and the Sun Child", uContext.getString(R.string.story_unit_5));
        insertUnitData(db, R.drawable.thefriendlyghost, R.raw.ghost, R.raw.ghost_complete_word_audio, "The Friendly Ghost", uContext.getString(R.string.story_unit_6));
        insertUnitData(db, R.drawable.thebestprince, R.raw.prince, R.raw.prince_complete_word_audio, "The Best Prince", uContext.getString(R.string.story_unit_7));
        insertUnitData(db, R.drawable.moonmade, R.raw.sunmoonmade, R.raw.sunmoonmade_complete_word_audio, "How the Sun and Moon Were Made", uContext.getString(R.string.story_unit_8));
        insertUnitData(db, R.drawable.starfish, R.raw.starfish, R.raw.starfish_complete_word_audio,  "The Starfish", uContext.getString(R.string.story_unit_9));
        insertUnitData(db, R.drawable.peacock, R.raw.peacock, R.raw.peacock_complete_word_audio,"The First Peacock", uContext.getString(R.string.story_unit_10));
        insertUnitData(db, R.drawable.creature, R.raw.rosecreature, R.raw.rosecreature_complete_word_audio, "Princess Rose and the Creature", uContext.getString(R.string.story_unit_11));
        insertUnitData(db, R.drawable.artist, R.raw.crazyartist, R.raw.crazyartist_complete_word_audio,  "the Crazy Artist", uContext.getString(R.string.story_unit_12));
        insertUnitData(db, R.drawable.cats, R.raw.farmercats, R.raw.farmercats_complete_word_audio,  "the Farmer and the Cats", uContext.getString(R.string.story_unit_13));
        insertUnitData(db, R.drawable.magicalbook, R.raw.magicalbook, R.raw.magicalbook_complete_word_audio, "A Magical Book", uContext.getString(R.string.story_unit_14));
        insertUnitData(db, R.drawable.bigrace, R.raw. bigrace, R.raw.bigrace_complete_word_audio,  "The Big Race", uContext.getString(R.string.story_unit_15));
    }

    private void insertUnitData(SQLiteDatabase db, int unitImg,
                                int unitAudio, int completeWordAudio,
                                String unitTitle, String unitStory){

        ContentValues unitValues = new ContentValues();
        unitValues.put(UNIT_IMG, unitImg);
        unitValues.put(UNIT_AUDIO, unitAudio);
        unitValues.put(UNIT_COMPLETE_WORD_AUDIO, completeWordAudio);
        unitValues.put(UNIT_STORY, unitStory);
        unitValues.put(UNIT_TITLE, unitTitle);
        db.insert(UNIT_TABLE, null, unitValues);
    }
}
