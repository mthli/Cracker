package io.github.mthli.Cracker.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import io.github.mthli.Cracker.Crash.CrashHandler;

public class CrackerService extends Service {
    private CrashHandler handler;

    @Override
    public void onCreate() {
        super.onCreate();

        System.out.println("------------------------");
        System.out.println("onCreate()");
        System.out.println("------------------------");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("------------------------");
        System.out.println("onStartCommand()");
        System.out.println("------------------------");

        handler = new CrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(null);
        Thread.setDefaultUncaughtExceptionHandler(handler);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        System.out.println("------------------------");
        System.out.println("onDestroy()");
        System.out.println("------------------------");

        super.onDestroy();
    }
}
