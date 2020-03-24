package com.example.dimoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View

class IntroActivity : AppCompatActivity() {

    var handler: Handler? = null // Runnable을 실행하는 클래스
    var runnable: Runnable? = null // 병렬 실행이 가능한 Thread를 만들어주는 클래스

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // 작업표시줄 등 시스템 UI 숨기는 코드
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

    }

    override fun onResume() {
        super.onResume()
        // Runnable이 실행되면 ListActivity로 이동)
        runnable= Runnable {
            val intent = Intent(applicationContext, ListActivity::class.java)
            startActivity(intent)
        }
        handler = Handler()
        handler?.run{
            postDelayed(runnable,2000)  // Handler를 생성하고 2000ms(2초) 후 runnable 을 실행
        }
    }

    override fun onPause() {
        super.onPause()

        handler?.removeCallbacks(runnable)
        //Activity Pause 상태일 때는 runnable도 중단하도록 함
    }
}
