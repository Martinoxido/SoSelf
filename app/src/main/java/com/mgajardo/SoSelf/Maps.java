package com.mgajardo.SoSelf;

import android.annotation.SuppressLint;





import android.content.pm.PackageManager;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import android.location.Location;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

public class Maps extends AppCompatActivity {

    private MapView mapView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    Marker MiMarker;
    private static final float MAX_ACCEPTABLE_ACCURACY = 1000.0f;
    TextView coords;
    Switch swCentrar;
    private IMapController Imap;


    @SuppressLint("VisibleForTests")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        coords = (TextView) findViewById(R.id.coords);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        swCentrar = (Switch) findViewById(R.id.switch1);

        fusedLocationClient = new FusedLocationProviderClient(this);
        locationRequest = new LocationRequest();
        locationRequest.setInterval(3000); // Intervalo de actualización en milisegundos (10 segundos en este ejemplo)
        locationRequest.setFastestInterval(5000); // Intervalo más rápido posible
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));



        mapView = findViewById(R.id.mapView);

        MiMarker = new Marker(mapView);
        // Configura el mapa
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        Imap = mapView.getController();
        Imap.setZoom(20);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {

                    if (location.getAccuracy() < MAX_ACCEPTABLE_ACCURACY) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        //miUbicacion.setCoords(latitude,longitude);
                        // Actualiza la interfaz de usuario o realiza cualquier otra acción necesaria
                        updateUI(latitude, longitude);
                    }

                    // Puedes usar un callback para comunicar la ubicación a otras partes de tu aplicación

                }
            }
        };

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

    }
    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void updateUI(double latitude, double longitude) {
        coords.setText("lotitud: "+latitude+" longitud: "+ longitude);
        mapView.getOverlays().remove(MiMarker);
        GeoPoint geoPoint = new GeoPoint(latitude,longitude);
        Imap.setCenter(geoPoint);
        MiMarker = new Marker(mapView);
        MiMarker.setTitle("Mi Ubicacion");
        MiMarker.setPosition(geoPoint);
        mapView.getOverlays().add(MiMarker);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        }

    }
}














