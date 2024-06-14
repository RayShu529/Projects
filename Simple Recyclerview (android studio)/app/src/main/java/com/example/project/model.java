package com.example.project;

import android.graphics.Bitmap;

public class model {
    private Bitmap imgview;
    private String tv;
    private String tv2;

    model(Bitmap imgview, String tv, String tv2){
        this.imgview=imgview;
        this.tv=tv;
        this.tv2=tv2;
    }
    public Bitmap getImgview() {
        return imgview;
    }

    public String getTv() {
        return tv;
    }

    public String getTv2() {
        return tv2;
    }

}

