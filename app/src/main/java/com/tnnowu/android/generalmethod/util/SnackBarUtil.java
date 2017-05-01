package com.tnnowu.android.generalmethod.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Tnno Wu on 2017/04/27.
 */

public class SnackBarUtil {

    public static void show(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showShort(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

}
