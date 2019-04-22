package com.etebarian.navigation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

/**
 * Created by 1HE on 10/23/2018.
 */

public class MeowBottomNavigation extends FrameLayout {

    ArrayList<Model> models = new ArrayList<>();
    ArrayList<MeowBottomNavigationCell> cells = new ArrayList<>();

    private int selectedId = -1;

    private IBottomNavigationListener mOnClickedListener;
    private IBottomNavigationListener mOnShowListener;

    private int heightCell = 0;
    private boolean isAnimating = false;

    private int defaultIconColor = Color.parseColor("#757575");
    private int selectedIconColor = Color.parseColor("#2196f3");
    private int backgroundBottomColor = Color.parseColor("#ffffff");
    private int shadowColor = -0x454546;
    private int countTextColor = Color.parseColor("#ffffff");
    private int countBackgroundColor = Color.parseColor("#ff0000");
    private Typeface countTypeface;
    private int rippleColor = Color.parseColor("#757575");

    private LinearLayout ll_cells;
    private BezierView bezierView;

    public MeowBottomNavigation(@NonNull Context context) {
        super(context);
        initializeViews();
    }

    public MeowBottomNavigation(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttributeFromXml(context, attrs);
        initializeViews();
    }

    public MeowBottomNavigation(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributeFromXml(context, attrs);
        initializeViews();
    }

    private void setAttributeFromXml(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MeowBottomNavigation, 0, 0);
        try {
            defaultIconColor = a.getColor(R.styleable.MeowBottomNavigation_mbn_defaultIconColor, defaultIconColor);
            selectedIconColor = a.getColor(R.styleable.MeowBottomNavigation_mbn_selectedIconColor, selectedIconColor);
            backgroundBottomColor = a.getColor(R.styleable.MeowBottomNavigation_mbn_backgroundBottomColor, backgroundBottomColor);
            countTextColor = a.getColor(R.styleable.MeowBottomNavigation_mbn_countTextColor, countTextColor);
            countBackgroundColor = a.getColor(R.styleable.MeowBottomNavigation_mbn_countBackgroundColor, countBackgroundColor);
            String typeface = a.getString(R.styleable.MeowBottomNavigation_mbn_countTypeface);
            rippleColor = a.getColor(R.styleable.MeowBottomNavigation_mbn_rippleColor, rippleColor);
            shadowColor = a.getColor(R.styleable.MeowBottomNavigation_mbn_shadowColor, shadowColor);

            if (typeface != null && !typeface.isEmpty()) {
                countTypeface = Typeface.createFromAsset(context.getAssets(), typeface);
            }
        } finally {
            a.recycle();
        }
    }

    private void initializeViews() {
        heightCell = Utils.dip(getContext(), 72);
        ll_cells = new LinearLayout(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heightCell);
        params.gravity = Gravity.BOTTOM;
        ll_cells.setLayoutParams(params);
        ll_cells.setOrientation(LinearLayout.HORIZONTAL);
        ll_cells.setClipChildren(false);
        ll_cells.setClipToPadding(false);

        bezierView = new BezierView(getContext());
        bezierView.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heightCell));
        bezierView.setColor(backgroundBottomColor);
        bezierView.setShadowColor(shadowColor);

        addView(bezierView);
        addView(ll_cells);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (selectedId == -1) {
            bezierView.setBezierX((Build.VERSION.SDK_INT >= 17 && getLayoutDirection() == LayoutDirection.RTL) ? getMeasuredWidth() + Utils.dipf(getContext(), 72)
                    : -Utils.dipf(getContext(), 72));
        }
        if (selectedId != -1) {
            show(selectedId, false);
        }
    }

    public void add(final Model model) {
        final MeowBottomNavigationCell cell = new MeowBottomNavigationCell(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, heightCell, 1f);
        cell.setLayoutParams(params);
        cell.setIcon(model.getIcon());
        cell.setCount(model.count);
        cell.setCircleColor(backgroundBottomColor);
        cell.setCountTextColor(countTextColor);
        cell.setCountBackgroundColor(countBackgroundColor);
        cell.setCountTypeface(countTypeface);
        cell.setRippleColor(rippleColor);
        cell.setDefaultIconColor(defaultIconColor);
        cell.setSelectedIconColor(selectedIconColor);
        cell.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cell.isEnabledCell() || isAnimating) {
                    return;
                }
                show(model.getId(),true);
                if (mOnClickedListener != null) {
                    mOnClickedListener.onClicked(model);
                }
            }
        });
        cell.disableCell();
        ll_cells.addView(cell);
        cells.add(cell);
        models.add(model);
    }

    public void show(int id, boolean enableAnimation) {
        for (int i = 0; i < models.size(); i++) {

            Model model = models.get(i);
            MeowBottomNavigationCell cell = cells.get(i);
            if (model.getId() == id) {
                anim(cell, id, enableAnimation);
                cell.enableCell(true);
                if (mOnShowListener != null) {
                    mOnShowListener.onClicked(model);
                }
            } else {
                cell.disableCell();
            }
        }
        selectedId = id;
    }

    private void anim(final MeowBottomNavigationCell cell, int id, Boolean enableAnimation) {//true
        isAnimating = true;

        int pos = getModelPosition(id);
        int nowPos = getModelPosition(selectedId);

        int nPos = (nowPos < 0) ? 0 : nowPos;
        int dif = Math.abs(pos - nPos);
        long d = (dif) * 100L + 150L;

        long animDuration = (enableAnimation) ? d : 1L;
        FastOutSlowInInterpolator animInterpolator = new FastOutSlowInInterpolator();

        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.setDuration(animDuration);
        anim.setInterpolator(animInterpolator);
        final float beforeX = bezierView.getBezierX();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator it) {
                float f = it.getAnimatedFraction();
                float newX = cell.getX() + (cell.getMeasuredWidth() / 2);
                if (newX > beforeX) {
                    bezierView.setBezierX(f * (newX - beforeX) + beforeX);
                } else {
                    bezierView.setBezierX(beforeX - f * (beforeX - newX));
                }
                if (f == 1f) {
                    isAnimating = false;
                }
            }
        });
        anim.start();

        if (Math.abs(pos - nowPos) > 1) {
            ValueAnimator progressAnim = ValueAnimator.ofFloat(0f, 1f);
            progressAnim.setDuration(animDuration);
            progressAnim.setInterpolator(animInterpolator);
            progressAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator it) {
                    float f = it.getAnimatedFraction();
                    bezierView.setProgress(f * 2f);
                }
            });
            progressAnim.start();
        }

        cell.setFromLeft(pos > nowPos);
        for (MeowBottomNavigationCell it : cells) {
            it.setDuration(d);

        }
    }

    public boolean isShowing(int id) {
        return selectedId == id;
    }

    Model getModelById(int id) {
        for (Model model : models) {
            if (model.getId() == id) {
                return model;
            }
        }
        return null;
    }

    public MeowBottomNavigationCell getCellById(int id) {
        return cells.get(getModelPosition(id));
    }

    public int getModelPosition(int id) {
        for (int i = 0; i < models.size(); i++) {

            Model item = models.get(i);
            if (item.getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public void setCount(int id, String count) {
        Model modelById = getModelById(id);
        if (modelById == null) {
            return;
        }
        Model model = modelById;
        int pos = getModelPosition(id);
        model.count = count;
        cells.get(pos).count = count;
    }

    public void setOnShowListener(IBottomNavigationListener listener) {
        mOnShowListener = listener;
    }

    public void setOnClickMenuListener(IBottomNavigationListener listener) {
        mOnClickedListener = listener;
    }
}
