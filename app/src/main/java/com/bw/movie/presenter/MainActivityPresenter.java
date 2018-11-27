package com.bw.movie.presenter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.mvp.view.AppDelegate;

/*
 *作者：刘进
 *日期：2018/11/27
 **/
public class MainActivityPresenter extends AppDelegate{

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
    private Context context;
    @Override
    public void getContext(Context context) {
        this.context=context;
    }

    @Override
    public void initData() {

    }
}
