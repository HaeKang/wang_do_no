package com.example.wang_do_no.subway

import android.app.ProgressDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.wang_do_no.MainActivity
import com.example.wang_do_no.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_in_.*
import kotlinx.android.synthetic.main.activity_subway_friend.*
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class subway_friend : AppCompatActivity() {

    val TAG = "phpquerytest"
    val TAG_JSON = "webnautes"
    val TAG_NICK = "nickname"
    val TAG_SUBWAY = "subway"

    var mJsonString: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subway_friend)

        var nickname = intent.getStringExtra("user_nickname")

        main_textview.setText(nickname+"님 환영합니다 \n 알고싶은 사용자의 닉네임을 검색하세요!")

        //버튼 클릭 이벤트
        search_btn.setOnClickListener {
            val task = GetData()
            task.execute(search_nick.getText().toString())
        }

        //text 복사 이벤트
        fun clip() {
            val subway_f = subresult_textview.getText().toString()
            val clipManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("전철역",subway_f)
            clipManager!!.setPrimaryClip(clipData)

            Toast.makeText(this@subway_friend,"전철역 복사 완료",Toast.LENGTH_LONG).show()

        }

        subresult_textview.setOnTouchListener(View.OnTouchListener{view,motionEvent->
            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> clip()
            }

            return@OnTouchListener true
        })


    }

    private inner class GetData : AsyncTask<String, Void, String>() {

        internal lateinit var progressDialog: ProgressDialog
        internal var errorString: String? = null

        override fun onPreExecute() {
            super.onPreExecute()

            progressDialog = ProgressDialog.show(
                this@subway_friend,
                "Please Wait", null, true, true
            )
        }


        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            progressDialog.dismiss()

            var result = result.toString()

            if(result == "닉네임을 다시 확인하세요") {
                Toast.makeText(this@subway_friend, result, Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@subway_friend, "사용자를 찾았습니다!",Toast.LENGTH_LONG).show()
            }

            Log.d(TAG, "response - " + result!!)


            if (result == null) {
            }
            else {
                mJsonString = result
                showResult()
            }
        }


        override fun doInBackground(vararg params: String): String? {

            val searchKeyword1 = params[0]

            val serverURL = "http://" + getString(R.string.IP_ADDRESS) + "/query_friend.php"
            val postParameters = "nickname=$searchKeyword1"


            try {

                val url = URL(serverURL)
                val httpURLConnection = url.openConnection() as HttpURLConnection


                httpURLConnection.readTimeout = 5000
                httpURLConnection.connectTimeout = 5000
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.doInput = true
                httpURLConnection.connect()


                val outputStream = httpURLConnection.outputStream
                outputStream.write(postParameters.toByteArray(charset("UTF-8")))
                outputStream.flush()
                outputStream.close()


                val responseStatusCode = httpURLConnection.responseCode
                Log.d(TAG, "response code - $responseStatusCode")

                val inputStream: InputStream
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.inputStream
                } else {
                    inputStream = httpURLConnection.errorStream
                }


                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val bufferedReader = BufferedReader(inputStreamReader)

                val sb = StringBuilder()
                var line: String

                var nullcheck = true

                while (nullcheck)  {
                    var line = bufferedReader.readLine()
                    if(line != null){
                        sb.append(line)
                    }
                    else{
                        nullcheck = false;
                    }
                }

                bufferedReader.close()
                return sb.toString().trim { it <= ' ' }


            } catch (e: Exception) {

                Log.d(TAG, "InsertData: Error ", e)
                errorString = e.toString()

                return null
            }

        }
    }


    private fun showResult() {
        try {
            val jsonObject = JSONObject(mJsonString)
            val jsonArray = jsonObject.getJSONArray(TAG_JSON)

            val item = jsonArray.getJSONObject(0)

            val nickname = item.getString(TAG_NICK)
            val subway = item.getString(TAG_SUBWAY)

            Log.d("test","여긴옴")
            nickresult_textview.setText(nickname)
            subresult_textview.setText(subway)

        } catch (e: JSONException) {

            Log.d(TAG, "showResult : ", e)

        }

    }

}
