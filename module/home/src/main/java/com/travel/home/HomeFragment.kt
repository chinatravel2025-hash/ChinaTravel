package com.travel.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ours.ui.home.HomeViewModel
import com.travel.home.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        _binding?.textHome?.setOnClickListener {

        }
            test()
        return root
    }

    private fun test(){
        // 原始列表
        val originalList: MutableList<String> = ArrayList()
        originalList.add("A")
        originalList.add("B")
        originalList.add("C")
        originalList.add("D")
        originalList.add("E")
        //originalList.add("F")
        val chunked = originalList.chunked(2)

        for (group in chunked) {
            Log.d("llllll","chunked=${group}")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}