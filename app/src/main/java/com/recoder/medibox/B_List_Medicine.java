package com.recoder.medibox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class B_List_Medicine extends AppCompatActivity implements View.OnClickListener{

    long nowIndex;
    ArrayList<String> arrayIndex =  new ArrayList<String>();
    B_DbOpenHelper mDbOpenHelper;

    boolean isSearched;
    ListView listview;
    SearchView searchView;
    MenuItem searchItem;

    SharedPreferences prefs;

    TextView tv_empty;

    LinearLayout linear_list_notempty;
    View view_notempty;
    LinearLayout listview_notempty;

    ArrayList<B_List_Element> items;
    ArrayList<B_List_Element> searched;
    Context context;
    B_List_Adapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_medicine);

        prefs = getSharedPreferences("MediBox", Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("보유약품목록");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        context = this;

        mDbOpenHelper= new B_DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        items = getDataBases(prefs.getString("method_order", "_id"));
        if(items == null) items = new ArrayList<B_List_Element>();

        Log.d("size", String.valueOf(items.size()));

        for(int g=0;g<items.size();g++){
            Log.d("InMain"+g, items.get(g).medicine_name);
        }

        listview = (ListView)findViewById(R.id.listview);

        tv_empty = (TextView)findViewById(R.id.tv_list_isempty);

        linear_list_notempty = (LinearLayout)findViewById(R.id.linearlayout_list_notempty);
        view_notempty = (View)findViewById(R.id.view_list_notempty);
        listview_notempty = (LinearLayout)findViewById(R.id.listview_list_notempty);

        listModeSet(items.isEmpty());

        adapter = new B_List_Adapter(items, false);
        listview.setAdapter(adapter);
        adapter.list_elementArrayList = items;
        adapter.notifyDataSetChanged();

        isSearched= true;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.control_vis(position);
            }
        });
    }

    //추가된 소스, ToolBar에 menu.xml을 인플레이트함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_toolbar, menu);

        // 검색 버튼 클릭했을 때 searchview 길이 꽉차게 늘려주기
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setMaxWidth(Integer.MAX_VALUE);

        //검색 버튼 클릭했을 때 searchview에 힌트 추가
        searchView.setQueryHint("키워드로 검색합니다.");

        //검색 리스너 추가
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //검색어 입력시 이벤트 제어
            @Override
            public boolean onQueryTextChange(String s) {
                if(isSearched == true) {
                    items = getDataBases(prefs.getString("method_order", "_id"));
                    adapter.list_elementArrayList = items;
                    isSearched = false;
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
            //검색어 완료시 이벤트 제어 --> 간단하게 Toast 메세지 출력으로
            @Override
            public boolean onQueryTextSubmit(String keyword) {
                searched = new ArrayList<B_List_Element>();
                searched.clear();

                if(keyword.equalsIgnoreCase("")) {
                    Toast.makeText(B_List_Medicine.this, "검색할 단어를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return false;
                }

                searched = B_Search_List_Keyword.keyword_list(context, items, keyword,prefs.getString("method_emphasis", "color"));
                isSearched = true;
                adapter.array_searched = true;
                adapter.list_elementArrayList = searched;
                adapter.notifyDataSetChanged();

                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if(isSearched == true) {
                    items = getDataBases(prefs.getString("method_order", "_id"));
                    adapter.list_elementArrayList = items;
                    isSearched = false;
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                listview.smoothScrollToPosition(0);
                break;
            case R.id.menu_add:
                Intent intent = new Intent(B_List_Medicine.this, B_Select_Add_Method.class);
                startActivity(intent);
                break;
            case R.id.menu_setting:
                intent = new Intent(B_List_Medicine.this, F_Setting.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        int nViewTag = Integer.parseInt((String)v.getTag());
        View oParentView = (View)v.getParent();
        int position;
        int realposition;
        String str_position;
        switch (nViewTag)
        {
            case 1: // delete
                Log.d("clicked", "delete");
                oParentView = (View)oParentView .getParent().getParent().getParent().getParent();
                str_position = oParentView.getTag().toString();

                if(str_position.contains("&")){
                    position = Integer.parseInt(str_position.substring(0,str_position.indexOf("&")));
                    realposition = Integer.parseInt(str_position.substring(str_position.indexOf("&")+1,str_position.length()));
                    Log.d("searchedposition", String.valueOf(position));
                    searched.remove(realposition); //-머땜시 에러....
                    nowIndex = Long.parseLong(arrayIndex.get(position));
                    mDbOpenHelper.deleteColumn(nowIndex);
                    adapter.list_elementArrayList = searched;
                    adapter.notifyDataSetChanged();
                }
                else {
                    realposition = Integer.parseInt((String) oParentView.getTag());
                    nowIndex = Long.parseLong(arrayIndex.get(realposition));
                    mDbOpenHelper.deleteColumn(nowIndex);
                    items = getDataBases(prefs.getString("method_order", "_id"));
                    adapter.list_elementArrayList = items;
                    adapter.notifyDataSetChanged();

                    listModeSet(items.isEmpty());
                }
                break;

            case 2: // edit
                Log.d("clicked", "edit");
                oParentView = (View) oParentView.getParent().getParent().getParent().getParent();
                str_position = oParentView.getTag().toString();
                if (str_position.contains("&")) {
                    position = Integer.parseInt(str_position.substring(0, str_position.indexOf("&")));
                    realposition = Integer.parseInt(str_position.substring(str_position.indexOf("&") + 1, str_position.length()));
                    //검색일 때 동작
                } else position = Integer.parseInt((String) oParentView.getTag()); //검색아닐 때 동작
                nowIndex = Long.parseLong(arrayIndex.get(position));
                Intent ed_intent = new Intent(B_List_Medicine.this, B_Edit_Info.class);
                ed_intent.putExtra("id", String.valueOf(nowIndex));
                startActivity(ed_intent);
                break;

            case 3: // detail information
                Log.d("clicked", "detail information");
                oParentView = (View)oParentView .getParent().getParent().getParent().getParent();
                str_position = oParentView.getTag().toString();
                if(str_position.contains("&")) realposition = Integer.parseInt(str_position.substring(str_position.indexOf("&")+1,str_position.length()));
                else realposition = Integer.parseInt((String) oParentView.getTag());

                Intent intent = new Intent(B_List_Medicine.this,B_List_Medicine_Detail.class);
                Log.d("약코드",items.get(realposition).medicine_code);
                intent.putExtra("drug_code", items.get(realposition).medicine_code);
                intent.putExtra("drug_name", items.get(realposition).medicine_name);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(isSearched == true) {
            SharedPreferences prefs_ = getSharedPreferences("MediBox", Context.MODE_PRIVATE);
            items = getDataBases(prefs_.getString("method_order", "_id"));
            adapter.list_elementArrayList = items;
            isSearched = false;
            adapter.notifyDataSetChanged();
        }
        else {
            adapter.notifyDataSetChanged();
        }
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        }
        else super.onBackPressed();
    }

    public Bitmap stringToBitmap(String string){
        if(string !=null && !string.equalsIgnoreCase("")) return BitmapFactory.decodeByteArray(Base64.decode(string, Base64.NO_WRAP), 0, Base64.decode(string, Base64.NO_WRAP).length);
        return null;
    }

    public ArrayList<B_List_Element> getDataBases(String method){
        ArrayList<B_List_Element> array = new ArrayList<B_List_Element>();

        Cursor iCursor = mDbOpenHelper.sortColumn(method);
        if(iCursor==null) return null;

        Log.d("methth", method);

        Log.d("showDatabase", "DB Size: " + iCursor.getCount());

        arrayIndex.clear();

        while (iCursor.moveToNext()) {
            String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));
            String tempPackImg = iCursor.getString(iCursor.getColumnIndex("_packimg"));
            String tempName = iCursor.getString(iCursor.getColumnIndex("_name"));
            String tempEffect = iCursor.getString(iCursor.getColumnIndex("_effect"));
            String tempCheck = iCursor.getString(iCursor.getColumnIndex("_check"));
            String tempDrugImg = iCursor.getString(iCursor.getColumnIndex("_drugimg"));
            String tempCode = iCursor.getString(iCursor.getColumnIndex("_code"));
            String tempDate = iCursor.getString(iCursor.getColumnIndex("_date"));
            String tempMemo = iCursor.getString(iCursor.getColumnIndex("_memo"));
            String tempDEffect = iCursor.getString(iCursor.getColumnIndex("_deffect"));

            //(Bitmap medicine_pack_img, String medicine_name, String medicine_effect, String check, Bitmap medicine_drug_img, String date, String code, String memo, String medicine_detail_effect)
            B_List_Element new_ = new B_List_Element(stringToBitmap(tempPackImg), tempName, tempEffect, tempCheck, stringToBitmap(tempDrugImg), tempDate, tempCode, tempMemo, tempDEffect);

            new_.onClickListener = (View.OnClickListener) context;

            array.add(new_);
            arrayIndex.add(tempIndex);
        }
        return array;
    }

    void listModeSet (boolean isempty){
        if(isempty==true) {
            tv_empty.setVisibility(View.VISIBLE);
            linear_list_notempty.setVisibility(View.GONE);
            view_notempty.setVisibility(View.GONE);
            listview_notempty.setVisibility(View.GONE);
        }
        else {
            tv_empty.setVisibility(View.GONE);
            linear_list_notempty.setVisibility(View.VISIBLE);
            view_notempty.setVisibility(View.VISIBLE);
            listview_notempty.setVisibility(View.VISIBLE);

            adapter = new B_List_Adapter(items, false);
            listview.setAdapter(adapter);
            adapter.list_elementArrayList = items;
            adapter.notifyDataSetChanged();
        }
    }
}