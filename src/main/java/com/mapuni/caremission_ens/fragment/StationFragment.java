package com.mapuni.caremission_ens.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.mapuni.caremission_ens.R;
import com.mapuni.caremission_ens.activity.ChartActivity;
import com.mapuni.caremission_ens.activity.DetailActivity;
import com.mapuni.caremission_ens.activity.StationActivity;
import com.mapuni.caremission_ens.adapter.ExpandaListAdapter;
import com.mapuni.caremission_ens.bean.AllStationBean;
import com.mapuni.caremission_ens.presenter.BaseControl;
import com.mapuni.caremission_ens.presenter.StationInfoControl;
import com.mapuni.caremission_ens.utils.JsonUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;


public class StationFragment extends BaseFragment implements ExpandableListView.OnChildClickListener {

    private ExpandableListView exList;
    private ExpandaListAdapter mAdapter;
    private AllStationBean bean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station, container, false);
        yutuLoading.showDialog();
        mControl.requestForAllStation(call);
        initView(view);
        return view;
    }

    public static StationFragment newInstance(String s) {
        StationFragment fragment = new StationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void initView(View view) {
        setTitle(view, "首页");
//        stationLayout.setOnClickListener(this);
//        airLayout.setOnClickListener(this);
//        sunLayout.setOnClickListener(this);
//        infoLayout.setOnClickListener(this);
        exList = (ExpandableListView) view.findViewById(R.id.exList);
        exList.setOnChildClickListener(this);
    }

    private void setAdapter() {

        if (bean == null) {
            return;
        }
        mAdapter = new ExpandaListAdapter(mAct, bean);
        exList.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
//            点击重试
            case R.id.showFailed:
                yutuLoading.showDialog();
//                延时发送
                delayedPost(new Runnable() {
                    @Override
                    public void run() {
                        mControl.requestForAllStation(call);
                    }
                }, 2000);
                requestAgain();
                break;
            case R.id.airLayout:
                intent = new Intent(mAct, ChartActivity.class);
                intent.putExtra("url", "file:///android_asset/public/air.html");
                startActivity(intent);
                break;
            case R.id.sunLayout:
                intent = new Intent(mAct, ChartActivity.class);
                intent.putExtra("url", "file:///android_asset/public/weiguicaozuo.html");
                startActivity(intent);
                break;
            case R.id.infoLayout:
                intent = new Intent(mAct, ChartActivity.class);
                intent.putExtra("url", "file:///android_asset/public/weixiuzhan.html");
                startActivity(intent);
                break;
            case R.id.stationLayout:
                intent = new Intent(mAct, StationActivity.class);
                startActivity(intent);
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
            bean = (AllStationBean) JsonUtil.jsonToBean(response, AllStationBean.class);
            yutuLoading.dismissDialog();
            if (bean != null && bean.getFlag() == 1) {
                setAdapter();
            } else {
                showFailed();
            }
        }
    };

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Intent intent = new Intent(mAct, DetailActivity.class);
        BaseControl model = new StationInfoControl();
        AllStationBean.DataBean.StationsBean station = (AllStationBean.DataBean.StationsBean) v.getTag(R.id.childText);
        model.setId(station.getPKID());
        model.setTitle(station.getSTATIONSHORTNAME());
        intent.putExtra("class", model);
        startActivity(intent);
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
