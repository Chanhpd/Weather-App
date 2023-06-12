package com.example.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item.view.*

class ItemAdapter(var context: Context, var list: ArrayList<Weather>): RecyclerView.Adapter<ItemAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date = itemView.tv_date
        val status = itemView.tv_status
        val image = itemView.imageStatus
        val maxTemp = itemView.tv_max_temp
        val minTemp = itemView.tv_min_temp
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = list.get(position).day
        holder.status.text = list.get(position).status
        holder.maxTemp.text = list.get(position).maxTemp
        holder.minTemp.text = list.get(position).minTemp

        Picasso.get().load("https://openweathermap.org/img/wn/"+list.get(position).image+"@2x.png").into(holder.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }


}