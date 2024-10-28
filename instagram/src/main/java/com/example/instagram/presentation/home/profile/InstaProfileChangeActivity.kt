package com.example.instagram.presentation.home.profile

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.Utils
import com.example.instagram.databinding.ActivityInstaProfileChangeBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class InstaProfileChangeActivity : AppCompatActivity() {
    lateinit var binding: ActivityInstaProfileChangeBinding
    lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstaProfileChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val requestGalleyLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            imageUri = it.data?.data!!
            val inputStream = contentResolver.openInputStream(imageUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.ivEditProfile.setImageBitmap(bitmap) // 굳이 bitmap 으로 바꿔야하나? 그냥 바로 uri 를 세팅하면 안되나? 책에서는 bitmap 으로 바꿨어서 이렇게 했는데
        }

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        requestGalleyLauncher.launch(intent)

        // 여전히 업로드하는데 어느 정도 딜레이가 발생한다.
        binding.btnChangeProfile.setOnClickListener {
            // 서버에 등록
            // 필요한 준비물 = 현재 로그인한 userId, token, 업로드할 이미지
            // userId
            val sharedPreference = getSharedPreferences("user_info", Context.MODE_PRIVATE)
            val userId = sharedPreference.getInt("current_login_user_id", -1)
            // token
            val userToken = sharedPreference.getString("current_login_user_token", "none")
            val header = HashMap<String, String>()
            header.put("Authorization", "token $userToken")
            // 업로드 이미지 ( Uri -> File -> RequestBody -> ? )
            val imageFile = getImageFile(imageUri) // File?
            val requestFile = RequestBody.create(
                MediaType.parse(contentResolver.getType(imageUri)),
                imageFile
            ) // RequestBody
            val requestBody = MultipartBody.Part.createFormData(
                "image",
                imageFile?.name,
                requestFile
            ) // 첫번째 파라미터에 뭘 적는거지?
            val requestUserId = RequestBody.create(MultipartBody.FORM, userId.toString())

            // Callback import 할 때 okhttp 말고 retrofit 으로 import 해야한다.
            Utils().retrofitService.changeUserProfile(userId, header, requestBody, requestUserId).enqueue(object :
                Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
//                        Toast.makeText(applicationContext, "프로필 수정이 성공했습니다!", Toast.LENGTH_SHORT).show() -> applicationContext 이라서 토스트가 안뜨는건가?
                        Log.d("response:", "successful")
                        onBackPressed() // finish 랑 차이점?
                    } else {
//                        Toast.makeText(applicationContext, "else", Toast.LENGTH_SHORT).show()
                        Log.d("response:", "is not successful(else)")
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
//                    Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_SHORT).show() // activity 내부인데 왜 activity 랑 context 가 제대로 안뜨지?
                    Log.d("response:", "onFailure")
                }
            })
        }
    }

    // cursor를 이용하여 uri 에서 해당 이미지의 file 을 얻어오는 함수
    fun getImageFile(uri: Uri): File? {
        var uri = uri
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        if (uri == null) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        // uri 는 파일의 위치임. 위치까지 이동하려면 커서를 이용해야함. 이미지를 얻어와서 파일로 바꿔줌
        var cursor: Cursor? = contentResolver.query(
            uri,
            projection,
            null,
            null,
            MediaStore.Images.Media.DATE_MODIFIED + " desc"
        )
        if (cursor == null || cursor.columnCount < 1) return null
        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val path = cursor.getString(columnIndex)
        if (cursor != null) {
            cursor.close()
            cursor = null
        }
        return File(path)
    }
}