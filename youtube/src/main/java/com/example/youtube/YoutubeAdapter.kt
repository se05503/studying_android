package com.example.youtube

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class YoutubeAdapter(
    var videoItems: ArrayList<VideoItem>,
    var inflater: LayoutInflater,
) : RecyclerView.Adapter<YoutubeAdapter.YoutubeViewHolder>() {

    inner class YoutubeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var videoThumbnail: ImageView
        val videoTitle: TextView
        val videoDescription: TextView

        init {
            videoThumbnail = view.findViewById(R.id.iv_thumbnail)
            videoTitle = view.findViewById(R.id.tv_title)
            videoDescription = view.findViewById(R.id.tv_description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeViewHolder {
        val view = inflater.inflate(R.layout.video_item, parent, false)
        return YoutubeViewHolder(view)
    }

    override fun onBindViewHolder(holder: YoutubeViewHolder, position: Int) {
        Glide.with(holder.videoThumbnail) // with 설정이 맞는지 잘 모르겠음 -> 작동은 함
            .load(videoItems[position].thumbnail) // string 을 Uri 로 안바꿔도 된다!
            .into(holder.videoThumbnail)
        holder.videoTitle.text = videoItems[position].title
        holder.videoDescription.text = videoItems[position].content
    }

    override fun getItemCount(): Int {
        return videoItems.size
    }
}