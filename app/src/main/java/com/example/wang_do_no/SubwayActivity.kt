package com.example.wang_do_no

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_subway.*
import kr.go.seoul.trafficsubway.TrafficSubwayButtonTypeB
import java.sql.Types.NULL

class SubwayActivity : AppCompatActivity() {
    private val OpenApiKey = "sample"
    private lateinit var TypeB : TrafficSubwayButtonTypeB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subway)

        TypeB = findViewById(R.id.type_b)
        TypeB.setOpenAPIKey(OpenApiKey)

    }
}
