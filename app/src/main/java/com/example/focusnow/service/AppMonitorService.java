package com.example.focusnow.service;

import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.example.focusnow.BlockingActivity;

import java.util.Arrays;
import java.util.List;

public class AppMonitorService extends Service {

    private Handler handler = new Handler();
    private Runnable runnable;

    private int distractionCount = 0;

    // Distracting apps
    private final List<String> blockedApps = Arrays.asList(
            "com.instagram.android",
            "com.google.android.youtube",
            "com.facebook.katana",
            "com.whatsapp"
    );

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        runnable = new Runnable() {
            @Override
            public void run() {

                checkForegroundApp();

                handler.postDelayed(this, 2000);
            }
        };

        handler.post(runnable);

        return START_STICKY;
    }

    private void checkForegroundApp() {

        UsageStatsManager usageStatsManager =
                (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);

        long time = System.currentTimeMillis();

        UsageEvents events = usageStatsManager.queryEvents(
                time - 5000,
                time
        );

        UsageEvents.Event event = new UsageEvents.Event();
        String currentApp = "";

        while (events.hasNextEvent()) {

            events.getNextEvent(event);

            if (event.getEventType() ==
                    UsageEvents.Event.MOVE_TO_FOREGROUND) {

                currentApp = event.getPackageName();
            }
        }

        if (blockedApps.contains(currentApp)) {

            distractionCount++;

            Intent intent = new Intent(
                    getApplicationContext(),
                    BlockingActivity.class
            );

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.putExtra("attempts", distractionCount);

            startActivity(intent);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}