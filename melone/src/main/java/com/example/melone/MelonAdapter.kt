package com.example.melone

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager

class MelonAdapter(
    val melonItems: ArrayList<MelonItem>,
    val context: Context,
    val inflater: LayoutInflater,
    val glide: RequestManager
): RecyclerView.Adapter<MelonAdapter.MelonViewHolder>() {

    inner class MelonViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val thumbnail: ImageView
        val title: TextView
        init {
            thumbnail = view.findViewById(R.id.iv_thumbnail)
            title = view.findViewById(R.id.tv_title)
            view.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("thumbnail", melonItems[adapterPosition].thumbnail)
                intent.putExtra("title", melonItems[adapterPosition].title)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MelonViewHolder {
        val melonView = inflater.inflate(R.layout.melon_item, parent, false)
        return MelonViewHolder(melonView)
    }

    override fun getItemCount(): Int {
        return melonItems.size
    }

    override fun onBindViewHolder(holder: MelonViewHolder, position: Int) {
        holder.title.text = melonItems[position].title
        glide.load(melonItems[position].thumbnail).centerCrop()
            .into(holder.thumbnail)
    }

}