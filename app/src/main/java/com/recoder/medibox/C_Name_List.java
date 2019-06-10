package com.recoder.medibox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.view.View.GONE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class C_Name_List extends AppCompatActivity {

    C_List_Element click_listener = new C_List_Element(null,null,null);

    ArrayList<C_List_Element> oData = new ArrayList<>();
    private ListView medicine_ListView = null;

    TextView tv_search_num;
    TextView tv_search_info;
    String search = "";
    LinearLayout pagelayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("약품 추가");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        Intent intent = new Intent(this.getIntent());
        search = intent.getStringExtra("search");

        pagelayout=(LinearLayout)findViewById(R.id.pagelayout);
        pagelayout.setVisibility(GONE);

        tv_search_info = (TextView)findViewById(R.id.tv_search_info);
        tv_search_num = (TextView)findViewById(R.id.tv_search_num);
        tv_search_info.setText(search+"(으)로 검색한 결과입니다.");
        tv_search_num.setText("검색 중입니다. 잠시만 기다려 주세요.");

        try {
            String searchUrl = "http://localapi.health.kr:8090/totalProduceY.localapi?search_word=" +
                    URLEncoder.encode(search.replaceAll("밀리그람","").replaceAll("밀리그램".replaceAll("mg",""),""), "UTF-8") + "&search_flag=all&sunb_count=&callback=&_="; //타이레놀 고정 search로 바꾸기
            new getList().execute(searchUrl);
        } catch (UnsupportedEncodingException e) {
            Log.e("찍기", e.toString());
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_toolbar, menu) ;
        return true ;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                intent = new Intent(C_Name_List.this, B_List_Medicine.class);
                startActivity(intent);
                break;
            case R.id.menu_cancel:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class getList extends AsyncTask<String, Integer, String> {

        String[][] druglist;
        Bitmap[] img;
        int num = 0;

        @Override
        protected String doInBackground(String... search) {
            String contents = "존재하지 않는 결과 입니다.";


            boolean check = FALSE;
            try {
                E_ConnectServer FConnectServer = new E_ConnectServer();
                contents = FConnectServer.requestGet(search[0]);
                check = TRUE;

                //Log.d("찍기", contents);

                if (!check) return contents;
                else {
                    JSONArray jarray = new JSONArray(contents.replaceAll("\\(", "").replaceAll("\\)", ""));  // JSONArray 생성
                    druglist = new String[jarray.length()][4];
                    img = new Bitmap[jarray.length()];

                    num = jarray.length();

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject jObject = null;  // JSONObject 추출
                        jObject = jarray.getJSONObject(i);
                        druglist[i][0] = jObject.getString("drug_name");
                        druglist[i][1] = jObject.getString("drug_code");
                        druglist[i][3] = jObject.getString("pack_img");
                        try {
                            URL url = new URL(druglist[i][3]);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setDoInput(true);
                            conn.connect();

                            InputStream is = conn.getInputStream();
                            img[i] = BitmapFactory.decodeStream(is);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                Log.e("찍기", e.toString());
            } catch (JSONException e) {
                Log.e("찍기", e.toString());
            }
            return "0";
        }

        protected void onPostExecute(String data) {
            int count = 0;

            if (druglist != null) {
                if (data != null) {
                    for (int i = 0; i < druglist.length; i++) {
                        count++;
                        C_List_Element item = new C_List_Element(druglist[i][0], druglist[i][1], img[i]);
                        item.onClickListener = click_listener.onClickListener;
                        oData.add(item);
                    }
                    // ListView, Adapter 생성 및 연결 ------------------------
                }
                if (count != 0) {
                    tv_search_num.setText("총 " + count + "개의 검색결과가 있습니다.");

                    medicine_ListView = (ListView) findViewById(R.id.medicine_list_view);
                    C_List_Adapter oAdapter = new C_List_Adapter(getApplicationContext(), 0, oData);
                    medicine_ListView.setAdapter(oAdapter);
                    oAdapter.notifyDataSetChanged();

                    medicine_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(C_Name_List.this, D_SearchResult.class);
                            intent.putExtra("drug_code", druglist[position][1]);
                            intent.putExtra("drug_name", druglist[position][0]);
                            startActivity(intent);
                        }
                    });
                } else {
                    Intent intent = new Intent(C_Name_List.this, C_Nolist_Popup.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
}