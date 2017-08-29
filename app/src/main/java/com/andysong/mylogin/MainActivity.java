package com.andysong.mylogin;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.andysong.mylogin.base.BaseActivity;
import com.andysong.mylogin.common.db.AssociationData;
import com.andysong.mylogin.common.db.RealmUtils;
import com.andysong.mylogin.common.network.ApiResponse;
import com.andysong.mylogin.common.network.RetrofitHelper;
import com.andysong.mylogin.common.network.SingleLoginService;
import com.andysong.mylogin.utils.CommonUitls;
import com.squareup.picasso.Picasso;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Header;

public class MainActivity extends BaseActivity {

    EditText etUsername;//用户名字
    EditText userPsd;//用户密码

    ImageView userImg;//

    String TAG = "print";
    UserBean bean = new UserBean();

    @Override
    protected void swipeBack() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setStatusBar() {

    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        etUsername = (EditText) findViewById(R.id.ac_ed_userName);
        userPsd = (EditText) findViewById(R.id.ac_ed_userPsd);
        userImg = (ImageView) findViewById(R.id.ac_img_userName);
    }


    /**
     * 点击事件
     */
    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.ac_btn_login://点击登录

                //getAD();//获取头部数据
                login();//点击登录
                break;

            case R.id.ac_btn_loginQure://查看是否登录
                //单点登录
                Log.d(TAG, "获取当前用户的token: " + AssociationData.getUserToken());
//                checkLogin();
                break;
            case R.id.ac_btn_logOut://退出登录
                logOut();
                break;
            case R.id.ac_btn_staServer://启动服务
                SingleLoginService.start(MainActivity.this);//启动服务
                break;
        }
    }


    /**
     * 单点登录
     */
    private void checkLogin() {
        RetrofitHelper.getApi()
                .singleLoginCheck(AssociationData.getUserToken())
                .subscribe(
                        new Consumer<ApiResponse<DeviceBean>>() {
                            @Override
                            public void accept(@NonNull ApiResponse<DeviceBean> deviceBeanApiResponse) throws Exception {
                                if (deviceBeanApiResponse.getErrorCode() != 20000) {
                                    Log.d(TAG, "查看是否登录:   已经登录");
                                } else {
                                    Log.d("print", "accept:     您已下线");
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                throwException(throwable);//抛出异常
                            }
                        });
    }


    /**
     * 用户登录
     */
    private void login() {

        //清除用户资料
        if (RealmUtils.getInstance().existUserInfo())//是否存在用户信息
        {
            RealmUtils.getInstance().deleteUserInfo();//删除用户信息
        }
        if(AssociationData.getUserToken()!=null){
            AssociationData.info.clear();
        }


        long timestamp = System.currentTimeMillis() / 1000;//当前时间戳，签名验证使用
        Map<String, Object> postParams = new HashMap<>();
        final String pas = "123456789";
        String userName = "18782050317";

        postParams.put("u", userName.trim());//用户名或手机号码
        postParams.put("p", EncryptionTool.encryptAES(pas.trim()));//密码(AES 加密)
        postParams.put("t", "1");//APP 类型【1：安卓；2：IPHONE】
        postParams.put("lt", "cpmanhel");//登录类型【cpmanhel:中鸽助手登录，默认为中鸽网登录】
        postParams.put("devid", AssociationData.DEV_ID);//设备 ID【设备唯一编号，通过各种渠道获取并计算，最后 MD5 加密】
        postParams.put("dev", AssociationData.DEV);//设备信息【例如：BLN-AL10)】
        postParams.put("ver", AssociationData.VER);//APP 版本代码
        postParams.put("appid", BuildConfig.APPLICATION_ID);//应用标识 【Android 为 applicationId；IOS 为 BundleId】


        RetrofitHelper.getApi()
                .login(postParams, timestamp, CommonUitls.getApiSign(timestamp, postParams))
                .compose(this.<ApiResponse<UserBean>>bindToLifecycle())//组成（绑定声明周期）
                .subscribeOn(Schedulers.io())//订阅（调度程序）
                .observeOn(AndroidSchedulers.mainThread())//观察（调度程序.主线程）
                .subscribe(new Consumer<ApiResponse<UserBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<UserBean> userBeanApiResponse) throws Exception {
                        if (userBeanApiResponse != null) {
                            Log.d(TAG, "accept: 获取到用户头像       " + userBeanApiResponse.getData().getHeadimgurl());
                            Log.d(TAG, "accept: 获取到用户密码       " + userBeanApiResponse.getData().getPassword());
                            Log.d(TAG, "accept: 获取到用户用户名       " + userBeanApiResponse.getData().getUsername());
                            Log.d(TAG, "accept: 获取到用户昵称        " + userBeanApiResponse.getData().getNickname());
                            Log.d(TAG, "accept: 获取到用户ID          " + userBeanApiResponse.getData().getUid());
                            Log.d(TAG, "accept: 获取到用户账户类型    " + userBeanApiResponse.getData().getAccountType());
                            Log.d(TAG, "accept: 获取到用户通行证      " + userBeanApiResponse.getData().getToken());
                            Log.d(TAG, "accept: 获取到用户签名        " + userBeanApiResponse.getData().getSign());
                            Log.d(TAG, "accept: 获取到用户单点登录通行证    " + userBeanApiResponse.getData().getSltoken());


                            bean.setSign(TextUtils.isEmpty(userBeanApiResponse.getData().getSign())
                                    ? "这家伙很懒，什么都没有留下" : userBeanApiResponse.getData().getSign());//存放用户签名
                            bean.setHeadimgurl(userBeanApiResponse.getData().getHeadimgurl());
                            bean.setNickname(TextUtils.isEmpty(userBeanApiResponse.getData().getNickname())
                                    ? userBeanApiResponse.getData().getUsername()
                                    : userBeanApiResponse.getData().getNickname());
                            bean.setPassword(EncryptionTool.encryptAES(pas.trim()));
                            bean.setSltoken(userBeanApiResponse.getData().getSltoken());
                            bean.setUsername(userBeanApiResponse.getData().getUsername());
                            bean.setToken(userBeanApiResponse.getData().getToken());
                            bean.setUid(userBeanApiResponse.getData().getUid());
                            bean.setAccountType(userBeanApiResponse.getData().getAccountType());
                            bean.setAtype("admin");
                            bean.setType("xiehui");
                            RealmUtils.getInstance().insertUserInfo(bean);//添加用户数据

                            SingleLoginService.start(MainActivity.this);//启动服务
                        } else {
                            Log.d(TAG, "accept:   获取数据为空");
                        }
                        //设置数据
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwException(throwable);//抛出异常
                    }
                })
        ;

    }

    /**
     * 退出登录
     */
    private void logOut() {
        if (!AssociationData.checkIsLogin()) {//如果没有登录就不执行下面的代码
            Log.d(TAG, "用户没有登录");
            return;
        }

        RetrofitHelper.getApi()
                .logOut(AssociationData.getUserToken())
                .compose(this.<ApiResponse<String>>bindToLifecycle())//组成（绑定声明周期）
                .subscribeOn(Schedulers.io())//订阅（调度程序）
                .observeOn(AndroidSchedulers.mainThread())//观察（调度程序.主线程）
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> stringApiResponse) throws Exception {

                        if (stringApiResponse != null) {
                            Log.d(TAG, "accept: 退出登录" + stringApiResponse.getData());
                            if (RealmUtils.getInstance().existGYTInfo())//是否存在中鸽通服务
                            {
                                Log.d(TAG, "accept: 否存在中鸽通服务    删除中鸽通服务");
                                RealmUtils.getInstance().deleteGYTInfo();//删除中鸽通服务
                            }
                            if (RealmUtils.getInstance().existUserInfo())//是否存在用户信息
                            {
                                Log.d(TAG, "accept: 是否存在用户信息      删除用户信息");
                                RealmUtils.getInstance().deleteUserInfo();//删除用户信息
                                AssociationData.info.clear();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                        throwException(throwable);//抛出异常
                    }
                });
    }

    /**
     * 获取首页头图轮播图片
     */
    private void getAD() {
        RetrofitHelper.getApi().getAllAd()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<ApiResponse<List<Ad>>>bindToLifecycle())
                .subscribe(new Consumer<ApiResponse<List<Ad>>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<List<Ad>> listApiResponse) throws Exception {
                        if (listApiResponse != null) {
                            int size = listApiResponse.getData().size();
                            Log.d(TAG, "accept: 数据数量为   " + size);
                            Log.d(TAG, "accept: " + listApiResponse.getData().get(0).getAdImageUrl());
                            Log.d(TAG, "accept: " + listApiResponse.getData().get(1).getAdImageUrl());
                            Log.d(TAG, "accept: " + listApiResponse.getData().get(2).getAdImageUrl());
                            Log.d(TAG, "accept: " + listApiResponse.getData().get(3).getAdImageUrl());
                        } else {
                            Log.d(TAG, "获取数据为空");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwException(throwable);//抛出异常
                    }
                });
    }

    /**
     * hl 输入用户名，显示头像
     *
     * @param s
     */
    @OnTextChanged(value = R.id.ac_ed_userName, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterNameEditTextChanged(Editable s) {
        if (CommonUitls.isAccountValid(etUsername.getText().toString().trim())) {
            RetrofitHelper.getApi()
                    .getUserHeadImg(etUsername.getText().toString().trim())
                    .compose(this.<ApiResponse<String>>bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ApiResponse<String>>() {
                        @Override
                        public void accept(@NonNull ApiResponse<String> stringApiResponse) throws Exception {
                            Picasso.with(mContext).load(TextUtils.isEmpty(stringApiResponse.getData()) ? null : stringApiResponse.getData())
                                    .placeholder(R.mipmap.logos)
                                    .error(R.mipmap.logos)
                                    .resizeDimen(R.dimen.image_width_headicon, R.dimen.image_height_headicon)
                                    .config(Bitmap.Config.RGB_565)
                                    .onlyScaleDown()
                                    .into(userImg);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            throwException(throwable);//抛出异常
                        }
                    });
        } else if (etUsername.getText().toString().trim().length() < 11 || !CommonUitls.isAccountValid(etUsername.getText().toString())) {
            Picasso.with(mContext).load(R.mipmap.logos).into(userImg);
        }

    }

    /**
     * 抛出异常
     * @param throwable
     */
    private void throwException(Throwable throwable){

        //抛出网络异常相关
        if (throwable instanceof SocketTimeoutException) {
            CommonUitls.showToast(MainActivity.this, "网路有点不稳定，请检查网速");
        } else if (throwable instanceof ConnectException) {
            CommonUitls.showToast(MainActivity.this, "未能连接到服务器，请检查连接");
        } else if (throwable instanceof RuntimeException) {
            CommonUitls.showToast(MainActivity.this, "发生了不可预期的问题");
        }
    }

}
