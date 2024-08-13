package com.example.base.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.base.utils.InputMonitorHelpUtils
import com.example.base.utils.LogUtils
import com.example.base.utils.StatusBarUtil

abstract class BaseStatusBarActivity : AppCompatActivity() {

    protected lateinit var mBaseBinding: ViewDataBinding
    private var mIvBack: View? = null
    var statusBarDarkTheme: Boolean = true
        set(value) {
            field = value
            supportActionBar?.hide()
            StatusBarUtil.immersive(this)
            StatusBarUtil.setStatusBarDarkTheme(this, value)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBaseBinding =
            DataBindingUtil.inflate(layoutInflater, getLayoutId(), null, false)
        statusBarDarkTheme = true
        initView(savedInstanceState)
        mBaseBinding.root.setOnClickListener {
            hideSoft(it)
        }
        mIvBack= mBaseBinding.root.findViewById(ivBack)
        mIvBack?.setOnClickListener {
            finish()
        }
    }

    open fun initView(savedInstanceState: Bundle?) {
    }

    abstract fun getLayoutId(): Int

    @get:IdRes
    protected abstract val ivBack: Int

    protected fun hideSoft(view: View): List<View> {
        val allchildren: MutableList<View> = ArrayList()
        if (view is ViewGroup) {
            val vp = view
            for (i in 0 until vp.childCount) {
                val viewchild: View = vp.getChildAt(i)
                allchildren.add(viewchild)
                //再次调用本身（递归）
                allchildren.addAll(hideSoft(viewchild))
                if (viewchild is EditText) {
                    InputMonitorHelpUtils.hideSoftInput(this, viewchild)
                }

            }
        }
        return allchildren
    }

}