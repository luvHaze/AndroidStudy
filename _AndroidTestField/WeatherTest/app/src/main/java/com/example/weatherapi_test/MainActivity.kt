package com.example.weatherapi_test

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val API_KEY = "ef7108872ae71fd5fd237a0cdd318096"
        val DEFAULT_API = "api.openweathermap.org/data/2.5/weather"

        var uri :Uri = Uri.parse("api.openweathermap.org/data/2.5/forecast?id=524901").buildUpon()
                .appendQueryParameter("APPID",API_KEY).build()

        GlobalScope.launch {
            weatherAPI_Call()
        }
    }


    fun weatherAPI_Call (){

    }
}
