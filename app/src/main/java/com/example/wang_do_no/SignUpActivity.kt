package com.example.wang_do_no

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_sign.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.net.HttpURLConnection.HTTP_OK
import android.app.ProgressDialog
import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.R.attr.country
import android.R.attr.name
import android.os.AsyncTask.execute



class SignUpActivity : AppCompatActivity() {

    private val IP_ADDRESS = "172.30.18.8"
    private val TAG = "phptest"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


       Sign_up_btn.setOnClickListener {
           var id = Id.getText().toString()
           var pw  = Pw.getText().toString()
           var nickname = Nickname.getText().toString()
           var subway = Subway_name.getText().toString()

           val task = InsertData()
           task.execute("http://$IP_ADDRESS/insert.php", id, pw, nickname, subway)

           Id.setText("")
           Pw.setText("")
           Nickname.setText("")
           Subway_name.setText("")
       }


    }

    internal inner class InsertData : AsyncTask<String, Void, String>() {
        lateinit var progressDialog: ProgressDialog

        override fun onPreExecute() {
            super.onPreExecute()

            progressDialog = ProgressDialog.show(
                this@SignUpActivity,
                "Please Wait", null, true, true
            )
        }


        override fun onPostExecute(result: String) {
            super.onPostExecute(result)

            progressDialog.dismiss()
            Test_Text.setText(result)
            Log.d(TAG, "POST response  - $result")
        }


        override fun doInBackground(vararg params: String): String {

            val serverURL = params[0]
            val postParameters = "id=${params[1]} & pw=${params[2]} & nickname=${params[3]} & subway=${params[4]}"

            try {

                val url = URL(serverURL)
                val httpURLConnection = url.openConnection() as HttpURLConnection


                httpURLConnection.setReadTimeout(5000)
                httpURLConnection.setConnectTimeout(5000)
                httpURLConnection.setRequestMethod("POST")
                httpURLConnection.connect()


                val outputStream = httpURLConnection.getOutputStream()
                outputStream.write(postParameters.toByteArray(charset("UTF-8")))
                outputStream.flush()
                outputStream.close()


                val responseStatusCode = httpURLConnection.getResponseCode()
                Log.d(TAG, "POST response code - $responseStatusCode")

                val inputStream: InputStream
                if (responseStatusCode == HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream()
                } else {
                    inputStream = httpURLConnection.getErrorStream()
                }


                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val bufferedReader = BufferedReader(inputStreamReader)

                val sb = StringBuilder()

                var nullcheck = true

                // while(line = bufferedReader.readLine() != null )

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
                return sb.toString()


            } catch (e: Exception) {

                Log.d(TAG, "InsertData: Error ", e)
                var msg = "Error: " + e.message
                return msg
            }

        }
    }

}

