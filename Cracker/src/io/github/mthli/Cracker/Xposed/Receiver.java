package io.github.mthli.Cracker.Xposed;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import io.github.mthli.Cracker.Activity.DetailActivity;
import io.github.mthli.Cracker.Crash.CrashItem;
import io.github.mthli.Cracker.Crash.CrashUnit;
import io.github.mthli.Cracker.Data.DataAction;
import io.github.mthli.Cracker.R;

public class Receiver extends BroadcastReceiver {
    private Context context;

    private String packageName;
    private long time;
    private Throwable throwable;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        if (intent.getAction().equals(Hook.INTENT_ACTION)) {
            packageName = intent.getStringExtra(Hook.INTENT_PACKAGE_NAME);
            time = intent.getLongExtra(Hook.INTENT_TIME, 0);
            throwable = (Throwable) intent.getSerializableExtra(Hook.INTENT_THROWABLE);

            DataAction action = new DataAction(context);
            action.open(true);
            CrashItem item = new CrashItem();
            item.setPackageName(packageName);
            item.setTime(time);
            item.setContent(getContent(throwable));
            action.add(item);
            action.close();

            SharedPreferences preferences = context.getSharedPreferences(
                    context.getString(R.string.sp_cracker),
                    Context.MODE_PRIVATE
            );
            if (preferences.getBoolean(context.getString(R.string.sp_notification), true)) {
                showNotification();
            }
        }
    }

    private Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private String getContent(Throwable throwable) {
        if (throwable == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        String string = throwable.toString() + "\n";
        builder.append(string);

        StackTraceElement[] elements = throwable.getStackTrace();
        if (elements != null) {
            for (StackTraceElement element : elements) {
                string = "    at " + element.toString() + "\n";
                builder.append(string);
            }
        }

        Throwable cause = throwable.getCause();
        if (cause != null) {
            string = "Caused by: " + getContent(cause);
            builder.append(string);
        }

        return builder.toString();
    }

    private void showNotification() {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setSmallIcon(R.drawable.ic_notification_bug);
        builder.setLargeIcon(getBitmap(CrashUnit.getAppIcon(context, packageName)));
        builder.setContentTitle(CrashUnit.getAppName(context, packageName));
        builder.setContentText(throwable.toString());
        builder.setAutoCancel(true);

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(context.getString(R.string.detain_intent_content), getContent(throwable));
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(DetailActivity.class);
        taskStackBuilder.addNextIntent(intent);
        builder.setContentIntent(taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT));

        manager.notify(CrashUnit.NOTIFICATION_ID, builder.build());
    }
}
