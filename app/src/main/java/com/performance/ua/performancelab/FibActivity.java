package com.performance.ua.performancelab;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by sergey on 4/22/16.
 */
public class FibActivity extends AppCompatActivity {

    public static final String TAG = FibActivity.class.getSimpleName();
    public static final int POSITION_IN_FIB_SEQUENCE = 40;
    private TextView textView;
    private HashMap<Integer, Integer> mCacheMap = new HashMap<>();

    public static void start(Context context) {
        context.startActivity(new Intent(context, FibActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fib_layout);
        textView = (TextView) findViewById(R.id.fib_text);
        findViewById(R.id.fib_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 4; i++) {
                    long time = new Date().getTime();
                    String value = String.valueOf(computeFibonacci(POSITION_IN_FIB_SEQUENCE));
                    Log.d(TAG, String.valueOf(new Date().getTime() - time) + " ====" + value);
                    time = new Date().getTime();
                    String.valueOf(computeFibonacciOptimized(POSITION_IN_FIB_SEQUENCE));
                    Log.d(TAG, String.valueOf(new Date().getTime() - time) + " ====" + value);
                    new StupidAsyncTask().doInBackground(new Integer[]{POSITION_IN_FIB_SEQUENCE});
                }
            }
        });
        WebView webView = (WebView) findViewById(R.id.anim_view);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/androidify.gif");
    }

    public int computeFibonacci(int positionInFibSequence) {
//        Log.i(TAG, "inside fib" + positionInFibSequence);
        if (positionInFibSequence <= 2) {
            return 1;
        } else {
            return computeFibonacci(positionInFibSequence - 1)
                    + computeFibonacci(positionInFibSequence - 2);
        }
    }

    public int computeFibonacciOptimized(int positionInFibSequence) {
        if(mCacheMap.containsKey(positionInFibSequence)) {
            return mCacheMap.get(positionInFibSequence);
        }
//        Log.i(TAG, "inside fib" + positionInFibSequence);
        if (positionInFibSequence <= 2) {
            mCacheMap.put(positionInFibSequence, 1);
            return 1;
        } else {
            int number = computeFibonacciOptimized(positionInFibSequence - 1)
                    + computeFibonacciOptimized(positionInFibSequence - 2);
            mCacheMap.put(positionInFibSequence, number);
            return number;
        }
    }

    private class StupidAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer[] params) {
            return computeFibonacci(params[0]);
        }

        @Override
        protected void onPostExecute(Integer aVoid) {
            super.onPostExecute(aVoid);
            String.valueOf(aVoid);
        }
    }

}
