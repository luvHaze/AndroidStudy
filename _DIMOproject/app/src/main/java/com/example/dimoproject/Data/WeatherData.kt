package com.example.dimoproject.Data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL

class WeatherData {
    companion object{
        // xml 을 파싱하기 위한 XmlPullParser를 생성하는 객체 생성
        private val xmlPullParserFactory by lazy { XmlPullParserFactory.newInstance() }

        //위치정보를 받아 현재 날씨를 문자열로 반환하는 함수
        // suspend는 언제든지 중지 또는 재개할 수 있도록 함
        suspend fun getCurrentWeather(latitude : Double, longitude : Double): String{
            // 함수의 내용은 네트웍으로 처리되므로 코루틴을 사용하여
            // IO 쓰레드에서 동작하도록 함 (이렇게 하지 않을경우 Exception 발생)
            return GlobalScope.async(Dispatchers.IO){
                val requestUrl = "https://api.openweathermap.org/data/2.5/weather"+
                                    "?lat=${latitude}&lon=${longitude}&mode=xml&units=metric&"+
                                "&appid=ef7108872ae71fd5fd237a0cdd318096"
                var currentWeather=""
                try{
                    //요청할 주소 물자열을 URL 객체로 만들어 줌
                    val url = URL(requestUrl)
                    //URL에 데이더를 요청하고 결과를 가져오는 입력 Stream 열어 줌
                    val stream=url.openStream()

                    //xml 형식
                    val parser = xmlPullParserFactory.newPullParser()

                    // URL의 입력 스트림을 UTf-8 방식으로 읽을 수 있는
                    // InputStreamReader 객체에 넣어 parser에 입력해줌줌
                    parser.setInput(InputStreamReader(stream,"UTF-8"))

                    // parser에서 현재 eventType 을 가져옴
                    // eventType이란 문서의 시작과 끝, 태그의 시작과 끝, 태그 내의 텍스트 등을 분류해서 알려줌
                    var eventType = parser.eventType
                    var currentWeatherCode = 0

                    while (eventType!= XmlPullParser.END_DOCUMENT){
                        // 출력된 xml에서 weather 태그가 시작하는곳을 찾음
                        if(eventType==XmlPullParser.START_TAG&& parser.name=="weather"){
                            // weather 태그의 number 속성을 Int타입으로
                            // 변환하여 가져온 후 반복문을 중지한다.
                            currentWeatherCode= parser.getAttributeValue(null,"number").toInt()
                            break
                        }
                        //반복할 때마다 next()함수를 호출하여
                        // 다음 Event를 파싱하고 eventType을 반환받음
                        eventType=parser.next()
                    }
                    when (currentWeatherCode){
                        in 200..299 -> currentWeather="뇌우"
                        in 300..399 -> currentWeather="이슬비"
                        in 500..599 -> currentWeather="비"
                        in 600..699 -> currentWeather="눈"
                        in 700..761 ->currentWeather="안개"
                        771 -> currentWeather="돌풍"
                        781->currentWeather="토네이도"
                        800->currentWeather="구름조금"
                        in 801..802 -> currentWeather ="구름조금"
                        in 803..804 -> currentWeather ="구름많음"
                        else -> currentWeather=""
                    }

                }catch (e:Exception){
                    println(e)
                }

                // try ~ catch 구문 내에서결과값인 currentWeather를 만든 후
                // 반환해줌 ( 코루틴의 반환이므로 변수만 써주면 됨)
                currentWeather

            }.await()   //코루틴 결과를 await로 기다렸다가 반환한다.


        }
    }
}