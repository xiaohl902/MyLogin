package com.andysong.mylogin.common.network;


import com.andysong.mylogin.Ad;
import com.andysong.mylogin.DeviceBean;
import com.andysong.mylogin.UserBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Retrofit api
 * Created by Administrator on 2017/5/25.
 */

public interface ApiService {
    ///////////////////////////////////////////////////////////////////////////
    // 用户信息接口
    ///////////////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("GAPI/V1/Login")
    Observable<ApiResponse<UserBean>> login(@FieldMap Map<String, Object> params, @Query("timestamp") long timestamp, @Query("sign") String sign);

    // 主页数据
    @GET("GAPI/V1/GetAd")
    Observable<ApiResponse<List<Ad>>> getAllAd();


    //获取头像
    @GET("GAPI/V1/GetUserHeadImg")
    Observable<ApiResponse<String>> getUserHeadImg(@Query("u") String u);



    //单点登录
    @POST("GAPI/V1/SingleLoginCheck")
    Observable<ApiResponse<DeviceBean>> singleLoginCheck(@Header("auth") String token);

    // 主页数据
    @GET("GAPI/V1/Logout")
    Observable<ApiResponse<String>> logOut(@Query("auth") String auth);

}
