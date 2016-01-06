package com.modernexpo.task.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (sSyncAdapterLock) {}
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
