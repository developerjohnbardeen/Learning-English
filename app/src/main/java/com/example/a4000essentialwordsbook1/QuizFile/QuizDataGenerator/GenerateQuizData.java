package com.example.a4000essentialwordsbook1.QuizFile.QuizDataGenerator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;
import com.example.a4000essentialwordsbook1.SelectedUnitTab.WordList.WordDatabase.WordDatabaseOpenHelper;

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
    private WordDatabaseOpenHelper wordSQLiteDatabase;

    public GenerateQuizData(Context context, int columnId){
        this.columnId = columnId;
        this.context = context;
    }

    public void quizDataGenerator(String tvWord, String optionWord){



        wordSQLiteDatabase = new WordDatabaseOpenHelper(context);
        SQLiteDatabase db = wordSQLiteDatabase.getReadableDatabase();

        Cursor uCursor = db.query(DB_NOTES.WORD_TABLE,
                new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD, DB_NOTES.TRANSLATE_WORD, DB_NOTES.WORD_IMG, DB_NOTES.HARD_FLAG},
                DB_NOTES.WORD_ID + " = ?", new String[]{Integer.toString(columnId)},
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
        wordSQLiteDatabase = new WordDatabaseOpenHelper(context);
        SQLiteDatabase db = wordSQLiteDatabase.getReadableDatabase();
        Cursor dumCursor = null;

        for (int i = 0 ; i < 3 ; i ++) {
            int id = list[i];
            dumCursor = db.query(DB_NOTES.WORD_TABLE,
                    new String[]{DB_NOTES.WORD_ID, DB_NOTES.WORD, DB_NOTES.TRANSLATE_WORD}
                    , DB_NOTES.WORD_ID + " = ? ", new String[]{Integer.toString(id)},
                    null, null, null);
            if (dumCursor != null && dumCursor.getCount() != 0) {
                while (dumCursor.moveToNext()) {
                    dWord = dumCursor.getString(dumCursor.getColumnIndex(optionWord));
                    wordsOptionList[i] = dWord;
                }
            }
        }
        dumCursor.close();
        db.close();

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
