package com.yxkj.basemoudle.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.yxkj.basemoudle.util.NetworkUtils;
import com.yxkj.basemoudle.util.ToastUtil;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Fragment基类
 */
public abstract class BaseFragment extends RxFragment implements View.OnClickListener {
    /**
     * 贴附的activity
     */
    protected FragmentActivity mActivity;
    /**
     * 是否对用户可见
     */
    protected boolean mIsVisible;
    /**
     * 是否加载完成
     * 当执行完oncreatview,View的初始化方法后方法后即为true
     */
    protected boolean mIsPrepare;
    /**
     * 根view
     */
    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), null);
            beforeInitView();
            mIsPrepare = true;
            initData();
            setListener();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.mIsVisible = isVisibleToUser;

        if (isVisibleToUser) {
            onVisibleToUser();
        }
    }

    /**
     * 用户可见时执行的操作
     *
     * @author 漆可
     * @date 2016-5-26 下午4:09:39
     */
    protected void onVisibleToUser() {
        if (mIsPrepare && mIsVisible) {
            initData();
        }
    }

    /**
     * [防止快速点击]
     *
     * @return
     */
    protected boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    protected abstract int getLayoutId();

    protected void beforeInitView() {
    }

    protected abstract void initData();

    protected abstract void setListener();//设置事件

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    /**
     * 线程调度
     */
    public <T> ObservableTransformer<T, T> compose(final LifecycleTransformer<T> lifecycle) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        // 可添加网络连接判断等
                        if (!NetworkUtils.isNetAvailable(getActivity())) {
                            ToastUtil.showShortText("网络已断开");
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread()).compose(lifecycle);
            }
        };
    }
}
