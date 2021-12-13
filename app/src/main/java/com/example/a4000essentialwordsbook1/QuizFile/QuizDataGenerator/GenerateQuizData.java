package com.example.a4000essentialwordsbook1.QuizFile.QuizDataGenerator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFive;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookFour;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookOne;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookSix;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookThree;
import com.example.a4000essentialwordsbook1.DataBases.WordBookDatabases.WordDatabaseBookTwo;
import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;

import java.util.concurrent.ThreadLocalRandom;

public class GenerateQuizData {
    private final Context context;
    private final int columnId;
    private int hardFlag;
    private int mainImage;
    private int[] randomIntList;
    private String mainWord;
    private String engMainWord;
    private String answerWord;
    private String dWord;
    private String imgUrl;
    private int id;
    private int easyFlag;
    private int bookNum, unitNum;
    private int wrdStart, wrdEnd;
    private int defStart, defEnd;
    private int exmplStart, exmplEnd;
    private String word, phonetic, translateWord;
    private String definition, translateDef;
    private String example, translateExmpl;
    private String addNote;

    private final String[] wordsOptionList = new String[4];
    private final int dbNumber;
    private final int unitNumb;

    public GenerateQuizData(Context context, int columnId, int dbNumber, int unitNumb){
        this.columnId = columnId;
        this.context = context;
        this.dbNumber = dbNumber;
        this.unitNumb = unitNumb;
    }
    @SuppressLint("Range")
    public void quizDataGenerator(String tvWord, String optionWord){



        //wordSQLiteDatabase = new WordDatabaseOpenHelper(context);
        SQLiteDatabase db = wordListDatabase(dbNumber).getReadableDatabase();

        Cursor uCursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitNumb,
                new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD, DB_NOTES.PHONETIC_WORD, DB_NOTES.TRANSLATE_WORD, DB_NOTES.WORD_IMG,
                        DB_NOTES.HARD_FLAG, DB_NOTES.EASY_FLAG, DB_NOTES.BOOK_NUMBER, DB_NOTES.UNIT_NUMBER,
                        DB_NOTES.WORD_START, DB_NOTES.WORD_END, DB_NOTES.DEF_START, DB_NOTES.DEF_END, DB_NOTES.EXMPL_START, DB_NOTES.EXMPL_END,
                        DB_NOTES.DEFINITION_WORD ,DB_NOTES.DEFINITION_TRANSLATE_WORD,
                        DB_NOTES.EXAMPLE_TRANSLATE_WORD, DB_NOTES.EXAMPLE_WORD , DB_NOTES.EXTRA_NOTE},
                DB_NOTES.WORD_ID + " = ? ", new String[]{Integer.toString(columnId)},
                null, null, null);

        if (uCursor != null && uCursor.getCount() != 0) {
            while (uCursor.moveToNext()){
                mainWord = uCursor.getString(uCursor.getColumnIndex(tvWord));
                engMainWord = uCursor.getString(uCursor.getColumnIndex(DB_NOTES.WORD));
                hardFlag = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.HARD_FLAG));
                wordsOptionList[3] = uCursor.getString(uCursor.getColumnIndex(optionWord));
                answerWord = uCursor.getString(uCursor.getColumnIndex(optionWord));
                imgUrl = uCursor.getString(uCursor.getColumnIndex(DB_NOTES.WORD_IMG));


                id = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.WORD_ID));
                easyFlag = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.EASY_FLAG));
                bookNum = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.BOOK_NUMBER));
                unitNum = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.UNIT_NUMBER));
                wrdStart = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.WORD_START));
                wrdEnd = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.WORD_END));
                defStart = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.DEF_START));
                defEnd = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.DEF_END));
                exmplStart = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.EXMPL_START));
                exmplEnd = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.EXMPL_END));
                word = uCursor.getString(uCursor.getColumnIndex(DB_NOTES.WORD));
                phonetic = uCursor.getString(uCursor.getColumnIndex(DB_NOTES.PHONETIC_WORD));
                translateWord = uCursor.getString(uCursor.getColumnIndex(DB_NOTES.TRANSLATE_WORD));
                definition = uCursor.getString(uCursor.getColumnIndex(DB_NOTES.DEFINITION_WORD));
                translateDef = uCursor.getString(uCursor.getColumnIndex(DB_NOTES.DEFINITION_TRANSLATE_WORD));
                example = uCursor.getString(uCursor.getColumnIndex(DB_NOTES.EXAMPLE_WORD));
                translateExmpl = uCursor.getString(uCursor.getColumnIndex(DB_NOTES.EXAMPLE_TRANSLATE_WORD));
                addNote = uCursor.getString(uCursor.getColumnIndex(DB_NOTES.EXTRA_NOTE));
            }
        }
        assert uCursor != null;
        uCursor.close();
        db.close();

        dumNumberGenerator(columnId);
        dumWordGenerator(randomIntList, optionWord);
    }

    private void dumNumberGenerator(int id){
        boolean flag = false;
        int listSize = 3;
        randomIntList = new int[listSize];
        int rand;

        for (int i = 0 ; i < listSize ; i++){
            rand = ThreadLocalRandom.current().nextInt(1, 21);
            for (int num : randomIntList){
                if ((num == rand) || (rand == id)){
                    i--;
                    flag = true;
                    break;
                }
            }
            if (!flag){
                randomIntList[i] = rand;
            }else {
                flag = false;
            }

        }
    }

    @SuppressLint("Range")
    private void dumWordGenerator(int[] list, String optionWord){
        //wordSQLiteDatabase = new WordDatabaseOpenHelper(context);
        SQLiteDatabase db = wordListDatabase(dbNumber).getReadableDatabase();
        Cursor dumCursor = null;

        for (int i = 0 ; i < 3 ; i ++) {
            int id = list[i];
            dumCursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitNumb,
                    new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD, DB_NOTES.TRANSLATE_WORD}
                    ,DB_NOTES.WORD_ID + " = ? ", new String[]{Integer.toString(id)},
                    null, null, null);

            if (dumCursor != null && dumCursor.getCount() != 0) {
                while (dumCursor.moveToNext()) {
                    dWord = dumCursor.getString(dumCursor.getColumnIndex(optionWord));
                    wordsOptionList[i] = dWord;
                }
            }
        }
        assert dumCursor != null;
        dumCursor.close();
        db.close();
    }
    private SQLiteOpenHelper wordListDatabase(int databaseNum){
        if (databaseNum == 1){
            return new WordDatabaseBookOne(context);
        }else if (databaseNum == 2){
            return new WordDatabaseBookTwo(context);
        }else if (databaseNum == 3){
            return new WordDatabaseBookThree(context);
        }else if (databaseNum == 4){
            return new WordDatabaseBookFour(context);
        }else if (databaseNum == 5){
            return new WordDatabaseBookFive(context);
        }else {
            return new WordDatabaseBookSix(context);
        }
    }

    public String getMainWord() {return mainWord;}
    public String getEngMainWord(){return engMainWord;}
    public String[] getWordsOptionList() {return wordsOptionList;}
    public int getMainImage() {return mainImage;}
    public String getImageUrl(){return imgUrl;}
    public String getAnswerWord() {return answerWord;}
    public int getHardFlag() {return hardFlag;}
    public String getAddNote() {return addNote;}
    public String getWord() {return word;}
    public String getPhonetic() {return phonetic;}
    public String getTranslateWord() {return translateWord;}
    public String getDefinition() {return definition;}
    public String getExample() {return example;}
    public String getTranslateExmpl() {return translateExmpl;}
    public String getTranslateDef() {return translateDef;}
    public int getId() {return id;}
    public int getBookNum() {return bookNum;}
    public int getUnitNum() {return unitNum;}
    public int getEasyFlag() {return easyFlag;}
    public int getWrdStart() {return wrdStart - 200;}
    public int getWrdEnd() {return wrdEnd - 200;}
    public int getDefStart() {return defStart - 200;}
    public int getDefEnd() {return defEnd - 200;}
    public int getExmplStart() {return exmplStart - 200;}
    public int getExmplEnd() {return exmplEnd - 200;}
}
