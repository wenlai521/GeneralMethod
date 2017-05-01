package com.tnnowu.android.generalmethod.util;

/**
 * Created by Tnno Wu on 2017/04/28.
 */

public class DoubleClickExitUtil {

    public static long mLastClick = 0L;

    private static final int THRESHOLD = 2000;

    public static boolean check() {
        long now = System.currentTimeMillis();
        boolean b = now - mLastClick < THRESHOLD;
        mLastClick = now;
        return b;
    }

}
