package com.luckynumbers.mycax.luckynumbers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class DataBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "results_database";
    private static final String RESULTS_TABLE_NAME = "results_table";
    private static final String RESULTS_COLUMN_ID = "_id";
    private static final String RESULTS_COLUMN_NAME = "name";
    private static final String RESULTS_COLUMN_RESULT = "result";
    private final Context context;

    public DataBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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
        values.put(DataBHelper.RESULTS_COLUMN_NAME, name);
        values.put(DataBHelper.RESULTS_COLUMN_RESULT, result);
        if (PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean("app_allow_dbdups", false))
            database.insert(DataBHelper.RESULTS_TABLE_NAME, null, values);
        else {
            if (!IsInDB(name)) database.insert(DataBHelper.RESULTS_TABLE_NAME, null, values);
            else Toast.makeText(context, "Duplicate name!", Toast.LENGTH_SHORT).show();
        }
        database.close();
    }

    private boolean IsInDB(String fieldValue) {
        SQLiteDatabase database = getWritableDatabase();
        String Query = "Select * from " + RESULTS_TABLE_NAME + " where " + RESULTS_COLUMN_NAME + " = '" + fieldValue + "'";
        Cursor cursor = database.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public List<DataModel> readDB() {
        List<DataModel> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from "+RESULTS_TABLE_NAME+" ORDER BY " + RESULTS_COLUMN_ID + " DESC",null );
        @SuppressWarnings("MismatchedQueryAndUpdateOfStringBuilder") StringBuilder stringBuffer = new StringBuilder();
        DataModel dataModel;
        while (cursor.moveToNext()) {
            dataModel = new DataModel();
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String result = cursor.getString(cursor.getColumnIndexOrThrow("result"));
            String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
            dataModel.setName(name);
            dataModel.setResult(result);
            dataModel.setId(id);
            stringBuffer.append(dataModel);
            data.add(dataModel);
        }
        db.close();
        return data;
    }

    public void clearDatabase() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String clearDBQuery = "DELETE FROM " + RESULTS_TABLE_NAME;
        sqLiteDatabase.execSQL(clearDBQuery);
        sqLiteDatabase.close();
    }

    public void deleteEntry(long row) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(RESULTS_TABLE_NAME, RESULTS_COLUMN_ID + "=" + row, null);
        sqLiteDatabase.close();
    }
}