package com.recoder.medibox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.recoder.medibox.B_List_Element;
import com.recoder.medibox.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class B_List_Adapter extends BaseAdapter {

    LayoutInflater inflater = null;
    ArrayList<B_List_Element> list_elementArrayList = new ArrayList<>();
    boolean array_searched;

    public B_List_Adapter(ArrayList<B_List_Element> list_elementArrayList, boolean isSearch) {
        this.list_elementArrayList = list_elementArrayList;
        this.array_searched = isSearch;
    }

    @Override
    public int getCount() {
        return this.list_elementArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list_elementArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void control_vis(int position) {
        for(int i=0 ; i<list_elementArrayList.size(); i++)
            list_elementArrayList.get(i).isclicked = false;

        list_elementArrayList.get(position).isclicked = true;
        notifyDataSetChanged();
        Log.d("position", String.valueOf(position));
    }

    public void closeAll(){
        for(int i=0 ; i<list_elementArrayList.size(); i++)
            list_elementArrayList.get(i).isclicked = false;

        notifyDataSetChanged();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.list_element, parent, false);
        }

        TextView detail_effect = (TextView) convertView.findViewById(R.id.list_element_case_click_textview);
        TextView element_name = (TextView) convertView.findViewById(R.id.element_name);
        ImageView element_image = (ImageView) convertView.findViewById(R.id.element_image);
        TextView element_effect = (TextView) convertView.findViewById(R.id.element_effect);
        LinearLayout list_element_case_click = convertView.findViewById(R.id.list_element_case_click);
        LinearLayout list_element_case_delete = convertView.findViewById(R.id.list_element_case_delete);
        LinearLayout list_element_case_not_click = convertView.findViewById(R.id.list_element_case_not_click);

        Button delete = (Button) convertView.findViewById(R.id.bt_medicine_list_delete);
        Button edit = (Button)convertView.findViewById(R.id.bt_list_element_edit);
        Button bt_detail = (Button)convertView.findViewById(R.id.bt_list_element_detail_information);

        String pre ="";
        if(list_elementArrayList.get(position).CHECK.replaceAll("D","").equalsIgnoreCase("처방")) {
            pre = "<font color='#EE0000'> ※ 처방약입니다</font>\n\n";
        }


        if(list_elementArrayList.get(position).CHECK.contains("D"))
            bt_detail.setVisibility(GONE);
        else bt_detail.setVisibility(VISIBLE);

        detail_effect.setText(Html.fromHtml((pre+list_elementArrayList.get(position).getMedicine_detail_effect()+"\n\n메모 : "+list_elementArrayList.get(position).medicine_memo + "\n유통기한 :  "+list_elementArrayList.get(position).getDate().substring(0,4) +"년 "  +String.valueOf(Integer.parseInt(list_elementArrayList.get(position).getDate().substring(4,6)))+"월  "+String.valueOf(Integer.parseInt(list_elementArrayList.get(position).getDate().substring(6,8)))+"일").replaceAll("\n","<br />"), 1));
        element_name.setText(Html.fromHtml(list_elementArrayList.get(position).getMedicine_name().replace("\n","<br />"), 1));
        element_image.setImageBitmap(list_elementArrayList.get(position).getMedicine_pack_image());
        element_effect.setText(Html.fromHtml(list_elementArrayList.get(position).getMedicine_effect().replace("\n","<br />"), 1));

        //medicine_year.setText(list_elementArrayList.get(position).getDate().substring(0,4));
        //medicine_month.setText(String.valueOf(Integer.parseInt(list_elementArrayList.get(position).getDate().substring(4,6))));
        //medicine_day.setText(String.valueOf(Integer.parseInt(list_elementArrayList.get(position).getDate().substring(6,8))));
        //element_pre.setText(list_elementArrayList.get(position).getMedicine_check());

        if(isDateOver(list_elementArrayList.get(position).medicine_date) == true) {
            convertView.setBackgroundColor(Color.rgb(255, 163, 150));
        }
        else convertView.setBackgroundColor(Color.rgb(250, 250, 250));

        if (list_elementArrayList.get(position).isclicked == false) {
           // Log.d("하하",position+" ");
            list_element_case_click.setVisibility(GONE);
        }

        else {
            list_element_case_click.setVisibility(VISIBLE);
            list_elementArrayList.get(position).isclicked = true;
        }

        list_element_case_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list_elementArrayList.get(position).isclicked == true) {
                    list_element_case_click.setVisibility(GONE);
                    list_elementArrayList.get(position).isclicked = false;
                }
            }
        });

        list_element_case_not_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(list_elementArrayList.get(position).isclicked ==false){ //클릭이 되지 않았을 때
                    closeAll();
                    list_element_case_click.setVisibility(VISIBLE);
                    list_elementArrayList.get(position).isclicked = true;
                }

                else{
                    list_element_case_click.setVisibility(GONE);
                    list_elementArrayList.get(position).isclicked = false;
                }

            }
        });

        delete.setTag("1");
        edit.setTag("2");
        bt_detail.setTag("3");
        delete.setOnClickListener(list_elementArrayList.get(position).onClickListener);
        edit.setOnClickListener(list_elementArrayList.get(position).onClickListener);
        bt_detail.setOnClickListener(list_elementArrayList.get(position).onClickListener);

        if(array_searched == false) convertView.setTag("" + position);
        else convertView.setTag(list_elementArrayList.get(position).realposition+"&"+position);

        return convertView;
    }

    boolean isDateOver(String date){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        String year = yearFormat.format(currentTime);
        String month = monthFormat.format(currentTime);
        String day = dayFormat.format(currentTime);

        int today = Integer.parseInt(year+month+day);

        //Log.d("webnautes", year + "년 " + month + "월 " + day + "일 ");

        if(Integer.parseInt(date) < today) return true;

        else return false;
    }
}
