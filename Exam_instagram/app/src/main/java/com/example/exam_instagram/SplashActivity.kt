package com.example.exam_instagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    lateinit var handler : Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler= Handler()

        handler.postDelayed({

            var intent_toLogin : Intent = Intent(this,LoginActivity::class.java)

            startActivity(intent_toLogin)

            finish()
        },3500)

    }
}
