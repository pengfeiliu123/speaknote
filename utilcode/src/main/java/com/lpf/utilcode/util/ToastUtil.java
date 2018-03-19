package com.lpf.utilcode.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liupengfei on 2018/2/24 11:28.
 */

public  class ToastUtil {

    public static void showShort(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
