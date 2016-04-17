package com.example.f9483.italk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 94839 on 2016/4/17.
 */
public class TextAdapter extends BaseAdapter{
    private List<ListData> lists;
    //承接上下文的context
    private Context mContext;
    //
    private RelativeLayout layout;

    public TextAdapter(List<ListData> lists,Context mContext){
        this.lists=lists;
        this.mContext=mContext;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return super.areAllItemsEnabled();
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //布局过滤器，从mContext创建
        LayoutInflater inflater=LayoutInflater.from(mContext);
        //根据数据封装里面的flag操作视窗
        if (lists.get(position).getFlag()==ListData.RECEIVER){
            layout=(RelativeLayout) inflater.inflate(R.layout.leftitem,null);
        }
        if (lists.get(position).getFlag()==ListData.SEND){
            layout=(RelativeLayout) inflater.inflate(R.layout.rightitem,null);
        }
        //获取对应布局中的TV，设置文本
        TextView tv= (TextView) layout.findViewById(R.id.tv);
        //时间文本框
        TextView time= (TextView) layout.findViewById(R.id.time);
        tv.setText(lists.get(position).getContent());
        time.setText(lists.get(position).getTime());
        return layout;
    }
}
