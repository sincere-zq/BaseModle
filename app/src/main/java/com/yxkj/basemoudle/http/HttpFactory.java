package com.yxkj.basemoudle.http;

import com.yxkj.basemoudle.base.BaseObserver;
import com.yxkj.basemoudle.bean.SgByChannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 请求类
 */

public class HttpFactory {


    /**
     * 根据类别查询商品
     * <p>
     * cImei      中控唯一标识imei编号
     *
     * @param cateId     商品类别ID
     * @param pageSize
     * @param pageNumber
     */
    public static void getByCate(String cateId, String pageSize,
                                 String pageNumber, boolean isAll, BaseObserver<List<SgByChannel>> observer) {
        Map<String, String> map = new HashMap<>();
        if (!isAll)
            map.put("cateId", cateId);
        map.put("pageSize", pageSize);
        map.put("pageNumber", pageNumber);
        autoSubscribe(RetrofitFactory.getInstance().getByCate(map), observer);
    }

    /**
     * 订阅(带compse)
     */
    private static <T> void autoCompseSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribe(observer);
    }

    /**
     * 订阅(带compse)
     */
    private static <T> void autoSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }



}
