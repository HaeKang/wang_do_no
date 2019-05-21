package com.example.wang_do_no

import android.os.Bundle
import android.support.annotation.IntegerRes
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_subway_.*
import kotlinx.android.synthetic.main.activity_subway_.view.*
import kr.go.seoul.trafficsubway.TrafficSubwayButtonTypeB

class Subway_ : AppCompatActivity() {

    private val OpenApiKey = "525353425463757436324659634e59" // 실시간 정보 제외
    private val OpenApiKey2 = "not" // 실시간 정보 포함

    private lateinit var TypeB : TrafficSubwayButtonTypeB


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_Subway -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_Find -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_Friend -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subway_)

        //TypeB = findViewById(R.id.type_b)
        TypeB = findViewById(R.id.type_b)
        TypeB.setOpenAPIKey(OpenApiKey)
        // 실시간 사용 시 TypeB.setsubwayLocationAPIKey(OpenApiKey2)


        val drawableId = resources.getIdentifier("ic_home_black_24dp", "drawable", "com.example.package")

        TypeB.setButtonText("")
        TypeB.setButtonImage(drawableId)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }


}
