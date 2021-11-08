package com.example.a4000essentialwordsbook1.QuizFile.QuizDataGenerator;

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
    private String answerWord;
    private String dWord;


    private final String[] wordsOptionList = new String[4];
    private final int dbNumber;
    private final int unitNumb;

    public GenerateQuizData(Context context, int columnId, int dbNumber, int unitNumb){
        this.columnId = columnId;
        this.context = context;
        this.dbNumber = dbNumber;
        this.unitNumb = unitNumb;
    }

    public void quizDataGenerator(String tvWord, String optionWord){



        //wordSQLiteDatabase = new WordDatabaseOpenHelper(context);
        SQLiteDatabase db = wordListDatabase(dbNumber).getReadableDatabase();

        Cursor uCursor = db.query(DB_NOTES.NEUTRAL_WORD_TABLE + unitNumb,
                new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD, DB_NOTES.TRANSLATE_WORD, DB_NOTES.WORD_IMG, DB_NOTES.HARD_FLAG},
                DB_NOTES.WORD_ID + " = ? ", new String[]{Integer.toString(columnId)},
                null, null, null);

        if (uCursor != null && uCursor.getCount() != 0) {
            while (uCursor.moveToNext()){
                mainWord = uCursor.getString(uCursor.getColumnIndex(tvWord));
                hardFlag = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.HARD_FLAG));
                wordsOptionList[3] = uCursor.getString(uCursor.getColumnIndex(optionWord));
                answerWord = uCursor.getString(uCursor.getColumnIndex(optionWord));
                mainImage = uCursor.getInt(uCursor.getColumnIndex(DB_NOTES.WORD_IMG));
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

    public String getMainWord() {
        return mainWord;
    }

    public String[] getWordsOptionList() {
        return wordsOptionList;
    }

    public int getMainImage() {
        return mainImage;
    }

    public String getAnswerWord() {
        return answerWord;
    }

    public int getHardFlag() {
        return hardFlag;
    }
}
