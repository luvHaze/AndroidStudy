package com.example.dimoproject

import android.app.Application
import com.naver.maps.map.NaverMapSdk
import io.realm.Realm

class DimoMemoApplication :Application() {

    // 앱 시작시 실행되는 onCreate() 함수를 overrride 하고 그안에서 Realm 데이터베이스를 초기화 함
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        NaverMapSdk.getInstance(this).setClient(
            NaverMapSdk.NaverCloudPlatformClient("3jijtyopjo")
        )

    }
}