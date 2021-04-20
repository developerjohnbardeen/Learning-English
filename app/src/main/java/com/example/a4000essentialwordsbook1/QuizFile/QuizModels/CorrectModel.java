package com.example.a4000essentialwordsbook1.QuizFile.QuizModels;

import android.os.Parcel;
import android.os.Parcelable;


public class CorrectModel implements Parcelable {
    private int tvImage;
    private String tvWord;
    private String correctWord;


    public static Creator<CorrectModel> getCREATOR() {
        return CREATOR;
    }

    public CorrectModel(int tvImage, String word, String answer){
        this.tvImage = tvImage;
        this.tvWord = word;
        this.correctWord = answer;
    }
    public CorrectModel(){ }



    protected CorrectModel(Parcel in) {
        tvImage = in.readInt();
        tvWord = in.readString();
        correctWord = in.readString();
    }

    public static final Creator<CorrectModel> CREATOR = new Creator<CorrectModel>() {
        @Override
        public CorrectModel createFromParcel(Parcel in) {
            return new CorrectModel(in);
        }

        @Override
        public CorrectModel[] newArray(int size) {
            return new CorrectModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tvImage);
        dest.writeString(tvWord);
        dest.writeString(correctWord);
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
}
