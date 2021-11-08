package com.example.a4000essentialwordsbook1.DataBases.UnitBookDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.CreateTables.TablesNote;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;


public class UnitDatabaseBookOne extends SQLiteOpenHelper {
    private static final String UNIT_DATABASE = TablesNote.UNIT_DATABASE_BOOK_1;
    private static final int UNIT_DB_VERSION = DB_NOTES.DB_VERSION;
    private static final String UNIT_TABLE = DB_NOTES.UNIT_TABLE;
    private static final String UNIT_ID = DB_NOTES.UNIT_ID; //use it is UNIT_NUMBER as well
    private static final String UNIT_IMG = DB_NOTES.UNIT_IMG;
    private static final String UNIT_TITLE = DB_NOTES.UNIT_TITLE;
    private static final String FARSI_UNIT_TITLE = DB_NOTES.FARSI_UNIT_TITLE;
    private static final String UNIT_AUDIO = DB_NOTES.UNIT_AUDIO;
    private static final String UNIT_COMPLETE_WORD_AUDIO = DB_NOTES.UNIT_COMPLETE_WORD_AUDIO;
    private static final String UNIT_ENG_STORY = DB_NOTES.UNIT_ENG_STORY;
    private static final String UNIT_PERSIAN_STORY = DB_NOTES.UNIT_PERSIAN_STORY;
    private final Context uContext;

    private static final String SQLITE_UNIT_TABLE = "CREATE TABLE " + UNIT_TABLE + " (" +
            UNIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            UNIT_IMG + " INTEGER, " +
            UNIT_AUDIO + " INTEGER, " +
            UNIT_COMPLETE_WORD_AUDIO + " INTEGER, " +
            UNIT_TITLE + " TEXT, " +
            FARSI_UNIT_TITLE + " TEXT, " +
            UNIT_PERSIAN_STORY + " TEXT, " +
            UNIT_ENG_STORY + " TEXT);";




    public UnitDatabaseBookOne(Context context) {
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

        insertUnitData(db, R.drawable.lionandrabit, R.raw.lionandrabit, R.raw.lion_complete_word_audio,"شیر و خرگوش", "The Lion and the Rabbit", uContext.getString(R.string.story_b1_u1_eng), uContext.getString(R.string.story_b1_u1_persian));
        insertUnitData(db, R.drawable.laboratory, R.raw.laboratory, R.raw.laboratory_complete_word_audio,"آزمایشگاه",  "The Laboratory", uContext.getString(R.string.story_b1_u2_eng), uContext.getString(R.string.story_b1_u2_persian));
        insertUnitData(db, R.drawable.report, R.raw.report, R.raw.report_complete_word_audio,"گزارش",  "The Report", uContext.getString(R.string.story_b1_u3_eng), uContext.getString(R.string.story_b1_u3_persian));
        insertUnitData(db, R.drawable.dogbell, R.raw.dogbell, R.raw.dogbell_complete_word_audio,"زنگ (قلاده) سگ",  "The Dog's Bell", uContext.getString(R.string.story_b1_u4_eng), uContext.getString(R.string.story_b1_u4_persian));
        insertUnitData(db, R.drawable.sunchilde, R.raw.sunchilde, R.raw.sunchilde_complete_word_audio,"جکل و فرزند خورشید",  "the Jackal and the Sun Child", uContext.getString(R.string.story_b1_u5_eng), uContext.getString(R.string.story_b1_u5_persian));
        insertUnitData(db, R.drawable.thefriendlyghost, R.raw.ghost, R.raw.ghost_complete_word_audio,"روه مهربان", "The Friendly Ghost", uContext.getString(R.string.story_b1_u6_eng), uContext.getString(R.string.story_b1_u6_persian));
        insertUnitData(db, R.drawable.thebestprince, R.raw.prince, R.raw.prince_complete_word_audio,"بهترین شاهزاده", "The Best Prince", uContext.getString(R.string.story_b1_u7_eng), uContext.getString(R.string.story_b1_u7_persian));
        insertUnitData(db, R.drawable.moonmade, R.raw.sunmoonmade, R.raw.sunmoonmade_complete_word_audio,"چگونگی بوجود آمدن خورشید و ماه", "How the Sun and Moon Were Made", uContext.getString(R.string.story_b1_u8_eng), uContext.getString(R.string.story_b1_u8_persian));
        insertUnitData(db, R.drawable.starfish, R.raw.starfish, R.raw.starfish_complete_word_audio,"ستاره دریایی",  "The Starfish", uContext.getString(R.string.story_b1_u9_eng), uContext.getString(R.string.story_b1_u9_persian));
        insertUnitData(db, R.drawable.peacock, R.raw.peacock, R.raw.peacock_complete_word_audio,"اولین طاووس","The First Peacock", uContext.getString(R.string.story_b1_u10_eng), uContext.getString(R.string.story_b1_u10_persian));
        insertUnitData(db, R.drawable.creature, R.raw.rosecreature, R.raw.rosecreature_complete_word_audio,"شاه دخت رز و موجود", "Princess Rose and the Creature", uContext.getString(R.string.story_b1_u11_eng), uContext.getString(R.string.story_b1_u11_persian));
        insertUnitData(db, R.drawable.artist, R.raw.crazyartist, R.raw.crazyartist_complete_word_audio,"هنرمند دیوانه",  "the Crazy Artist", uContext.getString(R.string.story_b1_u12_eng), uContext.getString(R.string.story_b1_u12_persian));
        insertUnitData(db, R.drawable.cats, R.raw.farmercats, R.raw.farmercats_complete_word_audio,"کشاورز و گربه ها",  "the Farmer and the Cats", uContext.getString(R.string.story_b1_u13_eng), uContext.getString(R.string.story_b1_u13_persian));
        insertUnitData(db, R.drawable.magicalbook, R.raw.magicalbook, R.raw.magicalbook_complete_word_audio,"یک کتاب جادویی", "A Magical Book", uContext.getString(R.string.story_b1_u14_eng), uContext.getString(R.string.story_b1_u14_persian));
        insertUnitData(db, R.drawable.bigrace, R.raw. bigrace, R.raw.bigrace_complete_word_audio,"مسابقه بزرگ",  "The Big Race", uContext.getString(R.string.story_b1_u15_eng), uContext.getString(R.string.story_b1_u15_persian));
        insertUnitData(db, R.drawable.adams_gold, R.raw.adams_gold, R.raw.adams_gold_complete_word_audio,"طلاهای آدامز کانتی", "Adams County’s Gold", uContext.getString(R.string.story_b1_u16_eng), uContext.getString(R.string.story_b1_u16_persian));
        insertUnitData(db, R.drawable.racewater, R.raw.racewater, R.raw.racewater_complete_word_audio,"مسابقه برای آب","The Race For Water", uContext.getString(R.string.story_b1_u17_eng), uContext.getString(R.string.story_b1_u17_persian));
        insertUnitData(db, R.drawable.redchicken, R.raw.redchicken, R.raw.redchicken_complete_word_audio,"مرغ سرخ کوچک",  "The Little Red Chicken", uContext.getString(R.string.story_b1_u18_eng), uContext.getString(R.string.story_b1_u18_persian));
        insertUnitData(db, R.drawable.shipwrecked, R.raw.shipwrecked, R.raw.shipwrecked_complete_word_audio,"کشتی خراب شده",  "Shipwrecked", uContext.getString(R.string.story_b1_u19_eng), uContext.getString(R.string.story_b1_u19_persian));
        insertUnitData(db, R.drawable.citiesgold, R.raw.citiesgold, R.raw.citiesgold_complete_word_audio,"هفت شهر از طلا",  "The Seven Cities Of Gold", uContext.getString(R.string.story_b1_u20_eng), uContext.getString(R.string.story_b1_u20_persian));
        insertUnitData(db, R.drawable.katy, R.raw.katy, R.raw.katy_complete_word_audio,"کتی", "Katy", uContext.getString(R.string.story_b1_u21_eng), uContext.getString(R.string.story_b1_u21_persian));
        insertUnitData(db, R.drawable.betterreward, R.raw.betterreward, R.raw.betterreward_complete_word_audio,"جایزه ای بهتر", "A Better Reward", uContext.getString(R.string.story_b1_u22_eng), uContext.getString(R.string.story_b1_u22_persian));
        insertUnitData(db, R.drawable.thecamp, R.raw.thecamp, R.raw.thecamp_complete_word_audio,"اردوگاه", "The Camp", uContext.getString(R.string.story_b1_u23_eng), uContext.getString(R.string.story_b1_u23_persian));
        insertUnitData(db, R.drawable.strongfriendship, R.raw.strongfriendship, R.raw.strongfriendship_complete_word_audio,"یک دوستی مستحکم",  "A Strong Friendship", uContext.getString(R.string.story_b1_u24_eng), uContext.getString(R.string.story_b1_u24_persian));
        insertUnitData(db, R.drawable.joespond, R.raw.joespond, R.raw.joespond_complete_word_audio,"گودال جویی","Joe’s Pound", uContext.getString(R.string.story_b1_u25_eng), uContext.getString(R.string.story_b1_u25_persian));
        insertUnitData(db, R.drawable.hisdonkey, R.raw.hisdonkey, R.raw.hisdonkey_complete_word_audio,"آرچیو و الاغش", "Archive And His Donkey", uContext.getString(R.string.story_b1_u26_eng), uContext.getString(R.string.story_b1_u26_persian));
        insertUnitData(db, R.drawable.spiterandbird, R.raw.spiterandbird, R.raw.spiterandbird_complete_word_audio,"عنکبوت و روباه",  "The Spider And The Bird", uContext.getString(R.string.story_b1_u27_eng), uContext.getString(R.string.story_b1_u27_persian));
        insertUnitData(db, R.drawable.party, R.raw.party, R.raw.party_complete_word_audio,"مهمانی", "The Party", uContext.getString(R.string.story_b1_u28_eng), uContext.getString(R.string.story_b1_u28_persian));
        insertUnitData(db, R.drawable.worldlight, R.raw.worldlight, R.raw.worldlight_complete_word_audio,"چگونه دنیا نور گرفت", "How The World Got Light", uContext.getString(R.string.story_b1_u29_eng), uContext.getString(R.string.story_b1_u29_persian));
        insertUnitData(db, R.drawable.catandssecrets, R.raw.catandssecrets, R.raw.catandssecrets_complete_word_audio,"گربه ها و راز ها",  "Cats And Secrets", uContext.getString(R.string.story_b1_u30_eng), uContext.getString(R.string.story_b1_u30_persian));
    }

    private void insertUnitData(SQLiteDatabase db, int unitImg,
                                int unitAudio, int completeWordAudio,
                                String farsiTitle, String unitTitle, String unitEngStory, String unitPersianStory){

        ContentValues unitValues = new ContentValues();
        unitValues.put(UNIT_IMG, unitImg);
        unitValues.put(UNIT_AUDIO, unitAudio);
        unitValues.put(UNIT_COMPLETE_WORD_AUDIO, completeWordAudio);
        unitValues.put(FARSI_UNIT_TITLE, farsiTitle);
        unitValues.put(UNIT_TITLE, unitTitle);
        unitValues.put(UNIT_ENG_STORY, unitEngStory);
        unitValues.put(UNIT_PERSIAN_STORY, unitPersianStory);
        db.insert(UNIT_TABLE, null, unitValues);
    }
}
