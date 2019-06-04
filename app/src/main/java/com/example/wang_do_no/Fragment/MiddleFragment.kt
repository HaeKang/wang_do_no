package com.example.wang_do_no.Fragment


import android.os.Bundle
import android.os.Handler
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

var stationId_mid = intArrayOf(0, 0)
var stationNum_mid = ""
var stationMidName = ""


class MiddleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_middle, container, false)
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
                        var stationNum = odsayData.json.getJSONObject("result").getString("globalStationCount").toString()
                        var stationMidIndex = stationNum.toInt() / 2

                        var stationMidArray = odsayData.json.getJSONObject("result").getJSONObject("stationSet").
                            getJSONArray("stations").getJSONObject(stationMidIndex)

                        stationMidName = stationMidArray.getString("startName").toString()

                        test.setText(stationMidName)

                        Log.d("test", stationMidName)

                    }

                    //지하철 역 id
                    if(api == API.SEARCH_STATION){
                        var stationNo = odsayData.json.getJSONObject("result")
                            .getJSONArray("station").getJSONObject(0).getString("stationID")

                        var stationLine = odsayData.json.getJSONObject("result")
                            .getJSONArray("station").getJSONObject(0).getString("type")

                        if(stationId_mid[0] == 0){
                            stationId_mid[0] = stationNo.toInt()
                        }else if (stationId_mid[1] == 0){
                            stationId_mid[1] = stationNo.toInt()
                        }

                        if(stationMidName != ""){
                            stationNum_mid = stationLine
                        }

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
        middle_btn.setOnClickListener {

            var startstation = start_text_mid.getText()
            var endstation = end_text_mid.getText()

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

            val delayHandler = Handler()

            delayHandler.postDelayed({
                Log.d("test",stationId_mid[0].toString())
                Log.d("test",stationId_mid[1].toString())


                odsayService.requestSubwayPath("1000",
                    "${stationId_mid[0]}",
                    "${stationId_mid[1]}",
                    "1",
                    onResultCallbackListener)

            }, 1000)

            Log.d("test", stationMidName)

            delayHandler.postDelayed({

                odsayService.requestSearchStation(
                    stationMidName,
                    "1000",
                    "2",
                    "1",
                    "1",
                    "",
                    onResultCallbackListener
                )

                when(stationNum_mid){
                    "0" -> middle_img.setImageResource(R.drawable.check_off)
                    "1" -> middle_img.setImageResource(R.drawable.full_1_2)
                    "2" -> middle_img.setImageResource(R.drawable.full_2_2)
                    "3" -> middle_img.setImageResource(R.drawable.full_3_2)
                    "4" -> middle_img.setImageResource(R.drawable.full_4_2)
                    "5" -> middle_img.setImageResource(R.drawable.full_5_2)
                    "6" -> middle_img.setImageResource(R.drawable.full_6_2)
                    "7" -> middle_img.setImageResource(R.drawable.full_7_2)
                    "8" -> middle_img.setImageResource(R.drawable.full_8_2)
                    "9" -> middle_img.setImageResource(R.drawable.full_9_2)
                    "100" -> middle_img.setImageResource(R.drawable.full_bundang_2)
                    "101" -> middle_img.setImageResource(R.drawable.full_konghang_2)
                    "108" -> middle_img.setImageResource(R.drawable.full_kyeongchun_2)
                    "109" -> middle_img.setImageResource(R.drawable.full_sinbundang_2)
                    "112" -> middle_img.setImageResource(R.drawable.full_kyeong_2)
                }


                stationId_mid[0] = 0
                stationId_mid[1] = 0
                stationMidName = ""
                stationNum_mid = ""
            }, 1000)
        }

    }


}
