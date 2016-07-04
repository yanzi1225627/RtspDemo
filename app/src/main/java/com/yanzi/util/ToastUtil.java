package com.yanzi.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by yanzi on 16/7/2.
 */
public class ToastUtil {
    private static volatile Toast mToast;

    private static Toast getInstance(Context context) {
        if (mToast == null) {
            synchronized (ToastUtil.class) {
                if (mToast == null) {
                    mToast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
                }
            }
        }
        return mToast;
    }

    public static void show(Context context, CharSequence text) {
        Toast toast = getInstance(context);
        toast.setText(text);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }


    public static void cancel() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
