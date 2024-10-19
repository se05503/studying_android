package com.example.instagram.presentation.home.post

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagram.R
import com.example.instagram.databinding.FragmentInstaPostBinding

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
            ActivityResultContracts.StartActivityForResult()) {
            val inputStream = requireContext().contentResolver.openInputStream(it.data!!.data!!)
            val bitmap = BitmapFactory.decodeStream(inputStream, null, bitmapOption)
            binding.ivUserGalleyImage.setImageBitmap(bitmap)
        }

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        requestGalleyLauncher.launch(intent)

        binding.tvPostCancel.setOnClickListener {
            // 무슨 상황이 발생해야 하는거지..?
        }

        binding.btnPost.setOnClickListener {
            // 서버에 전달해야함 -> retrofit 필요
            // 사용자 토큰(누가 게시물을 썼는가), 이미지, 내용, 작성날짜..?
        }
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
}