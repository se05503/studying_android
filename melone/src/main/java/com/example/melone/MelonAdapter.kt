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
import java.io.Serializable
import java.util.*

class MelonAdapter(
    val melonItems: ArrayList<MelonItem>,
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
                val titles = ArrayList<String>()
                val audios = ArrayList<String>()
                melonItems[0].tracks.forEach { trackItem ->
                    titles.add(trackItem.name)
                }
                melonItems[0].tracks.forEach { trackItem ->
                    audios.add(trackItem.audio)
                }
                val intent = Intent(context, DetailActivity::class.java)
//                intent.putExtra("thumbnail", melonItems[0].image) // 서버에서 내려오는 이미지는 한개임
//                intent.putExtra("titles", titles)
//                intent.putExtra("audios", audios)
                // 이전곡, 다음곡을 재생하기 위해서는 리스트 형태로 데이터를 넘겨야 함 (한개만 넘기면 안됨)
                // intent에 List 는 안되지만, ArrayList 는 넣을 수 있다.
                intent.putExtra("melonItems", melonItems as Serializable) // MelonItem 의 property 를 다 분해해서 넘김
                intent.putExtra("current_position",adapterPosition)
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