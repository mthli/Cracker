package io.github.mthli.Cracker.Crash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CrashAction {
    private SQLiteDatabase database;
    private CrashHelper helper;

    public CrashAction(Context context) {
        this.helper = new CrashHelper(context);
    }

    public void open(boolean rw) {
        if (rw) {
            database = helper.getWritableDatabase();
        } else {
            database = helper.getReadableDatabase();
        }
    }

    public void close() {
        helper.close();
    }

    public void add(CrashItem item) {
        ContentValues values = new ContentValues();

        values.put(CrashUnit.PACKAGE_NAME, item.getPackageName());
        values.put(CrashUnit.TIME, item.getTime());
        values.put(CrashUnit.CONTENT, item.getContent());

        database.insert(CrashUnit.TABLE, null, values);
    }

    public void clear() {
        database.execSQL("DELETE FROM " + CrashUnit.TABLE);
    }

    private CrashItem get(Cursor cursor) {
        CrashItem item = new CrashItem();

        item.setPackageName(cursor.getString(0));
        item.setTime(cursor.getLong(1));
        item.setContent(cursor.getString(2));

        return item;
    }

    public List<CrashItem> list() {
        List<CrashItem> list = new ArrayList<CrashItem>();

        Cursor cursor = database.query(
                CrashUnit.TABLE,
                new String[] {
                        CrashUnit.PACKAGE_NAME,
                        CrashUnit.TIME,
                        CrashUnit.CONTENT
                },
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            return list;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(get(cursor));
            cursor.moveToNext();
        }

        cursor.close();

        return list;
    }
}
