package com.example.appskasir.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appskasir.Database.Menu
import com.example.appskasir.R
import kotlinx.android.synthetic.main.adapter_menu.view.*

class AdapterMenu(private var items: ArrayList<Menu>, private val listener: OnAdapterListener) :
    RecyclerView.Adapter<AdapterMenu.MenuViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.adapter_menu,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val iMenu = items[position]
        holder.view.tv_item_name.text = iMenu.name
        holder.view.tv_item_desc.text = iMenu.desc
        holder.view.tv_item_price.text = "Rp " + iMenu.price

        holder.view.setOnClickListener {
            listener.onRead(iMenu)
        }
        holder.view.iconUpdate.setOnClickListener {
            listener.onUpdate(iMenu)
        }
        holder.view.iconDel.setOnClickListener {
            listener.onDelete(iMenu)
        }

    }

    inner class MenuViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(newList: List<Menu>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onRead(menu: Menu)
        fun onUpdate(menu: Menu)
        fun onDelete(menu: Menu)
    }
}