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

class YoutubeAdapter(
    var videoItems: ArrayList<VideoItem>,
    var inflater: LayoutInflater,
    var contentResolver: ContentResolver
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
        // 여기만 해결되면 서버에서 응답이 잘 오는지 확인할 수 있음
        // 필요한 데이터만 뷰에 세팅해주면 됨
        // 이미지는 glide 안쓰고 한번 작성해보기
        val thumbnailString = videoItems[position].thumbnail
        val thumbnailUri = Uri.parse(thumbnailString)
        var inputStream = contentResolver.openInputStream(thumbnailUri)
        var bitmap = BitmapFactory.decodeStream(inputStream)
        holder.videoThumbnail.setImageBitmap(bitmap)
        holder.videoTitle.text = videoItems[position].title
        holder.videoDescription.text = videoItems[position].content
    }

    override fun getItemCount(): Int {
        return videoItems.size
    }
}