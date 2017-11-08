package com.yxkj.basemoudle.util;

import android.widget.Toast;

import com.yxkj.basemoudle.MyApplication;

public class ToastUtil {

    protected static final String TAG = "ToastUtil";
    public static Toast toast;

    /**
     * 信息提示
     *
     * @param content
     */
    public static void makeToast(String content) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(MyApplication.getMyApplication(), content, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void makeShortToast(String content) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(MyApplication.getMyApplication(), content, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showShortText(int resId) {
        try {
            if (toast != null)
                toast.cancel();
            toast = Toast.makeText(MyApplication.getMyApplication(), resId, Toast.LENGTH_SHORT);
            toast.show();
        } catch (Exception e) {
        }
    }

    public static void showShortText(CharSequence text) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(MyApplication.getMyApplication(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showLongText(int resId) {
        try {
            if (toast != null)
                toast.cancel();
            toast = Toast.makeText(MyApplication.getMyApplication(), resId, Toast.LENGTH_LONG);
            toast.show();

        } catch (Exception e) {
        }
    }


}
