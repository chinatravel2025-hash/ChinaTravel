package com.china.travel.web.ui

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.china.travel.web.R
import com.china.travel.web.databinding.ModuleWebActivityMainBinding
import com.example.base.base.App
import com.example.base.utils.StatusBarUtil
import com.example.router.ARouterPathList



@Route(path = ARouterPathList.WEB_HOME)
class WebActivity : AppCompatActivity() {


    @JvmField
    @Autowired
    var url: String? = null

    @JvmField
    @Autowired
    var title: String? = null

    @JvmField
    @Autowired
    var hideActionBar: Boolean? = false
    private lateinit var binding: ModuleWebActivityMainBinding
    private lateinit var viewModel: WebViewModel
    var statusBarDarkTheme: Boolean = true
        set(value) {
            field = value
            StatusBarUtil.setStatusBarDarkTheme(this, value)
        }



    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.module_web_activity_main, null, false)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[WebViewModel::class.java]
        binding.lifecycleOwner = this
        binding.vm = viewModel
        StatusBarUtil.immersive(this)
        statusBarDarkTheme = true
        ARouter.getInstance().inject(this)

        if (hideActionBar == true) {
            binding.llTop.visibility = View.GONE
        } else {
            binding.llTop.visibility = View.VISIBLE
        }
    /*    if (!isOursH5()){
            binding.llTop.visibility = View.VISIBLE
        }*/

        binding.ivBack.setOnClickListener {
            if (hasBack()) goBack() else finish()
        }

        // 设置标题
        if (title != null && title?.isNotEmpty() == true) {
            viewModel.title.value = title
        } else {
            viewModel.title.value = ""
        }
        //binding.blurview.setBlurRadius(100F)
        binding.wbWeb.settings.run {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            domStorageEnabled = true
            allowFileAccess = true
            allowContentAccess = true
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
            javaScriptCanOpenWindowsAutomatically = true
            textZoom = 100
            mediaPlaybackRequiresUserGesture = false
            cacheMode = WebSettings.LOAD_NO_CACHE

            userAgentString = "$userAgentString Travel-V${App.getVersion()}"
        }
        binding.wbWeb.webChromeClient = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                viewModel.isProgressShowHide.value = newProgress != 100
                viewModel.progress.value = newProgress
            }
        }

        val cookieManager = CookieManager.getInstance()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(binding.wbWeb, true)
        }

        url?.let {
           // if (isOursH5()) {
              //  binding.wbWeb.addJavascriptInterface(JSBridgeUtil(this, binding.wbWeb, binding.llTop, this.javaClass.name), "native")
            // 支持加载本地 assets 文件
            if (it.startsWith("file:///android_asset/") || it.startsWith("file://android_asset/")) {
                binding.wbWeb.loadUrl(it)
            } else {
                binding.wbWeb.loadUrl(it)
            }
        }
        if (title == null || title?.isEmpty() == true) {
            title = ""
        }



    }





/*    fun isTravelH5(): Boolean {
        if (url?.contains(API().hostH5())==true ){
            return true
        }
        return false
    }*/

    fun hasBack(): Boolean {
        binding.wbWeb.let {
            return it.canGoBack()
        }
        return false
    }

    fun goBack() {
        binding.wbWeb.goBack()
    }





    override fun onBackPressed() {
        if (hasBack()) goBack() else finish()
    }


}
