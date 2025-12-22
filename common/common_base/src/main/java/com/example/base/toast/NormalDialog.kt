package com.example.base.toast

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.base.utils.DisplayUtils
import com.example.base.utils.KeyBoardUtil
import com.example.commponent.ui.dialog.OnClickLeftListener
import com.example.commponent.ui.dialog.OnClickRightListener
import com.example.peanutmusic.base.R
import com.example.peanutmusic.base.databinding.DialogNormalBinding


class NormalDialog : DialogFragment() {

    lateinit var binding: DialogNormalBinding
    private var mOnClickLeftListener: OnClickLeftListener? = null
    private var mOnClickRightListener: OnClickRightListener? = null
    private var mTitle: String = ""
    private var mSubTitle: String = ""
    private var mLeftBtnText: String = "取消"
    private var mRightBtnText: String = "确定"

    private var mTitleTextColor:Int?=null
    private var mSubTitleTextColor:Int?=null

    private var mLeftBtnTextColor:Int?=null
    private var mLeftBgResId:Int?=null
    private var mRightBtnTextColor:Int?=null
    private var mRightBgResId:Int?=null
    private var mBackgroundColor:Int?=null

    /**
     * 单个按钮是用右边的按钮
     */
    private var mSingleBtn:Boolean?=false



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_normal,
            container,
            false
        )

        binding.tvTitle.text = mTitle
        binding.tvSubTitle.text = mSubTitle
        if (mSubTitle.isNullOrEmpty()){
            binding.tvSubTitle.visibility=View.INVISIBLE
            binding.viewHolder.visibility=View.INVISIBLE
        }else{
            binding.viewHolder.visibility=View.GONE
            binding.tvSubTitle.visibility=View.VISIBLE
        }
        if (mSingleBtn==true){
            binding.btnLeft.visibility=View.GONE
            binding.holderA.visibility=View.VISIBLE
            binding.holderB.visibility=View.VISIBLE
        }else{
            binding.holderA.visibility=View.GONE
            binding.holderB.visibility=View.GONE
            binding.btnLeft.visibility=View.VISIBLE
        }
        binding.btnLeft.text = mLeftBtnText
        binding.btnRight.text = mRightBtnText
        binding.btnLeft.setOnClickListener {
            dismissAllowingStateLoss()
            mOnClickLeftListener?.onLeft()
        }
        binding.btnRight.setOnClickListener {
            dismissAllowingStateLoss()
            mOnClickRightListener?.onRight()
        }
        mLeftBtnTextColor?.let {
            binding.btnLeft.setTextColor(it)
        }
        mRightBtnTextColor?.let {
            binding.btnRight.setTextColor(it)
        }
        mTitleTextColor?.let {
            binding.tvTitle.setTextColor(it)
        }
        mSubTitleTextColor?.let {
            binding.tvSubTitle.setTextColor(it)
        }
        mLeftBgResId?.let {
            binding.btnLeft.setBackgroundResource(it)
        }
        mRightBgResId?.let {
            binding.btnRight.setBackgroundResource(it)
        }
        mBackgroundColor?.let {
            binding.flContainer.setBackgroundColor(it)
        }
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        val dialog = Dialog(requireContext(), R.style.ui_style_dialog_center)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // 设置Content前设定
        dialog.setCanceledOnTouchOutside(false) // 外部点击取消
        //不能按返回键退出
        dialog.setCancelable(false)
        val window = dialog.window
        window?.decorView?.setPadding(0, 0, 0, 0)
        val lp = window?.attributes
        lp?.width  =  DisplayUtils.getScreenWidth(context)- DisplayUtils.dp2px(context,64f)// WindowManager.LayoutParams.MATCH_PARENT
        lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp?.gravity = Gravity.CENTER_HORIZONTAL
        window?.setBackgroundDrawableResource(com.example.peanutmusic.base.R.color.transparent)
        window?.attributes = lp
        return dialog
    }

    class Builder{
        private val dialog = NormalDialog()

        fun build(): NormalDialog {
            return dialog
        }

        fun setLeftListener(leftListener: OnClickLeftListener): Builder {
            dialog.mOnClickLeftListener = leftListener
            return this
        }
        fun setRightListener(rightListener: OnClickRightListener):Builder{
            dialog.mOnClickRightListener = rightListener
            return this
        }

        fun setTitle(title:String):Builder{
            dialog.mTitle=title
            return this
        }
        fun setSubTitle(subTitle:String):Builder{
            dialog.mSubTitle=subTitle
            return this
        }

        fun setLeftText(btnText:String):Builder{
            dialog.mLeftBtnText=btnText
            return this
        }
        fun setRightText(btnText:String):Builder{
            dialog.mRightBtnText=btnText
            return this
        }

        fun setTitleTextColor(color:Int):Builder{
            dialog.mTitleTextColor=color
            return this
        }
        fun setSubTitleTextColor(color:Int):Builder{
            dialog.mSubTitleTextColor=color
            return this
        }
        fun setLeftTextColor(color:Int):Builder{
            dialog.mLeftBtnTextColor=color
            return this
        }
        fun setRightTextColor(color:Int):Builder{
            dialog.mRightBtnTextColor=color
            return this
        }

        fun setLeftBg(bgResId:Int):Builder{
            dialog.mLeftBgResId=bgResId
            return this
        }
        fun setRightBg(bgResId:Int):Builder{
            dialog.mRightBgResId=bgResId
            return this
        }
        fun setSingleBtn(singleBtn:Boolean):Builder{
            dialog.mSingleBtn=singleBtn
            return this
        }

        fun setBackgroundColor(color:Int):Builder{
            dialog.mBackgroundColor=color
            return this
        }

    }



    override fun onStop() {
        super.onStop()
        KeyBoardUtil.hideKeyBoard(context, this)
    }

}


