package com.china.travel.widget.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.china.travel.widget.R
import com.china.travel.widget.databinding.DialogTakePhotoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TakePhotoDialog(
    private val listener:ImagePickerAction
) : BottomSheetDialogFragment(), View.OnClickListener {
    private var mBinding: DialogTakePhotoBinding? = null
    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogTakePhotoBinding.inflate(inflater, container, false)
        mBinding?.apply {
            tvCancel.setOnClickListener(this@TakePhotoDialog)
            tvTakePhone.setOnClickListener(this@TakePhotoDialog)
            selectAlbum.setOnClickListener(this@TakePhotoDialog)
        }
        return mBinding?.root
    }





    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_take_phone ->{
                listener.takePhoto()
                dismissAllowingStateLoss()
            }
            R.id.select_album ->{
                listener.pickImage()
                dismissAllowingStateLoss()
            }
            R.id.tv_cancel ->{
                dismissAllowingStateLoss()
            }

        }
    }





    interface ImagePickerAction {
        fun takePhoto()
        fun pickImage()

    }
}
