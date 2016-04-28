package com.performance.ua.performancelab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.WeakHashMap;

/**
 * Created by sergey on 4/22/16.
 */
public class MemoryLeakActivity extends AppCompatActivity {
    public static void start(Context context) {
        context.startActivity(new Intent(context, MemoryLeakActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_layout);
        View view = findViewById(R.id.memory_view);
    }

}
