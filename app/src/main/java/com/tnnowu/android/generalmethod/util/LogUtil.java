package com.tnnowu.android.generalmethod.util;

import com.orhanobut.logger.Logger;
import com.tnnowu.android.generalmethod.BuildConfig;

/**
 * Created by Tnno Wu on 2017/04/27.
 */

public class LogUtil {

    private static final String TAG = "LogUtil";

    public static boolean isDebug = BuildConfig.DEBUG;

    public static void e(String tag, Object o) {
        if (isDebug) {
            Logger.e(tag, o);
        }
    }

    public static void e(Object o) {
        LogUtil.e(TAG, o);
    }

    public static void w(String tag, Object o) {
        if (isDebug) {
            Logger.w(tag, o);
        }
    }

    public static void w(Object o) {
        LogUtil.w(TAG, o);
    }

    public static void d(String msg) {
        if (isDebug) {
            Logger.d(msg);
        }
    }

    public static void i(String msg) {
        if (isDebug) {
            Logger.i(msg);
        }
    }

}
