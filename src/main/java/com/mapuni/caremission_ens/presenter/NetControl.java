package com.mapuni.caremission_ens.presenter;

import android.util.Log;

import com.mapuni.caremission_ens.config.PathManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by yawei on 2017/3/28.
 */

public class NetControl {
//    检测站信息
    public void requestForAllStation(StringCallback call){
        requestNetByGet(call, PathManager.GetRegionStation);
    }
//    查询监测站详情
    public void requestForStationSearch(String id,StringCallback call){
        Map map = new HashMap();
        map.put("pkid",id);
        requestNetByPost(map,call, PathManager.GetCheckStationInfo);
    }
//    查询车辆
    public void requestForCarInfo(Map map,StringCallback call){
        requestNetByPost(map,call, PathManager.GetCarInfo);
    }
    //    地图信息
    public void requestForGis(StringCallback call){
        requestNetByGet(call, PathManager.GetPointFilter);
    }
//    技术文档
    public void requestForDoc(StringCallback call){
        requestNetByGet(call, PathManager.GetAllFile);
    }
//    文档下载
    public  void downFile(String url,FileCallBack call,int id){
       requestNetByGet(call,url,id);
    }
//    通知列表
    public void allNews(int page,StringCallback call){
        Map map = new HashMap();
        map.put("page",page+"");
        map.put("rows",10+"");
        requestNetByPost(map,call, PathManager.GetAllCommonInfo);
    }
//    通知内容
    public void newsContent(String pkid,StringCallback call){
        Map map = new HashMap();
        map.put("pkid",pkid);
        requestNetByPost(map,call, PathManager.GetInfoContent);
    }
//   更新消息数量
    public void messageCount(String time,StringCallback call){
        Map map = new HashMap();
        map.put("time",time);
        requestNetByPost(map,call, PathManager.GetNewMessageCount);
    }
    private void requestNetByPost(Map params,Callback call,String uri){
        OkHttpUtils.post().url(uri).params(params).build().execute(call);
    }
    private void requestNetByGet(Callback call,String uri){
        OkHttpUtils.get().url(uri).build().execute(call);
    }
    private void requestNetByGet(FileCallBack call, String uri, int i){
        OkHttpUtils.get().url(uri).id(i).build().execute(call);
    }
}
