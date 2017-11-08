package com.yxkj.basemoudle.constant;


import android.os.Environment;

import com.yxkj.basemoudle.MyApplication;

import java.io.File;

/**
 * 常量类
 */

public class Constant {
    /* 图片保存路径 */
    public static final String PHOTO_URI = "/Deliveryman/Image";
    /*拍照的照片存储位置*/
    public static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + Constant.PHOTO_URI);
    /* 用来标识请求照相功能的activity*/
    public static final int CAMERA_WITH_DATA = 3021;
    /*用来标识请求相册的activity*/
    public static final int PHOTO_PICKED_WITH_DATA = 3022;
    /* 用跳转剪切*/
    public static final int CROP_BIG_PICTURE = 3024;
    public static final String SQLITE_NAME = "_db";//数据库名称

    public static final int SQLITE_VERSION = 1;//数据库版本

    //数据库所有的表
    public static String[] TABLES;
//            = new String[]{UserModel.class.getName()};

//    public static final String BASE_URL = "http://machine.ybjcq.com/yxkj-machine/";
    public static final String BASE_URL = "http://10.1.0.143:8080/";
//    public static final String BASE_RECEIVER_URL = "101.132.144.100";
    public static final String BASE_RECEIVER_URL = "10.1.0.143";

    public static final long TIMEOUT = 30;

    public static String QRCODE = "http://test.ybjcq.com/h5/cntr/";

    /**
     * 顶部、底部默认视频地址
     */
    public static String VIDEO_TOP_DEFAULT_URL = "http://tb-video.bdstatic.com/tieba-smallvideo-spider/14960611_9fa6dd80cff56ea0e7ed0793d38e135a.mp4";
//    public static String VIDEO_BOTTOM_DEFAULT_URL = "http://192.167.1.242:8082/yxkj/advertisement/machine/dytt.mp4";
    public static String VIDEO_BOTTOM_DEFAULT_URL = "http://tb-video.bdstatic.com/tieba-smallvideo-spider/8091147_4234bf66fbb5c72de53be6ce74b87b42.mp4";
    /**
     * 视频文件名字
     */
    public static String VIDEO_TOP_NAME = "video_top.mp4";
    public static String VIDEO_BOTTOM_NAME = "video_bottom.mp4";

    /**
     * 视频状态
     */
    public static String VIDEO_TOP_STATE = "video_top_state";
    public static String VIDEO_BOTTOM_STATE = "video_bottom_state";

    public static final String CONFIGURE_FILE_PATH = "/controller";

    /**
     * 两个视频本地地址
     */
    public static String VIDEO_TOP_ADDRESS = MyApplication.getMyApplication().getExternalFilesDir(null) + File.separator + VIDEO_TOP_NAME;
    public static String VIDEO_BOTTOM_ADDRESS = MyApplication.getMyApplication().getExternalFilesDir(null) + File.separator + VIDEO_BOTTOM_NAME;
    /**
     * 上位机指令
     */
    public static final String VIDEO_TOP = "video_top";
    public static final String VIDEO_TOP_BOOLEAN = "video_top_boolean";
    public static final String VIDEO_TOP_CURRENTLENGTH = "video_top_currentlength";
    public static final String VIDEO_BOTTOM = "video_bottom";
    public static final String VIDEO_BOTTOM_BOOLEAN = "video_bottom_boolean";
    public static final String VIDEO_BOTTOM_CURRENTLENGTH = "video_bottom_currentlength";
    public static final String IMG_LEFT = "img_left";
    public static final String IMG_CENTER = "img_center";
    public static final String IMG_RIGHT = "img_right";
    public static final String PAYSUCCESS = "pay_success";
    public static final String RESTARTSYSTEM = "restart_system";
    public static final String APK_DOWNLOADED = "apk_downloaded";
    public static final String APK_DOWNLOAD_STATE = "apk_download_state";
    public static final String APK_DOWNLOAD_URL = "apk_download_url";
    /**
     * 中控接口
     */

    public static final String GETSGBYCHANNEL = "goods/getSgByChannel";//1.7.1	根据货道编号查询商品
    public static final String GETCATEGORY = "goods/getCategory";//1.7.2	查询商品类别
    public static final String GETBYCATE = "goods/getByCate";//1.7.3	根据类别查询商品
    public static final String VERIFYSTOCK = "goods/verifyStock";//1.7.4	验证商品库存数量
    public static final String UPATECMDSTATUS = "cmd/finishCmdStatus";//1.7.4	更新命令执行结果
    public static final String UPDATESHIPMENTSTATUS = "orderItem/updateOrderItemShipmentStatus";//1.7.4	更新订单项出货状态
    public static final String INITMACHINESTATUS = "cmd/initStatus";//1.7.4	更新订单项出货状态
}
