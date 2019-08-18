package com.dev.lungyu.cvgip_2017_pass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lungyu on 8/19/17.
 */

public class DBHelper  extends SQLiteOpenHelper {

    // 資料庫名稱
    public static final String DATABASE_NAME = "_data.db";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 1;
    // 資料庫物件，固定的欄位變數
    private static SQLiteDatabase database;


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(LodgingDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 刪除原有的表格
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LodgingDAO.TABLE_NAME);
        // 呼叫onCreate建立新版的表格
        onCreate(sqLiteDatabase);
    }

    public static void renew(SQLiteDatabase sqLiteDatabase){
        // 刪除原有的表格
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LodgingDAO.TABLE_NAME);
        // 呼叫onCreate建立新版的表格
        sqLiteDatabase.execSQL(LodgingDAO.CREATE_TABLE);
    }


    // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new DBHelper(context, DATABASE_NAME, null, VERSION).getWritableDatabase();
        }

        return database;
    }
}
