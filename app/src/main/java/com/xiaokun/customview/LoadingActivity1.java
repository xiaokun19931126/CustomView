package com.xiaokun.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by xiaokun on 2019/3/3.
 *
 * @author xiaokun
 * @date 2019/3/3
 */
public class LoadingActivity1 extends AppCompatActivity {


    private LoadingView loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading1);


        initView();
    }


    private void initView() {
        loading = findViewById(R.id.loading);

//        loading.start();

    }

    public void start(View view) {
        loading.start();
    }

    public void stop(View view) {
        loading.release();
    }
}
