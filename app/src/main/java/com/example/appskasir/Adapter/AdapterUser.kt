package com.example.appskasir.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appskasir.Database.User
import com.example.appskasir.R
import kotlinx.android.synthetic.main.adapter_menu.view.iconDel
import kotlinx.android.synthetic.main.adapter_menu.view.iconUpdate
import kotlinx.android.synthetic.main.adapter_user.view.tv_user_email
import kotlinx.android.synthetic.main.adapter_user.view.tv_user_name
//import kotlinx.android.synthetic.main.adapter_user.view.tv_user_pass

class AdapterUser(private var items: ArrayList<User>, private val listener: OnAdapterListener) :
    RecyclerView.Adapter<AdapterUser.UserViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.adapter_user,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val iUser = items[position]
        holder.view.tv_user_name.text = iUser.name
        holder.view.tv_user_email.text = iUser.email
//        holder.view.tv_user_pass.text = iUser.password

        holder.view.setOnClickListener {
            listener.onRead(iUser)
        }
        holder.view.iconUpdate.setOnClickListener {
            listener.onUpdate(iUser)
        }
        holder.view.iconDel.setOnClickListener {
            listener.onDelete(iUser)
        }

    }

    inner class UserViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(newList: List<User>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onRead(user: User)
        fun onUpdate(user: User)
        fun onDelete(user: User)
    }
}