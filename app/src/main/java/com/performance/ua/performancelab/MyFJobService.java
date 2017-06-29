package com.performance.ua.performancelab;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyFJobService extends JobService {

  private static final String LOG_TAG = "MyFJobService";

  @Override
  public boolean onStartJob(JobParameters params) {
    // Do some work here
    if (isNetworkConnected()) {
      new MyFJobService.SimpleDownloadTask().execute(params);
      return true;
    } else {
      Log.i(LOG_TAG, "No connection on job " + params.getTag() + "; sad face");
    }
    return false; // Answers the question: "Is there still work going on?"
  }

  @Override
  public boolean onStopJob(JobParameters job) {
    return false; // Answers the question: "Should this job be retried?"
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
   * Uses AsyncTask to create a task away from the main UI thread. This task creates a
   * HTTPUrlConnection, and then downloads the contents of the webpage as an InputStream.
   * The InputStream is then converted to a String, which is logged by the
   * onPostExecute() method.
   */
  private class SimpleDownloadTask extends AsyncTask<JobParameters, Void, String> {

    protected JobParameters mJobParam;

    @Override
    protected String doInBackground(JobParameters... params) {
      // cache system provided job requirements
      mJobParam = params[0];
      try {
        InputStream is = null;
        // Only display the first 50 characters of the retrieved web page content.
        int len = 50;

        URL url = new URL("https://www.google.com");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000); //10sec
        conn.setConnectTimeout(15000); //15sec
        conn.setRequestMethod("GET");
        //Starts the query
        conn.connect();
        int response = conn.getResponseCode();
        Log.d(LOG_TAG, "The response is: " + response);
        is = conn.getInputStream();

        // Convert the input stream to a string
        Reader reader = null;
        reader = new InputStreamReader(is, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);

      } catch (IOException e) {
        return "Unable to retrieve web page.";
      }
    }

    @Override
    protected void onPostExecute(String result) {
      jobFinished(mJobParam, false);
      Log.i(LOG_TAG, result);
    }
  }
}
