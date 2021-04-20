package com.example.a4000essentialwordsbook1.SelectedUnitTab;

import android.os.Parcel;
import android.os.Parcelable;

public class WordModel implements Parcelable {
    private int id;
    private int wordImage;
    private int audio;
    private int markedFlag;
    private int markedClickedImg;
    private String word;
    private String phonetic;
    private String translateWord;
    private String definition;
    private String translateDef;
    private String example;
    private String translateExmpl;

    public WordModel(int id, int wordImage, String word, String phonetic,
                     String translateWord, String definition,
                     String example, String translateExmpl){
        this.id = id;
        this.wordImage = wordImage;
        this.word = word;
        this.phonetic = phonetic;
        this.translateWord = translateWord;
        this.definition = definition;
        this.example = example;
        this.translateExmpl = translateExmpl;

    }
    public WordModel(){
    }

    protected WordModel(Parcel in) {
        id = in.readInt();
        wordImage = in.readInt();
        audio = in.readInt();
        markedFlag = in.readInt();
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

    public int getMarkedFlag() {
        return markedFlag;
    }

    public void setMarkedFlag(int markedFlag) {
        this.markedFlag = markedFlag;
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
        dest.writeInt(markedFlag);
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
