package com.example.wang_do_no

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast

import com.example.wang_do_no.Sign.SignIn_Activity
import com.example.wang_do_no.bus.FeelBusActivity
import com.example.wang_do_no.subway.Subway_
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var time : Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 상태바 가림
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)

        val auto = getSharedPreferences("auto", 0)

        // 버튼 클릭 이벤트
        Subway_Button.setOnClickListener {

            var id = auto.getString("inputId", null)
            val intent_subway = Intent(this, Subway_::class.java)

            if(id != null) {
                // 자동로그인 상태일때
                var nickname = auto.getString("inputNick", null)
                var subway = auto.getString("inputSub", null)

                intent_subway.putExtra("user_id",id)
                intent_subway.putExtra("user_nickname",nickname)
                intent_subway.putExtra("user_subway",subway)
                startActivity(intent_subway)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()

            } else {
                // 자동로그인 X
                id = intent.getStringExtra("user_id")
                var nickname = intent.getStringExtra("user_nickname")
                var subway = intent.getStringExtra("user_subway")

                intent_subway.putExtra("user_id",id)
                intent_subway.putExtra("user_nickname",nickname)
                intent_subway.putExtra("user_subway",subway)
                startActivity(intent_subway)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
        }

        SignIn_Button.setOnClickListener {
            val intent_sign = Intent(this, SignIn_Activity::class.java)
            startActivity(intent_sign)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        Bus_Button.setOnClickListener {
            val intent_bus = Intent(this, FeelBusActivity::class.java)
            startActivity(intent_bus)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        //로그아웃
        logout.setOnClickListener {
            val autoEditor = auto.edit()

            autoEditor.clear()
            autoEditor.commit()

            Toast.makeText(this@MainActivity,"로그아웃 했습니다",Toast.LENGTH_LONG).show()

        }
    }

    // 뒤로가기 두번 이벤트
    override fun onBackPressed(){
        if(System.currentTimeMillis()-time >=2000){
            time = System.currentTimeMillis()
            Toast.makeText(this@MainActivity, "뒤로 버튼을 한번 더 누르면 종료합니다", Toast.LENGTH_LONG).show()
        } else if(System.currentTimeMillis()-time < 2000){
            finish()
        }
    }

}
