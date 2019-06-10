package com.recoder.medibox;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class C_Shape extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Button btOK;

    //edittext
    private EditText etfront;
    private EditText etback;
    String rawfront;//한글
    String rawback;
    String frontresult;//url
    String backresult;

    //shape
    private CheckBox cbwholeshape;
    private CheckBox cbshape01;
    private CheckBox cbshape02;
    private CheckBox cbshape03;
    private CheckBox cbshape04;
    private CheckBox cbshape05;
    private CheckBox cbshape06;
    private CheckBox cbshape07;
    private CheckBox cbshape08;
    private CheckBox cbshape09;
    private CheckBox cbshape10;
    private CheckBox cbshape11;
    private CheckBox cbshapeetc;

    private ImageButton ivshape01;
    private ImageButton ivshape02;
    private ImageButton ivshape03;
    private ImageButton ivshape04;
    private ImageButton ivshape05;
    private ImageButton ivshape06;
    private ImageButton ivshape07;
    private ImageButton ivshape08;
    private ImageButton ivshape09;
    private ImageButton ivshape10;
    private ImageButton ivshape11;
    private ImageButton ivshapeetc;

    //color
    private CheckBox cbwholecolor;
    private CheckBox cbwhite;
    private CheckBox cbyellow;
    private CheckBox cborange;
    private CheckBox cbpink;
    private CheckBox cbred;
    private CheckBox cbbrown;
    private CheckBox cbygreen;
    private CheckBox cbgreen;
    private CheckBox cbbgreen;
    private CheckBox cbblue;
    private CheckBox cbnavy;
    private CheckBox cbwine;
    private CheckBox cbpurple;
    private CheckBox cbgrey;
    private CheckBox cbblack;
    private CheckBox cbtransp;

    private ImageButton ivwhite;
    private ImageButton ivyellow;
    private ImageButton ivorange;
    private ImageButton ivpink;
    private ImageButton ivred;
    private ImageButton ivbrown;
    private ImageButton ivygreen;
    private ImageButton ivgreen;
    private ImageButton ivbgreen;
    private ImageButton ivblue;
    private ImageButton ivnavy;
    private ImageButton ivwine;
    private ImageButton ivpurple;
    private ImageButton ivgrey;
    private ImageButton ivblack;
    private ImageButton ivtransp;

    //form
    private CheckBox cbwholetype;
    private CheckBox cbtype01;
    private CheckBox cbtype02;
    private CheckBox cbtype03;
    private CheckBox cbtypeetc;

    private ImageButton ivtype01;
    private ImageButton ivtype02;
    private ImageButton ivtype03;
    private ImageButton ivtypeetc;

    //line
    private CheckBox cbwholeline;
    private CheckBox cbno;
    private CheckBox cbplus;
    private CheckBox cbminus;
    private CheckBox cblineetc;

    private ImageButton ivno;
    private ImageButton ivplus;
    private ImageButton ivminus;
    private ImageButton ivlineetc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shape);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("약품 추가");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        btOK = (Button)findViewById(R.id.btOK);
        btOK.setOnClickListener(this);

        etfront = (EditText) findViewById(R.id.etfront);
        etback = (EditText) findViewById(R.id.etback);
        etfront.setText(rawfront);
        etback.setText(rawback);

        //front입력에서 enter치면 back입력으로 바로 넘어가게
        etfront.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)) {
                    etback.requestFocus();
                    return true;
                }
                return false;
            }
        });
        //back에서 enter치면 정보를 올린다.
        etback.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)) {
                    rawfront = etfront.getText().toString();
                    rawback = etback.getText().toString();

                    if (rawfront == null) {
                        frontresult = "&drug_print_front=";
                    } else {
                        try {
                            frontresult = "&drug_print_front=" + URLEncoder.encode(rawfront, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    if (rawback == null) {
                        backresult = "&drug_print_back=";
                    } else {
                        try {
                            backresult = "&drug_print_back=" + URLEncoder.encode(rawback, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        //Shape
        ivshape01 = (ImageButton)findViewById(R.id.ivshape01);
        ivshape01.setImageResource(R.drawable.shape1);
        ivshape01.setBackground(null);
        ivshape01.setOnClickListener(this);
        ivshape02 = (ImageButton)findViewById(R.id.ivshape02);
        ivshape02.setImageResource(R.drawable.shape2);
        ivshape02.setBackground(null);
        ivshape02.setOnClickListener(this);
        ivshape03 = (ImageButton)findViewById(R.id.ivshape03);
        ivshape03.setImageResource(R.drawable.shape3);
        ivshape03.setBackground(null);
        ivshape03.setOnClickListener(this);
        ivshape04 = (ImageButton)findViewById(R.id.ivshape04);
        ivshape04.setImageResource(R.drawable.shape4);
        ivshape04.setBackground(null);
        ivshape04.setOnClickListener(this);
        ivshape05 = (ImageButton)findViewById(R.id.ivshape05);
        ivshape05.setImageResource(R.drawable.shape5);
        ivshape05.setBackground(null);
        ivshape05.setOnClickListener(this);
        ivshape06 = (ImageButton)findViewById(R.id.ivshape06);
        ivshape06.setImageResource(R.drawable.shape6);
        ivshape06.setBackground(null);
        ivshape06.setOnClickListener(this);
        ivshape07 = (ImageButton)findViewById(R.id.ivshape07);
        ivshape07.setImageResource(R.drawable.shape11);
        ivshape07.setBackground(null);
        ivshape07.setOnClickListener(this);
        ivshape08 = (ImageButton)findViewById(R.id.ivshape08);
        ivshape08.setImageResource(R.drawable.shape7);
        ivshape08.setBackground(null);
        ivshape08.setOnClickListener(this);
        ivshape09 = (ImageButton)findViewById(R.id.ivshape09);
        ivshape09.setImageResource(R.drawable.shape8);
        ivshape09.setBackground(null);
        ivshape09.setOnClickListener(this);
        ivshape10 = (ImageButton)findViewById(R.id.ivshape10);
        ivshape10.setImageResource(R.drawable.shape9);
        ivshape10.setBackground(null);
        ivshape10.setOnClickListener(this);
        ivshape11 = (ImageButton)findViewById(R.id.ivshape11);
        ivshape11.setImageResource(R.drawable.shape10);
        ivshape11.setBackground(null);
        ivshape11.setOnClickListener(this);
        ivshapeetc = (ImageButton)findViewById(R.id.ivetcshape);
        ivshapeetc.setImageResource(R.drawable.etc);
        ivshapeetc.setBackground(null);
        ivshapeetc.setOnClickListener(this);

        //체크박스
        cbwholeshape = (CheckBox)findViewById(R.id.cbwholeshape);
        cbwholeshape.setOnCheckedChangeListener(this);
        cbshape01 = (CheckBox)findViewById(R.id.cbshape01);
        cbshape01.setOnCheckedChangeListener(this);
        cbshape02= (CheckBox)findViewById(R.id.cbshape02);
        cbshape02.setOnCheckedChangeListener(this);
        cbshape03 = (CheckBox)findViewById(R.id.cbshape03);
        cbshape03.setOnCheckedChangeListener(this);
        cbshape04 = (CheckBox)findViewById(R.id.cbshape04);
        cbshape04.setOnCheckedChangeListener(this);
        cbshape05 = (CheckBox)findViewById(R.id.cbshape05);
        cbshape05.setOnCheckedChangeListener(this);
        cbshape06 = (CheckBox)findViewById(R.id.cbshape06);
        cbshape06.setOnCheckedChangeListener(this);
        cbshape07 = (CheckBox)findViewById(R.id.cbshape07);
        cbshape07.setOnCheckedChangeListener(this);
        cbshape08 = (CheckBox)findViewById(R.id.cbshape08);
        cbshape08.setOnCheckedChangeListener(this);
        cbshape09 = (CheckBox)findViewById(R.id.cbshape09);
        cbshape09.setOnCheckedChangeListener(this);
        cbshape10 = (CheckBox)findViewById(R.id.cbshape10);
        cbshape10.setOnCheckedChangeListener(this);
        cbshape11 = (CheckBox)findViewById(R.id.cbshape11);
        cbshape11.setOnCheckedChangeListener(this);
        cbshapeetc = (CheckBox)findViewById(R.id.cbetcshape);
        cbshapeetc.setOnCheckedChangeListener(this);

        //color
        ivwhite = (ImageButton) findViewById(R.id.ivwhite);
        ivwhite.setImageResource(R.drawable.color1);
        ivwhite.setBackground(null);
        ivwhite.setOnClickListener(this);
        ivyellow = (ImageButton) findViewById(R.id.ivyellow);
        ivyellow.setImageResource(R.drawable.color2);
        ivyellow.setBackground(null);
        ivyellow.setOnClickListener(this);
        ivorange = (ImageButton) findViewById(R.id.ivorange);
        ivorange.setImageResource(R.drawable.color3);
        ivorange.setBackground(null);
        ivorange.setOnClickListener(this);
        ivpink = (ImageButton) findViewById(R.id.ivpink);
        ivpink.setImageResource(R.drawable.color4);
        ivpink.setBackground(null);
        ivpink.setOnClickListener(this);
        ivred = (ImageButton) findViewById(R.id.ivred);
        ivred.setImageResource(R.drawable.color5);
        ivred.setBackground(null);
        ivred.setOnClickListener(this);
        ivbrown = (ImageButton) findViewById(R.id.ivbrown);
        ivbrown.setImageResource(R.drawable.color6);
        ivbrown.setBackground(null);
        ivbrown.setOnClickListener(this);
        ivygreen = (ImageButton) findViewById(R.id.ivygreen);
        ivygreen.setImageResource(R.drawable.color7);
        ivygreen.setBackground(null);
        ivygreen.setOnClickListener(this);
        ivgreen = (ImageButton) findViewById(R.id.ivgreen);
        ivgreen.setImageResource(R.drawable.color8);
        ivgreen.setBackground(null);
        ivgreen.setOnClickListener(this);
        ivbgreen = (ImageButton) findViewById(R.id.ivbgreen);
        ivbgreen.setImageResource(R.drawable.color9);
        ivbgreen.setBackground(null);
        ivbgreen.setOnClickListener(this);
        ivblue = (ImageButton) findViewById(R.id.ivblue);
        ivblue.setImageResource(R.drawable.color10);
        ivblue.setBackground(null);
        ivblue.setOnClickListener(this);
        ivnavy = (ImageButton) findViewById(R.id.ivnavy);
        ivnavy.setImageResource(R.drawable.color11);
        ivnavy.setBackground(null);
        ivnavy.setOnClickListener(this);
        ivwine = (ImageButton) findViewById(R.id.ivwine);
        ivwine.setImageResource(R.drawable.color16);
        ivwine.setBackground(null);
        ivwine.setOnClickListener(this);
        ivpurple = (ImageButton) findViewById(R.id.ivpurple);
        ivpurple.setImageResource(R.drawable.color12);
        ivpurple.setBackground(null);
        ivpurple.setOnClickListener(this);
        ivgrey = (ImageButton) findViewById(R.id.ivgrey);
        ivgrey.setImageResource(R.drawable.color13);
        ivgrey.setBackground(null);
        ivgrey.setOnClickListener(this);
        ivblack = (ImageButton) findViewById(R.id.ivblack);
        ivblack.setImageResource(R.drawable.color14);
        ivblack.setBackground(null);
        ivblack.setOnClickListener(this);
        ivtransp = (ImageButton) findViewById(R.id.ivtransp);
        ivtransp.setImageResource(R.drawable.color15);
        ivtransp.setBackground(null);
        ivtransp.setOnClickListener(this);

        //체크박스
        cbwholecolor = (CheckBox) findViewById(R.id.cbwholecolor);
        cbwholecolor.setOnCheckedChangeListener(this);
        cbwhite = (CheckBox) findViewById(R.id.cbwhite);
        cbwhite.setOnCheckedChangeListener(this);
        cbyellow = (CheckBox) findViewById(R.id.cbyellow);
        cbyellow.setOnCheckedChangeListener(this);
        cborange = (CheckBox) findViewById(R.id.cborange);
        cborange.setOnCheckedChangeListener(this);
        cbpink = (CheckBox) findViewById(R.id.cbpink);
        cbpink.setOnCheckedChangeListener(this);
        cbred = (CheckBox) findViewById(R.id.cbred);
        cbred.setOnCheckedChangeListener(this);
        cbbrown = (CheckBox) findViewById(R.id.cbbrown);
        cbbrown.setOnCheckedChangeListener(this);
        cbygreen = (CheckBox) findViewById(R.id.cbygreen);
        cbygreen.setOnCheckedChangeListener(this);
        cbgreen = (CheckBox) findViewById(R.id.cbgreen);
        cbgreen.setOnCheckedChangeListener(this);
        cbbgreen = (CheckBox) findViewById(R.id.cbbgreen);
        cbbgreen.setOnCheckedChangeListener(this);
        cbblue = (CheckBox) findViewById(R.id.cbblue);
        cbblue.setOnCheckedChangeListener(this);
        cbnavy = (CheckBox) findViewById(R.id.cbnavy);
        cbnavy.setOnCheckedChangeListener(this);
        cbwine = (CheckBox) findViewById(R.id.cbwine);
        cbwine.setOnCheckedChangeListener(this);
        cbpurple = (CheckBox) findViewById(R.id.cbpurple);
        cbpurple.setOnCheckedChangeListener(this);
        cbgrey = (CheckBox) findViewById(R.id.cbgrey);
        cbgrey.setOnCheckedChangeListener(this);
        cbblack = (CheckBox) findViewById(R.id.cbblack);
        cbblack.setOnCheckedChangeListener(this);
        cbtransp = (CheckBox) findViewById(R.id.cbtransp);
        cbtransp.setOnCheckedChangeListener(this);

        //form
        ivtype01 = (ImageButton)findViewById(R.id.ivtype01);
        ivtype01.setImageResource(R.drawable.form1);
        ivtype01.setBackground(null);
        ivtype01.setOnClickListener(this);
        ivtype02 = (ImageButton)findViewById(R.id.ivtype02);
        ivtype02.setImageResource(R.drawable.form2);
        ivtype02.setBackground(null);
        ivtype02.setOnClickListener(this);
        ivtype03 = (ImageButton)findViewById(R.id.ivtype03);
        ivtype03.setImageResource(R.drawable.form3);
        ivtype03.setBackground(null);
        ivtype03.setOnClickListener(this);
        ivtypeetc = (ImageButton)findViewById(R.id.ivetctype);
        ivtypeetc.setImageResource(R.drawable.etc);
        ivtypeetc.setBackground(null);
        ivtypeetc.setOnClickListener(this);
        //체크박스
        cbwholetype = (CheckBox)findViewById(R.id.cbwholetype);
        cbwholetype.setOnCheckedChangeListener(this);
        cbtype01 = (CheckBox)findViewById(R.id.cbtype01);
        cbtype01.setOnCheckedChangeListener(this);
        cbtype02= (CheckBox)findViewById(R.id.cbtype02);
        cbtype02.setOnCheckedChangeListener(this);
        cbtype03 = (CheckBox)findViewById(R.id.cbtype03);
        cbtype03.setOnCheckedChangeListener(this);
        cbtypeetc = (CheckBox)findViewById(R.id.cbetctype);
        cbtypeetc.setOnCheckedChangeListener(this);

        //line
        ivno = (ImageButton) findViewById(R.id.ivno);
        ivno.setImageResource(R.drawable.line1);
        ivno.setBackground(null);
        ivno.setOnClickListener(this);
        ivplus = (ImageButton) findViewById(R.id.ivplus);
        ivplus.setImageResource(R.drawable.line2);
        ivplus.setBackground(null);
        ivplus.setOnClickListener(this);
        ivminus = (ImageButton) findViewById(R.id.ivminus);
        ivminus.setImageResource(R.drawable.line3);
        ivminus.setBackground(null);
        ivminus.setOnClickListener(this);
        ivlineetc = (ImageButton) findViewById(R.id.ivetcline);
        ivlineetc.setImageResource(R.drawable.etc);
        ivlineetc.setBackground(null);
        ivlineetc.setOnClickListener(this);

        //체크박스
        cbwholeline = (CheckBox) findViewById(R.id.cbwholeline);
        cbwholeline.setOnCheckedChangeListener(this);
        cbno = (CheckBox) findViewById(R.id.cbno);
        cbno.setOnCheckedChangeListener(this);
        cbplus = (CheckBox) findViewById(R.id.cbplus);
        cbplus.setOnCheckedChangeListener(this);
        cbminus = (CheckBox) findViewById(R.id.cbminus);
        cbminus.setOnCheckedChangeListener(this);
        cblineetc = (CheckBox) findViewById(R.id.cbetcline);
        cblineetc.setOnCheckedChangeListener(this);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_toolbar, menu) ;
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                intent = new Intent(C_Shape.this, B_List_Medicine.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menu_cancel:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public String ShapeChecked(View v) {
        String result = "";
        String rawresult ="";
        if(cbwholeshape.isChecked()){
            result += "&shapes=";
            rawresult += "전체";
            return result+" "+rawresult;
        }
        else{
            try {
                if (cbshape01.isChecked()) {
                    result += "&shapes=" + URLEncoder.encode("완전원형", "UTF-8");
                    rawresult += "원형,";
                }
                if (cbshape02.isChecked()) {
                    result += "&shapes=" + URLEncoder.encode("타원형", "UTF-8");
                    rawresult += "타원형,";
                }
                if (cbshape03.isChecked()) {
                    result += "&shapes=" + URLEncoder.encode("장방형", "UTF-8");
                    rawresult += "장방형,";
                }
                if (cbshape04.isChecked()) {
                    result += "&shapes=" + URLEncoder.encode("반원형", "UTF-8");
                    rawresult += "반원형,";
                }
                if (cbshape05.isChecked()) {
                    result += "&shapes=" + URLEncoder.encode("삼각형", "UTF-8");
                    rawresult += "삼각형,";
                }
                if (cbshape06.isChecked()) {
                    result += "&shapes=" + URLEncoder.encode("사각형", "UTF-8");
                    rawresult += "사각형,";
                }
                if (cbshape07.isChecked()) {
                    result += "&shapes=" + URLEncoder.encode("마름모형", "UTF-8");
                    rawresult += "마름모형,";
                }
                if (cbshape08.isChecked()) {
                    result += "&shapes=" + URLEncoder.encode("오각형", "UTF-8");
                    rawresult += "오각형,";
                }
                if (cbshape09.isChecked()) {
                    result += "&shapes=" + URLEncoder.encode("육각형", "UTF-8");
                    rawresult += "육각형,";
                }
                if (cbshape10.isChecked()) {
                    result += "&shapes=" + URLEncoder.encode("팔각형", "UTF-8");
                    rawresult += "팔각형,";
                }
                if (cbshape11.isChecked()) {
                    result += "&shapes=&shapes_alt=on&shapes_etc=" + URLEncoder.encode("8자형", "UTF-8");
                    rawresult += "8자형,";
                }
                if (cbshapeetc.isChecked()) {
                    result += "&shapes_alt=on";
                    rawresult += "기타,";
                }
                if(!cbwholeshape.isChecked() && !cbshape01.isChecked() && !cbshape02.isChecked() && !cbshape03.isChecked()
                        && !cbshape04.isChecked() && !cbshape05.isChecked() && !cbshape06.isChecked()
                        && !cbshape07.isChecked() && !cbshape08.isChecked() && !cbshape09.isChecked()
                        && !cbshape10.isChecked() && !cbshape11.isChecked() && !cbshapeetc.isChecked()){
                    result +="none";
                    rawresult += "none";
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return result+" "+rawresult;
        }
    }
    public String ColorChecked(View v) {
        String result = "";
        String rawresult ="";
        if (cbwholecolor.isChecked()) {
            result += "&colors=";
            rawresult += "전체";
            return result+" "+rawresult;
        } else {
            try {
                if (cbwhite.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("하양", "UTF-8");
                    rawresult += "하양,";
                }
                if (cbyellow.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("노랑", "UTF-8");
                    rawresult += "노랑,";
                }
                if (cborange.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("주황", "UTF-8");
                    rawresult += "주황,";
                }
                if (cbpink.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("분홍", "UTF-8");
                    rawresult += "분홍,";
                }
                if (cbred.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("빨강", "UTF-8");
                    rawresult += "빨강,";
                }
                if (cbbrown.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("갈색", "UTF-8");
                    rawresult += "갈색,";
                }
                if (cbygreen.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("연두", "UTF-8");
                    rawresult += "연두,";
                }
                if (cbgreen.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("초록", "UTF-8");
                    rawresult += "초록,";
                }
                if (cbbgreen.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("청록", "UTF-8");
                    rawresult += "청록,";
                }
                if (cbblue.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("파랑", "UTF-8");
                    rawresult += "파랑,";
                }
                if (cbnavy.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("남색", "UTF-8");
                    rawresult += "남색,";
                }
                if (cbwine.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("자주", "UTF-8");
                    rawresult += "자주,";
                }
                if (cbpurple.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("보라", "UTF-8");
                    rawresult += "보라,";
                }
                if (cbgrey.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("회색", "UTF-8");
                    rawresult += "회색,";
                }
                if (cbblack.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("검정", "UTF-8");
                    rawresult += "검정,";
                }
                if (cbtransp.isChecked()) {
                    result += "&colors=" + URLEncoder.encode("투명", "UTF-8");
                    rawresult += "투명,";
                }
                if(!cbwholecolor.isChecked() && !cbwhite.isChecked() && !cbyellow.isChecked() && !cborange.isChecked() && !cbpink.isChecked()
                        && !cbred.isChecked() && !cbbrown.isChecked() && !cbygreen.isChecked() && !cbgreen.isChecked()
                        && !cbbgreen.isChecked() && !cbblue.isChecked() && !cbnavy.isChecked() && !cbwine.isChecked()
                        && !cbpurple.isChecked() && !cbgrey.isChecked() && !cbblack.isChecked() && !cbtransp.isChecked()){
                    result += "none";
                    rawresult += "none";
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return result+" "+rawresult;
        }
    }
    public String FormChecked(View v) {
        String result = "";
        String rawresult ="";
        if(cbwholetype.isChecked()){
            result += "&forms=";
            rawresult += "전체";
            return result+" "+rawresult;
        }
        else{
            try{
                if(cbtype01.isChecked()) {
                    result += "&forms=" + URLEncoder.encode("정제", "UTF-8");
                    rawresult += "정제,";
                }
                if(cbtype02.isChecked()) {
                    result += "&forms=" + URLEncoder.encode("경질캡슐", "UTF-8");
                    rawresult += "경질캡슐,";
                }
                if(cbtype03.isChecked()) {
                    result += "&forms=" + URLEncoder.encode("연질캡슐", "UTF-8");
                    rawresult += "연질캡슐,";
                }
                if(cbtypeetc.isChecked()) {
                    result += "&forms=" + URLEncoder.encode("기타", "UTF-8");
                    rawresult += "기타,";
                }
                if(!cbwholetype.isChecked() && !cbtype01.isChecked() && !cbtype02.isChecked() && !cbtype03.isChecked() && !cbtypeetc.isChecked()){
                    result +="none";
                    rawresult += "none";
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return result+" "+rawresult;
        }
    }
    public String LineChecked(View v) {
        String result = "";
        String rawresult = "";
        if (cbwholeline.isChecked()) {
            result += "&lines=";
            rawresult += "전체";
            return result+" "+rawresult;
        } else {
            if (cbno.isChecked()) {
                result += "&lines=no";
                rawresult += "분할선없음,";
            }
            if (cbplus.isChecked()) {
                result += "&lines=plus";
                rawresult += "(+)모양,";
            }
            if (cbminus.isChecked()) {
                result += "&lines=minus";
                rawresult += "(-)모양,";
            }
            if (cblineetc.isChecked()) {
                result += "&lines=etc";
                rawresult += "기타,";
            }
            if(!cbwholeline.isChecked() && !cbno.isChecked() && !cbplus.isChecked() && !cbminus.isChecked() && !cblineetc.isChecked()){
                result +="none";
                rawresult += "none";
            }
            return result+" "+rawresult;
        }
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btOK) { //button_result 이라는 버튼이 생성되었다고 가정.
            String error_shape = ShapeChecked(v);
            String error_color = ColorChecked(v);
            String error_form = FormChecked(v);
            String error_line = LineChecked(v);
            String error_toast = "";
            String formdata = null;
            String[] shape = new String[]{"&shapes=", "전체선택"};
            String[] color = new String[]{"&colors=", "전체선택"};
            String[] form = new String[]{"&forms=", "전체선택"};
            String[] line = new String[]{"&lines=", "전체선택"};
            if (error_shape.equals("none none")) {
                error_toast += "모양 ";
            }
            else {
                shape = error_shape.split(" ");
                Log.d("들어갔나요", shape[0] + "ㅎㅎㅎㅎ" + shape[1]);

            }
            if (error_color.equals("none none")) {
                error_toast += "색깔 ";
            }
            else {
                color = error_color.split(" ");
                Log.d("들어갔나요", color[0] + "ㅎㅎㅎㅎ" + color[1]);
            }
            if (error_form.equals("none none")) {
                error_toast += "제형 ";
            } else {
                form = error_form.split(" ");
                Log.d("들어갔나요", form[0] + "ㅎㅎㅎㅎ" + form[1]);
            }
            if (error_line.equals("none none")) {
                error_toast += "분할선 ";
            } else {
                line = error_line.split(" ");
                Log.d("들어갔나요", line[0] + "ㅎㅎㅎㅎ" + line[1]);
            }
            if(!error_toast.equals("")) {
                Toast.makeText(getApplicationContext(), error_toast + "이(가) 자동으로 전체선택되었습니다.", Toast.LENGTH_LONG).show();
            }
            formdata = "search_detail=Y&TabState=0&proYN=" +
                    "&rowLength=50&printList=&fixvalue_all=&fixvalue_y=&fixvalue_n=&item_ingr=&pageSize=&pageNum=&produceKind=" +
                    "&search_mark=" +
                    "&drug_name=&firm_name=" +
                    shape[0] + color[0] + form[0] + line[0];
            if(frontresult!=null){formdata=formdata+frontresult;}
            if(backresult!=null){formdata=formdata+backresult;}
            Log.d("formdata 체크", formdata);
            Intent gotoshapeList = new Intent(C_Shape.this, C_Shape_List.class);
            gotoshapeList.putExtra("formdata", formdata);
            gotoshapeList.putExtra("data", "모양 : " + shape[1] + "  색깔 : " + color[1] + "\n제형 : " + form[1] + "  분할선 : " + line[1]);
            startActivity(gotoshapeList);
        }
        if (v.getId() == R.id.ivshape01) {
            if (cbshape01.isChecked()) {cbshape01.setChecked(false);}else{cbshape01.setChecked(true);}
        }
        if (v.getId() == R.id.ivshape02) {
            if (cbshape02.isChecked()) {cbshape02.setChecked(false);}else{cbshape02.setChecked(true);}
        }
        if (v.getId() == R.id.ivshape03) {
            if (cbshape03.isChecked()) {cbshape03.setChecked(false);}else{cbshape03.setChecked(true);}
        }
        if (v.getId() == R.id.ivshape04) {
            if (cbshape04.isChecked()) {cbshape04.setChecked(false);}else{cbshape04.setChecked(true);}
        }
        if (v.getId() == R.id.ivshape05) {
            if (cbshape05.isChecked()) {cbshape05.setChecked(false);}else{cbshape05.setChecked(true);}
        }
        if (v.getId() == R.id.ivshape06) {
            if (cbshape06.isChecked()) {cbshape06.setChecked(false);}else{cbshape06.setChecked(true);}
        }
        if (v.getId() == R.id.ivshape07) {
            if (cbshape07.isChecked()) {cbshape07.setChecked(false);}else{cbshape07.setChecked(true);}
        }
        if (v.getId() == R.id.ivshape08) {
            if (cbshape08.isChecked()) {cbshape08.setChecked(false);}else{cbshape08.setChecked(true);}
        }
        if (v.getId() == R.id.ivshape09) {
            if (cbshape09.isChecked()) {cbshape09.setChecked(false);}else{cbshape09.setChecked(true);}
        }
        if (v.getId() == R.id.ivshape10) {
            if (cbshape10.isChecked()) {cbshape10.setChecked(false);}else{cbshape10.setChecked(true);}
        }
        if (v.getId() == R.id.ivshape11) {
            if (cbshape11.isChecked()) {cbshape11.setChecked(false);}else{cbshape11.setChecked(true);}
        }
        if (v.getId() == R.id.ivetcshape) {
            if (cbshapeetc.isChecked()) {cbshapeetc.setChecked(false);}else{cbshapeetc.setChecked(true);}
        }
        if (v.getId() == R.id.ivwhite) {
            if (cbwhite.isChecked()) {cbwhite.setChecked(false);}else{cbwhite.setChecked(true);}
        }
        if (v.getId() == R.id.ivyellow) {
            if (cbyellow.isChecked()) {cbyellow.setChecked(false);}else{cbyellow.setChecked(true);}
        }
        if (v.getId() == R.id.ivorange) {
            if (cborange.isChecked()) {cborange.setChecked(false);}else{cborange.setChecked(true);}
        }
        if (v.getId() == R.id.ivpink) {
            if (cbpink.isChecked()) {cbpink.setChecked(false);}else{cbpink.setChecked(true);}
        }
        if (v.getId() == R.id.ivred) {
            if (cbred.isChecked()) {cbred.setChecked(false);}else{cbred.setChecked(true);}
        }
        if (v.getId() == R.id.ivbrown) {
            if (cbbrown.isChecked()) {cbbrown.setChecked(false);}else{cbbrown.setChecked(true);}
        }
        if (v.getId() == R.id.ivygreen) {
            if (cbygreen.isChecked()) {cbygreen.setChecked(false);}else{cbygreen.setChecked(true);}
        }
        if (v.getId() == R.id.ivgreen) {
            if (cbgreen.isChecked()) {cbgreen.setChecked(false);}else{cbgreen.setChecked(true);}
        }
        if (v.getId() == R.id.ivblue) {
            if (cbblue.isChecked()) {cbblue.setChecked(false);}else{cbblue.setChecked(true);}
        }
        if (v.getId() == R.id.ivbgreen) {
            if (cbbgreen.isChecked()) {cbbgreen.setChecked(false);}else{cbbgreen.setChecked(true);}
        }
        if (v.getId() == R.id.ivnavy) {
            if (cbnavy.isChecked()) {cbnavy.setChecked(false);}else{cbnavy.setChecked(true);}
        }
        if (v.getId() == R.id.ivwine) {
            if (cbwine.isChecked()) {cbwine.setChecked(false);}else{cbwine.setChecked(true);}
        }
        if (v.getId() == R.id.ivpurple) {
            if (cbpurple.isChecked()) {cbpurple.setChecked(false);}else{cbpurple.setChecked(true);}
        }
        if (v.getId() == R.id.ivgrey) {
            if (cbgrey.isChecked()) {cbgrey.setChecked(false);}else{cbgrey.setChecked(true);}
        }
        if (v.getId() == R.id.ivblack) {
            if (cbblack.isChecked()) {cbblack.setChecked(false);}else{cbblack.setChecked(true);}
        }
        if (v.getId() == R.id.ivtransp) {
            if (cbtransp.isChecked()) {cbtransp.setChecked(false);}else{cbtransp.setChecked(true);}
        }
        if (v.getId() == R.id.ivtype01) {
            if (cbtype01.isChecked()) {cbtype01.setChecked(false);}else{cbtype01.setChecked(true);}
        }
        if (v.getId() == R.id.ivtype02) {
            if (cbtype02.isChecked()) {cbtype02.setChecked(false);}else{cbtype02.setChecked(true);}
        }
        if (v.getId() == R.id.ivtype03) {
            if (cbtype03.isChecked()) {cbtype03.setChecked(false);}else{cbtype03.setChecked(true);}
        }
        if (v.getId() == R.id.ivetctype) {
            if (cbtypeetc.isChecked()) {cbtypeetc.setChecked(false);}else{cbtypeetc.setChecked(true);}
        }
        if (v.getId() == R.id.ivno) {
            if (cbno.isChecked()) {cbno.setChecked(false);}else{cbno.setChecked(true);}
        }
        if (v.getId() == R.id.ivplus) {
            if (cbplus.isChecked()) {cbplus.setChecked(false);}else{cbplus.setChecked(true);}
        }
        if (v.getId() == R.id.ivminus) {
            if (cbminus.isChecked()) {cbminus.setChecked(false);}else{cbminus.setChecked(true);}
        }
        if (v.getId() == R.id.ivetcline) {
            if (cblineetc.isChecked()) {cblineetc.setChecked(false);}else{cblineetc.setChecked(true);}
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()){
            case R.id.cbwholeshape :
                if(isChecked){
                    if(!cbshape01.isChecked()) cbshape01.setChecked(isChecked);
                    if(!cbshape02.isChecked()) cbshape02.setChecked(isChecked);
                    if(!cbshape03.isChecked()) cbshape03.setChecked(isChecked);
                    if(!cbshape04.isChecked()) cbshape04.setChecked(isChecked);
                    if(!cbshape05.isChecked()) cbshape05.setChecked(isChecked);
                    if(!cbshape06.isChecked()) cbshape06.setChecked(isChecked);
                    if(!cbshape07.isChecked()) cbshape07.setChecked(isChecked);
                    if(!cbshape08.isChecked()) cbshape08.setChecked(isChecked);
                    if(!cbshape09.isChecked()) cbshape09.setChecked(isChecked);
                    if(!cbshape10.isChecked()) cbshape10.setChecked(isChecked);
                    if(!cbshape11.isChecked()) cbshape11.setChecked(isChecked);
                    if(!cbshapeetc.isChecked()) cbshapeetc.setChecked(isChecked);
                }
                else{
                    if(cbwholeshape.isPressed()){
                        cbshape01.setChecked(isChecked);
                        cbshape02.setChecked(isChecked);
                        cbshape03.setChecked(isChecked);
                        cbshape04.setChecked(isChecked);
                        cbshape05.setChecked(isChecked);
                        cbshape06.setChecked(isChecked);
                        cbshape07.setChecked(isChecked);
                        cbshape08.setChecked(isChecked);
                        cbshape09.setChecked(isChecked);
                        cbshape10.setChecked(isChecked);
                        cbshape11.setChecked(isChecked);
                        cbshapeetc.setChecked(isChecked);
                    }
                }
                break;
            case R.id.cbshape01 :
                if(cbshape01.isChecked() && cbshape02.isChecked() && cbshape03.isChecked()
                        && cbshape04.isChecked() && cbshape05.isChecked() && cbshape06.isChecked()
                        && cbshape07.isChecked() && cbshape08.isChecked() && cbshape09.isChecked()
                        && cbshape10.isChecked() && cbshape11.isChecked() && cbshapeetc.isChecked()){
                    cbwholeshape.setChecked(true);
                }
                else{
                    cbwholeshape.setChecked(false);
                }
                break;
            case R.id.cbshape02 :
                if(cbshape01.isChecked() && cbshape02.isChecked() && cbshape03.isChecked()
                        && cbshape04.isChecked() && cbshape05.isChecked() && cbshape06.isChecked()
                        && cbshape07.isChecked() && cbshape08.isChecked() && cbshape09.isChecked()
                        && cbshape10.isChecked() && cbshape11.isChecked() && cbshapeetc.isChecked()){
                    cbwholeshape.setChecked(true);
                }
                else{
                    cbwholeshape.setChecked(false);
                }
                break;
            case R.id.cbshape03 :
                if(cbshape01.isChecked() && cbshape02.isChecked() && cbshape03.isChecked()
                        && cbshape04.isChecked() && cbshape05.isChecked() && cbshape06.isChecked()
                        && cbshape07.isChecked() && cbshape08.isChecked() && cbshape09.isChecked()
                        && cbshape10.isChecked() && cbshape11.isChecked() && cbshapeetc.isChecked()){
                    cbwholeshape.setChecked(true);
                }
                else{
                    cbwholeshape.setChecked(false);
                }
                break;
            case R.id.cbshape04 :
                if(cbshape01.isChecked() && cbshape02.isChecked() && cbshape03.isChecked()
                        && cbshape04.isChecked() && cbshape05.isChecked() && cbshape06.isChecked()
                        && cbshape07.isChecked() && cbshape08.isChecked() && cbshape09.isChecked()
                        && cbshape10.isChecked() && cbshape11.isChecked() && cbshapeetc.isChecked()){
                    cbwholeshape.setChecked(true);
                }
                else{
                    cbwholeshape.setChecked(false);
                }
                break;
            case R.id.cbshape05 :
                if(cbshape01.isChecked() && cbshape02.isChecked() && cbshape03.isChecked()
                        && cbshape04.isChecked() && cbshape05.isChecked() && cbshape06.isChecked()
                        && cbshape07.isChecked() && cbshape08.isChecked() && cbshape09.isChecked()
                        && cbshape10.isChecked() && cbshape11.isChecked() && cbshapeetc.isChecked()){
                    cbwholeshape.setChecked(true);
                }
                else{
                    cbwholeshape.setChecked(false);
                }
                break;
            case R.id.cbshape06 :
                if(cbshape01.isChecked() && cbshape02.isChecked() && cbshape03.isChecked()
                        && cbshape04.isChecked() && cbshape05.isChecked() && cbshape06.isChecked()
                        && cbshape07.isChecked() && cbshape08.isChecked() && cbshape09.isChecked()
                        && cbshape10.isChecked() && cbshape11.isChecked() && cbshapeetc.isChecked()){
                    cbwholeshape.setChecked(true);
                }
                else{
                    cbwholeshape.setChecked(false);
                }
                break;
            case R.id.cbshape07 :
                if(cbshape01.isChecked() && cbshape02.isChecked() && cbshape03.isChecked()
                        && cbshape04.isChecked() && cbshape05.isChecked() && cbshape06.isChecked()
                        && cbshape07.isChecked() && cbshape08.isChecked() && cbshape09.isChecked()
                        && cbshape10.isChecked() && cbshape11.isChecked() && cbshapeetc.isChecked()){
                    cbwholeshape.setChecked(true);
                }
                else{
                    cbwholeshape.setChecked(false);
                }
                break;
            case R.id.cbshape08 :
                if(cbshape01.isChecked() && cbshape02.isChecked() && cbshape03.isChecked()
                        && cbshape04.isChecked() && cbshape05.isChecked() && cbshape06.isChecked()
                        && cbshape07.isChecked() && cbshape08.isChecked() && cbshape09.isChecked()
                        && cbshape10.isChecked() && cbshape11.isChecked() && cbshapeetc.isChecked()){
                    cbwholeshape.setChecked(true);
                }
                else{
                    cbwholeshape.setChecked(false);
                }
                break;
            case R.id.cbshape09 :
                if(cbshape01.isChecked() && cbshape02.isChecked() && cbshape03.isChecked()
                        && cbshape04.isChecked() && cbshape05.isChecked() && cbshape06.isChecked()
                        && cbshape07.isChecked() && cbshape08.isChecked() && cbshape09.isChecked()
                        && cbshape10.isChecked() && cbshape11.isChecked() && cbshapeetc.isChecked()){
                    cbwholeshape.setChecked(true);
                }
                else{
                    cbwholeshape.setChecked(false);
                }
                break;
            case R.id.cbshape10 :
                if(cbshape01.isChecked() && cbshape02.isChecked() && cbshape03.isChecked()
                        && cbshape04.isChecked() && cbshape05.isChecked() && cbshape06.isChecked()
                        && cbshape07.isChecked() && cbshape08.isChecked() && cbshape09.isChecked()
                        && cbshape10.isChecked() && cbshape11.isChecked() && cbshapeetc.isChecked()){
                    cbwholeshape.setChecked(true);
                }
                else{
                    cbwholeshape.setChecked(false);
                }
                break;
            case R.id.cbshape11 :
                if(cbshape01.isChecked() && cbshape02.isChecked() && cbshape03.isChecked()
                        && cbshape04.isChecked() && cbshape05.isChecked() && cbshape06.isChecked()
                        && cbshape07.isChecked() && cbshape08.isChecked() && cbshape09.isChecked()
                        && cbshape10.isChecked() && cbshape11.isChecked() && cbshapeetc.isChecked()){
                    cbwholeshape.setChecked(true);
                }
                else{
                    cbwholeshape.setChecked(false);
                }
                break;
            case R.id.cbetcshape :
                if(cbshape01.isChecked() && cbshape02.isChecked() && cbshape03.isChecked()
                        && cbshape04.isChecked() && cbshape05.isChecked() && cbshape06.isChecked()
                        && cbshape07.isChecked() && cbshape08.isChecked() && cbshape09.isChecked()
                        && cbshape10.isChecked() && cbshape11.isChecked() && cbshapeetc.isChecked()){
                    cbwholeshape.setChecked(true);
                }
                else{
                    cbwholeshape.setChecked(false);
                }
                break;        }
        switch (buttonView.getId()) {
            case R.id.cbwholecolor:
                if (isChecked) {
                    if (!cbwhite.isChecked()) cbwhite.setChecked(isChecked);
                    if (!cbyellow.isChecked()) cbyellow.setChecked(isChecked);
                    if (!cborange.isChecked()) cborange.setChecked(isChecked);
                    if (!cbpink.isChecked()) cbpink.setChecked(isChecked);
                    if (!cbred.isChecked()) cbred.setChecked(isChecked);
                    if (!cbbrown.isChecked()) cbbrown.setChecked(isChecked);
                    if (!cbygreen.isChecked()) cbygreen.setChecked(isChecked);
                    if (!cbgreen.isChecked()) cbgreen.setChecked(isChecked);
                    if (!cbbgreen.isChecked()) cbbgreen.setChecked(isChecked);
                    if (!cbblue.isChecked()) cbblue.setChecked(isChecked);
                    if (!cbnavy.isChecked()) cbnavy.setChecked(isChecked);
                    if (!cbwine.isChecked()) cbwine.setChecked(isChecked);
                    if (!cbpurple.isChecked()) cbpurple.setChecked(isChecked);
                    if (!cbgrey.isChecked()) cbgrey.setChecked(isChecked);
                    if (!cbblack.isChecked()) cbblack.setChecked(isChecked);
                    if (!cbtransp.isChecked()) cbtransp.setChecked(isChecked);

                } else {
                    if (cbwholecolor.isPressed()) {
                        cbwhite.setChecked(isChecked);
                        cbyellow.setChecked(isChecked);
                        cborange.setChecked(isChecked);
                        cbpink.setChecked(isChecked);
                        cbred.setChecked(isChecked);
                        cbbrown.setChecked(isChecked);
                        cbygreen.setChecked(isChecked);
                        cbgreen.setChecked(isChecked);
                        cbbgreen.setChecked(isChecked);
                        cbblue.setChecked(isChecked);
                        cbnavy.setChecked(isChecked);
                        cbwine.setChecked(isChecked);
                        cbpurple.setChecked(isChecked);
                        cbgrey.setChecked(isChecked);
                        cbblack.setChecked(isChecked);
                        cbtransp.setChecked(isChecked);
                    }
                }
                break;
            case R.id.cbwhite:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;
            case R.id.cbyellow:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;
            case R.id.cborange:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;
            case R.id.cbpink:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;
            case R.id.cbred:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;
            case R.id.cbbrown:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;
            case R.id.cbygreen:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;
            case R.id.cbgreen:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;
            case R.id.cbbgreen:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;
            case R.id.cbblue:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;
            case R.id.cbnavy:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;
            case R.id.cbwine:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;
            case R.id.cbpurple:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;
            case R.id.cbgrey:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;
            case R.id.cbblack:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;
            case R.id.cbtransp:
                if (cbwhite.isChecked() && cbyellow.isChecked() && cborange.isChecked() && cbpink.isChecked()
                        && cbred.isChecked() && cbbrown.isChecked() && cbygreen.isChecked() && cbgreen.isChecked()
                        && cbbgreen.isChecked() && cbblue.isChecked() && cbnavy.isChecked() && cbwine.isChecked()
                        && cbpurple.isChecked() && cbgrey.isChecked() && cbblack.isChecked() && cbtransp.isChecked()) {
                    cbwholecolor.setChecked(true);
                } else {
                    cbwholecolor.setChecked(false);
                }
                break;

        }
        switch(buttonView.getId()){
            case R.id.cbwholetype :
                if(isChecked){
                    if(!cbtype01.isChecked()) cbtype01.setChecked(isChecked);
                    if(!cbtype02.isChecked()) cbtype02.setChecked(isChecked);
                    if(!cbtype03.isChecked()) cbtype03.setChecked(isChecked);
                    if(!cbtypeetc.isChecked()) cbtypeetc.setChecked(isChecked);
                }
                else{
                    if(cbwholetype.isPressed()){
                        cbtype01.setChecked(isChecked);
                        cbtype02.setChecked(isChecked);
                        cbtype03.setChecked(isChecked);
                        cbtypeetc.setChecked(isChecked);
                    }
                }
                break;
            case R.id.cbtype01 :
                if(cbtype01.isChecked() && cbtype02.isChecked() && cbtype03.isChecked() && cbtypeetc.isChecked()){
                    cbwholetype.setChecked(true);
                }
                else{
                    cbwholetype.setChecked(false);
                }
                break;
            case R.id.cbtype02 :
                if(cbtype01.isChecked() && cbtype02.isChecked() && cbtype03.isChecked() && cbtypeetc.isChecked()){
                    cbwholetype.setChecked(true);
                }
                else{
                    cbwholetype.setChecked(false);
                }
                break;
            case R.id.cbtype03 :
                if(cbtype01.isChecked() && cbtype02.isChecked() && cbtype03.isChecked() && cbtypeetc.isChecked()){
                    cbwholetype.setChecked(true);
                }
                else{
                    cbwholetype.setChecked(false);
                }
                break;
            case R.id.cbetctype :
                if(cbtype01.isChecked() && cbtype02.isChecked() && cbtype03.isChecked() && cbtypeetc.isChecked()){
                    cbwholetype.setChecked(true);
                }
                else{
                    cbwholetype.setChecked(false);
                }
                break;
        }
        switch (buttonView.getId()) {
            case R.id.cbwholeline:
                if (isChecked) {
                    if (!cbno.isChecked()) cbno.setChecked(isChecked);
                    if (!cbplus.isChecked()) cbplus.setChecked(isChecked);
                    if (!cbminus.isChecked()) cbminus.setChecked(isChecked);
                    if (!cblineetc.isChecked()) cblineetc.setChecked(isChecked);
                } else {
                    if (cbwholeline.isPressed()) {
                        cbno.setChecked(isChecked);
                        cbplus.setChecked(isChecked);
                        cbminus.setChecked(isChecked);
                        cblineetc.setChecked(isChecked);
                    }
                }
                break;
            case R.id.cbno:
                if (cbno.isChecked() && cbplus.isChecked() && cbminus.isChecked() && cblineetc.isChecked()) {
                    cbwholeline.setChecked(true);
                } else {
                    cbwholeline.setChecked(false);
                }
                break;
            case R.id.cbplus:
                if (cbno.isChecked() && cbplus.isChecked() && cbminus.isChecked() && cblineetc.isChecked()) {
                    cbwholeline.setChecked(true);
                } else {
                    cbwholeline.setChecked(false);
                }
                break;
            case R.id.cbminus:
                if (cbno.isChecked() && cbplus.isChecked() && cbminus.isChecked() && cblineetc.isChecked()) {
                    cbwholeline.setChecked(true);
                } else {
                    cbwholeline.setChecked(false);
                }
                break;
            case R.id.cbetcline:
                if (cbno.isChecked() && cbplus.isChecked() && cbminus.isChecked() && cblineetc.isChecked()) {
                    cbwholeline.setChecked(true);
                } else {
                    cbwholeline.setChecked(false);
                }
                break;
        }

    }
}