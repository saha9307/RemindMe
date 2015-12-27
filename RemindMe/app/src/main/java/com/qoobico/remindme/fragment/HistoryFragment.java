package com.qoobico.remindme.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qoobico.remindme.R;

/**
 * Created by oleksandr.pachkovsky on 25.12.2015.
 */
public class HistoryFragment extends AbstractTabFragment{

    public static HistoryFragment getInstance(Context context, RecyclerView mRecycler){
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setRecyclerView(mRecycler);
        fragment.setTitle(context.getString(R.string.tab_item_history));
        fragment.viewmRecycler();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    protected void setRecyclerView( RecyclerView mRecycler) {
        this.mRecycler = mRecycler;
    }

}
