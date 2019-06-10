package com.recoder.medibox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class B_Select_Add_Method extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_add_method);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("약품추가");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        ImageButton searchName = findViewById(R.id.go_name);
        ImageButton searchBarcode = findViewById(R.id.go_barcode);
        ImageButton searchShape = findViewById(R.id.go_shape);
        ImageButton AddDirect = findViewById(R.id.go_direct);

        searchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(B_Select_Add_Method.this, C_Name.class);
                startActivity(intent);
                finish();
            }
        });

        searchShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(B_Select_Add_Method.this, C_Shape.class);
                startActivity(intent);
                finish();
            }
        });

        AddDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(B_Select_Add_Method.this, C_Direct.class);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_cancel:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //C_Barcode 내용 삽입
    public void scanBarcode(View view){ new IntentIntegrator((Activity)this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String num_barcode;
        String num_product;

        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }

            else {
                num_barcode= result.getContents();

                //substring 해결하기 -- 수정완료
                if(num_barcode.length()==13) num_product=num_barcode.substring(0,11);
                else if(num_barcode.length()==14) num_product=num_barcode.substring(1,12);
                else if(num_barcode.substring(0,2).equalsIgnoreCase("01")) num_product=num_barcode.substring(3,14);
                else {
                    num_product=null;
                    Toast.makeText(this, "의약품의 바코드가 맞는지 다시 확인해주십시오.", Toast.LENGTH_LONG).show();
                    return;
                }

                //Xml 파싱
                boolean item_name = false;
                String item_name_val = null;

                if (Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }

                String key = "Oko%2BtmV0bs5qVS5X902wm1wU%2BdwWjNwxC%2BCs6J0637Cv0Tkl%2FAdYHae6SPOrrlL4MhORDluYibA%2FFUn3D8ZLgA%3D%3D";

                try {
                    URL url = new URL("http://apis.data.go.kr/1471057/MdcinPrductPrmisnInfoService/getMdcinPrductItem?serviceKey="
                            + key + "&pageNo=1&startPage=1&numOfRows=3&pageSize=3" + "&bar_code=" + URLEncoder.encode(num_barcode, "UTF-8"));

                    XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = parserCreator.newPullParser();

                    parser.setInput(url.openStream(), null);
                    int parserEvent = parser.getEventType();

                    while (parserEvent != XmlPullParser.END_DOCUMENT) {
                        switch (parserEvent) {
                            case XmlPullParser.START_TAG:
                                if (parser.getName().equalsIgnoreCase("ITEM_NAME")) {
                                    item_name = true;
                                }
                                break;

                            case XmlPullParser.TEXT:
                                if (item_name) {
                                    item_name_val = parser.getText();
                                    item_name = false;
                                }
                                break;
                        }
                        parserEvent = parser.next();
                    }
                    if (item_name_val.indexOf("(") != -1)
                        item_name_val = item_name_val.substring(0, item_name_val.indexOf("("));
                    else
                        item_name_val = item_name_val.replaceAll("밀리그람", "");
                } catch (Exception e) {
                    e.getStackTrace();
                }

                String drug_code = "약품을 찾을 수 없습니다.";
                try {
                    item_name_val = item_name_val.replaceAll("밀리그람","").replaceAll("밀리그램","");
                    String searchUrl = "http://localapi.health.kr:8090/totalProduceY.localapi?search_word="
                            + URLEncoder.encode(item_name_val, "UTF-8") + "&search_flag=all&sunb_count=&callback=&_=";
                    drug_code = new getCode().execute(item_name_val, searchUrl).get();
                } catch (Exception e) {
                    e.getStackTrace();
                }
                if(drug_code.equals("약품을 찾을 수 없습니다.")){
                    Intent intent = new Intent(B_Select_Add_Method.this,B_Popup_NoBarcode.class);
                    startActivity(intent);
                }
                else {
                    //액티비티 전환
                    Intent intent = new Intent(B_Select_Add_Method.this, D_SearchResult.class);
                    intent.putExtra("drug_code", String.valueOf(drug_code));
                    intent.putExtra("drug_name", String.valueOf(item_name_val));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                    finish();
                }
            }
        }

        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private static class getCode extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... search) {
            String contents = "0";
            String result = "";

            boolean check = FALSE;
            try {
                E_ConnectServer FConnectServer = new E_ConnectServer();
                contents = FConnectServer.requestGet(search[1]);
                check = TRUE;
            } catch (IOException e) {
                Log.e("약",e.toString());
            }

            if (!check) return contents;
            try {
                JSONArray jarray = new JSONArray(contents.replaceAll("\\(","").replaceAll("\\)",""));
                Log.d("RRRR",jarray.length()+"");
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = null;
                    Log.d("RRRRR","1"+i+"");
                    jObject = jarray.getJSONObject(i);
                    if (jObject.getString("drug_name").contains(search[0]))
                        result = jObject.getString("drug_code");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public void saveListInLocal(String string, String key) {
        SharedPreferences prefs = getSharedPreferences("MediBox", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(string);
        editor.putString(key, string);
        editor.apply();
    }
}
