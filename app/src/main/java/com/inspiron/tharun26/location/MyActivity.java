package com.inspiron.tharun26.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MyActivity extends Activity {

    GoogleMap googleMap;
    TextView latitude;
    TextView longitude;
    public static LatLng end_tail=null;
    public static LatLng new_tail = null;
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

        locationManager.requestLocationUpdates(provider,1000,10,locationListener);
    }

    private void updatelocation(Location location) {
        double lat = 0, lon = 0;

         if(end_tail!=null)
         {
             lat = location.getLatitude();
             lon = location.getLongitude();

             new_tail=new LatLng(lat,lon);
             PolylineOptions polylineOptions=new PolylineOptions().add(end_tail).add(new_tail).color(Color.GREEN).width(5).geodesic(true);
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
    }
    private void initmap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        }
    }

}