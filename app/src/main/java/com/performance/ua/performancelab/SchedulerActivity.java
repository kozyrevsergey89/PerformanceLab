package com.performance.ua.performancelab;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by sergey on 4/22/16.
 */
public class SchedulerActivity extends AppCompatActivity {

    PowerManager mPowerManager;
    PowerManager.WakeLock mWakeLock;
    TextView mWakeLockMsg;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SchedulerActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scheduler_layout);
        mWakeLockMsg = (TextView) findViewById(R.id.scheduler_text);
        //BAD
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelockTag");


        findViewById(R.id.scheduler_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pollServer();
            }
        });
    }

    /**
     * Determines if the device is currently online.
     */
    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * These are placeholder methods for where your app might do something interesting! Try not to
     * confuse them with functional code.
     * <p/>
     * In this case, we are showing how your app might want to poll your server for an update that
     * isn't time-sensitive. Perhaps you have new data every day, or regularly scheduled content
     * updates that are not user-initiated. To perform these updates, you might use a wakelock in
     * a background service to fetch the content when the user is not currently using the phone.
     * These data fetches can benefit from batching.
     * <p/>
     * In this sample, we are going to demonstrate how to "poll" a server using a wakelock. For
     * brevity, in this sample, we are simplifying the situation by running the same task several
     * times in quick succession. However, in your app, try to think of similar tasks you run
     * several times throughout the day/week/etc. Is each occurrence necessary? Can any of them
     * wait? For example, how many times are you connecting to the network in the background?
     */
    private void pollServer() {
        mWakeLockMsg.append("Polling the server! This day sure went by fast.");
        for (int i = 0; i < 10; i++) {
            mWakeLock.acquire();
            mWakeLockMsg.append("Connection attempt, take " + i + ":\n");
            mWakeLockMsg.append("WakeLock acquired!");

            // Always check that the network is available before trying to connect. You don't want
            // to break things and embarrass yourself.
            if (isNetworkConnected()) {
//                new SimpleDownloadTask().execute();
                try {
                    wait(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                releaseWakeLock();
            } else {
                mWakeLockMsg.append("No connection on job " + i + "; SAD FACE");
            }
        }
    }

    private void releaseWakeLock() {
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
            mWakeLockMsg.append("WakeLock Released!");
        }
    }

}
