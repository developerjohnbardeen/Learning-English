package com.example.a4000essentialwordsbook1.QuizFile.QuizModels;

import android.os.Parcel;
import android.os.Parcelable;


public class WrongModel implements Parcelable {
    private int tvImage;
    private int id;
    private int dbNum;
    private int unitNum;
    private int hardFlag;
    private int easyFlag;
    private String tvWord;
    private String wrongWord;
    private String correctWord;


    public WrongModel(int id, int tvImage,int flag, int easyFlag, int dbNum, int unitNum, String tvWord, String wrongWord , String correctWord){
        this.id = id;
        this.hardFlag = flag;
        this.tvImage = tvImage;
        this.tvWord = tvWord;
        this.wrongWord = wrongWord;
        this.correctWord = correctWord;
        this.dbNum = dbNum;
        this.unitNum = unitNum;
        this.easyFlag = easyFlag;
    }

    public WrongModel(){}

    protected WrongModel(Parcel in) {
        id = in.readInt();
        hardFlag = in.readInt();
        tvImage = in.readInt();
        tvWord = in.readString();
        wrongWord = in.readString();
        correctWord = in.readString();
        dbNum = in.readInt();
        unitNum = in.readInt();
        easyFlag = in.readInt();
    }

    public static final Creator<WrongModel> CREATOR = new Creator<WrongModel>() {
        @Override
        public WrongModel createFromParcel(Parcel in) {
            return new WrongModel(in);
        }

        @Override
        public WrongModel[] newArray(int size) {
            return new WrongModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(hardFlag);
        dest.writeInt(tvImage);
        dest.writeString(tvWord);
        dest.writeString(wrongWord);
        dest.writeString(correctWord);
        dest.writeInt(dbNum);
        dest.writeInt(unitNum);
        dest.writeInt(easyFlag);
    }

    public int getDbNum() {
        return dbNum;
    }

    public void setDbNum(int dbNum) {
        this.dbNum = dbNum;
    }

    public int getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(int unitNum) {
        this.unitNum = unitNum;
    }

    public int getEasyFlag() {
        return easyFlag;
    }

    public void setEasyFlag(int easyFlag) {
        this.easyFlag = easyFlag;
    }

    public int getTvImage() {
        return tvImage;
    }

    public void setTvImage(int tvImage) {
        this.tvImage = tvImage;
    }

    public String getTvWord() {
        return tvWord;
    }

    public void setTvWord(String tvWord) {
        this.tvWord = tvWord;
    }

    public String getWrongWord() {
        return wrongWord;
    }

    public void setWrongWord(String wrongWord) {
        this.wrongWord = wrongWord;
    }

    public String getCorrectWord() {
        return correctWord;
    }

    public void setCorrectWord(String correctWord) {
        this.correctWord = correctWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHardFlag() {
        return hardFlag;
    }

    public void setHardFlag(int hardFlag) {
        this.hardFlag = hardFlag;
    }

    public static Creator<WrongModel> getCREATOR() {
        return CREATOR;
    }
}
