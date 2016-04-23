package com.performance.ua.performancelab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by sergey on 4/22/16.
 */
public class MemoryChurnActivity extends AppCompatActivity {
    public static void start(Context context) {
        context.startActivity(new Intent(context, MemoryChurnActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.churn_layout);
        findViewById(R.id.churn_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imPrettySureSortingIsFree();
            }
        });
        WebView webView = (WebView) findViewById(R.id.anim_view);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/androidify.gif");
    }

    /**
     * Sorts and prints every row of a 2D array, one element at a time.
     */
    public void imPrettySureSortingIsFree() {
        // Throw together a nice, big 2D array of random numbers.
        int dimension = 300;
        int[][] lotsOfInts = new int[dimension][dimension];
        Random randomGenerator = new Random();
        for (int i = 0; i < lotsOfInts.length; i++) {
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                lotsOfInts[i][j] = randomGenerator.nextInt();
            }
        }

        for (int i = 0; i < lotsOfInts.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                // Clearly, the only reasonable way to construct a string is one character at a
                // time, with lots and lots of convenient concatenation.
                sb.append(getSorted(lotsOfInts[i])[j]);
                if (j < (lotsOfInts[i].length - 1)) {
                    sb.append(", ");
                }
            }
            Log.i("MemoryChurnActivity", "Row " + i + ": " + sb.toString());
        }
    }

    /**
     * Helper method, returns the sorted copy of an array.
     *
     * @param input the unsorted array
     * @return the sorted array
     */
    public int[] getSorted(int[] input) {
        int[] clone = input.clone();
        Arrays.sort(clone);
        return clone;
    }
}
