package com.recoder.medibox;

import java.io.IOException;
import android.util.Log;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class E_ConnectServer {

    public String requestGet(String Url) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Url)
                .get()
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "eabeb30e-e9c9-4044-b6e8-06dfab7d1c97")
                .build();

        Response response = client.newCall(request).execute();

        Log.d("URL찍기",Url);

        return response.body().string();
    }
}