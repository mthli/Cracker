package io.github.mthli.Cracker.Crash;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

public class CrashUnit {
    public static final int NOTIFICATION_ID = 0x100;

    public static Drawable getAppIcon(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();

        try {
            ApplicationInfo info = manager.getApplicationInfo(packageName, 0);
            return info.loadIcon(manager);
        } catch (PackageManager.NameNotFoundException n) {
            return null;
        }
    }

    public static String getAppName(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();

        try {
            ApplicationInfo info = manager.getApplicationInfo(packageName, 0);
            return info.loadLabel(manager).toString();
        } catch (PackageManager.NameNotFoundException n) {
            return null;
        }
    }
}
