package com.example.instagram.presentation.home.profile

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.instagram.R
import com.example.instagram.UserInfo
import com.example.instagram.Utils
import com.example.instagram.databinding.FragmentInstaProfileBinding
import com.example.instagram.presentation.home.InstaMainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InstaProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InstaProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentInstaProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("fragment", "onCreate")
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("fragment", "onCreateView")
        binding = FragmentInstaProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    // fragment 생명주기 공부하기
    // view 에 대한 초기화를 onViewCreated 가 아닌 onResume 단계에서 적용해도 되는건가?
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("fragment", "onViewCreated")
        binding.btnChangeProfile.setOnClickListener {
            val intent = Intent(requireContext(), InstaProfileChangeActivity::class.java) // context 와 requireContext 의 차이가 뭐지?
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("fragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("fragment", "onResume")
        val headers = HashMap<String,String>()
        val sharedpreference = requireContext().getSharedPreferences("user_info",Context.MODE_PRIVATE) // requireActivity vs requireContext?
        val userToken = sharedpreference.getString("current_login_user_token", "null")
        headers.put("Authorization", "token $userToken") // 이거 utils 로 빼기, 너무 코드 중복 반복됨
        Utils().retrofitService.getUserInfo(headers).enqueue(object: Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if(response.isSuccessful) {
                    val response = response.body()
                    // null 체크 해야하나?
                    Glide.with(requireContext()).load(response?.profile?.userImage).into(binding.ivEditProfile)
                } else {
                    Toast.makeText(requireContext(), "else", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Toast.makeText(requireContext(), "onFailure", Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onPause() {
        super.onPause()
        Log.d("fragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("fragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("fragment", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("fragment", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("fragment", "onDetach")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InstaProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InstaProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}