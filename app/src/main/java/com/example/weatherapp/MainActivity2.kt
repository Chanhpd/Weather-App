package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main2.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {
    var city = ""

    lateinit var adapter: ItemAdapter
    lateinit var weatherList: ArrayList<Weather>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        weatherList = ArrayList()
        adapter = ItemAdapter(this, weatherList)

        recycleView_7days.layoutManager = LinearLayoutManager(this)
        recycleView_7days.adapter = adapter

        city = intent.getStringExtra("city") as String



        if (city.equals("")) {
            city = "danang"
            get7DaysData(city)
        } else {
            get7DaysData(city)
        }
        get7DaysData(city)

        imgBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun get7DaysData(data: String) {
        var url =
            "http://api.openweathermap.org/data/2.5/forecast?q=$data&units=metric&cnt=7&appid=c3f3d729a254966cbaa9eba0d7c2f771&lang=vi"
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val stringRequest = object : StringRequest(Request.Method.GET, url, Response.Listener {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            val jsonObject = JSONObject(it)

            val jsonObjectCity = jsonObject.getJSONObject("city")
            val name: String = jsonObjectCity.getString("name")
            tv_city_7days.text = name

            val jsonArrayList: JSONArray = jsonObject.getJSONArray("list")
            for (i in 0 until jsonArrayList.length()) {
                val jsonObjectList: JSONObject = jsonArrayList.getJSONObject(i)

                val day: String = jsonObjectList.getString("dt")

                // date
                val l: Long = day.toLong()
                val date = Date(l * 1000L)
                val simpleDateFormat = SimpleDateFormat("EEEE yyyy-MM-dd")
                val ngay: String = simpleDateFormat.format(date)
                

                // Temperature
                val jsonObjectTemp : JSONObject = jsonObjectList.getJSONObject("main")
                val max : String = jsonObjectTemp.getString("temp_max")
                val min : String = jsonObjectTemp.getString("temp_min")

                val jsonArrayWeather:JSONArray= jsonObjectList.getJSONArray("weather")
                val jsonObjectWeather = jsonArrayWeather.getJSONObject(0)
                val status : String = jsonObjectWeather.getString("description")
                val icon = jsonObjectWeather.getString("icon")

                weatherList.add(Weather(day,status,icon,max,min))
            }
            adapter = ItemAdapter(this, weatherList)
            adapter.notifyDataSetChanged()

        }, Response.ErrorListener {
            Log.d("DDD", it.message.toString())
            Toast.makeText(this, "Error ${it.message}", Toast.LENGTH_SHORT).show()

        }) {}
        requestQueue.add(stringRequest)
    }
}