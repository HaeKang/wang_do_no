package com.example.wang_do_no.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.wang_do_no.R
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import kotlinx.android.synthetic.main.fragment_find_.*
import org.json.JSONException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

var stationId = intArrayOf(0, 0)


class Find_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val KEY_Final = "1yVQ8nnVJ4S4AxSyhd/sI5Onevlu5YwdvlxOQ9O6iy8"
        var odsayService = ODsayService.init(this!!.getActivity()!!, KEY_Final)

        // 서버 연결 제한 시간(단위(초), default : 5초)
        odsayService.setReadTimeout(5000)
        // 데이터 획득 제한 시간(단위(초), default : 5초)
        odsayService.setConnectionTimeout(5000)



        // 콜백 함수 구현
        val onResultCallbackListener = object : OnResultCallbackListener {
            // 호출 성공 시 실행
            override fun onSuccess(odsayData: ODsayData, api: API) {
                try {

                    //최단거리
                    if(api == API.SUBWAY_PATH) {
                        var startStation = odsayData.json.getJSONObject("result").getString("globalStartName")
                        var endStation = odsayData.json.getJSONObject("result").getString("globalEndName")
                        var time = odsayData.json.getJSONObject("result").getString("globalTravelTime")

                        textView2.setText(startStation + " " + endStation + " " + time)
                        Log.d("test", "여긴 옴")
                    }

                    //지하철 역 id
                    if(api == API.SEARCH_STATION){
                        var stationNo = odsayData.json.getJSONObject("result")
                            .getJSONArray("station").getJSONObject(0).getString("stationID")

                        if(stationId[0] == 0){
                            stationId[0] = stationNo.toInt()
                            Log.d("test",stationId[0].toString())
                        }else{
                            stationId[1] = stationNo.toInt()
                            Log.d("test",stationId[1].toString())
                        }
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            // 호출 실패 시 실행
            override fun onError(i: Int, errorMessage: String, api: API) {
                Log.d("test", errorMessage)
                textView2.setText("API : " + api.name + "\n" + KEY_Final + "\n"+ errorMessage)
            }
        }

        // API 호출
        var startstation = start_text.getText()
        var endstation = end_text.getText()

        odsayService.requestSearchStation(
            startstation.toString(),
            "1000",
            "2",
            "1",
            "1",
            "127.0363583:37.5113295",
            onResultCallbackListener
        )


        odsayService.requestSearchStation(
            endstation.toString(),
            "1000",
            "2",
            "1",
            "1",
            "",
            onResultCallbackListener
        )

        find_btn.setOnClickListener {

            odsayService.requestSubwayPath("1000", "${stationId[0]}","${stationId[1]}", "1", onResultCallbackListener)

            stationId[0] = 0
            stationId[1] = 0

        }
    }
}
