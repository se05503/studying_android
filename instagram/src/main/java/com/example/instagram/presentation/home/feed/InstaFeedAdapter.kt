package com.example.instagram.presentation.home.feed

import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import java.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram.PostItem
import com.example.instagram.R
import java.text.SimpleDateFormat
import java.util.*

class InstaFeedAdapter(
    val postItems: List<PostItem>,
    val context: Context
): RecyclerView.Adapter<InstaFeedAdapter.InstaFeedViewHolder>() {

    inner class InstaFeedViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val userImage: ImageView
        val userName: TextView
        val postImage: ImageView
        val postHeart: ImageView
        val postLikeCount: TextView
        val postContent: TextView
        val postDate: TextView

        init {
            userImage = view.findViewById(R.id.iv_user_image)
            userName = view.findViewById(R.id.tv_username)
            postImage = view.findViewById(R.id.iv_post_image)
            postHeart = view.findViewById(R.id.iv_heart)
            postLikeCount = view.findViewById(R.id.tv_heart_count)
            postContent = view.findViewById(R.id.tv_content)
            postDate = view.findViewById(R.id.tv_post_date)

            postHeart.setOnClickListener {
                if(postItems[adapterPosition].isHeartClick) {
                    postItems[adapterPosition].isHeartClick = false
                    postHeart.setImageResource(R.drawable.ic_blank_heart)
                    Toast.makeText(context, "좋아요가 해제되었습니다!", Toast.LENGTH_SHORT).show()
                } else {
                    postItems[adapterPosition].isHeartClick = true
                    postHeart.setImageResource(R.drawable.ic_full_heart)
                    Toast.makeText(context, "좋아요가 눌렸습니다!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstaFeedViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.feed_item, parent, false)
        return InstaFeedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postItems.size
    }

    // onBindViewHolder 는 recyclerview 의 item 항목 1개가 뷰에 보일때마다 호출되는구나!
    override fun onBindViewHolder(holder: InstaFeedViewHolder, position: Int) {
        Glide.with(context).load(postItems[position].owner_profile.userImage).into(holder.userImage)
        holder.userName.text = postItems[position].owner_profile.username
        Glide.with(context).load(postItems[position].postImage).into(holder.postImage)
        holder.postLikeCount.text = "${postItems[position].like_count}명이 게시물을 좋아합니다."
        holder.postContent.text = postItems[position].content
        val rawDate = postItems[position].postDate

        val beforeDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val beforeDate = beforeDateFormat.parse(rawDate)

        // HH시 mm분 ss초 → 나중에 필요하면 추가하기
        // 현재 시간이랑 비교해서 "~전" 형식으로 바꾸기
        val afterDateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
        val convertedDate = afterDateFormat.format(beforeDate)

        Log.d("date", convertedDate)

        holder.postDate.text = convertedDate
    }
}