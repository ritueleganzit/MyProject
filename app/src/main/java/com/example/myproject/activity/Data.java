package com.example.myproject.activity;

public class Data {
    String nm1;
    int img, color;

    public Data(String nm1, int img, int color) {
        this.nm1 = nm1;
        this.img = img;
        this.color = color;
    }

    public String getNm1() {
        return nm1;
    }

    public void setNm1(String nm1) {
        this.nm1 = nm1;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}