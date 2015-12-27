package com.qoobico.remindme.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;

import com.qoobico.remindme.fragment.AbstractTabFragment;
import com.qoobico.remindme.fragment.BirthdaysFragment;
import com.qoobico.remindme.fragment.HistoryFragment;
import com.qoobico.remindme.fragment.IdeasFragment;
import com.qoobico.remindme.fragment.TodoFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oleksandr.pachkovsky on 25.12.2015.
 */

public class TabsFragmentAdapter extends FragmentPagerAdapter{

    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;

    public TabsFragmentAdapter(Context context, FragmentManager fm, RecyclerView mRecycler) {
        super(fm);
        this.context = context;
        initTabsMap(context, mRecycler);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabsMap(Context context, RecyclerView mRecycler) {
        tabs = new HashMap<>();

        tabs.put(0, HistoryFragment.getInstance(context, mRecycler));
        tabs.put(1, IdeasFragment.getInstance(context, mRecycler));
        tabs.put(2, TodoFragment.getInstance(context, mRecycler));
        tabs.put(3, BirthdaysFragment.getInstance(context, mRecycler));
    }



}
