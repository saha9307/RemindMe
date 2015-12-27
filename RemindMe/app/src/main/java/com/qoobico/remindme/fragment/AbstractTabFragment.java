package com.qoobico.remindme.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qoobico.remindme.R;

import java.util.ArrayList;


/**
 * Created by Олександр on 26.12.2015.
 */
public class AbstractTabFragment extends Fragment {

    private String title;

    protected Context context;
    protected View view;
    protected static final int LAYOUT = R.layout.contact_item;
    protected RecyclerView mRecycler;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void viewmRecycler(){
        mRecycler.setAdapter(new DataManager());
    }
}
