package com.etebarian.navigation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MeowBottomNavigationCell extends RelativeLayout {
    public static final String EMPTY_VALUE = "empty";

    private int defaultIconColor = 0;
    private int selectedIconColor = 0;
    private int circleColor = 0;
    private int icon = 0;
    private TextView tv_count;
    private CellImageView iv;
    private FrameLayout fl;
    private View v_circle;

    public void setDefaultIconColor(int defaultIconColor) {
        this.defaultIconColor = defaultIconColor;
    }

    public void setSelectedIconColor(int selectedIconColor) {
        this.selectedIconColor = selectedIconColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    public MeowBottomNavigationCell(Context context) {
        super(context);
        initializeView();
    }

    public MeowBottomNavigationCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributeFromXml(context, attrs);
        initializeView();
    }

    public MeowBottomNavigationCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributeFromXml(context, attrs);
        initializeView();
    }

    public void setIcon(int value) {
        icon = value;
        if (allowDraw) {
            iv.setResource(value);
        }
    }

    public String count = EMPTY_VALUE;

    public void setCount(String value) {
        count = value;
        if (allowDraw) {
            if (count != null && count.equals(EMPTY_VALUE)) {
                tv_count.setText("");
                tv_count.setVisibility(View.INVISIBLE);
            } else {
                if (count != null && count.length() >= 3) {
                    count = count.substring(0, 1) + "..";
                }
                tv_count.setText(count);
                tv_count.setVisibility(View.VISIBLE);
                float scale = count.isEmpty() ? 0.5f : 1f;
                tv_count.setScaleX(scale);
                tv_count.setScaleY(scale);
            }
        }
    }

    private int iconSize = Utils.dip(getContext(), 24);

    private void setIconSize(int value) {
        iconSize = value;
        if (allowDraw) {
            iv.setSize(value);
            iv.setPivotX(iconSize / 2f);
            iv.setPivotY(iconSize / 2f);
        }
    }

    private int countTextColor = 0;

    public void setCountTextColor(int value) {
        countTextColor = value;
        if (allowDraw) {
            tv_count.setTextColor(countTextColor);
        }
    }

    private int countBackgroundColor = 0;

    public void setCountBackgroundColor(int value) {
        countBackgroundColor = value;
        if (allowDraw) {
            GradientDrawable d = new GradientDrawable();
            d.setColor(countBackgroundColor);
            d.setShape(GradientDrawable.OVAL);
            ViewCompat.setBackground(tv_count, d);
        }
    }

    private Typeface countTypeface;

    public void setCountTypeface(Typeface value) {
        countTypeface = value;
        if (allowDraw && countTypeface != null) {
            tv_count.setTypeface(countTypeface);
        }
    }

    private int rippleColor = 0;

    public void setRippleColor(int value) {
        rippleColor = value;
        if (allowDraw) {

        }
    }

    private boolean isFromLeft = false;

    public boolean isFromLeft() {
        return isFromLeft;
    }

    public void setFromLeft(boolean fromLeft) {
        isFromLeft = fromLeft;
    }

    private long duration = 0L;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    private float progress = 0f;

    public void setProgress(float value) {
        progress = value;
        fl.setY((1f - progress) * Utils.dip(getContext(), 18) + Utils.dip(getContext(), 10));

        iv.setColor((progress == 1f) ? selectedIconColor : defaultIconColor);
        float scale = (1f - progress) * (-0.2f) + 1f;
        iv.setScaleX(scale);
        iv.setScaleY(scale);

        GradientDrawable d = new GradientDrawable();
        d.setColor(circleColor);
        d.setShape(GradientDrawable.OVAL);

        ViewCompat.setBackground(v_circle, d);

        ViewCompat.setElevation(v_circle, (progress > 0.7f) ? Utils.dipf(getContext(), progress * 4f) : 0f);

        int m = Utils.dip(getContext(), 24);
        v_circle.setX((1f - progress) * ((isFromLeft) ? -m : m) + ((getMeasuredWidth() - Utils.dip(getContext(), 48)) / 2f));
        v_circle.setY((1f - progress) * getMeasuredHeight() + Utils.dip(getContext(), 6));


    }

    private boolean isEnabledCell = false;

    public boolean isEnabledCell() {
        return isEnabledCell;
    }

    public void setEnabledCell(boolean value) {
        isEnabledCell = value;
        if (Build.VERSION.SDK_INT >= 21 && !isEnabledCell) {
            GradientDrawable d = new GradientDrawable();
            d.setColor(circleColor);
            d.setShape(GradientDrawable.OVAL);
            fl.setBackground(new RippleDrawable(ColorStateList.valueOf(rippleColor), null, d));
        } else {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    fl.setBackgroundColor(Color.TRANSPARENT);
                }
            }, 200);
        }
    }

    private View containerView;
    private boolean allowDraw = false;

    private void setAttributeFromXml(Context context, AttributeSet attrs) {
    }

    private void initializeView() {
        allowDraw = true;
        containerView = LayoutInflater.from(getContext()).inflate(R.layout.meow_navigation_cell, this);
        tv_count = containerView.findViewById(R.id.tv_count);
        iv = containerView.findViewById(R.id.iv);
        fl = containerView.findViewById(R.id.fl);
        v_circle = containerView.findViewById(R.id.v_circle);
        draw();
    }

    private void draw() {
        if (!allowDraw) {
            return;
        }

        setIcon(icon);
        setCount(count);
        setIconSize(iconSize);
        setCountTextColor(countTextColor);
        setCountBackgroundColor(countBackgroundColor);
        setCountTypeface(countTypeface);
        setRippleColor(rippleColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setProgress(progress);
    }

    public void disableCell() {
        if (isEnabledCell) {
            animateProgress(false, true);
        }
        setEnabledCell(false);
    }

    public void enableCell(boolean isAnimate) {
        if (!isEnabledCell) {
            animateProgress(true, isAnimate);
        }
        setEnabledCell(true);
    }

    private void animateProgress(final boolean enableCell, boolean isAnimate) {
        long d;
        if (enableCell) {
            d = duration;
        } else {
            d = 250;
        }
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.setStartDelay(enableCell ? d / 4 : 0L);
        anim.setDuration(isAnimate ? d : 1L);
        anim.setInterpolator(new FastOutSlowInInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = animation.getAnimatedFraction();
                if (enableCell) {
                    setProgress(f);
                } else {
                    setProgress(1f - f);
                }
            }
        });
        anim.start();
    }
}