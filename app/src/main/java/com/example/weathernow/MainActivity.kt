package com.example.weathernow

import android.content.Context
import android.content.pm.PackageManager
import android.icu.util.TimeUnit
import android.location.Address
import android.location.LocationManager
//import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import androidx.core.location.LocationRequestCompat
import com.google.android.gms.location.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Suppress("DEPRECATION")
const val url = "https://api.openweathermap.org/data/2.5/weather?"
class MainActivity : AppCompatActivity() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    val permid = 29482
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val currentweather: TextView = findViewById(R.id.status)
        val logo: ImageView = findViewById(R.id.weatherlogo)
        val weather = "clear"
        val weatherid = 600
        val loca: TextView = findViewById(R.id.Address)

        getweatherdata();










        currentweather.text = weather.toString()

        val logoid = when (weatherid) {
            200 -> R.drawable.cloud//Thunderstorm
            500 -> R.drawable.rain//rain
            600 -> R.drawable.rain //snow
            801 -> R.drawable.cloud // cloudy
            else -> R.drawable.sun //clear sky
        }
        logo.setImageResource(logoid)
        fun getlastlocation(){
            if (checkpermission()){
                if (islocationenabled()){
                    fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                        val location = task.result
                        if (location == null){

                        }else{
                            loca.text = "Lat:"+location.latitude+" Lon:"+location.longitude
                        }
                    }
                }else{
                    Toast.makeText(this,"Please enable your location",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                RequestPermission()
            }
        }


    }

    private fun getweatherdata() {
        val retro = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
            .create(APIinterface::class.java)
        val retrofitdata = retro.getdata()
        retrofitdata.enqueue(object : Callback<List<apidata>?> {
            override fun onResponse(
                call: Call<List<apidata>?>,
                response: Response<List<apidata>?>
            ) {

            }

            override fun onFailure(call: Call<List<apidata>?>, t: Throwable) {

            }
        })
    }


    private  fun checkpermission():Boolean{
            if(    ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ){
                return true
            }
            return false
        }
       private fun RequestPermission(){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,),permid
            )
        }
        private fun islocationenabled():Boolean{
            var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == permid){
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Log.d("debug","permission recieved")
                }
            }
        }







    }
