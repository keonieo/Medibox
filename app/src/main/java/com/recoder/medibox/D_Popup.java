package com.recoder.medibox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class D_Popup extends AppCompatActivity {

    B_DbOpenHelper mDbOpenHelper;
    String CHECK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 상태바 제거 ( 전체화면 모드 )
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.result_popup);

        Intent intent = new Intent(this.getIntent());

        String pack_img = getListFromLocal("pack_img");
        String drug_img = getListFromLocal("drug_img");
        String overlap = intent.getStringExtra("overlap");
        String drug_name = intent.getStringExtra("name");
        String drug_effect = intent.getStringExtra("effect");
        String drug_code = intent.getStringExtra("code");
        String drug_deffect = intent.getStringExtra("d_effect");


        CHECK = "일반";
        DatePicker popup_date = findViewById(R.id.popup_date);

        Calendar minDate = Calendar.getInstance();
        minDate.set(2019,1-1,1);
        popup_date.setMinDate(minDate.getTime().getTime());

        Button popup_close = findViewById(R.id.popup_close);
        TextView popup_text = findViewById(R.id.popup_text);
        CheckBox checkBox = findViewById(R.id.popup_check);
        checkBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()==true) {
                    CHECK = "처방";
                } else
                    CHECK = "일반";
            }
        }) ;

        TextView popup_overlap = findViewById(R.id.tv_popup_overlap);

        popup_overlap.setVisibility(GONE);

        if(!overlap.equalsIgnoreCase("")){
            popup_overlap.setVisibility(VISIBLE);
            popup_overlap.setText(Html.fromHtml("<font color='#EE0000'> * "+overlap+ " 의약품이 이미 존재합니다!</font>"));
        }

        mDbOpenHelper= new B_DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        popup_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = String.valueOf(popup_date.getYear());
                if( String.valueOf(popup_date.getMonth()+1).length() == 1 )
                    date += "0"+String.valueOf(popup_date.getMonth()+1);
                else date += String.valueOf(popup_date.getMonth()+1);
                if( String.valueOf(popup_date.getDayOfMonth()).length() == 1 )
                    date += "0"+String.valueOf(popup_date.getDayOfMonth());
                else date += String.valueOf(popup_date.getDayOfMonth());

                String memo = popup_text.getText().toString();
                mDbOpenHelper.insertColumn(pack_img, drug_name , drug_effect , CHECK , drug_img, drug_code, date, memo, drug_deffect);

                //add
                Intent intent = new Intent(D_Popup.this, B_List_Medicine.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
*/
    public String getListFromLocal(String key) {
        SharedPreferences prefs = getSharedPreferences("MediBox", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        return json;
    }

}