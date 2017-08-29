package com.andysong.mylogin.common.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.andysong.mylogin.DeviceBean;

import com.andysong.mylogin.MainActivity;
import com.andysong.mylogin.common.db.AssociationData;
import com.andysong.mylogin.common.db.RealmUtils;
import com.andysong.mylogin.utils.CommonUitls;
import com.orhanobut.logger.Logger;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 单点登录的Servcie
 * Created by Administrator on 2017/7/17.
 */

public class SingleLoginService extends IntentService {
    private static String token;
    private static final String ACTION_CHECK_SINGLE_LOGIN = "com.andysong.mylogin.common.network.SingleLoginService";
    private static final String TAG = "print";

    public SingleLoginService() {
        super("SingleLoginService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
//        token = AssociationData.getUserToken();
//        token = AssociationData.getUserToken();
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHECK_SINGLE_LOGIN.equals(action)) {
                checkLogin();
            }
        }
    }

    public static void start(Context context) {
        token = AssociationData.getUserToken();
        Log.d(TAG, "启动服务前: "+ AssociationData.getUserToken());
        Intent intent = new Intent(context, SingleLoginService.class);
        intent.setAction(ACTION_CHECK_SINGLE_LOGIN);
        context.startService(intent);
        Log.d(TAG, "启动服务后: ");
    }

    private void checkLogin() {
        if(token==null){
            Log.d(TAG, "checkLogin: token为空");
            return;
        }
        RetrofitHelper.getApi()
                .singleLoginCheck(token)
                .subscribe( new Consumer<ApiResponse<DeviceBean>>() {
                            @Override
                            public void accept(@NonNull ApiResponse<DeviceBean> deviceBeanApiResponse) throws Exception {
                                if (deviceBeanApiResponse.getErrorCode() != 20000) {
                                    Thread.sleep(3000);
                                    Log.d(TAG, "accept: 服务启动中,开始检测");
                                    checkLogin();
                                } else {
                                    Log.d("print", "accept:     您已下线");

                                    if (RealmUtils.getInstance().existGYTInfo())//是否存在中鸽通服务
                                    {
                                        RealmUtils.getInstance().deleteGYTInfo();//删除中鸽通服务
                                    }
                                    if (RealmUtils.getInstance().existUserInfo())//是否存在用户信息
                                    {
                                        RealmUtils.getInstance().deleteUserInfo();//删除用户信息
                                    }
                                    AssociationData.info.clear();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                //抛出网络异常相关
                                if (throwable instanceof SocketTimeoutException) {
                                    CommonUitls.showToast(getApplication().getApplicationContext(), "网路有点不稳定，请检查网速");
                                } else if (throwable instanceof ConnectException) {
                                    CommonUitls.showToast(getApplication().getApplicationContext(), "未能连接到服务器，请检查连接");
                                }
                                else if (throwable instanceof RuntimeException) {
                                    CommonUitls.showToast(getApplication().getApplicationContext(), "发生了不可预期的问题");
                                }
                            }
                        });
    }
}
