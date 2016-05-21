package com.example.ultrabook.rhinotube;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

public class VideoApp extends Application {

    EventBus mEventBus;

    @Override
    public void onCreate() {
        super.onCreate();

        mEventBus = new EventBus();
    }

    public EventBus getEventBus(){
        return mEventBus;
    }
}
