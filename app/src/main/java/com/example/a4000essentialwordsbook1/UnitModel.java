package com.example.a4000essentialwordsbook1;

public class UnitModel {
    private int uId;
    private int unitImg;
    private int unitAudio;
    private String unitTitle;
    private String unitNumber;

    public UnitModel(int id, int img, int audio,  String title, String number){
        this.uId = id;
        this.unitImg = img;
        this.unitAudio = audio;
        this.unitTitle = title;
        this.unitNumber = number;
    }

    public UnitModel(){}


    public int getuId(){return uId;}
    public String getUnitTitle() {
        return unitTitle;
    }
    public String getUnitNumber() {
        return unitNumber;
    }
    public int getUnitImg() {
        return unitImg;
    }
    public int getUnitAudio(){return  unitAudio;}

    public void setuId(int unitId){this.uId =unitId;}
    public void setUnitImg(int unitImg) {
        this.unitImg = unitImg;
    }
    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }
    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }
    public void setUnitAudio(int unitAudio){this.unitAudio = unitAudio;}
}
