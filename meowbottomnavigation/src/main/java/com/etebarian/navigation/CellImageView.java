package com.etebarian.navigation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


public class CellImageView extends AppCompatImageView {

    private boolean isBitmap = false;

    public void setIsBitmap(boolean value) {
        isBitmap = value;
        draw();
    }

    private boolean useColor = true;

    public void setUseColor(boolean value) {
        useColor = value;
        draw();
    }

    private int resource = 0;

    public void setResource(int value) {
        resource = value;
        draw();
    }

    private int color = 0;

    public void setColor(int value) {
        color = value;
        draw();
    }

    private int size = Utils.dip(getContext(), 24);

    public void setSize(int value) {
        size = value;
        requestLayout();
    }

    private boolean actionBackgroundAlpha = false;
    private boolean changeSize = true;
    private boolean fitImage = false;
    private ValueAnimator colorAnimator;
    private boolean allowDraw = false;


    public CellImageView(Context context) {
        super(context);
        initializeView();
    }

    public CellImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributeFromXml(context, attrs);
        initializeView();
    }

    public CellImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributeFromXml(context, attrs);
        initializeView();
    }

    private void setAttributeFromXml(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CellImageView, 0, 0);
        try {
            isBitmap = a.getBoolean(R.styleable.CellImageView_meow_imageview_isBitmap, isBitmap);
            useColor = a.getBoolean(R.styleable.CellImageView_meow_imageview_useColor, useColor);
            resource = a.getResourceId(R.styleable.CellImageView_meow_imageview_resource, resource);
            color = a.getColor(R.styleable.CellImageView_meow_imageview_color, color);
            size = a.getDimensionPixelSize(R.styleable.CellImageView_meow_imageview_size, size);
            actionBackgroundAlpha = a.getBoolean(R.styleable.CellImageView_meow_imageview_actionBackgroundAlpha,
                    actionBackgroundAlpha);
            changeSize = a.getBoolean(R.styleable.CellImageView_meow_imageview_changeSize, changeSize);
            fitImage = a.getBoolean(R.styleable.CellImageView_meow_imageview_fitImage, fitImage);
        } finally {
            a.recycle();
        }
    }

    private void initializeView() {
        allowDraw = true;
        draw();
    }

    private void draw() {
        if (!allowDraw) {
            return;
        }

        if (resource == 0) {
            return;
        }

        if (isBitmap) {
            try {
                Drawable drawable;
                if (color == 0) {
                    drawable = ContextCompat.getDrawable(getContext(), resource);
                } else {
                    drawable = DrawableHelper.changeColorDrawableRes(getContext(), resource, color);
                }
                setImageDrawable(drawable);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        if (useColor && color == 0) {
            return;
        }

        int c;
        if (useColor) {
            c = color;
        } else {
            c = -2;
        }
        try {
            setImageDrawable(DrawableHelper.changeColorDrawableVector(getContext(), resource, c));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeColorByAnim(final int newColor, long d) {
        if (color == 0) {
            color = newColor;
            return;
        }
        final int lastColor = color;
        if (colorAnimator == null) {
            colorAnimator = ValueAnimator.ofFloat(0f, 1f);
        }
        colorAnimator.cancel();

        colorAnimator.setDuration(d);
        colorAnimator.setInterpolator(new FastOutSlowInInterpolator());
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = animation.getAnimatedFraction();
                color = ColorHelper.mixTwoColors(newColor, lastColor, f);
                animation.start();
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (fitImage) {
            Drawable d = getDrawable();
            if (d != null) {
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = (int) Math.ceil((width * d.getIntrinsicHeight() / d.getIntrinsicWidth()));
                setMeasuredDimension(width, height);
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
            return;
        }

        if (isBitmap || !changeSize) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int newSize = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(newSize, newSize);
    }

}