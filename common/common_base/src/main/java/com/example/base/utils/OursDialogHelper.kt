package com.example.base.utils

import android.content.Context
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.base.toast.NormalDialog
import com.example.base.weiget.permission.PermissionDialog
import com.example.base.weiget.permission.PermissionView
import com.example.commponent.ui.dialog.OnClickRightListener
import com.example.peanutmusic.base.R
import com.lxj.xpopup.XPopup

object OursDialogHelper {

    var permissionDialog: PermissionDialog?=null
    var permissionView: PermissionView?=null


    /**
     * 特殊地方试用  这是view形式
     */
    fun showPermission(view:ViewGroup,context: Context){
         permissionView = PermissionView(context)
        val  w= WindowManager.LayoutParams.MATCH_PARENT
        val  h= WindowManager.LayoutParams.WRAP_CONTENT
        val params = FrameLayout.LayoutParams(w, h)
        permissionView?.layoutParams = params
        permissionView?.let {
            view.addView(permissionView)
        }


    }

    /**
     * 特殊地方试用  这是view形式
     */
    fun hidePermission(view:ViewGroup){
        permissionView?.let {
            view.removeViewInLayout(permissionView)
        }
    }
    fun showPermission(context: Context,title:String,content: String){
        permissionDialog= PermissionDialog(context,title,content)
        XPopup.Builder(context)
            .hasStatusBar(false)
            .hasNavigationBar(false)
            .hasShadowBg(false)
            .isDestroyOnDismiss(true)
            .asCustom(permissionDialog)
            .show()
    }
    fun isShow():Boolean?{
      return  permissionDialog?.isShow
    }
    fun hidePermission(){
        permissionDialog?.dismiss()
        permissionDialog=null
    }



    fun showNoPermission(activity: FragmentActivity,title: String,describe:String){
        NormalDialog.Builder()
            .setTitle(title)
            .setTitleTextColor(R.color.black)
            .setSubTitleTextColor(R.color.black)
            .setLeftBg(R.color.bg_fff8f8f8)
            .setLeftTextColor(R.color.black)
            .setRightTextColor(R.color.black)
            .setRightBg(R.color.bg_fff8f8f8)
            .setSubTitle(describe)
            .setLeftText("我知道了")
            .setRightText("前往设置")
            .setRightListener(object : OnClickRightListener {
                override fun onRight(content: String?) {
                    MobileUtils.jumpStartInterface(activity)
                }
            }).build().show(activity.supportFragmentManager, "noPermission")
    }

    /**
     * 服务器维护中
     */
    fun showServiceMaintenanceDialog(fragmentManager: FragmentManager){
        NormalDialog.Builder()
            .setTitle("服务器维护中")
            .setSubTitle("请稍后再尝试重新登录！")
            .setRightText("知道了")
            .setSingleBtn(true)
            .setTitleTextColor(R.color.black)
            .setSubTitleTextColor(R.color.black)
            .setRightTextColor(R.color.black)
            .setRightBg(R.color.bg_fff8f8f8)
            .setRightListener(object : OnClickRightListener {
                override fun onRight(content: String?) {

                }
            }).build().show(fragmentManager, "ServiceMaintenanceDialog")
    }

    /**
     * 网络错误
     */
    fun showNetWorkErrorDialog(fragmentManager: FragmentManager){
        NormalDialog.Builder()
            .setTitle("网络出问题了")
            .setSubTitle("请调整您的网络设置后再尝试操作！")
            .setRightText("知道了")
            .setSingleBtn(true)
            .setTitleTextColor(R.color.black)
            .setSubTitleTextColor(R.color.black)
            .setRightTextColor(R.color.black)
            .setRightBg(R.color.bg_fff8f8f8)
            .setRightListener(object : OnClickRightListener {
                override fun onRight(content: String?) {

                }
            }).build().show(fragmentManager, "NetWorkErrorDialog")
    }



}