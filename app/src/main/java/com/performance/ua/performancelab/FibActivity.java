package com.performance.ua.performancelab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by sergey on 4/22/16.
 */
public class FibActivity extends AppCompatActivity {

    public static final String TAG = FibActivity.class.getSimpleName();
    public static final int POSITION_IN_FIB_SEQUENCE = 20;
    private TextView textView;

    private static final int CACHE_SIZE = 1000;
    private int[] cache = new int[CACHE_SIZE];

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
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        final int result = computeFibonacci(POSITION_IN_FIB_SEQUENCE);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(String.valueOf(result));
                            }
                        });
                    }
                }.start();
            }
        });
        WebView webView = (WebView) findViewById(R.id.anim_view);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/androidify.gif");
    }

    public int computeFibonacci(int positionInFibSequence) {
        if (positionInFibSequence < CACHE_SIZE) {
            if (cache[positionInFibSequence] != 0) {
                return cache[positionInFibSequence];
            }
        }
        int result;
        if (positionInFibSequence <= 2) {
            result = 1;
        } else {
            result = computeFibonacci(positionInFibSequence - 1)
                    + computeFibonacci(positionInFibSequence - 2);
        }
        cache[positionInFibSequence] = result;
        return result;
    }

}
