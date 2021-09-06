package com.example.a4000essentialwordsbook1.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class WordModel implements Parcelable {
    private int id;
    private int wordImage;
    private int audio;
    private int hardFlag, easyFlag;
    private int bookNum, unitNum;
    private int markedClickedImg;
    private int wrdStart, wrdEnd;
    private int defStart, defEnd;
    private int exmplStart, exmplEnd;
    private String word, phonetic, translateWord;
    private String definition, translateDef;
    private String example, translateExmpl;




    public WordModel(int id, int wordImage, String word, String phonetic,
                     String translateWord, String definition,
                     String example, String translateExmpl, int hardFlag, int easyFlag,
                     int wrdStart, int wrdEnd, int defStart, int defEnd, int exmplStart, int exmplEnd,
                     int bookNum, int unitNum){
        this.id = id;
        this.wordImage = wordImage;
        this.word = word;
        this.phonetic = phonetic;
        this.translateWord = translateWord;
        this.definition = definition;
        this.example = example;
        this.translateExmpl = translateExmpl;
        this.hardFlag = hardFlag;
        this.easyFlag = easyFlag;
        this.wrdStart = wrdStart;
        this.wrdEnd = wrdEnd;
        this.defStart = defStart;
        this.defEnd = defEnd;
        this.exmplStart = exmplStart;
        this.exmplEnd = exmplEnd;
        this.bookNum = bookNum;
        this.unitNum = unitNum;
    }
    public WordModel(){
    }

    protected WordModel(Parcel in) {
        id = in.readInt();
        wordImage = in.readInt();
        audio = in.readInt();
        hardFlag = in.readInt();
        easyFlag = in.readInt();
        wrdStart = in.readInt();
        wrdEnd = in.readInt();
        defStart = in.readInt();
        defEnd = in.readInt();
        exmplStart  = in.readInt();
        exmplEnd = in.readInt();
        bookNum = in.readInt();
        unitNum = in.readInt();
        markedClickedImg = in.readInt();
        word = in.readString();
        phonetic = in.readString();
        translateWord = in.readString();
        definition = in.readString();
        translateDef = in.readString();
        example = in.readString();
        translateExmpl = in.readString();
    }

    public static final Creator<WordModel> CREATOR = new Creator<WordModel>() {
        @Override
        public WordModel createFromParcel(Parcel in) {
            return new WordModel(in);
        }

        @Override
        public WordModel[] newArray(int size) {
            return new WordModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAudio(){return audio;}

    public void setAudio(int audio){this.audio = audio;}

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getTranslateWord() {
        return translateWord;
    }

    public void setTranslateWord(String translateWord) {
        this.translateWord = translateWord;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getTranslateExmpl() {
        return translateExmpl;
    }

    public void setTranslateExmpl(String translateExmpl) {
        this.translateExmpl = translateExmpl;
    }

    public int getWordImage() {
        return wordImage;
    }

    public void setWordImage(int wordImage) {
        this.wordImage = wordImage;
    }

    public int getHardFlag() {
        return hardFlag;
    }

    public void setHardFlag(int hardFlag) {
        this.hardFlag = hardFlag;
    }

    public int getEasyFlag() {
        return easyFlag;
    }

    public void setEasyFlag(int easyFlag) {
        this.easyFlag = easyFlag;
    }

    public int getBookNum() {
        return bookNum;
    }

    public void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }

    public int getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(int unitNum) {
        this.unitNum = unitNum;
    }

    public int getWrdStart() {
        return wrdStart;
    }

    public void setWrdStart(int wrdStart) {
        this.wrdStart = wrdStart;
    }

    public int getWrdEnd() {
        return wrdEnd;
    }

    public void setWrdEnd(int wrdEnd) {
        this.wrdEnd = wrdEnd;
    }

    public int getDefStart() {
        return defStart;
    }

    public void setDefStart(int defStart) {
        this.defStart = defStart;
    }

    public int getDefEnd() {
        return defEnd;
    }

    public void setDefEnd(int defEnd) {
        this.defEnd = defEnd;
    }

    public int getExmplStart() {
        return exmplStart;
    }

    public void setExmplStart(int exmplStart) {
        this.exmplStart = exmplStart;
    }

    public int getExmplEnd() {
        return exmplEnd;
    }

    public void setExmplEnd(int exmplEnd) {
        this.exmplEnd = exmplEnd;
    }

    public void setMarkedClickedImg(int markedClickedImg) {
        this.markedClickedImg = markedClickedImg;
    }

    public int getMarkedClickedImg() {
        return markedClickedImg;
    }

    public String getTranslateDef() {
        return translateDef;
    }

    public void setTranslateDef(String translateDef) {
        this.translateDef = translateDef;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(wordImage);
        dest.writeInt(audio);
        dest.writeInt(hardFlag);
        dest.writeInt(easyFlag);
        dest.writeInt(wrdStart);
        dest.writeInt(wrdEnd);
        dest.writeInt(defStart);
        dest.writeInt(defEnd);
        dest.writeInt(exmplStart);
        dest.writeInt(exmplEnd);
        dest.writeInt(bookNum);
        dest.writeInt(unitNum);
        dest.writeInt(markedClickedImg);
        dest.writeString(word);
        dest.writeString(phonetic);
        dest.writeString(translateWord);
        dest.writeString(definition);
        dest.writeString(translateDef);
        dest.writeString(example);
        dest.writeString(translateExmpl);
    }
}
