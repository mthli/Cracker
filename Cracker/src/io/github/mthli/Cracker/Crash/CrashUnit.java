package io.github.mthli.Cracker.Crash;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

public class CrashUnit {
    public static final String TABLE = "CRASH";

    public static final String PACKAGE_NAME = "PACKAGE_NAME";
    public static final String TIME = "TIME";
    public static final String CONTENT = "CONTENT";

    public static final String CREATE_SQL = "CREATE TABLE "
            + TABLE
            + " ("
            + " " + PACKAGE_NAME + " text,"
            + " " + TIME + " integer,"
            + " " + CONTENT + " text"
            + ")";

    // TODO
    public static String getPackageName(String content) {
        return null;
    }

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
