package com.mapuni.caremission_ens.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapuni.caremission_ens.R;
import com.mapuni.caremission_ens.adapter.assemblyadapter.AssemblyRecyclerAdapter;
import com.mapuni.caremission_ens.itemfactory.DetailItemFactory;
import com.mapuni.caremission_ens.presenter.BaseControl;
import com.mapuni.caremission_ens.utils.JsonUtil;
import com.mapuni.caremission_ens.views.MyDecoration;
import com.mapuni.caremission_ens.views.YutuLoading;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class DetailActivity extends BaseActivity {
    @Bind(R.id.recycle)
    RecyclerView recycle;
    List data;
    private ImageView backView;
    private TextView titleView;
    private BaseControl control;
    private YutuLoading yutuLoading;
    private AssemblyRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        yutuLoading = new YutuLoading(this);
        yutuLoading.showDialog();
        control = (BaseControl) getIntent().getExtras().get("class");
        setTitle(control.getTitle(),true,false);
        control.requestData(call);
    }

    private void initAdapter() {
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.addItemDecoration(new MyDecoration(this,MyDecoration.VERTICAL_LIST));
        adapter = new AssemblyRecyclerAdapter(data);
        adapter.addItemFactory(new DetailItemFactory(this));
        recycle.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showFailed:
                yutuLoading.showDialog();
                delayedPost(new Runnable() {
                    @Override
                    public void run() {
                        control.requestData(call);
                    }
                },2000);
                requestAgain();
                break;
            case R.id.leftIcon:
                finish();
        }

    }
    public StringCallback call = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            yutuLoading.dismissDialog();
            showFailed();
//            String s = e.toString();
        }

        @Override
        public void onResponse(String response, int id) {
            Map map = JsonUtil.jsonToMap(response);
            String flag = map.get("flag").toString().substring(0,1);
            yutuLoading.dismissDialog();
            if(flag.equals("1")){
                data = control.transData(response);
                if(data!=null&&data.size()>0)
                    initAdapter();
            }else{
                showFailed();
            }
        }
    } ;
}
