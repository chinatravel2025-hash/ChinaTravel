package com.travel.guide.api

data class IMChatInfo(
    var list:ArrayList<IMChatDataInfo>?
)

data class IMChatDataInfo(
    var id:Long?,
    var sign:Long?,
    var customer_id:String?
)