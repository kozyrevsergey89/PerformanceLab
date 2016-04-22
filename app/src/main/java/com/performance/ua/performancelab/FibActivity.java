package com.performance.ua.performancelab;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
                //caching
//                cache[1] = 1;
//                textView.setText(String.valueOf(fibonacci(POSITION_IN_FIB_SEQUENCE)));

                //async
//                new MyFibonacciTask().execute();

                //straight
                textView.setText(String.valueOf(computeFibonacci(POSITION_IN_FIB_SEQUENCE)));
            }
        });

    }

    public int computeFibonacci(int positionInFibSequence) {
        Log.i(TAG, "inside fib" + positionInFibSequence);
        if (positionInFibSequence <= 2) {
            return 1;
        } else {
            return computeFibonacci(positionInFibSequence - 1)
                    + computeFibonacci(positionInFibSequence - 2);
        }
    }

    private int[] cache = new int[1000];

    public int fibonacci(int n) {

        if (cache[n] != 0) {
            return cache[n];
        }

        // n de 0 = 0
        if (n <= 0) {
            return 0;
        }

        int result = fibonacci(n - 1) + fibonacci(n - 2);
        cache[n] = result;
        return result;
    }

    class MyFibonacciTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            return String.valueOf(computeFibonacci(POSITION_IN_FIB_SEQUENCE));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }
    }


}
