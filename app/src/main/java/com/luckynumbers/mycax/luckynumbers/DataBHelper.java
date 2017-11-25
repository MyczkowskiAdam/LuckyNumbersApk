package com.luckynumbers.mycax.luckynumbers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "results_database";
    public static final String RESULTS_TABLE_NAME = "results_table";
    public static final String RESULTS_COLUMN_ID = "_id";
    public static final String RESULTS_COLUMN_NAME = "name";
    public static final String RESULTS_COLUMN_RESULT = "result";

    public DataBHelper(Context context) {
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
        values.put(DataBHelper.RESULTS_COLUMN_NAME, name);
        values.put(DataBHelper.RESULTS_COLUMN_RESULT, result);
        database.insert(DataBHelper.RESULTS_TABLE_NAME, null, values);
        database.close();
    }

    public List<DataModel> readDB(){
        List<DataModel> data=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+RESULTS_TABLE_NAME+" ;",null);
        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext()) {
            dataModel= new DataModel();
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
        String clearDBQuery = "DELETE FROM "+RESULTS_TABLE_NAME;
        sqLiteDatabase.execSQL(clearDBQuery);
        sqLiteDatabase.close();
    }

    public void deleteEntry(long row) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(RESULTS_TABLE_NAME, RESULTS_COLUMN_ID + "=" + row, null);
        sqLiteDatabase.close();
    }
}