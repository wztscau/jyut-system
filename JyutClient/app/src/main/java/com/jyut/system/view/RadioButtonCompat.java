package com.jyut.system.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RadioButton;

/**
 * 针对radiobutton没有控制drawabletop的方法,特定设置一个这样的类
 * @author wztscau
 * @date 10/8/2016
 * @project 粤盟管理系统客户端
 */

public class RadioButtonCompat extends AppCompatRadioButton {
    private static final String TAG = "RadioButtonCompat";

    public RadioButtonCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RadioButtonCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioButtonCompat(Context context) {
        super(context);
    }

    /**
     * 改变drawable大小,这里只提供改变DrawableTop的方法
     * @param width 整个控件的宽
     * @param height 整个控件的高
     */
    public void changeDrawableSize(int width, int height) {
        if (isInEditMode()) {
            return;
        }
        Rect rect = new Rect();
        float f = (float) (2.0 / 3.0);
        // 这里分别是 left top right bottom  代表距离父view 的距离   长宽 是  right-left   bottom-top
        rect.set(0, 0, (int) (f * height), (int) (f * height));
        // 第一个就是DrawableTop
        Drawable drawable = getCompoundDrawables()[1];
        // 设置约束范围
        drawable.setBounds(rect);
        setCompoundDrawables(null, drawable, null, null);
    }

    private boolean draw;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 只绘制一次,省cpu
        if (!draw) {
            Log.d(TAG, "onDraw: getwidth--" + getWidth() + " getheight--" + getHeight());
            Log.d(TAG, "onDraw: getMeasuredwidth--" + getMeasuredWidth() + "  getMeasuredHeight--" + getMeasuredHeight());
            changeDrawableSize(getWidth(), getHeight());
            draw = true;
        }
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawables(left, top, right, bottom);
        draw = false;
    }

    /**
     * 设置被选中和为选中时的drawable和textColor
     * @param checkedDrawableId 选中时的drawableId
     * @param unCheckDrawableId 未选中时的drawableId
     * @param checkedTextColor 选中时的textColor
     * @param unCheckTextColor 未中时的textColor
     */
    public void setCheckDrawableTopAndTextStyle(int checkedDrawableId, int unCheckDrawableId, int checkedTextColor, int unCheckTextColor) {
        if (isChecked()) {
            setCompoundDrawables(null, getResources().getDrawable(checkedDrawableId), null, null);
            setTextColor(checkedTextColor);
        } else {
            setCompoundDrawables(null, getResources().getDrawable(unCheckDrawableId), null, null);
            setTextColor(unCheckTextColor);
        }
        // 重新绘制一遍
        invalidate();
    }
}
