package com.karin.digmarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SupermarketDBHandler extends SQLiteOpenHelper {

    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "supermarketDB.db";
    public static final String TABLE_NAME = "supermarket";
    public static final String COLUMN_NAME = "SupermarketName";
    public static final String  COLUMN_ADDRESS = "SupermarketAddress";
    public static final String COLUMN_ID = "SupermarketID";


    //initialize the database
    public SupermarketDBHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        this.dropTable(this.getWritableDatabase());
        this.onCreate(this.getWritableDatabase());

    }
    @Override

    public void onCreate(SQLiteDatabase db) {


        String CREATE_ITRM_TABLE = "CREATE TABLE if not exists " + TABLE_NAME + "(" + COLUMN_ID +
                " PRIMARYKEY ," + COLUMN_NAME + " TEXT," + COLUMN_ADDRESS + " TEXT" + ")";
        Log.d("Query", CREATE_ITRM_TABLE);
        db.execSQL(CREATE_ITRM_TABLE);
    }

    public void dropTable(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}
    public String loadHandler()
    {
        String result = "";
        String query = "Select * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public void addHandler(String id, String name, String address)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_ADDRESS, address);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public String[] findHandler(String supermarketID)
    {
        String[] returnData = new String[3];

        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + "'" + supermarketID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            returnData[0] = (cursor.getString(0));

            returnData[1] = (cursor.getString(1));
            returnData[2] = (cursor.getString(2));
            cursor.close();
        } else {
           returnData[0] = null;
            returnData[1] = null;
            returnData[2] = null;

        }
        db.close();
        return returnData;
    }


//    public boolean deleteHandler(String itemCode)
//    {
//        boolean result = false;
//        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_CODE + "= '" + String.valueOf(itemCode) + "'";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        Item item = new Item();
//        if (cursor.moveToFirst()) {
//            item.setCode(cursor.getString(0));
//            db.delete(TABLE_NAME, COLUMN_CODE + "=?",
//                    new String[] {
//                            String.valueOf(item.getCode())
//                    });
//            cursor.close();
//            result = true;
//        }
//        db.close();
//        return result;
//    }
    public boolean updateHandler(String id, String name, String address)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID, id);
        args.put(COLUMN_NAME, name);
        args.put(COLUMN_ADDRESS, address);
        return db.update(TABLE_NAME, args, COLUMN_ID + "=" + id, null) > 0;
    }
}

