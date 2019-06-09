package com.example.wang_do_no.subway

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.wang_do_no.MainActivity

import com.example.wang_do_no.R
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import kotlinx.android.synthetic.main.activity_subway_.*
import org.json.JSONException

class Subway_ : AppCompatActivity() {
    var time : Long = 0
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
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
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
            val intent_find = Intent(this, Find_Subway::class.java)
            startActivity(intent_find)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }


        main_btn.setOnClickListener {
            val intent_main = Intent(this, MainActivity::class.java)
            startActivity(intent_main)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

    }

    // 뒤로가기 두번 이벤트
    override fun onBackPressed(){
        if(System.currentTimeMillis()-time >=2000){
            time = System.currentTimeMillis()
            Toast.makeText(this@Subway_, "뒤로 버튼을 한번 더 누르면 메인으로 갑니다", Toast.LENGTH_LONG).show()
        } else if(System.currentTimeMillis()-time < 2000){
            val intent_main = Intent(this, MainActivity::class.java)
            startActivity(intent_main)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }
}
