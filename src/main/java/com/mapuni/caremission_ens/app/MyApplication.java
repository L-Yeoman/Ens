package com.mapuni.caremission_ens.app;

import android.app.Application;

import com.mapuni.caremission_ens.presenter.NetControl;
import com.mapuni.caremission_ens.utils.JsonUtil;
import com.mapuni.caremission_ens.utils.SharepreferenceUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by yawei on 2017/4/6.
 */

public class MyApplication extends Application {
    private static int UpdateCount = 0;
    public NetControl netControl;
    @Override
    public void onCreate() {
        super.onCreate();
        netControl = new NetControl();
        netControl.messageCount(SharepreferenceUtil.getUpdateTime(this),call);
    }
    private StringCallback call = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
        }

        @Override
        public void onResponse(String response, int id) {
            String s = response;
            Map map = JsonUtil.jsonToMap(response);
            double count = (double) map.get("count");
            UpdateCount = (int) count;
        }
    };
    public static int getUpDateCount(){
        return UpdateCount;
    }
}
