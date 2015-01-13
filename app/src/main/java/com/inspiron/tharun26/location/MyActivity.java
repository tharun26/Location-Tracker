package com.inspiron.tharun26.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MyActivity extends Activity {

    GoogleMap googleMap;
    TextView latitude;
    TextView longitude;
    public static LatLng end_tail=null;
    public static LatLng new_tail = null;

    ImageButton.OnClickListener listener1=null;
    ImageButton button;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initmap();


        googleMap.setMyLocationEnabled(true);

        LocationManager locationManager;
        String svcname=Context.LOCATION_SERVICE;
        locationManager=(LocationManager)getSystemService(svcname);






/*
        Criteria criteria=new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
*/
        String provider=LocationManager.NETWORK_PROVIDER;
        Location l=locationManager.getLastKnownLocation(provider);
        updatelocation(l);

         final LocationListener locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                updatelocation(location);
                try {
                    try {
                        updatedatabase(location);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(provider,3000,20,locationListener);
    }

    public void updatedatabase(Location location) throws SQLException, IOException {

        double lat=location.getLatitude();
        double lon=location.getLongitude();
        String lat1=""+lat;
        String lon1=""+lon;
        String add="No Address";
        Geocoder gc=new Geocoder(this, Locale.getDefault());

        List<Address> addresses=gc.getFromLocation(lat,lon,1);
        StringBuilder sb=new StringBuilder();
        if(addresses.size()>0)
        {
            Address address=addresses.get(0);
            for(int i=0;i<address.getMaxAddressLineIndex();i++)
            {
                sb.append(address.getAddressLine(i)).append("\n");
                sb.append(address.getLocality()).append("\n");
            }
            add=sb.toString();

        }



        GPSDatabase myDatabase=new GPSDatabase(getApplicationContext());
        myDatabase.open();
        myDatabase.insertRows(lat1, lon1,add);
        myDatabase.close();
    }
    public void sendMessage(View view) {

          Intent intent=new Intent(MyActivity.this,secondactivity.class);
        startActivity(intent);
        // Do something in response to button click

    }
    public void sendMessage1(View view) throws SQLException {
        GPSDatabase myDatabase=new GPSDatabase(getApplicationContext());
        myDatabase.open();
        myDatabase.deletedb();
        myDatabase.close();
    }


    private void updatelocation(Location location) {
        double lat = 0, lon = 0;

         if(end_tail!=null)
         {
             lat = location.getLatitude();
             lon = location.getLongitude();

            // insertItem();

             new_tail=new LatLng(lat,lon);
             PolylineOptions polylineOptions=new PolylineOptions().add(end_tail).add(new_tail).color(Color.RED).width(10).geodesic(true);
             googleMap.addPolyline(polylineOptions);




         }


        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            end_tail=new LatLng(lat,lon);

        }
        latitude.setText(" " + lat);
        longitude.setText(" " + lon);
      //  addDB(lat,lon);
    }

    private void initmap()
    {
        if (googleMap == null)
        {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        }
    }


}

