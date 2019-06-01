package com.example.wang_do_no.subway

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast

import com.example.wang_do_no.R
import com.example.wang_do_no.test2
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import kotlinx.android.synthetic.main.activity_subway_.*
import org.json.JSONException

class Subway_ : AppCompatActivity() {

    private val OpenApiKey = "44794542716375743539694f487571" // 실시간 정보 포함

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subway_)

        if(intent.getStringExtra("user_id") != null) {
            var id = intent.getStringExtra("user_id")
            var nickname = intent.getStringExtra("user_nickname")
            var subway = intent.getStringExtra("user_subway")

            Freind_btn.setOnClickListener{
                val intent_friend = Intent(this, subway_friend::class.java)
                intent_friend.putExtra("user_nickname",nickname)
                startActivity(intent_friend)
            }
        }
        else{
            Freind_btn.setOnClickListener{
                Toast.makeText(this,"로그인 후 이용해주세요",Toast.LENGTH_LONG).show()
            }
        }

        type_b.setOpenAPIKey(OpenApiKey)

        type_b.setButtonImage(R.drawable.subway)
        type_b.setButtonText("")

        Find_btn.setOnClickListener{
            val intent_find = Intent(this, test2::class.java)
            startActivity(intent_find)
        }


    }


}
