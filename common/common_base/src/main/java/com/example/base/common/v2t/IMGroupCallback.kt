package com.example.base.common.v2t

import com.tencent.imsdk.v2.V2TIMGroupChangeInfo
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo

interface IMGroupCallback {
    /**
     * 有用户加入群（全员能够收到）
     */
    fun onMemberEnter(groupID: String, memberList: List<V2TIMGroupMemberInfo>){}
    /**
     * 有用户离开群（全员能够收到）
     */
    fun onMemberLeave(groupID: String, member: V2TIMGroupMemberInfo){}
    /**
     * 某些人被拉入某群（全员能够收到）
     */
    fun onMemberInvited(groupID: String,opUser:V2TIMGroupMemberInfo, memberList: List<V2TIMGroupMemberInfo>){}
    /**
     * 某些人被踢出某群（全员能够收到）
     */
    fun onMemberKicked(groupID: String,opUser:V2TIMGroupMemberInfo, memberList: List<V2TIMGroupMemberInfo>){}
    /**
     * 群被解散了（全员能收到）
     */
    fun onGroupDismissed(groupID: String,opUser:V2TIMGroupMemberInfo){}

    /**
     * 群信息被修改（全员能收到） 以下字段的修改可能会引发该通知 groupName & introduction & notification & faceUrl & owner & allMute & custom 控制指定字段
     */
    fun onGroupInfoChanged(groupID: String){}
    /**
     * 有新的加群请求（只有群主或管理员会收到）
     */
    fun onReceiveJoinApplication(groupID: String,member:V2TIMGroupMemberInfo, opReason: String?){}
    /**
     * 加群请求已经被群主或管理员处理了（只有申请人能够收到）
     */
    fun onApplicationProcessed(groupID: String,opUser:V2TIMGroupMemberInfo, isAgreeJoin: Boolean?,opReason:String?){}
    /**
     * 指定管理员身份
     */
    fun onGrantAdministrator(groupID: String,opUser:V2TIMGroupMemberInfo, memberList:List<V2TIMGroupMemberInfo>){}
    /**
     * 取消管理员身份
     */
    fun onRevokeAdministrator(groupID: String,opUser:V2TIMGroupMemberInfo, memberList:List<V2TIMGroupMemberInfo>){}
    /**
     * 主动退出群组（主要用于多端同步）
     */
    fun onQuitFromGroup(groupID: String){}


}

