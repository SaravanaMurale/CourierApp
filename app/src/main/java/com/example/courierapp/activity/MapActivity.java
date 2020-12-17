package com.example.courierapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.courierapp.R;
import com.example.courierapp.utils.GpsUtils;
import com.example.courierapp.utils.LoaderUtil;
import com.example.courierapp.utils.PreferenceUtil;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.courierapp.utils.AppConstant.GPS_PROVIDER_CODE;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mGoogleMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private View mapView;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;

    private Location mLastKnownLocation;
    private LocationCallback locationCallback;
    private MaterialSearchBar materialSearchBar;
    private final float DEFAULT_ZOOM = 13;

    private LocationManager locationManager;
    Double myLocationLat, myLocationLon;

    public boolean isGPS;

    String addrPoint;

    ImageView tickIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        materialSearchBar = findViewById(R.id.searchBar);
        tickIcon=findViewById(R.id.tickIcon);

        Intent intent = getIntent();
        addrPoint = intent.getStringExtra("ADDRESSPOINT");

        System.out.println("ADDRESS_POINT" + addrPoint);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapActivity.this);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        enableGPS();

        Places.initialize(MapActivity.this, "AIzaSyCnG_pJ7ZVHK3CyT1Y8OG_ortNhgvJbxBQ");
        placesClient = Places.createClient(this);
        final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        tickIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // launchDrawbleActivity();

                onBackPressed();

            }
        });


        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                startSearch(text.toString(), true, null, true);
            }

            @Override
            public void onButtonClicked(int buttonCode) {



                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
                    //backbutton
                    Toast.makeText(MapActivity.this,"I am Navigation  ", Toast.LENGTH_LONG).show();

                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    materialSearchBar.disableSearch();
                    materialSearchBar.clearSuggestions();

                    tickIcon.setVisibility(View.GONE);
                    Toast.makeText(MapActivity.this,"I am BackButton  ", Toast.LENGTH_LONG).show();
                }

            }
        });

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                        .setCountry("MY")
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .setSessionToken(token)
                        .setQuery(s.toString())
                        .build();

                placesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {

                        if (task.isSuccessful()) {

                            FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                            if (predictionsResponse != null) {
                                predictionList = predictionsResponse.getAutocompletePredictions();
                                List<String> suggestionList = new ArrayList<>();

                                for (int i = 0; i < predictionList.size(); i++) {

                                    AutocompletePrediction prediction = predictionList.get(i);
                                    suggestionList.add(prediction.getFullText(null).toString());

                                }

                                materialSearchBar.updateLastSuggestions(suggestionList);
                                if (!materialSearchBar.isSuggestionsVisible()) {
                                    materialSearchBar.showSuggestionsList();

                                }
                            }

                        } else {

                        }

                    }
                });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {
                if (position >= predictionList.size()) {
                    return;
                }

                AutocompletePrediction selectedPrediction = predictionList.get(position);
                String suggestion = materialSearchBar.getLastSuggestions().get(position).toString();
                materialSearchBar.setText(suggestion);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialSearchBar.clearSuggestions();
                    }
                }, 1000);


                InputMethodManager imm = (InputMethodManager) (getSystemService(INPUT_METHOD_SERVICE));
                if (imm != null) {
                    imm.hideSoftInputFromWindow(materialSearchBar.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                    String placeId = selectedPrediction.getPlaceId();
                    List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);

                    FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
                    placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                        @Override
                        public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {

                            Place place = fetchPlaceResponse.getPlace();
                            System.out.println("PlaceFound" + place.getName());

                            LatLng latLngOfPlace = place.getLatLng();
                            if (latLngOfPlace != null) {
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, DEFAULT_ZOOM));
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            ApiException apiException = (ApiException) e;
                            apiException.printStackTrace();
                            int statusCode = apiException.getStatusCode();
                            System.out.println("PlaceNotFound" + e.getMessage());
                            System.out.println("StatusCode" + statusCode);


                        }
                    });
                }


            }

            @Override
            public void OnItemDeleteListener(int position, View v) {

            }
        });


    }



    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        //mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        //mGoogleMap.setPadding(0,0,0,100);

        //Assigning Position of location button at right bottom
        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {

            //Toolbar has the ID 4. ID 2 is for location icon.
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 0, 50);

        }
        //Assigning Position of location button at right bottom

        mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                Double nextlatitude = mGoogleMap.getProjection().getVisibleRegion().latLngBounds.getCenter().latitude;
                Double nextlongitude = mGoogleMap.getProjection().getVisibleRegion().latLngBounds.getCenter().longitude;

                System.out.println("OnCameraIdleListenerLatLong" + nextlatitude + " " + nextlongitude);


                Dialog dialog= LoaderUtil.showProgressBar(MapActivity.this);
                getAddressFromLatiandLongi(nextlatitude, nextlongitude);
                LoaderUtil.dismisProgressBar(MapActivity.this,dialog);
            }
        });

    }


    private void enableGPS() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Toast.makeText(MapActivity.this, "Get Device Location", Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    getDeviceLocation();

                }
            }, 3000);


        } else {

            gpsCheck();

        }
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {

        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {


                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {

                                System.out.println("MyLastKnownLocation " + mLastKnownLocation);

                                myLocationLat = mLastKnownLocation.getLatitude();
                                myLocationLon = mLastKnownLocation.getLongitude();

                                System.out.println("MyLocationLatLong" + myLocationLat + " " + myLocationLon);
                                moveCamera(myLocationLat, myLocationLon);

                            } else if (mLastKnownLocation == null) {

                                final LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                                locationCallback = new LocationCallback() {

                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);

                                        mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

                                        if (locationResult == null) {
                                            return;
                                        } else if (locationResult != null) {

                                            mLastKnownLocation = locationResult.getLastLocation();

                                            myLocationLat = mLastKnownLocation.getLatitude();
                                            myLocationLon = mLastKnownLocation.getLongitude();

                                            System.out.println("MyLocationLatLong" + myLocationLat + " " + myLocationLon);

                                            moveCamera(myLocationLat, myLocationLon);


                                            mFusedLocationProviderClient.removeLocationUpdates(locationCallback);

                                        }


                                    }
                                };


                            }


                        }


                    }
                });


    }

    private void getAddressFromLatiandLongi(Double myLocationLat, Double myLocationLon) {

        storeCoordinatesInSqlite(myLocationLat, myLocationLon);

        List<Address> geoAddresses = GpsUtils.getAddressFromMap(MapActivity.this, myLocationLat, myLocationLon);

        if (geoAddresses.size() != 0) {

            String address = geoAddresses.get(0).getAddressLine(0);
            String area = geoAddresses.get(0).getLocality();
            String city = geoAddresses.get(0).getAdminArea();
            String country = geoAddresses.get(0).getCountryName();
            String postalCode = geoAddresses.get(0).getPostalCode();
            String subAdminArea = geoAddresses.get(0).getSubAdminArea();
            String subLocality = geoAddresses.get(0).getSubLocality();
            String premises = geoAddresses.get(0).getPremises();
            String addressLine = geoAddresses.get(0).getAddressLine(0);

            System.out.println("MyAddress" + address + " " + area + " " + city + " " + postalCode);
            materialSearchBar.enableSearch();
            materialSearchBar.setText(address + " " + area + " " + city + " " + postalCode);
            tickIcon.setVisibility(View.VISIBLE);


            materialSearchBar.setTextColor(R.color.red);

        } else {

            //Toast.makeText(MapActivity.this, "Please try again", Toast.LENGTH_LONG).show();
        }


    }


    private void moveCamera(Double myLocationLat, Double myLocationLon) {

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocationLat, myLocationLon), DEFAULT_ZOOM));

//        Dialog dialog=LoaderUtil.showProgressBar(MapActivity.this);

        getAddressFromLatiandLongi(myLocationLat, myLocationLon);

       // LoaderUtil.dismisProgressBar(MapActivity.this,dialog);


    }


    private void gpsCheck() {
        new GpsUtils(MapActivity.this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS*

                isGPS = isGPSEnable;

                System.out.println("ISGPS" + isGPS);

            }
        });

    }

    @Override
    public void
    onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_PROVIDER_CODE) {
                // flag maintain before get location

                isGPS = true;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDeviceLocation();
                    }
                }, 10000);


                Toast.makeText(MapActivity.this, "GetDeviceLocationnn", Toast.LENGTH_LONG).show();


            }

        } else {
           // Toast.makeText(MapActivity.this, "Open Map", Toast.LENGTH_LONG).show();
        }
    }

    private void storeCoordinatesInSqlite(Double myLocationLat, Double myLocationLon) {
        String prefLat = String.valueOf(myLocationLat);
        String preLon = String.valueOf(myLocationLon);
        if (addrPoint.equals("A")) {
            PreferenceUtil.setValueString(MapActivity.this, PreferenceUtil.POINT_A_LAT, prefLat);
            PreferenceUtil.setValueString(MapActivity.this, PreferenceUtil.POINT_A_LONG, preLon);
        } else if (addrPoint.equals("B")) {
            PreferenceUtil.setValueString(MapActivity.this, PreferenceUtil.POINT_B_LAT, prefLat);
            PreferenceUtil.setValueString(MapActivity.this, PreferenceUtil.POINT_B_LONG, preLon);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        /*PreferenceUtil.remove(MapActivity.this, PreferenceUtil.POINT_A_LAT);
        PreferenceUtil.remove(MapActivity.this, PreferenceUtil.POINT_A_LONG);

        PreferenceUtil.remove(MapActivity.this, PreferenceUtil.POINT_B_LAT);
        PreferenceUtil.remove(MapActivity.this, PreferenceUtil.POINT_B_LONG);
*/

        /*Intent launchDrawbleActivity=new Intent(MapActivity.this,DrawerActivity.class);
        startActivity(launchDrawbleActivity);
        finish();*/


       //Toast.makeText(MapActivity.this,"OnBackButtonIsPressed",Toast.LENGTH_LONG).show();
    }

    private void launchDrawbleActivity() {

        Intent launchDrawbleActivity=new Intent(MapActivity.this,DrawerActivity.class);
        startActivity(launchDrawbleActivity);
        finish();

    }

}
