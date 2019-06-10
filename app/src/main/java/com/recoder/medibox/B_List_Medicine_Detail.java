package com.recoder.medibox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class B_List_Medicine_Detail extends AppCompatActivity {

    String drug_name = "오류가 발생했습니다";
    String drug_code = "오류가 발생했습니다";
    static String drug_effect;
    static String drug_deffect;
    Bitmap pack_img;
    static Bitmap drug_img;

    String ingredient = "";
    String effect = "";
    String dosage = "";
    String guide = "";
    String caution = "";
    TextView tv_drug_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_medicine_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("약품상세정보");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        //인텐트로 받은 barcode를 json parsing으로 보내 정보 받아오기
        Intent intent = new Intent(this.getIntent());
        drug_code = intent.getStringExtra("drug_code");
        drug_name = intent.getStringExtra("drug_name");

        String drug_info = null;

        //drugcode - druginfo
        try {
            String searchUrl = "http://localapi.health.kr:8090/result_drug.localapi?drug_cd=" +
                    URLEncoder.encode(drug_code, "UTF-8") + "&callback=";

            Log.d("파싱", searchUrl);
            drug_info = new getInfo().execute(searchUrl).get();
        } catch (Exception e) {
            e.getStackTrace();
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                Log.d("position", String.valueOf(pos));
                changeView(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // do nothing
            }
        });

        //레이아웃 정의
        tv_drug_info = (TextView) findViewById(R.id.tv_drug_info);
        tv_drug_info.setMovementMethod(new ScrollingMovementMethod());
        TextView DrugName = (TextView) findViewById(R.id.tv_drug_name);
        ImageView DrugImg = (ImageView) findViewById(R.id.iv_drug);
        ImageView PackImg = (ImageView) findViewById(R.id.iv_pack);

        //data set
        DrugName.setText(drug_name);
        tv_drug_info.setText(drug_info);
        DrugImg.setImageBitmap(drug_img);
        PackImg.setImageBitmap(pack_img);
    }

    private void changeView(int index) {

        switch (index) {
            case 0:
                tv_drug_info.scrollTo(0, 0);
                tv_drug_info.setText(ingredient + "\n" + effect);
                break;
            case 1:
                tv_drug_info.scrollTo(0, 0);
                tv_drug_info.setText(dosage + "\n" + guide);
                break;
            case 2:
                tv_drug_info.scrollTo(0, 0);
                tv_drug_info.setText(caution);
                break;
            case 3:
                tv_drug_info.scrollTo(0, 0);
                tv_drug_info.setText(ingredient + "\n" + effect + "\n" + dosage + "\n" + guide + "\n" + caution);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                intent = new Intent(B_List_Medicine_Detail.this, B_List_Medicine.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menu_add:
                intent = new Intent(B_List_Medicine_Detail.this, B_Select_Add_Method.class);
                startActivity(intent);
                break;
            case R.id.menu_setting:
                intent = new Intent(B_List_Medicine_Detail.this, F_Setting.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
}

    private class getInfo extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... search) {
            String contents = "null";
            boolean connect_check = FALSE;

            try {
                E_ConnectServer FConnectServer = new E_ConnectServer();
                contents = FConnectServer.requestGet(search[0]);
                connect_check = TRUE;
            } catch (IOException e) {
                Log.d("RR", e.toString());
            }
            if (!connect_check) return "존재하지 않는 결과입니다.";

            StringBuffer jsonData = new StringBuffer();

            try {
                JSONArray jarray = new JSONArray(contents.replaceAll("\\(", "").replaceAll("\\)", ""));
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = null;
                    jObject = jarray.getJSONObject(i);

                    drug_effect = jObject.getString("cls_code");

                    String img_url = jObject.getString("drug_pic");
                    String pack_url = img_url.contains("pack_img")?img_url.contains("|")?img_url.substring(0,img_url.indexOf("|")):img_url:null;
                    String drug_url = img_url.contains("images")?img_url.contains("|")?img_url.substring(img_url.indexOf("|")+1,img_url.length()):img_url:null;


                    try {
                        URL url = new URL(drug_url);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        drug_img = BitmapFactory.decodeStream(is);

                        url = new URL(pack_url);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        is = conn.getInputStream();
                        pack_img = BitmapFactory.decodeStream(is);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String dur_name[] = {"연령금지 : ", " : ", "임부금기 : ", "노인주의 : ", " : ", "투여기간주의 : ", "헌혈금지 : "};
                    String dur_info[] = {jObject.getString("dur_age"), jObject.getString("dur_contra"), jObject.getString("dur_preg"),
                            jObject.getString("dur_senior"), jObject.getString("dur_period"), jObject.getString("dur_dose"), jObject.getString("dur_donate")};

                    String dur = "DUR정보"; //String정리
                    for (int j = 0; j < dur_name.length; j++)
                        dur += "\n" + dur_name[j] + dur_info[j];

                    String stmt = "\n[저장방법] " + jObject.getString("stmt") + "\n";


                    String str = jObject.getString("sunb").replaceAll("|", "");
                    while (str.indexOf("@") != -1) {
                        String element = str.substring(str.indexOf("<") + 1, str.indexOf("@"));
                        element = element.substring(element.indexOf(">") + 1, element.indexOf("<"));
                        ingredient += element;
                        str = str.substring(str.indexOf("@") + 1, str.length());
                    }
                    ingredient = "\n[성분]\n\n" + ingredient;
                    //ingredient 왜 괄호가 안 출력되는지는 모르겠음 그거 제외 끝

                    effect = jObject.getString("effect");
                    if (effect.indexOf("[") != -1)
                        effect = effect.substring(effect.indexOf("]") + 1, effect.length());
                    effect = "\n[효능,효과]\n\n" + effect.replaceAll("brbr", "\n");

                    dosage = jObject.getString("dosage");
                    if (dosage.indexOf("[") != -1)
                        dosage = dosage.substring(dosage.indexOf("]") + 1, dosage.length());
                    while (dosage.indexOf("<") != -1)
                        dosage = dosage.indexOf("<") <= 1 ? dosage.substring(dosage.indexOf(">") + 1, dosage.length()) :
                                dosage.substring(0, dosage.indexOf("<") - 1) + dosage.substring(dosage.indexOf(">") + 1, dosage.length());
                    dosage = "\n[용법용량]\n\n" + dosage.replaceAll("br", "\n");


                    guide = jObject.getString("mediguide");
                    while (guide.indexOf("<") != -1)
                        guide = guide.indexOf("<") <= 1 ? guide.substring(guide.indexOf(">") + 1, guide.length()) :
                                guide.substring(0, guide.indexOf("<") - 1) + guide.substring(guide.indexOf(">") + 1, guide.length());
                    guide = "\n[복약정보]\n\n" + guide.replaceAll("br", "");

                    caution = jObject.getString("caution").replaceAll("\r", "");
                    if (caution.indexOf("[") != -1)
                        caution = caution.substring(caution.indexOf("]") + 1, caution.length());
                    caution = "\n[사용 상의 주의사항]\n\n" + caution;

                    drug_deffect = effect + "\n" + dosage + "\n" + stmt;
                    //Log.d("으아아아",drug_deffect);
                    //Log.d("으아아아",jsonData.toString());
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return ingredient + "\n" + effect;
        }
    }
}