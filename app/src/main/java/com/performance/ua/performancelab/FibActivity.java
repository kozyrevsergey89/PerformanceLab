package com.performance.ua.performancelab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
                textView.setText(String.valueOf(computeFibonacci(POSITION_IN_FIB_SEQUENCE)));
            }
        });
        WebView webView = (WebView) findViewById(R.id.anim_view);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/androidify.gif");
    }

    public int computeFibonacci(int positionInFibSequence) {
        //Log.i(TAG, "inside fib" + positionInFibSequence);
        if (positionInFibSequence <= 2) {
            return 1;
        } else {
            return computeFibonacci(positionInFibSequence - 1)
                    + computeFibonacci(positionInFibSequence - 2);
        }
    }

}
