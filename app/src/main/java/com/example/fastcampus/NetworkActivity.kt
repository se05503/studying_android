package com.example.fastcampus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/*
- NetworkOnMainThreadException
네트워크 작업은 메인 쓰레드에서 진행하면 안됨. (요청을 보내고 서버로부터 응답을 받을때까지 메인쓰레드는 기다리게 되고 사용자의 input을 받을 수 없게 됨)
메인 쓰레드에서는 UI 관련 작업만 해야함
해결 방법: 쓰레드를 따로 만들어서 네트워크 작업을 해야한다.
 */
class NetworkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)

        /*
        클라이언트가 서버에 요청을 보내는 부분
        - urlString: 요청할 주소
        - URL : url 형태로 바꾸기
        - openConnection : 서버와 연결을 맺음. 이때는, FTP, SMTP 등 다양한 종류가 있는데 난 그 중에 Http 를 사용하겠다. (casting)
        - 이외에도 request method, request header 를 작성해야함 (request body 는 꼭 작성할 필요 없음)
         */
        val urlString = "https://mellowcode.org/json/students/"
        val url = URL(urlString)
        val connection : HttpURLConnection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "GET"
        connection.setRequestProperty("Content_Type", "application/json") // request Header

        /*
        response
        - 요청 응답이 200 이라면 응답값을 읽겠다
        connection.inputStream : 응답이 들어있는 곳. byte 코드 (아직 json 으로 변환되지 않는 것)
        UTF-8 : 사람이 알아들을 수 있는 범용적인 언어로 바꾸겠다.
        = 컴퓨터만 알아들을 수 있는 응답(connection.inputStream)을 사람이 알아들을 수 있는 언어(UTF-8)로 바꾸겠다.
        BufferedReader : buffer 공간을 마련해서, 해당 공간이 찰때까지 컴퓨터가 읽고 버퍼가 꽉 차면 사용자에게 알려주는 방식. ex. 1 2 3 읽고 1 2 3 을 알려준다.
         */
        var buffer = ""
        if(connection.responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(
                InputStreamReader(
                    connection.inputStream,
                    "UTF-8"
                )
            )
            buffer = reader.readLine()
        }
    }
}