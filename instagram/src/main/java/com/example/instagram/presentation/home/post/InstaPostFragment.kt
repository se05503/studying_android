package com.example.instagram.presentation.home.post

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagram.R
import com.example.instagram.Utils
import com.example.instagram.databinding.FragmentInstaPostBinding
import com.example.instagram.presentation.home.InstaMainActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InstaPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InstaPostFragment : Fragment() {
    // [★] 적절한 프래그먼트 생명주기에 작성하지 않아서 그런지 인텐트 화면보다 binding 화면이 먼저 보임
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentInstaPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInstaPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    // context vs requirecontext
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bitmapOption = BitmapFactory.Options()
        bitmapOption.inSampleSize = 4

        val requestGalleyLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            val imageUri = it.data?.data
            val inputStream = requireContext().contentResolver.openInputStream(it.data!!.data!!)
            val bitmap = BitmapFactory.decodeStream(inputStream, null, bitmapOption)
            binding.ivUserGalleyImage.setImageBitmap(bitmap)

            // uri 는 "파일"이 아닌 이미지의 "주소"이다. 서버에 이미지를 보낼려면 "주소"가 아닌 "파일"이 필요하다.
            // 서버 통신을 위한 파라미터 준비
            val imageFile = getImageFile(imageUri!!)
            val requestFile = RequestBody.create(
                MediaType.parse((activity as InstaMainActivity).contentResolver.getType(imageUri)),
                imageFile
            )
            val body = MultipartBody.Part.createFormData("image", imageFile?.name, requestFile)
            val header = HashMap<String, String>()
            // requireContext vs requireActivity ?
            val sharedPreference =
                requireContext().getSharedPreferences("user_info", Context.MODE_PRIVATE)
            val userToken = sharedPreference.getString(
                "current_login_user_token",
                "user token is not exist"
            )
            header.put("Authorization", "token $userToken")

            // 여기다가 코드를 작성하는게 맞나?
            binding.btnPost.setOnClickListener {
                val content =
                    RequestBody.create(MultipartBody.FORM, binding.etPostContent.text.toString())
                // 서버 통신 작업
                Utils().retrofitService.uploadInstaPost(header, body, content).enqueue(object :
                    Callback<Any> {
                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "포스트가 정상적으로 업로드 되었습니다!",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.etPostContent.setText("")
                        } else {
                            Toast.makeText(requireContext(), "else", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        Toast.makeText(requireContext(), "onFailure", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        requestGalleyLauncher.launch(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InstaPostFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InstaPostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
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
        var cursor: Cursor? = (activity as InstaMainActivity).contentResolver.query(
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