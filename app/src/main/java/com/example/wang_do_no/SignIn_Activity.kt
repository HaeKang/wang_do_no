package com.example.wang_do_no

import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_in_.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SignIn_Activity : AppCompatActivity() {

    private val TAG = "phpquerytest"

    private val TAG_JSON = "webnautes"
    private val TAG_ID = "id"
    private val TAG_NAME = "name"
    private val TAG_ADDRESS = "country"

    private val mTextViewResult: TextView? = null
    var mArrayList: ArrayList<HashMap<String, String>>? = null
    var mJsonString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_)

        var id = edit_username.getText().toString()
        var pw = edit_password.getText().toString()
        var nickname = ""
        var  mTextViewResult : TextView? = textView




        SignUp_Btn.setOnClickListener {
            val intent_signup = Intent(this, SignUpActivity::class.java)
            startActivity(intent_signup)
        }


        SignInOk_Btn.setOnClickListener{

            mArrayList?.clear()
            val task = GetData()
            task.execute(id, pw)

            mArrayList = ArrayList()

            // 로그인 성공 시
            //Toast.makeText(this@SignIn_Activity,"로그인 성공", Toast.LENGTH_LONG).show()
           // val intent_main = Intent(this, MainActivity::class.java)
            // startActivity(intent_main)
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
            mTextViewResult?.setText(result)

            if (result == null) {
                mTextViewResult?.setText(errorString)
            } else {

                mJsonString = result
            }
        }


        override fun doInBackground(vararg params: String): String? {

            val searchKeyword1 = params[0]
            val searchKeyword2 = params[1]

            val serverURL = "http://"+ getString(com.example.wang_do_no.R.string.IP_ADDRESS) +"query.php"
            val postParameters = "id=$searchKeyword1 & pw=$searchKeyword2"


            try {

                val url = URL(serverURL)
                val httpURLConnection = url.openConnection() as HttpURLConnection


                httpURLConnection.setReadTimeout(5000)
                httpURLConnection.setConnectTimeout(5000)
                httpURLConnection.setRequestMethod("POST")
                httpURLConnection.setDoInput(true)
                httpURLConnection.connect()


                val outputStream = httpURLConnection.getOutputStream()
                outputStream.write(postParameters.toByteArray(charset("UTF-8")))
                outputStream.flush()
                outputStream.close()


                val responseStatusCode = httpURLConnection.getResponseCode()
                Log.d(TAG, "response code - $responseStatusCode")

                val inputStream: InputStream
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream()
                } else {
                    inputStream = httpURLConnection.getErrorStream()
                }


                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val bufferedReader = BufferedReader(inputStreamReader)

                val sb = StringBuilder()
                var line: String

                var nullcheck = true

                while (nullcheck) {
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

}
