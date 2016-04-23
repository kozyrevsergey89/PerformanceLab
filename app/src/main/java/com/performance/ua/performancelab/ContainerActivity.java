package com.performance.ua.performancelab;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Trace;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by sergey on 4/22/16.
 */
public class ContainerActivity extends AppCompatActivity {
    public static void start(Context context) {
        context.startActivity(new Intent(context, ContainerActivity.class));
    }

    static HashMap<Integer, ArrayList<Integer>> hashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_layout);
        findViewById(R.id.container_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dumpPopularRandomNumbersByRank();
            }
        });
        WebView webView = (WebView) findViewById(R.id.anim_view);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/androidify.gif");
    }

    /**
     * Using the pre-formed array of random numbers ordered by popularity, prints out an ordered
     * list of the random number + rank in the form "(RandomNumber): #(Rank)".
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void dumpPopularRandomNumbersByRank() {
        Trace.beginSection("Data Structures");
        // First we need a sorted list of the numbers to iterate through.
        Integer[] sortedNumbers = coolestRandomNumbers.clone();
        Arrays.sort(sortedNumbers);

        // Great!  Now because we have no rank lookup in the population-sorted array,
        // take the random number in sorted order, and find its index in the array
        // that's sorted by popularity.  The index is the rank, so report that.  Easy and efficient!
        // Except that it's... you know... It's not.
        for (int i = 0; i < coolestRandomNumbers.length; i++) {
            if(hashMap.containsKey(coolestRandomNumbers[i])) {
                hashMap.get(coolestRandomNumbers[i]).add(i);
            } else {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(i);
                hashMap.put(coolestRandomNumbers[i], temp);
            }
        }
        for (Integer sortNumber : sortedNumbers) {
            if(hashMap.containsKey(sortNumber)) {
                ArrayList<Integer> indexes = hashMap.get(sortNumber);
                for (Integer index:indexes) {
                    Log.i("Popularity Dump", sortNumber + ": #" + index);
                }
            }
        }
        Trace.endSection();
    }

    public static Integer[] coolestRandomNumbers = new Integer[3000];

    static {
        Random randomGenerator = new Random();
        for (int i = 0; i < 3000; i++) {
            coolestRandomNumbers[i] = randomGenerator.nextInt();
        }
    }
}
