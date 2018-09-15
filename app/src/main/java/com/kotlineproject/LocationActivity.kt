package com.kotlineproject

import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationActivity : AppCompatActivity(), LocationListener, OnMapReadyCallback {

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
    }

    override fun onProviderEnabled(p0: String?) {
    }

    override fun onProviderDisabled(p0: String?) {
    }

    override fun onLocationChanged(location: Location?) {
        val locationTv = findViewById(R.id.latlongLocation) as TextView
        val latitude = location!!.getLatitude()
        val longitude = location!!.getLongitude()
        val latLng = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(latLng))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
        locationTv.text = "Latitude:$latitude, Longitude:$longitude"
    }

    override fun onMapReady(map: GoogleMap?) {
        this.googleMap = map!!
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        googleMap.isMyLocationEnabled = true
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        criteria.isAltitudeRequired = false
        criteria.isBearingRequired = false
        criteria.isCostAllowed = true
        criteria.powerRequirement = Criteria.POWER_LOW
        val bestProvider = locationManager.getBestProvider(criteria, true)
        val location = locationManager.getLastKnownLocation(bestProvider)

        if (location != null) {
            onLocationChanged(location)
            try {
                camera = LatLng(location.latitude, location.longitude)
                origin = LatLng(location.latitude, location.longitude)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(camera, 16f))
                // Zoom in, animating the camera.
                googleMap.animateCamera(CameraUpdateFactory.zoomIn())
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(camera, 25f), 4000, null)
                locationManager.requestLocationUpdates(bestProvider, 2000, 10f, this)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /*camera = LatLng(22.2795222,70.7677471)
        origin = LatLng(22.2795222,70.7677471)*/

    }

    lateinit var googleMap: GoogleMap
    private var camera: LatLng? = null
    private var origin: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        (supportFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment).getMapAsync(this@LocationActivity)
    }
}
