package com.travel.guide

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.travel.guide.databinding.FragmentGuideBinding
import com.travel.guide.fragment.SingleChatFragment

class GuideFragment : Fragment() {

    private var _binding: FragmentGuideBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val guideViewModel =
            ViewModelProvider(this)[GuideViewModel::class.java]

        _binding = FragmentGuideBinding.inflate(inflater, container, false)
        val root: View = binding.root
        _binding?.flList?.let {
            childFragmentManager.beginTransaction().replace(R.id.fl_list, SingleChatFragment())
                .commitNowAllowingStateLoss()
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}