package com.yxkj.basemoudle;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Button;

import com.yxkj.basemoudle.base.BaseActivity;
import com.yxkj.basemoudle.permission.Permission;
import com.yxkj.basemoudle.permission.RxPermissions;
import com.yxkj.basemoudle.ui.NormalActivity;
import com.yxkj.basemoudle.ui.SwipActivityActivity;
import com.yxkj.basemoudle.util.ToastUtil;
import com.yxkj.basemoudle.util.UploadImageUtil;

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

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        openActivity(NormalActivity.class);
    }


    @OnClick(R.id.swipActivityActivity)
    public void onClicked() {
        openActivity(SwipActivityActivity.class);
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
}

