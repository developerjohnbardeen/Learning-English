package com.example.a4000essentialwordsbook1.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class WordModel implements Parcelable {
    private int id;
    private int wordImage;
    private String imgUri;
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
    private String addNote;
    private String correctWord;
    private String wrongWord;
    private String skippedWord;




    public WordModel(int id, int wordImage, String word, String phonetic,
                     String translateWord, String definition,
                     String example, String translateExmpl, String addNote,String correctWord,
                     String wrongWord, String skippedWord, int hardFlag, int easyFlag, int wrdStart, int wrdEnd,
                     int defStart, int defEnd, int exmplStart, int exmplEnd,
                     int bookNum, int unitNum){
        this.id = id;
        this.wordImage = wordImage;
        this.word = word;
        this.phonetic = phonetic;
        this.translateWord = translateWord;
        this.definition = definition;
        this.example = example;
        this.translateExmpl = translateExmpl;
        this.addNote = addNote;
        this.correctWord = correctWord;
        this.wrongWord = wrongWord;
        this.skippedWord = skippedWord;
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
        imgUri = in.readString();
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
        addNote = in.readString();
        correctWord = in.readString();
        wrongWord = in.readString();
        skippedWord = in.readString();
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


    // Getters
    public String getAddNote() {return addNote;}
    public int getId() {return id;}
    public int getAudio(){return audio;}
    public String getWord() {return word;}
    public String getPhonetic() {return phonetic;}
    public String getTranslateWord() {return translateWord;}
    public String getDefinition() {return definition;}
    public String getExample() {return example;}
    public String getTranslateExmpl() {return translateExmpl;}
    public int getWordImage() {return wordImage;}
    public int getHardFlag() {return hardFlag;}
    public int getEasyFlag() {return easyFlag;}
    public int getBookNum() {return bookNum;}
    public int getUnitNum() {return unitNum;}
    public int getWrdStart() {return wrdStart;}
    public int getWrdEnd() {return wrdEnd;}
    public int getDefStart() {return defStart;}
    public int getDefEnd() {return defEnd;}
    public int getExmplStart() {return exmplStart;}
    public int getExmplEnd() {return exmplEnd;}
    public String getCorrectWord() {return correctWord;}
    public String getWrongWord() {return wrongWord;}
    public String getSkippedWord() {return skippedWord;}
    public int getMarkedClickedImg() {return markedClickedImg;}
    public String getTranslateDef() {return translateDef;}
    public String getImgUri() {return imgUri;}



    // Setters
    public void setId(int id) {this.id = id;}
    public void setAudio(int audio){this.audio = audio;}
    public void setWord(String word) {this.word = word;}
    public void setPhonetic(String phonetic) {this.phonetic = phonetic;}
    public void setTranslateWord(String translateWord) {this.translateWord = translateWord;}
    public void setDefinition(String definition) {this.definition = definition;}
    public void setExample(String example) { this.example = example;}
    public void setTranslateExmpl(String translateExmpl) {this.translateExmpl = translateExmpl;}
    public void setWordImage(int wordImage) {this.wordImage = wordImage;}
    public void setHardFlag(int hardFlag) {this.hardFlag = hardFlag;}
    public void setEasyFlag(int easyFlag) {this.easyFlag = easyFlag;}
    public void setBookNum(int bookNum) {this.bookNum = bookNum;}
    public void setUnitNum(int unitNum) {this.unitNum = unitNum;}
    public void setWrdStart(int wrdStart) {this.wrdStart = wrdStart;}
    public void setWrdEnd(int wrdEnd) {this.wrdEnd = wrdEnd;}
    public void setDefStart(int defStart) {this.defStart = defStart;}
    public void setDefEnd(int defEnd) {this.defEnd = defEnd;}
    public void setExmplStart(int exmplStart) {this.exmplStart = exmplStart;}
    public void setExmplEnd(int exmplEnd) {this.exmplEnd = exmplEnd;}
    public void setCorrectWord(String correctWord) {this.correctWord = correctWord;}
    public void setWrongWord(String wrongWord) {this.wrongWord = wrongWord;}
    public void setSkippedWord(String skippedWord) {this.skippedWord = skippedWord;}
    public void setAddNote(String addNote) {this.addNote = addNote;}
    public void setMarkedClickedImg(int markedClickedImg) {this.markedClickedImg = markedClickedImg;}
    public void setTranslateDef(String translateDef) {this.translateDef = translateDef;}
    public void setImgUri(String imgUri) {this.imgUri = imgUri;}

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(wordImage);
        dest.writeString(imgUri);
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
        dest.writeString(addNote);
        dest.writeString(correctWord);
        dest.writeString(wrongWord);
        dest.writeString(skippedWord);
    }
}
