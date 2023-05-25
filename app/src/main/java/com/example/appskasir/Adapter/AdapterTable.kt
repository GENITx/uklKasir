package com.example.appskasir.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appskasir.Database.Meja
import com.example.appskasir.R
import kotlinx.android.synthetic.main.adapter_menu.view.*

class AdapterTable(private var items: ArrayList<Meja>, private val listener: OnAdapterListener) :
    RecyclerView.Adapter<AdapterTable.TableViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        return TableViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.adapter_table,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        val iTable = items[position]
        holder.view.tv_item_name.text = iTable.name

        holder.view.iconUpdate.setOnClickListener {
            listener.onUpdate(iTable)
        }
        holder.view.iconDel.setOnClickListener {
            listener.onDelete(iTable)
        }

    }

    inner class TableViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(newList: List<Meja>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onUpdate(meja: Meja)
        fun onDelete(meja: Meja)
    }
}