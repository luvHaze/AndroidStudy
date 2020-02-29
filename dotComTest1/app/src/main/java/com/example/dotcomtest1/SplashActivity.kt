package com.example.dotcomtest1

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.AnimationUtilsCompat
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    lateinit var handler:Handler
    lateinit var anime : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val img2: ImageView=findViewById(R.id.imageView2)

        //Handler 로 스플래쉬 화면 5초간 기다리게 함
        handler= Handler()
        handler.postDelayed( {

            var intent_appStart = Intent(this, MainActivity::class.java)

            startActivity(intent_appStart)

            finish()
        },5000)

    }
}
