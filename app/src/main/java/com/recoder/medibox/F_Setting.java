package com.recoder.medibox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.Arrays;

public class F_Setting extends AppCompatActivity {

    String st_emphasis[] = {"붉은색으로 표시","두꺼운 글씨체로 표시", "기울인 글씨체로 표시", "밑줄로 표시"};
    String st_order[] = {"가나다 순", "등록 순", "유통기한 순"};
    ArrayAdapter adapter_order;
    ArrayAdapter adapter_emphasis;
    ListView lv_order;
    ListView lv_emphasis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_setting);

        lv_order = (ListView)findViewById(R.id.lv_order);
        lv_emphasis = (ListView)findViewById(R.id.lv_emphasis);

        Button button = (Button)findViewById(R.id.bt_setting);

        adapter_order = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, Arrays.asList(st_order));
        adapter_emphasis = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, Arrays.asList(st_emphasis));

        lv_emphasis.setAdapter(adapter_emphasis);
        lv_order.setAdapter(adapter_order);

        adapter_emphasis.notifyDataSetChanged();
        adapter_order.notifyDataSetChanged();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int checked_order;
                int checked_emphasis;

                int count_order = adapter_order.getCount();
                int count_emphasis = adapter_emphasis.getCount();

                String order="";
                String emphasis="";

                if (count_order > 0) {
                    checked_order = lv_order.getCheckedItemPosition();
                    if (checked_order > -1 && checked_order < count_order) {
                        if(checked_order==0) order = "_name";
                        else if(checked_order==1) order ="_id";
                        else if(checked_order==2) order = "_date";
                        else order ="";
                    }
                }

                if (count_emphasis > 0) {
                    checked_emphasis = lv_emphasis.getCheckedItemPosition();
                    if (checked_emphasis > -1 && checked_emphasis < count_emphasis) {
                        if(checked_emphasis==0) emphasis = "color";
                        else if(checked_emphasis==1) emphasis ="bold";
                        else if(checked_emphasis==2) emphasis = "tilt";
                        else if(checked_emphasis==3) emphasis = "underline";
                        else emphasis ="";
                    }
                }

                if(!order.equalsIgnoreCase("")) saveListInLocal(order, "method_order");
                if(!emphasis.equalsIgnoreCase("")) saveListInLocal(emphasis, "method_emphasis");

                Intent intent = new Intent(F_Setting.this, B_List_Medicine.class);
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
    public void saveListInLocal(String string, String key) {
        SharedPreferences prefs = getSharedPreferences("MediBox", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, string);
        editor.apply();

        Log.d("methth_saveinLocal",string);
    }

}