package com.example.wang_do_no.Sign

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_in_.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject
import org.json.JSONException
import android.app.ProgressDialog
import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager

import com.example.wang_do_no.MainActivity
import com.example.wang_do_no.R


class SignIn_Activity : AppCompatActivity() {

    val TAG = "phpquerytest"
    val TAG_JSON = "webnautes"
    val TAG_ID = "id"
    val TAG_PW = "pw"
    val TAG_NICK = "nickname"
    val TAG_SUBWAY = "subway"

    var mJsonString: String? = null

    var time:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 상태바 가림
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_sign_in_)

        //자동로그인
        val auto = getSharedPreferences("auto", 0)
        val loginId = auto.getString("inputId", null)
        val loginPw = auto.getString("inputPw",null)

        if(loginId != null && loginPw != null){
            Toast.makeText(this@SignIn_Activity, "자동로그인 상태입니다 로그아웃 후 이용해주세요", Toast.LENGTH_LONG).show()
            val auto_intent = Intent(this@SignIn_Activity, MainActivity::class.java)
            startActivity(auto_intent)
            finish()
        }


        SignUp_Btn.setOnClickListener {
            val intent_signup = Intent(this, SignUpActivity::class.java)
            startActivity(intent_signup)
            finish()
        }

        SignInOk_Btn.setOnClickListener {
            val task = GetData()
            task.execute(edit_username.getText().toString(), edit_password.getText().toString())
        }

    }


    private inner class GetData : AsyncTask<String, Void, String>() {

        internal lateinit var progressDialog: ProgressDialog
        internal var errorString: String? = null

        override fun onPreExecute() {
            super.onPreExecute()

            progressDialog = ProgressDialog.show(
                this@SignIn_Activity,
                "Please Wait", null, true, true
            )
        }


        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            progressDialog.dismiss()

            var result = result.toString()

            if(result == "아이디와 패스워드를 다시 확인하세요") {
                Toast.makeText(this@SignIn_Activity, result, Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@SignIn_Activity, "로그인에 성공했습니다!",Toast.LENGTH_LONG).show()
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
            val searchKeyword2 = params[1]

            val serverURL = "http://" + getString(R.string.IP_ADDRESS) + "/query_test.php"
            val postParameters = "id=$searchKeyword1 &pw=$searchKeyword2"


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

            val id = item.getString(TAG_ID)
            val pw = item.getString(TAG_PW)
            val nickname = item.getString(TAG_NICK)
            val subway = item.getString(TAG_SUBWAY)

            //자동로그인
            if(autologin.isChecked()) {
                val auto = getSharedPreferences("auto", 0)
                val autoEditor = auto.edit()

                autoEditor.putString("inputId", id)
                autoEditor.putString("inputPw", pw)
                autoEditor.putString("inputNick", nickname)
                autoEditor.putString("inputSub", subway)

                autoEditor.commit()
            }

            //로그인 완료
            val intent_signin = Intent(this@SignIn_Activity, MainActivity::class.java)
            intent_signin.putExtra("user_id",id)
            intent_signin.putExtra("user_pw",pw)
            intent_signin.putExtra("user_nickname",nickname)
            intent_signin.putExtra("user_subway",subway)
            startActivity(intent_signin)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        } catch (e: JSONException) {

            Log.d(TAG, "showResult : ", e)

        }

    }

    override fun onBackPressed(){
        if(System.currentTimeMillis()-time >=2000){
            time = System.currentTimeMillis()
            Toast.makeText(this@SignIn_Activity, "뒤로 버튼을 한번 더 누르면 메인으로 갑니다", Toast.LENGTH_LONG).show()
        } else if(System.currentTimeMillis()-time < 2000){
            val intent_main = Intent(this, MainActivity::class.java)
            startActivity(intent_main)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }

}


