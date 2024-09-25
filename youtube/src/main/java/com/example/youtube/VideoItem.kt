package com.example.youtube

// 변수 이름은 postman 에서 받아오는 키(key) 값과 동일해야한다. 변수의 타입도 value와 동일해야 한다.
data class VideoItem(
    val id: Int,
    val title: String,
    val content: String,
    val video: String, // 확장자: mp4
    val thumbnail: String // 확장자: png
)