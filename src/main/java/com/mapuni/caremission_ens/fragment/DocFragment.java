package com.mapuni.caremission_ens.fragment;

import android.media.MediaActionSound;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mapuni.caremission_ens.R;
import com.mapuni.caremission_ens.adapter.assemblyadapter.AssemblyRecyclerAdapter;
import com.mapuni.caremission_ens.bean.FileBean;
import com.mapuni.caremission_ens.itemfactory.FileItemFactory;
import com.mapuni.caremission_ens.presenter.DownLoadFile;
import com.mapuni.caremission_ens.utils.JsonUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;


public class DocFragment extends BaseFragment {

    @Bind(R.id.failedLayout)
    LinearLayout failedLayout;
    AssemblyRecyclerAdapter adapter;
    ArrayList<FileBean> data;
    @Bind(R.id.recycle)
    RecyclerView recycle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doc, container, false);
        setTitle(view, "法律法规及技术标准");
        ButterKnife.bind(this, view);
        yutuLoading.showDialog();
        mControl.requestForDoc(call);
        return view;
    }

    private void initAdapter() {
        recycle.setLayoutManager(new LinearLayoutManager(mAct));
        adapter = new AssemblyRecyclerAdapter(data);
        recycle.setAdapter(adapter);
        DownLoadFile downFile = new DownLoadFile(mAct,adapter);
        adapter.addItemFactory(new FileItemFactory(mAct,downFile));

    }

    public static DocFragment newInstance(String s) {
        DocFragment fragment = new DocFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showFailed:
                yutuLoading.showDialog();
                delayedPost(new Runnable() {
                    @Override
                    public void run() {
                        mControl.requestForDoc(call);
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
            yutuLoading.dismissDialog();
            data = JsonUtil.fromJsonList(response, FileBean.class);
            if (data != null && data.size() > 0 && adapter == null) {
                initAdapter();
            }else{
                showFailed();
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
