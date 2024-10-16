package com.example.instagram

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
