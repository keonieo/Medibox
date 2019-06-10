package com.recoder.medibox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class C_List_Adapter extends BaseAdapter {
    Context context;
    ArrayList<C_List_Element> list_elementArrayList = new ArrayList<>();

    public C_List_Adapter(Context context, int i, ArrayList<C_List_Element> list_elementArrayList) {
        this.context = context;
        this.list_elementArrayList = list_elementArrayList;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.name_element, null);
        }
        TextView element_name = (TextView) convertView.findViewById(R.id.tv_element_name);
        ImageView element_image = (ImageView) convertView.findViewById(R.id.iv_element_image);

        element_name.setText(list_elementArrayList.get(position).getMedicine_name());
        element_image.setImageBitmap(list_elementArrayList.get(position).getMedicine_image());

        return convertView;
    }
}