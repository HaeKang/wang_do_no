package com.example.wang_do_no.bus

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.example.wang_do_no.R
import kotlinx.android.synthetic.main.activity_feel_bus.*

class FeelBusActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feel_bus)


        var feel : String? =null

        bad.setOnClickListener{
            feel = "bad"
            text_good.text = null
            text_soso.text = null
            text_bad.text = "피곤해요..."
            checkCondition()
        }

        soso.setOnClickListener{
            feel = "soso"
            checkCondition()
            text_soso.text = "보통이에요"
            text_good.text = null
            text_bad.text = null
        }

        good.setOnClickListener{
            feel = "good"
            checkCondition()
            text_good.text = "쌩쌩해요"
            text_bad.text = null
            text_soso.text = null
        }

        submit.setOnClickListener{
            val intent = Intent(this, BusActivity::class.java)
            intent.putExtra("feeling", feel)
            startActivity(intent)
        }

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkCondition(){
        submit.setBackgroundColor(getColor(android.R.color.holo_orange_light))
        submit.setTextColor(getColor(android.R.color.white))
    }
}

