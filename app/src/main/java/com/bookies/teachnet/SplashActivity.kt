package com.bookies.teachnet

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_main)
       val splashIconImage:ImageView=findViewById(R.id.splash_icon_image);
    val appNameSplash:TextView=findViewById(R.id.splash_text);
//      var  splashAppCompany=findViewById(R.id.splash_app_company);
        //animation declaration
        //var fromBottom= AnimationUtils.loadAnimation(this,R.anim.from_bottom);
       val fadeIn=AnimationUtils.loadAnimation(this,R.anim.fade_in)
        //animation initialization
//        splashAppCompany.setAnimation(fromBottom);
//        splashAppCompany.startAnimation(fromBottom);
        splashIconImage.animation = fadeIn;
        splashIconImage.startAnimation(fadeIn);
        appNameSplash.animation = fadeIn;
        appNameSplash.startAnimation(fadeIn);
        val time = 2000
        Handler().postDelayed({
            val splashIntent = Intent(this@SplashActivity,HomePage::class.java)
            startActivity(splashIntent)
            finish()
        }, time.toLong())
    }
}