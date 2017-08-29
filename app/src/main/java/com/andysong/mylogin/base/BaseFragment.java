package com.andysong.mylogin.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *基类Fragment
 * Created by Administrator on 2017/5/25.
 *
 */

public abstract class BaseFragment extends RxFragment{

    private View parentView;

    private FragmentActivity activity;

    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;

    //标志位 fragment是否可见
    protected boolean isVisible;

    private Unbinder bind;


    public abstract
    @LayoutRes
    int getLayoutResId();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        activity = getSupportActivity();
        return parentView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        finishCreateView(savedInstanceState);
    }


    public abstract void finishCreateView(Bundle state);


    @Override
    public void onResume() {

        super.onResume();
    }


    @Override
    public void onDestroyView() {

        super.onDestroyView();
        bind.unbind();
    }


    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }


    @Override
    public void onDetach() {

        super.onDetach();
        this.activity = null;
    }


    public FragmentActivity getSupportActivity() {

        return super.getActivity();
    }


    public android.app.ActionBar getSupportActionBar() {

        return getSupportActivity().getActionBar();
    }


    public Context getApplicationContext() {

        return this.activity == null
                ? (getActivity() == null ? null :
                getActivity().getApplicationContext())
                : this.activity.getApplicationContext();
    }


    /**
     * Fragment数据的懒加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 当界面可见
     */
    protected void onVisible() {

        lazyLoad();
    }

    /**
     * 懒加载
     */
    protected void lazyLoad() {}

    /**
     * 当显示完成
     */
    protected void onInvisible() {}

    /**
     * 加载数据
     */
    protected void loadData() {}

    /**
     * 隐藏Progressbar
     */
    protected void showProgressBar() {}

    /**
     * 显示ProgressBar
     */
    protected void hideProgressBar() {}

    /**
     * 初始化RecyclerView
     */
    protected void initRecyclerView() {}

    /**
     * 刷新
     */
    protected void initRefreshLayout() {}

    /**
     * 完成加载之后
     */
    protected void finishTask() {}


    @SuppressWarnings("unchecked")
    public <T extends View> T $(int id) {

        return (T) parentView.findViewById(id);
    }
}
