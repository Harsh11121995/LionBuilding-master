package com.dies.lionbuilding.activity;

import android.graphics.Color;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.dies.lionbuilding.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Locale;

public class ViewRootActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<LatLng> latLngList;
    LatLng pont;
    String coord1lat = "23.033863";
    String coord1lng = "72.585022";
    String coord2lat = "22.308155";
    String coord2lng = " 70.800705";
    String coord3lat = " 21.170240";
    String coord3lng = " 72.831062";
    String[] coordinatesStringList = {coord1lat, coord1lng, coord2lat, coord2lng,coord3lat,coord3lng};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_root);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        latLngList = new ArrayList<LatLng>();






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

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        for(int i=0; i<coordinatesStringList.length; i+=2){
            pont = new LatLng(Double.parseDouble(coordinatesStringList[i]), Double.parseDouble(coordinatesStringList[i+1]));
            latLngList.add(pont);
            MarkerOptions markerOptions=new MarkerOptions();
            mMap.addMarker(new MarkerOptions().position(pont).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));

//            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                @Override
//                public boolean onMarkerClick(Marker marker) {
//                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//                    ArrayList<String> addresses = geocoder.getFromLocation(marker.getPosition().latitude(), marker.getPosition().longitude(), 1); //1 num of possible location returned
//                    String address = addresses.get(0).getAddressLine(0); //0 to obtain first possible address
//                    String city = addresses.get(0).getLocality();
//                    String state = addresses.get(0).getAdminArea();
//                    String country = addresses.get(0).getCountryName();
//                    String postalCode = addresses.get(0).getPostalCode();
//                    //create your custom title
//                    String title = address +"-"+city+"-"+state;
//                    marker.setTitle(title);
//                    marker.showInfoWindow();
//                    return true;
//                }
//            });
        }


        PolylineOptions polylineOptions = new PolylineOptions();

// Create polyline options with existing LatLng ArrayList
        polylineOptions.addAll(latLngList);
        polylineOptions
                .width(5)
                .color(Color.RED);

// Adding multiple points in map using polyline and arraylist
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.addPolyline(polylineOptions);
    }
}
