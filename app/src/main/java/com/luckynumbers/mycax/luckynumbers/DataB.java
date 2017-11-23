package com.luckynumbers.mycax.luckynumbers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DataB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "results_database";
    public static final String RESULTS_TABLE_NAME = "results_table";
    public static final String RESULTS_COLUMN_ID = "_id";
    public static final String RESULTS_COLUMN_NAME = "name";
    public static final String RESULTS_COLUMN_RESULT = "result";

    public DataB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + RESULTS_TABLE_NAME + " (" +
                RESULTS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RESULTS_COLUMN_NAME + " TEXT, " +
                RESULTS_COLUMN_RESULT + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RESULTS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void saveToDB(String fname, String lname, String result) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        String name = fname + " " + lname;
        values.put(DataB.RESULTS_COLUMN_NAME, name);
        values.put(DataB.RESULTS_COLUMN_RESULT, result);
        database.insert(DataB.RESULTS_TABLE_NAME, null, values);
        database.close();
    }

    public void ReadDB(Context context, ListView listView) {
        SQLiteDatabase database = getReadableDatabase();

        String[] from = new String[]{DataB.RESULTS_COLUMN_NAME, DataB.RESULTS_COLUMN_RESULT};
        int[] to = new int[]{R.id.list_name, R.id.list_result};

        String[] projection = {
                DataB.RESULTS_COLUMN_ID,
                DataB.RESULTS_COLUMN_NAME,
                DataB.RESULTS_COLUMN_RESULT
        };

        Cursor cursor = database.query(DataB.RESULTS_TABLE_NAME, projection, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context, R.layout.row, cursor, from, to, 0);

        listView.setAdapter(adapter);
        database.close();

    }

    public void clearDatabase() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+RESULTS_TABLE_NAME;
        sqLiteDatabase.execSQL(clearDBQuery);
    }

    public void deleteEntry(long row) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(RESULTS_TABLE_NAME, RESULTS_COLUMN_ID + "=" + row, null);
    }
}