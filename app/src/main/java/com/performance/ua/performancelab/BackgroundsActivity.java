package com.performance.ua.performancelab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.Arrays;

/**
 * Created by sergey on 4/22/16.
 */
public class BackgroundsActivity extends AppCompatActivity {
    public static void start(Context context) {
        context.startActivity(new Intent(context, BackgroundsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backgrounds_layout);
        ListView lv = (ListView) findViewById(R.id.backgrounds_list);
        if (lv != null) {
            lv.setAdapter(new BackgroundListAdapter(this, 0, Arrays.asList("title1", "title2", "title3")));
        }

    }
}
