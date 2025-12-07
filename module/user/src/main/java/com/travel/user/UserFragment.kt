package com.travel.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.base.base.User
import com.example.base.utils.LogUtils
import com.example.base.utils.PrivacyPolicyDialogHelper
import com.travel.user.R
import com.travel.user.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding


    private lateinit var userVM: UserViewModel
    private var privacyPolicyDialog: AlertDialog? = null

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
        
        // 监听显示隐私政策dialog的事件
        userVM.showPrivacyPolicyDialog.observe(viewLifecycleOwner) { show ->
            if (show) {
                showPrivacyPolicyDialog()
                userVM.showPrivacyPolicyDialog.value = false
            }
        }
        
        return binding.root
    }

    private fun showPrivacyPolicyDialog() {
        privacyPolicyDialog = PrivacyPolicyDialogHelper.showPrivacyPolicyDialog(
            fragment = this,
            layoutId = R.layout.dialog_privacy_policy,
            titleId = R.id.tv_title,
            contentId = R.id.tv_content,
            closeButtonId = R.id.btn_close
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        privacyPolicyDialog?.dismiss()
        privacyPolicyDialog = null
    }
}