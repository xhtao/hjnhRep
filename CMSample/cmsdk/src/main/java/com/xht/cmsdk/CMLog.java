package com.xht.cmsdk;

import android.util.Log;

/**
 * Created by XIE on 2018/8/9.
 */

public class CMLog {

    public static void log(String... args){
        StringBuilder sb = new StringBuilder();
        for (String str : args) {
            sb.append(str).append(" ");
        }
        Log.i("CMLog：", sb.toString());
    }
}
