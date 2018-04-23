package com.yxkj.basemoudle.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by 曾强 on 2018/3/5.
 * 获取验证码倒计时控件
 */

public class TimeCount extends CountDownTimer {
    private static final int TIME_TASCK = 1000;
    private TextView button;
    private String desc = "%ss后重试";
    private OnTimeCountFinishListener onTimeCountFinishListener;

    public void setOnTimeCountFinishListener(OnTimeCountFinishListener onTimeCountFinishListener) {
        this.onTimeCountFinishListener = onTimeCountFinishListener;
    }

    public TimeCount(Context context, long millisInFuture, TextView view) {
        super(millisInFuture, TIME_TASCK);
        button = view;
        button.setText("获取验证码");
    }

    @Override
    public void onFinish() {// 计时完毕
        button.setTextColor(Color.BLACK);
        button.setBackgroundColor(Color.WHITE);
        button.setText("重新获取");
        button.setClickable(true);
        if (onTimeCountFinishListener != null) {
            onTimeCountFinishListener.onFinish();
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {// 计时过程
        button.setTextColor(Color.GRAY);
        button.setClickable(false);//防止重复点击
        button.setBackgroundColor(Color.DKGRAY);
        button.setText(String.format(desc, millisUntilFinished / TIME_TASCK));
    }

    /**
     * 倒计时完成监听
     */
    public interface OnTimeCountFinishListener {
        void onFinish();
    }
}
