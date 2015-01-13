package com.inspiron.tharun26.location;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by tharun26 on 13/1/15.
 */
public class GPSDatabase {
    private Context context;
    private DbHelper dbHelper;
    public final String DBNAME = "gps2";
    public final int DBVERSION = 4;
    public SQLiteDatabase db;
    public final String COLUMN2 = "latitude";
    public final String COLUMN3 = "longitude";
    public final String COLUMN1 = "locationId";
    public final String COLUMN4= "address";
    public final String TABLENAME = "location";
    public final String CREATERDB = "create table location(locationId integer primary key autoincrement,latitude text not null, longitude text not null,address text not null);";
    public final String DELETE="delete from location";
    public GPSDatabase(Context context) {

        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context){
            super(context,DBNAME,null,DBVERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(CREATERDB);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
        }
    }
    public long insertRows(String column2, String column3,String column4){
        ContentValues value=new ContentValues();
        value.put(COLUMN2, column2);
        value.put(COLUMN3, column3);
        value.put(COLUMN4,column4);
        return db.insert(TABLENAME,null,value);
    }
    public void deletedb()
    {
        db.execSQL(DELETE);
    }

    public Cursor getAllRows(){
        Cursor cursor=db.query(TABLENAME, new String[]{COLUMN1,COLUMN2,COLUMN3,COLUMN4}, null,null, null, null, null);
        return cursor;
    }
    public void open() throws SQLException {
        db=dbHelper.getWritableDatabase();
        //return true;
    }
    public void close(){
        dbHelper.close();
        //return true;
    }
}
