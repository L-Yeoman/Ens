package com.mapuni.caremission_ens.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.mapuni.caremission_ens.R;
import com.mapuni.caremission_ens.adapter.ExpandaListAdapter;
import com.mapuni.caremission_ens.bean.AllStationBean;
import com.mapuni.caremission_ens.presenter.BaseControl;
import com.mapuni.caremission_ens.presenter.NetControl;
import com.mapuni.caremission_ens.presenter.StationInfoControl;
import com.mapuni.caremission_ens.utils.JsonUtil;
import com.mapuni.caremission_ens.views.YutuLoading;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class StationActivity extends BaseActivity implements ExpandableListView.OnChildClickListener{
    private ExpandableListView exList;
    private ExpandaListAdapter mAdapter;
    private AllStationBean bean;
    private YutuLoading yutuLoading ;
    private NetControl mControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_station);
        yutuLoading  =  new YutuLoading(this);
        yutuLoading.showDialog();
        setTitle("站点信息",true,false);
        exList = (ExpandableListView)findViewById(R.id.exList);
        exList.setOnChildClickListener(this);
        mControl = new NetControl();
        mControl.requestForAllStation(call);
    }

    private void setAdapter(){

        if(bean == null){
            return;
        }
        mAdapter = new ExpandaListAdapter(this,bean);
        exList.setAdapter(mAdapter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            点击重试
            case R.id.showFailed:
                yutuLoading.showDialog();
//                延时发送
                delayedPost(new Runnable() {
                    @Override
                    public void run() {
                        mControl.requestForAllStation(call);
                    }
                },2000);
                requestAgain();
                break;
            default:
                super.onClick(v);
                break;
        }
    }
    public StringCallback call = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            yutuLoading.dismissDialog();
            showFailed();
        }

        @Override
        public void onResponse(String response, int id) {
            bean = (AllStationBean) JsonUtil.jsonToBean(response,AllStationBean.class);
            yutuLoading.dismissDialog();
            if(bean!=null&&bean.getFlag()==1){
                setAdapter();
            }else{
                showFailed();
            }
        }
    } ;

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Intent intent = new Intent(this, DetailActivity.class);
        BaseControl model = new StationInfoControl();
        AllStationBean.DataBean.StationsBean station = (AllStationBean.DataBean.StationsBean) v.getTag(R.id.childText);
        model.setId(station.getPKID());
        model.setTitle(station.getSTATIONSHORTNAME());
        intent.putExtra("class",model);
        startActivity(intent);
        return false;
    }
}
