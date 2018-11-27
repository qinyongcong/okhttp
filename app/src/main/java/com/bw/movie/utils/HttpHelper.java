package com.bw.movie.utils;

import android.os.Handler;
import android.os.Message;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/*
 *作者：刘进
 *日期：2018/11/27
 **/
public class HttpHelper {
    private final int SUCCESS=1;//成功
    private final int ERROR=0;//失败

    public HttpHelper get(String url){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            doHttp(client, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    private void doHttp(OkHttpClient client, Request request) throws IOException {
        client.newCall(request).enqueue(new Callback() {
            final Message message = new Message();
            @Override
            public void onFailure(Request request, IOException e) {
                message.what=ERROR;
                message.obj=e.getMessage();//失败的信息
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String data = response.body().string();
                message.what=SUCCESS;
                message.obj=data;
                handler.sendMessage(message);
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS:
                    String data = (String) msg.obj;
                    httplistenner.success(data);
                    break;
                case ERROR:
                    String error = (String) msg.obj;
                    httplistenner.error(error);
                    break;
            }
        }
    };
    //传递接口
    private Httplistenner httplistenner;
    public void result(Httplistenner httplistenner){
        this.httplistenner=httplistenner;
    }

    public interface Httplistenner {

        void success(String data);
        void error(String error);
    }

    public void post(String url,RequestBody body){
        OkHttpClient client = new OkHttpClient();
        Request request=new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            doHttp(client,request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
