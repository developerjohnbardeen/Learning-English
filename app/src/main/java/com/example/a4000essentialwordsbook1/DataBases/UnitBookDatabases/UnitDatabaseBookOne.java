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
            UNIT_IMG + " TEXT, " +
            UNIT_AUDIO + " TEXT, " +
            UNIT_COMPLETE_WORD_AUDIO + " TEXT, " +
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


    private void unitUpdateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLITE_UNIT_TABLE);

        insertUnitData(db, uContext.getString(R.string.b1_u_1_unit_image_url_sub), uContext.getString(R.string.b1_u_1_unit_audio_url_sub), uContext.getString(R.string.b1_u_1_word_audio_url_sub), "شیر و خرگوش", "The Lion and the Rabbit", uContext.getString(R.string.story_b1_u1_eng), uContext.getString(R.string.story_b1_u1_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_2_unit_image_url_sub), uContext.getString(R.string.b1_u_2_unit_audio_url_sub), uContext.getString(R.string.b1_u_2_word_audio_url_sub), "آزمایشگاه", "The Laboratory", uContext.getString(R.string.story_b1_u2_eng), uContext.getString(R.string.story_b1_u2_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_3_unit_image_url_sub), uContext.getString(R.string.b1_u_3_unit_audio_url_sub), uContext.getString(R.string.b1_u_3_word_audio_url_sub), "گزارش", "The Report", uContext.getString(R.string.story_b1_u3_eng), uContext.getString(R.string.story_b1_u3_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_4_unit_image_url_sub), uContext.getString(R.string.b1_u_4_unit_audio_url_sub), uContext.getString(R.string.b1_u_4_word_audio_url_sub), "زنگ (قلاده) سگ", "The Dog's Bell", uContext.getString(R.string.story_b1_u4_eng), uContext.getString(R.string.story_b1_u4_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_5_unit_image_url_sub), uContext.getString(R.string.b1_u_5_unit_audio_url_sub), uContext.getString(R.string.b1_u_5_word_audio_url_sub), "جکل و فرزند خورشید", "the Jackal and the Sun Child", uContext.getString(R.string.story_b1_u5_eng), uContext.getString(R.string.story_b1_u5_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_6_unit_image_url_sub), uContext.getString(R.string.b1_u_6_unit_audio_url_sub), uContext.getString(R.string.b1_u_6_word_audio_url_sub), "روه مهربان", "The Friendly Ghost", uContext.getString(R.string.story_b1_u6_eng), uContext.getString(R.string.story_b1_u6_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_7_unit_image_url_sub), uContext.getString(R.string.b1_u_7_unit_audio_url_sub), uContext.getString(R.string.b1_u_7_word_audio_url_sub), "بهترین شاهزاده", "The Best Prince", uContext.getString(R.string.story_b1_u7_eng), uContext.getString(R.string.story_b1_u7_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_8_unit_image_url_sub), uContext.getString(R.string.b1_u_8_unit_audio_url_sub), uContext.getString(R.string.b1_u_8_word_audio_url_sub), "چگونگی بوجود آمدن خورشید و ماه", "How the Sun and Moon Were Made", uContext.getString(R.string.story_b1_u8_eng), uContext.getString(R.string.story_b1_u8_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_9_unit_image_url_sub), uContext.getString(R.string.b1_u_9_unit_audio_url_sub), uContext.getString(R.string.b1_u_9_word_audio_url_sub), "ستاره دریایی", "The Starfish", uContext.getString(R.string.story_b1_u9_eng), uContext.getString(R.string.story_b1_u9_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_10_unit_image_url_sub), uContext.getString(R.string.b1_u_10_unit_audio_url_sub), uContext.getString(R.string.b1_u_10_word_audio_url_sub), "اولین طاووس", "The First Peacock", uContext.getString(R.string.story_b1_u10_eng), uContext.getString(R.string.story_b1_u10_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_11_unit_image_url_sub), uContext.getString(R.string.b1_u_11_unit_audio_url_sub), uContext.getString(R.string.b1_u_11_word_audio_url_sub), "شاه دخت رز و موجود", "Princess Rose and the Creature", uContext.getString(R.string.story_b1_u11_eng), uContext.getString(R.string.story_b1_u11_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_12_unit_image_url_sub), uContext.getString(R.string.b1_u_12_unit_audio_url_sub), uContext.getString(R.string.b1_u_12_word_audio_url_sub), "هنرمند دیوانه", "the Crazy Artist", uContext.getString(R.string.story_b1_u12_eng), uContext.getString(R.string.story_b1_u12_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_13_unit_image_url_sub), uContext.getString(R.string.b1_u_13_unit_audio_url_sub), uContext.getString(R.string.b1_u_13_word_audio_url_sub), "کشاورز و گربه ها", "the Farmer and the Cats", uContext.getString(R.string.story_b1_u13_eng), uContext.getString(R.string.story_b1_u13_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_14_unit_image_url_sub), uContext.getString(R.string.b1_u_14_unit_audio_url_sub), uContext.getString(R.string.b1_u_14_word_audio_url_sub), "یک کتاب جادویی", "A Magical Book", uContext.getString(R.string.story_b1_u14_eng), uContext.getString(R.string.story_b1_u14_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_15_unit_image_url_sub), uContext.getString(R.string.b1_u_15_unit_audio_url_sub), uContext.getString(R.string.b1_u_15_word_audio_url_sub), "مسابقه بزرگ", "The Big Race", uContext.getString(R.string.story_b1_u15_eng), uContext.getString(R.string.story_b1_u15_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_16_unit_image_url_sub), uContext.getString(R.string.b1_u_16_unit_audio_url_sub), uContext.getString(R.string.b1_u_16_word_audio_url_sub), "طلاهای آدامز کانتی", "Adams County’s Gold", uContext.getString(R.string.story_b1_u16_eng), uContext.getString(R.string.story_b1_u16_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_17_unit_image_url_sub), uContext.getString(R.string.b1_u_17_unit_audio_url_sub), uContext.getString(R.string.b1_u_17_word_audio_url_sub), "مسابقه برای آب", "The Race For Water", uContext.getString(R.string.story_b1_u17_eng), uContext.getString(R.string.story_b1_u17_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_18_unit_image_url_sub), uContext.getString(R.string.b1_u_18_unit_audio_url_sub), uContext.getString(R.string.b1_u_18_word_audio_url_sub), "مرغ سرخ کوچک", "The Little Red Chicken", uContext.getString(R.string.story_b1_u18_eng), uContext.getString(R.string.story_b1_u18_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_19_unit_image_url_sub), uContext.getString(R.string.b1_u_19_unit_audio_url_sub), uContext.getString(R.string.b1_u_19_word_audio_url_sub), "کشتی خراب شده", "Shipwrecked", uContext.getString(R.string.story_b1_u19_eng), uContext.getString(R.string.story_b1_u19_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_20_unit_image_url_sub), uContext.getString(R.string.b1_u_20_unit_audio_url_sub), uContext.getString(R.string.b1_u_20_word_audio_url_sub), "هفت شهر از طلا", "The Seven Cities Of Gold", uContext.getString(R.string.story_b1_u20_eng), uContext.getString(R.string.story_b1_u20_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_21_unit_image_url_sub), uContext.getString(R.string.b1_u_21_unit_audio_url_sub), uContext.getString(R.string.b1_u_21_word_audio_url_sub), "کتی", "Katy", uContext.getString(R.string.story_b1_u21_eng), uContext.getString(R.string.story_b1_u21_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_22_unit_image_url_sub), uContext.getString(R.string.b1_u_22_unit_audio_url_sub), uContext.getString(R.string.b1_u_22_word_audio_url_sub), "جایزه ای بهتر", "A Better Reward", uContext.getString(R.string.story_b1_u22_eng), uContext.getString(R.string.story_b1_u22_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_23_unit_image_url_sub), uContext.getString(R.string.b1_u_23_unit_audio_url_sub), uContext.getString(R.string.b1_u_23_word_audio_url_sub), "اردوگاه", "The Camp", uContext.getString(R.string.story_b1_u23_eng), uContext.getString(R.string.story_b1_u23_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_24_unit_image_url_sub), uContext.getString(R.string.b1_u_24_unit_audio_url_sub), uContext.getString(R.string.b1_u_24_word_audio_url_sub), "یک دوستی مستحکم", "A Strong Friendship", uContext.getString(R.string.story_b1_u24_eng), uContext.getString(R.string.story_b1_u24_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_25_unit_image_url_sub), uContext.getString(R.string.b1_u_25_unit_audio_url_sub), uContext.getString(R.string.b1_u_25_word_audio_url_sub), "گودال جویی", "Joe’s Pound", uContext.getString(R.string.story_b1_u25_eng), uContext.getString(R.string.story_b1_u25_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_26_unit_image_url_sub), uContext.getString(R.string.b1_u_26_unit_audio_url_sub), uContext.getString(R.string.b1_u_26_word_audio_url_sub), "آرچیو و الاغش", "Archive And His Donkey", uContext.getString(R.string.story_b1_u26_eng), uContext.getString(R.string.story_b1_u26_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_27_unit_image_url_sub), uContext.getString(R.string.b1_u_27_unit_audio_url_sub), uContext.getString(R.string.b1_u_27_word_audio_url_sub), "عنکبوت و روباه", "The Spider And The Bird", uContext.getString(R.string.story_b1_u27_eng), uContext.getString(R.string.story_b1_u27_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_28_unit_image_url_sub), uContext.getString(R.string.b1_u_28_unit_audio_url_sub), uContext.getString(R.string.b1_u_28_word_audio_url_sub), "مهمانی", "The Party", uContext.getString(R.string.story_b1_u28_eng), uContext.getString(R.string.story_b1_u28_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_29_unit_image_url_sub), uContext.getString(R.string.b1_u_29_unit_audio_url_sub), uContext.getString(R.string.b1_u_29_word_audio_url_sub), "چگونه دنیا نور گرفت", "How The World Got Light", uContext.getString(R.string.story_b1_u29_eng), uContext.getString(R.string.story_b1_u29_persian));
        insertUnitData(db, uContext.getString(R.string.b1_u_30_unit_image_url_sub), uContext.getString(R.string.b1_u_30_unit_audio_url_sub), uContext.getString(R.string.b1_u_30_word_audio_url_sub), "گربه ها و راز ها", "Cats And Secrets", uContext.getString(R.string.story_b1_u30_eng), uContext.getString(R.string.story_b1_u30_persian));
    }

    private void insertUnitData(SQLiteDatabase db, String unitImg,
                                String unitAudio, String completeWordAudio,
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
