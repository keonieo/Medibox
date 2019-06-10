package com.recoder.medibox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;


public class C_Name extends AppCompatActivity {

    EditText et_medicine_list;
    ImageButton bt_medicine_list;
    String empty, search, hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("약품 추가");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        //키보드가 URL 안가리게
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        et_medicine_list = (EditText) findViewById(R.id.et_medicine_list);
        bt_medicine_list = (ImageButton) findViewById(R.id.bt_medicine_list);

        et_medicine_list.setHint(hint);

        et_medicine_list.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (et_medicine_list.getText().toString().length() == 0) {
                        Toast.makeText(C_Name.this, "약품명이 입력되지 않았습니다.", Toast.LENGTH_LONG).show();
                        et_medicine_list.requestFocus();
                        return true;
                    }

                    String search = et_medicine_list.getText().toString();
                    Intent intent = new Intent(C_Name.this,C_Name_List.class);
                    intent.putExtra("search",search);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        bt_medicine_list.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_medicine_list.getText().toString().length() == 0) {
                    Toast.makeText(C_Name.this, "약품명이 입력되지 않았습니다.", Toast.LENGTH_LONG).show();
                    et_medicine_list.requestFocus();
                    return;
                }

                String search = et_medicine_list.getText().toString();
                Intent intent = new Intent(C_Name.this,C_Name_List.class);
                intent.putExtra("search",search);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_toolbar, menu) ;
        return true ;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                intent = new Intent(C_Name.this, B_List_Medicine.class);
                startActivity(intent);
                break;
            case R.id.menu_cancel:
                intent = new Intent(C_Name.this, B_List_Medicine.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
