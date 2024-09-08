package com.example.base.common.v2t

import androidx.lifecycle.MutableLiveData
import com.example.base.utils.LogUtils
import com.example.base.utils.ThreadUtils
import com.tencent.imsdk.v2.V2TIMCallback
import com.tencent.imsdk.v2.V2TIMCreateGroupMemberInfo
import com.tencent.imsdk.v2.V2TIMGroupApplication
import com.tencent.imsdk.v2.V2TIMGroupApplicationResult
import com.tencent.imsdk.v2.V2TIMGroupInfo
import com.tencent.imsdk.v2.V2TIMGroupInfoResult
import com.tencent.imsdk.v2.V2TIMGroupMemberFullInfo
import com.tencent.imsdk.v2.V2TIMGroupMemberInfoResult
import com.tencent.imsdk.v2.V2TIMGroupMemberOperationResult
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMValueCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object V2TGroupManager {
    var groupApplicationResult =
        MutableLiveData<HashMap<String, MutableList<V2TIMGroupApplication>>>()
    var groupApplicationCount = MutableLiveData<HashMap<String, Int>>()

    /**
     * 创建群聊
     */
    fun createGroup(
        groupName: String,
        faceUrl: String,
        users: MutableList<V2TIMCreateGroupMemberInfo>,
        callback: ((String?) -> Unit?)?
    ) {

        val v2TIMGroupInfo = V2TIMGroupInfo()
        v2TIMGroupInfo.groupType = V2TIMManager.GROUP_TYPE_PUBLIC
        v2TIMGroupInfo.groupName = groupName
        v2TIMGroupInfo.groupAddOpt = V2TIMGroupInfo.V2TIM_GROUP_ADD_ANY
        v2TIMGroupInfo.groupApproveOpt = V2TIMGroupInfo.V2TIM_GROUP_ADD_ANY
        v2TIMGroupInfo.faceUrl = faceUrl
        V2TIMManager.getGroupManager().createGroup(
            v2TIMGroupInfo,
            users,
            object : V2TIMValueCallback<String?> {
                override fun onSuccess(s: String?) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(s)
                    }
                }

                override fun onError(code: Int, desc: String) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(null)
                    }
                }
            })
    }

    /**
     * 退出群聊
     */
    fun quitGroup(
        isOwner: Boolean,
        groupId: String,
        callback: ((Boolean) -> Unit?)?
    ) {
        val c = object : V2TIMCallback {
            override fun onSuccess() {
                CoroutineScope(Dispatchers.Main).launch {
                    callback?.invoke(true)
                }
            }

            override fun onError(code: Int, desc: String?) {
                CoroutineScope(Dispatchers.Main).launch {
                    callback?.invoke(false)
                }
            }
        }
        if (isOwner) {
            V2TIMManager.getInstance().dismissGroup(groupId, c)
        } else {
            V2TIMManager.getInstance().quitGroup(groupId, c)
        }
    }

    /**
     * 申请加入群聊
     */
    fun joinGroup(
        groupId: String,
        isTopic: Boolean,
        callback: ((Boolean, Boolean) -> Unit?)?
    ) {
        V2TIMManager.getInstance().joinGroup(
            groupId,
            "",
            object : V2TIMCallback {
                override fun onSuccess() {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(true, true)
                    }
                }

                override fun onError(code: Int, desc: String?) {
                    CoroutineScope(Dispatchers.Main).launch {
                        when (code) {
                            10013 -> {
                                callback?.invoke(true, false)
                            }

                            else -> {
                                callback?.invoke(false, false)
                            }
                        }
                    }
                }
            })
    }

    /**
     * 获取群成员
     */
    fun getGroupMemberList(
        groupId: String, callback: ((MutableList<V2TIMGroupMemberFullInfo>?, Boolean) -> Unit?)?
    ) {
        getGroupMemberList(groupId, 0, mutableListOf(), callback)
    }

    fun getGroupMemberList(
        groupId: String,
        nextSeq: Long,
        infoList: MutableList<V2TIMGroupMemberFullInfo>,
        callback: ((MutableList<V2TIMGroupMemberFullInfo>?, Boolean) -> Unit?)?
    ) {
        V2TIMManager.getGroupManager().getGroupMemberList(
            groupId,
            V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_FILTER_ALL,
            nextSeq,
            object : V2TIMValueCallback<V2TIMGroupMemberInfoResult> {
                override fun onSuccess(s: V2TIMGroupMemberInfoResult) {
                    CoroutineScope(Dispatchers.Main).launch {
                        if (s.memberInfoList?.isNotEmpty() == true) {
                            infoList.addAll(s.memberInfoList)
                        }
                        if (s.nextSeq == 0L) {
                            callback?.invoke(infoList, true)
                        } else {
                            getGroupMemberList(groupId, s.nextSeq, infoList, callback)
                        }
                    }
                }

                override fun onError(code: Int, desc: String) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(
                            null, when (code) {
                                10007, 10010, 10015 -> {
                                    false
                                }

                                else -> {
                                    true
                                }
                            }
                        )
                    }
                }
            })
    }

    /**
     * 邀请群成员
     */
    fun inviteUserToGroup(
        groupId: String, list: MutableList<String>, callback: ((Boolean) -> Unit?)?
    ) {
        V2TIMManager.getGroupManager().inviteUserToGroup(
            groupId,
            list,
            object : V2TIMValueCallback<List<V2TIMGroupMemberOperationResult>> {
                override fun onSuccess(s: List<V2TIMGroupMemberOperationResult>) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(true)
                    }
                }

                override fun onError(code: Int, desc: String) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(false)
                    }
                }
            })
    }

    /**
     * 获取群信息
     */
    fun getGroupsInfo(
        groupId: String, callback: ((V2TIMGroupInfo?) -> Unit?)?
    ) {
        V2TIMManager.getGroupManager().getGroupsInfo(
            arrayListOf(groupId),
            object : V2TIMValueCallback<List<V2TIMGroupInfoResult>> {
                override fun onSuccess(s: List<V2TIMGroupInfoResult>) {
                    ThreadUtils.runOnUiThread {
                        if (s.isNotEmpty()) {
                            callback?.invoke(s[0].groupInfo)
                        } else {
                            callback?.invoke(null)
                        }
                    }
                }

                override fun onError(code: Int, desc: String) {
                    ThreadUtils.runOnUiThread {
                        callback?.invoke(null)
                    }
                }
            })
    }

    /**
     * 修改群信息
     */
    fun setGroupsInfo(
        group: V2TIMGroupInfo, callback: ((Boolean) -> Unit?)?
    ) {
        V2TIMManager.getGroupManager().setGroupInfo(
            group,
            object : V2TIMCallback {
                override fun onSuccess() {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(true)
                    }
                }

                override fun onError(code: Int, desc: String?) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(false)
                    }
                }
            })
    }

    /**
     * 设置群扩展
     */
    fun setGroupAttributes(
        groupId: String,
        ext: HashMap<String, String>,
        callback: ((Boolean) -> Unit?)?
    ) {
        getGroupsInfo(groupId) { group ->
            group?.let {
                ext.forEach {
                    if (it.value.isNotEmpty()) {

                        group.customInfo[it.key] = it.value.toByteArray()
                    }
                }

                val newGroup = V2TIMGroupInfo()
                newGroup.groupID = group.groupID
                newGroup.customInfo = group.customInfo
                setGroupsInfo(newGroup, callback)
            }
        }
    }

    /**
     * 获取群扩展
     */
    fun getGroupAttributes(
        groupId: String,
        ids: MutableList<String>,
        callback: ((Map<String, ByteArray>?) -> Unit?)?
    ) {
        getGroupsInfo(groupId) { group ->
            group?.let {
                callback?.invoke(group.customInfo)
            }
        }
    }

    /**
     * 修改群成员身份
     */
    fun setGroupAdmin(
        groupId: String,
        adminId: String,
        isAdmin: Boolean,
        callback: ((Boolean) -> Unit?)?
    ) {
        V2TIMManager.getGroupManager().setGroupMemberRole(
            groupId,
            adminId,
            if (isAdmin) V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_ROLE_ADMIN else V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_ROLE_MEMBER,
            object : V2TIMCallback {
                override fun onSuccess() {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(true)
                    }
                }

                override fun onError(code: Int, desc: String?) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(false)
                    }
                }
            })
    }

    /**
     * 移除群内用户
     */
    fun kickGroupMember(
        groupId: String,
        ids: MutableList<String>,
        callback: ((Boolean) -> Unit?)?
    ) {
        V2TIMManager.getGroupManager().kickGroupMember(
            groupId,
            ids,
            "",
            0,
            object : V2TIMValueCallback<List<V2TIMGroupMemberOperationResult>> {
                override fun onSuccess(s: List<V2TIMGroupMemberOperationResult>) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(s.isNotEmpty())
                    }
                }

                override fun onError(code: Int, desc: String) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(false)
                    }
                }
            })
    }

    /**
     * 转移群主
     */
    fun changeOwner(
        groupId: String,
        newOwnerId: String,
        callback: ((Boolean) -> Unit?)?
    ) {
        V2TIMManager.getGroupManager()
            .transferGroupOwner(groupId, newOwnerId, object : V2TIMCallback {
                override fun onError(code: Int, desc: String) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(false)
                    }
                }

                override fun onSuccess() {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(true)
                    }
                }
            })
    }

    /**
     * 设置我的群昵称
     */
    fun modifyMyGroupNickname(
        groupID: String,
        nickname: String?,
        callback: ((Boolean) -> Unit?)?
    ) {
        val v2TIMGroupMemberFullInfo = V2TIMGroupMemberFullInfo()
        v2TIMGroupMemberFullInfo.userID = V2TIMManager.getInstance().loginUser
        v2TIMGroupMemberFullInfo.nameCard = nickname
        V2TIMManager.getGroupManager().setGroupMemberInfo(
            groupID,
            v2TIMGroupMemberFullInfo,
            object : V2TIMCallback {
                override fun onError(code: Int, desc: String) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(false)
                    }
                }

                override fun onSuccess() {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(true)
                    }
                }
            })
    }


    /**
     * 获取群申请列表
     */
    fun getGroupApplicationList(
        callback: ((HashMap<String, MutableList<V2TIMGroupApplication>>?, HashMap<String, Int>?) -> Unit?)?
    ) {
        V2TIMManager.getGroupManager().getGroupApplicationList(
            object : V2TIMValueCallback<V2TIMGroupApplicationResult> {
                override fun onSuccess(s: V2TIMGroupApplicationResult) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val map = HashMap<String, MutableList<V2TIMGroupApplication>>()
                        val countMap = HashMap<String, Int>()
                        s.groupApplicationList.forEach {
                            if (it.handleStatus == V2TIMGroupApplication.V2TIM_GROUP_APPLICATION_HANDLE_STATUS_UNHANDLED) {
                                countMap[it.groupID] = (countMap[it.groupID] ?: 0) + 1
                            }
                            if (map.contains(it.groupID)) {
                                map[it.groupID]?.add(it)
                            } else {
                                map[it.groupID] = mutableListOf(it)
                            }
                        }
                        CoroutineScope(Dispatchers.Main).launch {
                            groupApplicationResult.value = map
                            groupApplicationCount.value = countMap
                            callback?.invoke(map, countMap)
                        }
                    }
                }

                override fun onError(code: Int, desc: String) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(null, null)
                    }
                }
            })
    }

    /**
     * 同意群申请
     */
    fun acceptGroupApplication(
        groupApplication: V2TIMGroupApplication,
        callback: ((Boolean) -> Unit?)?
    ) {
        V2TIMManager.getGroupManager().acceptGroupApplication(
            groupApplication,
            "",
            object : V2TIMCallback {
                override fun onSuccess() {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(true)
                    }
                }

                override fun onError(code: Int, desc: String?) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(false)
                    }
                }
            })
    }

    /**
     * 拒绝群申请
     */
    fun refuseGroupApplication(
        groupApplication: V2TIMGroupApplication,
        callback: ((Boolean) -> Unit?)?
    ) {
        V2TIMManager.getGroupManager().refuseGroupApplication(
            groupApplication,
            "",
            object : V2TIMCallback {
                override fun onSuccess() {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(true)
                    }
                }

                override fun onError(code: Int, desc: String?) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(false)
                    }
                }
            })
    }

    /**
     * 获取已加入的群组列表
     */
    fun getJoinedGroupList(
        callback: ((MutableList<V2TIMGroupInfo>?) -> Unit?)?
    ) {
        V2TIMManager.getGroupManager()
            .getJoinedGroupList(object : V2TIMValueCallback<MutableList<V2TIMGroupInfo>?> {
                override fun onSuccess(v2TIMGroupInfos: MutableList<V2TIMGroupInfo>?) {
                    callback?.invoke(v2TIMGroupInfos)
                }

                override fun onError(code: Int, desc: String) {
                    CoroutineScope(Dispatchers.Main).launch {
                        callback?.invoke(null)
                    }
                }
            })
    }

    /**
     * 添加群组监听
     */
    fun addGroupListener(
        callback: IMGroupCallback
    ) {
        IMCallback.addIMGroupCallback(callback)
    }

    /**
     * 移除群组监听
     */
    fun removeGroupListener(
        callback: IMGroupCallback
    ) {
        IMCallback.removeIMGroupCallback(callback)
    }
}