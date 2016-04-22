package com.performance.ua.performancelab;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by sergey on 4/22/16.
 */
public class SchedulerActivity extends AppCompatActivity {

    private ComponentName mServiceComponent;
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

        //GOOD
        mServiceComponent = new ComponentName(this, MyJobService.class);
        Intent startServiceIntent = new Intent(this, MyJobService.class);
        startService(startServiceIntent);

        findViewById(R.id.scheduler_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pollServer();
//                downloadSmarter();// or any other type of download
            }
        });
    }


    /**
     * This method checks for power by comparing the current battery state against all possible
     * plugged in states. In this case, a device may be considered plugged in either by USB, AC, or
     * wireless charge. (Wireless charge was introduced in API Level 17.)
     */
    private boolean checkForPower() {
        // It is very easy to subscribe to changes to the battery state, but you can get the current
        // state by simply passing null in as your receiver.  Nifty, isn't that?
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, filter);

        // There are currently three ways a device can be plugged in. We should check them all.
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_USB);
        boolean acCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_AC);
        boolean wirelessCharge = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wirelessCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS);
        }
        return (usbCharge || acCharge || wirelessCharge);
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
     *
     * In this case, we are showing how your app might want to poll your server for an update that
     * isn't time-sensitive. Perhaps you have new data every day, or regularly scheduled content
     * updates that are not user-initiated. To perform these updates, you might use a wakelock in
     * a background service to fetch the content when the user is not currently using the phone.
     * These data fetches can benefit from batching.
     *
     * In this sample, we are going to demonstrate how to "poll" a server using a wakelock. For
     * brevity, in this sample, we are simplifying the situation by running the same task several
     * times in quick succession. However, in your app, try to think of similar tasks you run
     * several times throughout the day/week/etc. Is each occurrence necessary? Can any of them
     * wait? For example, how many times are you connecting to the network in the background?
     */
    private void pollServer() {
        mWakeLockMsg.append("Polling the server! This day sure went by fast.");
        for (int i=0; i<10; i++) {
            mWakeLock.acquire();
            mWakeLockMsg.append("Connection attempt, take " + i + ":\n");
            mWakeLockMsg.append("WakeLock acquired!");

            // Always check that the network is available before trying to connect. You don't want
            // to break things and embarrass yourself.
            if (isNetworkConnected()) {
                //new SimpleDownloadTask().execute();
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

    /**
     * This method polls the server via the JobScheduler API. By scheduling the job with this API,
     * your app can be confident it will execute, but without the need for a wake lock. Rather, the
     * API will take your network jobs and execute them in batch to best take advantage of the
     * initial network connection cost.
     *
     * The JobScheduler API works through a background service. In this sample, we have
     * a simple service in MyJobService to get you started. The job is scheduled here in
     * the activity, but the job itself is executed in MyJobService in the startJob() method. For
     * example, to poll your server, you would create the network connection, send your GET
     * request, and then process the response all in MyJobService. This allows the JobScheduler API
     * to invoke your logic without needed to restart your activity.
     *
     * For brevity in the sample, we are scheduling the same job several times in quick succession,
     * but again, try to consider similar tasks occurring over time in your application that can
     * afford to wait and may benefit from batching.
     */
    public void pollServerJob() {
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        for (int i=0; i<10; i++) {
            JobInfo jobInfo = new JobInfo.Builder(i, mServiceComponent)
                    .setMinimumLatency(5000) // 5 seconds
                    .setOverrideDeadline(60000) // 60 seconds (for brevity in the sample)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // WiFi or data connections
                    .build();

            mWakeLockMsg.append("Scheduling job " + i + "!\n");
            scheduler.schedule(jobInfo);
        }
    }

    /**
     * This method polls the server via the JobScheduler API. This is the same as in the previous
     * task concerning wake locks, but now we can fine-tune our job requirements so that we are
     * only connecting via WiFi. There are many possible configurations with the JobScheduler API,
     * and you can use them to fine-tune your job requirements. Check the documentation for more
     * information.
     * https://developer.android.com/reference/android/app/job/JobInfo.Builder.html
     */
    private void downloadSmarter() {
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        // Beginning with 10 here to distinguish this activity's jobs from the
        // FreeTheWakelockActivity's jobs within the JobScheduler API.
        for (int i=10; i<20; i++) {
            JobInfo jobInfo = new JobInfo.Builder(i, mServiceComponent)
                    .setMinimumLatency(5000) // 5 seconds
                    .setOverrideDeadline(60000) // 60 seconds (for brevity in the sample)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) // for Wifi only
                    .build();

            mWakeLockMsg.append("Scheduling job " + i + "!\n");
            scheduler.schedule(jobInfo);
        }
    }
}
