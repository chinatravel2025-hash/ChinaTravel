package com.example.base.event

class EMExitEvent(val isDelete:Boolean,val conversationId:String)
class EMClearEvent(val conversationId:String)
class EMCreateEvent
class EMRequestToJoinEvent(val groupId:String)

class EMMsgStateUpdateEvent(val conversationId:String)

class EMGroupOwnerUpdateEvent(val groupId:String)

class EMClearBubbleToUserEvent(val conversationId:String)

class EMGroupNameUpdateEvent(val groupId:String,val groupName:String)

class PhoneOpenEvent(val nickname:String?,val rid:String?,val isSender:Boolean?,val headPic:String?,val isConnect:Boolean?,val msgId:String?,val avatar:String?,val type:String?,val isInvite:Boolean)
class PhoneCloseEvent

class PhoneRejectEvent
class MoodStartEvent(val rid:String,val action:String)
class MoodEndEvent(val rid:String,val path:String)
class QuickSetMoodEvent
class GroupTagEvent
data class GroupInfoChangedEvent(val announcement:String)
//体验官完成后的通知

data class EmoSelectedPageEvent(val position:Int)
class EMAddMsgEvent(val rid:String)
//class FriendUpdateEvent(val info: FriendInfo)

class VideoPageEvent(val index: Int)
class NoReadCountEvent(val count: Long?)

