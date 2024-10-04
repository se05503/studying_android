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
    val melonItems: List<MelonItem>,
    val context: Context,
    val inflater: LayoutInflater,
    val glide: RequestManager
): RecyclerView.Adapter<MelonAdapter.MelonViewHolder>() {

    inner class MelonViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val thumbnail: ImageView
        val title: TextView
        val playButton: ImageView
        init {
            thumbnail = view.findViewById(R.id.iv_thumbnail)
            title = view.findViewById(R.id.tv_title)
            playButton = view.findViewById(R.id.iv_play)
            playButton.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("thumbnail", melonItems[0].image)
                intent.putExtra("title", melonItems[0].tracks[adapterPosition].name)
                intent.putExtra("audio",melonItems[0].tracks[adapterPosition].audio)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MelonViewHolder {
        val melonView = inflater.inflate(R.layout.melon_item, parent, false)
        return MelonViewHolder(melonView)
    }

    override fun getItemCount(): Int {
        return melonItems[0].tracks.size
    }

    override fun onBindViewHolder(holder: MelonViewHolder, position: Int) {
        holder.title.text = melonItems[0].tracks[position].name
        glide.load(melonItems[0].image).centerCrop()
            .into(holder.thumbnail)
    }

}