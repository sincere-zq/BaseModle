package com.yxkj.basemoudle.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.ItemDecoration.LuDividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.yxkj.basemoudle.R;
import com.yxkj.basemoudle.adapter.NormalAdapter;
import com.yxkj.basemoudle.base.BaseActivity;
import com.yxkj.basemoudle.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SwipActivityActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.lurecyclerview)
    LuRecyclerView lurecyclerview;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private LuRecyclerViewAdapter lRecyclerViewAdapter = null;
    private NormalAdapter normalAdapter = null;
    private int page = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_swip_activity;
    }

    @Override
    protected void initData() {
        normalAdapter = new NormalAdapter(this);
        lRecyclerViewAdapter = new LuRecyclerViewAdapter(normalAdapter);
        lurecyclerview.addItemDecoration(new LuDividerDecoration.Builder(this, lRecyclerViewAdapter)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.colorAccent)
                .build());
        lurecyclerview.setLayoutManager(new LinearLayoutManager(this));
        lurecyclerview.setAdapter(lRecyclerViewAdapter);
        lurecyclerview.setFooterViewHint("正在加载...", "暂无更多", "点击重试");
    }

    @Override
    protected void setListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
        lurecyclerview.setOnLoadMoreListener(this);
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        page = 0;
        lurecyclerview.setNoMore(false);
        getData(page);
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMore() {
        page++;
        getData(page);
    }

    /**
     * 获取数据
     */
    private void getData(final int page) {
        final List<String> list = new ArrayList<>();
        Observable.range(page * 20, (page + 1) * 20)
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        list.add("item " + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (NetworkUtils.isNetAvailable(SwipActivityActivity.this)) {
                            if (page == 0) {
                                normalAdapter.setDataList(list);
                            } else {
                                normalAdapter.addAll(list);
                            }
                            if (page > 5) {
                                lurecyclerview.setNoMore(true);
                            }
                            lurecyclerview.refreshComplete(page);
                        } else {
                            lurecyclerview.refreshComplete(page);
                            lRecyclerViewAdapter.notifyDataSetChanged();
                            lurecyclerview.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                                @Override
                                public void reload() {
                                    getData(page);
                                }
                            });
                        }
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
    }
}
