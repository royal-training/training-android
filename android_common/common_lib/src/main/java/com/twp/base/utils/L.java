package com.twp.base.utils;

/*
 * 版       权:  Royal.k.peng@gmail.com, All rights reserved
 * 作       者:  Royal
 * 座 右  铭:  Never give up, adhere to in the end.
 */

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by [pengqinping] on [2016/11/17].<br>
 * Email: pengqinping@gmail.com <br>
 * Motto: A man can be destroyed but not defeated <br>
 * Blog:  https://github.com/pengqinping <br>
 * Keys: Android Java IOS <br>
 * 类描述：日志管理类 主要功能，  <br> 1.直接log工具查看，不用打印详细的时间，第几行，写入到sdcard的日志才加这些东西。<br>
 * 2.写入日志文件的只有警告和error,才会写入到日志，注意异常信息的log需要写入到文件，
 * <br>
 */

public class L {

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss ms");

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat sdfLogName = new SimpleDateFormat(
            "yyyy-MM-dd");

    private static ILogConfig iLogger = new SimpleLogConfig();

    public static void initWithCustomerConfig(ILogConfig config) {
        if (null == config) {
            throw new NullPointerException("this config not be null");
        }
        iLogger = config;
    }


    /**
     * 只有在写入文件的时候才去调用这个方法来构建消息
     */
    private static String buildMsg(String msg) {
        StringBuilder buffer = new StringBuilder();
        Date date = new Date();
        String time = sdf.format(date);

        buffer.append(time);
        final StackTraceElement stackTraceElement = Thread.currentThread()
                .getStackTrace()[4];
        buffer.append(" [");
        buffer.append(Thread.currentThread().getName());
        buffer.append(":");
        buffer.append(stackTraceElement.getLineNumber());
        buffer.append(":");
        buffer.append(stackTraceElement.getMethodName());
        buffer.append("()] ");
        buffer.append(msg);
        buffer.append("\n");
        return buffer.toString();
    }

    private static boolean checkLevelIsEnable(int level) {
        return iLogger.isEnableLog() && iLogger.defaultLogLevel() >= level;
    }

    private static boolean checkEnableSave2Sdcard(){
        return iLogger.isEnableSdcardSave();
    }

    public static void v(String msg) {
        v(iLogger.defaultLogTag(), msg);
    }

    public static void v(String tag, String msg) {
        if (checkLevelIsEnable(Log.VERBOSE)) {
            Log.v(tag, msg);
        }
    }

    public static void d(String msg) {
        d(iLogger.defaultLogTag(), msg);
    }

    public static void d(String tag, String msg) {
        if (checkLevelIsEnable(Log.DEBUG)) {
            Log.d(tag, msg);
        }
    }

    public static void i(String msg) {
        i(iLogger.defaultLogTag(), msg);
    }

    public static void i(String tag, String msg) {
        if (checkLevelIsEnable(Log.INFO)) {
            Log.i(tag, msg);
        }
    }

    public static void w(String msg) {
        w(iLogger.defaultLogTag(), msg);
    }

    public static void w(String tag, String msg) {
        if (checkLevelIsEnable(Log.WARN)) {
            Log.w(tag, msg);
            if (checkEnableSave2Sdcard()) {
                String info = buildMsg(msg);
                writeFileToSD(info);
            }
        }
    }

    public static void w(String msg, Exception e) {
        w(iLogger.defaultLogTag(), msg, e);
    }

    public static void w(String tag, String msg, Exception e) {
        if (checkLevelIsEnable(Log.WARN)) {
            Log.w(tag, msg, e);
            if (checkEnableSave2Sdcard()) {
                String info = buildMsg(msg);
                writeFileToSD(info + getStackTrace(e));
            }
        }
    }

    public static void e(String msg) {
        e(iLogger.defaultLogTag(), msg);
    }

    public static void e(String tag, String msg) {
        if (checkLevelIsEnable(Log.ERROR)) {
            Log.e(tag, msg);
            if (checkEnableSave2Sdcard()) {
                String info = buildMsg(msg);
                writeFileToSD(info);
            }
        }
    }

    public static void e(String msg, Exception e) {
        e(iLogger.defaultLogTag(), msg, e);
    }

    public static void e(String tag, String msg, Exception e) {
        if (checkLevelIsEnable(Log.ERROR)) {
            Log.e(tag, msg, e);
            if (checkEnableSave2Sdcard()) {
                String info = buildMsg(msg);
                writeFileToSD(info + getStackTrace(e));
            }
        }
    }

    @SuppressLint("SdCardPath")
    private static void writeFileToSD(String context) {
        RandomAccessFile raf = null;
        if (iLogger.defaultLogSavePath().startsWith("/sdcard") || iLogger.defaultLogSavePath().startsWith("/mnt")) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                Log.d(iLogger.defaultLogTag(), "SD card is not avaiable right now.");
                return;
            }
        }
        try {
            String pathName = iLogger.defaultLogSavePath();
            String fileName = iLogger.defaultLogFileName();
            File path = new File(pathName);
            File file = new File(pathName + fileName);
            if (!path.exists()) {
                Log.d(iLogger.defaultLogTag(), "Create the path:" + pathName);
                path.mkdirs();
            }
            if (!file.exists()) {
                Log.d(iLogger.defaultLogTag(), "Create the file:" + fileName);
                file.createNewFile();
            }

            raf = new RandomAccessFile(file, "rw");
            if (file.length() > iLogger.maxLogFileSize()) {
                raf.seek(0);
            } else {
                raf.seek(file.length());
            }

            raf.write(context.getBytes());
        } catch (Exception e) {
            Log.e(iLogger.defaultLogTag(), "Error to write SD card.");
        } finally {
            if (null != raf) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取堆栈信息
     *
     * @param t
     * @return
     */
    private static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }

    public static class SimpleLogConfig implements ILogConfig {
        @Override
        public String defaultLogTag() {
            return FILE_TAG;
        }

        @Override
        public String defaultLogSavePath() {
            return FILE_PATH;
        }

        @Override
        public String defaultLogFileName() {
            return FILE_NAME;
        }

        @Override
        public int defaultLogLevel() {
            return LOG_LEVEL;
        }

        @Override
        public boolean isEnableSdcardSave() {
            return LOG_ENABLE_SAVE_SDCARD;
        }

        @Override
        public boolean isEnableLog() {
            return LOG_ENABLE_PRINT;
        }

        @Override
        public long maxLogFileSize() {
            return FILE_MAX_SIZE;
        }
    }

}
