package com.yxkj.basemoudle.base;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.yxkj.basemoudle.R;
import com.yxkj.basemoudle.util.LogUtil;
import com.yxkj.basemoudle.util.ToastUtil;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

import static com.yxkj.basemoudle.base.DefaultObserver.ExceptionReason.CONNECT_ERROR;
import static com.yxkj.basemoudle.base.DefaultObserver.ExceptionReason.CONNECT_TIMEOUT;
import static com.yxkj.basemoudle.base.DefaultObserver.ExceptionReason.PARSE_ERROR;
import static com.yxkj.basemoudle.base.DefaultObserver.ExceptionReason.UNKNOWN_ERROR;

/**
 * Created by 曾强 on 2018/3/6.
 */

public abstract class DefaultObserver<T extends BasicResponse> implements Observer<T> {
    private Activity activity;
    //  Activity 是否在执行onStop()时取消订阅
    private boolean isAddInStop = false;
    private CommonDialogUtils dialogUtils;

    public DefaultObserver(Activity activity) {
        this.activity = activity;
        dialogUtils = new CommonDialogUtils();
        dialogUtils.showProgress(activity);
    }

    public DefaultObserver(Activity activity, boolean isShowLoading) {
        this.activity = activity;
        dialogUtils = new CommonDialogUtils();
        if (isShowLoading) {
            dialogUtils.showProgress(activity, "Loading...");
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        dismissProgress();
        if (response.code != 200) {
            onSuccess(response);
        } else {
            onFail(response);
        }
        /*if (response.getCode() == 200) {
            onSuccess(response);
        } else {
            onFail(response);
        }*/
    }

    private void dismissProgress() {
        if (dialogUtils != null) {
            dialogUtils.dismissProgress();
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e("Retrofit", e.getMessage());
        dismissProgress();
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(PARSE_ERROR);
        } else {
            onException(UNKNOWN_ERROR);
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    /**
     * 服务器返回数据，但响应码不为200
     *
     * @param response 服务器返回的数据
     */
    public void onFail(T response) {
        String message = response.message;
        if (TextUtils.isEmpty(message)) {
            ToastUtil.showShortText("服务器连接错误");
        } else {
            ToastUtil.showShortText(message);
        }
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtil.showShortText("连接错误");
                break;

            case CONNECT_TIMEOUT:
                ToastUtil.showShortText("连接超时");
                break;

            case BAD_NETWORK:
                ToastUtil.showShortText("网络问题");
                break;

            case PARSE_ERROR:
                ToastUtil.showShortText("解析数据失败");
                break;

            case UNKNOWN_ERROR:
            default:
                ToastUtil.showShortText("未知错误");
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
