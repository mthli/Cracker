package io.github.mthli.Cracker.Crash;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CrashHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CRACKER.db";
    private static final int DATABASE_VERSION = 1;

    public CrashHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CrashUnit.CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {}
}
