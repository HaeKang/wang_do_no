package com.example.wang_do_no

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up_end.*

class SignUpEnd : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_end)

        intent.getStringExtra(SignUpActivity.USER_NICKNAME).run {
            text_user.text = this + "님!"
        }
    }
}
