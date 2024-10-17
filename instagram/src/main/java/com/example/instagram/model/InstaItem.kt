package com.example.instagram

import com.google.gson.annotations.SerializedName

class InstaItem {
}

data class  SignupToken(
    val id: Int?, // 얘는 언제 필요한거지?
    val username: String?, // 얘는 언제 필요한거지?
    val token: String? // 얘가 제일 중요한 것 같음
)

data class LoginToken(
    val token: String
)

data class PostItem(
    @SerializedName("id") val postId: Int, // id 같은 경우에는 어떻게 활용해야 할 지 감이 아직 안옴
    @SerializedName("created") val postDate: String, // 인스타 어플 따라하기
    val content: String, // 강의에서 쓴 변수
    @SerializedName("image") val postImage: String, // 강의에서 쓴 변수
    val like_count: Int, // 인스타 어플 참고
    val owner_profile: PostOwnerInfo
)

data class PostOwnerInfo(
    @SerializedName("id") val userId: Int, // 아직 어떻게 활용해야할 지 감이 안옴
    val username: String, // 강의에서 쓴 변수
    @SerializedName("image") val userImage: String, // 강의에서 쓴 변수
    val user: Int // user id 랑 같은 속성인 것 같음. 안쓸 것 같음.
)
