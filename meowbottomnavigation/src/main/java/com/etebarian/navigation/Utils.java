package com.etebarian.navigation;

import android.content.Context;

public class Utils {

    public static int getDP(Context context) {
        return (int) context.getResources().getDisplayMetrics().density;
    }

    public static float dipf(Context context, Float f) {
        return f * getDP(context);
    }

    public static float dipf(Context context, int i) {
        return i * getDP(context);
    }

    public static int dip(Context context, int i) {
        return i * getDP(context);
    }

}
