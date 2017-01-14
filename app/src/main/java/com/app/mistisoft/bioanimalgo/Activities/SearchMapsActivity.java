package com.app.mistisoft.bioanimalgo.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.mistisoft.bioanimalgo.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchMapsActivity extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    //    FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button My_location;
    private Button New_location;
    private TextView mLatitudeText;
    private TextView mLongitudeText;
    GoogleApiClient mGoogleApiClient;

    //Firebase
    private DatabaseReference settingsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_maps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Firebase
        settingsDB = FirebaseDatabase.getInstance().getReference().child("settings").child(getUid());


        My_location = (Button) findViewById(R.id.my_location);
        New_location = (Button) findViewById(R.id.new_location);
        mLatitudeText = (TextView) findViewById(R.id.last_latitud);
        mLongitudeText = (TextView) findViewById(R.id.last_longitud);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        My_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("NavigationDrawer", "Click en My_location");
                geoLocate();
            }
        });

        New_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("NavigationDrawer", "Click en New_location");
            }
        });

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
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

        // Escogiendo tipo de map "hybrid"
        // GoogleMap.MAP_TYPE_NORMAL,GoogleMap.MAP_TYPE_HYBRID y GoogleMap.MAP_TYPE_SATELLITE
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Deshabilitando mapas interiores
        mMap.setIndoorEnabled(false);

        //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        //return
        //}
        ///mMap.setMyLocationEnabled(true);


        // Add a marker in Sydney and move the camera
        LatLng arequipa = new LatLng(-16.3988900, -71.5350000);
        mMap.addMarker(new MarkerOptions().position(arequipa).title("Tu estas AQUI"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(arequipa));

        // Move the camera instantly to Sydney with a zoom of 15.
        //Editar zoom CameraUpdateFactory.zoomIn() y CameraUpdateFactory.zoomOut()
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arequipa, 14));

        //Mover posicion de la camara
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng));

        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        //CameraPosition cameraPosition = new CameraPosition.Builder()
        //        .target(MOUNTAIN_VIEW)      // Sets the center of the map to Mountain View
        //        .zoom(17)                   // Sets the zoom
        //        .bearing(90)                // Sets the orientation of the camera to east
        //        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
        //        .build();                   // Creates a CameraPosition from the builder
        //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //Detectar clicks en el mapa
        //OnMapClickListener.onMapClick()

        //Agregar relleno al mapa
        mMap.setPadding(0, 300, 0, 0);

        AddCircleRegion(arequipa);

    }

    private void AddCircleRegion(LatLng Pos) {
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(Pos)
                .radius(1500)
                .strokeColor(Color.BLUE)
        );//.fillColor(Color.BLUE)
    }


    private void GoToLocationZoom(double lt, double lg, float zoom) {
        LatLng newPos = new LatLng(lt, lg);
        mMap.addMarker(new MarkerOptions().position(newPos).title("Tu estas AQUI"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPos, 14));
    }

    @Override
    public void onConnected(@Nullable Bundle connectionHint) {
        Location mLastLocation;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }

    public void geoLocate(){
        Location mLastLocation;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i("NavigationDrawer", "Sin acceso a datos de GPS");
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
