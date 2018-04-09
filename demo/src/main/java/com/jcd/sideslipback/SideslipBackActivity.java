package com.jcd.sideslipback;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by jcd on 2018/4/9.
 */

public class SideslipBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SideslipBackHelper.inject(this);
        setTitle(SideslipBackActivity.class.getSimpleName());
    }

    public void startActivity(View view) {
        startActivity(new Intent(this,SideslipBackActivity.class));
    }
}
