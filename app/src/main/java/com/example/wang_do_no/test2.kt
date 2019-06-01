package com.example.wang_do_no

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView

import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_test2.*
import org.json.JSONException

class test2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)

        val tempKEY = "1yVQ8nnVJ4S4AxSyhd/sI5Onevlu5YwdvlxOQ9O6iy8"
        val KEY_Final = Uri.encode(tempKEY)
        //val KEY_Final = "1yVQ8nnVJ4S4AxSyhd%2FsI5Onevlu5YwdvlxOQ9O6iy8"

        var odsayService = ODsayService.init(this, KEY_Final)
        // 서버 연결 제한 시간(단위(초), default : 5초)
        odsayService.setReadTimeout(5000)
        // 데이터 획득 제한 시간(단위(초), default : 5초)
        odsayService.setConnectionTimeout(5000)

        // 콜백 함수 구현
         val onResultCallbackListener = object : OnResultCallbackListener {
            // 호출 성공 시 실행
            override fun onSuccess(odsayData: ODsayData, api: API) {
                try {
                    // API Value 는 API 호출 메소드 명을 따라갑니다.
                    if (api == API.BUS_STATION_INFO) {
                        var stationName = odsayData.json.getJSONObject("result").getString("stationName")
                        Log.d("Station name : %s", stationName)
                        textView3.setText(stationName)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            // 호출 실패 시 실행
            override fun onError(i: Int, errorMessage: String, api: API) {
                if (api == API.BUS_STATION_INFO) {
                    Log.d("test", errorMessage)
                    textView3.setText("API : " + api.name + "\n" + KEY_Final + "\n"+ errorMessage)
                }
            }
        }
        // API 호출
        odsayService.requestBusStationInfo("107475", onResultCallbackListener)
    }

}
