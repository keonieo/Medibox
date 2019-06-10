package com.recoder.medibox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class B_Popup_NoBarcode extends AppCompatActivity {

    TextView tv_info;
    Button yes;
    Button no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.no_popup);

        tv_info = (TextView)findViewById(R.id.textView6);
        tv_info.setText("인식한 바코드에 해당하는 의약품이 존재하지 않습니다. 약품을 직접 추가하시겠습니까?\n\n 예를 누르면 직접입력으로 아니오를 누르면 보유약품목록으로 돌아갑니다.\n");

        yes = (Button)findViewById(R.id.bt_yes_direct);
        no = (Button)findViewById(R.id.bt_no_direct);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(B_Popup_NoBarcode.this, C_Direct.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(B_Popup_NoBarcode.this, B_List_Medicine.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
