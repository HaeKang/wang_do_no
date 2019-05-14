package com.example.wang_do_no

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kr.go.seoul.trafficsubway.TrafficSubwayButtonTypeB

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 버튼 클릭 이벤트
        Subway_Button.setOnClickListener {
            val intent_subway = Intent(this, Subway_::class.java)
            startActivity(intent_subway)
        }

        SignIn_Button.setOnClickListener {
            val intent_sign = Intent(this,SignActivity::class.java)
            startActivity(intent_sign)
        }

        Bus_Button.setOnClickListener {
            val intent_bus = Intent(this, BusActivity::class.java)
            startActivity(intent_bus)
        }

    }

}
