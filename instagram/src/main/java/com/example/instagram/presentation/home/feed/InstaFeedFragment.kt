package com.example.instagram.presentation.home.feed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.PostItem
import com.example.instagram.R
import com.example.instagram.Utils
import com.example.instagram.databinding.FragmentInstaFeedBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InstaFeedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InstaFeedFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentInstaFeedBinding

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
        binding = FragmentInstaFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 어느 생명주기에 작성해야하지?

        // 서버 데이터 받아오기
        Utils().retrofitService.getInstaPosts().enqueue(object: Callback<List<PostItem>> {
            override fun onResponse(
                call: Call<List<PostItem>>,
                response: Response<List<PostItem>>
            ) {
                Log.d("server response", response.message())
                if(response.isSuccessful) {
                    val postItems = response.body()
                    binding.recyclerviewFeed.adapter = InstaFeedAdapter(postItems!!, requireContext())
                } else {
                    Toast.makeText(context, "else 문에 걸렸습니다", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<PostItem>>, t: Throwable) {
                Log.d("onFailure", t.message!!)
                Toast.makeText(context, "서버 연결이 불안정합니다", Toast.LENGTH_SHORT).show()
            }

        })

        binding.fab.setOnClickListener {
            binding.recyclerviewFeed.smoothScrollToPosition(0)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InstaFeedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InstaFeedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}