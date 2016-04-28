package com.performance.ua.performancelab;

import android.view.View;

import java.util.WeakHashMap;

/**
 * @author Evgeniy Tkachenko
 * @since 4/23/16.
 */
public class ListenerCollector {
    // A common case is to want to store all the listeners for a particular type of view
    // somewhere.  Harmless AND convenient.  Or... is it? o_0
    static private WeakHashMap<View, MyCustomView.MyListener> sListeners = new WeakHashMap<View, MyCustomView.MyListener>();

    public static void setListener(View view, MyCustomView.MyListener listener) {
        sListeners.put(view, listener);
    }

    public static void removeListeners(View view) {
        sListeners.remove(view);
    }
}
