package com.jcd.sideslipback.widgets;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by jcd on 2018/4/9.
 * <p>
 * 侧滑退出自定义ViewGroup
 */

public class SideslipBackLayout extends FrameLayout {

    /**
     * 打开时的透明度，取值范围 1.0 - 0.0，1 就是完全不透明
     */
    private float startAlpha = 0.8F;

    // 屏幕宽度像素值
    private int mScreenWidth = getResources().getDisplayMetrics().widthPixels;
    // 手势拖拽帮助类
    private ViewDragHelper mViewDragHelper;
    // 背景，从不透明到透明过度背景
    private View mBackView;
    // 用户的 ContentView
    private View mContentView;

    public SideslipBackLayout(@NonNull Context context, View contentView, float startAlpha) {
        this(context, contentView);
        this.startAlpha = startAlpha;
    }

    public SideslipBackLayout(@NonNull Context context, View contentView) {
        super(context);
        this.mContentView = contentView;
        // 创建 ViewDragHelper 并绑定到当前 ViewGroup
        mViewDragHelper = ViewDragHelper.create(this, mDragCallback);
        // 设置左边边缘手势跟踪
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);

        // 创建过度背景
        mBackView = new View(context);
        mBackView.setBackgroundColor(Color.BLACK);
        mBackView.setAlpha(startAlpha);
        addView(mBackView);

        // 设置默认背景色，把用户 contentView 叠加到过度背景上
        mContentView.setBackgroundColor(0xFFF2F2F2);
        addView(mContentView);
    }

    private ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            // 返回left的值，取 0 - 屏幕宽度之间
            return Math.max(0, Math.min(mScreenWidth, left));
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            // 拖拽的时候，捕获用户 ContentView ，让它跟随父 ViewGroup 拖动
            mViewDragHelper.captureChildView(mContentView, pointerId);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            // 拖拽的时候，计算滑动比例，并相应的改变过度背景的透明度
            float percent = 1.0F - (float) left / mScreenWidth;
            mBackView.setAlpha(percent * startAlpha);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            // 推拽停止的时候，计算拖动的距离，和设置的距离比较
            int left = releasedChild.getLeft();
            int finalLeft = 0;

            // 屏幕一半的宽度
            int threshold = (int) (mScreenWidth * 0.5F);

            // 如果大于屏幕一半，就把 finalLeft 设置为屏幕的宽度，否则还是为0
            if (left > threshold) {
                finalLeft = mScreenWidth;
            }

            // 要么关闭，要么全部打开
            mViewDragHelper.settleCapturedViewAt(finalLeft, 0);
            invalidate();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            // 当拖拽动作完成（闲置）、完全打开、监听不为空
            if (state == ViewDragHelper.STATE_IDLE && 0 != mContentView.getLeft() && null != mSlipListener) {
                // 退出当前 Activity
                mSlipListener.onFinish();
            }
        }
    };

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    private OnSlipListener mSlipListener;

    public void setOnSlipListener(OnSlipListener listener) {
        mSlipListener = listener;
    }

    public interface OnSlipListener {
        void onFinish();
    }
}
