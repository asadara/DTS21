package com.example.githubuserrview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter2(private val listResult: List<String>): RecyclerView.Adapter<Adapter2.Holder2>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder2(LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false))

    override fun onBindViewHolder(hh: Holder2, position: Int) {
        hh.tvUserIdRetro.text = listResult[position]
        hh.tvNameRetro.text = listResult[position]
        hh.ivAvatar.setImageResource(position)
    }

    override fun getItemCount() = listResult.size

    class Holder2(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivAvatar: ImageView = itemView.findViewById(R.id.iv_photo)
        var tvUserIdRetro: TextView = itemView.findViewById(R.id.tv_userId_retro)
        var tvNameRetro: TextView = itemView.findViewById(R.id.tv_name_retro)
    }
}
