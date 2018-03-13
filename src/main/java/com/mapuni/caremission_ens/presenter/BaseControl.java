package com.mapuni.caremission_ens.presenter;

import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.List;


public abstract class BaseControl implements Serializable {
    public String id;
    public String title;
    public abstract  String getId() ;
    public abstract  void setId(String id);

    public abstract  String getTitle();

    public abstract  void setTitle(String title) ;



    public abstract void requestData(StringCallback call);
    public abstract List transData(String response);
}
