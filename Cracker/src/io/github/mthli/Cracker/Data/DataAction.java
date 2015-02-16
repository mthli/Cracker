package io.github.mthli.Cracker.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import io.github.mthli.Cracker.Crash.CrashItem;

import java.util.ArrayList;
import java.util.List;

public class DataAction {
    private SQLiteDatabase database;
    private DataHelper dataHelper;

    public DataAction(Context context) {
        this.dataHelper = new DataHelper(context);
    }

    public void open(boolean rw) {
        if (rw) {
            database = dataHelper.getWritableDatabase();
        } else {
            database = dataHelper.getReadableDatabase();
        }
    }

    public void close() {
        dataHelper.close();
    }

    public void add(CrashItem item) {
        ContentValues values = new ContentValues();

        values.put(DataUnit.PACKAGE_NAME, item.getPackageName());
        values.put(DataUnit.TIME, item.getTime());
        values.put(DataUnit.CONTENT, item.getContent());

        database.insert(DataUnit.TABLE, null, values);
    }

    public void delete(CrashItem item) {
        database.execSQL("DELETE FROM "+ DataUnit.TABLE + " WHERE " + DataUnit.TIME + " = " + item.getTime());
    }

    public void clear() {
        database.execSQL("DELETE FROM " + DataUnit.TABLE);
    }

    private CrashItem get(Cursor cursor) {
        CrashItem crashItem = new CrashItem();

        crashItem.setPackageName(cursor.getString(0));
        crashItem.setTime(cursor.getLong(1));
        crashItem.setContent(cursor.getString(2));

        return crashItem;
    }

    public List<CrashItem> list() {
        List<CrashItem> list = new ArrayList<CrashItem>();

        Cursor cursor = database.query(
                DataUnit.TABLE,
                new String[] {
                        DataUnit.PACKAGE_NAME,
                        DataUnit.TIME,
                        DataUnit.CONTENT
                },
                null,
                null,
                null,
                null,
                DataUnit.TIME + " desc"
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
