package com.exercise.threadexample;

import android.app.*;
import android.os.*;
import android.widget.*;

import java.util.*;

public class MainApplication extends Application {

    private Stack<String> recordLifeCycle = new Stack<String>();

    public Stack<String> getRecordLifeCycle() {
        return recordLifeCycle;
    }

    public void setRecordLifeCycle(Stack<String> recordLifeCycle) {
        this.recordLifeCycle = recordLifeCycle;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifeCycle();
    }

    private void registerActivityLifeCycle(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                recordLifeCycle.push(activity.getLocalClassName() + " Created");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                recordLifeCycle.push(activity.getLocalClassName() + " Started");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                recordLifeCycle.push(activity.getLocalClassName() + " Resumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                recordLifeCycle.push(activity.getLocalClassName() + " Paused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                recordLifeCycle.push(activity.getLocalClassName() + " Stopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                recordLifeCycle.push(activity.getLocalClassName() + " Created");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                recordLifeCycle.push(activity.getLocalClassName() + " Destroyed");
            }
        });
    }
}
