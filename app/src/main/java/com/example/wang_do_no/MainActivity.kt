package com.example.wang_do_no

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kr.go.seoul.trafficsubway.TrafficSubwayButtonTypeB
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(intent.hasExtra("user_nickname")) {
            textView.setText(intent.getStringExtra("user_nickname") + "님 환영합니다!")
        } else{
            textView.setText("회원님 환영합니다!")
        }



        // 버튼 클릭 이벤트
        Subway_Button.setOnClickListener {
            val intent_subway = Intent(this, Subway_::class.java)
            startActivity(intent_subway)
        }

        SignIn_Button.setOnClickListener {
            val intent_sign = Intent(this,SignIn_Activity::class.java)
            startActivity(intent_sign)
        }

        Bus_Button.setOnClickListener {
            val intent_bus = Intent(this, BusActivity::class.java)
            startActivity(intent_bus)
        }



    }


}
