package com.example.kingofseas.Adapter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
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
        val image = rowView.findViewById(R.id.iv_icon_player) as ImageView
        val layoutItem = rowView.findViewById<ConstraintLayout>(R.id.layout_item)
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
        holder.healthText.text = dataSource[position].health.toString()
        holder.winPointsText.text = dataSource[position].winPoint.toString()
        holder.energyText.text = dataSource[position].energy.toString()
        holder.image.setImageResource(dataSource[position].Icon)
        //Grey out the player if he/she is dead or not
        if (dataSource[position].isAlive)
            holder.layoutItem.setBackgroundColor(Color.parseColor("#FFFFFF"))
        else
            holder.layoutItem.setBackgroundColor(Color.parseColor("#AAAAAA"))
        holder.itemView.setOnClickListener {
            clickListener(position)
        }
    }



}