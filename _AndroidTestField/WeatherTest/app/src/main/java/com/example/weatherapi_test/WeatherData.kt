package com.example.weatherapi_test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class WeatherData {

    companion object {
        /*xml 을 파싱하기 위한
        XmlPullParser를 생성하는
        XmnlPullParserFactory의 객체를 생성*/
        private val xmlPullParserFactory by lazy {
            // suspend를 붙여 코루틴에서 언제든지 중지 또는 재개할 수 있도록 함
            suspend fun getCurrentWeather(latitude : Double , longitude : Double):String{
                return GlobalScope.async(Dispatchers.IO){

                    val requestURL = "https://api.openweathermap.org/data/2.5/weather"+
                            "?lat=${latitude}&lon=${longitude}&mode=xml&units=metric&"+
                            "&appid=api_key"

                }.await()
                // 코루틴의 결과를 await()함수로 기다린 후 반환환


            }

       }
    }
}