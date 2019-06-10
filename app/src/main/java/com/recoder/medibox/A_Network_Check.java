package com.recoder.medibox;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class A_Network_Check extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_check);

        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();

        if(check_Net(this)==false)
            Toast.makeText(this, "인터넷 연결이 되지 않아 일부 기능 제한이 있을 수 있습니다", Toast.LENGTH_SHORT).show();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(A_Network_Check.this, B_List_Medicine.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }, 1000);

    }

    public static boolean check_Net(Context context){

        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(manager.getActiveNetworkInfo()!=null)
            return true;

        else return false;
    }
}

