package com.example.wang_do_no.subway

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.example.wang_do_no.Fragment.Find_Fragment
import com.example.wang_do_no.Fragment.MiddleFragment
import com.example.wang_do_no.R

import kotlinx.android.synthetic.main.activity_find__subway.*

class Find_Subway : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_find -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame_main,
                    Find_Fragment() ).commit()
            }

            R.id.navigation_middle -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame_main,
                    MiddleFragment()).commit()

            }

        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find__subway)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction().replace(R.id.frame_main,Find_Fragment()).commit()
    }
}
