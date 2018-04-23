package com.yxkj.basemoudle.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.Thread.State;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 带日志文件输入的，又可控开关的日志调试
 *
 * @author BaoHang
 * @version 1.0
 * @data 2012-2-20
 */
public class MyLog {

    private final static String tag = MyLog.class.getCanonicalName();

    /**
     * 日志模式 1-disable 2-normal 3-system
     */
    private static int LOG_MODE = 1;
    private static int LOG_LEVEL = 1;// 输入日志类型，w代表只输出告警信息等，v代表输出所有信息
    private static boolean NATIVE_LOG_SAVE = true;// 协议栈日志是否写入文件
    private static int NATIVE_LOG_LEVEL = 5;// 协议栈日志输出级别，默认级别最高，输出所有日志
    public static String MYLOG_PATH_SDCARD_DIR = "/sdcard/SmarTalk/"; // 日志文件在sdcard中的路径
    // /data/data/skdroid/log
    // 大终端环境
    private static Date APP_START_TIME = null;
    private static int SDCARD_LOG_FILE_SAVE_DAYS = 0;// sd卡中日志文件的最多保存天数
    private static String MYLOG_FILE_NAME = "SKSLog.txt";// 本类输出的日志文件名称
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");// 日志的输出格式
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式

    private static MySyslogThread mSyslogThread = null;

    /**
     * 初始化日志存储路径
     *
     * @param dir
     */
    public static void init(String dir, Date appTime) {
        MYLOG_PATH_SDCARD_DIR += dir + "/syslogs/" + logfile.format(new Date()) + "/";
        APP_START_TIME = appTime;
        Log.d(tag, "日志文件路径：" + MYLOG_PATH_SDCARD_DIR);
    }

    /**
     * 日志打印总开关
     */
    public static void setLogMode(int logMode) {
        LOG_MODE = logMode;
        if (LOG_MODE == 3) {
            writeSystemLogToFile();
        }
    }

    public static boolean isSaveNativeLog() {
        return NATIVE_LOG_SAVE;
    }

    public static void saveNativeLogs(boolean save) {
        NATIVE_LOG_SAVE = save;
    }

    public static int getNativeLogLevel() {
        return NATIVE_LOG_LEVEL;
    }

    /**
     * 设置日志级别
     *
     * @param logLevel
     */
    public static void setLogLevel(int logLevel) {
        LOG_LEVEL = logLevel;
    }

    public static int getLogLevel() {
        return LOG_LEVEL;
    }

    public static void e(String tag, String text) {
        e(tag, text, null);
    }

    public static void e(String tag, String text, Throwable e) {
        Log.e(tag, text);
        if (LOG_MODE == 2 && LOG_LEVEL >= 1) {
            writeLogtoFile("E", tag, text);
        }
    }

    public static void w(String tag, String text) {
        w(tag, text, null);
    }

    public static void w(String tag, String text, Throwable e) {
        Log.w(tag, text);
        if (LOG_MODE == 2 && LOG_LEVEL >= 2) {
            writeLogtoFile("W", tag, text);
        }
    }

    public static void i(String tag, String text) {
        i(tag, text, null);
    }

    public static void i(String tag, String text, Throwable e) {
        Log.i(tag, text);
        if (LOG_MODE == 2 && LOG_LEVEL >= 3) {
            writeLogtoFile("I", tag, text);
        }
    }

    public static void d(String tag, String text) {
        d(tag, text, null);
    }

    public static void d(String tag, String text, Throwable e) {
        Log.d(tag, text);
        if (LOG_MODE == 2 && LOG_LEVEL >= 4) {
            writeLogtoFile("D", tag, text);
        }
    }

    public static void v(String tag, String text) {
        v(tag, text, null);
    }

    public static void v(String tag, String text, Throwable e) {
        Log.v(tag, text);
        if (LOG_MODE == 2 && LOG_LEVEL >= 5) {
            writeLogtoFile("V", tag, text);
        }
    }

    private static long LOG_LINES = 0;
    private static int fileNum = 0;

    /**
     * 打开日志文件并写入日志
     *
     * @return
     */
    private static void writeLogtoFile(String mylogtype, String tag, String text) { // 新建或打开日志文件

        if (MYLOG_PATH_SDCARD_DIR == null || MYLOG_PATH_SDCARD_DIR.startsWith("null")) {
            Log.e("MyLog", "Log save path is null.");
            return;
        }
        FileWriter filerWriter = null;
        try {
            File dir = new File(MYLOG_PATH_SDCARD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            SimpleDateFormat appStartTime = new SimpleDateFormat("HH-mm-ss");
            SimpleDateFormat msgTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            if (APP_START_TIME == null) {
                APP_START_TIME = new Date();
            }
            String needWriteMessage = msgTime.format(System.currentTimeMillis()) + "  " + mylogtype + " " + tag + "   "
                    + text;
            LOG_LINES++;
            BufferedWriter bufWriter = null;
            File file = null;
            // MyLog.d("", "系统日志路径 : "+file.getAbsolutePath());
            if (LOG_LINES > 30000) {
                fileNum++;
                LOG_LINES = 0;
                file = new File(MYLOG_PATH_SDCARD_DIR,
                        appStartTime.format(APP_START_TIME) + "_Logs_" + fileNum + ".log");
            } else {
                file = new File(MYLOG_PATH_SDCARD_DIR, appStartTime.format(APP_START_TIME) + "_Logs_0.log");
            }
            filerWriter = new FileWriter(file, true);
            bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
        } catch (FileNotFoundException f) {
            Log.d(tag, "系统日志文件路径不存在，停止写入系统日志");
            f.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (filerWriter != null) {
                    filerWriter.close();
                }
            } catch (Exception e2) {
            }
        }
    }

    public static void writeSystemLogToFile() {
        Log.d(tag, "保存系统日志");
        if (mSyslogThread == null) {
            mSyslogThread = new MySyslogThread();
            mSyslogThread.start();
        } else if (mSyslogThread.getState() == State.TERMINATED || mSyslogThread.getState() == State.TIMED_WAITING) {
            mSyslogThread = new MySyslogThread();
            mSyslogThread.start();
        }
    }

    /**
     * 删除制定的日志文件
     */
    public static void delFile() { // 删除日志文件
        String needDelFile = logfile.format(getDateBefore());
        File file = new File(MYLOG_PATH_SDCARD_DIR, needDelFile + MYLOG_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 得到现在时间前的几天日期，用来得到需要删除的日志文件名
     */
    private static Date getDateBefore() {
        Date nowtime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowtime);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - SDCARD_LOG_FILE_SAVE_DAYS);
        return now.getTime();
    }

    static class MySyslogThread extends Thread {

        public MySyslogThread() {
            super("Syslog");
        }

        @Override
        public void run() {
            super.run();
            String comm = "logcat";

            Process process;
            InputStreamReader isr = null;
            FileWriter filerWriter = null;
            try {
                process = Runtime.getRuntime().exec(comm);
                isr = new InputStreamReader(process.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String res = "";
                File dir = new File(MYLOG_PATH_SDCARD_DIR);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                int fileNum = 0;
                int loglines = 0;
                SimpleDateFormat appStartTime = new SimpleDateFormat("HHmmss");
                if (APP_START_TIME == null) {
                    APP_START_TIME = new Date();
                }
                File file = new File(MYLOG_PATH_SDCARD_DIR, appStartTime.format(APP_START_TIME) + "_systemLogs_0.log");
                // MyLog.d("", "系统日志路径 : "+file.getAbsolutePath());
                filerWriter = new FileWriter(file, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
                BufferedWriter bufWriter = new BufferedWriter(filerWriter);
                while ((res = br.readLine()) != null) {
                    if (LOG_MODE != 3) {
                        return;
                    }

                    if (loglines > 15000) {
                        fileNum++;
                        loglines = 0;
                        file = new File(MYLOG_PATH_SDCARD_DIR,
                                appStartTime.format(APP_START_TIME) + "_sysLog_" + fileNum + ".log");
                        filerWriter = new FileWriter(file, true);
                        bufWriter = new BufferedWriter(filerWriter);
                    }

                    Date nowtime = new Date();
                    String needWriteMessage = myLogSdf.format(nowtime) + "-" + res;
                    bufWriter.write(needWriteMessage);
                    bufWriter.newLine();
                    loglines++;
                }

                bufWriter.close();
            } catch (FileNotFoundException f) {
                Log.d(tag, "系统日志文件路径不存在，停止写入系统日志");
                f.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (isr != null) {
                        isr.close();
                    }
                    if (filerWriter != null) {
                        filerWriter.close();
                    }
                } catch (Exception e2) {
                }
            }
        }
    }

}
