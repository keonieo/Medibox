package com.recoder.medibox;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class B_List_Element {

    public Bitmap medicine_pack_img;
    public String medicine_name;
    public String medicine_effect;
    public String CHECK;
    public Bitmap medicine_drug_img;
    public String medicine_code;
    public String medicine_date;
    public String medicine_memo;
    public String medicine_detail_effect;

    boolean isclicked;
    boolean delete;
    int realposition;

    public Bitmap getMedicine_pack_image() {
        return medicine_pack_img;
    }

    public String getMedicine_name() { return medicine_name; }

    public String getMedicine_effect() {
        return medicine_effect;
    }

    public String getMedicine_check() {return CHECK;}

    public Bitmap getMedicine_drug_image() {
        return medicine_drug_img;
    }

    public String getDate() { return medicine_date; }

    public String getCode() { return medicine_code; }

    public String getMemo() { return medicine_memo; }

    public String getMedicine_detail_effect() {
        return medicine_detail_effect;
    }

    public B_List_Element(Bitmap medicine_pack_img, String medicine_name, String medicine_effect, String check, Bitmap medicine_drug_img, String date, String code, String memo, String medicine_detail_effect) {

        this.medicine_pack_img = medicine_pack_img;
        this.medicine_name = medicine_name;
        this.medicine_effect = medicine_effect;
        this.CHECK = check;
        this.medicine_drug_img = medicine_drug_img;
        this.medicine_date = date;
        this.medicine_code = code;
        this.medicine_memo = memo;
        this.medicine_detail_effect = medicine_detail_effect;

        isclicked = false;
        delete = false;
    }

    public View.OnClickListener onClickListener;
}