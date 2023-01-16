package com.example.mychatapp;

import java.util.List;

public class GrupModel {
    private String name, aciklama,image,uid;
    private List<String> numbers;

    public GrupModel() {
    }

    public GrupModel(String name, String aciklama, String image, List<String> numbers, String uid ) {
        this.name = name;
        this.aciklama = aciklama;
        this.image = image;
        this.numbers = numbers;
        this.uid = uid;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }
}
