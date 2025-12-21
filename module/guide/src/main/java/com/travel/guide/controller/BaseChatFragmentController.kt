package com.travel.guide.controller

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.alibaba.android.arouter.launcher.ARouter
import com.china.travel.widget.permission.PermissionInterceptor
import com.example.base.base.App
import com.example.base.common.v2t.V2TMessageManager
import com.example.base.common.v2t.V2TMessageManager.doubleCheckerMap
import com.example.base.common.v2t.im.CommonIMManager
import com.example.base.msg.i.TUIMessageBean
import com.example.base.msg.i.TUIMessageBean.Companion.TYPE_MSG_TIME
import com.example.base.toast.ToastHelper
import com.example.base.utils.*
import com.example.base.weiget.LongTimerAndMoveButton
import com.example.base.weiget.NoAnimationRecyclerView
import com.example.base.weiget.OursLinearLayoutManager
import com.example.router.ARouterPathList
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.nguyenhoanglam.imagepicker.helper.DeviceHelper
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker
import com.permissionx.guolindev.PermissionX
import com.tencent.imsdk.v2.V2TIMAdvancedMsgListener
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMMessage
import com.tencent.imsdk.v2.V2TIMUserFullInfo
import com.travel.guide.common.V2TMsgHomeCacheManager
import com.travel.guide.fragment.SingleChatFragment
import com.travel.guide.viewmodel.ChatViewModel
import com.travel.guide.viewmodel.SingleChatViewModel
import com.travel.guide.weiget.IMChatAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference


/**
 * 单聊界面Controller
 * li yi
 */
open class BaseChatFragmentController constructor(
    fragment: SingleChatFragment,
    viewModel: ChatViewModel,
    adapter: IMChatAdapter,
    userId: String?,
    layoutManager: OursLinearLayoutManager
) {

    protected val TAG = this.javaClass.simpleName
    protected val CAMERA_REQUEST_CODE = 999


    protected var mFragmentWeakReference: WeakReference<SingleChatFragment>? = null
    protected var mViewModel: ChatViewModel
    protected var mAdapter: IMChatAdapter
    protected var mId: String = ""
    var mConversationId: String = ""
    protected var softInputListener: ViewTreeObserver.OnGlobalLayoutListener? = null
    var mLayoutManager: OursLinearLayoutManager
    protected lateinit var rvList: NoAnimationRecyclerView
    protected lateinit var flRoot: View
    protected lateinit var ltb: LongTimerAndMoveButton
    protected lateinit var etContent: EditText
    private var startMessage: V2TIMMessage? = null
    private var msgId: String = ""
    protected var isHasNextPage = true
    private var loadingMsgID = HashSet<String>()
    private var screenHeight = 0

    private var isLoadMore = false
    private val maxLoadMore = 0.7f

    companion object {
        const val TYPE_EYE = "eye"
        const val TYPE_CLOUD = "cloud"

        @JvmStatic
        fun newInstance(
            activity: SingleChatFragment,
            mViewModel: SingleChatViewModel,
            adapter: IMChatAdapter,
            userId: String?,
            layoutManager: OursLinearLayoutManager
        ) = BaseChatFragmentController(
            activity,
            mViewModel,
            adapter,
            userId,
            layoutManager
        )
    }

    init {
        screenHeight = DisplayUtils.getScreenHeight(fragment.context)
        mFragmentWeakReference = WeakReference(fragment)
        mId = userId ?: ""
        mViewModel = viewModel
        mAdapter = adapter
        mLayoutManager = layoutManager
    }

    open fun initList() {
        addAdvancedMsgListener()
        mAdapter.msgModules.clear()
        loadList()

        rvList.setOnScrollListener(object : OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == SCROLL_STATE_DRAGGING) {
                    mAdapter.isLragging = true
                } else if (newState == SCROLL_STATE_IDLE) {
                    if (mAdapter.isLragging && isRecyclerViewAtTop()) {
                        //loadList()
                    }
                    mAdapter.isLragging = false
                }
                LogUtils.w(
                    "setOnScrollListener",
                    "onScrollStateChanged--$newState!!!isRecyclerViewAtTop--${isRecyclerViewAtTop()}"
                )
            }

            /**
             * Callback method to be invoked when the RecyclerView has been scrolled. This will be
             * called after the scroll has completed.
             *
             *
             * This callback will also be called if visible item range changes after a layout
             * calculation. In that case, dx and dy will be 0.
             *
             * @param recyclerView The RecyclerView which scrolled.
             * @param dx           The amount of horizontal scroll.
             * @param dy           The amount of vertical scroll.
             */
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if ((mViewModel.newMsgCount.value ?: 0) > 0) {
                    if (mLayoutManager.findFirstVisibleItemPosition() <= (mViewModel.newMsgStartIndex.value
                            ?: 0)
                    ) {
                        //如果滚动范围大于目标，则放弃.
                        mViewModel.newMsgCount.value = 0
                        LogUtils.i(TAG, "新消息被设置了")
                    }
                }
                val findFirstVisibleItemPosition =
                    mLayoutManager.findFirstVisibleItemPosition()

                LogUtils.w(
                    TAG,
                    "findFirstVisibleItemPosition--${findFirstVisibleItemPosition},,mAdapter.msgModules.size-${mAdapter.msgModules.size}"
                )
                if (!isLoadMore) {
                    if (findFirstVisibleItemPosition <= 5) {
                        //if (findFirstVisibleItemPosition >= mAdapter.msgModules.size * maxLoadMore) {
                        isLoadMore = true
                        LogUtils.i(TAG, "start more")
                        loadList()
                        isLoadMore = false
                    }
                }
            }
        })
    }

    fun scrollToDown(size: Int) {
        mLayoutManager.smoothScrollToPosition(
            rvList,
            RecyclerView.State(),
            size,
            true
        )

        LogUtils.w("scrollToPosition", "mLayoutManager.smoothScrollToPosition(${size})")
    }

    fun scrollToDown(size: Int, isLowSmooth: Boolean) {
        if (rvList.isActionDownMove) {
            return
        }
        mLayoutManager.smoothScrollToPosition(
            rvList,
            RecyclerView.State(),
            size,
            isLowSmooth
        )
    }

    fun bindEditText() {
        etContent.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event -> //判断是否是“完成”键
            if (actionId == EditorInfo.IME_ACTION_SEND && v.text.isNotEmpty()) {
                //隐藏软键盘
                val imm: InputMethodManager = v
                    .context.getSystemService(
                        Context.INPUT_METHOD_SERVICE
                    ) as InputMethodManager
                if (imm.isActive) {
                    imm.hideSoftInputFromWindow(
                        v.applicationWindowToken, 0
                    )
                }
                onSend()

                return@OnEditorActionListener true
            }
            false
        })
        etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mViewModel.isInputType.value = (s?.toString()?.length ?: 0) > 0

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }


    private val imagePickerLauncher =
        mFragmentWeakReference?.get()?.activity?.registerImagePicker { images ->
            if (images.isNotEmpty()) {
                val path = images[0].path
                upload(arrayListOf(path))
            }
        }

    open fun onSend(ext: String? = null) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return
        }
        if (etContent.text?.toString()?.isNotEmpty() == true) {
            V2TMessageManager.sendText(
                mId,
                mViewModel.nickname.value ?: "",
                mViewModel.isGroup.value == true,
                mViewModel.isTopic.value == true,
                etContent.text?.toString() ?: "",
                ext ?: "",
                mViewModel.quoteMsgId.value ?: ""
            )
            etContent.post {
                etContent.setText("")
                onCancelQuoteClick()
            }
        } else {
            ToastHelper.createToastToFail(App.getContext(), "Please enter the content!")
        }
    }

    open fun onSendPhone() {

    }

    open fun onSendImg(type: Int) {
        mFragmentWeakReference?.get()?.activity?.run {
            if (type == 0) {
                val permissionList = mutableListOf(Permission.CAMERA)
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    permissionList.add(Permission.WRITE_EXTERNAL_STORAGE)
                }
                XXPermissions.with(this)
                    .permission(permissionList)
                    .interceptor(
                        PermissionInterceptor(
                            "Camera Authorization Instructions",
                            "We need your permission to enable the camera so we can take photos and upload them."
                        )
                    )
                    .request(object : OnPermissionCallback {
                        override fun onGranted(
                            permissions: MutableList<String>,
                            allGranted: Boolean
                        ) {
                            if (allGranted) {
                                val config = ImagePickerConfig(
                                    isCameraOnly = true
                                )
                                imagePickerLauncher?.launch(config)
                            }
                        }

                        override fun onDenied(
                            permissions: MutableList<String>,
                            doNotAskAgain: Boolean
                        ) {

                        }
                    })
            } else {
                val config = ImagePickerConfig(
                    isLightStatusBar = true,
                    isMultipleMode = true,
                    isShowNumberIndicator = false,
                    maxSize = 1,
                )
                imagePickerLauncher?.launch(config)
            }

        }
    }


    @SuppressLint("Recycle")
    open fun onFragmentResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mFragmentWeakReference?.get()?.activity?.run {
            when (requestCode) {
                SingleChatFragment.CAMERA_REQUEST_CODE -> if (resultCode == AppCompatActivity.RESULT_OK) {
                    /*try {
                        data?.data?.run {
                            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                            val cursor = contentResolver?.query(
                                this,
                                filePathColumn,
                                null,
                                null,
                                null
                            );
                            cursor?.moveToFirst()
                            val columnIndex = cursor?.getColumnIndex(filePathColumn[0]) ?: 0
                            upload(arrayListOf(cursor?.getString(columnIndex) ?: ""))
                        }
                    } catch (e: Exception) {

                    }*/
                }

                SingleChatFragment.QUICK_CAPTURE_CODE -> {
                    if (resultCode == AppCompatActivity.RESULT_OK) {
                        /*QuickPhotoResult.quickPhotoResult(this) {
                            if (it?.isNotEmpty() == true) {
                                upload(arrayListOf(it))
                            }
                        }*/
                    }
                }
            }

        }
    }

    private fun upload(paths: ArrayList<String>) {
        mFragmentWeakReference?.get()?.activity?.run {
            try {
                paths.let {
                    it.forEach { path ->
                        if (path.endsWith(".mp4") || path.endsWith(".wmv") || path.endsWith(
                                ".avi"
                            )
                        ) {
                            V2TMessageManager.sendVideo(
                                mId,
                                mViewModel.nickname.value ?: "",
                                mViewModel.isGroup.value == true,
                                mViewModel.isTopic.value == true,
                                path
                            )

                        } else {
                            V2TMessageManager.sendImg(
                                mId,
                                mViewModel.nickname.value ?: "",
                                mViewModel.isGroup.value == true,
                                mViewModel.isTopic.value == true,
                                path
                            )
                        }

                    }
                }

            } catch (e: java.lang.Exception) {
            }

        }
    }

    open fun onHeadClick(userId: String) {
    }

    open fun onContentClick(userId: String, position: Int, list: ArrayList<TUIMessageBean>) {
        list[position].apply {
            message?.let { emMessage ->
                when (emMessage.elemType) {
                    V2TIMMessage.V2TIM_ELEM_TYPE_TEXT -> {
                        /*XRouter.getRouter()
                            .create(UnityNavigator::class.java)
                            .toTextDetailFragment(
                                emMessage.textElem?.text ?: ""
                            )
                            .startActivity()*/
                    }

                    V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO, V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE -> {
                        V2TMessageManager.resourceMessages.clear()
                        var endIndex = -1
                        var startIndex = 0
                        list.forEachIndexed { index, vo ->
                            vo.message?.let { message ->
                                when (message.elemType) {
                                    V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO, V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE -> {
                                        V2TMessageManager.resourceMessages.add(message)
                                        if (endIndex == -1) {
                                            if (emMessage.msgID == message.msgID) {
                                                endIndex = startIndex
                                            } else {
                                                startIndex++
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        /*XRouter.getRouter()
                            .create(UnityNavigator::class.java)
                            .toAlbumFragment(this.userId, endIndex, true)
                            .startActivity()*/
                    }

                    V2TIMMessage.V2TIM_ELEM_TYPE_SOUND -> {
                        V2TMessageManager.clearBubbleToUser(
                            this.userId,
                            emMessage.msgID
                        ) {}

                    }

                    V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM -> {
                        emMessage.customDataToBean()?.let {
                            if (it.tripsId?.isNotEmpty() == true) {
                                ARouter.getInstance().build(ARouterPathList.HOME_TRIP_DETAIL)
                                    //.withOptionsCompat(option)
                                    .withLong("tripId", it.tripsId?.toLong() ?: 0L)
                                    .withInt("isTravelTrip", 1)
                                    .navigation(SmartActivityUtils.getTopActivity())
                            }
                        }
                    }
                }
            }
        }
    }

    open fun onContentLongClick(
        userId: String,
        position: Int,
        list: ArrayList<TUIMessageBean>,
        view: View
    ) {
        val bean = list[position]
        /*bean.message?.apply {
            XPopup.Builder(view.context)
                .isTouchThrough(false)
                .atView(view)
                .hasStatusBar(false)
                .hasNavigationBar(false)
                .hasShadowBg(false)
                .asCustom(
                    ChatBubbleAttachPopup(view.context,
                        typeList(this, mViewModel.isGroup.value == true, list[position].isCheck),
                        view,
                        object : ChatPopupItemClick {
                            override fun onToText() {
                                when (elemType) {
                                    V2TIMMessage.V2TIM_ELEM_TYPE_SOUND -> {
                                        val ext = cloudCustomDataToBean()?.ours_voice_text ?: ""
                                        if (ext.isNotEmpty()) {
                                            mAdapter.audioToText(
                                                position,
                                                isSelf,
                                                this@apply
                                            )
                                        } else {
                                            ToastHelper.createToastToFail(
                                                App.getContext(),
                                                "Failed to convert to text"
                                            )
                                        }
                                    }
                                }
                            }

                            override fun onWeiShare() {
                                com.alis.commponent_widget.navigator.PageSkipController.navigatorOursVideoSharePage(
                                    localCustomDataToBean().ours_file_path ?: ""
                                )
                            }

                            override fun onCopy() {
                                when (elemType) {
                                    V2TIMMessage.V2TIM_ELEM_TYPE_TEXT -> {
                                        MobileUtils.copyContentToClipboard(
                                            textElem?.text ?: ""
                                        )
                                        ToastHelper.createToastToTxt(view.context.getString(R.string.copy_ok))
                                    }
                                }
                            }

                            override fun onShare() {
                                V2TMessageManager.doubleCheckerMode.value = false
                                V2TMessageManager.doubleCheckerMap.value?.put(msgID, this@apply)
                                PageSkipController.navigatorShareChooseFriendPage()
                            }

                            override fun onChoice() {
                                V2TMessageManager.doubleCheckerMode.value = true
                                V2TMessageManager.doubleCheckerMap.value?.put(msgID, this@apply)
                                V2TMessageManager.doubleCheckerName.value =
                                    mViewModel.nickname.value ?: ""
                            }

                            override fun onCreateTopic() {
                                onCreateTopic(this@apply)
                            }

                            override fun onWithWard() {
                                V2TMessageManager.revokeMessage(mConversationId, this@apply)
                            }

                            override fun onDelete() {
                                mAdapter.removeDataAt(mId, position, true)
                            }

                            override fun onQuote() {
                                mViewModel.isQuote.value = true
                                mViewModel.quoteMsgId.value = msgID
                                mViewModel.isVoiceType.value = 0
                                var name = "Me"
                                if (userId != User.ridString) {
                                    FriendsRepository.repository
                                        .queryFriendInfo(userId)
                                        ?.apply {
                                            name = nickName
                                        }
                                }
                                mViewModel.quoteTxt.value =
                                    "${name}：${
                                        toTitle(
                                            userId == User.ridString,
                                            mViewModel.isGroup.value == true,
                                            false
                                        )
                                    }"
                            }
                        })
                )
                .show()
        }*/
    }

    open fun onErrorClick(
        vo: TUIMessageBean
    ) {
        vo.message?.let { emMessage ->
            V2TMessageManager.sendMessage(
                mId,
                mViewModel.nickname.value ?: "",
                emMessage,
                mViewModel.isGroup.value == true,
                mViewModel.isTopic.value == true,
                true
            )
        }
    }

    open fun onProgress(progress: Int) {
    }

    /**
     * 权限检查
     *
     * @param neededPermissions 需要的权限
     * @return 是否全部被允许
     */
    private fun checkPermissions(
        activity: FragmentActivity,
        neededPermissions: Array<String>
    ): Boolean {
        if (neededPermissions.isEmpty()) {
            return true
        }
        var allGranted = true
        for (neededPermission in neededPermissions) {
            allGranted = allGranted and (ContextCompat.checkSelfPermission(
                activity,
                neededPermission
            ) == PackageManager.PERMISSION_GRANTED)
        }
        return allGranted
    }

    open fun onTouchStart() {
        mFragmentWeakReference?.get()?.activity?.run {
            val has = PermissionX.isGranted(this, Manifest.permission.RECORD_AUDIO)
            if (!has) {
                OursDialogHelper.showPermission(
                    this,
                    "Voice Message Authorization Instructions",
                    "We need your permission to enable the microphone so you can use the voice recording feature."
                )
            }
            val px =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) PermissionX.init(this)
                    .permissions(
                        Manifest.permission.RECORD_AUDIO
                    ) else PermissionX.init(this)
                    .permissions(
                        Manifest.permission.RECORD_AUDIO
                    )
            px.request { allGranted, grantedList, deniedList ->
                if (deniedList.isNotEmpty()) {
                    OursDialogHelper.showNoPermission(
                        this,
                        "麦克风权限未开启",
                        "无法正常使用发送录音功能,前往【设置>OURS】中打开麦克风权限"
                    )
                }
                OursDialogHelper.hidePermission()
                if (allGranted) {
                    mViewModel.isVoiceType.value = 2
                    ltb.start()
                    //RTASRHelper.helper.start(mViewModel.isOursMode.value == true)
                } else {
                    ltb.suspend()
                }

            }

        }
    }

    open fun onTouchStop(isChange: Boolean, isSand: Boolean, progress: Long) {
        /*CoroutineScope(Dispatchers.Main).launch {
            mViewModel.isVoiceType.value = 1
            mViewModel.isTouchTopType.value = !isSand
            if (isSand) {
                flRoot.context?.let {
                    if (it is Activity) {
                        LoadHelper.showLoading(it, "Processing data...")
                    }
                }
            }

            RTASRHelper.helper.stop { path, secondsToInt ->
                LoadHelper.dismissLoading()
                if (isSand) {
                    if (secondsToInt > 0) {
                        val isOursAudio =
                            mViewModel.isOursMode.value == true && RTASRHelper.helper.animationList.text.isNotEmpty()
                        if(mViewModel.isOursMode.value == true && RTASRHelper.helper.animationList.text.isEmpty()){
                            ToastHelper.createToastToTxt("Your OURS voice was not recognized and has been automatically converted to regular voice")
                        }
                        V2TMessageManager.sendVoiceMsg(
                            mId,
                            mViewModel.nickname.value ?: "",
                            mViewModel.isGroup.value == true,
                            mViewModel.isTopic.value == true,
                            path,
                            secondsToInt,
                            if (isOursAudio) GsonUtil.toJson(RTASRHelper.helper.animationList) else "",
                            RTASRHelper.helper.animationList.text
                        )
                        if (isOursAudio) {
                            TaskRepository.repository.setOursMessage()
                        }
                        AcousticsManager.instance.playbackAcoustics(if (isOursAudio) 8 else 7)
                    } else {
                        MainScope().launch {
                            ToastHelper.createToastToFail(
                                App.getContext(),
                                "Recording cannot be less than one second"
                            )
                        }
                    }
                } else {

                }

                null
            }
            //AIUIHelper.helper.destroyAgent()

        }
*/
    }

    fun onTouchChange(isTop: Boolean) {
        mViewModel.isTouchTopType.value = isTop
    }

    fun onTouchError() {
        mFragmentWeakReference?.get()?.activity?.run {
            val has = PermissionX.isGranted(this, Manifest.permission.RECORD_AUDIO)
            if (!has) {
                OursDialogHelper.showPermission(
                    this,
                    "Voice Message Authorization Instructions",
                    "We need your permission to enable the microphone so you can use the voice recording feature."
                )
            }
            val px =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) PermissionX.init(this)
                    .permissions(
                        Manifest.permission.RECORD_AUDIO
                    ) else PermissionX.init(this)
                    .permissions(
                        Manifest.permission.RECORD_AUDIO
                    )
            px.request { allGranted, grantedList, deniedList ->
                if (deniedList.isNotEmpty()) {
                    OursDialogHelper.showNoPermission(
                        this,
                        "麦克风权限未开启",
                        "无法正常使用发送录音功能,前往【设置>OURS】中打开麦克风权限"
                    )
                }
                OursDialogHelper.hidePermission()
                ltb.isDown = allGranted

            }
        }
    }

    open fun onDestroy() {
        //EventBus.getDefault().post(ClearConversationCountEvent(mConversationId ))
        //AIUIRepository.repository.stopPcmPlayer()
        if (V2TMessageManager.msgModuleMap.containsKey(mConversationId)) {
            V2TMessageManager.msgModuleMap.put(mConversationId, mAdapter.msgModules)
        }
        CommonIMManager.clearUnreadMessageCount(mConversationId, null)
        V2TMessageManager.doubleCheckerMode.value = false
        doubleCheckerMap.value?.clear()
        loadingMsgID.clear()
        removeInputListener()
    }


    open fun onCancelDoubleClick() {
        V2TMessageManager.doubleCheckerMode.value = false
        V2TMessageManager.doubleCheckerMap.value?.clear()
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun onDeleteClick() {
        V2TMessageManager.doubleCheckerMode.value = false
        if (V2TMessageManager.doubleCheckerMap.value.isNullOrEmpty()) {
            return
        }
        val msgList = mutableListOf<V2TIMMessage>()
        val list = mutableListOf<TUIMessageBean>()
        val tempList = mutableListOf<TUIMessageBean>()

        mAdapter.msgModules.forEachIndexed { index, tuiMessageBean ->
            if (V2TMessageManager.doubleCheckerMap.value?.containsKey(tuiMessageBean.message?.msgID) == true) {
                val msg = mAdapter.msgModules[index]
                if (index - 1 >= 0) {
                    val prvMsg = mAdapter.msgModules[index - 1]
                    if (prvMsg.msgType == TYPE_MSG_TIME) {
                        tempList.add(prvMsg)
                    }
                }
                if (index + 1 < mAdapter.msgModules.size) {
                    val nextMsg = mAdapter.msgModules[index + 1]
                    if (nextMsg.isExtType()) {
                        tempList.add(nextMsg)
                    }
                }
                list.add(msg)
            }
        }

        V2TMessageManager.deleteCacheMessages(mConversationId, msgList.map { it.msgID })
        V2TMessageManager.deleteMessages(msgList, null)
        list.forEach { msg -> mAdapter.msgModules.remove(msg) }
        tempList.forEach { msg -> mAdapter.msgModules.remove(msg) }

        mAdapter.notifyDataSetChanged()
        V2TMessageManager.doubleCheckerMap.value?.clear()
    }

    open fun onRelayClick() {
    }

    open fun onScrollUp() {
        scrollToDown(mViewModel.newMsgStartIndex.value ?: 0)
        mViewModel.newMsgCount.value = 0
    }

    open fun onCancelQuoteClick() {
        mViewModel.isQuote.value = false
        mViewModel.quoteTxt.value = ""
        mViewModel.quoteMsgId.value = ""
    }

    open fun onSetting() {

    }


    open fun removeInputListener() {
        flRoot.context?.let {
            if (it is Activity) {
                InputMonitorHelpUtils.removeSoftInputListener(it, softInputListener)
            }
        }
    }

    open fun registerInputListener() {
        flRoot.context?.let {
            if (it is Activity) {
                softInputListener = InputMonitorHelpUtils.softInputListener(it, object :
                    InputMonitorHelpUtils.SoftInputListener {
                    override fun onSoftKeyBoardVisible(visible: Boolean, keyBroadHeight: Int) {
                        if (visible) {
                            flRoot.setPadding(0, 0, 0, keyBroadHeight)
                        } else {
                            flRoot.setPadding(0, 0, 0, 0)
                        }
                    }
                })
            }
        }
    }

    open fun onCreateTopic(msg: V2TIMMessage) {
    }

    private fun addAdvancedMsgListener() {
        V2TIMManager.getMessageManager()
            .addAdvancedMsgListener(object : V2TIMAdvancedMsgListener() {
                override fun onRecvMessageRevoked(
                    msgID: String?,
                    operateUser: V2TIMUserFullInfo?,
                    reason: String?
                ) {

                }
            })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadList() {
        if (!isHasNextPage || loadingMsgID.contains(startMessage?.msgID ?: "")) {
            LogUtils.i(TAG, "loadList--return")
            return
        }
        val isInit = mAdapter.msgModules.isEmpty()

        val callback: ((ArrayList<TUIMessageBean>) -> Unit?) = { msgModules ->


//            for (msg in mAdapter.msgModules){
//                val item = msgModules.find { it.message?.msgID == msg.message?.msgID }
//                if(item != null){
//                    LogUtils.i(TAG,"已找到了重复的消息id = ${item?.message?.msgID}")
//                }
//            }

            mAdapter.msgModules.addAll(0, msgModules)


            val isNotify = msgModules.size == 1
            mAdapter.apply {
                ThreadUtils.runOnUiThread {
                    LogUtils.w("loadList", "isNotify (${isNotify})")
                    if (isNotify) {

                        notifyItemInserted(itemCount)
                        notifyItemRangeChanged(itemCount, msgModules.size + 1)
                    } else {
                        if (isInit) {
                            LogUtils.w("loadList", "notifyDataSetChanged")
                            notifyDataSetChanged()
                        } else {

                            // 记录当前的位置
                            val currentPosition = mAdapter.msgModules.size - 20
                            //notifyDataSetChanged()
                            // 恢复到之前的位置
                            //mLayoutManager.setScrollPosition(currentPosition)
                            //rvList.scrollToPosition(currentPosition.minus(1))

                            notifyItemRangeInserted(0, msgModules.size)
                            LogUtils.i(
                                "loadList",
                                "notifyDataSetChanged--${currentPosition.minus(1)}"
                            )
                        }
                    }
                    if (isInit || isNotify) {
                        //rvList.postDelayed({
                        LogUtils.w(
                            "loadList",
                            "addDataAndIndexOf recyclerView.scrollToPosition(itemCount - 1)"
                        )

                        //OnSoftPop(0)
                        rvList.postDelayed({
                            if (isInit) {
                                checkUnread { startUnrealIndex, startUnrealCount, vMap ->
                                    mViewModel.newMsgCount.value = startUnrealCount
                                    if (startUnrealIndex >= 0) {
                                        mViewModel.newMsgStartIndex.value = startUnrealIndex
                                    }
                                    //无论多少消息，只要进入到这个界面，都被认为是已读了.

                                    CommonIMManager.clearUnreadMessageCount(mConversationId, null)
                                    //EventBus.getDefault().post(EMClearBubbleToUserEvent(mId))
                                    null

                                }
                            }
                        }, 60)
                    }
                    isLoading = false
                }
            }
            null
        }


        if (V2TMessageManager.msgModuleMap.containsKey(mConversationId) && isInit) {
            val list = V2TMessageManager.msgModuleMap[mConversationId] ?: arrayListOf()
            isHasNextPage = list.size >= 20
            if (list.isNotEmpty()) {
                startMessage = list[0].message
            }
            callback.invoke(list)
            return
        }
        V2TMessageManager.t = System.currentTimeMillis()
        LogUtils.i(
            "loadList",
            "searchMsgFromDB--${System.currentTimeMillis() - V2TMessageManager.t}"
        )

        loadingMsgID.add(startMessage?.msgID ?: "")
        LogUtils.i(
            TAG,
            "search startMessage = ${startMessage?.msgID} timeStamp = ${startMessage?.timestamp}"
        )

        V2TMessageManager.searchMsgFromDB(
            mViewModel.isGroup.value == true,
            mId,
            startMessage
        ) { list, isHasNextPage ->
            this@BaseChatFragmentController.isHasNextPage = isHasNextPage
            list?.let {
                if (list.isNotEmpty()) {
                    startMessage = list[list.size - 1]
                }
                V2TMsgHomeCacheManager.addDataAndIndexOf(
                    mId,
                    list,
                    mViewModel.isGroup.value == true,
                    mViewModel.isTopic.value == true,
                    callback
                )
            }
            /* if (isInit) {
                 var itemHeight = 0//渲染全部item需要的高度
                 list.forEachIndexed { index, tuiMessageBean ->
                     LogUtils.i(
                         TAG,
                         "index--${index}!!!tuiMessageBean.itemHeight():${tuiMessageBean.itemHeight()}"
                     )
                     itemHeight += tuiMessageBean.itemHeight()
                 }
                 val screenHeightDP = DisplayUtils.px2dp(App.getContext(), screenHeight.toFloat())
                 isReverse = itemHeight >= screenHeightDP - 50//是否满屏 大概的值 差不多就算满了
                 LogUtils.w(
                     TAG,
                     "isReverse--${isReverse};;;;;itemHeight:${itemHeight}  screenHeight:${screenHeight}---screenHeight.dp:${screenHeightDP}"
                 )
             }*/
        }

        loadingMsgID.add(startMessage?.msgID ?: "")
    }

    fun checkUnread(
        callback: ((Int, Int, HashMap<String, TUIMessageBean>) -> Unit?)?
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            var startUnrealIndex = -1
            var startUnrealCount = 0
            val map = mAdapter.getVisibleItemMap()
            mAdapter.msgModules.forEachIndexed { index, itemVO ->
                itemVO.message?.let { message ->
                    if (itemVO.isUnread && message.elemType != V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM) {
                        if (map.containsKey(message.msgID)) {

                        } else {
                            if (startUnrealIndex == -1) {
                                startUnrealIndex = index
                            }
                            startUnrealCount++
                        }
                    }
                }
            }
            LogUtils.i(
                TAG,
                "startUnrealIndex = ${startUnrealIndex} startUnrealCount = ${startUnrealCount}"
            )
            CoroutineScope(Dispatchers.Main).launch {
                callback?.invoke(startUnrealIndex, startUnrealCount, map)
            }
        }
    }


    fun isRecyclerViewAtTop(): Boolean {
        return !rvList.canScrollVertically(-1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyDataSetChanged() {
        if (mAdapter.msgModules.isNotEmpty()) {
            mAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearMsg() {
        mAdapter.msgModules.clear()
        mAdapter.notifyDataSetChanged()
    }
}