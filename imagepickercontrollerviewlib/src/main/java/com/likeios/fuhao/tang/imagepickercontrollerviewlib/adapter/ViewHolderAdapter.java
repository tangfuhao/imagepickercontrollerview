package com.likeios.fuhao.tang.imagepickercontrollerviewlib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by tang on 16/1/26.
 */
public abstract class ViewHolderAdapter <VH extends ViewHolderAdapter.ViewHolder, T> extends BaseAdapter {
    private Context mContext;
    private List<T> mList;
    private LayoutInflater mInflater;

    public ViewHolderAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public int getCount() {
        return this.mList.size();
    }

    public T getItem(int position) {
        return this.mList.get(position);
    }

    public long getItemId(int position) {
        return (long)position;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderAdapter.ViewHolder holder;
        if(view == null) {
            holder = this.onCreateViewHolder(viewGroup, i);
            holder.view.setTag(holder);
        } else {
            holder = (ViewHolderAdapter.ViewHolder)view.getTag();
        }

        this.onBindViewHolder((VH) holder, i);
        return holder.view;
    }

    public abstract VH onCreateViewHolder(ViewGroup var1, int var2);

    public abstract void onBindViewHolder(VH var1, int var2);

    public View inflate(int resLayout, ViewGroup parent) {
        return this.mInflater.inflate(resLayout, parent, false);
    }

    public List<T> getDatas() {
        return this.mList;
    }

    public Context getContext() {
        return this.mContext;
    }

    public LayoutInflater getLayoutInflater() {
        return this.mInflater;
    }

    public static class ViewHolder {
        View view;

        public ViewHolder(View view) {
            this.view = view;
        }
    }
}
