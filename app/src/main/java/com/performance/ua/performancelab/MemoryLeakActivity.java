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

    static class ListenerCollector {
        // A common case is to want to store all the listeners for a particular type of view
        // somewhere.  Harmless AND convenient.  Or... is it? o_0
        static private WeakHashMap<View, MyCustomView.MyListener> sListeners = new WeakHashMap<View, MyCustomView.MyListener>();

        public void setListener(View view, MyCustomView.MyListener listener) {
            sListeners.put(view, listener);
        }
    }

}
