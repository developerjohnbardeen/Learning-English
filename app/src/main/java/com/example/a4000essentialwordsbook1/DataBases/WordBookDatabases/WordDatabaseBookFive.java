package com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a4000essentialwordsbook1.R;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.CreateTables.BluePrints.WordBluePrint.CreateTablesBluePrint;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.CreateTables.InsertTablesValues;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.CreateTables.TablesNote;

public class WordDatabaseBookFive extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String BOOK_ONE_WORD_DATABASE = TablesNote.WORD_DATABASE_BOOK_5;
    private final Context dbContext;
    private final InsertTablesValues insert = new InsertTablesValues();


    public WordDatabaseBookFive(Context context){
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
    public void apple(){
        /*
        <string name ="afraid">afraid</string> <string name ="afraid_pho">[əˈfreid] adj.</string> <string name ="afraid_defi">When someone is afraid, they feel fear.</string> <string name ="afraid_exmpl"> The woman was afraid of what she saw.</string>
    <string name ="agree">agree</string> <string name ="agree_pho">[əˈɡriː] v.</string> <string name ="agree_defi">To agree is to say “yes” or to think the same way.</string> <string name ="agree_exmpl"> A: The food is very good in that restaurant. B: I agree with you.</string>
    <string name ="angry">angry</string> <string name ="angry_pho">[ˈæŋɡrɪ] adj.</string> <string name ="angry_defi">When someone is angry, they may want to speak loudly or fight.</string> <string name ="angry_exmpl"> She didn’t do her homework, so her father is angry.</string>
    <string name ="arrive">arrive</string> <string name ="arrive_pho">[əˈraɪv] v.</string> <string name ="arrive_defi">To arrive is to get to or reach some place.</string> <string name ="arrive_exmpl"> The bus always arrives at the corner of my street at 4:00.</string>
    <string name ="attack">attack</string> <string name ="attack_pho">[əˈtæk] v.</string> <string name ="attack_defi">To attack is to try to fight or to hurt.</string> <string name ="attack_exmpl"> The man with the sword attacked the other man first.</string>
    <string name ="bottom">bottom</string> <string name ="bottom_pho">[ˈbɒtəm] n.</string> <string name ="bottom_defi">The bottom is the lowest part.</string> <string name ="bottom_exmpl"> The bottom of my shoe has a hole in it.</string>
    <string name ="clever">clever</string> <string name ="clever_pho">[ˈklɛv ər] adj.</string> <string name ="clever_defi">When someone is clever, they can solve a hard puzzle or problem.</string> <string name ="clever_exmpl"> The clever boy thought of a good idea.</string>
    <string name ="cruel">cruel</string> <string name ="cruel_pho">[ˈkruːəl] adj.</string> <string name ="cruel_defi">When someone is cruel, they do bad things to hurt others.</string> <string name ="cruel_exmpl"> The cruel man yelled at his sister.</string>
    <string name ="finallyfinally_1">finally</string> <string name ="finally_pho">[ˈfaɪnəlɪ] adv.</string> <string name ="finally_defi">If something happens finally, it happens after a longtime or at the end.</string> <string name ="finally_exmpl"> He finally crossed the finish line after five hours of running.</string>
    <string name ="hide">hide</string> <string name ="hide_pho">[haɪd] v.</string> <string name ="hide_defi">To hide is to try not to let others see you.</string> <string name ="hide_exmpl"> The other children will hide while you count to 100.</string>
    <string name ="hunt">hunt</string> <string name ="hunt_pho">[hʌnt] v.</string> <string name ="hunt_defi">To hunt is to look for or search for an animal to kill.</string> <string name ="hunt_exmpl"> Long ago, people hunted with bows and arrows.</string>
    <string name ="lot">lot</string> <string name ="lot_pho">[lɒt] n.</string> <string name ="lot_defi">A lot means a large number or amount of people, animals, things, etc.</string> <string name ="lot_exmpl"> There are a lot of apples in the basket.</string>
    <string name ="middle">middle</string> <string name ="middle_pho">[mɪdl] n.</string> <string name ="middle_defi">The middle of something is the center or halfway point.</string> <string name ="middle_exmpl"> The Canadian flag has a maple leaf in the middle of it.</string>
    <string name ="moment">moment</string> <string name ="moment_pho">[ˈmoʊmənt] n.</string> <string name ="moment_defi">A moment is a second or a very short time.</string> <string name ="moment_exmpl"> I was only a few moments late for the meeting.</string>
    <string name ="pleased">pleased</string> <string name ="pleased_pho">[pli:zd] adj.</string> <string name ="pleased_defi">When someone is pleased, they are happy.</string> <string name ="pleased_exmpl"> She was pleased with the phone call she received.</string>
    <string name ="promise">promise</string> <string name ="promise_pho">[ˈprɒmɪs] v.</string> <string name ="promise_defi">To promise is to say you will do something for sure.</string> <string name ="promise_exmpl"> He promised to return my key by tomorrow.</string>
    <string name ="reply">reply</string> <string name ="reply_pho">[rɪˈplaɪ] v.</string> <string name ="reply_defi">To reply is to give an answer or say back to someone.</string> <string name ="reply_exmpl"> She asked him what time his meeting was. He replied, “at three.”</string>
    <string name ="safe">safe</string> <string name ="safe_pho">[seɪf] adj.</string> <string name ="safe_defi">When a person is safe, they are not in danger.</string> <string name ="safe_exmpl"> Put on your seat belt in the car to be safe.</string>
    <string name ="trick">trick</string> <string name ="trick_pho">[trɪk] n.</string> <string name ="trick_defi">A trick is something you do to fool another person.</string> <string name ="trick_exmpl"> His card trick really surprised us.</string>
    <string name ="well">well</string> <string name ="well_pho">[wɛl] adv.</string> <string name ="well_defi">You use well to say that something was done in a good way.</string> <string name ="well_exmpl"> The couple can dance quite well.</string>

    <string name ="afraid_translate">ترسیده</string> <string name ="afraid_dfntn_trnslt">وقتی کسی ترسیده احساس ترس می کند.</string> <string name ="afraid_exmpl_tran">زن از آنچه دیده بود ترسیده بود.</string>
    <string name ="agree_translate">موافق بودن</string> <string name ="agree_dfntn_trnslt">موافقت این است که "بله" بگویید یا همان فکر را داشته باشید.</string> <string name ="agree_exmpl_tran">الف: غذا در آن رستوران بسیار خوب است.  ب: من با شما موافقم.</string>
    <string name ="angry_translate">عصبانی</string> <string name ="angry_dfntn_trnslt">وقتی کسی عصبانی است ، ممکن است بخواهد با صدای بلند صحبت کند یا دعوا کند.</string> <string name ="angry_exmpl_tran">او تکالیف خود را انجام نداد ، بنابراین پدرش عصبانی است.</string>
    <string name ="arrive_translate">رسیدن</string> <string name ="arrive_dfntn_trnslt">رسیدن یعنی دستیابی به مکانی.</string> <string name ="arrive_exmpl_tran">اتوبوس همیشه ساعت 4:00 به گوشه خیابان من می رسد.</string>
    <string name ="attack_translate">حمله کردن</string> <string name ="attack_dfntn_trnslt">حمله ، تلاش برای جنگیدن یا صدمه زدن است.</string> <string name ="attack_exmpl_tran">آن مرد با شمشیر ابتدا به مرد دیگر حمله کرد.</string>
    <string name ="bottom_translate">کف</string> <string name ="bottom_dfntn_trnslt">کف پایین ترین قسمت است.</string> <string name ="bottom_exmpl_tran">پایین کفش من سوراخی دارد.</string>
    <string name ="clever_translate">زیرک</string> <string name ="clever_dfntn_trnslt">وقتی کسی زیرک است ، می تواند یک معما یا مشکل سخت را حل کند.</string> <string name ="clever_exmpl_tran"> پسر باهوش فکر خوبی کرد.</string>
    <string name ="cruel_translate">بی رحم</string> <string name ="cruel_dfntn_trnslt">وقتی کسی بی رحم است ، کارهای بدی انجام می دهد تا دیگران را آزار دهد.</string> <string name ="cruel_exmpl_tran">مرد بیرحم سر خواهرش فریاد زد.</string>
    <string name ="finally_translate">سرانجام </string> <string name ="finally_dfntn_trnslt">اگر چیزی سرانجام اتفاقی بیفتد ، پس از مدتها یا پایان آن اتفاق می افتد.</string> <string name ="finally_exmpl_tran">او سرانجام پس از پنج ساعت دویدن از خط پایان عبور کرد.</string>
    <string name ="hide_translate">پنهان کردن</string> <string name ="hide_dfntn_trnslt">پنهان کردن این است که سعی کنید اجازه ندهید دیگران شما را ببینند.</string> <string name ="hide_exmpl_tran">بچه های دیگر پنهان می شوند در حالی که شما تا 100 می شمارید.</string>
    <string name ="hunt_translate">شکار کردن</string> <string name ="hunt_dfntn_trnslt">شکار جستجوی حیوان برای کشتن است.</string> <string name ="hunt_exmpl_tran">مدت ها پیش ، مردم با تیر و کمان شکار می کردند.</string>
    <string name ="lot_translate">مقدار زیادی</string> <string name ="lot_dfntn_trnslt">مقدار زیاد به معنی تعداد یا تعداد زیادی از افراد ، حیوانات ، اشیا و غیره است.</string> <string name ="lot_exmpl_tran">تعداد زیادی سیب در سبد وجود دارد.</string>
    <string name ="middle_translate">وسط</string> <string name ="middle_dfntn_trnslt">وسط چیزی مرکز یا نیمه راه ان است.</string> <string name ="middle_exmpl_tran">- »پرچم کانادا دارای یک برگ افرا در وسط آن است.</string>
    <string name ="moment_translate">لحظه</string> <string name ="moment_dfntn_trnslt">یک لحظه یک ثانیه است یا یک زمان بسیار کوتاه.</string> <string name ="moment_exmpl_tran">من فقط چند لحظه برای جلسه تأخیر داشتم.</string>
    <string name ="pleased_translate">راضی</string> <string name ="pleased_dfntn_trnslt">وقتی کسی راضی باشد خوشحال می شود.</string> <string name ="pleased_exmpl_tran">او از تماس تلفنی که دریافت کرد خوشحال شد.</string>
    <string name ="promise_translate">قول  دادن</string> <string name ="promise_dfntn_trnslt">قول دادن این است که بگویید مطمئناً کاری خواهید کرد.</string> <string name ="promise_exmpl_tran">او قول داد که تا فردا کلید من را پس خواهد داد.</string>
    <string name ="reply_translate">پاسخ دادن</string> <string name ="reply_dfntn_trnslt">پاسخ دادن جواب دادن به کسی است.</string> <string name ="reply_exmpl_tran">او از او پرسید که ملاقاتش ساعت چند است؟ او پاسخ داد ، "در سه."</string>
    <string name ="safe_translate">ایمن</string> <string name ="safe_dfntn_trnslt">وقتی شخصی در امنیت است ، خطری در علیه او نیست.</string> <string name ="safe_exmpl_tran">کمربند ایمنی خود را در ماشین بزنید تا ایمن باشید.</string>
    <string name ="trick_translate">ترفند</string> <string name ="trick_dfntn_trnslt">ترفند کاری است که شما برای فریب شخص دیگری انجام می دهید.</string> <string name ="trick_exmpl_tran">حقه بازی او با  کارت واقعاً ما را متعجب کرد.</string>
    <string name ="well_translate">به خوبی</string> <string name ="well_dfntn_trnslt">شما از کلمه ی به خوبی استفاده می کنید تا بگویید کاری به طرز خوبی انجام شده است.</string> <string name ="well_exmpl_tran">زوجین می توانند کاملاً خوب برقصند.</string>

         */
    }

}


