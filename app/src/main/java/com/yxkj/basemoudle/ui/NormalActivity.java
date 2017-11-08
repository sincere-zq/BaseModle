package com.yxkj.basemoudle.ui;

import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.yxkj.basemoudle.R;
import com.yxkj.basemoudle.adapter.NormalAdapter;
import com.yxkj.basemoudle.base.BaseActivity;
import com.yxkj.basemoudle.util.DividerUtil;
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

public class NormalActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.lRecyclerView)
    LRecyclerView lRecyclerView;
    private LRecyclerViewAdapter lRecyclerViewAdapter = null;
    private NormalAdapter normalAdapter = null;
    private int page = 0;

    @Override
    public int getContentViewId() {
        return R.layout.activity_normal;
    }

    @Override
    public void initData() {
        normalAdapter = new NormalAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(normalAdapter);
        lRecyclerView.addItemDecoration(DividerUtil.getDividerDecoration(this));
        lRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerView.setAdapter(lRecyclerViewAdapter);
        lRecyclerView.setFooterViewHint("正在加载...", "暂无更多", "点击重试");
    }

    @Override
    protected void setListener() {
        lRecyclerView.setOnRefreshListener(this);
        lRecyclerView.setOnLoadMoreListener(this);
        onRefresh();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        page = 0;
        lRecyclerView.setNoMore(false);
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
                        if (NetworkUtils.isNetAvailable(NormalActivity.this)) {
                            if (page == 0) {
                                normalAdapter.setDataList(list);
                            } else {
                                normalAdapter.addAll(list);
                            }
                            if (page > 5) {
                                lRecyclerView.setNoMore(true);
                            }
                            lRecyclerView.refreshComplete(page);
                        } else {
                            lRecyclerView.refreshComplete(page);
                            lRecyclerViewAdapter.notifyDataSetChanged();
                            lRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                                @Override
                                public void reload() {
                                    getData(page);
                                }
                            });
                        }
                    }
                });
    }
}
