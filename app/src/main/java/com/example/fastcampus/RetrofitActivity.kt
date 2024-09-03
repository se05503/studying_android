package com.example.fastcampus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        // Builder pattern (처음: Builder, 중간: 어떤걸 어떻게 만들건가?, 마지막: build)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mellowcode.org/")
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
        retrofitService.getStudentList().enqueue(object : Callback<ArrayList<StudentFromServer>> {
            override fun onResponse(
                call: Call<ArrayList<StudentFromServer>>,
                response: Response<ArrayList<StudentFromServer>>
            ) {
                if (response.isSuccessful) {
                    val studentList = response.body()
                    findViewById<RecyclerView>(R.id.studentsRecyclerview).apply {
                        this.adapter = StudentListRecyclerViewAdapter(
                            studentList!!, LayoutInflater.from(this@RetrofitActivity)
                        )
                        this.layoutManager = LinearLayoutManager(this@RetrofitActivity)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<StudentFromServer>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}

class StudentListRecyclerViewAdapter(
    var studentList: ArrayList<StudentFromServer>,
    var inflater: LayoutInflater
) : RecyclerView.Adapter<StudentListRecyclerViewAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentName: TextView
        val studentAge: TextView
        val studentIntro: TextView

        init {
            studentName = itemView.findViewById(R.id.student_name)
            studentAge = itemView.findViewById(R.id.student_age)
            studentIntro = itemView.findViewById(R.id.student_intro)
        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.studentName.text = studentList[position].name
        holder.studentAge.text = studentList[position].age.toString()
        holder.studentIntro.text = studentList[position].intro
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = inflater.inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

}