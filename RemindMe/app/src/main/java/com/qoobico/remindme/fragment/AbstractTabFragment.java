package com.qoobico.remindme.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Олександр on 26.12.2015.
 */
public class AbstractTabFragment extends Fragment {

    private String title;

    protected Context context;
    protected View view;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
