package com.recoder.medibox;

import android.content.Intent;
import android.database.Cursor;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class B_Edit_Info extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;
    Bitmap pic_medicine;
    String Year;
    String Month;
    String Day;
    String CHECK;
    String d_effect;
    Bitmap pack_image;
    String code;
    B_DbOpenHelper mDbOpenHelper;

    EditText direct_name;
    EditText direct_effect;
    EditText memo;
    CheckBox check;
    DatePicker date;
    ImageView med_image;
    String isDirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direct);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("약품정보수정");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요


        direct_name = findViewById(R.id.direct_name);
        direct_effect = findViewById(R.id.direct_effect);
        memo = findViewById(R.id.memo);
        check = findViewById(R.id.check);
        date = findViewById(R.id.date);
        med_image = (ImageView)findViewById(R.id.camera);

        Calendar minDate = Calendar.getInstance();
        minDate.set(2019,1-1,1);
        date.setMinDate(minDate.getTime().getTime());

        mDbOpenHelper = new B_DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        Intent intent = getIntent();
        Long id = Long.parseLong(intent.getStringExtra("id"));

        B_List_Element element = getInfo(id);
        setMode(element);
        pic_medicine = element.medicine_pack_img;

        isDirect = "";

        CHECK = element.CHECK;

        if(element.CHECK.contains("D")) {
                isDirect = "D";
        }

        check.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check.isChecked()==true) {
                    CHECK = isDirect+"처방";
                } else
                    CHECK = isDirect+"일반";
            }
        }) ;



        ImageButton camera = findViewById(R.id.camera);
        camera.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                if(element.CHECK.contains("D")) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                }
            }
        });

        Button yes = findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Year = String.valueOf(date.getYear());
                if( String.valueOf(date.getMonth()+1).length() == 1 ) Month = "0"+String.valueOf(date.getMonth()+1);
                else Month = String.valueOf(date.getMonth()+1);
                if( String.valueOf(date.getDayOfMonth()).length() == 1 ) Day = "0"+String.valueOf(date.getDayOfMonth());
                else Day = String.valueOf(date.getDayOfMonth());

                String img_drug = "";
                if(!element.CHECK.contains("D")) img_drug = getBase64String(element.medicine_drug_img);

                //drug_image에서 에러뜸
                mDbOpenHelper.updateColumn(id, getBase64String(pic_medicine), direct_name.getText().toString(),direct_effect.getText().toString(), CHECK , img_drug, code, Year+Month+Day, memo.getText().toString(), d_effect);

                Intent intent = new Intent(B_Edit_Info.this, B_List_Medicine.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                clearMode();
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
                intent = new Intent(B_Edit_Info.this, B_List_Medicine.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menu_cancel:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                med_image.setImageBitmap(bitmap);
                pic_medicine = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBase64String (Bitmap bitmap)
    {
        if (bitmap == null) return "";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);

    }

    public B_List_Element getInfo(Long id) {

        Cursor iCursor = mDbOpenHelper.sortColumn("_id");
        if (iCursor == null) return null;

        while (iCursor.moveToNext()) {
            String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));

            if (Long.parseLong(tempIndex) == id) {

                String tempPackImg = iCursor.getString(iCursor.getColumnIndex("_packimg"));
                String tempName = iCursor.getString(iCursor.getColumnIndex("_name"));
                String tempEffect = iCursor.getString(iCursor.getColumnIndex("_effect"));
                String tempCheck = iCursor.getString(iCursor.getColumnIndex("_check"));
                String tempDrugImg = iCursor.getString(iCursor.getColumnIndex("_drugimg"));
                String tempCode = iCursor.getString(iCursor.getColumnIndex("_code"));
                String tempDate = iCursor.getString(iCursor.getColumnIndex("_date"));
                String tempMemo = iCursor.getString(iCursor.getColumnIndex("_memo"));
                String tempDEffect = iCursor.getString(iCursor.getColumnIndex("_deffect"));

                B_List_Element new_ = new B_List_Element(stringToBitmap(tempPackImg), tempName, tempEffect, tempCheck, stringToBitmap(tempDrugImg), tempDate, tempCode, tempMemo, tempDEffect);
                return new_;
            }
        }
        return null;
    }

    public void setMode(B_List_Element element){
        boolean checked = false;

        direct_name.setText(element.medicine_name);

        direct_effect.setText(element.medicine_effect);

        if(!element.CHECK.contains("D")) {
            direct_name.setClickable(false);
            direct_name.setFocusable(false);

            direct_effect.setClickable(false);
            direct_effect.setFocusable(false);
        }

        memo.setText(element.medicine_memo);

        if((element.CHECK.replaceAll("D","")).equalsIgnoreCase("처방")) checked = true;
        check.setChecked(checked);

        date.updateDate(Integer.parseInt(element.medicine_date.substring(0,4)),Integer.parseInt(element.medicine_date.substring(4,6))-1,Integer.parseInt(element.medicine_date.substring(6,8)));

        med_image.setImageBitmap(element.medicine_pack_img);

        d_effect = element.medicine_detail_effect;
        code = element.medicine_code;
    }

    public void clearMode(){

        direct_name.setText("");
        direct_name.setClickable(true);

        direct_effect.setText("");
        direct_effect.setClickable(true);

        memo.setText("");
        direct_effect.setClickable(true);

        check.setChecked(false);
    }

    public Bitmap stringToBitmap(String string){
        if(string !=null && !string.equalsIgnoreCase("")) return BitmapFactory.decodeByteArray(Base64.decode(string, Base64.NO_WRAP), 0, Base64.decode(string, Base64.NO_WRAP).length);
        return null;
    }

}