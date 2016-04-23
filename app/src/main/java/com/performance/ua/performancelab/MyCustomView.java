package com.performance.ua.performancelab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sergey on 4/22/16.
 */
public class MyCustomView extends View {

    public interface MyListener {
        void someListenerCallback();
    }

    /**
     * Internal initialization procedures for this view, regardless of which constructor was called.
     */
    private void init() {
        ListenerCollector.setListener(this, mListener);
    }

    public MyCustomView(Context context) {
        super(context);
        init();
    }

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private MyListener mListener = new MyListener() {
        @Override
        public void someListenerCallback() {
            System.out.println("Someone called me!");
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ListenerCollector.removeListeners(this);
    }
}
