package io.github.mthli.Cracker.Xposed;

import android.app.Application;
import android.content.Intent;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook implements IXposedHookLoadPackage {
    public static final String INTENT_ACTION = "io.github.mthli.Cracker.RECEIVER";
    public static final String INTENT_PACKAGE_NAME = "PACKAGE_NAME";
    public static final String INTENT_TIME = "TIME";
    public static final String INTENT_THROWABLE = "THROWABLE";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam param) throws Throwable {
        XposedHelpers.findAndHookMethod(Application.class, "onCreate", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                hookUncaughtException((Application) param.thisObject);
            }
        });
    }

    private void hookUncaughtException(final Application application) {
        Class<?> c = Thread.getDefaultUncaughtExceptionHandler().getClass();
        XposedHelpers.findAndHookMethod(c, "uncaughtException", Thread.class, Throwable.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                Intent intent = new Intent(INTENT_ACTION);
                intent.putExtra(INTENT_PACKAGE_NAME, application.getPackageName());
                intent.putExtra(INTENT_TIME, System.currentTimeMillis());
                intent.putExtra(INTENT_THROWABLE, (Throwable) param.args[1]);
                application.sendBroadcast(intent);
            }
        });
    }
}
