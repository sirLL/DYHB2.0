package com.wareroom.lib_base.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMultiAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> mData;

    public void setData(List<T> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        } else {
            mData.clear();
        }
        if (data != null && data.size() > 0) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void appendData(List<T> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }

        if (data != null && data.size() > 0) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
