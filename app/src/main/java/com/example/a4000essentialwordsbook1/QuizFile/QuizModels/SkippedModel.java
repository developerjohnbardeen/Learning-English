package com.example.a4000essentialwordsbook1.QuizFile.QuizModels;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class SkippedModel implements Parcelable {
    private int tvImage;
    private int wordId;
    private int hardFlag;
    private String tvWord;
    private String correctWord;



    public SkippedModel(int image, int wordId, int hardFlag, String word, String correctWord){
        this.wordId = wordId;
        this.hardFlag = hardFlag;
        this.tvImage = image;
        this.tvWord = word;
        this.correctWord = correctWord;
    }

    public SkippedModel(){}

    protected SkippedModel(Parcel in) {
        wordId = in.readInt();
        hardFlag = in.readInt();
        tvImage = in.readInt();
        tvWord = in.readString();
        correctWord = in.readString();
    }

    public static final Creator<SkippedModel> CREATOR = new Creator<SkippedModel>() {
        @Override
        public SkippedModel createFromParcel(Parcel in) {
            return new SkippedModel(in);
        }

        @Override
        public SkippedModel[] newArray(int size) {
            return new SkippedModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(wordId);
        dest.writeInt(hardFlag);
        dest.writeInt(tvImage);
        dest.writeString(tvWord);
        dest.writeString(correctWord);
    }


    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public int getHardFlag() {
        return hardFlag;
    }

    public void setHardFlag(int hardFlag) {
        this.hardFlag = hardFlag;
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

    public String getCorrectWord() {
        return correctWord;
    }

    public void setCorrectWord(String correctWord) {
        this.correctWord = correctWord;
    }

    public static Creator<SkippedModel> getCREATOR() {
        return CREATOR;
    }
}
