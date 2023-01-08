package com.example.kingofseas.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingofseas.Model.Dice
import com.example.kingofseas.Model.DiceFace
import com.example.kingofseas.Model.diceFaceToString
import com.example.kingofseas.R

class DiceAdapter (var dataSource: List<Dice>, val clickListener: (Int) -> Unit): RecyclerView.Adapter<DiceAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(item: Dice?)
    }

    private var items: List<Dice>? = null
    private var listener: OnItemClickListener? = null

    fun ContentAdapter(items: List<Dice>?, listener: OnItemClickListener?) {
        this.items = items
        this.listener = listener
    }

    fun setData(data: List<Dice>) {
        dataSource = data
        notifyDataSetChanged()
    }

    class ViewHolder(rowView: View) : RecyclerView.ViewHolder(rowView) {
        val faceText = rowView.findViewById(R.id.tv_face) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView : View = LayoutInflater.from(parent.context).inflate(R.layout.dice_item_list, parent, false)

        return ViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.faceText.text = diceFaceToString(dataSource[position].face)
        holder.itemView.setOnClickListener {
            clickListener(position)
        }
        if (dataSource[position].isSelected) {
            holder.faceText.setBackgroundColor(Color.parseColor("#bbbbbb"))
        }
        else {
            holder.faceText.setBackgroundColor(Color.parseColor("#ffffff"))
        }
    }
}