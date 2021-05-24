package rs.gecko.demoappnapredninivo.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import rs.gecko.demoappnapredninivo.R
import rs.gecko.demoappnapredninivo.ui.adapters.CustomInfoWindowAdapter
import rs.gecko.demoappnapredninivo.ui.fragments.UserPhotosFragment.Companion.tmpPhotoForTest

@AndroidEntryPoint
class MapActivity : AppCompatActivity(R.layout.activity_map), OnMapReadyCallback {

    override fun onMapReady(googleMap: GoogleMap) {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onMapReady: Map is ready")
        mMap = googleMap

        if(mLocationPermissionsGranted) {
            getDeviceLocation()

            //mMap.isMyLocationEnabled = true //blue dot on current location
            mMap.uiSettings.isMyLocationButtonEnabled = false   //gps button remove
        }

    }

    private val TAG: String = "MapActivity"
    private val LOCATION_PERMISSION_REQUEST_CODE = 1234
    private val DEFAULT_ZOOM = 15f


    //vars
    private var mLocationPermissionsGranted: Boolean = false
    private lateinit var mMap: GoogleMap
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mMarker: Marker
    private lateinit var locationLatLng: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getLocationPermissions()
    }

    private fun initMap() {
        Log.d(TAG, "initMap: Initializing map")
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        //one way of initialising map
//        mapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(@NonNull GoogleMap googleMap) {
//                mMap = googleMap;
//            }
//        });
        //other way is that whole MapActivity class implements OnReadyCallbackInterface
        mapFragment!!.getMapAsync(this@MapActivity) //this will trigger onMapReady
    }

    private fun getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location")
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        try {
            if (mLocationPermissionsGranted) {
                //fix
                val mLocationRequest = LocationRequest.create();
                mLocationRequest.setInterval(60000);
                mLocationRequest.setFastestInterval(5000);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                val mLocationCallback = object: LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        if (locationResult == null)
                            return
                        else {
                            val currentLocation: Location = locationResult.lastLocation
                            locationLatLng = LatLng(currentLocation.latitude, currentLocation.longitude)
                            moveCamera(LatLng(currentLocation.latitude, currentLocation.longitude), DEFAULT_ZOOM)
                        }
                    }
                }
                mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null)

                // ne radi na ovaj nacin stalno - iznad fix
//                val location: Task<Location> = mFusedLocationProviderClient.lastLocation
//
//                location.addOnCompleteListener {
//                    if (it.isSuccessful && it.getResult() != null) {
//                        Log.d(TAG, "onComplete: found location")
//                        val currentLocation: Location = it.getResult() as Location
//                        moveCamera(LatLng(currentLocation.latitude, currentLocation.longitude), DEFAULT_ZOOM)
//
//                    } else {
//                        Log.d(TAG, "onComplete: current location is null")
//                        Toast.makeText(this, "unable to get current location", Toast.LENGTH_SHORT).show()
//                        //ovde ako je lokacija null
//                    }
//                }
            }
        } catch (e: SecurityException) {
            Log.d(TAG, "getDeviceLocation: SecurityException: " + e.message)
        }
    }

    private fun moveCamera(latLng: LatLng, zoom: Float) {
        Log.d(TAG, "moveCamera: Moving camera to lat: ${latLng.latitude}, lng: ${latLng.longitude}")
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))

        mMap.clear()


        //drop a pin
        val markerOptions = MarkerOptions().apply {
            position(latLng)
        }
        mMarker = mMap.addMarker(markerOptions)

        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter(context = this, userPhoto = tmpPhotoForTest))

        mMarker.showInfoWindow()
    }

    //check location permissions
    private fun getLocationPermissions() {
        Log.d(TAG, "getLocationPermissions: checking location permissions")
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "getLocationPermissions: location permissions are good")
                mLocationPermissionsGranted = true
                initMap()
            }
        } else {
            //if permission needs to be allowed by user
            //this triggers onRequestPermissionsResult
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE)
            Log.d(TAG, "getLocationPermissions: user needs to give permissions")
        }
    }

    //if permission needed manually by user
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        Log.d(TAG, "onRequestPermissionsResult: called")
        mLocationPermissionsGranted = false
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE-> {
                if (grantResults.size > 0) {
                    var i = 0
                    while (i < grantResults.size) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false
                            Log.d(TAG, "oNRequestPermissionsResult: permission failed")
                            return
                        }
                        i++
                    }
                    Log.d(TAG, "oNRequestPermissionsResult: permission granted")
                    mLocationPermissionsGranted = true
                    //permissions are all good - initialize map
                    initMap()
                }
            }
        }
    }

    override fun onBackPressed() {

        val resultIntent = Intent().apply {
            putExtra("locationLat", locationLatLng.latitude)
            putExtra("locationLng", locationLatLng.longitude)
        }
        Log.d(TAG, "onBackPressed: Returning lat: ${locationLatLng.latitude}, lng: ${locationLatLng.longitude}")
        setResult(RESULT_OK, resultIntent)
        finish()
    }

}