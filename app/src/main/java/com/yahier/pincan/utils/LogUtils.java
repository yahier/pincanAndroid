package com.yahier.pincan.utils;

import android.util.Log;

/**
 * Created by yahier on 2018/2/5.
 */

public class LogUtils {

    public static void logE(String tag, String msg) {
        if (msg == null) msg = "msg is null";
        Log.e(tag, msg);
    }
}
