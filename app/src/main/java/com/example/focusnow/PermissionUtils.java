package com.example.focusnow;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Process;

public class PermissionUtils {

    public static boolean hasUsageAccess(Context context) {

        AppOpsManager appOps =
                (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

        int mode = appOps.checkOpNoThrow(
                "android:get_usage_stats",
                Process.myUid(),
                context.getPackageName()
        );

        return mode == AppOpsManager.MODE_ALLOWED;
    }
}