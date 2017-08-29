package com.andysong.mylogin.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.andysong.mylogin.common.network.ApiResponse;
import com.andysong.mylogin.service.databean.UseDevInfo;
import com.orhanobut.logger.Logger;

import org.xutils.http.RequestParams;

import static com.facebook.stetho.inspector.network.PrettyPrinterDisplayType.JSON;


/**
 * Created by Administrator on 2016/11/25.
 */

public class CallAPI {
//    /**
//     * 单点登录检查
//     *
//     * @param devid
//     * @param dev
//     * @param ver
//     * @param appid
//     * @param sltoken
//     * @param callback
//     * @return
//     */
//    public static org.xutils.common.Callback.Cancelable singleLoginCheck(Context context,
//                                                                         String devid,
//                                                                         String dev,
//                                                                         String ver,
//                                                                         String appid,
//                                                                         String sltoken, @NonNull final Callback callback) {
//        RequestParams params = new RequestParams(CPigeonApiUrl.getInstance().getServer() + CPigeonApiUrl.SINGLE_LOGIN_CHECK_URL);
//        pretreatmentParams(params);
//        params.addQueryStringParameter("devid", devid);
//        params.addQueryStringParameter("dev", dev);
//        params.addQueryStringParameter("ver", ver);
//        params.addQueryStringParameter("appid", appid);
//        params.addQueryStringParameter("sltoken", sltoken);
//        params.addQueryStringParameter("t", "1");
//        params.addQueryStringParameter("u", CpigeonData.getInstance().getUserId(context) + "");
//        params.addHeader("u", CommonTool.getUserToken(context));
//        addApiSign(params);
//        return x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {
//
//            @Override
//            public void onSuccess(String result) {
//                Logger.i(result);
//                try {
//                    ApiResponse<UseDevInfo> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<UseDevInfo>>() {
//                    });
//                    if (apiResponse.isStatus()) {
//                        callback.onSuccess(apiResponse.getData());
//                    } else {
//                        callback.onError(Callback.ERROR_TYPE_API_RETURN, apiResponse);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    callback.onError(Callback.ERROR_TYPE_PARSING_EXCEPTION, 0);
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                callback.onError(Callback.ERROR_TYPE_REQUST_EXCEPTION, ex);
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//    }
//
//
//
//
//    public interface Callback<E> {
//        int NO_ERROR = -1;
//        int ERROR_TYPE_OTHER = 0;//其他
//        int ERROR_TYPE_NOT_NETWORK = 1;//没有网络
//        int ERROR_TYPE_TIMEOUT = 2;//超时
//        int ERROR_TYPE_NOT_LOGIN = 3;//没有登录
//        int ERROR_TYPE_PARSING_EXCEPTION = 4;//解析异常
//        int ERROR_TYPE_API_RETURN = 5;//API返回代码
//        int ERROR_TYPE_REQUST_EXCEPTION = 6;//请求异常
//        int ERROR_TYPE_SAVE_TO_DB_EXCEPTION = 7;//数据保存异常
//
//        void onSuccess(E data);
//
//        void onError(int errorType, Object data);
//    }
//
//
//    /**
//     * 预处理处理请求配置
//     *
//     * @param requestParams
//     * @return
//     */
//    public static void pretreatmentParams(RequestParams requestParams) {
//        requestParams.setConnectTimeout(8000);
//        requestParams.setMaxRetryCount(0);
//    }

}