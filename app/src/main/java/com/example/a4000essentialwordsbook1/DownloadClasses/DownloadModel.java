package com.example.a4000essentialwordsbook1.DownloadClasses;

public class DownloadModel {
    private int tvImage;
    private float defaultAudioSize;
    private float defaultImgSize;
    private String audioFileSize;
    private String imageFileSize;

    public DownloadModel(int img, String audioFileSize, String imageFileSize, float defaultAudioSize, float defaultImgSize){
        this.tvImage = img;
        this.audioFileSize = audioFileSize;
        this.imageFileSize = imageFileSize;
        this.defaultAudioSize = defaultAudioSize;
        this.defaultImgSize = defaultImgSize;
    }

    public int getTvImage() {return tvImage;}
    public String getAudioFileSize() {return audioFileSize;}
    public String getImageFileSize() {return imageFileSize;}
    public float getDefaultImgSize() {return defaultImgSize;}
    public float getDefaultAudioSize() {return defaultAudioSize;}


    public void setImageFileSize(String imageFileSize) {this.imageFileSize = imageFileSize;}
    public void setTvImage(int tvImage) {this.tvImage = tvImage;}
    public void setAudioFileSize(String audioFileSize) {this.audioFileSize = audioFileSize;}
    public void setDefaultAudioSize(float defaultAudioSize) {this.defaultAudioSize = defaultAudioSize;}
    public void setDefaultImgSize(float defaultImgSize) {this.defaultImgSize = defaultImgSize;}
}
