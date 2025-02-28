package com.example.test8

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.test8.data.LocationData
import com.example.test8.presentation.LocationViewModel
import com.example.test8.presentation.MarkerBottomSheet
import com.example.test8.presentation.MarkerClusterItem
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var locationViewModel: LocationViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var clusterManager: ClusterManager<MarkerClusterItem>
    private val LOCATION_REQUEST_CODE = 1001

    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

            if (fineLocationGranted || coarseLocationGranted) {

                getUserLocation()
            } else {

                Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show()
            }
        }


    private val locationSettingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                Toast.makeText(this, "Location enabled", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this, "Location is required for this feature", Toast.LENGTH_SHORT).show()
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        val progressBar = ProgressBar(this)
        progressBar.isIndeterminate = true
        progressBar.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setView(progressBar)
            .setCancelable(false)
            .create()






            dialog.show()
        locationViewModel = ViewModelProvider(this)[LocationViewModel::class.java]

        locationViewModel.locations.observe(this, Observer { locations ->
            addMarkers(locations)
            dialog.dismiss()

        })

        locationViewModel.error.observe(this, Observer { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })

        locationViewModel.fetchLocations()


        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment

        mapFragment?.getMapAsync(this)
        checkLocationSettings()

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        clusterManager = ClusterManager(this, mMap)
        mMap.setOnCameraIdleListener(clusterManager)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            getUserLocation()
        observeLocationDataForClustering()
        }
    }

    private fun observeLocationDataForClustering() {

        locationViewModel.locations.observe(this) { locations ->
            clusterManager.clearItems()
            val markerItems = locations.map {
                MarkerClusterItem(it.lat, it.lan, it.title, it.address)
            }
            clusterManager.addItems(markerItems)
            clusterManager.cluster()
        }
    }


    @SuppressLint("PotentialBehaviorOverride")
    private fun addMarkers(locations: List<LocationData>) {
        for (location in locations) {
            val position = LatLng(location.lat, location.lan)
            val marker = mMap.addMarker(
                MarkerOptions()
                    .position(position)
                    .title(location.title)
                    .snippet(location.address)
            )
            marker?.tag = location
        }

        mMap.setOnMarkerClickListener { marker ->
            val location = marker.tag as? LocationData
            location?.let {
                val bottomSheet = MarkerBottomSheet.newInstance(it.title, it.lat, it.lan, it.address)
                bottomSheet.show(supportFragmentManager, "MarkerBottomSheet")
            }
            true
        }
    }

    private fun getUserLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: android.location.Location? ->
                location?.let {
                    val latitude = it.latitude
                    val longitude = it.longitude
                    Toast.makeText(this, "Lat: $latitude, Long: $longitude", Toast.LENGTH_LONG).show()

                    val userLatLng = LatLng(it.latitude, it.longitude)


                    mMap.addMarker(
                        MarkerOptions()
                            .position(userLatLng)
                            .title("You are here")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    )


                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
                } ?: Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show()
            }
        }


    }


    private fun checkLocationSettings() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000).build()
        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .setAlwaysShow(true)
            .build()

        val settingsClient = LocationServices.getSettingsClient(this)
        val task = settingsClient.checkLocationSettings(locationSettingsRequest)

        task.addOnSuccessListener {

            Toast.makeText(this, "Location is already enabled", Toast.LENGTH_SHORT).show()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {

                    locationSettingsLauncher.launch(
                        IntentSenderRequest.Builder(exception.resolution).build()
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LOCATION_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Toast.makeText(this, "Location enabled", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this, "Location is required for this feature", Toast.LENGTH_SHORT).show()
            }
        }
    }













}

