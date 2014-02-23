package com.example.helloworld;


import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.*;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.*;

import com.google.android.gms.maps.model.*;



public class MainActivity extends Activity {

	private GoogleMap map ;
	private  double home_lat;
	private  double home_long;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        
        setUp();
       
    }
    
    private void setUp(){
    	
    	int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
    	
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available

             int requestCode = 10;
             Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
             dialog.show();
    	
        }else { 
    		
    		centerMapOnMyLocation();
    		setDummyMarkerAndPlotDistance();
    		    		
    	}
    	
    	    	
    }
         
    
    
    
    private void centerMapOnMyLocation() {

        //map.setMyLocationEnabled(true);
        
        LocationManager locationmanager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        
        String provider = locationmanager.getBestProvider(criteria, true);
        
        Location mylocation = locationmanager.getLastKnownLocation(provider);
        
        
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        
        double latitude = mylocation.getLatitude();
        double longitude = mylocation.getLongitude();
        
        LatLng latlng = new LatLng(latitude, longitude);
        
        map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        //map.animateCamera(CameraUpdateFactory.zoomTo(20));
        map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here"));
        
        home_lat = latitude;
        home_long = longitude; 
        
               
              
        
    }
    
    private void setDummyMarkerAndPlotDistance(){
    	
    	 map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();
    	
    	    	    	
    	 double latitude1 = 44.568499;
    	 double latitude2 = 44.563728;
    	 double latitude3 = 44.564951;
    	 double latitude4 = 44.567802;
    	 
    	 double longitude1 = -123.279009;
    	 double longitude2 = -123.279782;
    	 double longitude3 = -123.276810;
         double longitude4 = -123.278001;
    	
    	 
    	 map.animateCamera(CameraUpdateFactory.zoomTo(15));
    	 
    	 double dist1 = latLongToDistance(latitude1, longitude1);
    	 double dist2 = latLongToDistance(latitude2, longitude2);
    	 double dist3 = latLongToDistance(latitude3, longitude3);
    	 double dist4 = latLongToDistance(latitude4, longitude4);
    	 
    	 String distance1 = Integer.toString((int)dist1);
    	 String distance2 = Integer.toString((int)dist2);
    	 String distance3 = Integer.toString((int)dist3);
    	 String distance4 = Integer.toString((int)dist4);
    	 
    	 MarkerOptions marker1 = (new MarkerOptions().position(new LatLng(latitude1, longitude1)).title("Parking Meter 1").snippet("Distance: "+ distance1 + "m"));
    	 MarkerOptions marker2 = (new MarkerOptions().position(new LatLng(latitude2, longitude2)).title("Parking Meter 2").snippet("Distance: "+distance2 + "m"));
    	 MarkerOptions marker3 = (new MarkerOptions().position(new LatLng(latitude3, longitude3)).title("Parking Meter 3").snippet("Distance: " + distance3 + "m"));
    	 MarkerOptions marker4 = (new MarkerOptions().position(new LatLng(latitude4, longitude4)).title("Parking Meter 4").snippet("Distance: " + distance4 + "m"));
    	 
    	 map.addPolyline(new PolylineOptions().geodesic(true)
    			 		.add(new LatLng(home_lat, home_long))
    			 		.add(new LatLng(latitude1, longitude1)));
    	 map.addPolyline(new PolylineOptions().geodesic(true)
    			 	    .add(new LatLng(home_lat, home_long))
    			 		.add(new LatLng(latitude2, longitude2)));
    	 map.addPolyline(new PolylineOptions().geodesic(true)
    			 		.add(new LatLng(home_lat, home_long))
    			 		.add(new LatLng(latitude3, longitude3)));
    	 map.addPolyline(new PolylineOptions().geodesic(true)
    			 		 .add(new LatLng(home_lat, home_long))
    			 		 .add(new LatLng(latitude4, longitude4))); 
    			 		
    	 
    	 if (dist1 < dist2 && dist1 < dist3 && dist1 < dist4){ 		 
    		 String text1 = "Meter 1";
    		
    		 
    		 marker1 = marker1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
    		 
    	 } else if (dist2 < dist1 && dist2 < dist3 && dist2 < dist4){ 
    		 
       		 
    		 String text1 = "Meter 2";
    		
    		 
    		 marker2 = marker2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
    		 
    		 
    	 } else if (dist3 < dist1 && dist3 < dist2 && dist3 < dist4){
    		 
    		 String text1 = "Meter 3";
    		
    		 
    		
    		 marker3 = marker3.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
    		 
    	 } else{ 
    		
    		 String text1 = "Meter 4";
		 	
    		 marker4 = marker4.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
    		  
    	 }
    	 dialogReturn();   	
    	 map.addMarker(marker1);
    	 map.addMarker(marker2);
    	 map.addMarker(marker3);
    	 map.addMarker(marker4);
    }
    
    private void dialogReturn(){
    	
    	String message = String.format("Your shortest distance is  marked in blue");
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
        .setTitle(message)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setPositiveButton("Cancel", new DialogInterface.OnClickListener() 
        {
            public void onClick(DialogInterface dialog, int which) 
            {       
                   dialog.dismiss();
        }
        });             
        
        AlertDialog alert = builder.create();
        alert.show();
    	 	
    }
       
    private double latLongToDistance(double endLatitude, double endLongitude){
    	
    	double pk = (float) (180/3.14169);

	    double a1 = home_lat / pk;
	    double a2 = home_long/ pk;
	    double b1 = endLatitude / pk;
	    double b2 = endLongitude / pk;

	    double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
	    double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
	    double t3 = Math.sin(a1)*Math.sin(b1);
	    double tt = Math.acos(t1 + t2 + t3);

	    return 6366000*tt;

    	
    }
    
    	
 }
    
    
    
    
    
    
    
    
    
    
    
    