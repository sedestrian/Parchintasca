package com.agaboardi.parchintasca.splash

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
