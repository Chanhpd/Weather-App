package com.example.weatherapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    var city: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCurrentWeatherData("Danang")

        btnSearch.setOnClickListener {

            city = edtSearch.text.toString().trim()
            if(city.equals("")){
                city = "danang"
                getCurrentWeatherData(city)
            }else{
                getCurrentWeatherData(city)
            }

        }
        btn_next.setOnClickListener {
            val i = Intent(this, MainActivity2::class.java)
            var city: String = edtSearch.text.toString().trim()
            i.putExtra("city", city)
            startActivity(i)
        }
    }

    fun getCurrentWeatherData(data: String) {
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val url =
            "https://api.openweathermap.org/data/2.5/weather?q=$data&units=metric&appid=c3f3d729a254966cbaa9eba0d7c2f771&lang=vi"
        var stringRequest = object : StringRequest(Request.Method.GET, url, Response.Listener {

            val jsonObject = JSONObject(it)
            val day = jsonObject.getString("dt")
            val name = jsonObject.getString("name")

            tv_tenTP.text = name

            val l: Long = day.toLong()
            val date = Date(l * 1000L)
            val simpleDateFormat = SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss")

            val ngay: String = simpleDateFormat.format(date)
            tv_date.text = ngay

            // Weather
            val jsonArrayWeather = jsonObject.getJSONArray("weather")
            val jsonObjectWeather = jsonArrayWeather.getJSONObject(0)
            val status: String = jsonObjectWeather.getString("main")
            val icon: String = jsonObjectWeather.getString("icon")

            Picasso.get().load("https://openweathermap.org/img/wn/$icon@2x.png").into(imageStatus)
            tv_status.text = status

            // Main
            val jsonObjectMain = jsonObject.getJSONObject("main")
            val temp: String = jsonObjectMain.getString("temp")
            val humidity: String = jsonObjectMain.getString("humidity")

            tv_temp.text = "$temp°C"
            tv_do_am.text = "$humidity%"

            // Wind
            val jsonObjectWind = jsonObject.getJSONObject("wind")
            val speed: String = jsonObjectWind.getString("speed")

            tv_gio.text = speed + "m/s"

            // Cloud
            val jsonObjectCloud = jsonObject.getJSONObject("clouds")
            val cloud: String = jsonObjectCloud.getString("all")

            tv_may.text = "$cloud%"


            // Sys
            val jsonObjectSys = jsonObject.getJSONObject("sys")
            val country: String = jsonObjectSys.getString("country")

            tv_nuoc.text = "$country"


        }, Response.ErrorListener {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }) {}
        requestQueue.add(stringRequest)
    }

}