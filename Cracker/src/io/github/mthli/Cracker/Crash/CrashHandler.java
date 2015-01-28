package io.github.mthli.Cracker.Crash;

import android.content.Context;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context context;

    public CrashHandler(Context context) {
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        System.out.println("XXXXXXXXXXXXXXXXXXXX");
    }
}
