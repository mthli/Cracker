package io.github.mthli.Cracker.Crash;

import android.content.Context;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context context;
    private Thread.UncaughtExceptionHandler defaultHandler;

    private CrashHandler(Context context) {
        this.context = context;
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (!handleException(throwable) && defaultHandler != null) {
            defaultHandler.uncaughtException(thread, throwable);
            return;
        }

        // TODO

    }

    private boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }

        // TODO

        return true;
    }
}
