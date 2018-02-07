package com.yahier.pincan.utils;

import android.app.Activity;
import android.app.AlertDialog;

import com.yahier.pincan.R;


/**
 * Created by yahier on 2018/2/6.
 */

public class DialogUtils {


    public static void showList(Activity mActivity, String[] data, OnListSelectedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, 3);
        builder.setTitle("列表");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(data, (dialog, which) -> {
            dialog.dismiss();
            listener.choosed(data[which]);
        });
        builder.create().show();
    }

    public static void showDialog(Activity mActivity, String msg, Runnable runnable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("确定", (dialog, which) -> runnable.run());
        builder.create().show();
    }


    public interface OnListSelectedListener {
        void choosed(String value);
    }

}
