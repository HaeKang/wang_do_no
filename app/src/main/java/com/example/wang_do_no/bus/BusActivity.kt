package com.example.wang_do_no.bus

import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.wang_do_no.R
import kotlinx.android.synthetic.main.activity_bus.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.lang.Exception
import java.net.URL


class BusActivity : AppCompatActivity() {

    val serviceKey = "77CHsmKRRT0%2Bpp15DA8k8%2BRsbtfmSNrIuSCesO7lrAPp8y5PyuuJR0t4VcKbHwpnFF4euQvt7fKVWs69MBRg1w%3D%3D"
    var stationId : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus)
        StrictMode.enableDefaults()

        var busList: ArrayList<Bus>? = ArrayList()

        Log.d("MSG","왜 안돼돼")

        btn_search.setOnClickListener{

            Log.d("MSG","왜 안돼돼")

            if (editText != null){
                stationId = editText.text.toString()
                Log.d("MSG","왜 안돼")

                var htmlURL = "http://openapi.gbis.go.kr/ws/rest/busarrivalservice/station?serviceKey="+serviceKey+"&stationId="+stationId

                try{

                    Log.d("MSG","왜")

                    val url = URL(htmlURL)

                    val factory = XmlPullParserFactory.newInstance()
                    factory.isNamespaceAware = true
                    val parser = factory.newPullParser()

                    parser.setInput(url.openStream(), "UTF-8")

                    Log.d("MSG","왜 안돼돼")
                    var parserEvent = parser.eventType

                    var currentBus : Bus? = null



                    while (parserEvent != XmlPullParser.END_DOCUMENT){
                        val tagname = parser.name

                        when(parserEvent){

                            XmlPullParser.START_TAG -> {
                                if (tagname.equals("busArrivalList")) {
                                    currentBus = Bus()

                                } else if (tagname.equals("plateNo1")) {
                                    currentBus?.plateNo1 = parser.nextText()
                                    Log.d("test", currentBus?.plateNo1)

                                } else if (tagname.equals("predictTime1")) {
                                    currentBus?.predictTime1 = parser.nextText().toInt()
                                    Log.d("test", currentBus?.predictTime1.toString())

                                } else if (tagname.equals("remainSeatCnt1")) {
                                    currentBus?.remainSeatCnt1 = parser.nextText().toInt()
                                    Log.d("test", currentBus?.remainSeatCnt1.toString())

                                }
                            }

                            XmlPullParser.END_TAG -> {
                                Log.d("text","띠용")
                                if(tagname.equals("busArrivalList")){
                                    currentBus.let { it?.let { it1 -> busList?.add(it1) } }
                                    Log.d("text", busList.toString())
                                }
                            }

                        }
                        parserEvent = parser.next()
                    }

                    Log.d("TEXT","도대체 어디가 안되는 거야")

                    val feel:String = intent.getStringExtra("feeling")

                    when(feel) {
                        "good" -> {
                            busList?.sortBy { it.predictTime1 }
                        }
                        "soso" -> {
                            busList?.sortWith(compareBy<Bus>{it.predictTime1}.thenByDescending{it.remainSeatCnt1})
                        }
                        "bad" -> {
                            busList?.sortByDescending { it.remainSeatCnt1 }
                        }
                    }

                    Log.d("MSG", busList.toString())

                    val busAdapter by lazy {
                        BusAdapter(ArrayList())
                    }

                    DividerItemDecoration(applicationContext, LinearLayoutManager(this).orientation).run {
                        //리사이클러뷰(list_save)에 구분선(Divider) 추가
                        recycle_bus.addItemDecoration(this)
                    }

                    recycle_bus.adapter = busAdapter
                    recycle_bus.layoutManager = LinearLayoutManager(this)

                    busList?.let {
                        busAdapter.busList = it
                        busAdapter.notifyDataSetChanged()
                    }
                    //recycle_bus.adapter = BusAdapter(busList!!)
                    //recycle_bus.layoutManager = LinearLayoutManager(this)


                }catch (e: Exception){
                    e.printStackTrace()
                    Log.d("TEST","에러발생")
                }


            }else{
                Toast.makeText(this,"정류장을 입력해주세요!", Toast.LENGTH_LONG).show()
            }


        }


    }
}