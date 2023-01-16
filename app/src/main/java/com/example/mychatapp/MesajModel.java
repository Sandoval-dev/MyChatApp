package com.example.mychatapp;

public class MesajModel {
    String mesajName,icerik,uid;

    public MesajModel(){

    }

    public MesajModel(String mesajName, String icerik, String uid) {
        this.mesajName = mesajName;
        this.icerik = icerik;
        this.uid = uid;
    }

    public String getMesajName() {
        return mesajName;
    }

    public void setMesajName(String mesajName) {
        this.mesajName = mesajName;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
