package com.example.fastcampus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/*
Retrofit : 안드로이드에서 기본적으로 제공해주는 라이브러리가 아님 -> 직접 추가해야함
Retrofit 을 이용해 주소에 요청한 다음 학생 데이터를 리스트로 받는 것
baseUrl : 무조건 존재하는 url의 일부
돈을 지불하고 url(domain)을 살 수가 있다. -> baseUrl만 사는 것이다.
baseUrl은 .(점)을 기준으로 판단한다.
ex) https://mellowcode.org/json/students/ 의 baseUrl -> https://mellowcode.org/
addConverterFactory : 서버가 클라이언트에 데이터를 보낼 때, 알아들을 수 없는 데이터를 json 형태로 바꿔야 한다(convert).
누가 convert 할 것인가? -> Gson
Gson : 읽을 수 없는 데이터를 코틀린 클래스의 객체로 바꾸어줌, gson 도 안드로이드에서 기본적으로 제공해주는 라이브러리가 아님 -> 직접 추가해야함
retrofit, gson 은 square 회사에서 만듬
 */

class RetrofitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        // SecurityException resolve
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()

        // Builder pattern (처음: Builder, 중간: 어떤걸 어떻게 만들건가?, 마지막: build)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        /*
        queue(대기줄) : 작업을 하는데에 있어 줄을 세워놓겠다 -> 내 차례가 되면 실행이 됨
        enqueue(queue 에다가 en 줄을 세우겠다)
        callback : call (서버에 요청) 을 했을 때 back(서버의 응답)을 받겠다.
        onResponse : 서버에서부터 응답이 제대로 온 경우
        onFailure : 서버가 내 요청에 응답을 제대로 못한 경우
         */
        val retrofitService = retrofit.create(RetrofitService::class.java)
        retrofitService.getPostList().enqueue(object : Callback<ArrayList<PostFromServer>> {
            override fun onResponse(
                call: Call<ArrayList<PostFromServer>>,
                response: Response<ArrayList<PostFromServer>>
            ) {
                if (response.isSuccessful) {
                    val postList = response.body()
                    findViewById<RecyclerView>(R.id.postsRecyclerview).apply {
                        this.adapter = PostListRecyclerViewAdapter(
                            postList!!, LayoutInflater.from(this@RetrofitActivity)
                        )
                        this.layoutManager = LinearLayoutManager(this@RetrofitActivity)
                        this.addItemDecoration(
                            DividerItemDecoration(
                                this@RetrofitActivity,
                                LinearLayoutManager.VERTICAL
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<PostFromServer>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        findViewById<TextView>(R.id.registerPost).setOnClickListener {
            val post = HashMap<String, Any>()
            // 아이디는 자동적으로 증가하게 설정되어 있어서 굳이 id를 넘겨줄 필요가 없다. (서버에서 알아서 한다)
            post["title"] = "New Post Title"
            post["body"] = "New Post Content"
            retrofitService.registerPost(post).enqueue(object : Callback<PostFromServer> {
                override fun onResponse(
                    call: Call<PostFromServer>,
                    response: Response<PostFromServer>
                ) {
                    Log.d("onResponse", response.message())
                    if (response.isSuccessful) {
                        val post = response.body()
                        Log.d("button 1 response", "post: " + post?.title)
                    }
                }

                override fun onFailure(call: Call<PostFromServer>, t: Throwable) {
                    Log.d("server", "response failure")
                }

            })
        }

        findViewById<TextView>(R.id.easyRegisterPost).setOnClickListener {
            val post = PostFromServer(title = "나는야 휴학생", body = "배달음식 시켜먹고 싶다")
            retrofitService.easyRegisterPost(post).enqueue(object : Callback<PostFromServer> {
                override fun onResponse(
                    call: Call<PostFromServer>,
                    response: Response<PostFromServer>
                ) {
                    val post = response.body()
                    Log.d("button 2 response", "post: "+post?.body)
                }

                override fun onFailure(call: Call<PostFromServer>, t: Throwable) {
                    Log.d("server", "response failure")
                }

            })
        }
    }
}

class PostListRecyclerViewAdapter(
    var postList: ArrayList<PostFromServer>,
    var inflater: LayoutInflater
) : RecyclerView.Adapter<PostListRecyclerViewAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postTitle: TextView
        val postContent: TextView

        init {
            postTitle = itemView.findViewById(R.id.post_title)
            postContent = itemView.findViewById(R.id.post_content)
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.postTitle.text = postList[position].title
        holder.postContent.text = postList[position].body
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = inflater.inflate(R.layout.post_item, parent, false)
        return PostViewHolder(view)
    }

}