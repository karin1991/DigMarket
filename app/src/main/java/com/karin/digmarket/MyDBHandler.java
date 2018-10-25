package com.karin.digmarket;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

public class MyDBHandler extends SQLiteOpenHelper {

    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "itemDB.db";
    public static final String TABLE_NAME = "Item1";
    public static final String COLUMN_DESCRIPTION = "ItemDescription";
    public static final String COLUMN_AMOUNT = "ItemAmount";
    public static final String  COLUMN_PRICE = "ItemPrice";
    public static final String COLUMN_CODE = "ItemCode";


    //initialize the database
    public MyDBHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        this.dropTable(this.getWritableDatabase());
        this.onCreate(this.getWritableDatabase());

    }
    @Override

    public void onCreate(SQLiteDatabase db) {


        String CREATE_ITRM_TABLE = "CREATE TABLE if not exists " + TABLE_NAME + "(" + COLUMN_CODE +
                " PRIMARYKEY ," + COLUMN_DESCRIPTION + " TEXT," + COLUMN_PRICE + " TEXT" + ")";
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

    public void addHandler(Item item)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CODE, item.getCode());
        values.put(COLUMN_DESCRIPTION, item.getDescription());
        values.put(COLUMN_PRICE, item.getPrice());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public Item findHandler(String itemCode)
    {

        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_CODE + " = " + "'" + itemCode + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Item item = new Item();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            item.setCode(cursor.getString(0));
            //Integer.parseInt(cursor.getString(0))
            item.setDescription(cursor.getString(1));
            item.setPrice(cursor.getString(2));
            cursor.close();
        } else {
            item = null;
        }
        db.close();
        return item;
    }


    public boolean deleteHandler(String itemCode)
    {
        boolean result = false;
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_CODE + "= '" + String.valueOf(itemCode) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Item item = new Item();
        if (cursor.moveToFirst()) {
            item.setCode(cursor.getString(0));
            db.delete(TABLE_NAME, COLUMN_CODE + "=?",
                    new String[] {
                String.valueOf(item.getCode())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean updateHandler(String code, String description, String price)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_CODE, code);
        args.put(COLUMN_DESCRIPTION, description);
        args.put(COLUMN_PRICE, price);
        return db.update(TABLE_NAME, args, COLUMN_CODE + "=" + code, null) > 0;
    }
}
