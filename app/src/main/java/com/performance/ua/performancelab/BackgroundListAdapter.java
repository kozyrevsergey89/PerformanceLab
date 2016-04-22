package com.performance.ua.performancelab;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sergey on 4/22/16.
 */
public class BackgroundListAdapter extends ArrayAdapter<String> {
    private final List<String> mTitles;

    public BackgroundListAdapter(Context context, int resource, List<String> titles) {
        super(context, resource);
        mTitles = titles;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v =  View.inflate(getContext(), R.layout.backgrounds_item, null);
        ((TextView)v.findViewById(R.id.title)).setText(mTitles.get(position));
        return v;
    }
}
