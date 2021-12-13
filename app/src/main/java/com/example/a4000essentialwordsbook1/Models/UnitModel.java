package com.example.a4000essentialwordsbook1.Models;

public class UnitModel {
    private int uId;
    private String unitImg;
    private String unitAudio;
    private String unitTitle;
    private String unitNumber;

    public UnitModel(int id, String img, String audio,  String title, String number){
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
    public String getUnitImg() {
        return unitImg;
    }
    public String getUnitAudio(){return  unitAudio;}

    public void setuId(int unitId){this.uId =unitId;}
    public void setUnitImg(String unitImg) {
        this.unitImg = unitImg;
    }
    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }
    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }
    public void setUnitAudio(String unitAudio){this.unitAudio = unitAudio;}
}
