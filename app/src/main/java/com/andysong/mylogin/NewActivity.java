package com.andysong.mylogin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.andysong.mylogin.base.BaseActivity;
import com.andysong.mylogin.utils.StatusBarUtil;
import com.r0adkll.slidr.Slidr;

/**
 * Created by Administrator on 2017/8/30.
 */

public class NewActivity extends BaseActivity{

    @Override
    protected void swipeBack() {
        //右滑删除
        Slidr.attach(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }


    /**
     * 点击事件
     * @param view
     */
    public void doClick(View view){
        switch (view.getId()){
            case R.id.ac_btn_staNewActivity://当前页面的点击事件
                Toast.makeText(this,"新建立的Activity",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
