package io.github.mthli.Cracker.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CrackerService extends Service {

    @Override
    public void onCreate() {
        // TODO
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
