package com.andysong.mylogin.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 无Scroll的ViewPager
 * Created by Administrator on 2017/5/31.
 *
 */

public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context) {

        super(context);
    }


    public NoScrollViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);
    }


    @Override
    public void scrollTo(int x, int y) {

        super.scrollTo(x, y);
    }


    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {

        super.setCurrentItem(item, smoothScroll);
    }


    @Override
    public void setCurrentItem(int item) {

        super.setCurrentItem(item, false);
    }
}