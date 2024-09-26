package com.example.youtube

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager

class YoutubeAdapter(
    var videoItems: ArrayList<VideoItem>,
    var inflater: LayoutInflater,
    val glide: RequestManager,
    val context: Context // 상세 페이지 이동할 때 필요
) : RecyclerView.Adapter<YoutubeAdapter.YoutubeViewHolder>() {

    inner class YoutubeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var videoThumbnail: ImageView
        val videoTitle: TextView
        val videoDescription: TextView

        init {
            videoThumbnail = view.findViewById(R.id.iv_thumbnail)
            videoTitle = view.findViewById(R.id.tv_title)
            videoDescription = view.findViewById(R.id.tv_description)

            // click listener
            // View, context, adapterPosition
            view.setOnClickListener {
                val intent = Intent(context, VideoActivity::class.java)
                intent.putExtra("video_url", videoItems[adapterPosition].video)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeViewHolder {
        val view = inflater.inflate(R.layout.video_item, parent, false)
        return YoutubeViewHolder(view)
    }

    override fun onBindViewHolder(holder: YoutubeViewHolder, position: Int) {
        glide.load(videoItems[position].thumbnail).centerCrop().into(holder.videoThumbnail)
        holder.videoTitle.text = videoItems[position].title
        holder.videoDescription.text = videoItems[position].content
    }

    override fun getItemCount(): Int {
        return videoItems.size
    }
}