package com.jcd.sideslipback;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.jcd.sideslipback.widgets.SideslipBackLayout;

/**
 * Created by jcd on 2018/4/9.
 * <p>
 * 帮助类，直接注入要侧滑退出功能的Activity
 */

public class SideslipBackHelper {

    private SideslipBackHelper() {

    }

    /**
     * 注入需要侧滑退出功能的 Activity
     *
     * @param activity   需要侧滑退出功能的 Activity
     * @param startAlpha 最开始的透明度
     */
    public static void inject(final Activity activity, float startAlpha) {
        // 获取当前 Activity 的 DecorView
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        // 取出 DecorView 中的第一个子 View(ContentView) 传递过去
        View contentView = decorView.getChildAt(0);
        // 移除原来的 ContentView
        decorView.removeViewAt(0);
        SideslipBackLayout sideslipBackLayout = new SideslipBackLayout(activity, contentView, startAlpha);
        // 添加自定义的 ContentView
        decorView.addView(sideslipBackLayout, 0);

        // 侧滑退出监听
        sideslipBackLayout.setOnSlipListener(new SideslipBackLayout.OnSlipListener() {
            @Override
            public void onFinish() {
                activity.finish();
            }
        });
    }

    /**
     * 默认 0.8 透明度
     *
     * @param activity
     */
    public static void inject(final Activity activity) {
        inject(activity, 0.8F);
    }
}
