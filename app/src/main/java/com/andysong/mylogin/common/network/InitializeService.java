package com.andysong.mylogin.common.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;


import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

/**
 *初始化App所需要的服务
 * Created by Administrator on 2017/7/17.
 *
 *
 */

public class InitializeService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.cpigeon.cpigeonhelper.common.network.InitializeService";

    public InitializeService() {
        super("InitializeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    private void performInit() {

        //初始化Bugly
        initBugly();
        //初始化Steho调试工具
        initSteho();
    }


    private void initSteho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this.getApplicationContext())
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this.getApplicationContext()))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this.getApplicationContext()))
                        .build());
    }

    private void initBugly() {
        CrashReport.initCrashReport(this.getApplicationContext(), "d27405999a", BuildConfig.DEBUG);
    }

}
