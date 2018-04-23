package com.yxkj.basemoudle;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.yxkj.basemoudle.adapter.SelectAdapter;
import com.yxkj.basemoudle.adapter.TypeAdapter;
import com.yxkj.basemoudle.base.BaseActivity;
import com.yxkj.basemoudle.base.BaseObserver;
import com.yxkj.basemoudle.bean.BackGround;
import com.yxkj.basemoudle.bean.SgByChannel;
import com.yxkj.basemoudle.http.HttpFactory;
import com.yxkj.basemoudle.permission.Permission;
import com.yxkj.basemoudle.permission.RxPermissions;
import com.yxkj.basemoudle.util.ToastUtil;
import com.yxkj.basemoudle.util.UploadImageUtil;
import com.yxkj.basemoudle.widget.TimeCount;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.swipActivityActivity)
    Button swipActivityActivity;
    @BindView(R.id.camera)
    Button camera;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.timeCount)
    Button timeCount;
    private TypeAdapter typeAdapter;
    private List<BackGround> integer1 = new ArrayList<>();
    private List<Integer> integer2 = new ArrayList<>();
    private List<Integer> integer3 = new ArrayList<>();
    private List<Integer> integer4 = new ArrayList<>();
    private SelectAdapter selectAdapter;
    private TimeCount time;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        time = new TimeCount(this, 60000, timeCount);
    }

    @Override
    protected void setListener() {
        integer1.add(new BackGround(1, false));
        integer1.add(new BackGround(2, false));
        integer1.add(new BackGround(3, false));
        integer1.add(new BackGround(4, false));
        typeAdapter = new TypeAdapter(this);
        selectAdapter = new SelectAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(selectAdapter);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
//        openActivity(NormalActivity.class);
//        selectAdapter.setBackGrounds(integer1);
        Intent intent = new Intent();
        intent.setAction("com.vogtech.ipa3.action");
        intent.putExtra("data", "侯尧");
        startActivity(intent);
    }


    @OnClick(R.id.swipActivityActivity)
    public void onClicked() {
//        openActivity(SwipActivityActivity.class);
//        typeAdapter.setStringList(integer3);
//        RetrofitFactory.getInstance().getByCate().compose(this.<BaseEntity<List<SgByChannel>>>bindToLifecycle()).subscribe(new BaseObserver<List<SgByChannel>>() {
//            @Override
//            protected void onHandleSuccess(List<SgByChannel> sgByChannels) {
//
//            }
//
//            @Override
//            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//            }
//        });
        HttpFactory.getByCate("", "", "", true, new BaseObserver<List<SgByChannel>>() {
            @Override
            protected void onHandleSuccess(List<SgByChannel> sgByChannels) {

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

            }
        });
    }


    @OnClick(R.id.camera)
    public void onCamera() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        Observable.just(rxPermissions)
                .compose(rxPermissions.ensureEach(Manifest.permission.CAMERA))
                .subscribe(new Observer<Permission>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Permission permission) {
                        if (permission.granted) {
                             /*跳转相机拍照*/
                            UploadImageUtil.doTakePhoto(MainActivity.this);
                        } else if (permission.shouldShowRequestPermissionRationale) {

                        } else {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setMessage("请允许系统使用您的相机")
                                    .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //引导用户至设置页手动授权
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                                            intent.setData(uri);
                                            startActivity(intent);
                                        }
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ToastUtil.showShortText("权限请求失败");
                                }
                            }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    ToastUtil.showShortText("权限请求失败");
                                }
                            }).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @OnClick(R.id.timeCount)
    public void onClick() {
        time.start();
    }
}

