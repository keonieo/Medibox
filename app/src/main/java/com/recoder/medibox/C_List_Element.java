package com.recoder.medibox;

import android.graphics.Bitmap;
import android.view.View;

public class C_List_Element {

    public String medicine_name;
    public Bitmap medicine_image;
    public String medicine_code;

    public Bitmap getMedicine_image() {
        return medicine_image;
    }

    public String getMedicine_code() {
        return medicine_code;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public C_List_Element(String medicine_name, String medicine_code, Bitmap medicine_image) {
        this.medicine_name = medicine_name;
        this.medicine_code = medicine_code;
        this.medicine_image = medicine_image;
    }

    public View.OnClickListener onClickListener;
}