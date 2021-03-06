package com.mapuni.caremission_ens.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapuni.caremission_ens.R;
import com.mapuni.caremission_ens.bean.AllStationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yawei on 2017/3/28.
 */

public class ExpandaListAdapter extends BaseExpandableListAdapter {
    private List groupList;
    private List<ArrayList> childList;
    private AllStationBean bean;
    private Context mContext;
    public ExpandaListAdapter(Context context,AllStationBean bean){
        this.bean = bean;
        mContext = context;
    }
    @Override
    public int getGroupCount() {
        return bean.getData().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return bean.getData().get(groupPosition).getStations().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return bean.getData().get(groupPosition).getRegionName();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return bean.getData().get(groupPosition).getStations().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHold hold;
        String text = bean.getData().get(groupPosition).getRegionName();
        int count = bean.getData().get(groupPosition).getStations().size();
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group_list,null);
            hold = new GroupHold();
            hold.mText = (TextView) convertView.findViewById(R.id.groupText);
            hold.mImage = (ImageView) convertView.findViewById(R.id.group_arrow);
            convertView.setTag(hold);
        }else{
            hold = (GroupHold) convertView.getTag();
        }
        hold.mText.setText(text+" ("+count+")");
        if(isExpanded){
            hold.mText.setTextColor(mContext.getResources().getColor(R.color.item_group_text_select));
            hold.mImage.setImageResource(R.mipmap.arrow_down);
        }else{
            hold.mText.setTextColor(mContext.getResources().getColor(R.color.item_group_text));
            hold.mImage.setImageResource(R.mipmap.arrow_right);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHold hold;
        AllStationBean.DataBean.StationsBean station = bean.getData().get(groupPosition).getStations().get(childPosition);
        String text = station.getSTATIONNAME();
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_child_list,null);
            hold = new ChildHold();
            hold.mText = (TextView) convertView.findViewById(R.id.childText);
            convertView.setTag(hold);
            convertView.setTag(R.id.childText,station);
        }else{
            hold = (ChildHold) convertView.getTag();
        }
        hold.mText.setText(text);
        return convertView;
    }

    public class ChildHold{
        public TextView mText;
    }
    class GroupHold{
        TextView mText;
        ImageView mImage;
    }
}
