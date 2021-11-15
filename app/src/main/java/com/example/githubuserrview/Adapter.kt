package com.example.githubuserrview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//dalam 1 class Adapter ini, bisa memiliki beberapa viewHolder
class Adapter1(private val listPackage: ArrayList<Package>) : RecyclerView.Adapter<Adapter1.Holder1>() {

    //berikut adalah fungsi interface untuk melakukan trigger action di MainActivity
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    //tempat menaruh layout yg dipakai (layout1.xml)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder1 {
        val tampil1: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return Holder1(tampil1)
    }

    //untuk mengambil data dari constructor di Class; juga untuk meletakkan aksi(listener,onClick dll)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder1, position: Int) {
        val (username, surename, photo, location, repository) = listPackage[position]
        holder.ivAvatar.setImageResource(photo)
        holder.tvUserId.text = username
        holder.tvName.text = "$surename, $location, $repository repos..."


        //fungsi OnClick pada kelas adapter :
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listPackage[holder.adapterPosition])
        }
    }

    //interface untuk menginisiasi fungsi action dari Class Adapter ke MainActivity
    interface OnItemClickCallback {
        fun onItemClicked(data: Package)
    }

    //untuk menentukan ukuran/jumlah item data yg "INGIN" ditampilkan
    override fun getItemCount(): Int = listPackage.size

    //perhatikan ada berapa Id dalam layoutnya (layout_item.xml)
    class Holder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivAvatar: ImageView = itemView.findViewById(R.id.iv_photo)
        var tvUserId: TextView = itemView.findViewById(R.id.tv_userId)
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
    }
}

