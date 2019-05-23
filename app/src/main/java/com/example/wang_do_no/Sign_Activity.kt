package com.example.wang_do_no

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign.*



class Sign_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        var id = edit_username.getText().toString()
        var pw = edit_password.getText().toString()


        SignUp_Btn.setOnClickListener {
            val intent_signup = Intent(this, SignUpActivity::class.java)
            startActivity(intent_signup)
        }

        SignInOk_Btn.setOnClickListener{


        }

    }


}


