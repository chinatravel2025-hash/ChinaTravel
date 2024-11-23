package com.travel.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.base.base.User
import com.example.base.utils.LogUtils
import com.travel.user.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding


    private lateinit var userVM: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_user, null, false)
        binding.lifecycleOwner = this
        userVM = ViewModelProvider(this)[UserViewModel::class.java]
        binding.vm = userVM
        userVM.userInfo.value=User.currentUser.value
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}