package com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.CreateTables;

import com.example.a4000essentialwordsbook1.StringNote.DB_NOTES.DB_NOTES;

public class TablesNote {

    //word Database
    public static final String WORD_TABLE_1 = DB_NOTES.WORD_TABLE_1;
    public static final String WORD_TABLE_2 = DB_NOTES.WORD_TABLE_2;
    public static final String WORD_TABLE_3 = DB_NOTES.WORD_TABLE_3;
    public static final String WORD_TABLE_4 = DB_NOTES.WORD_TABLE_4;
    public static final String WORD_TABLE_5 = DB_NOTES.WORD_TABLE_5;
    public static final String WORD_TABLE_6 = DB_NOTES.WORD_TABLE_6;
    public static final String WORD_TABLE_7 = DB_NOTES.WORD_TABLE_7;
    public static final String WORD_TABLE_8 = DB_NOTES.WORD_TABLE_8;
    public static final String WORD_TABLE_9 = DB_NOTES.WORD_TABLE_9;
    public static final String WORD_TABLE_10 = DB_NOTES.WORD_TABLE_10;
    public static final String WORD_TABLE_11 = DB_NOTES.WORD_TABLE_11;
    public static final String WORD_TABLE_12 = DB_NOTES.WORD_TABLE_12;
    public static final String WORD_TABLE_13 = DB_NOTES.WORD_TABLE_13;
    public static final String WORD_TABLE_14 = DB_NOTES.WORD_TABLE_14;
    public static final String WORD_TABLE_15 = DB_NOTES.WORD_TABLE_15;
    public static final String WORD_TABLE_16 = DB_NOTES.WORD_TABLE_16;
    public static final String WORD_TABLE_17 = DB_NOTES.WORD_TABLE_17;
    public static final String WORD_TABLE_18 = DB_NOTES.WORD_TABLE_18;
    public static final String WORD_TABLE_19 = DB_NOTES.WORD_TABLE_19;
    public static final String WORD_TABLE_20 = DB_NOTES.WORD_TABLE_20;
    public static final String WORD_TABLE_21 = DB_NOTES.WORD_TABLE_21;
    public static final String WORD_TABLE_22 = DB_NOTES.WORD_TABLE_22;
    public static final String WORD_TABLE_23 = DB_NOTES.WORD_TABLE_23;
    public static final String WORD_TABLE_24 = DB_NOTES.WORD_TABLE_24;
    public static final String WORD_TABLE_25 = DB_NOTES.WORD_TABLE_25;
    public static final String WORD_TABLE_26 = DB_NOTES.WORD_TABLE_26;
    public static final String WORD_TABLE_27 = DB_NOTES.WORD_TABLE_27;
    public static final String WORD_TABLE_28 = DB_NOTES.WORD_TABLE_28;
    public static final String WORD_TABLE_29 = DB_NOTES.WORD_TABLE_29;
    public static final String WORD_TABLE_30 = DB_NOTES.WORD_TABLE_30;

    public static final String WORD_DATABASE_BOOK_1 = "WORD_DATABASE_BOOK_1";
    public static final String WORD_DATABASE_BOOK_2 = "WORD_DATABASE_BOOK_2";
    public static final String WORD_DATABASE_BOOK_3 = "WORD_DATABASE_BOOK_3";
    public static final String WORD_DATABASE_BOOK_4 = "WORD_DATABASE_BOOK_4";
    public static final String WORD_DATABASE_BOOK_5 = "WORD_DATABASE_BOOK_5";
    public static final String WORD_DATABASE_BOOK_6 = "WORD_DATABASE_BOOK_6";

    public static final String WORD_ID = DB_NOTES.WORD_ID;
    public static final String WORD_IMG = DB_NOTES.WORD_IMG;
    public static final String WORD = DB_NOTES.WORD;
    public static final String PHONETIC_WORD = DB_NOTES.PHONETIC_WORD;
    public static final String TRANSLATE_WORD = DB_NOTES.TRANSLATE_WORD;
    public static final String DEFINITION_WORD = DB_NOTES.DEFINITION_WORD;
    public static final String DEFINITION_TRANSLATE_WORD = DB_NOTES.DEFINITION_TRANSLATE_WORD;
    public static final String EXAMPLE_WORD = DB_NOTES.EXAMPLE_WORD;
    public static final String EXAMPLE_TRANSLATE_WORD = DB_NOTES.EXAMPLE_TRANSLATE_WORD;
    public static final String HARD_FLAG = DB_NOTES.HARD_FLAG;
    public static final String EASY_FLAG = DB_NOTES.EASY_FLAG;
    public static final String AUDIO_WORD = DB_NOTES.AUDIO_WORD;
    public static final String BOOK_NUMBER = DB_NOTES.BOOK_NUMBER;
    public static final String UNIT_NUMBER = DB_NOTES.UNIT_NUMBER;


    //Unit_Databases
    public static final String UNIT_DATABASE_BOOK_1 = "UNIT_DATABASE_BOOK_1";
    public static final String UNIT_DATABASE_BOOK_2 = "UNIT_DATABASE_BOOK_2";
    public static final String UNIT_DATABASE_BOOK_3 = "UNIT_DATABASE_BOOK_3";
    public static final String UNIT_DATABASE_BOOK_4 = "UNIT_DATABASE_BOOK_4";
    public static final String UNIT_DATABASE_BOOK_5 = "UNIT_DATABASE_BOOK_5";
    public static final String UNIT_DATABASE_BOOK_6 = "UNIT_DATABASE_BOOK_6";



    public static String CREATE_WORD_TABLE_(int tableNum){
        return "CREATE TABLE " + "WORD_TABLE_" + tableNum + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }


    public static String CREATE_WORD_TABLE_1(){
        return "CREATE TABLE " + WORD_TABLE_1 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_1 = "CREATE TABLE " + WORD_TABLE_1 + " (" +
            WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            WORD_IMG + " INTEGER, " +
            AUDIO_WORD + " INTEGER, " +
            HARD_FLAG + " INTEGER, " +
            EASY_FLAG + " INTEGER, " +
            BOOK_NUMBER + " INTEGER, " +
            UNIT_NUMBER + " INTEGER, " +
            WORD + " TEXT, " +
            PHONETIC_WORD + " TEXT, " +
            TRANSLATE_WORD + " TEXT, " +
            DEFINITION_WORD + " TEXT, " +
            DEFINITION_TRANSLATE_WORD + " TEXT, " +
            EXAMPLE_WORD + " TEXT, " +
            EXAMPLE_TRANSLATE_WORD + " TEXT);";


    public static String CREATE_WORD_TABLE_2(){
        return "CREATE TABLE " + WORD_TABLE_2 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_2 = "CREATE TABLE " + WORD_TABLE_2 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";


    public static String CREATE_WORD_TABLE_3(){
        return "CREATE TABLE " + WORD_TABLE_3 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_3 = "CREATE TABLE " + WORD_TABLE_3 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";


    public static String CREATE_WORD_TABLE_4(){
        return "CREATE TABLE " + WORD_TABLE_4 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_4 = "CREATE TABLE " + WORD_TABLE_4 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



        public static String CREATE_WORD_TABLE_5(){
        return "CREATE TABLE " + WORD_TABLE_5 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_5 = "CREATE TABLE " + WORD_TABLE_5 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



        public static String CREATE_WORD_TABLE_6(){
        return "CREATE TABLE " + WORD_TABLE_6 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_6 = "CREATE TABLE " + WORD_TABLE_6 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



        public static String CREATE_WORD_TABLE_7(){
        return "CREATE TABLE " + WORD_TABLE_7 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_7 = "CREATE TABLE " + WORD_TABLE_7 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



        public static String CREATE_WORD_TABLE_8(){
        return "CREATE TABLE " + WORD_TABLE_8 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_8 = "CREATE TABLE " + WORD_TABLE_8 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



        public static String CREATE_WORD_TABLE_9(){
        return "CREATE TABLE " + WORD_TABLE_9 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_9 = "CREATE TABLE " + WORD_TABLE_9 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



        public static String CREATE_WORD_TABLE_10(){
        return "CREATE TABLE " + WORD_TABLE_10 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_10 = "CREATE TABLE " + WORD_TABLE_10 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



        public static String CREATE_WORD_TABLE_11(){
        return "CREATE TABLE " + WORD_TABLE_11 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_11 = "CREATE TABLE " + WORD_TABLE_11 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



        public static String CREATE_WORD_TABLE_12(){
        return "CREATE TABLE " + WORD_TABLE_12 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_12 = "CREATE TABLE " + WORD_TABLE_12 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";




        public static String CREATE_WORD_TABLE_13(){
        return "CREATE TABLE " + WORD_TABLE_13 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static final String CREATE_WORD_TABLE_13 =  "CREATE TABLE " + WORD_TABLE_13 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";


    public static String CREATE_WORD_TABLE_14(){
        return "CREATE TABLE " + WORD_TABLE_14 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_14 =  "CREATE TABLE " + WORD_TABLE_14 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";


        public static String CREATE_WORD_TABLE_15(){
        return "CREATE TABLE " + WORD_TABLE_15 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_15 = "CREATE TABLE " + WORD_TABLE_15 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



        public static String CREATE_WORD_TABLE_16(){
        return "CREATE TABLE " + WORD_TABLE_16 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_16 =  "CREATE TABLE " + WORD_TABLE_16 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



        public static String CREATE_WORD_TABLE_17(){
        return "CREATE TABLE " + WORD_TABLE_17 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_17 = "CREATE TABLE " + WORD_TABLE_17 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



        public static String CREATE_WORD_TABLE_18(){
        return "CREATE TABLE " + WORD_TABLE_18 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_18 =  "CREATE TABLE " + WORD_TABLE_18 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



        public static String CREATE_WORD_TABLE_19(){
        return "CREATE TABLE " + WORD_TABLE_19 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_19 = "CREATE TABLE " + WORD_TABLE_19 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



        public static String CREATE_WORD_TABLE_20(){
        return "CREATE TABLE " + WORD_TABLE_20 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_20 = "CREATE TABLE " + WORD_TABLE_20 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";




        public static String CREATE_WORD_TABLE_21(){
        return "CREATE TABLE " + WORD_TABLE_21 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_21 =  "CREATE TABLE " + WORD_TABLE_21 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";


    public static String CREATE_WORD_TABLE_22(){
        return "CREATE TABLE " + WORD_TABLE_22 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_22 = "CREATE TABLE " + WORD_TABLE_22 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";


    public static String CREATE_WORD_TABLE_23(){
        return "CREATE TABLE " + WORD_TABLE_23 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_23 = "CREATE TABLE " + WORD_TABLE_23 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



    public static String CREATE_WORD_TABLE_24(){
        return "CREATE TABLE " + WORD_TABLE_24 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_24 =  "CREATE TABLE " + WORD_TABLE_24 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";


    public static String CREATE_WORD_TABLE_25(){
        return "CREATE TABLE " + WORD_TABLE_25 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_25 =  "CREATE TABLE " + WORD_TABLE_25 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";




    public static String CREATE_WORD_TABLE_26(){
        return "CREATE TABLE " + WORD_TABLE_26 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_26 = "CREATE TABLE " + WORD_TABLE_26 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



    public static String CREATE_WORD_TABLE_27(){
        return "CREATE TABLE " + WORD_TABLE_27 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_27 = "CREATE TABLE " + WORD_TABLE_27 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";



        public static String CREATE_WORD_TABLE_28(){
        return "CREATE TABLE " + WORD_TABLE_28 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
        }
    public static String CREATE_WORD_TABLE_28 = "CREATE TABLE " + WORD_TABLE_28 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";


        public static String CREATE_WORD_TABLE_29(){
        return "CREATE TABLE " + WORD_TABLE_29 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_29 = "CREATE TABLE " + WORD_TABLE_29 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";


        public static String CREATE_WORD_TABLE_30(){
        return "CREATE TABLE " + WORD_TABLE_30 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
    }
    public static String CREATE_WORD_TABLE_30 = "CREATE TABLE " + WORD_TABLE_30 + " (" +
                WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORD_IMG + " INTEGER, " +
                AUDIO_WORD + " INTEGER, " +
                HARD_FLAG + " INTEGER, " +
                EASY_FLAG + " INTEGER, " +
                BOOK_NUMBER + " INTEGER, " +
                UNIT_NUMBER + " INTEGER, " +
                WORD + " TEXT, " +
                PHONETIC_WORD + " TEXT, " +
                TRANSLATE_WORD + " TEXT, " +
                DEFINITION_WORD + " TEXT, " +
                DEFINITION_TRANSLATE_WORD + " TEXT, " +
                EXAMPLE_WORD + " TEXT, " +
                EXAMPLE_TRANSLATE_WORD + " TEXT);";
}
