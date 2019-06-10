package com.recoder.medibox;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;

public class C_Direct extends AppCompatActivity {

    B_DbOpenHelper mDbOpenHelper;
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap pic_medicine;
    String Year;
    String Month;
    String Day;
    String CHECK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direct);

        mDbOpenHelper= new B_DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("보유약품목록");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        CHECK = "일반";

        CheckBox check = findViewById(R.id.check);
        check.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check.isChecked()==true) {
                    CHECK = "처방";
                } else
                    CHECK = "일반";
            }
        }) ;

        DatePicker date = findViewById(R.id.date);

        Calendar minDate = Calendar.getInstance();
        minDate.set(2019,1-1,1);
        date.setMinDate(minDate.getTime().getTime());

        final EditText direct_name = findViewById(R.id.direct_name);
        final EditText direct_effect = findViewById(R.id.direct_effect);
        final EditText memo = findViewById(R.id.memo);

        direct_name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)) {
                    direct_effect.requestFocus();
                    return true;
                }
                return false;
            }
        });

        direct_effect.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)) {
                    memo.requestFocus();
                    return true;
                }
                return false;
            }
        });

        ImageView med_image = (ImageView)findViewById(R.id.camera);
        Drawable d = med_image.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();

        ImageButton camera = findViewById(R.id.camera);
        camera.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        Button yes = findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_Direct.this, B_List_Medicine.class);

                Year = String.valueOf(date.getYear());
                if( String.valueOf(date.getMonth()+1).length() == 1 ) Month = "0"+String.valueOf(date.getMonth()+1);
                else Month = String.valueOf(date.getMonth()+1);
                if( String.valueOf(date.getDayOfMonth()).length() == 1 ) Day = "0"+String.valueOf(date.getDayOfMonth());
                else Day = String.valueOf(date.getDayOfMonth());

                mDbOpenHelper.insertColumn(getBase64String(pic_medicine), direct_name.getText().toString() , direct_effect.getText().toString() , "D"+CHECK , "", "", Year+Month+Day, memo.getText().toString(), "");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
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
                intent = new Intent(C_Direct.this, B_List_Medicine.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menu_cancel:
                intent = new Intent(C_Direct.this, B_List_Medicine.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getBase64String (Bitmap bitmap)
    {
        if (bitmap == null) return "";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView med_image = (ImageView) findViewById(R.id.camera);
                med_image.setImageBitmap(bitmap);
                pic_medicine = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}