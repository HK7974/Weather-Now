package com.example.weathernow

import retrofit2.Call
import retrofit2.http.GET

interface APIinterface {

   @GET("lat={lat}&lon={lon}&appid=d32be0ded4d9218205d70214b71b3477")

    fun getdata():Call<List<apidata>>
}