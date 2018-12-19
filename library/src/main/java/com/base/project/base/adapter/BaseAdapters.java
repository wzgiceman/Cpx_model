package com.base.project.base.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础封装adapter 封装Viewholder
 *
 * @param <T>
 * @author wzg
 */
public class BaseAdapters<T> extends BaseAdapter {
    // 数据
    private List<T> ltData;

    public BaseAdapters(List<T> ltData) {
        this.ltData = ltData;
    }

    public ArrayList<T> getLtData() {
        return new ArrayList<>(ltData);
    }

    public void setLtData(List<T> ltData) {
        this.ltData = ltData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ltData == null ? 0 : ltData.size();
    }

    @Override
    public Object getItem(int position) {
        return ltData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
