package com.example.lab7

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private val MY_PERMISSIONS_REQUEST_LOCATION = 1
    private var locationManager: LocationManager? = null

    private var currentLatitude : Double = 0.0;
    private var currentLongitude : Double = 0.0;
    private var targetLatitude : Double = 0.0;
    private var targetLongitude : Double = 0.0;

    private val locationsList : ArrayList<com.example.lab7.Location> = ArrayList()

    private val locationsNamesList: ArrayList<String> = ArrayList()



    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) { showInfo(location) }
        override fun onProviderDisabled(provider: String) { showInfo() }
        override fun onProviderEnabled(provider: String) { showInfo() }
        override fun onStatusChanged(provider: String, status: Int,
                                     extras: Bundle) { showInfo() }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeLocations()
        val listView = findViewById<ListView>(R.id.list_view)
        val adapter = ArrayAdapter(this, R.layout.my_list_item, locationsNamesList)

        listView.adapter = adapter

        listView.onItemClickListener = OnItemClickListener {  _, _, position, _  ->
            val location = locationsList[position]
            targetLatitude = location.latitude
            targetLongitude = location.longitude
            findViewById<TextView>(R.id.target_coords).text =
                "Выбранные координаты: \n Широта: " + targetLatitude.toString() + "\n Долгота: " + targetLongitude + "\n"
            val distance = calculateDistance(location.latitude, location.longitude,
                targetLatitude, targetLongitude)
            findViewById<TextView>(R.id.network_distance).text = distance.toString()
        }
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

    }




    override fun onResume() {
        super.onResume()
        startTracking()
    }
    override fun onPause() {
        super.onPause()
        stopTracking()
    }

    private fun startTracking(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

            }
            else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION)
            }
        }
        else {
            locationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 1f, locationListener)
            locationManager!!.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000, 1f, locationListener)
            showInfo()

        }
    }

    fun stopTracking() {
        locationManager!!.removeUpdates(locationListener)
    }

    private fun showInfo(location: Location? = null) {
        val isGpsOn = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkOn = locationManager!!.
        isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        findViewById<TextView>(R.id.gps_status).text =
            if (isGpsOn) "GPS ON" else "GPS OFF"
        findViewById<TextView>(R.id.network_status).text =
            if (isNetworkOn) "Network ON" else "Network OFF"


        if (location != null) {
            if (location.provider == LocationManager.GPS_PROVIDER) {
                findViewById<TextView>(R.id.gps_coords).text =
                    "GPS: \n широта = " + location.latitude.toString() +
                            ",\n долгота = " + location.longitude.toString()
            }
            if (location.provider == LocationManager.NETWORK_PROVIDER) {
                currentLatitude = location.latitude
                currentLongitude = location.longitude
                findViewById<TextView>(R.id.network_coords).text =
                    "Network: \n широта = " + location.latitude.toString() +
                            ",\n долгота = " + location.longitude.toString()
                if(targetLatitude != 0.0 && targetLongitude != 0.0){
                    val distance = calculateDistance(location.latitude, location.longitude,
                        targetLatitude, targetLongitude)
                    findViewById<TextView>(R.id.network_distance).text = "Расстояние до точки: " + distance.toString() + " м."
                    findViewById<TextView>(R.id.notification).text = if(distance <= 100.0) "Вы в радиусе 100м от точки!" else ""
                }
            }
        }
    }

    fun buttonOpenSettings(view: View) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName"))
        startActivity(intent)
    }


    private fun calculateDistance(currentLatitude: Double, currentLongitude: Double,
                                  targetLatitude: Double, targetLongitude : Double): Double{
        val theta = currentLongitude - targetLongitude

        val dist = sin(deg2rad(currentLatitude)) * sin(deg2rad(targetLatitude)) +
                   cos(deg2rad(currentLatitude)) * cos(deg2rad(targetLatitude)) *
                    cos(deg2rad(theta))
        return rad2deg(acos(dist)) * 60 * 1.1515 * 1609.34

    }

    private fun deg2rad(deg : Double) : Double{
        return (deg * Math.PI / 180.0)
    }

    private fun rad2deg(rad : Double) : Double{
        return (rad * 180.0 / Math.PI)
    }


    private fun initializeLocations(){
        locationsList.add(Location("НТБ", 56.4616268,84.9563719))
        locationsList.add(Location("Вершинина 39А", 56.4602638,84.9615794))
        locationsList.add(Location("КЦ",56.4617927, 84.9553205))

        locationsList.forEach{ x ->
            locationsNamesList.add(x.name)
        };

    }



}