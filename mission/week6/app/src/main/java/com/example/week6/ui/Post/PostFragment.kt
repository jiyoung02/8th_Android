package com.example.week6.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week6.data.PostData
import com.example.week6.databinding.FragmentPostBinding
import com.example.week6.ui.Post.PostRVAdapter
import java.time.LocalDate

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!
    private val dummy = arrayListOf<PostData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val postViewModel =
            ViewModelProvider(this).get(PostViewModel::class.java)

        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initDummy()
        val adapter = PostRVAdapter(dummy)
        binding.rvPost.adapter = adapter
        binding.rvPost.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initDummy(){
        for(i in 1..10){
            val date = LocalDate.now()
            dummy.add(PostData("title${i}", "content content content", 0, 0, " ", "name${i}" ))
        }
    }
}