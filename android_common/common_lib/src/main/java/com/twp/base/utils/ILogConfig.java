package com.twp.base.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by [pengqinping] on [2016/11/17].<br>
 * Email: pengqinping@gmail.com <br>
 * Motto: A man can be destroyed but not defeated <br>
 * Blog:  https://github.com/pengqinping <br>
 * Keys: Android Java IOS <br>
 * 类描述：<br>
 */

public interface ILogConfig {

    //10M
    long FILE_MAX_SIZE = 10*1024*1024;
    //default tag
    String FILE_TAG = "Logger";
    //default path
    String FILE_PATH = Environment.getExternalStorageDirectory().getPath().concat(File.separator).concat("log");
    //default name
    String FILE_NAME = "log.txt";
    //default level
    int LOG_LEVEL = Log.INFO;
    boolean LOG_ENABLE_SAVE_SDCARD = true;
    boolean LOG_ENABLE_PRINT = true;

    String defaultLogTag();

    String defaultLogSavePath();

    String defaultLogFileName();

    /**
     * print log less level;
     * @return
     */
    int defaultLogLevel();

    /**
     * <code>true</code> is save error level log to log file, <code>false</code>  is not
     * @return
     */
    boolean isEnableSdcardSave();

    /**
     * <code>true</code> is open log print out, <code>false</code>  is not
     * @return
     */
    boolean isEnableLog();

    /**
     * max log file size
     * @return
     */
    long maxLogFileSize();

}
