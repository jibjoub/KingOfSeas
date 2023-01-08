package com.example.kingofseas.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingofseas.Model.Card
import com.example.kingofseas.R

class CardAdapter(var dataSource: List<Card>, val clickListener: (Int) -> Unit): RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(item: Card?)
    }

    private var items: List<Card>? = null
    private var listener: OnItemClickListener? = null

    fun ContentAdapter(items: List<Card>?, listener: OnItemClickListener?) {
        this.items = items
        this.listener = listener
    }

    fun setData(data: List<Card>) {
        dataSource = data
        notifyDataSetChanged()
    }

    class ViewHolder(rowView: View) : RecyclerView.ViewHolder(rowView) {
        val image = rowView.findViewById(R.id.iv_card) as ImageView
        val title = rowView.findViewById(R.id.tv_title_card) as TextView
        val description = rowView.findViewById(R.id.tv_description) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView : View = LayoutInflater.from(parent.context).inflate(R.layout.card_item_list, parent, false)

        return ViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(dataSource[position].image)
        holder.title.text = dataSource[position].title
        holder.description.text = dataSource[position].description
        holder.itemView.setOnClickListener {
            clickListener(position)
        }
    }
}

