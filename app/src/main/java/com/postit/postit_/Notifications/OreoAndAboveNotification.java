package com.postit.postit_.Notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

public class OreoAndAboveNotification extends ContextWrapper {
    private static final  String ID= "some_id";
    private static final  String NAME= "PostItAPP";
    
    private NotificationManager notificationManager;

    public OreoAndAboveNotification(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O) {

        createChannel();
        }
    }

    private void createChannel() {
    }
}
