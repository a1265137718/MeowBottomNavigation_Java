package com.etebarian.navigation;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;


public class DrawableHelper {

    public static Drawable changeColorDrawableVector(Context c, int resDrawable, int color) {
        if (c == null) {
            return null;
        }

        VectorDrawableCompat d = VectorDrawableCompat.create(c.getResources(), resDrawable, null);
        d.mutate();
        if (color != -2) {
            d.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        return d;
    }

    public static Drawable changeColorDrawableRes(Context c, int resDrawable, int color) {
        if (c == null) {
            return null;
        }

        Drawable d = ContextCompat.getDrawable(c, resDrawable);
        d.mutate();
        if (color != -2) {
            d.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        return d;
    }
}
