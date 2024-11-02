package com.travel.user.ui.support

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.aws.bean.util.GsonUtil
import com.china.travel.widget.bottomsheet.TakePhotoDialog
import com.china.travel.widget.permission.PermissionInterceptor
import com.example.base.base.BaseStatusBarActivity
import com.example.base.utils.LogUtils
import com.example.router.ARouterPathList
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker
import com.travel.user.R
import com.travel.user.databinding.UserActivitySupportBinding
import com.travel.user.vm.UserSupportVM

@Route(path = ARouterPathList.USER_SUPPORT)
class UserSupportActivity : BaseStatusBarActivity() {

    private lateinit var binding: UserActivitySupportBinding
    private lateinit var mVM: UserSupportVM
    override val ivBack: Int
        get() = R.id.iv_back

    override fun getLayoutId(): Int {
        return R.layout.user_activity_support
    }

    private val imagePickerLauncher = registerImagePicker { images ->
        if (images.isNotEmpty()) {
            val path = images[0].path

        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mVM = ViewModelProvider(this)[UserSupportVM::class.java]
        binding = mBaseBinding as UserActivitySupportBinding
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.vm = mVM
        binding.ac = this

    }


    fun uploadPic() {
        val takePhotoDialog = TakePhotoDialog(object :
            TakePhotoDialog.ImagePickerAction {
            override fun takePhoto() {
                requestCameraPermission()
            }

            override fun pickImage() {
                requestPhotoPermission()
            }

        })
        takePhotoDialog.show(supportFragmentManager, "TakePhotoDialog")

    }

    private fun requestPhotoPermission() {

        val permissionList =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                listOf(Permission.READ_MEDIA_IMAGES,
                    Permission.READ_MEDIA_VIDEO,
                    Permission.READ_MEDIA_AUDIO)
            } else {
                listOf(Permission.READ_EXTERNAL_STORAGE)
            }

        XXPermissions.with(this)
            .permission(permissionList)
            .interceptor(PermissionInterceptor("相册授权说明", "需要您授权开启相册，以便于我们能选择图片上传。"))
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    if (allGranted) {
                        val config = ImagePickerConfig(
                            isLightStatusBar = true,
                            isMultipleMode = true,
                            isShowNumberIndicator = false,
                            maxSize = 1,
                        )
                        imagePickerLauncher.launch(config)
                    }
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {

                }
            })
    }

    private fun requestCameraPermission() {
        val permissionList = mutableListOf(Permission.CAMERA)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            permissionList.add(Permission.WRITE_EXTERNAL_STORAGE)
        }
        XXPermissions.with(this)
            .permission(permissionList)
            .interceptor(PermissionInterceptor("相机授权说明", "需要您授权开启相机，以便于我们能拍照上传。"))
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    if (allGranted) {
                        val config = ImagePickerConfig(
                            isCameraOnly = true
                        )
                        imagePickerLauncher.launch(config)
                    }
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {

                }
            })
    }


}