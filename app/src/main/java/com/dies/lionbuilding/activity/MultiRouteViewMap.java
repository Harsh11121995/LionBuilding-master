package com.dies.lionbuilding.activity;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.dies.lionbuilding.R;
import com.dies.lionbuilding.application.Utility;
import com.dies.lionbuilding.model.RouteModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiRouteViewMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<LatLng> points;
    List<RouteModel.Data> arrayList;
    String[] coordinatesStringList;
    ArrayList<String> arrayListlatlong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_route_view_map);
        points = new ArrayList<LatLng>();
        arrayList= Utility.getAppcon().getSession().arrayListRoute;
        arrayListlatlong=new ArrayList<>();
        for (int i=0;i<arrayList.get(0).getRoute_data().size();i++){
            arrayListlatlong.add(arrayList.get(0).getRoute_data().get(i).getSl_latitude());
            arrayListlatlong.add(arrayList.get(0).getRoute_data().get(i).getSl_longitude());
        }



        coordinatesStringList = arrayListlatlong.toArray(new String[arrayListlatlong.size()]);
        Log.e("latlong_array", Arrays.toString(coordinatesStringList));

       // points.add("15.235235");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
//                .clickable(true)
//                .add(
//                        new LatLng(-35.016, 143.321),
//                        new LatLng(-34.747, 145.592),
//                        new LatLng(-34.364, 147.891),
//                        new LatLng(-33.501, 150.217),
//                        new LatLng(-32.306, 149.248),
//                        new LatLng(-32.491, 147.309)));

//        String coord1lat = "15.235235";
//        String coord1lng = "31.776776";
//        String coord2lat = "34.334346";
//        String coord2lng = "31.776776";
//        String[] coordinatesStringList = {coord1lat, coord1lng, coord2lat, coord2lng};

        List<LatLng> latLngList = new ArrayList<LatLng>();
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(3);
        for(int i=0; i<coordinatesStringList.length; i+=2){
            LatLng point = new LatLng(Double.parseDouble(coordinatesStringList[i]), Double.parseDouble(coordinatesStringList[i+1]));
            latLngList.add(point);
            mMap.addMarker(new MarkerOptions().position(point));
            //mMap.animateCamera( CameraUpdateFactory.zoomTo( 11 ) );
           // points.add(point);
            polylineOptions.add(point);
        }
        googleMap.addPolyline(polylineOptions);


        // Add a marker in Sydney and move the camera
     //   LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
