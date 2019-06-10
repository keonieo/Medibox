package com.recoder.medibox;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.view.View.GONE;

public class C_Shape_List extends AppCompatActivity implements View.OnClickListener {

    C_List_Element click_listener = new C_List_Element(null, null, null);

    ArrayList<C_List_Element> oData = new ArrayList<>();
    private ListView medicine_ListView = null;

    String[][] NameImg = new String[50][3];
    StringBuilder sb = new StringBuilder();
    Bitmap[] img = new Bitmap[50];

    TextView tv_search_num;
    TextView tvshaperesult;
    TextView tvpage;

    ImageButton go_back;
    ImageButton go_next;
    int pageNo = 1;
    boolean endpage;

    String[] search = new String[2];
    String rawformdata = null;
    String dataset ="";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list);
        tv_search_num = (TextView) findViewById(R.id.tv_search_num);
        tvshaperesult = (TextView) findViewById(R.id.tv_search_info);
        tvpage = (TextView)findViewById(R.id.tvpage);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("약품 추가");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        go_back = (ImageButton)findViewById(R.id.ibgo_back);
        go_next = (ImageButton)findViewById(R.id.ibgo_next);
        go_back.setImageResource(R.drawable.goback);
        go_back.setBackground(null);
        go_next.setImageResource(R.drawable.gonext);
        go_next.setBackground(null);
        go_back.setOnClickListener(this);
        go_next.setOnClickListener(this);
        Intent intent = getIntent();
        rawformdata = intent.getStringExtra("formdata");
        //Log.d("formdata들어왔는지",rawformdata);
        rawformdata = rawformdata + "&pageNo=" + pageNo;
        search = new String[]{rawformdata, "ok"};
        //String[] search = {"1", "", "", "정제", "완전원형", "on", "", "하양", "no"};
        //Log.d("아악ㅇ악", search[0] + search[1] + search[2] + search[3] + search[4] + search[5];
        tvpage.setText(pageNo+"");
        dataset = intent.getStringExtra("data");
        tvshaperesult.setText(dataset);
        new getHtml().execute(search);

        Loading();
    }
    public void Loading(){

        if((pageNo==1)&&endpage){
            go_back.setVisibility(View.INVISIBLE);
            go_next.setVisibility(View.INVISIBLE);
        }
        else if((pageNo==1)&&!endpage){
            go_back.setVisibility(View.INVISIBLE);
            go_next.setVisibility(View.VISIBLE);
        }
        else if(pageNo!=1&&endpage){
            go_back.setVisibility(View.VISIBLE);
            go_next.setVisibility(View.INVISIBLE);
        }
        else{
            go_back.setVisibility(View.VISIBLE);
            go_next.setVisibility(View.VISIBLE);
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
                intent = new Intent(C_Shape_List.this, B_List_Medicine.class);
                startActivity(intent);
                break;
            case R.id.menu_cancel:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.ibgo_back){
            pageNo--;
            NameImg = new String[50][3];
            sb.delete(0,sb.length());
            img = new Bitmap[50];
            oData.clear();
            //Log.d("페이지번호",pageNo+"");
            Intent intent = getIntent();
            rawformdata = intent.getStringExtra("formdata");
           // Log.d("formdata들어왔는지",rawformdata);
            rawformdata = rawformdata + "&pageNo=" + pageNo;
            search = new String[]{rawformdata, "ok"};
            //String[] search = {"1", "", "", "정제", "완전원형", "on", "", "하양", "no"};
            //Log.d("아악ㅇ악", search[0] + search[1] + search[2] + search[3] + search[4] + search[5];
            tvpage.setText(pageNo+"");
            tvshaperesult.setText(intent.getStringExtra("data"));
            new getHtml().execute(search);

            Loading();

        }
        if(v.getId()==R.id.ibgo_next){
            pageNo++;
            NameImg = new String[50][3];
            sb.delete(0,sb.length());
            img = new Bitmap[50];
            oData.clear();
            //Log.d("페이지번호",pageNo+"");
            Intent intent = getIntent();
            rawformdata = intent.getStringExtra("formdata");
            //Log.d("formdata들어왔는지",rawformdata);
            rawformdata = rawformdata + "&pageNo=" + pageNo;
            search = new String[]{rawformdata, "ok"};
            //String[] search = {"1", "", "", "정제", "완전원형", "on", "", "하양", "no"};
            //Log.d("아악ㅇ악", search[0] + search[1] + search[2] + search[3] + search[4] + search[5];
            tvpage.setText(pageNo+"");
            tvshaperesult.setText(intent.getStringExtra("data"));
            new getHtml().execute(search);

            Loading();

        }

    }

    public class getHtml extends AsyncTask<String, Integer, String> {
        ProgressDialog asyncDialog = new ProgressDialog(C_Shape_List.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("정보를 불러오는 중 입니다... \n잠시만 기다려 주세요...");
            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... search) {
            //Log.d("들어와들어와 베이붸", search[0] + search[1] + search[2] + search[3] + search[4] + search[5]);
            for (int i = 0; i < 5; i++) {
                asyncDialog.setProgress(i * 30);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //0.5초
            }//0.5초 * 5

            String url = "https://www.health.kr/searchIdentity/search.asp";

            String formdata = search[0];
                        /*
                        "search_detail=Y&TabState=0&proYN=" +
                        "&rowLength=50&printList=&fixvalue_all=&fixvalue_y=&fixvalue_n=&item_ingr=&pageSize=&pageNum=&produceKind=" +
                        "&search_mark=" +
                        "&drug_name=&firm_name=" +
                        "&pageNo=1" +
                        "&drug_print_front=" + URLEncoder.encode(search[1], "UTF-8") +
                        "&drug_print_back=" + URLEncoder.encode(search[2], "UTF-8") +
                        "&forms=" + URLEncoder.encode(search[3], "UTF-8") +
                        "&shapes=" + URLEncoder.encode(search[4], "UTF-8") +
                        "&colors=" + URLEncoder.encode(search[7], "UTF-8") +
                        "&lines=" + URLEncoder.encode(search[8], "UTF-8") +
                        "&shapes_alt=" + URLEncoder.encode(search[5], "UTF-8") +
                        "&shapes_etc=" + URLEncoder.encode(search[6], "UTF-8");*/

           // Log.d("아 언제 되는거냐", formdata);

            try {
                URL obj = new URL(url);
                //연결
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Cache-Control", "no-cache");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("User-Agent", "PostmanRuntime/7.11.0");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setDefaultUseCaches(false);
                OutputStream out_stream = conn.getOutputStream();

                out_stream.write(formdata.getBytes("UTF-8"));
                out_stream.flush();
                out_stream.close();

                //1Byte 받음
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                String input = null;

                while ((input = in.readLine()) != null) {
                    sb.append(input);
                    //Log.d("파싱파싱", input.toString());
                }
              //  Log.d("파싱파싱", sb.toString().length() + "");

            } catch (IOException e) {
                e.printStackTrace();
            }
            String rawData = sb.toString();

            int n = 0;
            Document doc = Jsoup.parse(rawData);
            Elements drugImages = doc.select("img:not(img[name=img1])");
            for (Element oneImg : drugImages) {
                String rawdrugName = oneImg.attr("alt");
                //Log.d("이름",rawdrugName);
                String rawdrugImg = oneImg.attr("src");
                //Log.d("이미지",rawdrugImg);

                //A11A0650B0004
                if (rawdrugName.equals("의약품이미지") == false && rawdrugName != null && rawdrugName.equals("약학정보원") == false && rawdrugImg.contains("mark") == false && rawdrugImg.contains("https") == true) {
                    NameImg[n][0] = rawdrugName;
                    NameImg[n][1] = rawdrugImg;
                    NameImg[n][2] = rawdrugImg.substring(49, 62);
                    n++;
                }
            }

            for (int i = 0; i < n; i++) {
                try {
                    URL img_url = new URL(NameImg[i][1]);
                    HttpURLConnection conn = (HttpURLConnection) img_url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    img[i] = BitmapFactory.decodeStream(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

/*
            int ai = 0;
            while (ai < 50) {
                if (NameImg[ai][0] == null) {
                    Log.d("이제 약없어", "히히");
                    break;
                }
                Log.d("이름아악", NameImg[ai][0]);
                Log.d("링크아악", NameImg[ai][1]);
                Log.d("코드아악", NameImg[ai][2]);
                ai++;
            }
            //if(drugTable<50)이면 다음 페이지로 넘어가지 않게 함
*/
            return "0";
        }

        protected void onPostExecute(String data) {
            asyncDialog.dismiss();
            int count = 0;
            if (data != null) {
                for (int i = 0; i < NameImg.length; i++) {
                    if(NameImg[i][0]!=null){
                        count++;
                        C_List_Element item = new C_List_Element(NameImg[i][0], NameImg[i][2], img[i]);
                        item.onClickListener = click_listener.onClickListener;
                        oData.add(item);
                    }
                    //결과 50개 안될때 이거 풀면 오류임
                    //   Log.d("파싱", NameImg[i][0]);
                }
            }
            //Log.d("dd",count+"");
            if(0<count && count<50) {
                endpage = true;
                tv_search_num.setText("총 "+count+"개의 검색결과가 존재합니다");
                go_back.setVisibility(View.INVISIBLE); //고치기
            }
            else if(count==0){
                if(pageNo==2){
                    Toast.makeText(getApplicationContext(), "다음 페이지가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                    pageNo--;
                    NameImg = new String[50][3];
                    sb.delete(0,sb.length());
                    img = new Bitmap[50];
                    oData.clear();
                   // Log.d("페이지번호",pageNo+"");
                    Intent intent = getIntent();
                    rawformdata = intent.getStringExtra("formdata");
                   // Log.d("formdata들어왔는지",rawformdata);
                    rawformdata = rawformdata + "&pageNo=" + pageNo;
                    search = new String[]{rawformdata, "ok"};
                    //String[] search = {"1", "", "", "정제", "완전원형", "on", "", "하양", "no"};
                    //Log.d("아악ㅇ악", search[0] + search[1] + search[2] + search[3] + search[4] + search[5];
                    tvpage.setText(pageNo+"");
                    tvshaperesult.setText(intent.getStringExtra("data"));
                    new getHtml().execute(search);

                    Loading();
                }
                else{
                    Intent intent = new Intent(C_Shape_List.this, C_Nolist_Popup.class);
                    startActivity(intent);
                    finish();
                }
            }
            else{
                tv_search_num.setText("현재 페이지에 "+NameImg.length+"개의 검색결과가 존재합니다");
                endpage = false;
            }

            medicine_ListView = (ListView) findViewById(R.id.medicine_list_view);
            C_List_Adapter oAdapter = new C_List_Adapter(getApplicationContext(), 0, oData);
            medicine_ListView.setAdapter(oAdapter);
            oAdapter.notifyDataSetChanged();

            medicine_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(C_Shape_List.this, D_SearchResult.class);
                    intent.putExtra("drug_code", NameImg[position][2]);
                    intent.putExtra("drug_name", NameImg[position][0]);
                    startActivity(intent);
                }
            });
        }
    }
}