package com.xtp.mobilesafe.utli;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    /**
     * 打印方法
     * @param context
     * @param msg
     */
    public static void show(Context context,String msg ){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
