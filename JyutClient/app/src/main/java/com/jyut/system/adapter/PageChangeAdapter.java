package com.jyut.system.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Build;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.jyut.pagerslidingtabstrip_master.PagerSlidingTabStrip;

/**
 * ViewPager的Adapter<br>
 * {@inheritDoc}
 * @author wztscau
 * @date 10/5/2016
 * @project 粤盟管理系统客户端
 */

public class PageChangeAdapter implements ViewPager.OnPageChangeListener {

    private final Toolbar toolbar;
    private PagerSlidingTabStrip tabStrip;
    private Activity activity;

    /**
     * constructor
     *
     * @param tabStrip 顶部的页面滑动带,既可以点击也可以滑动
     * @param activity 主要为了提供resource
     * @param toolbar 顶部的toolbar工具栏
     */
    public PageChangeAdapter(PagerSlidingTabStrip tabStrip, Activity activity, Toolbar toolbar) {
        this.tabStrip = tabStrip;
        this.activity = activity;
        this.toolbar = toolbar;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        colorChanged(position);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 自定义的颜色组
     */
    private static int[] colorIds = {
            com.jyut.nohttp.R.color.red,
            com.jyut.nohttp.R.color.green,
            com.jyut.nohttp.R.color.blue,
            com.jyut.nohttp.R.color.yellow,
            com.jyut.nohttp.R.color.orange,
            com.jyut.nohttp.R.color.purple,
            com.jyut.nohttp.R.color.white,
            com.jyut.nohttp.R.color.black,
    };

    /**
     * 改变颜色,主要改变滑动带和工具栏的颜色,也为高版本改变通知栏和导航栏的颜色
     * @param position
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void colorChanged(int position) {
        tabStrip.setBackgroundResource(colorIds[position]);
        toolbar.setBackgroundResource(colorIds[position]);
        int rgb = activity.getResources().getColor(colorIds[position]);
        if (Build.VERSION.SDK_INT >= 23) {
            rgb = activity.getResources().getColor(colorIds[position], activity.getTheme());
        }
        tabStrip.setIndicatorColor(colorBurn(rgb));
        tabStrip.setTextColor(Color.WHITE);
        if(position==colorIds.length-2){
            tabStrip.setTextColor(Color.BLACK);
        }


        // 新API,低于android5.0的不能使用该方法
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.setStatusBarColor(colorBurn(rgb));
            window.setNavigationBarColor(colorBurn(rgb));
        }
    }

    /**
     * 颜色加深处理
     * @param RGBValues rgb的十进制值
     * @return 加深后的rgb十进制值
     */
    private int colorBurn(int RGBValues) {
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }

    /**
     * 反色处理
     * @param RGBValues rgb的十进制值
     * @return 反色后的rgb十进制值
     */
    private int colorReverse(int RGBValues) {
        int red = 255 - Color.red(RGBValues);
        int green = 255 - Color.green(RGBValues);
        int blue = 255 - Color.blue(RGBValues);
        return Color.rgb(red, green, blue);
    }
}
