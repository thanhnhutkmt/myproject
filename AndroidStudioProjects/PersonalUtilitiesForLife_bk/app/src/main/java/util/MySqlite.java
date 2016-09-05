package util;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nhut on 6/26/2016.
 */
public class MySqlite {
    /*
     *
     * String condition = "bookid='?' and booktype='?'"
     * String []conditionValues = {"3", "abc"}
     */
    public static int edit(Activity a, String dbName, String condition, String []conditionValues,
                               String tableName, String []columns, String []values) {
        ContentValues row = new ContentValues();
        int length = columns.length;
        if (length != values.length) return 0;
        for (int i = 0; i < length; i++) row.put(columns[i], values[i]);
        SQLiteDatabase sdb = a.openOrCreateDatabase(dbName, Activity.MODE_PRIVATE, null);
        return sdb.update(tableName, row, condition, conditionValues);
    }

    public static List<String> read(Activity a, String dbName, String table, String[] columns,
                                    String selection, String[] selectionArgs,
                                    String groupBy, String having, String orderBy) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase sdb = a.openOrCreateDatabase(dbName, Activity.MODE_PRIVATE, null); // Noi luu dbfile la thumuc_app/databases/dbfile
        Cursor cursor = sdb.query(table, columns, selection,
                selectionArgs, groupBy, having, orderBy);
        int numberOfColumn = cursor.getColumnNames().length;
        list.add(0, Arrays.toString(cursor.getColumnNames()));
        while(cursor.moveToNext()) {
            StringBuilder sb = new StringBuilder();
            for (int index = 0; index < numberOfColumn; index++) {
                if (index == (numberOfColumn - 1)) sb.append(cursor.getString(index));
                else sb.append(cursor.getString(index) + " ");
            }
            list.add(sb.toString());
        }
        return list;
    }

    public static boolean add(Activity a, String dbName,
                                 String tableName, String []columns, String []values) {
        ContentValues row = new ContentValues();
        int length = columns.length;
        if (length != values.length) return false;
        for (int i = 0; i < length; i++) row.put(columns[i], values[i]);
        SQLiteDatabase sdb = a.openOrCreateDatabase(dbName, Activity.MODE_PRIVATE, null);
        long r = sdb.insert(tableName, null, row);
        if (r == -1) return false;
        else return true;
    }

    public static long delete(Activity a, String tableName, String selection, String[] selectionArgs) {
        SQLiteDatabase sdb = a.openOrCreateDatabase(tableName, Activity.MODE_PRIVATE, null);
        return sdb.delete(tableName, selection, selectionArgs);
    }
}