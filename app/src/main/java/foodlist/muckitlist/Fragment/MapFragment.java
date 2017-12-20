/*
 * Copyright (C) 2015 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package foodlist.muckitlist.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import foodlist.muckitlist.R;


/**
 * Created by yujeong on 27/11/17.
 */
public class MapFragment extends android.app.Fragment implements OnMapReadyCallback {

    private static final String TAG = " googlmap";
    private MapView mapView = null;
    LocationManager locationManager;
    Double latitude = 37.56;
    Double longitude = 126.97;
    public final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;
    public final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1;

    private TextView mPlaceDetailsText;
    private TextView mPlaceAttribution;


    public MapFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View layout = inflater.inflate(R.layout.content_nav, container, false);

        mapView = (MapView) layout.findViewById(R.id.map);
        mapView.getMapAsync(this);


        startLocationService();

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreate");
        super.onActivityCreated(savedInstanceState);

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionResult");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationService();
                }
            }
        }
    }

    public void startLocationService() {
        Log.d(TAG, "startLocationService");
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        long minTime = 10000;
        float minDistance = 1;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Request Permission Rational")
                        .setMessage("앱 실행을 위해서는 위치 권한을 설정해야합니다.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
                            }
                        }).show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_ACCESS_FINE_LOCATION);
            }


            Toast.makeText(getActivity(), "Don't have permissions.", Toast.LENGTH_LONG).show();
            return;
        } else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, mLocationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, mLocationListener);
            Toast.makeText(getActivity(), "locationManager", Toast.LENGTH_LONG).show();
        }
    }

    private void stopLocationService() {
        Log.d(TAG, "stopLocationService");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Don't have permissions.", Toast.LENGTH_LONG).show();
            return;
        }
        locationManager.removeUpdates(mLocationListener);
        Toast.makeText(getActivity(), "LocationListener: " + latitude + ", " + longitude, Toast.LENGTH_LONG).show();

        mapView.getMapAsync(this);
    }


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onLocationChanged");
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            Bundle bundle = getArguments();
            if (bundle != null) {

                Double lat = bundle.getDouble("lat");
                //getArguments().getDouble("lat");
                Double lng = bundle.getDouble("lng");
                LatLng title = new LatLng(lat, lng);
                Toast.makeText(getActivity(), title.latitude + ", " + title.longitude, Toast.LENGTH_SHORT).show();
            }

            stopLocationService();

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {


        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        Log.d(TAG, "onStart");

    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        Log.d(TAG, "onResume");


        Bundle bundle = getArguments();
        if (bundle != null) {
            latitude = bundle.getDouble("lat");
            //getArguments().getDouble("lat");
            longitude = bundle.getDouble("lng");
            Log.d(TAG, latitude + "" + longitude);
            Toast.makeText(this.getActivity(), "bundle", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
        Log.d(TAG, "onDestroy");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady");
        LatLng SEOUL = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("현재위치");
        markerOptions.snippet("seoul");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }


}
