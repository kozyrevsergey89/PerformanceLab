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
                //imPrettySureSortingIsFreeOptimized();
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
        for(int i = 0; i < lotsOfInts.length; i++) {
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                lotsOfInts[i][j] = randomGenerator.nextInt();
            }
        }

        // Now go through and dump the sorted version of each row to output!
        for(int i = 0; i < lotsOfInts.length; i++) {
            String rowAsStr = "";
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                // Clearly, the only reasonable way to construct a string is one character at a
                // time, with lots and lots of convenient concatenation.
                rowAsStr += getSorted(lotsOfInts[i])[j];
                if(j < (lotsOfInts[i].length - 1)){
                    rowAsStr += ", ";
                }
            }
            Log.i("MemoryChurnActivity", "Row " + i + ": " + rowAsStr);
        }
    }

    /**
     * Helper method, returns the sorted copy of an array.
     * @param input the unsorted array
     * @return the sorted array
     */
    public int[] getSorted(int[] input) {
        int[] clone = input.clone();
        Arrays.sort(clone);
        return clone;
    }


    /**
     * Sorts and prints every row of a 2D array, one element at a time.
     */
    public void imPrettySureSortingIsFreeOptimized() {
        // Throw together a nice, big 2D array of random numbers.
        int dimension = 300;
        int[][] lotsOfInts = new int[dimension][dimension];
        Random randomGenerator = new Random();
        for(int i = 0; i < lotsOfInts.length; i++) {
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                lotsOfInts[i][j] = randomGenerator.nextInt();
            }
        }

        // Now go through and dump the sorted version of each row to output! This time, use a
        // StringBuilder object so that we can construct one String per row, instead of wasting
        // String objects with ridiculous concatenation that never ends.
        // You may notice that the dancing pirate still seems to have lost the beat here. You would
        // be correct. What else could you do now to like that pirate keep his groove? What have you
        // learned?
        StringBuilder sb = new StringBuilder();
        String rowAsStr = "";
        for(int i = 0; i < lotsOfInts.length; i++) {
            sb.delete(0, rowAsStr.length()); // clear the previous row
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                sb.append(getSorted(lotsOfInts[i])[j]);
                if(j < (lotsOfInts[i].length - 1)){
                    sb.append(", ");
                }
            }
            rowAsStr = sb.toString();
            Log.i("MemoryChurnActivity", "Row " + i + ": " + rowAsStr);
        }
    }

}
