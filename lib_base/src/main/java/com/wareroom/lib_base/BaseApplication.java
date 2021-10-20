package com.wareroom.lib_base;

import android.app.Application;

import com.hjq.toast.ToastUtils;
import com.tencent.mmkv.MMKV;
import com.wareroom.lib_base.net.RetrofitServiceManager;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
        MMKV.initialize(this);
        RetrofitServiceManager.initialize(this);
    }
}
