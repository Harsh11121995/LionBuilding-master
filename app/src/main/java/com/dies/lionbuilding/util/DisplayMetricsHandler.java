package com.dies.lionbuilding.util;

import android.content.res.Resources;

public class DisplayMetricsHandler {

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
