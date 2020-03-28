package com.dies.lionbuilding.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Product.db" ;
    public static final String TABLE_NAME = "product_table";
    public static final String BARCODE_DATA_TABLE = "barcode_data_table";
    public static final String COL_1 = "id";
    public static final String COL_2 = "product_id";
    public static final String COL_3 = "product_catagory_id";
    public static final String COL_4 = "product_name";
    public static final String COL_5 = "product_price";
    public static final String COL_6 = "product_descpriction";
    public static final String COL_7 = "product_img";
    public static final String COL_8 = "product_qty";
    public static final String COL_9 = "user_id";
    public static final String COL_10 = "product_point";
    public static final String COL_11 = "session_id";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME +" (id INTEGER PRIMARY KEY AUTOINCREMENT,product_id TEXT,product_catagory_id TEXT,product_name TEXT,product_price TEXT,product_descpriction TEXT,product_img TEXT,product_qty TEXT,user_id TEXT,product_point TEXT,session_id TEXT)");
        sqLiteDatabase.execSQL("create table " + BARCODE_DATA_TABLE +" (id INTEGER PRIMARY KEY AUTOINCREMENT,product_id TEXT,product_catagory_id TEXT,product_name TEXT,product_price TEXT,product_descpriction TEXT,product_img TEXT,product_qty TEXT,user_id TEXT,product_point TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+BARCODE_DATA_TABLE);
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String product_id,String product_catagory_id,String product_name,String product_price,String product_descpriction,String product_img,String product_qty,String user_id,String product_point,String session_id) {

        Cursor res=checkData(product_id);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,product_id);
        contentValues.put(COL_3,product_catagory_id);
        contentValues.put(COL_4,product_name);
        contentValues.put(COL_5,product_price);
        contentValues.put(COL_6,product_descpriction);
        contentValues.put(COL_7,product_img);
        contentValues.put(COL_8,product_qty);
        contentValues.put(COL_9,user_id);
        contentValues.put(COL_10,product_point);
        contentValues.put(COL_11,session_id);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertBarcodeData(String product_id,String product_catagory_id,String product_name,String product_price,String product_descpriction,String product_img,String product_qty,String user_id,String product_point) {

       // Cursor res=checkBarcodeData(product_id);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValuesbarcode = new ContentValues();
        contentValuesbarcode.put(COL_2,product_id);
        contentValuesbarcode.put(COL_3,product_catagory_id);
        contentValuesbarcode.put(COL_4,product_name);
        contentValuesbarcode.put(COL_5,product_price);
        contentValuesbarcode.put(COL_6,product_descpriction);
        contentValuesbarcode.put(COL_7,product_img);
        contentValuesbarcode.put(COL_8,product_qty);
        contentValuesbarcode.put(COL_9,user_id);
        contentValuesbarcode.put(COL_10,product_point);
        long result = db.insert(BARCODE_DATA_TABLE,null ,contentValuesbarcode);
        if(result == -1)
            return false;
        else
            return true;
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Cursor getIdWiseData(String session_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME+" where session_id =?", new String[]{session_id});
        return res;
    }

    public Cursor getAllBarcodeData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+BARCODE_DATA_TABLE,null);
        return res;
    }


    public Cursor checkData(String product_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * " +
                        "FROM product_table " +
                        "WHERE product_id = ?",
                new String[] {product_id});
        return result;
    }

    public Cursor checkBarcodeData(String product_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * " +
                        "FROM product_table " +
                        "WHERE product_id = ?",
                new String[] {product_id});
        return result;
    }


    //SELECT * FROM employees WHERE id = 1;



    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ?",new String[] {id});
    }

    public Integer deleteBarcodeData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(BARCODE_DATA_TABLE, "id = ?",new String[] {id});
    }


    public Integer removeAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, null, null);
    }

    public Integer removeAllBarcode()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(BARCODE_DATA_TABLE, null, null);
    }


}
