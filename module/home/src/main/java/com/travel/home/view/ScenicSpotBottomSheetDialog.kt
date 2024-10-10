package com.travel.home.view

import android.app.Dialog
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.base.utils.DisplayUtils

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.travel.home.databinding.BottomSheetScenicSpotDialogBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ScenicSpotBottomSheetDialog(
) : BottomSheetDialogFragment() {
    private var mBinding: BottomSheetScenicSpotDialogBinding? = null
    override fun getTheme(): Int = com.china.travel.widget.R.style.BottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = BottomSheetScenicSpotDialogBinding.inflate(inflater, container, false)
        initAboutContent()
        mBinding?.ivBack?.setOnClickListener {
            dismissAllowingStateLoss()
        }
        return mBinding?.root
    }


    private fun initAboutContent() {

        val sss = "Shanghai is the economic, financial, commercial, and cultural " +
            "center of China. It serves as a major global financial hub," +
            "center of China. It serves as a major global financial hub," +
            "center of China. It serves as a major global financial hub," +
            " boasting the world’s busiest container port. The city i"
        mBinding?.tvAboutContent?.text=sss

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //每次打开都调用该方法 类似于onCreateView 用于返回一个Dialog实例
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupRatio(bottomSheetDialog)
        }
        return dialog
    }


    /**
     * 设置弹窗的最大高度
     * @param bottomSheetDialog
     */
    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
        var coordinatorLayout = bottomSheet?.parent as CoordinatorLayout
        var bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        layoutParams?.height = (DisplayUtils.getScreenHeight(context) * 0.7).toInt()
        bottomSheet.layoutParams = layoutParams
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.isDraggable = false
        coordinatorLayout.parent.requestLayout()
    }

    companion object {
        fun newInstance(): ScenicSpotBottomSheetDialog {
            return ScenicSpotBottomSheetDialog()
        }
    }
}
