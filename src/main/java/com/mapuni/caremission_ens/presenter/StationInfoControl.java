package com.mapuni.caremission_ens.presenter;

import com.google.gson.internal.LinkedTreeMap;
import com.mapuni.caremission_ens.bean.AllStationBean;
import com.mapuni.caremission_ens.bean.DetailBean;
import com.mapuni.caremission_ens.utils.JsonUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by yawei on 2017/3/30.
 */

public class StationInfoControl extends BaseControl{
    private String id;
    private String title;
    private NetControl netControl;
    private String[] keys = new String[]{"REGION","STATIONNAME","STATIONADDRESS","FZRPHONE","STATIONSTATE","QIYOUCOUNT",
            "QINGCHAICOUNT","ZHONGCHAICOUNT","BUILDSTATECOUNT","business","notBusiness"};
    private String[] names = new String[]{"行政区名称:","检测站名称:","检测站地址:","负责人电话:","检测站状态:",
            "汽油检测线数量:","轻柴检测线数量:","重柴检测线数量:","在建检测线数量:","营业:","不营业:"};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public void requestData(StringCallback call){
        if(netControl==null){
            netControl = new NetControl();
        }
        netControl.requestForStationSearch(id,call);
    }

    @Override
    public List transData(String response) {
        List list = new ArrayList();
        Map map = JsonUtil.jsonToMap(response);
        LinkedTreeMap _Map = (LinkedTreeMap) map.get("data");
        for(int i = 0;i<keys.length;i++){
            DetailBean bean = new DetailBean();
            String value = (String) _Map.get(keys[i]);
            bean.setTitle(names[i]);
            bean.setValue(value);
            list.add(bean);
        }
        return list;
    }


}
