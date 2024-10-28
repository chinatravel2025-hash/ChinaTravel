package com.china.travel

import android.Manifest
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.china.travel.databinding.ActivityMainBinding
import com.coder.vincent.smart_toast.SmartToast
import com.example.base.base.User
import com.example.base.common.v2t.IMCallback
import com.example.base.common.v2t.V2TMessageManager
import com.example.base.common.v2t.im.CommonIMManager
import com.example.base.event.ChatCMDMsgType
import com.example.base.localstore.MMKVConstanst
import com.example.base.localstore.MMKVSpUtils
import com.example.base.msg.i.TUIMessageBean
import com.example.base.utils.LogUtils
import com.example.base.utils.PermissionCheckUtil
import com.example.base.utils.StatusBarUtil
import com.example.router.ARouterPathList
import com.example.base.utils.customDataToBean
import com.example.base.utils.getCmdType
import com.example.base.utils.isIgnore
import com.example.base.utils.setLocalUnread
import com.example.base.utils.toId
import com.example.base.utils.toTUBean
import com.example.base.utils.vibrator
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.permissionx.guolindev.PermissionX
import com.tencent.imsdk.v2.V2TIMAdvancedMsgListener
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMMessage
import com.tencent.imsdk.v2.V2TIMMessageReceipt
import com.tencent.imsdk.v2.V2TIMUserFullInfo
import com.travel.guide.common.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Route(path = ARouterPathList.APP_MAIN)
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var toolbar: Toolbar? = null
    private var statusBarDarkTheme: Boolean = true
        set(value) {
            field = value
            StatusBarUtil.setStatusBarDarkTheme(this, value)
        }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    /*************************************** 权限检查 */
    /**
     * 需要进行检测的权限数组
     */
     var needPermissions: Array<String> = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_PHONE_STATE,
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        StatusBarUtil.immersive(this)
        statusBarDarkTheme = true
        setUpBottomNavigationBar()
        binding.navView.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)
        }
        User.currentUser.observe(this){
            LoginRepository.repository.imLogin()
        }
        imInit()
       XXPermissions.with(this).permission(needPermissions)
           .request(object: OnPermissionCallback{
               override fun onGranted(p0: MutableList<String>, p1: Boolean) {

               }

           })
    }

    private fun setUpBottomNavigationBar() {
        val bottomNavigationView = binding.navView
        val colors = intArrayOf(
            this@MainActivity.getColor(com.example.peanutmusic.base.R.color.txt_FF666666),
            this@MainActivity.getColor(com.example.peanutmusic.base.R.color.txt_00B386)
        )
        val states = arrayOf(
            intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked)
        )
        bottomNavigationView.itemTextColor = ColorStateList(states, colors)
        bottomNavigationView.itemIconTintList = null
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
        // Setup the ActionBar with navController and top level destinations
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_guide, R.id.navigation_profile))
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return  navController.navigateUp(appBarConfiguration)
    }


    private var exitTime: Long = 0
    override fun finish() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            //弹出提示，可以有多种方式
            SmartToast.classic().showInCenter( "Press again to exit")
            exitTime = System.currentTimeMillis()
            return
        }
        super.finish()
    }



    private fun setupToolbar() {
        toolbar = findViewById(R.id.arch_toolbar)
        if (toolbar != null) {
            toolbar?.title = ""
            setSupportActionBar(toolbar)
            toolbar?.apply {
                navigationContentDescription = null
                setNavigationOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
            supportActionBar?.hide()
        }
    }

    private fun imInit(){
        V2TIMManager.getMessageManager()
            .addAdvancedMsgListener(object : V2TIMAdvancedMsgListener() {
                override fun onRecvNewMessage(msg: V2TIMMessage) {
                    onNewMessage(msg)
                }

                override fun onRecvMessageReadReceipts(receiptList: List<V2TIMMessageReceipt>) {

                }

                override fun onRecvMessageRevoked(msgID: String) {
                    //消息撤回

                }

                override fun onRecvMessageRevoked(
                    msgID: String,
                    operateUser: V2TIMUserFullInfo,
                    reason: String
                ) {
                    //消息撤回
                }

                override fun onRecvMessageModified(msg: V2TIMMessage) {

                    //消息被修改
                }
            })
    }

    private fun onNewMessage(msg: V2TIMMessage) {

        val bean = msg.toTUBean(msg.groupID?.isNotEmpty() == true, false)
        IMCallback.sendMessage(bean)
        isMsgPush(bean)

    }

    private fun isMsgPush(bean: TUIMessageBean) {
        CommonIMManager.getConversationById(
            CommonIMManager.convertUserIdToConversationId(
                bean.isGroup,
                bean.userId
            )
        ) {
            CoroutineScope(Dispatchers.Main).launch {
                this@MainActivity.vibrator(70)
            }
            null
        }
    }

}