package com.example.gen.destinymovers;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

public class Trucks extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener {
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private double currentLat, currentLong;
    private LocationRequest locationRequest;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private TextView mRequest;
    private SupportPlaceAutocompleteFragment autocompleteFragment, pickUpautocomplete;
    private String TAG = "TRUCK";
    private LatLng pickUpLatLng, dropLatLng;
    private String pick_up_address, drop_address;
    private LinearLayout mDrop_place_wrapper;
    private Double totalDistance;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_trucks, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        autocompleteFragment = (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        pickUpautocomplete = (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);
        mDrop_place_wrapper = view.findViewById(R.id.drop_layout);

        pickUpautocomplete.setHint("Pick Up Location");
        autocompleteFragment.setHint("Destination Point");

        pickUpautocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                pickUpLatLng = place.getLatLng();
                pick_up_address = place.getAddress().toString();
                if (place.getLatLng() != null) {
                    mDrop_place_wrapper.setVisibility(View.VISIBLE);
                }else {
                    mDrop_place_wrapper.setVisibility(View.INVISIBLE);
                }

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(pickUpLatLng).zoom(19f).tilt(70).build();

                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
            }

            @Override
            public void onError(Status status) {

            }
        });
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                drop_address = place.getAddress().toString();
                dropLatLng = place.getLatLng();


            }

            @Override
            public void onError(Status status) {

            }
        });

        mRequest = view.findViewById(R.id.ed_request);
        mRequest.setOnClickListener(this);

        buildGoogleClient();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    private void buildGoogleClient() {
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in event places and move the camera
        LatLng event = new LatLng(-1.006299, 37.011695);
        mMap.addMarker(new MarkerOptions().position(event).title("Car one"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(event));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);


    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getContext(), "connectionSuspended", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(), "connectionFailed", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            currentLat = location.getLatitude();
            currentLong = location.getLongitude();
            LatLng positionLatLng = new LatLng(currentLat, currentLong);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(positionLatLng).zoom(19f).tilt(70).build();

            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

        }

        Toast.makeText(getContext(), "Location changed:" + currentLat + "Longtitude:" + currentLong, Toast.LENGTH_LONG).show();

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.ed_request) {
            if(pick_up_address == null){
                showToast("Please select Pick Location");
                return;
            }
            if(drop_address ==   null){
                showToast("Please Enter Drop Destination");
                return;
            }
            if(pickUpLatLng != null  && dropLatLng !=null){
                totalDistance = CalculationByDistance(pickUpLatLng,dropLatLng);
                showToast("Total Distance"+totalDistance);

            }
            Intent intent = new Intent(getContext(), reguest_truck.class);
            intent.putExtra("PICK_ADDRESS",pick_up_address);
            intent.putExtra("DROP_ADDRESS",drop_address);
            intent.putExtra("PICK_LATLNG", pickUpLatLng);
            intent.putExtra("DROPLATLNG",dropLatLng);
            intent.putExtra("DISTANCE",totalDistance);


            startActivity(intent);
        }
    }

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();

    }
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }
}
