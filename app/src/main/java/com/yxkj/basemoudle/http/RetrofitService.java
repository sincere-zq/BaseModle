package com.yxkj.basemoudle.http;


import com.yxkj.basemoudle.base.BaseEntity;
import com.yxkj.basemoudle.bean.SgByChannel;
import com.yxkj.basemoudle.constant.Constant;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 接口API
 */
public interface RetrofitService {
    /*根据货道编号查询商品*/
    @POST(Constant.GETSGBYCHANNEL)
    Observable<BaseEntity<SgByChannel>> getSgByChannel(@Body Map<String, String> map);

    /*根据类别查询商品*/
    @POST(Constant.GETBYCATE)
    Observable<BaseEntity<List<SgByChannel>>> getByCate(@Body Map<String, String> map);

}
