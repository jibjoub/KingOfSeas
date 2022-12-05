package com.example.kingofseas.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextClock
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingofseas.Model.Player
import com.example.kingofseas.R
import kotlinx.coroutines.NonDisposableHandle.parent

class PlayerAdapter(var dataSource: List<Player>, val clickListener: (Int) -> Unit): RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(item: Player?)
    }

    private var items: List<Player>? = null
    private var listener: OnItemClickListener? = null

    fun ContentAdapter(items: List<Player>?, listener: OnItemClickListener?) {
        this.items = items
        this.listener = listener
    }

    fun setData(data: List<Player>) {
        dataSource = data
        notifyDataSetChanged()
    }

    class ViewHolder(rowView: View) : RecyclerView.ViewHolder(rowView) {
        val nameText = rowView.findViewById(R.id.name) as TextView
        val healthText = rowView.findViewById(R.id.health) as TextView
        val winPointsText = rowView.findViewById(R.id.win_points) as TextView
        val energyText = rowView.findViewById(R.id.energy) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView : View = LayoutInflater.from(parent.context).inflate(R.layout.player_item_list, parent, false)

        return ViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameText.text = dataSource[position].name
        holder.healthText.text = dataSource[position].health.toString() + "HP"
        holder.winPointsText.text = dataSource[position].winPoint.toString() + "WP"
        holder.energyText.text = dataSource[position].energy.toString() + "energy"
        holder.itemView.setOnClickListener {
            clickListener(position)
        }
    }



}