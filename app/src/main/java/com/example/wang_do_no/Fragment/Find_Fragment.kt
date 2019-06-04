package com.example.wang_do_no.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.wang_do_no.R
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import kotlinx.android.synthetic.main.fragment_find_.*
import kotlinx.android.synthetic.main.fragment_middle.*
import org.json.JSONException
import kotlin.concurrent.thread
import android.os.Handler




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

var stationId = intArrayOf(0, 0)         // ID 코드
var stationNum  = arrayOf("", "") // 몇호선

class Find_Fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.example.wang_do_no.R.layout.fragment_find_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val KEY_Final = "1yVQ8nnVJ4S4AxSyhd/sI5Onevlu5YwdvlxOQ9O6iy8"
        var odsayService = ODsayService.init(this!!.getActivity()!!, KEY_Final)

        // 서버 연결 제한 시간(단위(초), default : 10초)
        odsayService.setReadTimeout(10000)
        // 데이터 획득 제한 시간(단위(초), default : 10초)
        odsayService.setConnectionTimeout(10000)



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

                        TimeSum.setText("소요시간 \n" + time.toString())

                        Log.d("test", "경로찾기옴")

                    }

                    //지하철 역 id
                    if(api == API.SEARCH_STATION){
                        var stationarray = odsayData.json.getJSONObject("result")
                            .getJSONArray("station").getJSONObject(0)

                        var stationNo = odsayData.json.getJSONObject("result")
                            .getJSONArray("station").getJSONObject(0).getString("stationID")

                        var stationLine = odsayData.json.getJSONObject("result")
                            .getJSONArray("station").getJSONObject(0).getString("type")


                        Log.d("test",stationarray.toString())

                        if(stationId[0] == 0 && stationNum[0] == ""){
                            stationId[0] = stationNo.toInt()
                            stationNum[0] = stationLine
                        }else{
                            stationId[1] = stationNo.toInt()
                            stationNum[1] = stationLine
                        }

                        Log.d("test","if문 끝난 후 ${stationId[0]}  ${stationId[1]}")
                        Log.d("test","if문 끝난 후 ${stationNum[0]}  ${stationNum[1]}")
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            // 호출 실패 시 실행
            override fun onError(i: Int, errorMessage: String, api: API) {
                Log.d("test", errorMessage)

            }
        }

        // API 호출

        find_btn.setOnClickListener {

            var startstation = start_text.getText().toString()
            var endstation = end_text.getText().toString()

            Log.d("test",startstation+endstation)


            odsayService.requestSearchStation(
                startstation,
                "1000",
                "2",
                "1",
                "1",
                "127.0363583:37.5113295",
                onResultCallbackListener
            )


            odsayService.requestSearchStation(
                endstation,
                "1000",
                "2",
                "1",
                "1",
                "",
                onResultCallbackListener
            )

            val delayHandler = Handler()

            delayHandler.postDelayed({
                Log.d("test",stationId[0].toString())
                Log.d("test",stationId[1].toString())



                odsayService.requestSubwayPath(
                    "1000",
                    "${stationId[0]}",
                    "${stationId[1]}",
                    "1",
                    onResultCallbackListener
                )

                startName.setText(startstation)
                endName.setText(endstation)

                when(stationNum[0]){
                    "0" -> start_img.setImageResource(R.drawable.check_off)
                    "1" -> start_img.setImageResource(R.drawable.full_1_2)
                    "2" -> start_img.setImageResource(R.drawable.full_2_2)
                    "3" -> start_img.setImageResource(R.drawable.full_3_2)
                    "4" -> start_img.setImageResource(R.drawable.full_4_2)
                    "5" -> start_img.setImageResource(R.drawable.full_5_2)
                    "6" -> start_img.setImageResource(R.drawable.full_6_2)
                    "7" -> start_img.setImageResource(R.drawable.full_7_2)
                    "8" -> start_img.setImageResource(R.drawable.full_8_2)
                    "9" -> start_img.setImageResource(R.drawable.full_9_2)
                    "100" -> start_img.setImageResource(R.drawable.full_bundang_2)
                    "101" -> start_img.setImageResource(R.drawable.full_konghang_2)
                    "108" -> start_img.setImageResource(R.drawable.full_kyeongchun_2)
                    "109" -> start_img.setImageResource(R.drawable.full_sinbundang_2)
                    "112" -> start_img.setImageResource(R.drawable.full_kyeong_2)
                }

                when(stationNum[1]){
                    "0" -> end_img.setImageResource(R.drawable.check_off)
                    "1" -> end_img.setImageResource(R.drawable.full_1_2)
                    "2" -> end_img.setImageResource(R.drawable.full_2_2)
                    "3" -> end_img.setImageResource(R.drawable.full_3_2)
                    "4" -> end_img.setImageResource(R.drawable.full_4_2)
                    "5" -> end_img.setImageResource(R.drawable.full_5_2)
                    "6" -> end_img.setImageResource(R.drawable.full_6_2)
                    "7" -> end_img.setImageResource(R.drawable.full_7_2)
                    "8" -> end_img.setImageResource(R.drawable.full_8_2)
                    "9" -> end_img.setImageResource(R.drawable.full_9_2)
                    "100" -> end_img.setImageResource(R.drawable.full_bundang_2)
                    "101" -> end_img.setImageResource(R.drawable.full_konghang_2)
                    "108" -> end_img.setImageResource(R.drawable.full_kyeongchun_2)
                    "109" -> end_img.setImageResource(R.drawable.full_sinbundang_2)
                    "112" -> end_img.setImageResource(R.drawable.full_kyeong_2)
                }



                Log.d("test","마지막" + stationId[0].toString())
                Log.d("test","마지막"+ stationId[1].toString())
                Log.d("test","마지막 ${stationNum[0]}  ${stationNum[1]}")

                stationId[0] = 0
                stationId[1] = 0
                stationNum[0] = ""
                stationNum[1] = ""


            }, 2000)

        }
    }
}
