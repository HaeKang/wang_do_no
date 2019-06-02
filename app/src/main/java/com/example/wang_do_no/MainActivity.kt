package com.example.wang_do_no

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.example.wang_do_no.Sign.SignIn_Activity
import com.example.wang_do_no.subway.Subway_
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(intent.hasExtra("user_nickname")) {
            textView.setText(intent.getStringExtra("user_nickname") + "님 환영합니다!")
        } else{
            textView.setText("회원님 환영합니다!")
        }

        var id = intent.getStringExtra("user_id")
        var pw = intent.getStringExtra("user_pw")
        var nickname = intent.getStringExtra("user_nickname")
        var subway = intent.getStringExtra("user_subway")


        // 버튼 클릭 이벤트
        Subway_Button.setOnClickListener {
            val intent_subway = Intent(this, Subway_::class.java)
            intent_subway.putExtra("user_id",id)
            intent_subway.putExtra("user_pw",pw)
            intent_subway.putExtra("user_nickname",nickname)
            intent_subway.putExtra("user_subway",subway)
            startActivity(intent_subway)
        }

        SignIn_Button.setOnClickListener {
            val intent_sign = Intent(this, SignIn_Activity::class.java)
            startActivity(intent_sign)
        }

        Bus_Button.setOnClickListener {
            val intent_bus = Intent(this, BusActivity::class.java)
            intent_bus.putExtra("user_id",id)
            intent_bus.putExtra("user_pw",pw)
            intent_bus.putExtra("user_nickname",nickname)
            intent_bus.putExtra("user_subway",subway)
            startActivity(intent_bus)
        }



    }


}
