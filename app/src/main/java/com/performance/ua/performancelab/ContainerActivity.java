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
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by sergey on 4/22/16.
 */
public class ContainerActivity extends AppCompatActivity {
    public static void start(Context context) {
        context.startActivity(new Intent(context, ContainerActivity.class));
    }

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
        ArrayList<Integer> sortedNumbers = new ArrayList<>(coolestRandomNumbers);
        Collections.sort(sortedNumbers);

        for (Integer value : sortedNumbers) {
            Log.i("Popularity Dump", value + ": #" + coolestRandomNumbers.indexOf(value));
        }
        Trace.endSection();
    }

    public static List<Integer> coolestRandomNumbers = new ArrayList<>();

    static {
        Random randomGenerator = new Random();
        for (int i = 0; i < 3000; i++) {
            coolestRandomNumbers.add(randomGenerator.nextInt());
        }
    }
}
