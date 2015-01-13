package com.inspiron.tharun26.location;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;


public class secondactivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondactivity);
       Log.i("hi","hi" );
       Context context = getApplicationContext();
        GPSDatabase myDatabase=new GPSDatabase(this);
        try {
            myDatabase.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor=myDatabase.getAllRows();
        cursor.moveToFirst();
        ArrayList listContents;
        listContents = new ArrayList();

        for (int i = 0;i<cursor.getCount(); i++) {
            listContents.add("Latitude=" +cursor.getString(1) +"  "+"\nLogitude="+ cursor.getString(2));
            cursor.moveToNext();
        }

        myDatabase.close();

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, listContents);
        ListView list;
        list=(ListView)findViewById(R.id.ListView01);
        list.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.secondactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
