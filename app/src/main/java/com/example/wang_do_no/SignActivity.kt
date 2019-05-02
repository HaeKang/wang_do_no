package com.example.wang_do_no

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign.*

class SignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        SignUp_Btn.setOnClickListener {
            val intent_signup = Intent(this, SignUpActivity::class.java)
            startActivity(intent_signup)
        }
    }
}
