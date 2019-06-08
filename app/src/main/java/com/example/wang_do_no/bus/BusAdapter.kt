package com.example.wang_do_no.bus

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wang_do_no.R
import kotlinx.android.synthetic.main.bus_item.view.*

class BusAdapter(var busList: ArrayList<Bus>): RecyclerView.Adapter<BusAdapter.ItemViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.bus_item,parent,false)
        return ItemViewHolder(rootView)
    }

    override fun getItemCount() = busList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItems(busList[position])
    }

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindItems(bus: Bus){
            itemView.show_plateNo1.text = bus.plateNo1
            itemView.show_remainSeatCnt1.text = bus.remainSeatCnt1.toString()
            itemView.show_predictTime1.text = bus.predictTime1.toString()
        }
    }
}