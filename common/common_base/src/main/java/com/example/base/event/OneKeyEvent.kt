package com.example.base.event

class OneKeyInitEvent(var success: Boolean,val msg:String)
class OneKeyCheckEvent(var success: Boolean,val isOther:Boolean,val token:String,val msg:String)