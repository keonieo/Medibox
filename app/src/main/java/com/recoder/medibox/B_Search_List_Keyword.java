package com.recoder.medibox;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class B_Search_List_Keyword {

    public static ArrayList<B_List_Element> keyword_list(Context context, ArrayList<B_List_Element> element, String keyword, String method) {

        Log.d("methth_em", method);

        B_List_Element new_element = new B_List_Element(null, null, null, null, null, null, null, null, null);
        ArrayList<B_List_Element> searched_arr = new ArrayList<B_List_Element>();

        for (int i = 0; i < element.size(); i++) {
            if (element.get(i).medicine_name.contains(keyword) || element.get(i).medicine_effect.contains(keyword) || element.get(i).medicine_detail_effect.contains(keyword) || element.get(i).medicine_memo.contains(keyword)) {

                new_element = element.get(i);

                new_element.onClickListener = (View.OnClickListener) context;
                new_element.medicine_name = Emphasis(new_element.medicine_name,keyword, method);
                new_element.medicine_effect =  Emphasis(new_element.medicine_effect,keyword, method);
                new_element.medicine_detail_effect = Emphasis(new_element.medicine_detail_effect,keyword, method);
                new_element.medicine_memo = Emphasis(new_element.medicine_memo,keyword, method);

                new_element.realposition = i;

                searched_arr.add(new_element);
            }
        }

        return searched_arr;
    }

    public static String Emphasis(String resource, String keyword, String type) {
        String start;
        String end;

        if(type.equalsIgnoreCase("bold")){
            start="<b>";
            end="</b>";
        }

        else if(type.equalsIgnoreCase("color")){
            start="<font color='#EE0000'>";
            end="</font>";
        }

        else if(type.equalsIgnoreCase("underline")){
            start="<u>";
            end="</u>";
        }

        else{//기울임
            start="<i>";
            end="</i>";
        }

        return resource.replaceAll(keyword, start + keyword + end);
    }
}