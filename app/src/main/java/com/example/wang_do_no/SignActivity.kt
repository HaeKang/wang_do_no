package com.example.wang_do_no

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign.*

class SignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        SignUp_btn.setOnClickListener {
            val intent_signup = Intent(this, SignUpActivity::class.java)
            startActivity(intent_signup)
        }

       Login_btn.setOnClickListener {
            val inputId = User_id.text.trim().toString()
            val inputPw = User_pw.text.trim().toString()

           if(inputId.isNullOrBlank() || inputPw.isNullOrBlank()) {
               Toast.makeText(this, "ID와 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
           }

        }
    }
}
