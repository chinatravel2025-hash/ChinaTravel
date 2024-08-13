package com.travel.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.travel.community.databinding.FragmentCommunityBinding
import org.greenrobot.eventbus.EventBus

class CommunityFragment : Fragment() {

    private lateinit var binding: FragmentCommunityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_community, null, false)
        binding.lifecycleOwner = this
        val communityViewModel  = ViewModelProvider(this)[CommunityViewModel::class.java]
        return  binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
    }
}